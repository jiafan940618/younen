package com.yn.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;

/**
 * 数据源2-->次数据源。
 * 连接到youneng
 * @author {lzyqssn}
 * <2017年9月28日-下午5:05:48>
 */
@Configuration
@MapperScan(basePackages = "com.yn.dao", sqlSessionTemplateRef = "test2SqlSessionTemplate")
public class DataSource2 {
	@Bean(name = "test2DataSource")
	@ConfigurationProperties(prefix = "spring.datasource.secondary")
	public DataSource testDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "test2SqlSessionFactory")
	public SqlSessionFactory testSqlSessionFactory(@Qualifier("test2DataSource") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
		return bean.getObject();
	}

	@Bean(name = "test2TransactionManager")
	public PlatformTransactionManager testTransactionManager(@Qualifier("test2DataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "test2SqlSessionTemplate")
	public SqlSessionTemplate testSqlSessionTemplate(
			@Qualifier("test2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}