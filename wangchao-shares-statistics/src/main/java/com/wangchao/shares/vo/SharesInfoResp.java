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
 import java.math.BigDecimal;

 /**
  * @author wangchao4
  * @date 2018/12/310:33
  */

 @Getter
 @Setter
 public class SharesInfoResp implements Serializable {


     private String shareCode;


     private String shareName;

     private BigDecimal newPrice;


     private BigDecimal amountIncrease;

     private BigDecimal zhuliLiuruJinge;

     private BigDecimal zhuliLiuruBaifenbi;

     private BigDecimal zhuliBigdanLiuruJinge;


     private BigDecimal zhuliBigdanLiuruZhanbi;


     private BigDecimal zhuliliuruJinge;


     private BigDecimal zhuliliuruZhanbi;


     private BigDecimal zhuliMiddleLiuruJinge;


     private BigDecimal zhuliMiddleLiuruZhanbi;


     private BigDecimal zhuliSmallLiuruJinge;


     private BigDecimal zhuliSmallLiuruZhanbi;



 }
