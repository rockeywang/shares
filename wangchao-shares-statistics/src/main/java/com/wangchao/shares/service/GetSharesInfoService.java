 /*
  * Copyright 2018 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.wangchao.shares.service;

 import com.github.pagehelper.Page;
 import com.github.pagehelper.PageHelper;
 import com.github.pagehelper.PageInfo;
 import com.wangchao.shares.mapper.SharesInfoDoMapper;
 import com.wangchao.shares.dataobject.SharesInfoDo;
 import com.wangchao.shares.util.DateUtil;
 import com.wangchao.shares.util.HTTPUtils;
 import com.wangchao.shares.vo.SharesInfoReq;
 import com.wangchao.shares.vo.SharesInfoResp;
 import org.apache.commons.lang3.StringUtils;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.dao.DuplicateKeyException;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 import org.springframework.util.CollectionUtils;
 import org.springframework.web.client.RestTemplate;

 import java.math.BigDecimal;
 import java.text.SimpleDateFormat;
 import java.util.*;


 /**
  * @author wangchao4
  * @date 2018/12/310:59
  */
 @Service
 public class GetSharesInfoService {

     private final Logger logger = LoggerFactory.getLogger(getClass());

     @Autowired
     private RestTemplate restTemplate;

     @Autowired
     private SharesInfoDoMapper sharesInfoDoMapper;

     @Value("${data.url}")
     private String dataUrl;


     @Transactional
     public void getShareInfo() throws Exception {

//         String data = sharesInfoDoMapper.getNewLastTime();
//
//         Calendar calendar = Calendar.getInstance();
//         calendar.setTime(new Date());
//
//         if (checkHoliday(calendar)) {
//             for (int i = 1; i < 9; i++) {
//                 calendar.add(Calendar.DAY_OF_MONTH, -i);
//                 if (checkHoliday(calendar)) {
//                     continue;
//                 }
//             }
//         }
//
//         String date1 = DateUtil.format(calendar.getTime(), DateUtil.webFormat);
//
//         if (StringUtils.isNotBlank(data) && date1.equals(data)) {
//             return;
//         }
         String data = DateUtil.format(new Date(),DateUtil.webFormat);

         String url = "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=ct&st=(BalFlowMain)&sr=-1&p=1&ps=5000&js=var%20fCYsprcU={pages:(pc),date:%22"+data+"%22,data:[(x)]}&token=894050c76af8597a853f5b408b759f5d&cmd=C._AB&sty=DCFFITA&rt=51460099";
         String response = HTTPUtils.getRawHtml(url);
        // String html=restTemplate.getForEntity(url,String.class);

         String html = response.toString();
         String jsonarra=html.split("data:")[1].split(",pages")[0];
         String stocks[] = jsonarra.split("\",");
         List<String> stocklist = new ArrayList<String>();
         for (int i = 0; i < stocks.length; i++) {
             stocklist.add(stocks[i].replace("[\"", "").replace("\"", "").replace("]", ""));
         }

         if (CollectionUtils.isEmpty(stocklist)) {
             return;
         }
         List<SharesInfoDo> sharesInfoDos = new LinkedList<>();

         String dateTime = stocklist.get(0).split(",")[15];

         Date time = DateUtil.parseDate(dateTime, DateUtil.newFormat);

         for (int i = 0; i < stocklist.size(); i++) {
             // logger.warn(stocklist.get(i).toString());
             SharesInfoDo sharesInfoDo = new SharesInfoDo();
             String shareCode = stocklist.get(i).split(",")[1];
             String code = shareCode.substring(0, 3);
             if ("900".equals(code) || "200".equals(code)) {
                 continue;
             }
             sharesInfoDo.setShareCode(shareCode);
             String shareName = stocklist.get(i).split(",")[2];
             sharesInfoDo.setShareName(shareName);
             String price = stocklist.get(i).split(",")[3];
             if (StringUtils.isBlank(price) || "-".equals(price)) {
                 continue;
             }
             sharesInfoDo.setNewPrice(new BigDecimal(price));

             String amountIncrease = stocklist.get(i).split(",")[4];
             if (StringUtils.isBlank(amountIncrease) || "-".equals(amountIncrease)) {
                 amountIncrease = "0.000";
             }
             sharesInfoDo.setAmountIncrease(new BigDecimal(amountIncrease));


             String zhuliLiuruJinge = stocklist.get(i).split(",")[5];
             if (StringUtils.isBlank(zhuliLiuruJinge) || "-".equals(zhuliLiuruJinge)) {
                 zhuliLiuruJinge = "0.000";
             }
             sharesInfoDo.setZhuliLiuruJinge(new BigDecimal(zhuliLiuruJinge));
             String zhuliLiuruBaifenbi = stocklist.get(i).split(",")[6];
             if (StringUtils.isBlank(zhuliLiuruBaifenbi) || "-".equals(zhuliLiuruBaifenbi)) {
                 zhuliLiuruBaifenbi = "0.000";
             }
             sharesInfoDo.setZhuliLiuruBaifenbi(new BigDecimal(zhuliLiuruBaifenbi));

             String zhuliBigdanLiuruJinge = stocklist.get(i).split(",")[7];
             if (StringUtils.isBlank(zhuliBigdanLiuruJinge) || "-".equals(zhuliBigdanLiuruJinge)) {
                 zhuliBigdanLiuruJinge = "0.000";
             }
             sharesInfoDo.setZhuliBigdanLiuruJinge(new BigDecimal(zhuliBigdanLiuruJinge));

             String zhuliBigdanLiuruZhanbi = stocklist.get(i).split(",")[8];
             if (StringUtils.isBlank(zhuliBigdanLiuruZhanbi) || "-".equals(zhuliBigdanLiuruZhanbi)) {
                 zhuliBigdanLiuruZhanbi = "0.000";
             }
             sharesInfoDo.setZhuliBigdanLiuruZhanbi(new BigDecimal(zhuliBigdanLiuruZhanbi));


             String zhuliliuruJinge = stocklist.get(i).split(",")[9];
             if (StringUtils.isBlank(zhuliliuruJinge) || "-".equals(zhuliliuruJinge)) {
                 zhuliliuruJinge = "0.000";
             }
             sharesInfoDo.setZhuliliuruJinge(new BigDecimal(zhuliliuruJinge));

             String zhuliliuruZhanbi = stocklist.get(i).split(",")[10];
             if (StringUtils.isBlank(zhuliliuruZhanbi) || "-".equals(zhuliliuruZhanbi)) {
                 zhuliliuruZhanbi = "0.000";
             }
             sharesInfoDo.setZhuliliuruZhanbi(new BigDecimal(zhuliliuruZhanbi));

             String zhuliMiddleLiuruJinge = stocklist.get(i).split(",")[11];
             if (StringUtils.isBlank(zhuliMiddleLiuruJinge) || "-".equals(zhuliMiddleLiuruJinge)) {
                 zhuliMiddleLiuruJinge = "0.000";
             }
             sharesInfoDo.setZhuliMiddleLiuruJinge(new BigDecimal(zhuliMiddleLiuruJinge));

             String zhuliMiddleLiuruZhanbi = stocklist.get(i).split(",")[12];
             if (StringUtils.isBlank(zhuliMiddleLiuruZhanbi) || "-".equals(zhuliMiddleLiuruZhanbi)) {
                 zhuliMiddleLiuruZhanbi = "0.000";
             }
             sharesInfoDo.setZhuliMiddleLiuruZhanbi(new BigDecimal(zhuliMiddleLiuruZhanbi));

             String zhuliSmallLiuruJinge = stocklist.get(i).split(",")[13];
             if (StringUtils.isBlank(zhuliSmallLiuruJinge) || "-".equals(zhuliSmallLiuruJinge)) {
                 zhuliSmallLiuruJinge = "0.000";
             }
             sharesInfoDo.setZhuliSmallLiuruJinge(new BigDecimal(zhuliSmallLiuruJinge));

             String zhuliSmallLiuruZhanbi = stocklist.get(i).split(",")[14];
             if (StringUtils.isBlank(zhuliSmallLiuruZhanbi) || "-".equals(zhuliSmallLiuruZhanbi)) {
                 zhuliSmallLiuruZhanbi = "0.000";
             }
             sharesInfoDo.setZhuliSmallLiuruZhanbi(new BigDecimal(zhuliSmallLiuruZhanbi));


             sharesInfoDo.setCountData(time);
             sharesInfoDo.setCreateTime(new Date());

             String code1 = shareCode.substring(0, 2);
             String response3 = null;
             if ("60".equals(code1)) {
                 String url3 = "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&sty=DCARQRQB&st=z&sr=&p=&ps=&token=1942f5da9b46b069953c873404aad4b5&cmd=" + shareCode + "1&cb=jsonpHqDatakhFcGKkB&_=" + new Date();
                 //String respons7 = HTTPUtils.getRawHtml(url3);
                 String html3=restTemplate.getForObject(url3,String.class);
                 //String html3 = respons7.toString();
                 String respons2 = html3.replace("jsonpHqDatakhFcGKkB", "");
                 response3 = respons2.replace("([", "").replace("])", "").replace("\"", "");
             } else {
                 String url2 = "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&sty=DCARQRQB&st=z&sr=&p=&ps=&token=1942f5da9b46b069953c873404aad4b5&cmd=" + shareCode + "2&cb=jsonpHqDataOtImUcey&_=" + new Date();
                // String respons6 = HTTPUtils.getRawHtml(url2);
                 String respons6=restTemplate.getForObject(url2,String.class);
                // String html4 = respons6.toString();
                 String respons9 = respons6.replace("jsonpHqDataOtImUcey", "");
                 response3 = respons9.replace("([", "").replace("])", "").replace("\"", "");
             }
             String[] response4 = response3.split(",");
             logger.warn(shareCode+response3);
             sharesInfoDo.setZhangDie(new BigDecimal(response4[4]));
             sharesInfoDo.setZongShou(Long.valueOf(response4[6]));
             sharesInfoDo.setDealMoney(Long.valueOf(response4[7]));
             sharesInfoDo.setZhenFu(new BigDecimal(response4[8]));
             sharesInfoDo.setLastDayPrice(new BigDecimal(response4[9]));
             sharesInfoDo.setStartPrice(new BigDecimal(response4[10]));
             sharesInfoDo.setMaxPrice(new BigDecimal(response4[11]));
             sharesInfoDo.setMinPrice(new BigDecimal(response4[12]));
             sharesInfoDo.setHuanShou(new BigDecimal(response4[13]));
             sharesInfoDo.setLiangBi(new BigDecimal(response4[14]));
             sharesInfoDos.add(sharesInfoDo);
         }


         try {
             sharesInfoDoMapper.insertList(sharesInfoDos);
         } catch (DuplicateKeyException e) {
             logger.error("shares_info 唯一约束失效！", e);
         }

     }


     public boolean checkHoliday(Calendar calendar) throws Exception {
         Date date = calendar.getTime();
         SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
         String dateToString = sdf.format(date);
         Integer result = restTemplate.getForObject(dataUrl + dateToString, Integer.class);
         if (null == result) {
             return true;
         }
         if (result == 0) {
             return false;
         }
         return true;
     }

     public PageInfo<SharesInfoResp> getSharesInfoList(SharesInfoReq req) {

         PageHelper.startPage(req.getPageIndex(), req.getPageSize());

         Page<SharesInfoResp> shopConfigListRespPageInfo = sharesInfoDoMapper.findSharesInfoByConList(req);

         PageInfo<SharesInfoResp> sharesInfoRespPageInfos = new PageInfo<>();
         sharesInfoRespPageInfos.setPageSize(req.getPageSize());
         sharesInfoRespPageInfos.setPageNum(req.getPageIndex());
         if (CollectionUtils.isEmpty(shopConfigListRespPageInfo.getResult())) {
             sharesInfoRespPageInfos.setTotal(0);
             sharesInfoRespPageInfos.setList(null);
         } else {
             sharesInfoRespPageInfos.setList(shopConfigListRespPageInfo.getResult());
             sharesInfoRespPageInfos.setTotal(shopConfigListRespPageInfo.getTotal());
         }

         return sharesInfoRespPageInfos;

     }



     public boolean Find(int target, int [][] array) {

         int row_length=array.length;

         int  column_length=array[0].length;

         int row;

         int column;

         for(row=0,column=column_length-1;column>=0&&row<row_length;){
             if(target==array[row][column]){
                 return true;
             }

             if(target>array[row][column]){
                 row++;
                 continue;
             }
             if(target<array[row][column]){
                 column--;
                 continue;
             }
         }

         return false;

     }
 }
