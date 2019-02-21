package com.dodo.project.rebate.rebot.api.config;

import com.dodo.project.base.web.utils.TypeTransformHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/*
 * <b>CommonSystemConfiguration</b></br>
 *
 * <pre>
 * 公共系统配置
 * </pre>
 *
 * @Author xqyjjq walk_code@163.com
 * @Date 2019/2/20 14:16
 * @Since JDK 1.8
 */
@Component
public class CommonSystemConfiguration {
	private final static Logger logger = LoggerFactory.getLogger(CommonSystemConfiguration.class);

	@Resource
	private Environment env;

	/**
	 *
	 * <b>isDebug</b> <br/>
	 * <br/>
	 *
	 * 判断当前系统是否开启调试模式<br/>
	 *
	 * @author cpthack 1044559878@qq.com
	 * @return boolean ture表示开启了调试模式
	 *
	 */
	public boolean isDebug() {
		String isDebugToStr = env.getProperty("myServer.debug");
		if (StringUtils.isNotBlank(isDebugToStr)) {
			return TypeTransformHelper.objToBoolean(isDebugToStr, false);
		}
		else {
			logger.warn("检测不到配置项[server.debug],将设置默认值:false.");
		}

		return false;
	}
}
