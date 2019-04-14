package com.wangchao.shares.config;


import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 公共通用数据库
 */
@Configuration
public class PublicDataSourceConfig {

    @Bean(name = "publicDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.public")
    public DataSource publicDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "publicTransactionManager")
    public DataSourceTransactionManager mysqlTransactionManager() {
        return new DataSourceTransactionManager(publicDataSource());
    }

    @Bean(name = "publicSqlSessionFactory")
    public SqlSessionFactory mysqlSqlSessionFactory(@Qualifier("publicDataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        //如果不使用xml的方式配置mapper，则可以省去下面这行mapper location的配置。
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:mapper2/*.xml"));
        return sessionFactory.getObject();
    }
}
