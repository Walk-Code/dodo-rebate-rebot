package com.dodo.project.rebate.rebot.commons.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/*
 * <b>AppTypeXmlBean</b></br>
 *
 * <pre>
 * 微信消息类型为app，content内容为xml bean
 * </pre>
 *
 * @Author xqyjjq walk_code@163.com
 * @Date 2019/2/27 17:25
 * @Since JDK 1.8
 */
@XmlRootElement(name = "msg")
@XmlAccessorType(XmlAccessType.FIELD)
public class AppTypeXmlBean {
	@XmlElement(name = "appmsg")
	private AppTypeAppMsgXmlBean appMsg;

	@XmlElement(name = "fromusername")
	private String fromusername;

	@XmlElement(name = "scene")
	private int scene;

	@XmlElement(name = "appinfo")
	private AppTypeAppInfoXmlBean appInfo;

	@XmlElement(name = "commenturl")
	private String commenturl;

	public String getFromusername() {
		return fromusername;
	}

	public void setFromusername(String fromusername) {
		this.fromusername = fromusername;
	}

	public int getScene() {
		return scene;
	}

	public void setScene(int scene) {
		this.scene = scene;
	}


	public String getCommenturl() {
		return commenturl;
	}

	public void setCommenturl(String commenturl) {
		this.commenturl = commenturl;
	}

	public AppTypeAppMsgXmlBean getAppMsg() {
		return appMsg;
	}

	public void setAppMsg(AppTypeAppMsgXmlBean appMsg) {
		this.appMsg = appMsg;
	}

	public AppTypeAppInfoXmlBean getAppInfo() {
		return appInfo;
	}

	public void setAppInfo(AppTypeAppInfoXmlBean appInfo) {
		this.appInfo = appInfo;
	}
}
