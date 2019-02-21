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
package com.dodo.project.rebate.rebot.api.schedule;

import javax.annotation.Resource;

import com.dodo.project.rebate.rebot.api.manager.service.ItchatService;
import com.dodo.project.rebate.rebot.api.utils.ThreadHelper;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/* 
* @Description: 定期刷新同步登录用户信息
* @Author: walk_code walk_code@163.com
* @Param:  
* @return:   
* @Date: 2019/2/20 17:41
*/ 
@EnableScheduling
@Component
public class RefreshWeixinInfoSchedule {

    private final static Logger log = LoggerFactory.getLogger(RefreshWeixinInfoSchedule.class);

    @Resource
    private ItchatService itchatService;

    @Scheduled(cron = "${timer.refreshWeixinInfo.sync}")
    public void refresh() {
        if (!itchatService.isWxAlive()) {
            return;
        }
        // 增加请求随机性
        long sleepTimeMillis = RandomUtils.nextLong(1 * 1000, 10 * 1000);
        ThreadHelper.sleep(sleepTimeMillis);
        log.info("开始更新微信机器人通讯录信息.---延时{}毫秒", sleepTimeMillis);
        itchatService.refreshWxInfo();
        log.info("完成更新微信机器人通讯录信息.---延时{}毫秒", sleepTimeMillis);
    }

}
