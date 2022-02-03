package com.hsck.ubfw.component.com.cmm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

import egovframework.com.cmm.service.Globals;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * Created by LSW on 2017-05-15.
 */
@Component("pdfUtil")
public class PdfUtil {

    private static final Logger LOG = LoggerFactory.getLogger(PdfUtil.class);

    /**
     * html 형태의 문서를 만든후 pdf로 변환하여 메일에 첨부하기위한 용도로 사용됨.
     * pdfUtil.createPdfFileForBillingEmails(mailBillVO, subject, billdtlList, targetFilePathAndName , vmFilePath);
     * @param subject
     * @param mailBillVO
     * @param pdfFilePathAndName
     * @param vmFilePath
     * @throws Exception
     */



    public void createPdf(String pdfFilePath , String htmlFilePath , String cssFilePath) throws IOException, DocumentException {
        // step 1
        Document document = new Document(PageSize.A4 , 12.0F , 12.0F , 12.0F , 12.0F);

        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
        writer.setInitialLeading(12.5f);

        // step 3
        document.open();

        // step 4

        // CSS
        CSSResolver cssResolver = new StyleAttrCSSResolver();
        CssFile cssFile = XMLWorkerHelper.getCSS(new FileInputStream(cssFilePath));
        cssResolver.addCss(cssFile);

        // HTML
        HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());

        // Pipelines
        PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
        HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
        CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

        // XML Worker
        XMLWorker worker = new XMLWorker(css, true);
        XMLParser p = new XMLParser(worker);
        p.parse(new FileInputStream(htmlFilePath), true);

        // step 5
        document.close();
    }
    
    /**
     * html 형태의 문서를 만든후 pdf로 변환하여 메일에 첨부하기위한 용도로 사용됨.
     * pdfUtil.createPdfFileForBillingEmails(mailBillVO, subject, billdtlList, targetFilePathAndName , vmFilePath);
     * @param subject
     * @param mailBillVO
     * @param pdfFilePathAndName
     * @param vmFilePath
     * @throws Exception
     */
    public void MakePdf(String subject, String str, String pdfPath ,String vmFilePath ) throws Exception {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty("resource.loader", "class");
        ve.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        ve.init();

        Template veTemplate = ve.getTemplate(vmFilePath, "UTF-8");

            /*  add that list to a VelocityContext  */
        VelocityContext context = new VelocityContext();
//            String imgUrlCid = 	request.getScheme() + "://" +   // "http" + "://
//                    request.getServerName() +       // "myhost"
//                    ":" + request.getServerPort() + 	// ":" + "8080"
//                    request.getContextPath();
//            context.put("imgUrlCid", imgUrlCid);
        context.put("subject", subject );
//        context.put("mailBillVO", str);
        context.put("emailInternalFilePath", Globals.EMAIL_INTERNALFILE_PATH);
//        context.put("billdtlList", billList);

        StringWriter mailBodyString = new StringWriter();
        veTemplate.merge(context, mailBodyString);
        mailBodyString.flush();

        File htmlFile = new File(StringUtils.substringBeforeLast( pdfPath ,".") + ".html" );
        FileUtils.writeStringToFile(htmlFile, mailBodyString.toString() , "UTF-8");

//        createPdf( pdfFilePathAndName , FileUtils.readLines(htmlFile,"UTF-8").toString());
//        createPdf( pdfFilePathAndName , htmlFile.getAbsolutePath() , "D:\\home\\airmb\\tomcat\\mailAttachFiles\\billing\\hdsTest.css" );
        createPdf( pdfPath , htmlFile.getAbsolutePath() , Globals.EMAIL_INTERNALFILE_PATH + "\\css\\hds.css" );
    }
    
    public void createPdf(String pdfFilePath , String htmlFilePath ) throws Exception {

        PdfCustom pdf = new PdfCustom();
        pdf.addPageFromFile(htmlFilePath);
        pdf.saveAs(pdfFilePath);
    }


}
