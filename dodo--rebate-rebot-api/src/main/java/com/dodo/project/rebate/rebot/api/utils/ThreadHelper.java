/**
 * Copyright (c) 2013-2020, cpthack 成佩涛 (1044559878@qq.com).
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <b>ThreadHelper.java</b></br>
 * 
 * <pre>
 * 线程辅助工具类
 * </pre>
 *
 * @author cpthack 1044559878@qq.com
 * @date Dec 22, 2018 4:09:42 PM
 * @since JDK 1.8
 */
public class ThreadHelper {
	
	private final static Logger logger = LoggerFactory.getLogger(ThreadHelper.class);
	
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		}
		catch (Exception e) {
			logger.error("线程登录发生错误,当前线程:{}", Thread.currentThread().getName());
		}
	}
}
