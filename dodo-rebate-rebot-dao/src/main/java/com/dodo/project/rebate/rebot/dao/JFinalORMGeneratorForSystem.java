package com.dodo.project.rebate.rebot.dao;

import com.dodo.project.base.dao.jfinal.utils.JFinalORMConvertDTOHelper;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.druid.DruidPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;

/*
 * <b>JFinalORMGeneratorForSystem</b></br>
 *
 * <pre>
 * 生成system db model
 * </pre>
 *
 * @Author xqyjjq walk_code@163.com
 * @Date 2018/11/2 17:35
 * @Since JDK 1.8
 */
public class JFinalORMGeneratorForSystem {
	private static final Logger logger       = LoggerFactory.getLogger(JFinalORMGeneratorForSystem.class);
	// 配置文件名称
	private static final String PROFILE_NAME = "system-application.yml";

	// 映射类名
	private static final String MAPPING_KEY_CLASS_NAME = "SystemMappingKit";

	@ConfigurationProperties(prefix = "spring.system")
	public static DataSource getDataSource() throws Exception {
		Prop prop = PropKit.use(ResourceUtils.getFile(PROFILE_NAME).getPath());
		logger.debug("获取配置文件路径中jdbcUrl={}", prop.get("url"));
		String url      = prop.get("url");
		String username = prop.get("username");
		String passwrod = prop.get("password");

		logger.debug("\n数据库admin=" + username + "\n" + "password=" + passwrod + "\n" + "url=" + url);
		DruidPlugin druidPlugin = new DruidPlugin(url, username, passwrod);
		druidPlugin.start();

		return druidPlugin.getDataSource();
	}

	public static void main(String[] args) throws Exception {
		// 当前包名
		String currentPackageName = JFinalORMGeneratorForSystem.class.getPackage().getName();

		// 生成model包名
		String modelPackageName = currentPackageName + ".system.model";

		// 生成base model包名
		String baseModelPackageName = modelPackageName + ".base";

		// base model生成目录
		String baseModelOutputDir = PathKit.getWebRootPath() + "/src/main/java/" + baseModelPackageName.replace(".", "/");

		// model生成目录
		String modelOutputDir = baseModelOutputDir + "/..";

		Generator generator = new Generator(JFinalORMGeneratorForSystem.getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
		// 设置表映射工具的类名
		generator.setMappingKitClassName(MAPPING_KEY_CLASS_NAME);
		// 设置数据库方言
		generator.setDialect(new MysqlDialect());
		// 添加不需要生成的表名
		generator.addExcludedTable("");
		// 设置是否在 Model 中生成 dao 对象
		generator.setGenerateDaoInModel(true);
		// 设置是否生成字典文件
		generator.setGenerateDataDictionary(false);
		// 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为 "User"而非 OscUser
		//generator.setRemovedTableNamePrefixes("system_");
		generator.generate();
	}
}
