 /*
  * Copyright 2018 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.wangchao.shares.util;

 import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.InputStreamReader;
 import java.net.URL;
 import java.net.URLConnection;

 /**
  * @author wangchao4
  * @date 2018/12/313:38
  */
 public class HTTPUtils {
     //这个方法是向后台请求数据，获取html或者json等
     public static String getRawHtml(String personalUrl) throws InterruptedException, IOException {
         URL url = new URL(personalUrl);
         URLConnection conn = url.openConnection();
         InputStream in = null;
         try {
             conn.setConnectTimeout(3000);
             in = conn.getInputStream();
         } catch (Exception e) {
         }
         //将获取的数据转化为String
         String html = convertStreamToString(in);
         return html;
     }

     //这个方法是将InputStream转化为String
     public static String convertStreamToString(InputStream is) throws IOException {
         if (is == null)
             return "";
         BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
         StringBuilder sb = new StringBuilder();
         String line = null;
         try {
             while ((line = reader.readLine()) != null) {
                 sb.append(line);
             }
         } catch (IOException e) {
             e.printStackTrace();
         } finally {
             try {
                 is.close();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
         reader.close();
         return sb.toString();

     }
 }
