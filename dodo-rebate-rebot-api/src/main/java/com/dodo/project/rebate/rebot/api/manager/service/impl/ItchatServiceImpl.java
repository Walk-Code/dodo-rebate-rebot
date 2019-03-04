/**
 * Copyright (c) 2013-2020, cpthack 成佩涛 (1044559878@qq.com).
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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import com.dodo.project.base.exception.utils.AssertHelper;
import com.dodo.project.base.web.utils.JsonHelper;
import com.dodo.project.rebate.rebot.api.config.ItchatConfiguration;
import com.dodo.project.rebate.rebot.api.manager.MessageDealManager;
import com.dodo.project.rebate.rebot.api.manager.service.ItchatService;
import com.dodo.project.rebate.rebot.api.utils.FileDownloaderHelper;
import com.dodo.project.rebate.rebot.api.utils.ThreadHelper;
import com.helen.robot.core.MsgCenter;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.helen.robot.api.MessageTools;
import com.helen.robot.api.WechatTools;
import com.helen.robot.core.Core;
import com.helen.robot.utils.Config;
import com.helen.robot.utils.SleepUtils;
import com.helen.robot.utils.enums.RetCodeEnum;
import com.helen.robot.utils.enums.StorageLoginInfoEnum;
import com.helen.robot.utils.enums.URLEnum;
import com.helen.robot.utils.enums.parameters.BaseParaEnum;
import com.helen.robot.utils.tools.CommonTools;

import javax.annotation.Resource;

/*
 * @Description: 微信机器人相关逻辑处理 - 具体实现类
 * @Author: walk_code walk_code@163.com
 * @Param:
 * @return:
 * @Date: 2019/2/20 14:02
 */
@Service
public class ItchatServiceImpl implements ItchatService {

	public final static Logger logger = LoggerFactory.getLogger(ItchatServiceImpl.class);

	private static final int MAX_REQUEST_TIME = 5;

	@Resource
	private MessageDealManager messageDealManager;

	@Override
	public boolean isWxAlive() {
		return Core.getInstance().isAlive();
	}

	@Override
	public String getUserIdByFuzzySearch(String searchText) {
		logger.info("搜索用户，搜索条件：{}", searchText);
		JSONObject jsonObject = getXXXIdByFuzzySearch(searchText, getContactList());
		if (jsonObject != null) {
			logger.info("匹配对应关键词[{}]的好友，好友昵称：{}，好友UserName：{}", searchText, jsonObject.getString("NickName"), jsonObject.getString("UserName"));
			return jsonObject.getString("UserName");
		}
		logger.warn("匹配不到对应的关键词[{}]的好友.", searchText);
		return null;
	}

	@Override
	public String getGroupIdByFuzzySearch(String searchText) {
		JSONObject jsonObject = getXXXIdByFuzzySearch(searchText, getGroupList());
		if (jsonObject != null) {
			logger.info("匹配对应关键词[{}]的群，群昵称：{}，群UserName：{}", searchText, jsonObject.getString("NickName"), jsonObject.getString("UserName"));
			return jsonObject.getString("UserName");
		}
		logger.warn("匹配不到对应的关键词[{}]的群.", searchText);
		return null;
	}

	@Override
	public JSONObject getXXXIdByFuzzySearch(String searchText, List<JSONObject> list) {
		for (JSONObject jsonObject : list) {
			if (jsonObject.getString("NickName").contains(searchText)) {
				return jsonObject;
			}
			if (jsonObject.getString("RemarkName").contains(searchText)) {
				return jsonObject;
			}
		}
		return null;
	}

	@Override
	public byte[] getLoginQcImageBytes() {
		byte[] data            = null;
		String qcImageFilePath = ItchatConfiguration.qcCodePath + File.separator + "QR.jpg";
		try (InputStream file = new FileInputStream(qcImageFilePath)) {
			int size = file.available();
			data = new byte[size];
			file.read(data);
		} catch (Exception e) {
			logger.error("获取微信登录二维码图片失败:", e);
		}
		return data;
	}

	@Override
	public boolean wxLogout() {
		try {
			WechatTools.logout();
			Core.resetInstance();
			// TODO 可以抽离成单独的定时任务周期性执行
			// 启动等待二维码扫描的接口
			Thread scanWxLoginThread = new Thread(new Runnable() {
				@Override
				public void run() {
					com.helen.robot.controller.LoginController login = new com.helen.robot.controller.LoginController();
					login.login(ItchatConfiguration.qcCodePath);
				}
			});
			scanWxLoginThread.setName("scanWxLoginThread-" + RandomUtils.nextDouble());
			scanWxLoginThread.start();
			return true;
		} catch (Exception e) {
			logger.error("微信退出登录失败:", e);
		}
		return false;
	}

