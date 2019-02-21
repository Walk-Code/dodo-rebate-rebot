/**
 * Copyright (c) 2013-2020,xqyjjq  walk_code@163.com.
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
package com.dodo.project.rebate.rebot.api.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * <b>FileDownloaderHelper.java</b></br>
 * 
 * <pre>
 * 文件下载辅助类
 * </pre>
 *
 * @author xqyjjq walk_code@163.com
 * @date 2018年12月8日 上午11:13:35
 * @since JDK 1.8
 */
public class FileDownloaderHelper {
	
	public static String downloadFromUrl(String imagePath, String outputTempFilePath) throws IOException {
		InputStream is = null;
		FileOutputStream fos = null;
		URL url = new URL(imagePath);
		
		try {
			URLConnection urlConn = url.openConnection();
			
			// 获取输入流
			is = urlConn.getInputStream();
			fos = new FileOutputStream(outputTempFilePath);
			
			// 设置文件大小为4KB
			byte[] buffer = new byte[4096];
			int length;
			
			// 写入到临时文件目录
			while ((length = is.read(buffer)) > 0) {
				fos.write(buffer, 0, length);
			}
			return outputTempFilePath;
		}
		finally {
			try {
				if (is != null) {
					is.close();
				}
			}
			finally {
				if (fos != null) {
					fos.close();
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		// String result = downloadFromUrl(new
		// URL("https://i.loli.net/2018/12/08/5c0b37b32b89f.png"), "logo11w.png");
		
	}
}
