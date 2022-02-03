package com.hsck.ubfw.component.com.cmm.util;

import egovframework.rte.fdl.idgnr.impl.EgovTableIdGnrServiceImpl;
import java.math.BigDecimal;
import java.util.Locale;

import egovframework.rte.fdl.cmmn.exception.FdlException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Created by LSW on 2017-01-04.
 */
public class ExtensionTableIdGnrServiceImpl extends EgovTableIdGnrServiceImpl {


    private static final Logger LOGGER = LoggerFactory.getLogger(EgovTableIdGnrServiceImpl.class);

    /**
     * ID생성을 위한 테이블 정보 디폴트는 ids임.
     */
    private String table = "CM_CFGSN";

    /**
     * 테이블 정보에 기록되는 대상 키정보 대개의 경우는 아이디로 생성되는 테이블명을 기재함
     */
    private String tableName = "id";
    /**
     * 테이블 정보에 기록되는 대상 키정보 대개의 경우는 아이디로 생성되는 테이블명을 기재함
     */
    private String columnName = "id2";

    /**
     * 테이블명(구분값)에 대한 테이블 필드명 지정
     */
    private String tableNameFieldName = "TBL_NM";

    /**
     * 컬럼명(구분값)에 대한 컬럼 필드명 지정
     */
    private String columnNameFieldName = "COL_NM";

    /**
     * Next Id 정보를 보관하는 필드명 지정
     */
    private String nextIdFieldName = "SEQ";



    /**
     * Jdbc template
     */
    private JdbcTemplate jdbcTemplate;

    /**
     * TransactionTemplate
     */
    private TransactionTemplate transactionTemplate;

