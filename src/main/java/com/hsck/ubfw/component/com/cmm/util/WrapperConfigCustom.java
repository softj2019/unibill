package com.hsck.ubfw.component.com.cmm.util;

import com.github.jhonnymertz.wkhtmltopdf.wrapper.configurations.XvfbConfig;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by LSW on 2017-05-30.
 */
public class WrapperConfigCustom {
    private XvfbConfig xvfbConfig;
    private String wkhtmltopdfCommand = "wkhtmltopdf";

    public WrapperConfigCustom() {
        this.setWkhtmltopdfCommand(this.findExecutable());
    }

    public WrapperConfigCustom(String wkhtmltopdfCommand) {
        this.setWkhtmltopdfCommand(wkhtmltopdfCommand);
    }

    public String getWkhtmltopdfCommand() {
        return this.wkhtmltopdfCommand;
    }

    public void setWkhtmltopdfCommand(String wkhtmltopdfCommand) {
        this.wkhtmltopdfCommand = wkhtmltopdfCommand;
    }

    public String findExecutable() {
        try {
            String osname = System.getProperty("os.name").toLowerCase();
            String cmd = osname.contains("windows")?"where /R \"C:\\\\Program Files\" wkhtmltopdf":"which pdftrans";
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();

            String text = osname.contains("windows")?IOUtils.toString(p.getInputStream(), Charset.defaultCharset()).trim():"/tmp/unibill/wkhtmltox/bin/wkhtmltopdf";
            if(text.isEmpty()) {
                throw new RuntimeException();
            }
            this.setWkhtmltopdfCommand(text);
        } catch (InterruptedException var5) {
            var5.printStackTrace();
        } catch (IOException var6) {
            var6.printStackTrace();
        } catch (RuntimeException var7) {
            var7.printStackTrace();
        }

        return this.getWkhtmltopdfCommand();
    }

    public boolean isXvfbEnabled() {
        return this.xvfbConfig != null;
    }

    public XvfbConfig getXvfbConfig() {
        return this.xvfbConfig;
    }

    public void setXvfbConfig(XvfbConfig xvfbConfig) {
        this.xvfbConfig = xvfbConfig;
    }
}
