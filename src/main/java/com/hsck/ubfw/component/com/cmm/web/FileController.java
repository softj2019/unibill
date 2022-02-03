package com.hsck.ubfw.component.com.cmm.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hsck.ubfw.component.com.cmm.service.FileService;
import com.hsck.ubfw.component.com.cmm.util.FileMngUtil;
import com.hsck.ubfw.component.com.cmm.util.StringUtil;
import com.hsck.ubfw.component.com.cmm.vo.FileVO;
import com.hsck.ubfw.component.main.model.LoginVO;

import egovframework.com.cmm.service.Globals;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Controller
public class FileController {
		
	@Resource(name = "fileUploadProperties")
    Properties fileUploadProperties;
	
	/** FileService */
	@Resource(name = "fileService")
	private FileService fileService;
		
	/**
	 * 엑셀 파일 다운로드 처리
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@RequestMapping(value = "/file/downloadFile.do")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
	
		String subPath   = StringUtil.isNullToString(request.getParameter("file_cours"));   // 파일폴더
		String fileName  = StringUtil.isNullToString(request.getParameter("file_name"));    // 파일명
		String fileDelYn = StringUtil.isNullToString(request.getParameter("file_del_yn"));  // 파일삭제여부
		
		String uploadPath = "";
		try {
			if(subPath.contains("../")|| subPath.contains("/") || subPath.contains("\\")) {
				subPath = subPath.replace("../", "").replace("/", "").replace("//", "");
				throw new Exception("file error");
			}
			
			if(File.separator.equals("\\")){
				uploadPath = fileUploadProperties.getProperty("WINsystem.uploadpath")+"\\"+subPath;
			}else{
				uploadPath = fileUploadProperties.getProperty("system.uploadpath")+File.separator+subPath;
			}
			
			File uFile = new File(uploadPath, fileName);
			int fSize = (int) uFile.length();
			if (fSize > 0) {
				BufferedInputStream in = new BufferedInputStream(
						new FileInputStream(uFile));
				// String mimetype = servletContext.getMimeType(requestedFile);
				String mimetype = "text/html";
				response.setBufferSize(fSize);
				response.setContentType(mimetype);
				response.setHeader("Content-Disposition", "attachment; filename=\""
						+ URLEncoder.encode(fileName,"UTF-8") + "\"");
				response.setContentLength(fSize);

				FileCopyUtils.copy(in, response.getOutputStream());
				in.close();
				response.getOutputStream().flush();
				response.getOutputStream().close();
				
			} else {
				System.out.println("cccccc");
				//setContentType을 프로젝트 환경에 맞추어 변경
				response.setContentType("application/x-msdownload");
				PrintWriter printwriter = response.getWriter();
				printwriter.println("<html>");
				printwriter.println("<br><br><br><h2>파일이 손상되었습니다.</h2>");
				printwriter.println("<br><br><br><center><h3><a href='javascript: history.go(-1)'>Back</a></h3></center>");
				printwriter.println("<br><br><br>&copy; webAccess");
				printwriter.println("</html>");
				printwriter.flush();
				printwriter.close();
			}
			
			if ("Y".equals(fileDelYn)) {
				try {				
					File dFile = new File(uploadPath, fileName);
					dFile.delete();	// 물리적 삭제
					
				} catch(Exception e) {}
			}
			
		} catch(Exception e) {
			
			//setContentType을 프로젝트 환경에 맞추어 변경
			response.setContentType("application/x-msdownload");
			PrintWriter printwriter = response.getWriter();
			printwriter.println("<html>");
			printwriter.println("<br><br><br><h2>파일을 찾을 수 없습니다.</h2>");
			printwriter.println("<br><br><br><center><h3><a href='javascript: history.go(-1)'>Back</a></h3></center>");
			printwriter.println("<br><br><br>&copy; webAccess");
			printwriter.println("</html>");
			printwriter.flush();
			printwriter.close();
			
		}
	}
	
	/**
	 * 첨부 파일 다운로드 처리
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@RequestMapping(value = "/file/fileDownload.do")
	public void fileDownload(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		String subPath      = StringUtil.isNullToString(request.getParameter("download_path")); // 파일폴더
		String dirPath      = fileUploadProperties.getProperty("system.uploadpath");
		String fileFullPath = dirPath+File.separator+subPath;		
		String file_sn      = StringUtil.isNullToString(request.getParameter("file_sn"));       // 파일순번
		String ubseq        = StringUtil.isNullToString(request.getParameter("ubseq"));         // 자료순번
				
		FileVO vo = new FileVO();
		vo.setFileSn(StringUtil.strToInt(file_sn));
		vo.setUbseq(ubseq);

		FileVO fVO = fileService.selectFileInfo(vo);

		byte[] fileBytes = fVO.getRawdata();

		try {

			// 확장자 추출
			int index = fVO.getFileNm().lastIndexOf(".");
   	   		String fileExt = fVO.getFileNm().substring(index + 1);
   	   		
   	        // new 파일명
   	   		String newFile  = FileMngUtil.newFileName(); 
   	   		String fileName = newFile + "." + fileExt;
   	   		
			FileMngUtil.createDirectory(fileFullPath);
			FileMngUtil.makeBlobFileInWas(fileFullPath, fileName, fileBytes);
			
			File f = new File(fileFullPath + "/" + fileName);
			if (!f.exists()) {
			} else {
			}
			
			long filesize = f.length();
			
			byte b[] = new byte[5 * 1024 * 1024];
			String strClient = request.getHeader("User-Agent"); 
			
			String orgFileName = URLEncoder.encode(fVO.getFileNm().replace(" ", "_"),"UTF-8");
			
			if(strClient == null)strClient="";
			if (strClient.indexOf("MSIE 5.5") > -1) {
				response.setHeader("Content-Type", "doesn/matter;");
				response.setHeader("Content-Disposition", "filename=" + orgFileName + ";");
			} else {
				response.setHeader("Content-Type", "application/octet-stream");
				response.setHeader("Content-Disposition", "attachment;filename=" + orgFileName + ";");				
			}
			response.setHeader("Content-Length", "" + filesize);
			
			if (filesize > 0 && f.isFile()) {
				
				BufferedInputStream fin   = new BufferedInputStream(new FileInputStream(f));
				BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());
				int read = 0;
				try {
					while ((read = fin.read(b)) != -1) {
						outs.write(b, 0, read);
					}
					outs.flush();
				} catch (Exception e) {
				} finally {
					outs.close();
					fin.close();
				}
			}

			// 파일 삭제
			FileMngUtil.fileDelete(fileFullPath + "/" + fileName);
			
		}catch(Exception e){
		}
	}

	/**
	 * 첨부 파일 다운로드 처리
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/file/jiroFileDownload.do")
	public void jiroFileDownload(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		// 세션 사용자정보
		LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);

		String dirPath      = StringUtil.isNullToString(request.getParameter("file_dir")); // 파일폴더
		String scrin_code      = StringUtil.isNullToString(request.getParameter("scrin_id"));       // 화면ID
		String fileName      = StringUtil.isNullToString(request.getParameter("file_nm"));       // 파일명
		String cust_id      = StringUtil.isNullToString(request.getParameter("cust_id"));       // 고객ID
		String fileFullPath = dirPath+File.separator;		
		
		HashMap parameterObject =  new HashMap();
    	parameterObject.put("scrin_code",  scrin_code);             // 메뉴ID
    	parameterObject.put("role_code",   loginVO.getRoleId());    // 권한ID


		try {
			File uFile = new File(fileFullPath, fileName);
			int fSize = (int) uFile.length();
			if (fSize > 0) {

				BufferedInputStream in = new BufferedInputStream(
						new FileInputStream(uFile));
				// String mimetype = servletContext.getMimeType(requestedFile);
				String mimetype = "text/html";
				response.setBufferSize(fSize);
				response.setContentType(mimetype);
				response.setHeader("Content-Disposition", "attachment; filename=\""
						+ URLEncoder.encode(fileName,"UTF-8") + "\"");
				response.setContentLength(fSize);
				FileCopyUtils.copy(in, response.getOutputStream());
				in.close();
				response.getOutputStream().flush();
				response.getOutputStream().close();
			} else {
				
				//setContentType을 프로젝트 환경에 맞추어 변경
				response.setContentType("application/x-msdownload");
				PrintWriter printwriter = response.getWriter();
				printwriter.println("<html>");
				printwriter.println("<br><br><br><h2>파일이 손상되었습니다.</h2>");
				printwriter.println("<br><br><br><center><h3><a href='javascript: history.go(-1)'>Back</a></h3></center>");
				printwriter.println("<br><br><br>&copy; webAccess");
				printwriter.println("</html>");
				printwriter.flush();
				printwriter.close();
			}
			
		} catch(Exception e) {
			
			//setContentType을 프로젝트 환경에 맞추어 변경
			response.setContentType("application/x-msdownload");
			PrintWriter printwriter = response.getWriter();
			printwriter.println("<html>");
			printwriter.println("<br><br><br><h2>파일을 찾을 수 없습니다.</h2>");
			printwriter.println("<br><br><br><center><h3><a href='javascript: history.go(-1)'>Back</a></h3></center>");
			printwriter.println("<br><br><br>&copy; webAccess");
			printwriter.println("</html>");
			printwriter.flush();
			printwriter.close();
			
		}
	}
	
	/**
	 * 공지사항 파일 다운로드 처리
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@RequestMapping(value = "/file/noticefileDownload.do")
	public void noticefileDownload(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		String file_sn      = StringUtil.isNullToString(request.getParameter("file_sn"));       // 파일순번
		FileVO vo = new FileVO();
		vo.setFileSn(StringUtil.strToInt(file_sn));
		EgovMap noticefileInfo = fileService.selectNoticeFileInfo(vo);
		
		try {
			File f = new File(noticefileInfo.get("filePath") + File.separator + noticefileInfo.get("nm"));
			if (!f.exists()) {
			} else {
				long filesize = f.length();
				
				byte b[] = new byte[5 * 1024 * 1024];
				String strClient = request.getHeader("User-Agent"); 
				
				String orgFileName = URLEncoder.encode(noticefileInfo.get("nm").toString().replace(" ", "_"),"UTF-8");
				
				if(strClient == null)strClient="";
				if (strClient.indexOf("MSIE 5.5") > -1) {
					response.setHeader("Content-Type", "doesn/matter;");
					response.setHeader("Content-Disposition", "filename=" + orgFileName + ";");
				} else {
					response.setHeader("Content-Type", "application/octet-stream");
					response.setHeader("Content-Disposition", "attachment;filename=" + orgFileName + ";");				
				}
				response.setHeader("Content-Length", "" + filesize);
				
				if (filesize > 0 && f.isFile()) {
					
					BufferedInputStream fin   = new BufferedInputStream(new FileInputStream(f));
					BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());
					int read = 0;
					try {
						while ((read = fin.read(b)) != -1) {
							outs.write(b, 0, read);
						}
						outs.flush();
					} catch (Exception e) {
					} finally {
						outs.close();
						fin.close();
					}
				}
			}
			
			
		}catch(Exception e){
		}
	}
}