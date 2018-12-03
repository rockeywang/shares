 /*
  * Copyright 2018 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.wangchao.shares.vo;

 import lombok.Getter;
 import lombok.Setter;

 import java.io.Serializable;
 import java.util.Date;

 /**
  * @author wangchao4
  * @date 2018/12/311:33
  */
 @Getter
 @Setter
 public class ApiResult implements Serializable {

     private Integer pages;

     private Date date;

     private String data;
 }
