/*******************************************************************************
 *
 * Revision History
 * Author   Date            Description
 * ------   ----------      ------------------------------------
 * reo    2017. 5. 8.         First Draft.
 *
 *******************************************************************************/ 
package com.hsck.ubfw.component.com.cmm.service.impl;

import javax.activation.DataSource;

import javax.activation.FileDataSource;

import javax.mail.internet.MimeUtility;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.hsck.ubfw.component.com.cmm.service.MailSendService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
//import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.MailException;
//import org.springframework.mail.javamail.MimeMessagePreparator;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * JavaMailSender 를 이용하여 메일 발송 처리 클레스.
 */
@Service("MailSendService")
public class MailSendServiceImpl implements MailSendService {

	//private static final Logger LOGGER = LoggerFactory.getLogger(MailSendServiceImpl.class);
	
    @Autowired
    private JavaMailSender mailSender;

     @Override
     public void sendMail(String subject, String text, String fromUser, String toUser, String[] toCC, List<HashMap<String, String>> attachFileList) throws Exception {
         final MimeMessage message = mailSender.createMimeMessage();

         final MimeMessageHelper messageHelper;

         if( null != attachFileList && 0 < attachFileList.size() ){
             messageHelper = new MimeMessageHelper(message, true, "UTF-8");
             for(HashMap<String,String> attachFileMap : attachFileList ){

                 String attachFileAbsolutePath = attachFileMap.get("attachFileAbsolutePath"); // "c:\\attach\\20170508000001.xlsx"
                 String attachFileName = attachFileMap.get("attachFileName"); // "책목록.xlsx"
                 if( new File(attachFileAbsolutePath).exists() ){

                     DataSource dataSource = new FileDataSource(attachFileAbsolutePath);
                     messageHelper.addAttachment(MimeUtility.encodeText(attachFileName, "UTF-8", "B"), dataSource);
                 }
             }
         }else{

             messageHelper = new MimeMessageHelper(message, false, "UTF-8");
         }
         messageHelper.setSubject(subject);
         messageHelper.setTo(toUser);
         messageHelper.setCc(toCC);
         messageHelper.setFrom(fromUser);
         messageHelper.setText(text, true);

         mailSender.send(message);
     }


     @Override
    public void sendMailMulti(String subject, String text, String fromUser, String[] toUser, String[] toCC, List<HashMap<String, String>> attachFileList) throws Exception {
         final MimeMessage message = mailSender.createMimeMessage();

         final MimeMessageHelper messageHelper;

         if (null != attachFileList && 0 < attachFileList.size()) {
             messageHelper = new MimeMessageHelper(message, true, "UTF-8");
             for (HashMap<String, String> attachFileMap : attachFileList) {

                 String attachFileAbsolutePath = attachFileMap.get("attachFileAbsolutePath"); // "c:\\attach\\20170508000001.xlsx"
                 String attachFileName = attachFileMap.get("attachFileName"); // "책목록.xlsx"
                 if( new File(attachFileAbsolutePath).exists() ){

                     DataSource dataSource = new FileDataSource(attachFileAbsolutePath);
                     messageHelper.addAttachment(MimeUtility.encodeText(attachFileName, "UTF-8", "B"), dataSource);
                 }
             }
         } else {

             messageHelper = new MimeMessageHelper(message, false, "UTF-8");
         }
         messageHelper.setSubject(subject);
         messageHelper.setTo(toUser);
         messageHelper.setCc(toCC);
         messageHelper.setFrom(fromUser);
         messageHelper.setText(text, true);

         try {
        	 mailSender.send(message);
         }
         catch(MailException ex) {
	         // 로깅 등...
	         System.err.println(ex.getMessage());
	     }
     }
     
     

}
