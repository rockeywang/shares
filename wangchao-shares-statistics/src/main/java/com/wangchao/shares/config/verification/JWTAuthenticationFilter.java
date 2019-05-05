 /*
  * Copyright 2019 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.wangchao.shares.config.verification;

 import org.springframework.security.core.Authentication;
 import org.springframework.security.core.context.SecurityContextHolder;
 import org.springframework.web.filter.GenericFilterBean;

 import javax.servlet.FilterChain;
 import javax.servlet.ServletException;
 import javax.servlet.ServletRequest;
 import javax.servlet.ServletResponse;
 import javax.servlet.http.HttpServletRequest;
 import java.io.IOException;

 /**
  * 拦截器，拦截所有需要JWT的请求，然后调用TokenAuthenticationService类的静态方法去做JWT验证。
  * @author wangchao4
  * @date 2019/4/2219:31
  */
 public class JWTAuthenticationFilter extends GenericFilterBean {


     @Override
     public void doFilter(ServletRequest request,
                          ServletResponse response,
                          FilterChain filterChain)
             throws IOException, ServletException {
         Authentication authentication = TokenAuthenticationService
                 .getAuthentication((HttpServletRequest) request);

         SecurityContextHolder.getContext()
                 .setAuthentication(authentication);
         filterChain.doFilter(request, response);
     }

 }
