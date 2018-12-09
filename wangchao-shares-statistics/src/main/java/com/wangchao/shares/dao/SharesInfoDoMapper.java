 /*
  * Copyright 2018 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.wangchao.shares.dao;

 import com.github.pagehelper.Page;
 import com.wangchao.shares.base.BaseMapper;
 import com.wangchao.shares.dataobject.SharesInfoDo;
 import com.wangchao.shares.vo.SharesInfoReq;
 import com.wangchao.shares.vo.SharesInfoResp;
 import com.wangchao.shares.vo.SumSharePriceDateVo;
 import org.apache.ibatis.annotations.Param;

 import java.util.List;


 /**
  * @author wangchao4
  * @date 2018/12/310:32
  */
 public interface SharesInfoDoMapper extends BaseMapper<SharesInfoDo> {


     String getNewLastTime();


     Page<SharesInfoResp> findSharesInfoByConList(SharesInfoReq req);


     List<SumSharePriceDateVo> countSumByPriceAndType(SharesInfoReq req);

     int countSumShaInfos(@Param("dateTime") String dateTime);


     List<String> findShareCodes();
 }
