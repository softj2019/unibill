package com.hsck.ubfw.component.com.cmm.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {
	
	/**
     * 그 달에 해당되는 분기가 반환되는 메서드
     *
     * @return int
     */
    public static int quarterYear(int month) {
      return (int) Math.ceil( month / 3.0 );
    }

    /**
     * 현재 월 반환 메서드
     *
     * @return int
     */
    public static int currentMonth() {
      Calendar cal = Calendar.getInstance();

      return cal.get(Calendar.MONTH) + 1; // 현재 월만 반환
    }
    
    /**
     * 현재년도를 4자리로 리턴한다.
     *
     * @return String
     */
    static public String getYear() {
        Calendar cal = Calendar.getInstance();

        return String.valueOf(cal.get(Calendar.YEAR));
    }


    /**
     * 현재월을 2자리로 리턴한다.
     *
     * @return String
     */
    static public String getMonth() {
        Calendar cal = Calendar.getInstance();

        String month = String.valueOf(cal.get(Calendar.MONTH) + 1);

        if ((cal.get(Calendar.MONTH) + 1) < 10) month = "0" +
                String.valueOf((cal.get(Calendar.MONTH) + 1));
        else month = String.valueOf((cal.get(Calendar.MONTH) + 1));

        return month;
    }
    
    /**
	 * 	Log.info( DateUtil.getCurrentDateTimeString() );									// 20020719094837
	 * 	Log.info( DateUtil.getCurrentDateString() ) ;                                      	// 20020719
	 * 	Log.info( DateUtil.getCurrentDateString("yyyy-MM-dd") ) ;                     		// 2002-07-19
	 * 	Log.info( DateUtil.getCurrentTimeString() ) ;                                      	// 094837
	 * 	Log.info( DateUtil.getCurrentDateString("HH:mm:ss") ) ;                        		// 09:48:37
	 * 	Log.info( DateUtil.getCurrentDateString("hh:mm:ss") ) ;                         	// 09:48:37
	 *  Log.info( DateUtil.convertToTimestamp("20020717") ) ;                          		// 2002-07-17 09:48:37.459
	 * 
	 */
	//현재 날짜를 yyyy-MM-dd hh:mm:ss 형식으로 반환한다.
	public static Timestamp getCurrentDateTime1() {
		return convertToTimestamp(getCurrentDateString());
	}
	
	//현재 날짜를 yyyyMMddhhmmss 형식으로 반환한다.
	public static String getCurrentDateTime2() {
		return getCurrentDateString()+getCurrentTimeString();
	}
	
	//현재 날짜를 yyyyMMdd hh:mm:ss 형식으로 반환한다.
	public static String getCurrentDateTimeString() {
		return (getCurrentDateString("yyyyMMdd") + getCurrentDateString("HHmmss"));
	}		
	
	public static String getCurrentDateTimeApprovalString() {
		return (getCurrentDateString("yyMMdd") + getCurrentDateString("HHmmss"));
	}
	
	//현재 날짜를 yyyyMMdd hh:mm:ss 형식으로 반환한다.
	public static String getNowDateTimeString() {
		return (getCurrentDateString("yyyy-MM-dd") + " " + getCurrentDateString("HH:mm:ss"));
	}
	
	//현재 날짜를 yyyyMMdd 형식으로 반환한다.
	public static String getCurrentDateString() {
		return getCurrentDateString("yyyyMMdd");
	}
	
	//현재 시각을  HHmmss 형식으로 반환한다.
	public static String getCurrentTimeString() {
		return getCurrentDateString("HHmmss");
	}
	
	//현재날짜를 주어진 pattern 에 따라 반환한다.
	public static String getCurrentDateString(String pattern) {
		return convertToString(getCurrentTimeStamp(), pattern);
	}
	
	//yyyyMMdd 형식의 날짜를 yyyy/MM/dd 형식으로 반환한다.
	public static Timestamp getCurrentTimeStamp() {
		Calendar cal = new GregorianCalendar();
		Timestamp result = new Timestamp(cal.getTime().getTime());
		return result;
	}
	
	//yyyyMMdd 형식의 Timestamp 날짜를 yyyy/MM/dd 형식으로 반환한다.
	public static String convertToString(Timestamp dateData) {
		return convertToString(dateData, "yyyy/MM/dd");
	}
	
	//yyyyMMdd 형식의 Timestamp 날짜를 pattern 에 따른 형식으로 반환한다.
	public static String convertToString(Timestamp dateData, String pattern) {
		return convertToString(dateData, pattern, java.util.Locale.KOREA);
	}
	
	//yyyyMMdd 형식의 Timestamp 날짜를 pattern 과 locale 에 따른 형식으로 반환한다.
	public static String convertToString(	Timestamp dateData,
											String pattern,
											java.util.Locale locale) {
		if (dateData == null) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(pattern, locale);
		formatter.applyPattern( pattern );
		return formatter.format(dateData);
	}
	
	//yyyyMMdd 형식의  날짜를 Timestamp 로  반환한다.
	public static Timestamp convertToTimestamp(String dateData) {
		if (dateData == null)
			return null;
		if (dateData.trim().equals(""))
			return null;
		int dateObjLength = dateData.length();
		String yearString = "2002";
		String monthString = "01";
		String dayString = "01";
		if (dateObjLength >= 4) {
			yearString = dateData.substring(0, 4);
		}
		if (dateObjLength >= 6) {
			monthString = dateData.substring(4, 6);
		}
		if (dateObjLength >= 8) {
			dayString = dateData.substring(6, 8);
		}
		int year = Integer.parseInt(yearString);
		int month = Integer.parseInt(monthString) - 1;
		int day = Integer.parseInt(dayString);
		Calendar cal = new GregorianCalendar();
		cal.set(year, month, day);
		//cal.getTime();
		return new Timestamp(cal.getTime().getTime());
	}
	
	//yyyyMMddHHmmss 형식의  날짜시각을 Timestamp 로  반환한다.
	public static Timestamp convertToTimestampHMS(String dateData) {
		if (dateData == null)
			return null;
		if (dateData.trim().equals(""))
			return null;
		String yearString = dateData.substring(0, 4);
		String monthString = dateData.substring(4, 6);
		String dayString = dateData.substring(6, 8);
		String hourString = dateData.substring(8, 10);
		String minString = dateData.substring(10, 12);
		String secString = dateData.substring(12, 14);
		int year = Integer.parseInt(yearString);
		int month = Integer.parseInt(monthString) - 1;
		int day = Integer.parseInt(dayString);
		int hour = Integer.parseInt(hourString);
		int min = Integer.parseInt(minString);
		int sec = Integer.parseInt(secString);
		Calendar cal = new GregorianCalendar();
		cal.set(year, month, day, hour, min, sec);
		return new Timestamp(cal.getTime().getTime());
	}
	
	public static String timestampHMSOfName(String dateData) {
		if (dateData == null)
			return null;
		if (dateData.trim().equals(""))
			return null;
		String yearString = dateData.substring(0, 4);
		String monthString = dateData.substring(4, 6);
		String dayString = dateData.substring(6, 8);
		String hourString = dateData.substring(8, 10);
		String minString = dateData.substring(10, 12);
		String secString = dateData.substring(12, 14);
		String makeStr =	yearString	+ monthString + dayString + hourString + minString	+ secString;
		return makeStr;
	}
}