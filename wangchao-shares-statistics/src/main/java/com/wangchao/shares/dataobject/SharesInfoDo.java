 /*
  * Copyright 2018 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.wangchao.shares.dataobject;


 import lombok.Getter;
 import lombok.Setter;

 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.Table;
 import java.math.BigDecimal;
 import java.util.Date;

 /**
  * @author wangchao4
  * @date 2018/12/310:33
  */

 @Table(name = "shares_info")
 public class SharesInfoDo {


     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;


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


     private Date countData;

     private Date createTime;

     private BigDecimal huanShou;

     private Long zongShou;

     private Long dealMoney;


     private BigDecimal zhenFu;

     private BigDecimal lastDayPrice;

     private BigDecimal startPrice;

     private BigDecimal maxPrice;

     private BigDecimal minPrice;

     private BigDecimal liangBi;

     private BigDecimal zhangDie;

     public Long getId() {
         return id;
     }

     public void setId(Long id) {
         this.id = id;
     }

     public String getShareCode() {
         return shareCode;
     }

     public void setShareCode(String shareCode) {
         this.shareCode = shareCode;
     }

     public String getShareName() {
         return shareName;
     }

     public void setShareName(String shareName) {
         this.shareName = shareName;
     }

     public BigDecimal getNewPrice() {
         return newPrice;
     }

     public void setNewPrice(BigDecimal newPrice) {
         this.newPrice = newPrice;
     }

     public BigDecimal getAmountIncrease() {
         return amountIncrease;
     }

     public void setAmountIncrease(BigDecimal amountIncrease) {
         this.amountIncrease = amountIncrease;
     }

     public BigDecimal getZhuliLiuruJinge() {
         return zhuliLiuruJinge;
     }

     public void setZhuliLiuruJinge(BigDecimal zhuliLiuruJinge) {
         this.zhuliLiuruJinge = zhuliLiuruJinge;
     }

     public BigDecimal getZhuliLiuruBaifenbi() {
         return zhuliLiuruBaifenbi;
     }

     public void setZhuliLiuruBaifenbi(BigDecimal zhuliLiuruBaifenbi) {
         this.zhuliLiuruBaifenbi = zhuliLiuruBaifenbi;
     }

     public BigDecimal getZhuliBigdanLiuruJinge() {
         return zhuliBigdanLiuruJinge;
     }

     public void setZhuliBigdanLiuruJinge(BigDecimal zhuliBigdanLiuruJinge) {
         this.zhuliBigdanLiuruJinge = zhuliBigdanLiuruJinge;
     }

     public BigDecimal getZhuliBigdanLiuruZhanbi() {
         return zhuliBigdanLiuruZhanbi;
     }

     public void setZhuliBigdanLiuruZhanbi(BigDecimal zhuliBigdanLiuruZhanbi) {
         this.zhuliBigdanLiuruZhanbi = zhuliBigdanLiuruZhanbi;
     }

     public BigDecimal getZhuliliuruJinge() {
         return zhuliliuruJinge;
     }

     public void setZhuliliuruJinge(BigDecimal zhuliliuruJinge) {
         this.zhuliliuruJinge = zhuliliuruJinge;
     }

     public BigDecimal getZhuliliuruZhanbi() {
         return zhuliliuruZhanbi;
     }

     public void setZhuliliuruZhanbi(BigDecimal zhuliliuruZhanbi) {
         this.zhuliliuruZhanbi = zhuliliuruZhanbi;
     }

     public BigDecimal getZhuliMiddleLiuruJinge() {
         return zhuliMiddleLiuruJinge;
     }

     public void setZhuliMiddleLiuruJinge(BigDecimal zhuliMiddleLiuruJinge) {
         this.zhuliMiddleLiuruJinge = zhuliMiddleLiuruJinge;
     }

     public BigDecimal getZhuliMiddleLiuruZhanbi() {
         return zhuliMiddleLiuruZhanbi;
     }

     public void setZhuliMiddleLiuruZhanbi(BigDecimal zhuliMiddleLiuruZhanbi) {
         this.zhuliMiddleLiuruZhanbi = zhuliMiddleLiuruZhanbi;
     }

     public BigDecimal getZhuliSmallLiuruJinge() {
         return zhuliSmallLiuruJinge;
     }

     public void setZhuliSmallLiuruJinge(BigDecimal zhuliSmallLiuruJinge) {
         this.zhuliSmallLiuruJinge = zhuliSmallLiuruJinge;
     }

     public BigDecimal getZhuliSmallLiuruZhanbi() {
         return zhuliSmallLiuruZhanbi;
     }

     public void setZhuliSmallLiuruZhanbi(BigDecimal zhuliSmallLiuruZhanbi) {
         this.zhuliSmallLiuruZhanbi = zhuliSmallLiuruZhanbi;
     }

     public Date getCountData() {
         return countData;
     }

     public void setCountData(Date countData) {
         this.countData = countData;
     }

     public Date getCreateTime() {
         return createTime;
     }

     public void setCreateTime(Date createTime) {
         this.createTime = createTime;
     }

     public BigDecimal getHuanShou() {
         return huanShou;
     }

     public void setHuanShou(BigDecimal huanShou) {
         this.huanShou = huanShou;
     }

     public Long getZongShou() {
         return zongShou;
     }

     public void setZongShou(Long zongShou) {
         this.zongShou = zongShou;
     }

     public Long getDealMoney() {
         return dealMoney;
     }

     public void setDealMoney(Long dealMoney) {
         this.dealMoney = dealMoney;
     }

     public BigDecimal getZhenFu() {
         return zhenFu;
     }

     public void setZhenFu(BigDecimal zhenFu) {
         this.zhenFu = zhenFu;
     }

     public BigDecimal getLastDayPrice() {
         return lastDayPrice;
     }

     public void setLastDayPrice(BigDecimal lastDayPrice) {
         this.lastDayPrice = lastDayPrice;
     }

     public BigDecimal getStartPrice() {
         return startPrice;
     }

     public void setStartPrice(BigDecimal startPrice) {
         this.startPrice = startPrice;
     }

     public BigDecimal getMaxPrice() {
         return maxPrice;
     }

     public void setMaxPrice(BigDecimal maxPrice) {
         this.maxPrice = maxPrice;
     }

     public BigDecimal getMinPrice() {
         return minPrice;
     }

     public void setMinPrice(BigDecimal minPrice) {
         this.minPrice = minPrice;
     }

     public BigDecimal getLiangBi() {
         return liangBi;
     }

     public void setLiangBi(BigDecimal liangBi) {
         this.liangBi = liangBi;
     }

     public BigDecimal getZhangDie() {
         return zhangDie;
     }

     public void setZhangDie(BigDecimal zhangDie) {
         this.zhangDie = zhangDie;
     }
 }
