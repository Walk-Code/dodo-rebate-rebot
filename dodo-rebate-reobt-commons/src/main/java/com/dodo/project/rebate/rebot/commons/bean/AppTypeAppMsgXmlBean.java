package com.dodo.project.rebate.rebot.commons.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/*
 * <b>AppTypeAppMsgXmlBean</b></br>
 *
 * <pre>
 * 微信消息类型为app, app msg bean
 * </pre>
 *
 * @Author xqyjjq walk_code@163.com
 * @Date 2019/2/27 17:35
 * @Since JDK 1.8
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AppTypeAppMsgXmlBean {
	@XmlElement(name = "title")
	private String title;

	@XmlElement(name = "des")
	private String des;

	@XmlElement(name = "action")
	private String action;

	@XmlElement(name = "type")
	private int type;

	@XmlElement(name = "showtype")
	private int showtype;

	@XmlElement(name = "soundtype")
	private int soundtype;

	@XmlElement(name = "mediatagname")
	private String mediatagname;

	@XmlElement(name = "messageext")
	private String messageext;

	@XmlElement(name = "messageaction")
	private String messageaction;

	@XmlElement(name = "content")
	private String content;

	@XmlElement(name = "contentattr")
	private int contentattr;

	@XmlElement(name = "url")
	private String url;

	@XmlElement(name = "lowurl")
	private String lowurl;

	@XmlElement(name = "dataurl")
	private String dataurl;

	@XmlElement(name = "lowdataurl")
	private String lowdataurl;

	@XmlElement(name = "appattach")
	private AppTypeAppAttachXmlBean appattach;

	@XmlElement(name = "extinfo")
	private String extinfo;

	@XmlElement(name = "sourceusername")
	private String sourceusername;

	@XmlElement(name = "sourcedisplayname")
	private String sourcedisplayname;

	@XmlElement(name = "thumburl")
	private String thumburl;

	@XmlElement(name = "md5")
	private String md5;

	@XmlElement(name = "statextstr")
	private String statextstr;

	@XmlElement(name = "webviewshared")
	private AppTypeWebviewsharedXmlBean webviewshared;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getShowtype() {
		return showtype;
	}

	public void setShowtype(int showtype) {
		this.showtype = showtype;
	}

	public int getSoundtype() {
		return soundtype;
	}

	public void setSoundtype(int soundtype) {
		this.soundtype = soundtype;
	}

	public String getMediatagname() {
		return mediatagname;
	}

	public void setMediatagname(String mediatagname) {
		this.mediatagname = mediatagname;
	}

	public String getMessageext() {
		return messageext;
	}

	public void setMessageext(String messageext) {
		this.messageext = messageext;
	}

	public String getMessageaction() {
		return messageaction;
	}

	public void setMessageaction(String messageaction) {
		this.messageaction = messageaction;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getContentattr() {
		return contentattr;
	}

	public void setContentattr(int contentattr) {
		this.contentattr = contentattr;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLowurl() {
		return lowurl;
	}

	public void setLowurl(String lowurl) {
		this.lowurl = lowurl;
	}

	public String getDataurl() {
		return dataurl;
	}

	public void setDataurl(String dataurl) {
		this.dataurl = dataurl;
	}

	public String getLowdataurl() {
		return lowdataurl;
	}

	public void setLowdataurl(String lowdataurl) {
		this.lowdataurl = lowdataurl;
	}

	public AppTypeAppAttachXmlBean getAppattach() {
		return appattach;
	}

	public void setAppattach(AppTypeAppAttachXmlBean appattach) {
		this.appattach = appattach;
	}

	public String getExtinfo() {
		return extinfo;
	}

	public void setExtinfo(String extinfo) {
		this.extinfo = extinfo;
	}

	public String getSourceusername() {
		return sourceusername;
	}

	public void setSourceusername(String sourceusername) {
		this.sourceusername = sourceusername;
	}

	public String getSourcedisplayname() {
		return sourcedisplayname;
	}

	public void setSourcedisplayname(String sourcedisplayname) {
		this.sourcedisplayname = sourcedisplayname;
	}

	public String getThumburl() {
		return thumburl;
	}

	public void setThumburl(String thumburl) {
		this.thumburl = thumburl;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getStatextstr() {
		return statextstr;
	}

	public void setStatextstr(String statextstr) {
		this.statextstr = statextstr;
	}

	public AppTypeWebviewsharedXmlBean getWebviewshared() {
		return webviewshared;
	}

	public void setWebviewshared(AppTypeWebviewsharedXmlBean webviewshared) {
		this.webviewshared = webviewshared;
	}
}