    /**
     * 생성자
     */
    public ExtensionTableIdGnrServiceImpl() {
    }

    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);

        this.jdbcTemplate = new JdbcTemplate(dataSource);

        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);

        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.transactionTemplate.setPropagationBehaviorName("PROPAGATION_REQUIRES_NEW");
        this.transactionTemplate.setIsolationLevelName("ISOLATION_READ_COMMITTED");
    }

    /**
     * tableName에 대한 초기 값이 없는 경우 초기 id 값 등록 (blockSize 처리)
     *
     * @param useBigDecimals
     */
    private Object insertInitId(final boolean useBigDecimals, final int blockSize) {

        LOGGER.debug(messageSource.getMessage("debug.idgnr.init.idblock", new Object[] { tableName , columnName }, Locale.getDefault()).replaceAll("[\r\n]",""));

        Object initId = null;

        String insertQuery = "INSERT INTO " + table + "(" + tableNameFieldName + ", " + columnNameFieldName + ", " + nextIdFieldName + ") " + "values('" + tableName + "', '" + columnName + "' , ?)";

        LOGGER.debug("Insert Query : {}", insertQuery.replaceAll("[\r\n]",""));

        if (useBigDecimals) {
            initId = new BigDecimal(blockSize);

        } else {
            initId = new Long(blockSize);
        }

        jdbcTemplate.update(insertQuery, initId);

        return initId;

    }

    /**
     * blockSize 대로 ID 지정
     *
     * @param blockSize 지정되는 blockSize
     * @param useBigDecimals BigDecimal 사용 여부
     * @return BigDecimal을 사용하면 BigDecimal 아니면 long 리턴
     * @throws FdlException ID생성을 위한 블럭 할당이 불가능할때
     */
    private Object allocateIdBlock(final int blockSize, final boolean useBigDecimals) throws FdlException {

        LOGGER.debug(messageSource.getMessage("debug.idgnr.allocate.idblock", new Object[] { new Integer(blockSize), tableName , columnName }, Locale.getDefault()).replaceAll("[\r\n]",""));

        try {
            return transactionTemplate.execute(new TransactionCallback<Object>() {
                @SuppressWarnings("deprecation")
                public Object doInTransaction(TransactionStatus status) {

                    Object nextId;
                    Object newNextId;

                    try {

                        //String selectQuery = "SELECT " + nextIdFieldName + " FROM " + table + " WHERE " + tableNameFieldName + " = ? For UPDATE";
                        String selectQuery = "SELECT " + nextIdFieldName + " FROM " + table + " WHERE " + tableNameFieldName + " = ? AND " + columnNameFieldName + " = ?";

                        LOGGER.debug("Select Query : {}", selectQuery.replaceAll("[\r\n]",""));

                        if (useBigDecimals) {
                            try {
                                nextId = jdbcTemplate.queryForObject(selectQuery, new Object[] { tableName , columnName }, BigDecimal.class);
                                LOGGER.debug("#### nextId (BigDecimal.class) : " + nextId.toString().replaceAll("[\r\n]",""));
                            } catch (EmptyResultDataAccessException erdae) {
                                nextId = null;
                            }

                            if (nextId == null) { // no row
                                insertInitId(useBigDecimals, blockSize);

                                return new BigDecimal(0);
                            }
                        } else {
                            try {
//                                nextId = jdbcTemplate.queryForLong(selectQuery, new Object[] { tableName , columnName });
                                nextId = jdbcTemplate.queryForObject(selectQuery, new Object[] { tableName , columnName },Long.class);
                                LOGGER.debug("#### nextId (Long.class) : " + nextId.toString().replaceAll("[\r\n]",""));

                            } catch (EmptyResultDataAccessException erdae) {
                                nextId = -1L;
                            }

                            if ((Long) nextId == -1L) { // no row
                                insertInitId(useBigDecimals, blockSize);

                                return new Long(0);
                            }
                        }
                    } catch (DataAccessException dae) {
                        dae.printStackTrace();
                        LOGGER.debug("{}", dae);
                        status.setRollbackOnly();
                        throw new RuntimeException(new FdlException(messageSource, "error.idgnr.select.idblock", new String[] { tableName , columnName }, null));
                    }

                    try {
                        String updateQuery = "UPDATE " + table + " SET " + nextIdFieldName + " = ?" + " WHERE " + tableNameFieldName + " = ? AND " + columnNameFieldName + " = ?";

                        LOGGER.debug("Update Query : {}", updateQuery.replaceAll("[\r\n]",""));

                        if (useBigDecimals) {
                            newNextId = ((BigDecimal) nextId).add(new BigDecimal(blockSize));

                        } else {
                            newNextId = new Long(((Long) nextId).longValue() + blockSize);
                        }

                        jdbcTemplate.update(updateQuery, new Object[] { newNextId , tableName , columnName });

                        return nextId;
                    } catch (DataAccessException dae) {
                        status.setRollbackOnly();
                        throw new RuntimeException(new FdlException(messageSource, "error.idgnr.update.idblock", new String[] { tableName , columnName }, null));
                    }
                }
            });
        } catch (RuntimeException re) {
            if (re.getCause() instanceof FdlException) {
                throw (FdlException) re.getCause();
            } else {
                throw re;
            }
        }
    }

    /**
     * blockSize 대로 ID 지정(BigDecimal)
     *
     * @param blockSize 지정되는 blockSize
     * @return 할당된 블럭의 첫번째 아이디
     * @throws FdlException ID생성을 위한 블럭 할당이 불가능할때
     */
    protected BigDecimal allocateBigDecimalIdBlock(int blockSize) throws FdlException {
        return (BigDecimal) allocateIdBlock(blockSize, true);
    }

    /**
     * blockSize 대로 ID 지정(long)
     *
     * @param blockSize 지정되는 blockSize
     * @return 할당된 블럭의 첫번째 아이디
     * @throws FdlException ID생성을 위한 블럭 할당이 불가능할때
     */
    protected long allocateLongIdBlock(int blockSize) throws FdlException {
        Long id = (Long) allocateIdBlock(blockSize, false);

        return id.longValue();
    }

    /**
     * ID생성을 위한 테이블 정보 Injection
     *
     * @param table config로 지정되는 정보
     */
    public void setTable(String table) {
        this.table = table;
    }

    /**
     * ID 생성을 위한 테이블의 키정보 ( 대개의경우는 대상 테이블명을 기재함 )
     *
     * @param tableName config로 지정되는 정보
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     *  테이블명(구분값)에 대한 테이블 필드명 정보 지정
     *
     * @param tableNameFieldName
     */
    public void setTableNameFieldName(String tableNameFieldName) {
        this.tableNameFieldName = tableNameFieldName;
    }

    /**
     * Next Id 정보를 보관하는 필드명 정보 지정
     *
     * @param nextIdFieldName
     */
    public void setNextIdFieldName(String nextIdFieldName) {
        this.nextIdFieldName = nextIdFieldName;
    }


    public void setColumnNameFieldName(String columnNameFieldName) {
        this.columnNameFieldName = columnNameFieldName;
    }
}
