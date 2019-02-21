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
package com.dodo.project.rebate.rebot.api.manager;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.dodo.project.rebate.rebot.api.manager.service.ItchatService;
import com.dodo.project.rebate.rebot.commons.bean.ReceiveMessBean;
import com.dodo.project.rebate.rebot.commons.enums.RobotEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/* 
* @Description: 发送消息管理器 
* @Author: walk_code walk_code@163.com
* @Param:  
* @return:   
* @Date: 2019/2/20 17:45
*/ 
@Service
public class SendMessageManager {
	
	public final static Logger logger = LoggerFactory.getLogger(SendMessageManager.class);
	
	@Resource
	private ItchatService itchatService;
	
	public boolean sendMessage(ReceiveMessBean receiveMessBean) {
		try {
			return sendMessageToGroup(receiveMessBean);
		}
		catch (Exception e) {
			logger.error("发送失败：{}", e);
		}
		return false;
	}
	
	/* 
	* @Description: 获取所有robot名称 
	* @Author: walk_code walk_code@163.com
	* @Param: [] 
	* @return: java.util.Map<java.lang.String,com.dodo.project.rebate.rebot.commons.enums.RobotEnum>  
	* @Date: 2019/2/20 17:46
	*/ 
	public Map<String, RobotEnum> getRobotList() {
		Map<String, RobotEnum> robotMap = new HashMap<>();
		for (RobotEnum robotEnum : RobotEnum.values()) {
			robotMap.put(robotEnum.getValue(), robotEnum);
		}
		
		return robotMap;
	}
	
	/* 
	* @Description: 发送信息到群
	* @Author: walk_code walk_code@163.com
	* @Param: [receiveMessBean] 
	* @return: boolean  
	* @Date: 2019/2/20 17:46
	*/ 
	private boolean sendMessageToGroup(ReceiveMessBean receiveMessBean) {
		/*String uniqueCacheKey = createUniqueCacheKey(receiveMessBean);
		String uniqueCacheValue = customizedCache.getObjToStr(uniqueCacheKey);
		if (StringUtils.isNotBlank(uniqueCacheValue)) {
			logger.warn("跳过已发送的消息,userId={},message={},imgUrl={}", receiveMessBean.getUserId(),receiveMessBean.getMessage(), receiveMessBean.getImgUrl());
			return true;
		}*/
		
		String groupId = itchatService.getGroupIdByFuzzySearch(receiveMessBean.getUserId());
		
		if (StringUtils.isBlank(groupId)) {
			logger.warn("未找到符合的群：{}", receiveMessBean.getUserId());
			return false;
		}
		
		logger.info("获取发送的groupId: {}", groupId);
		
		boolean isMsgSuccess = false;
		if (StringUtils.isNotBlank(receiveMessBean.getMessage())) {
			// 发送文本消息
			isMsgSuccess = itchatService.sendMsgById(receiveMessBean.getMessage(), groupId);
		}
		boolean isPicSuccess = false;
		if (StringUtils.isNoneBlank(receiveMessBean.getImgUrl())) {
			// 发送图片信息
			isPicSuccess = itchatService.sendXXXPicMsgById(groupId, receiveMessBean.getImgUrl());
		}
		
		boolean isSuccess = isMsgSuccess || isPicSuccess;
		if (isSuccess) {
			// 将发送成功的消息缓存1天，避免重复发送.
			// customizedCache.set(uniqueCacheKey, receiveMessBean.getUserId(), 3600 * 24);
		}
		
		return isSuccess;
	}
	
	/*private String createUniqueCacheKey(ReceiveMessBean receiveMessBean) {
		String uniqueCacheKey = JsonHelper.toJson(receiveMessBean);
		uniqueCacheKey = Md5Helper.encrypt(uniqueCacheKey);
		return uniqueCacheKey;
	}*/
	
}
