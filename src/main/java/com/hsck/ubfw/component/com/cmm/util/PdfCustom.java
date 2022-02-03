package com.hsck.ubfw.component.com.cmm.util;

import com.github.jhonnymertz.wkhtmltopdf.wrapper.page.Page;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.page.PageType;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Params;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created by LSW on 2017-05-30.
 */
public class PdfCustom {

    private static final String STDINOUT = "-";
    private final WrapperConfigCustom wrapperConfig;
    private final Params params;
    private final List<Page> pages;
    private boolean hasToc;

    public PdfCustom() {
        this(new WrapperConfigCustom());
    }


    public PdfCustom(WrapperConfigCustom wrapperConfig) {
        this.hasToc = false;
        this.wrapperConfig = wrapperConfig;
        this.params = new Params();
        this.pages = new ArrayList();
    }


    public void addPageFromUrl(String source) {
        this.pages.add(new Page(source, PageType.url));
    }

    public void addPageFromString(String source) {
        this.pages.add(new Page(source, PageType.htmlAsString));
    }

    public void addPageFromFile(String source) {
        this.pages.add(new Page(source, PageType.file));
    }

    public void addToc() {
        this.hasToc = true;
    }

    public void addParam(Param param, Param... params) {
        this.params.add(param, params);
    }

    public File saveAs(String path) throws IOException, InterruptedException {
        File file = new File(path);
        FileUtils.writeByteArrayToFile(file, this.getPDF());
        return file;
    }

    public byte[] getPDF() throws IOException, InterruptedException {
        byte[] var4;
        try {
            Process process = Runtime.getRuntime().exec(this.getCommandAsArray());
            byte[] inputBytes = IOUtils.toByteArray(process.getInputStream());
            byte[] errorBytes = IOUtils.toByteArray(process.getErrorStream());
            process.waitFor();
            if(process.exitValue() != 0) {
                throw new RuntimeException("Process (" + this.getCommand() + ") exited with status code " + process.exitValue() + ":\n" + new String(errorBytes));
            }

            var4 = inputBytes;
        } finally {
            this.cleanTempFiles();
        }

        return var4;
    }

    private String[] getCommandAsArray() throws IOException {
        List<String> commandLine = new ArrayList();
        if(this.wrapperConfig.isXvfbEnabled()) {
            commandLine.addAll(this.wrapperConfig.getXvfbConfig().getCommandLine());
        }

        commandLine.add(this.wrapperConfig.getWkhtmltopdfCommand());
        commandLine.addAll(this.params.getParamsAsStringList());
        if(this.hasToc) {
            commandLine.add("toc");
        }

        Page page;
        for(Iterator var2 = this.pages.iterator(); var2.hasNext(); commandLine.add(page.getSource())) {
            page = (Page)var2.next();
            if(page.getType().equals(PageType.htmlAsString)) {
                File temp = File.createTempFile("java-wkhtmltopdf-wrapper" + UUID.randomUUID().toString(), ".html");
                FileUtils.writeStringToFile(temp, page.getSource(), "UTF-8");
                page.setSource(temp.getAbsolutePath());
            }
        }

        commandLine.add("-");
        return (String[])commandLine.toArray(new String[commandLine.size()]);
    }

    private void cleanTempFiles() {
        Iterator var1 = this.pages.iterator();

        while(var1.hasNext()) {
            Page page = (Page)var1.next();
            if(page.getType().equals(PageType.htmlAsString)) {
                (new File(page.getSource())).delete();
            }
        }

    }

    public String getCommand() throws IOException {
        return StringUtils.join(this.getCommandAsArray(), " ");
    }
}
