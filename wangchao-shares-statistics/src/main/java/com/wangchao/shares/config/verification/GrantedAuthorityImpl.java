 /*
  * Copyright 2019 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.wangchao.shares.config.verification;

 import org.springframework.security.core.GrantedAuthority;

 /**
  * @author wangchao4
  * @date 2019/4/2219:30
  */
 public class GrantedAuthorityImpl implements GrantedAuthority {


     private String authority;

     public GrantedAuthorityImpl(String authority) {
         this.authority = authority;
     }

     public void setAuthority(String authority) {
         this.authority = authority;
     }

     @Override
     public String getAuthority() {
         return this.authority;
     }

 }
