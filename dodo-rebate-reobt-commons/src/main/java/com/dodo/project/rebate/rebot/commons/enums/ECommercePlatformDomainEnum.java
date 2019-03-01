package com.dodo.project.rebate.rebot.commons.enums;

/*
 * @Description: 电商平台domain enum
 * @Author: walk_code walk_code@163.com
 * @Param:
 * @return:
 * @Date: 2019/2/27 15:43
 */
public enum ECommercePlatformDomainEnum {
	JD("jd.com", "京东域名");

	private String domainName;
	private String desc;

	ECommercePlatformDomainEnum(String domainName, String desc) {
		this.domainName = domainName;
		this.desc = desc;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
