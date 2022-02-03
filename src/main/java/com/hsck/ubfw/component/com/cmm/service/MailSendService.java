/*******************************************************************************
 *
 * Revision History
 * Author   Date            Description
 * ------   ----------      ------------------------------------
 * reo    2017. 5. 8.         First Draft.
 *
 *******************************************************************************/ 
package com.hsck.ubfw.component.com.cmm.service;

import java.util.HashMap;
import java.util.List;

public interface MailSendService {

    /**
     * 하나의 메일을 하나의 사용자에게 발송하는 기능( 첨부파일이 포함 )
     * @param subject
     * @param text
     * @param fromUser
     * @param toUser
     * @param toCC
     * @param attachFileList null 가능.
     *                       <br/>첨부파일 정보를 List<HashMap<String,String>> 형태로 받는다.( 첨부파일 물리적 경로 key : attachFileAbsolutePath , 첨부파일을 내려 받을때의 파일의 논리명 key : attachFileName )
     *                       <br/>attachFileMap.get("attachFileAbsolutePath"); // "c:\\attach\\20170508000001.xlsx"
     *                       <br/>attachFileMap.get("attachFileName"); // "책목록.xlsx"
     * @throws Exception
     */
    void sendMail(final String subject, final String text, final String fromUser, final String toUser, final String[] toCC, List<HashMap<String, String>> attachFileList) throws Exception;



    /**
     * 동일한 메일을 받는 사람 여러명인 경우
     * @param subject
     * @param text
     * @param fromUser
     * @param toUserList 메일을 받는 사람
     * @param toCC
     * @throws Exception
     */
    void sendMailMulti(final String subject, final String text, final String fromUser, final String[] toUserList, final String[] toCC, List<HashMap<String, String>> attachFileList) throws Exception;
}
