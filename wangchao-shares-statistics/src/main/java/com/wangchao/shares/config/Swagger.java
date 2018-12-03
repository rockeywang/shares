 /*
  * Copyright 2018 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.wangchao.shares.config;

 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import springfox.documentation.builders.ApiInfoBuilder;
 import springfox.documentation.builders.PathSelectors;
 import springfox.documentation.builders.RequestHandlerSelectors;
 import springfox.documentation.service.ApiInfo;
 import springfox.documentation.spi.DocumentationType;
 import springfox.documentation.spring.web.plugins.Docket;
 import springfox.documentation.swagger2.annotations.EnableSwagger2;

 /**
  * swagger配置
  * @author wangchao4
  * @date 2018/9/149:46
  */
 @Configuration
 @EnableSwagger2
 public class Swagger {
     //swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
     @Bean
     public Docket createRestApi() {
         return new Docket(DocumentationType.SWAGGER_2)
                 .apiInfo(apiInfo())
                 .select()
                 //为当前包路径
                 .apis(RequestHandlerSelectors.basePackage("com.wangchao.shares.controller"))
                 .paths(PathSelectors.any())
                 .build();
     }

     //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
     private ApiInfo apiInfo() {
         return new ApiInfoBuilder()
                 //页面标题
                 .title("门店铺货系统")
                 //创建人
                 //版本号
                 .version("1.0")
                 //描述
                 .description("前端接口")
                 .build();
     }

 }