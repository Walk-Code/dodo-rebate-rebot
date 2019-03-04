package com.dodo.project.rebate.rebot.api.manager;

import com.dodo.project.base.exception.utils.JsonHelper;
import com.dodo.project.rebate.rebot.api.utils.ValidatorHelper;
import com.dodo.project.rebate.rebot.api.utils.XmlHelper;
import com.dodo.project.rebate.rebot.commons.bean.AppTypeAppMsgXmlBean;
import com.dodo.project.rebate.rebot.commons.bean.AppTypeXmlBean;
import com.dodo.project.rebate.rebot.commons.service.JdApiService;
import com.helen.robot.api.MessageTools;
import com.helen.robot.api.WechatTools;
import com.helen.robot.beans.BaseMsg;
import com.helen.robot.face.IMsgHandlerFace;
import com.helen.robot.utils.enums.MsgCodeEnum;
import com.helen.robot.utils.enums.MsgTypeEnum;
import jd.union.open.promotion.common.get.response.UnionOpenPromotionCommonGetResponse;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/*
 * <b>MessageDealManager</b></br>
 *
 * <pre>
 * 接受消息管理类
 * </pre>
 *
 * @Author xqyjjq walk_code@163.com
 * @Date 2019/2/26 18:27
 * @Since JDK 1.8
 */
@Service
public class MessageDealManager implements IMsgHandlerFace {
	private static final Logger log = LoggerFactory.getLogger(MessageDealManager.class);

	@Resource
	private JdApiService jdApiService;

	@Override
	public String textMsgHandle(BaseMsg msg) {
		// MessageTools.webWxSendMsg(msg.getMsgType(), msg.getContent(), msg.getFromUserName());
		// 不处理群消息
		log.info("收到信息text json数据：{}", JsonHelper.toJson(msg));
		if (!msg.isGroupMsg()) {
			log.info("收到信息json数据：{}", JsonHelper.toJson(msg));
			if (ValidatorHelper.validateUrl(msg.getContent())) {
				String url = msg.getContent();
				String rebateUrl;
				if (ValidatorHelper.isJDUrl(url)) {
					UnionOpenPromotionCommonGetResponse response = jdApiService.getPromotionUrl(url);
					rebateUrl = response.getData().getClickURL();
				} else {
					rebateUrl = "不支持非JD Url";
				}

				MessageTools.sendMsgById(rebateUrl, msg.getFromUserName());
			}
		}
		return null;
	}

	@Override
	public String picMsgHandle(BaseMsg msg) {
		return null;
	}

	@Override
	public String voiceMsgHandle(BaseMsg msg) {
		return null;
	}

	@Override
	public String viedoMsgHandle(BaseMsg msg) {
		return null;
	}

	@Override
	public String nameCardMsgHandle(BaseMsg msg) {
		return null;
	}

	@Override
	public void sysMsgHandle(BaseMsg msg) {

	}

	@Override
	public String verifyAddFriendMsgHandle(BaseMsg msg) {
		return null;
	}

	@Override
	public String mediaMsgHandle(BaseMsg msg) {
		log.info("获取消息类型：{}", msg.getMsgType());
		// String url = "https://item.m.jd.com/product/770137.html?dl_abtest=o&amp;utm_source=iosapp&amp;utm_medium=appshare&amp;utm_campaign=t_335139774&amp;utm_term=Wxfriends&amp;ad_od=share&amp;ShareTm=IDKvUm38IrqU/6HCg4Kch%2BTCNX422ywRQl31bZ2/C7W8t7N76vTKN3YoWuMg957zdrjwAWOJMkXjlFuGKSm4yCpLlbe6Ca7hib0dLy%2B5j7P7fRcSdfNFw71MNx857tPxCiP5yTvpxbJTsi3p3P/q26Zl78p90KASuIkicoolIa4=";
		// MessageTools.webWxSendMsg(msg.getMsgType(),url, msg.getFromUserName());
		// 不处理群信息
		if (!msg.isGroupMsg()) {
			log.info("收到信息media json数据：{}", JsonHelper.toJson(msg));
			String content = StringEscapeUtils.unescapeXml(msg.getContent());
			if (ValidatorHelper.isXml(content)) {
				AppTypeXmlBean       bean       = XmlHelper.xmlToObj(content, AppTypeXmlBean.class);
				AppTypeAppMsgXmlBean appMsgBean = bean.getAppMsg();
				String               url        = appMsgBean.getUrl();
				String               rebateUrl  = "";
				if (ValidatorHelper.isJDUrl(url)) {
					UnionOpenPromotionCommonGetResponse response = jdApiService.getPromotionUrl(url);
					rebateUrl = response.getData().getClickURL();
					// 设置返利链接至xml
					log.info("返利url:{}", rebateUrl);
					// appMsgBean.setTitle("请点击以下链接/n" + appMsgBean.getTitle());
					// appMsgBean.setUrl(rebateUrl);
					// rebateUrl = XmlHelper.objToXml(bean);
					// log.info("转义后xml:{}", rebateUrl);
					// MessageTools.webWxSendMsg(MsgCodeEnum.MSGTYPE_MEDIA.getCode(), rebateUrl, "@c4dd8317b0b6a57b3bf7608ef29729ae7c8c19af0da4168da74848b9c392f944");
					MessageTools.sendMsgById(rebateUrl, msg.getFromUserName());
				} else {
					rebateUrl = "不支持非JD Url";
					MessageTools.sendMsgById(rebateUrl, msg.getFromUserName());
				}
			}
		}

		return null;
	}
}
