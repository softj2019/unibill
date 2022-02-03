/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hsck.ubfw.component.com.encryptMalbatis;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hsck.ubfw.component.com.cmm.util.CryptoUtils;

public class CustomBasicDataSource extends BasicDataSource{
	private static final Log logger = LogFactory.getLog(CustomBasicDataSource.class);
	
	@Override
	public void setPassword(String password) {
		InputStream in = this.getClass().getResourceAsStream("/egovframework/egovProps/globals.properties");
		Properties props = new Properties();
		String tmp = password;
		try {
			props.load(in);
			String encYn = props.getProperty("Globals.DBPassEncrypteYn");
			if(encYn.equals("Y")) {
				CryptoUtils enc = new CryptoUtils();
				//System.out.println(enc.encryptAES256("test"));
				tmp = enc.decryptAES256(password);
			}
		} catch (Exception e) {
			 
		}
		super.setPassword(tmp);
	}
	
}
