package com.wangchao.shares;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.wangchao.shares.dao")
@ImportResource(locations={"classpath:spring-elastic-job.xml"})
public class SharesAppServiceApplication {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder build) {
		return build.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(SharesAppServiceApplication.class, args);
	}
}
