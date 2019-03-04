package com.dodo.project.rebate.rebot.commons.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/*
 * <b>AppTypeWebviewsharedXmlBean</b></br>
 *
 * <pre>
 * 微信消息类型为app, app msg webviewshared bean
 * </pre>
 *
 * @Author xqyjjq walk_code@163.com
 * @Date 2019/2/27 17:58
 * @Since JDK 1.8
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AppTypeWebviewsharedXmlBean {
	@XmlElement(name = "jsAppId")
	private String jsAppId;

	public String getJsAppId() {
		return jsAppId;
	}

	public void setJsAppId(String jsAppId) {
		this.jsAppId = jsAppId;
	}
}
