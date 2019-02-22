package com.dodo.project.rebate.rebot.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/*
 * <b>Application</b></br>
 *
 * <pre>
 * 项目启动类
 * </pre>
 *
 * @Author xqyjjq walk_code@163.com
 * @Date 2019/2/20 13:50
 * @Since JDK 1.8
 */
@ComponentScan(basePackages = "com.dodo")
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	public static void main(String[] agrs) {
		SpringApplication.run(Application.class, agrs);
	}
}
