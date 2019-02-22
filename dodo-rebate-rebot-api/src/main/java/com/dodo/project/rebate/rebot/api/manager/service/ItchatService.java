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
package com.dodo.project.rebate.rebot.api.manager.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

/* 
* @Description: 微信机器人相关逻辑处理 - 接口类 
* @Author: walk_code walk_code@163.com
* @Param:  
* @return:   
* @Date: 2019/2/20 14:32
*/ 
public interface ItchatService {
	
	/* 
	* @Description: 当前微信是否在线 
	* @Author: walk_code walk_code@163.com
	* @Param: [] 
	* @return: boolean  
	* @Date: 2019/2/20 14:32
	*/ 
	boolean isWxAlive();
	
	/* 
	* @Description: 根据关键词模糊查询好友ID 
	* @Author: walk_code walk_code@163.com
	* @Param: [searchText] 
	* @return: java.lang.String  
	* @Date: 2019/2/20 14:32
	*/ 
	String getUserIdByFuzzySearch(String searchText);
	
	/* 
	* @Description: 根据关键词模糊查询群组ID 
	* @Author: walk_code walk_code@163.com
	* @Param: [searchText] 
	* @return: java.lang.String  
	* @Date: 2019/2/20 14:33
	*/ 
	String getGroupIdByFuzzySearch(String searchText);
	
	/* 
	* @Description: 根据关键词从数组中模糊查询符合的对象 
	* @Author: walk_code walk_code@163.com
	* @Param: [searchText, list] 
	* @return: com.alibaba.fastjson.JSONObject  
	* @Date: 2019/2/20 14:33
	*/ 
	JSONObject getXXXIdByFuzzySearch(String searchText, List<JSONObject> list);
	
	/* 
	* @Description: 获取微信登录二维码字节流 
	* @Author: walk_code walk_code@163.com
	* @Param: [] 
	* @return: byte[]  
	* @Date: 2019/2/20 14:33
	*/ 
	byte[] getLoginQcImageBytes();
	
	/* 
	* @Description: 微信注销登录 
	* @Author: walk_code walk_code@163.com
	* @Param: [] 
	* @return: boolean  
	* @Date: 2019/2/20 14:33
	*/ 
	boolean wxLogout();
	
	/* 
	* @Description: 微信登录 
	* @Author: walk_code walk_code@163.com
	* @Param: [] 
	* @return: byte[]  
	* @Date: 2019/2/20 14:33
	*/ 
	byte[] wxLogin();
	
	/* 
	* @Description: 根据ID发送文本类型消息 
	* @Author: walk_code walk_code@163.com
	* @Param: [text, id] 
	* @return: boolean  
	* @Date: 2019/2/20 14:33
	*/ 
	boolean sendMsgById(String text, String id);
	
	/* 
	* @Description: 根据ID发送XXX图片类型信息 
	* @Author: walk_code walk_code@163.com
	* @Param: [id, filePath] 
	* @return: boolean  
	* @Date: 2019/2/20 14:34
	*/ 
	boolean sendXXXPicMsgById(String id, String filePath);
	
	/* 
	* @Description: 根据ID发送远程图片类型消息 
	* @Author: walk_code walk_code@163.com
	* @Param: [id, fileUrl] 
	* @return: boolean  
	* @Date: 2019/2/20 14:34
	*/ 
	boolean sendRomotePicMsgById(String id, String fileUrl);
	
	/* 
	* @Description: 根据ID发送本地图片类型消息 
	* @Author: walk_code walk_code@163.com
	* @Param: [id, filePath] 
	* @return: boolean  
	* @Date: 2019/2/20 14:34
	*/ 
	boolean sendLocalPicMsgById(String id, String filePath);
	
	/* 
	* @Description: 获取群组数据列表 
	* @Author: walk_code walk_code@163.com
	* @Param: [] 
	* @return: java.util.List<com.alibaba.fastjson.JSONObject>  
	* @Date: 2019/2/20 14:34
	*/ 
	List<JSONObject> getGroupList();
	
	/* 
	* @Description: 获取好友列数据列表 
	* @Author: walk_code walk_code@163.com
	* @Param: [] 
	* @return: java.util.List<com.alibaba.fastjson.JSONObject>  
	* @Date: 2019/2/20 14:34
	*/ 
	List<JSONObject> getContactList();
	
	/* 
	* @Description: 刷新微信相关信息 
	* @Author: walk_code walk_code@163.com
	* @Param: [] 
	* @return: boolean  
	* @Date: 2019/2/20 14:38
	*/ 
	boolean refreshWxInfo();
	
	/* 
	* @Description: 检测是否退出登录状态
	* @Author: walk_code walk_code@163.com
	* @Param: [] 
	* @return: boolean  
	* @Date: 2019/2/20 14:52
	*/ 
	boolean checkIsLogout();
}
