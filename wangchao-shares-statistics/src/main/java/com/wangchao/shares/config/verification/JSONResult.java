 /*
  * Copyright 2019 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.wangchao.shares.config.verification;

 import com.alibaba.fastjson.JSONObject;

 /**
  * @author wangchao4
  * @date 2019/4/2219:19
  */
 public class JSONResult {


     public static String fillResultString(Integer status, String message, Object result) {
         JSONObject jsonObject = new JSONObject() {{
             put("status", status);
             put("message", message);
             put("result", result);
         }};
         return jsonObject.toString();
     }

 }
