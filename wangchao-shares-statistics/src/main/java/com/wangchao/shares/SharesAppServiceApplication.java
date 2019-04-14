package com.wangchao.shares;

import org.redisson.api.RedissonClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages ="com.wangchao.shares.dao",sqlSessionFactoryRef = "localSqlSessionFactory")
@ImportResource(locations={"classpath:spring-elastic-job.xml"})
@org.mybatis.spring.annotation.MapperScan(basePackages = "com.wangchao.shares.dao",sqlSessionFactoryRef = "publicSqlSessionFactory")

public class SharesAppServiceApplication {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder build) {
		return build.build();
	}



	public static void main(String[] args) {
		SpringApplication.run(SharesAppServiceApplication.class, args);
	}
}
