 /*
  * Copyright 2018 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.wangchao.shares.job;

 import com.dangdang.ddframe.job.api.ShardingContext;
 import com.dangdang.ddframe.job.api.simple.SimpleJob;
 import com.wangchao.shares.service.GetSharesInfoService;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Component;

 /**
  * @author wangchao4
  * @date 2018/12/310:57
  */
 @Component
 public class GetSharesInfoJob implements SimpleJob {

     private final Logger logger = LoggerFactory.getLogger(getClass());

     @Autowired
     private GetSharesInfoService sharesInfoService;

     @Override
     public void execute(ShardingContext shardingContext) {
         logger.info("getSharesInfoJob········获取东方财富网股票数据job类开始·······························");
         try {
             sharesInfoService.getShareInfo();
         } catch (Exception e) {
             logger.error("GetSharesInfoService类异常", e);
         }
         logger.info("getSharesInfoJob·········获取东方财富网股票数据job类结束·······························");
     }
 }
