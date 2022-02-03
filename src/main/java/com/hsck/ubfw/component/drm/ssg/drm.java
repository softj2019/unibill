package com.hsck.ubfw.component.drm.ssg;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import SCSL.SLDsFile;

/**
*
* $unibill.com.util.StringUtil.java
* 클래스 설명 :
*
* @author 홍길동
* @since 2014.11.03
* @version 1.0.0
* @see
*
* <pre>
* << 개정이력(Modification Information) >>
*
*  수정일		 		수정자			수정내용
*  -------    		--------    ---------------------------
*  2014.11.03		박현우		최초 생성
* </pre>
*/
public class drm {
    public drm() throws Exception {
    	
    }
    
    public int decDRM(String srcF, String desF) throws Exception{
    	String srcFile,dstFile;
    	InputStream in = this.getClass().getResourceAsStream("/egovframework/egovProps/globals.properties");
		Properties props = new Properties();
		String path = "";
		String subpath = "";
		String fullpath = "";
//    	
//    	srcFile="/app/test.xls";
//		dstFile="/app/test_Dec.xls";
		props.load(in);
    	if(File.separator.equals("\\")){
			path = props.getProperty("Globals.drm.Wclpath");
			subpath = props.getProperty("Globals.drm.Wsubpath");
		}else{
			path = props.getProperty("Globals.drm.Lclpath");
			subpath = props.getProperty("Globals.drm.Lsubpath");
		}
    	fullpath = path + File.separator +subpath;
		System.out.println("drm fullpath : " + fullpath);
		//path = File.separator + "app" + File.separator + "unibill" + File.separator + "tomcat" + File.separator + "webapps" + File.separator + "fileupload" + File.separator + "excel_download";
		

    	srcFile=srcF;
		dstFile=desF;
		SLDsFile sFile = new SLDsFile();
		//sFile.SettingPathForProperty("/egovframework/softcamp/drmProps/softcamp.properties");
		sFile.SettingPathForProperty(fullpath + File.separator + "drmProps" + File.separator + "softcamp.properties");
		
		
		//int GradeDACCreateDecryptFileDAC = sFile.CreateDecryptFileDAC ("/egovframework/softcamp/KeyFile/keyDAC_SVR0.sc", "SECURITYDOMAIN", srcFile, dstFile);
		int GradeDACCreateDecryptFileDAC = sFile.CreateDecryptFileDAC (fullpath + File.separator + "KeyFile" + File.separator +  "keyDAC_SVR0.sc", "SECURITYDOMAIN", srcFile, dstFile);
		System.out.println("GradeDACCreateDecryptFileDAC [" + GradeDACCreateDecryptFileDAC + "]");
		
//		File df = new File(srcFile);
//		
//		if(df.exists()) {
//			df.delete();
//		}
		
		return GradeDACCreateDecryptFileDAC;
    }

}
