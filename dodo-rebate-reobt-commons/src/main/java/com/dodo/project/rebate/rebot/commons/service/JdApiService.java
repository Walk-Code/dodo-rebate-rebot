package com.dodo.project.rebate.rebot.commons.service;

import com.jd.open.api.sdk.JdException;
import jd.union.open.promotion.common.get.response.UnionOpenPromotionCommonGetResponse;

/*
 * <b>JdApiService</b></br>
 *
 * <pre>
 * JD相关api调用服务类
 * </pre>
 *
 * @Author xqyjjq walk_code@163.com
 * @Date 2019/2/26 16:50
 * @Since JDK 1.8
 */
public interface JdApiService {
	
	/* 
	* @Description: 获取通用推广链接
	* @Author: walk_code walk_code@163.com
	* @Param: [url] 
	* @return: jd.union.open.promotion.common.get.response.UnionOpenPromotionCommonGetResponse  
	* @Date: 2019/2/26 17:08
	*/
	UnionOpenPromotionCommonGetResponse getPromotionUrl(String url);
}
