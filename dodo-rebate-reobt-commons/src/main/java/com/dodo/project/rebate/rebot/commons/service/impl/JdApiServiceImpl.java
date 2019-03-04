package com.dodo.project.rebate.rebot.commons.service.impl;

import com.dodo.project.rebate.rebot.commons.constans.JdApiConstans;
import com.dodo.project.rebate.rebot.commons.service.JdApiService;
import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import jd.union.open.order.query.response.UnionOpenOrderQueryResponse;
import jd.union.open.promotion.common.get.request.PromotionCodeReq;
import jd.union.open.promotion.common.get.request.UnionOpenPromotionCommonGetRequest;
import jd.union.open.promotion.common.get.response.UnionOpenPromotionCommonGetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/*
 * <b>JdApiServiceImpl</b></br>
 *
 * <pre>
 * JD相关api调用服务类实现类
 * </pre>
 *
 * @Author xqyjjq walk_code@163.com
 * @Date 2019/2/26 17:08
 * @Since JDK 1.8
 */
@Service
public class JdApiServiceImpl implements JdApiService {
	public static final Logger log = LoggerFactory.getLogger(JdApiServiceImpl.class);

	@Override
	public UnionOpenPromotionCommonGetResponse getPromotionUrl(String url) {
		String serverUrl = JdApiConstans.JD_DOMAIN;
		String appKey    = JdApiConstans.JD_APP_KEY;
		String appSecet  = JdApiConstans.JD_APP_SERCET;
		String siteId    = JdApiConstans.JD_WEB_SITE_ID;

		String   accessToken = "";
		JdClient client      = new DefaultJdClient(serverUrl, accessToken, appKey, appSecet);

		UnionOpenPromotionCommonGetRequest request          = new UnionOpenPromotionCommonGetRequest();
		PromotionCodeReq                   promotionCodeReq = new PromotionCodeReq();
		promotionCodeReq.setMaterialId(url);
		promotionCodeReq.setSiteId(siteId);
		request.setPromotionCodeReq(promotionCodeReq);
		try {
			return client.execute(request);
		} catch (JdException e) {
			log.error("调用京东联盟api失败：{}", e);
		}

		return null;
	}



}
