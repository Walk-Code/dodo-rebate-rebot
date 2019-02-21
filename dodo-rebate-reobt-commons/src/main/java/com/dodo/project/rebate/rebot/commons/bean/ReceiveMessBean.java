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
package com.dodo.project.rebate.rebot.commons.bean;

/* 
* @Description: 接收调用方发送的信息
* @Author: walk_code walk_code@163.com
* @Param:  
* @return:   
* @Date: 2019/2/20 14:13
*/ 
public class ReceiveMessBean {
    private String robot;      // 发送者名称

    private String userId;     // 发送用户id

    private int    userType;   // 1-家长 2-教员

    private String message;    // 发送信息

    private String imgUrl;     // 发送图片url

    private long   createTime; // 创建时间

    public String getRobot() {
        return robot;
    }

    public void setRobot(String robot) {
        this.robot = robot;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

}
