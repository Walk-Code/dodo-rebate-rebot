package com.dodo.project.rebate.rebot.api.utils;

import com.dodo.project.rebate.rebot.commons.enums.ECommercePlatformDomainEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.HandlerBase;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/*
 * <b>ValidatorHelper</b></br>
 *
 * <pre>
 * 常用相关验证
 * </pre>
 *
 * @Author xqyjjq walk_code@163.com
 * @Date 2019/2/27 15:27
 * @Since JDK 1.8
 */
public class ValidatorHelper {
	public static Logger log = LoggerFactory.getLogger(ValidatorHelper.class);

	/*
	 * @Description: 验证url是否合法
	 * @Author: walk_code walk_code@163.com
	 * @Param: [url]
	 * @return: boolean
	 * @Date: 2019/2/27 15:29
	 */
	public static boolean validateUrl(String url) {
		try {
			new URL(url).toURI();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * @Description: 校验url是否为jd商品url
	 * @Author: walk_code walk_code@163.com
	 * @Param: [url]
	 * @return: boolean
	 * @Date: 2019/2/27 15:31
	 */
	public static boolean isJDUrl(String url) {
		String domain = null;
		try {
			domain = getDomainName(url);
		} catch (URISyntaxException e) {
			log.error("无法访问url:{}", e);
		}

		if (domain.contains(ECommercePlatformDomainEnum.JD.getDomainName())) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * @Description: 获取domain
	 * @Author: walk_code walk_code@163.com
	 * @Param: [url]
	 * @return: java.lang.String
	 * @Date: 2019/2/27 15:36
	 */
	public static String getDomainName(String url) throws URISyntaxException {
		URI    uri    = new URI(url);
		String domain = uri.getHost();

		return domain.startsWith("www.") ? domain.substring(4) : domain;
	}
	
	/* 
	* @Description: 校验字符串是否为xml
	* @Author: walk_code walk_code@163.com
	* @Param: [content] 
	* @return: boolean  
	* @Date: 2019/2/27 18:38
	*/ 
	public static boolean isXml(String content) {
		try {
			SAXParser   saxParser = SAXParserFactory.newInstance().newSAXParser();
			InputStream stream    = new ByteArrayInputStream(content.getBytes("UTF-8"));
			saxParser.parse(stream, new DefaultHandler());
		} catch (Exception e) {
			log.error("XML解析失败：{}", e);
			return false;
		}

		return true;
	}

	public static void main(String[] args) {
		boolean result = isJDUrl("https://item.jd.com/31247994071.html?extension_id=eyJhZCI6IjE0NzYiLCJjaCI6IjIiLCJza3UiOiIzMTI0Nzk5NDA3MSIsInRzIjoiMTU1MTI1MzA3NSIsInVuaXFpZCI6IntcImNsaWNrX2lkXCI6XCJhMjM1YzhiOC02NWM0LTQzMzAtYmI5Mi1mNWFiODIwOTBiMmJcIixcIm1hdGVyaWFsX2lkXCI6XCIyNDYyNzkzMjZcIixcInBvc19pZFwiOlwiMTQ3NlwiLFwic2lkXCI6XCIzNzgwN2MyYi0zMjMyLTQyMzItODYzZi1kOTAyOTdmNTcxODFcIn0ifQ==&jd_pop=a235c8b8-65c4-4330-bb92-f5ab82090b2b&abt=0");
		System.out.println(result);

		String xmlStr="<?xmlversion=\"1.0\"?>\n" + "<msg>\n" + "\t<appmsgappid=\"\"sdkver=\"0\">\n" + "\t\t<title>小米(MI)游戏本15.6英寸轻薄窄边框笔记本电脑（英特尔八代酷睿I5-8300H8G1T+256GSSDGTX10606G72%NTSC高色域FHD）深空灰</title>\n" + "\t\t<des>售价：¥6966.00</des>\n" + "\t\t<action/>\n" + "\t\t<type>5</type>\n" + "\t\t<showtype>0</showtype>\n" + "\t\t<soundtype>0</soundtype>\n" + "\t\t<mediatagname/>\n" + "\t\t<messageext/>\n" + "\t\t<messageaction/>\n" + "\t\t<content/>\n" + "\t\t<contentattr>0</contentattr>\n" + "\t\t<url>https://item.m.jd.com/product/100000165908.html?PTAG=17053.1.1&amp;utm_source=weixin&amp;utm_medium=weixin&amp;utm_campaign=t_1000072672_17053_001</url>\n" + "\t\t<lowurl/>\n" + "\t\t<dataurl/>\n" + "\t\t<lowdataurl/>\n" + "\t\t<appattach>\n" + "\t\t\t<totallen>0</totallen>\n" + "\t\t\t<attachid/>\n" + "\t\t\t<emoticonmd5/>\n" + "\t\t\t<fileext/>\n" + "\t\t\t<cdnthumbaeskey/>\n" + "\t\t\t<aeskey/>\n" + "\t\t</appattach>\n" + "\t\t<extinfo/>\n" + "\t\t<sourceusername/>\n" + "\t\t<sourcedisplayname/>\n" + "\t\t<thumburl>http://img14.360buyimg.com/n4/jfs/t1/24350/7/194/248202/5c07bf08E31e95048/5500d487160b3a6e.jpg</thumburl>\n" + "\t\t<md5/>\n" + "\t\t<statextstr/>\n" + "\t\t<webviewshared>\n" + "\t\t\t<jsAppId>wxae3e8056daea8727</jsAppId>\n" + "\t\t</webviewshared>\n" + "\t</appmsg>\n" + "\t<fromusername></fromusername>\n" + "\t<scene>0</scene>\n" + "\t<appinfo>\n" + "\t\t<version>1</version>\n" + "\t\t<appname></appname>\n" + "\t</appinfo>\n" + "\t<commenturl></commenturl>\n" + "</msg>";
	}
}
