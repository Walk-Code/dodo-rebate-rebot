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
package com.dodo.project.rebate.rebot.api.controller;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.dodo.project.base.web.utils.JsonHelper;
import com.dodo.project.rebate.rebot.api.base.AbstractRobotBaseController;
import com.dodo.project.rebate.rebot.api.manager.MessageDealManager;
import com.dodo.project.rebate.rebot.api.manager.service.ItchatService;
import com.dodo.project.rebate.rebot.api.utils.ThreadHelper;
import com.helen.robot.Wechat;
import com.helen.robot.api.WechatTools;
import com.helen.robot.core.MsgCenter;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helen.robot.api.MessageTools;

import java.util.List;

/*
 * <b>LoginController.java</b></br>
 *
 * <pre>
 * 登录控制器
 * </pre>
 *
 * @author xqyjjq walk_code@163.com
 * @date 2018年12月12日 下午12:05:02
 * @since JDK 1.8
 */
@RestController
public class IndexController extends AbstractRobotBaseController {
	@Resource
	private ItchatService itchatService;

	public static final Logger log = LoggerFactory.getLogger(IndexController.class);

	@RequestMapping(value = "/login")
	public void index() {
		// itchatService.startMsgCenterThread();
		itchatService.testLog4jInServer();
		renderJsp("/login/index");
	}

	@RequestMapping(value = "/getWxLoginQrImageUrl")
	public ResponseEntity<byte[]> getWxLoginQrImageUrl() {
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(itchatService.wxLogin());
	}

	@RequestMapping(value = "/logout")
	public void logout() {
		boolean isSuccess       = itchatService.wxLogout();
		String  responseContent = "<center><h2>微信退出失败,请联系管理员!</h2></center>";
		if (isSuccess) {
			responseContent = "<center><h2>微信退出成功!<a href='/login'>点此重新登录</a></h2></center>";
		}
		ThreadHelper.sleep(1000);

		renderHtml(responseContent);
	}

	@RequestMapping(value = "/checkLoginStatus")
	public void checkLoginStatus() {
		boolean isAlive = itchatService.isWxAlive();
		if (isAlive) {
			String responseContent = "登录成功,好友数:[" + itchatService.getContactList().size() + "]";
			responseContent += ",群组数:[" + itchatService.getGroupList().size() + "]";

			responseSuccess(responseContent);
		}

		responseFailure("未登录.");
	}

	@RequestMapping(value = "/searchUser")
	public void searchUser() {
		int    sentUserType = getParameterToInt("sentUserType");
		String searchText   = getParameter("searchText");
		if (sentUserType == 1) {// 查询好友类型
			responseSuccess(itchatService.getXXXIdByFuzzySearch(searchText, itchatService.getContactList()));
		} else {// 查询群组类型
			responseSuccess(itchatService.getXXXIdByFuzzySearch(searchText, itchatService.getGroupList()));
		}
	}

	@RequestMapping(value = "/refreshWxInfo")
	public void refreshWxInfo() {
		itchatService.refreshWxInfo();

		responseSuccess("更新通讯录成功.");
	}

	@RequestMapping(value = "/test/sfsfsdfsdfs")
	public void testSendUser() {
		String id = getParameter("sentUserId");
		MessageTools.sendMsgById("test" + RandomUtils.nextDouble(), id);

		responseSuccess("发送成功.");
	}

	@RequestMapping(value = "/test/webWxSendInvite")
	public void webWxSendInvite() {
		String username = getParameter("username");
		log.info("好友列表：{}", username);

		responseSuccess(WechatTools.getUserNameByNickName(username));
	}

	@RequestMapping(value = "/test/sendMessageToUser")
	public void sendMessageToUser() {
		String  uid         = getParameter("uid");
		String  content     = getParameter("content");
		boolean sendSuccess = itchatService.sendMsgById(content, uid);

		responseSuccess(sendSuccess);
	}

	@RequestMapping(value = "/test/sendInviteByUserId")
	public void sendInviteByUserId(String nickName) {
		List<JSONObject> list = itchatService.getContactList();
		String toUserName = "";
		toUserName = WechatTools.getUserNameByNickName(nickName);
		log.info("获取所有好友列表：{}", JsonHelper.toJson(list));
		if (StringUtils.isEmpty(toUserName)) {
			for(JSONObject jsonObject: list){
				if (jsonObject.toJSONString().contains(nickName)){
					toUserName =jsonObject.getString("UserName");
				}
			}
		}

		log.info(nickName + "的uid: {}", toUserName);
		boolean result = MessageTools.sendInviteByUserId(nickName);

		responseSuccess(result);
	}

}
