package com.dodo.project.rebate.rebot.api.utils;

import com.dodo.project.base.exception.utils.JsonHelper;
import com.dodo.project.rebate.rebot.commons.bean.AppTypeXmlBean;
import org.apache.poi.ss.formula.functions.T;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/*
 * <b>XmlHelper</b></br>
 *
 * <pre>
 * xml工具类
 * </pre>
 *
 * @Author xqyjjq walk_code@163.com
 * @Date 2019/2/27 16:44
 * @Since JDK 1.8
 */
public class XmlHelper {
	/*
	 * @Description: xml字符串转bean
	 * @Author: walk_code walk_code@163.com
	 * @Param: [xml, tClass]
	 * @return: T
	 * @Date: 2019/2/27 16:46
	 */
	public static <T> T xmlToObj(String xml, Class<T> tClass) {
		T object = null;
		try {
			JAXBContext  context      = JAXBContext.newInstance(tClass);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			object = (T) unmarshaller.unmarshal(new StringReader(xml));
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return object;
	}

	/*
	 * @Description: obj转xml
	 * @Author: walk_code walk_code@163.com
	 * @Param: [obj]
	 * @return: java.lang.String
	 * @Date: 2019/2/27 17:22
	 */
	public static String objToXml(Object obj) {
		return objToXml(obj, "UTF-8");
	}

	/*
	 * @Description: obj转xml
	 * @Author: walk_code walk_code@163.com
	 * @Param: [object, encoding]
	 * @return: java.lang.String
	 * @Date: 2019/2/27 17:19
	 */
	public static String objToXml(Object object, String encoding) {
		String result = null;
		try {
			JAXBContext context    = JAXBContext.newInstance(object.getClass());
			Marshaller  marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);

			StringWriter writer = new StringWriter();
			marshaller.marshal(object, writer);
			result = writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static void main(String[] args) {
		 StringBuilder xmlStr = new StringBuilder();
		 xmlStr.append("<?xml version=\"1.0\"?>");
		xmlStr.append("<msg>");
		xmlStr.append("\t<appmsg appid=\"\" sdkver=\"0\">");
		xmlStr.append("\t\t<title>小米(MI)游戏本 15.6英寸轻薄窄边框笔记本电脑（英特尔八代酷睿I5-8300H 8G 1T+256G SSD GTX1060 6G 72%NTSC高色域 FHD）深空灰</title>");
		xmlStr.append("\t\t<des>售价：¥6966.00</des>");
		xmlStr.append("\t\t<action />");
		xmlStr.append("\t\t<type>5</type>");
		xmlStr.append("\t\t<showtype>0</showtype>");
		xmlStr.append("\t\t<soundtype>0</soundtype>");
		xmlStr.append("\t\t<mediatagname />");
		xmlStr.append("\t\t<messageext />");
		xmlStr.append("\t\t<mediatagname />");
		xmlStr.append("\t\t<messageaction />");
		xmlStr.append("\t\t<content />");
		xmlStr.append("\t\t<contentattr>0</contentattr>");
		xmlStr.append("\t\t<url>https://item.m.jd.com/product/100000165908.html?PTAG=17053.1.1&amp;utm_source=weixin&amp;utm_medium=weixin&amp;utm_campaign=t_1000072672_17053_001</url>");
		xmlStr.append("\t\t<lowurl />");
		xmlStr.append("\t\t<dataurl />");
		xmlStr.append("\t\t<lowdataurl />");
		xmlStr.append("\t\t<appattach>");
		xmlStr.append("\t\t<totallen>0</totallen>");
		xmlStr.append("\t\t<attachid />");
		xmlStr.append("\t\t<emoticonmd5 />");
		xmlStr.append("\t\t<fileext />");
		xmlStr.append("\t\t<cdnthumbaeskey />");
		xmlStr.append("\t\t<aeskey />");
		xmlStr.append("\t\t</appattach>");
		xmlStr.append("\t\t<extinfo />");
		xmlStr.append("\t\t<sourceusername />");
		xmlStr.append("\t\t<sourcedisplayname />");
		xmlStr.append("\t\t<thumburl>http://img14.360buyimg.com/n4/jfs/t1/24350/7/194/248202/5c07bf08E31e95048/5500d487160b3a6e.jpg</thumburl>");
		xmlStr.append("\t\t<md5 />");
		xmlStr.append("\t\t<statextstr />");
		xmlStr.append("\t\t<webviewshared>");
		xmlStr.append("\t\t<jsAppId>wxae3e8056daea8727</jsAppId>");
		xmlStr.append("\t\t</webviewshared>");
		xmlStr.append("\t\t</appmsg>");
		xmlStr.append("\t\t<fromusername></fromusername>");
		xmlStr.append("\t\t<scene>0</scene>");
		xmlStr.append("\t\t<appinfo>");
		xmlStr.append("\t\t<version>1</version>");
		xmlStr.append("\t\t<appname></appname>");
		xmlStr.append("\t\t</appinfo>");
		xmlStr.append("\t\t<commenturl></commenturl>");
		xmlStr.append("\t\t</msg>");

		 AppTypeXmlBean bean   = XmlHelper.xmlToObj(xmlStr.toString().replaceAll("//s", ""), AppTypeXmlBean.class);

		 System.out.println(JsonHelper.toJson(bean));
		// System.out.println(XmlHelper.objToXml(bean));
		// System.out.println(ValidatorHelper.isXml(xmlStr));
	}
}
