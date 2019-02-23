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
package com.dodo.project.rebate.rebot.api.controller;

import javax.annotation.Resource;

import com.dodo.project.rebate.rebot.api.base.AbstractRobotBaseController;
import com.dodo.project.rebate.rebot.api.manager.service.ItchatService;
import com.dodo.project.rebate.rebot.api.utils.ThreadHelper;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helen.robot.api.MessageTools;

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
        log.trace("trace");
        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");
        renderJsp("/login/index");
    }

    @RequestMapping(value = "/index")
    public void index1() {
        renderJsp("/index/index");
    }

    @RequestMapping(value = "/getWxLoginQrImageUrl")
    public ResponseEntity<byte[]> getWxLoginQrImageUrl() {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(itchatService.wxLogin());
    }

    @RequestMapping(value = "/logout")
    public void logout() {
        boolean isSuccess = itchatService.wxLogout();
        String responseContent = "<center><h2>微信退出失败,请联系管理员!</h2></center>";
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
        int sentUserType = getParameterToInt("sentUserType");
        String searchText = getParameter("searchText");
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

}
