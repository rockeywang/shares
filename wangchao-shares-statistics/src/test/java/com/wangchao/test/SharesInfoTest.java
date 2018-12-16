 /*
  * Copyright 2018 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.wangchao.test;

 import com.alibaba.fastjson.JSONObject;
 import com.wangchao.shares.service.GetSharesHolderService;
 import com.wangchao.shares.service.GetSharesInfoService;
 import com.wangchao.shares.service.GetSharesPriceCountService;
 import com.wangchao.shares.util.HTTPUtils;
 import com.wangchao.shares.vo.StockholderVo;
 import org.junit.Test;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.client.RestTemplate;

 import java.util.Date;
 import java.util.List;

 /**
  * @author wangchao4
  * @date 2018/12/311:04
  */
 public class SharesInfoTest extends BaseTest {


     @Autowired
     private GetSharesInfoService getSharesInfoService;

     @Autowired
     private GetSharesPriceCountService getSharesPriceCountService;

     @Autowired
     private RestTemplate restTemplate;

     @Autowired
     private GetSharesHolderService sharesHolderService;

    // @Test
     public void testShareInfoTest() throws Exception {

         getSharesInfoService.getShareInfo();

     }


     // @Test
     public void testSumPriceSection() throws Exception {
         getSharesPriceCountService.sumPriceSection();
     }

     //@Test
     public void testUrl() throws Exception {
         String url = "http://data.eastmoney.com/DataCenter_V3/gdfx/stockholder.ashx?code=600061&date=2018-09-30&type=Lt";
         // String response = HTTPUtils.getRawHtml(url);
         String response = restTemplate.getForObject(url, String.class);
         JSONObject jsonObject = JSONObject.parseObject(response);
         String message = jsonObject.get("data").toString();

         List<StockholderVo> obj = JSONObject.parseArray(message, StockholderVo.class);

         for (StockholderVo stockholderVo : obj) {
             stockholderVo.getSHAREHDCODE();
         }


     }


    @Test
     public void testGetSharesHolder() throws Exception{
         sharesHolderService.getSharesHolder();
     }
 }
