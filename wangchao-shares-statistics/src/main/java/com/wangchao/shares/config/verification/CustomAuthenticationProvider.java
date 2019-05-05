 /*
  * Copyright 2019 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.wangchao.shares.config.verification;

 import org.springframework.security.authentication.AuthenticationProvider;
 import org.springframework.security.authentication.BadCredentialsException;
 import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
 import org.springframework.security.core.Authentication;
 import org.springframework.security.core.AuthenticationException;
 import org.springframework.security.core.GrantedAuthority;

 import java.util.ArrayList;

 /**
  * @author wangchao4
  * @date 2019/4/2219:28
  */
// 自定义身份认证验证组件
 public class CustomAuthenticationProvider implements AuthenticationProvider {

     @Override
     public Authentication authenticate(Authentication authentication) throws AuthenticationException {
         // 获取认证的用户名 & 密码
         String name = authentication.getName();
         String password = authentication.getCredentials().toString();

         // 认证逻辑
         if (name.equals("admin") && password.equals("123456")) {

             // 这里设置权限和角色
             ArrayList<GrantedAuthority> authorities = new ArrayList<>();
             authorities.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
             authorities.add(new GrantedAuthorityImpl("AUTH_WRITE"));
             // 生成令牌
             Authentication auth = new UsernamePasswordAuthenticationToken(name, password, authorities);
             return auth;
         } else {
             throw new BadCredentialsException("密码错误~");
         }
     }

     // 是否可以提供输入类型的认证服务
     @Override
     public boolean supports(Class<?> authentication) {
         return authentication.equals(UsernamePasswordAuthenticationToken.class);
     }
 }