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
package com.dodo.project.rebate.rebot.commons.enums;

/**
 * <b>RobotEnums.java</b></br>
 * 
 * <pre>
 * 机器名称枚举类
 * </pre>
 *
 * @author xqyjjq walk_code@163.com
 * @date 2018年12月6日 下午4:52:12
 * @since JDK 1.8
 */
public enum RobotEnum {
    Robot_0x1("openId"); // openId
    private String value;

    RobotEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
