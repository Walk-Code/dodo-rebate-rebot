/**
 * Copyright (c) 2013-2020,xqyjjq  walk_code@163.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dodo.project.rebate.rebot.api.schedule;

import javax.annotation.Resource;

import com.dodo.project.base.web.utils.DateHelper;
import com.dodo.project.rebate.rebot.api.manager.CheckLoginStatusManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*
 * <b>CheckLoginStatusSchedule.java</b></br>
 *
 * <pre>
 * 校验微信机器人是否在线
 * </pre>
 *
 * @author xqyjjq walk_code@163.com
 * @date 2018年12月12日 上午11:13:02
 * @since JDK 1.8
 */
@EnableScheduling
@Component
public class CheckLoginStatusManagerSchedule {
	private final static Logger log = LoggerFactory.getLogger(CheckLoginStatusManagerSchedule.class);

	@Resource
	CheckLoginStatusManager checkLoginStatusManager;

	@Scheduled(cron = "${timer.checkLoginStatusManagerSchedule.check}")
	public void check() {
		log.debug("开始检测微信机器人登录状态-{}.", DateHelper.getCurrentDateStr());
		try {
			checkLoginStatusManager.checkLoginStatus();
		} catch (Exception e) {
			log.error("校验微信机器人是否在线发生错误:", e);
		}
		log.debug("完成检测微信机器人登录状态-{}.", DateHelper.getCurrentDateStr());
	}
}
