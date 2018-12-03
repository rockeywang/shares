 /*
  * Copyright 2018 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.wangchao.shares.controller;

 import com.alibaba.fastjson.JSON;
 import com.github.pagehelper.PageInfo;
 import com.wangchao.shares.base.BaseResp;
 import com.wangchao.shares.service.GetSharesInfoService;
 import com.wangchao.shares.vo.SharesInfoReq;
 import com.wangchao.shares.vo.SharesInfoResp;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiOperation;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RestController;

 import javax.validation.Valid;

 /**
  * @author wangchao4
  * @date 2018/12/318:47
  */
 @RestController
 @RequestMapping(value = "/shares/")
 @Api(basePath = "/shares/", value = "shares-count", description = "pc端-股票数据统计", produces = "application/json")
 public class SharesController {


     private final Logger logger = LoggerFactory.getLogger(getClass());

     @Autowired
     private GetSharesInfoService getSharesInfoService;


     @PostMapping(value = "findSharesList")
     @ApiOperation(httpMethod = "POST", value = "[pc端]-[查询股票数据列表]", notes = "查询股票数据列表", response = SharesInfoResp.class)
     public BaseResp<PageInfo<SharesInfoResp>> findShopConfigList(@Valid @RequestBody SharesInfoReq req) {
         BaseResp<PageInfo<SharesInfoResp>> resp = null;
         String funName = "pc端->查询股票数据列表";
         try {
             logger.info(" {},请求参数 : {}", funName, req);
             PageInfo<SharesInfoResp> pageList = getSharesInfoService.getSharesInfoList(req);
             logger.info(funName + ",返回结果：" + JSON.toJSONString(pageList));
             resp = BaseResp.buildSuccessResp(BaseResp.class);
             resp.setData(pageList);
         }  catch (Exception t) {
             logger.error("{},系统异常,:{}", funName, t);
             resp = BaseResp.buildFailResp(t, funName, BaseResp.class);
         }
         return resp;
     }

 }
