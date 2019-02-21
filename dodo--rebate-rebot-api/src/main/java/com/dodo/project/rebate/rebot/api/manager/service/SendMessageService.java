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
package com.dodo.project.rebate.rebot.api.manager.service;

import com.dodo.project.rebate.rebot.commons.bean.MassMessageBean;

/* 
* @Description: 发送信息业务服务类
* @Author: walk_code walk_code@163.com
* @Param:  
* @return:   
* @Date: 2019/2/20 14:23
*/ 
public interface SendMessageService {

    /* 
    * @Description: 发送信息
    * @Author: walk_code walk_code@163.com
    * @Param: [massMessageBean] 
    * @return: boolean  
    * @Date: 2019/2/20 14:23
    */ 
    boolean sendMessage(MassMessageBean massMessageBean);
}