	@Override
	public byte[] wxLogin() {
		// AssertHelper.isTrue(isWxAlive() == false, "目前微信已经在线，先退出再进行操作.");
		return getLoginQcImageBytes();
	}

	@Override
	public boolean sendMsgById(String text, String id) {
		try {
			MessageTools.sendMsgById(text, id);
			return true;
		} catch (Exception e) {
			logger.error("发送普通文本消息发生错误:", e);
		}
		return false;
	}

	@Override
	public boolean sendXXXPicMsgById(String id, String filePath) {
		if (filePath.startsWith("http") || filePath.startsWith("https")) {
			return sendRomotePicMsgById(id, filePath);
		}
		return sendLocalPicMsgById(id, filePath);
	}

	@Override
	public boolean sendRomotePicMsgById(String id, String fileUrl) {
		// 下载网络图片至临时目录
		try {
			String ext = FilenameUtils.getExtension(fileUrl);

			String tempName = DigestUtils.md5Hex(fileUrl) + "." + ext;

			String tempDir            = System.getProperty("java.io.tmpdir");
			String outputTempFilePath = tempDir + "/" + tempName;
			logger.info("当前图片的fileUrl:{}，下载后图片地址:{}", fileUrl, outputTempFilePath);
			File file = new File(outputTempFilePath);
			if (!file.exists()) {// 本地文件不存在时触发文件下载逻辑
				FileDownloaderHelper.downloadFromUrl(fileUrl, outputTempFilePath);
			}

			if (file.exists()) {
				return sendLocalPicMsgById(id, outputTempFilePath);
			} else {
				logger.warn("生成临时文件失败-无法发送，fileUrl路径:{},下载后图片地址:{}", fileUrl, outputTempFilePath);
			}
		} catch (Exception e) {
			logger.error("发送图片失败：{}", e);
		}
		return false;
	}

	@Override
	public boolean sendLocalPicMsgById(String id, String filePath) {
		try {
			ThreadHelper.sleep(RandomUtils.nextLong(300, 1500));
			return MessageTools.sendPicMsgByUserId(id, filePath);
		} catch (Exception e) {
			logger.error("发送图片类型消息失败:", e);
		}
		return false;
	}

	@Override
	public List<JSONObject> getGroupList() {
		return Core.getInstance().getGroupList();
	}

	@Override
	public List<JSONObject> getContactList() {
		return Core.getInstance().getContactList();
	}

	@Override
	public boolean refreshWxInfo() {
		webWxInit();
		webWxGetContact();
		WebWxBatchGetContact();
		Core core = Core.getInstance();
		core.setGroupIdList(removeDuplicateDataOfString(core.getGroupIdList()));
		core.setGroupNickNameList(removeDuplicateDataOfString(core.getGroupNickNameList()));
		core.setContactList(removeDuplicateDataOfJSONObject(core.getContactList()));
		core.setGroupList(removeDuplicateDataOfJSONObject(core.getGroupList()));
		core.setMemberList(removeDuplicateDataOfJSONObject(core.getMemberList()));
		core.setPublicUsersList(removeDuplicateDataOfJSONObject(core.getPublicUsersList()));
		core.setSpecialUsersList(removeDuplicateDataOfJSONObject(core.getSpecialUsersList()));
		core.setMemberCount(core.getMemberList().size());
		core.getMsgList().clear();

		return true;
	}

	/*
	 * @Description: 移除重复数据
	 * @Author: walk_code walk_code@163.com
	 * @Param: [list]
	 * @return: java.util.List<com.alibaba.fastjson.JSONObject>
	 * @Date: 2019/2/26 18:00
	 */
	private List<JSONObject> removeDuplicateDataOfJSONObject(List<JSONObject> list) {
		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList<>();
		}
		Map<String, JSONObject> map = new HashMap<>();
		for (JSONObject jSONObject : list) {
			map.put(jSONObject.getString("NickName") + jSONObject.getString("RemarkName"), jSONObject);
		}

