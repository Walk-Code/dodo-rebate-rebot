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

import javax.annotation.Resource;

import com.dodo.project.rebate.rebot.api.config.CommonSystemConfiguration;
import com.dodo.project.rebate.rebot.api.manager.service.ItchatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.helen.robot.core.Core;

/*
* @Description: 管理微信机器人登录状态
* @Author: walk_code walk_code@163.com
* @Param:
* @return:
* @Date: 2019/2/20 14:09
*/
@Service
public class CheckLoginStatusManager {

    @Resource
    private ItchatService itchatService;

    @Resource
    private Environment               environment;

    @Autowired
    private CommonSystemConfiguration commonSystemConfiguration;

    /* 
    * @Description: 校验微信在线状态，微信离线处理
    * @Author: walk_code walk_code@163.com
    * @Param: [] 
    * @return: void  
    * @Date: 2019/2/20 14:17
    */ 
    public void checkLoginStatus() {
        Core core = Core.getInstance();
        if (!core.isAlive()) {
            return;
        }
        boolean isLogout = itchatService.checkIsLogout();
        if (isLogout) {
            // 改变登录状态
            core.setAlive(false);

            if (!commonSystemConfiguration.isDebug()) {
                // TODO 发送断线通知
            }
        }
    }

}
