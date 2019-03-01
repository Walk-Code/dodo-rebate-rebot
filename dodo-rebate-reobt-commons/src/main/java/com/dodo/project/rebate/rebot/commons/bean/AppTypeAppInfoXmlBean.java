package com.dodo.project.rebate.rebot.commons.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/*
 * <b>AppTypeAppInfoXmlBean</b></br>
 *
 * <pre>
 * 微信消息类型为app, app info bean
 * </pre>
 *
 * @Author xqyjjq walk_code@163.com
 * @Date 2019/2/27 17:40
 * @Since JDK 1.8
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AppTypeAppInfoXmlBean {
	@XmlElement(name = "version")
	private int version;

	@XmlElement(name = "appname")
	private String appname;

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}
}
