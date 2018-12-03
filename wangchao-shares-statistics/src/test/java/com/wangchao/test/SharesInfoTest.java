 /*
  * Copyright 2018 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.wangchao.test;

 import com.wangchao.shares.service.GetSharesInfoService;
 import org.junit.Test;
 import org.springframework.beans.factory.annotation.Autowired;

 /**
  * @author wangchao4
  * @date 2018/12/311:04
  */
 public class SharesInfoTest extends BaseTest {


     @Autowired
     private GetSharesInfoService getSharesInfoService;

     @Test
     public void testShareInfoTest() throws Exception{

         getSharesInfoService.getShareInfo();

     }
 }
