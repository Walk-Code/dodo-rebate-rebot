package com.dodo.project.rebate.rebot.commons.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/*
 * <b>AppTypeAppAttachXmlBean</b></br>
 *
 * <pre>
 * 微信消息类型为app, app msg appattach bean
 * </pre>
 *
 * @Author xqyjjq walk_code@163.com
 * @Date 2019/2/27 17:55
 * @Since JDK 1.8
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AppTypeAppAttachXmlBean {
	@XmlElement(name = "totallen")
	private int totallen;

	@XmlElement(name = "attachid")
	private String attachid;

	@XmlElement(name = "emoticonmd5")
	private String emoticonmd5;

	@XmlElement(name = "fileext")
	private String fileext;

	@XmlElement(name = "cdnthumburl")
	private String cdnthumburl;

	@XmlElement(name = "cdnthumbmd5")
	private String cdnthumbmd5;

	@XmlElement(name = "cdnthumblength")
	private int cdnthumblength;

	@XmlElement(name = "cdnthumbwidth")
	private int cdnthumbwidth;

	@XmlElement(name = "cdnthumbheight")
	private int cdnthumbheight;

	@XmlElement(name = "cdnthumbaeskey")
	private String cdnthumbaeskey;

	@XmlElement(name = "aeskey")
	private String aeskey;

	@XmlElement(name = "encryver")
	private int encryver;

	@XmlElement(name = "filekey")
	private String filekey;

	public int getTotallen() {
		return totallen;
	}

	public void setTotallen(int totallen) {
		this.totallen = totallen;
	}

	public String getAttachid() {
		return attachid;
	}

	public void setAttachid(String attachid) {
		this.attachid = attachid;
	}

	public String getEmoticonmd5() {
		return emoticonmd5;
	}

	public void setEmoticonmd5(String emoticonmd5) {
		this.emoticonmd5 = emoticonmd5;
	}

	public String getFileext() {
		return fileext;
	}

	public void setFileext(String fileext) {
		this.fileext = fileext;
	}

	public String getCdnthumburl() {
		return cdnthumburl;
	}

	public void setCdnthumburl(String cdnthumburl) {
		this.cdnthumburl = cdnthumburl;
	}

	public String getCdnthumbmd5() {
		return cdnthumbmd5;
	}

	public void setCdnthumbmd5(String cdnthumbmd5) {
		this.cdnthumbmd5 = cdnthumbmd5;
	}

	public int getCdnthumblength() {
		return cdnthumblength;
	}

	public void setCdnthumblength(int cdnthumblength) {
		this.cdnthumblength = cdnthumblength;
	}

	public int getCdnthumbwidth() {
		return cdnthumbwidth;
	}

	public void setCdnthumbwidth(int cdnthumbwidth) {
		this.cdnthumbwidth = cdnthumbwidth;
	}

	public int getCdnthumbheight() {
		return cdnthumbheight;
	}

	public void setCdnthumbheight(int cdnthumbheight) {
		this.cdnthumbheight = cdnthumbheight;
	}

	public String getCdnthumbaeskey() {
		return cdnthumbaeskey;
	}

	public void setCdnthumbaeskey(String cdnthumbaeskey) {
		this.cdnthumbaeskey = cdnthumbaeskey;
	}

	public String getAeskey() {
		return aeskey;
	}

	public void setAeskey(String aeskey) {
		this.aeskey = aeskey;
	}

	public int getEncryver() {
		return encryver;
	}

	public void setEncryver(int encryver) {
		this.encryver = encryver;
	}

	public String getFilekey() {
		return filekey;
	}

	public void setFilekey(String filekey) {
		this.filekey = filekey;
	}
}