		return new ArrayList<>(map.values());
	}

	/*
	 * @Description: 移除重复数据
	 * @Author: walk_code walk_code@163.com
	 * @Param: [list]
	 * @return: java.util.List<java.lang.String>
	 * @Date: 2019/2/26 18:00
	 */
	private List<String> removeDuplicateDataOfString(List<String> list) {
		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList<>();
		}
		Set<String> set = new HashSet<>(list);

		return new ArrayList<>(set);
	}

	@Override
	public boolean checkIsLogout() {
		boolean             isLogout  = false;
		Map<String, String> resultMap = syncCheck();
		String              retcode   = resultMap.get("retcode");
		// String selector = resultMap.get("selector");
		if (retcode.equals(RetCodeEnum.UNKOWN.getCode())) {
			logger.info(RetCodeEnum.UNKOWN.getType());
		} else if (retcode.equals(RetCodeEnum.LOGIN_OUT.getCode())) { // 退出
			logger.info(RetCodeEnum.LOGIN_OUT.getType());
			isLogout = true;
		} else if (retcode.equals(RetCodeEnum.LOGIN_OTHERWHERE.getCode())) { // 其它地方登陆
			logger.info(RetCodeEnum.LOGIN_OTHERWHERE.getType());
			isLogout = true;
		} else if (retcode.equals(RetCodeEnum.MOBILE_LOGIN_OUT.getCode())) { // 移动端退出
			logger.info(RetCodeEnum.MOBILE_LOGIN_OUT.getType());
			isLogout = true;
		}

		return isLogout;
	}

	@Override
	public void startMsgCenterThread() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				MsgCenter.handleMsg(messageDealManager);
			}
		}).start();
	}

	@Override
	public String getUserNameByNickName(String nickName) {
		// TODO
		return null;
	}

	@Override
	public void testLog4jInServer() {
		logger.info("测试日志输出: {}", "test");
		logger.warn("测试日志输出: {}", "test");
		logger.debug("测试日志输出: {}", "test");
		logger.error("测试日志输出: {}", "test");
	}

	/*
	 *
	 * <b>syncCheck</b> <br/>
	 * <br/>
	 *
	 * 获取消息 状态码 <br/>
	 *
	 * @author xuxuan 1009466324@qq.com
	 * @return Map<String , String>
	 *
	 */
	private Map<String, String> syncCheck() {
		Core core = Core.getInstance();

		Map<String, String> resultMap = new HashMap<String, String>();
		// 组装请求URL和参数
		String                   url    = core.getLoginInfo().get(StorageLoginInfoEnum.syncUrl.getKey()) + URLEnum.SYNC_CHECK_URL.getUrl();
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		for (BaseParaEnum baseRequest : BaseParaEnum.values()) {
			params.add(new BasicNameValuePair(baseRequest.para().toLowerCase(), core.getLoginInfo().get(baseRequest.value()).toString()));
		}
		params.add(new BasicNameValuePair("r", String.valueOf(new Date().getTime())));
		params.add(new BasicNameValuePair("synckey", (String) core.getLoginInfo().get("synckey")));
		params.add(new BasicNameValuePair("_", String.valueOf(new Date().getTime())));
		SleepUtils.sleep(7);
		try {
			HttpEntity entity = core.getMyHttpClient().doGet(url, params, true, null);
			if (entity == null) {
				resultMap.put("retcode", "9999");
				resultMap.put("selector", "9999");
				return resultMap;
			}
			String  text    = EntityUtils.toString(entity);
			String  regEx   = "window.synccheck=\\{retcode:\"(\\d+)\",selector:\"(\\d+)\"\\}";
			Matcher matcher = CommonTools.getMatcher(regEx, text);
			if (!matcher.find() || matcher.group(1).equals("2")) {
				logger.info(String.format("Unexpected sync check result: %s", text));
			} else {
				resultMap.put("retcode", matcher.group(1));
				resultMap.put("selector", matcher.group(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultMap;
	}

	/*
	 * @Description: 微信初始化
	 * @Author: walk_code walk_code@163.com
	 * @Param: []
	 * @return: boolean
	 * @Date: 2019/2/26 17:58
	 */
	private boolean webWxInit() {
		Core core = Core.getInstance();
		core.setAlive(true);
		core.setLastNormalRetcodeTime(System.currentTimeMillis());
		// 组装请求URL和参数
		String url = String.format(URLEnum.INIT_URL.getUrl(), core.getLoginInfo().get(StorageLoginInfoEnum.url.getKey()), String.valueOf(System.currentTimeMillis() / 3158L), core.getLoginInfo().get(StorageLoginInfoEnum.pass_ticket.getKey()));

		Map<String, Object> paramMap = core.getParamMap();

		// 请求初始化接口
		HttpEntity entity = core.getMyHttpClient().doPost(url, JSON.toJSONString(paramMap));
		try {
			String     result = EntityUtils.toString(entity, Consts.UTF_8);
			JSONObject obj    = JSON.parseObject(result);

			JSONObject user    = obj.getJSONObject(StorageLoginInfoEnum.User.getKey());
			JSONObject syncKey = obj.getJSONObject(StorageLoginInfoEnum.SyncKey.getKey());

			core.getLoginInfo().put(StorageLoginInfoEnum.InviteStartCount.getKey(), obj.getInteger(StorageLoginInfoEnum.InviteStartCount.getKey()));
			core.getLoginInfo().put(StorageLoginInfoEnum.SyncKey.getKey(), syncKey);

			JSONArray     syncArray = syncKey.getJSONArray("List");
			StringBuilder sb        = new StringBuilder();
			for (int i = 0; i < syncArray.size(); i++) {
				sb.append(syncArray.getJSONObject(i).getString("Key") + "_" + syncArray.getJSONObject(i).getString("Val") + "|");
			}
			// 1_661706053|2_661706420|3_661706415|1000_1494151022|
			String synckey = sb.toString();

			// 1_661706053|2_661706420|3_661706415|1000_1494151022
			core.getLoginInfo().put(StorageLoginInfoEnum.synckey.getKey(), synckey.substring(0, synckey.length() - 1));// 1_656161336|2_656161626|3_656161313|11_656159955|13_656120033|201_1492273724|1000_1492265953|1001_1492250432|1004_1491805192
			core.setUserName(user.getString("UserName"));
			core.setNickName(user.getString("NickName"));
			core.setUserSelf(obj.getJSONObject("User"));

			String   chatSet      = obj.getString("ChatSet");
			String[] chatSetArray = chatSet.split(",");
			for (int i = 0; i < chatSetArray.length; i++) {
				if (chatSetArray[i].indexOf("@@") != -1) {
					// 更新GroupIdList
					core.getGroupIdList().add(chatSetArray[i]); //
				}
			}
			JSONArray contactListArray = obj.getJSONArray("ContactList");
			for (int i = 0; i < contactListArray.size(); i++) {
				JSONObject o = contactListArray.getJSONObject(i);
				if (o.getString("UserName").indexOf("@@") != -1) {
					core.getGroupIdList().add(o.getString("UserName")); //
					// 更新GroupIdList
					core.getGroupList().add(o); // 更新GroupList
					core.getGroupNickNameList().add(o.getString("NickName"));
				}
			}
		} catch (Exception e) {
			logger.error("微信初始化错误:", e);
			return false;
		}

		return true;
	}

	/*
	 * @Description: 获取联系人
	 * @Author: walk_code walk_code@163.com
	 * @Param: []
	 * @return: void
	 * @Date: 2019/2/26 17:58
	 */
	private void webWxGetContact() {
		Core                core     = Core.getInstance();
		String              url      = String.format(URLEnum.WEB_WX_GET_CONTACT.getUrl(), core.getLoginInfo().get(StorageLoginInfoEnum.url.getKey()));
		Map<String, Object> paramMap = core.getParamMap();
		HttpEntity          entity   = reRequest(url, paramMap);

		try {
			String     result              = EntityUtils.toString(entity, Consts.UTF_8);
			JSONObject fullFriendsJsonList = JSON.parseObject(result);
			// 查看seq是否为0，0表示好友列表已全部获取完毕，若大于0，则表示好友列表未获取完毕，当前的字节数（断点续传）
			long                     seq         = 0;
			long                     currentTime = 0L;
			List<BasicNameValuePair> params      = new ArrayList<BasicNameValuePair>();
			if (fullFriendsJsonList.get("Seq") != null) {
				seq = fullFriendsJsonList.getLong("Seq");
				currentTime = new Date().getTime();
			}
			core.setMemberCount(fullFriendsJsonList.getInteger(StorageLoginInfoEnum.MemberCount.getKey()));
			JSONArray member = fullFriendsJsonList.getJSONArray(StorageLoginInfoEnum.MemberList.getKey());
			// 循环获取seq直到为0，即获取全部好友列表 ==0：好友获取完毕 >0：好友未获取完毕，此时seq为已获取的字节数
			while (seq > 0) {
				// 设置seq传参
				params.add(new BasicNameValuePair("r", String.valueOf(currentTime)));
				params.add(new BasicNameValuePair("seq", String.valueOf(seq)));
				entity = core.getMyHttpClient().doGet(url, params, false, null);

				params.remove(new BasicNameValuePair("r", String.valueOf(currentTime)));
				params.remove(new BasicNameValuePair("seq", String.valueOf(seq)));

				result = EntityUtils.toString(entity, Consts.UTF_8);
				fullFriendsJsonList = JSON.parseObject(result);

				if (fullFriendsJsonList.get("Seq") != null) {
					seq = fullFriendsJsonList.getLong("Seq");
					currentTime = new Date().getTime();
				}

				// 累加好友列表
				member.addAll(fullFriendsJsonList.getJSONArray(StorageLoginInfoEnum.MemberList.getKey()));
			}
			core.setMemberCount(member.size());
			for (Iterator<?> iterator = member.iterator(); iterator.hasNext(); ) {
				JSONObject o = (JSONObject) iterator.next();
				if ((o.getInteger("VerifyFlag") & 8) != 0) { // 公众号/服务号
					core.getPublicUsersList().add(o);
				} else if (Config.API_SPECIAL_USER.contains(o.getString("UserName"))) { // 特殊账号
					core.getSpecialUsersList().add(o);
				} else if (o.getString("UserName").indexOf("@@") != -1) { // 群聊
					core.getGroupNickNameList().add(o.getString("NickName"));
					core.getGroupIdList().add(o.getString("UserName"));
					core.getGroupList().add(o);
				} else if (o.getString("UserName").equals(core.getUserSelf().getString("UserName"))) { // 自己
					core.getContactList().remove(o);
				} else { // 普通联系人
					core.getContactList().add(o);
				}
			}

			return;
		} catch (Exception e) {
			logger.error("获取联系人发送错误:", e);
		}

		return;
	}

	/*
	 * @Description: 获取微信联系人
	 * @Author: walk_code walk_code@163.com
	 * @Param: []
	 * @return: void
	 * @Date: 2019/2/26 17:59
	 */
	private void WebWxBatchGetContact() {
		Core core = Core.getInstance();

		String              url      = String.format(URLEnum.WEB_WX_BATCH_GET_CONTACT.getUrl(), core.getLoginInfo().get(StorageLoginInfoEnum.url.getKey()), new Date().getTime(), core.getLoginInfo().get(StorageLoginInfoEnum.pass_ticket.getKey()));
		Map<String, Object> paramMap = core.getParamMap();
		paramMap.put("Count", core.getGroupIdList().size());
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (int i = 0; i < core.getGroupIdList().size(); i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("UserName", core.getGroupIdList().get(i));
			map.put("EncryChatRoomId", "");
			list.add(map);
		}
		paramMap.put("List", list);
		HttpEntity entity = core.getMyHttpClient().doPost(url, JSON.toJSONString(paramMap));
		try {
			String     text        = EntityUtils.toString(entity, Consts.UTF_8);
			JSONObject obj         = JSON.parseObject(text);
			JSONArray  contactList = obj.getJSONArray("ContactList");
			for (int i = 0; i < contactList.size(); i++) { // 群好友
				if (contactList.getJSONObject(i).getString("UserName").indexOf("@@") > -1) { // 群
					core.getGroupNickNameList().add(contactList.getJSONObject(i).getString("NickName")); // 更新群昵称列表
					core.getGroupList().add(contactList.getJSONObject(i)); // 更新群信息（所有）列表
					core.getGroupMemeberMap().put(contactList.getJSONObject(i).getString("UserName"), contactList.getJSONObject(i).getJSONArray("MemberList")); // 更新群成员Map
				}
			}
		} catch (Exception e) {
			logger.error("批量获取联系人失败:", e);
		}
	}

	/*
	 * @Description: 微信api重连
	 * @Author: walk_code walk_code@163.com
	 * @Param: [url, map]
	 * @return: org.apache.http.HttpEntity
	 * @Date: 2019/2/26 17:59
	 */
	private HttpEntity reRequest(String url, Map<String, Object> map) {
		HttpEntity entity = null;
		for (int i = 0; i < MAX_REQUEST_TIME; i++) {
			try {
				Core                core     = Core.getInstance();
				Map<String, Object> paramMap = core.getParamMap();
				entity = core.getMyHttpClient().doPost(url, JSON.toJSONString(paramMap));

				if (null != entity) {
					return entity;
				}
			} catch (Exception e) {
				logger.info("请求参数：{}", JsonHelper.toJson(map) + " 请求url-" + url);
				logger.error("请求微信获取联系人接口失败：{}", e);
			}
		}

		return entity;
	}
}
