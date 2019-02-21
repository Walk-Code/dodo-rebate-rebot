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
package com.dodo.project.rebate.rebot.api.manager.service.impl;

import com.dodo.project.rebate.rebot.api.manager.SendMessageManager;
import com.dodo.project.rebate.rebot.api.manager.service.SendMessageService;
import com.dodo.project.rebate.rebot.commons.bean.MassMessageBean;
import com.dodo.project.rebate.rebot.commons.bean.ReceiveMessBean;
import com.dodo.project.rebate.rebot.commons.enums.RobotEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/*
 * @Description: 发送信息业务服务类实现类
 * @Author: walk_code walk_code@163.com
 * @Param:
 * @return:
 * @Date: 2019/2/20 17:43
 */
@Service
public class SendMessageServiceImpl implements SendMessageService {

	@Resource
	private SendMessageManager sendMessageManager;

	private static final Logger logger = LoggerFactory.getLogger(SendMessageServiceImpl.class);

	@Override
	public boolean sendMessage(MassMessageBean massMessageBean) {

		boolean isSuccess = false;

		if (StringUtils.isNotBlank(massMessageBean.getTextContent()) && StringUtils.isNotBlank(massMessageBean.getStudentName())) {
			String newContent = massMessageBean.getStudentName() + ",您好。" + massMessageBean.getTextContent();
			massMessageBean.setTextContent(newContent);
		}

		boolean sendTextSuccess = true;
		if (StringUtils.isNoneBlank(massMessageBean.getTextContent())) {
			ReceiveMessBean receiveMessBean = new ReceiveMessBean();
			receiveMessBean.setMessage(massMessageBean.getTextContent());
			receiveMessBean.setRobot(RobotEnum.Robot_0x1.getValue());
			receiveMessBean.setUserId(massMessageBean.getStudentId());
			receiveMessBean.setUserType(1);
			receiveMessBean.setCreateTime(massMessageBean.getCreateTime());
			sendTextSuccess = sendMessageManager.sendMessage(receiveMessBean);
		}

		isSuccess = sendTextSuccess;

		boolean sendImageSuccess = true;
		if (StringUtils.isNoneBlank(massMessageBean.getImgUrls())) {
			String[] images = massMessageBean.getImgUrls().split(",");
			for (String image : images) {
				ReceiveMessBean receiveMessBean = new ReceiveMessBean();
				receiveMessBean.setImgUrl(image);
				receiveMessBean.setRobot(RobotEnum.Robot_0x1.getValue());
				receiveMessBean.setUserId(massMessageBean.getStudentId());
				receiveMessBean.setUserType(1);
				sendImageSuccess = sendMessageManager.sendMessage(receiveMessBean);
				isSuccess = isSuccess && sendImageSuccess;
			}
		}

		return isSuccess;
	}
}
