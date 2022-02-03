package com.hsck.ubfw.component.com.cmm.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.sql.Clob;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Transport;
import javax.servlet.http.HttpServletRequest;

import org.springframework.mail.javamail.JavaMailSenderImpl;

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
public class mailUtil {
	public static JavaMailSenderImpl setMailConnectInfo(JavaMailSenderImpl mailSend, String host, int port, boolean auth, boolean debug, String id, String passwd) {
		mailSend.setHost(host);
    	mailSend.setPort(port);
    	
    	Properties javaMailProps = new Properties();
    	
    	javaMailProps.put("mail.smtp.auth", auth);
        javaMailProps.put("mail.debug", debug); // 기초코드에서 설정 
        
        mailSend.setUsername(id);
        mailSend.setPassword(passwd);
        
		return mailSend;
	}
	
	public static Transport setMailConnectInfo1(JavaMailSenderImpl mailSend, String host, int port, boolean auth, boolean debug, String id, String passwd) {
		Properties javaMailProps = new Properties();
		
		javaMailProps.put("mail.smtp.auth", auth);
        javaMailProps.put("mail.debug", debug); // 기초코드에서 설정 
        
        Transport transport = null;
		try {
			transport = mailSend.getSession().getTransport("smtp");
			transport.connect(host, port, id, passwd);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		return transport;
		
		
		
		
		
		//mailSend.setHost(host);
		//mailSend.setPort(port);
    	//mailSend.setUsername(id);
        //mailSend.setPassword(passwd);
        
		
	}
	
	public static boolean isValidEmail(String email) { 
		boolean err = false; 
		String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$"; 
		Pattern p = Pattern.compile(regex); 
		Matcher m = p.matcher(email); 
		
		if(m.matches()) { 
			err = true; 
		} 
		return err; 
	}
}
