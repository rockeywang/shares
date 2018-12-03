 /*
  * Copyright 2018 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.wangchao.shares.vo;

 import com.wangchao.shares.base.BaseReq;
 import io.swagger.annotations.ApiModelProperty;
 import lombok.Getter;
 import lombok.Setter;

 import javax.validation.constraints.NotNull;
 import java.math.BigDecimal;


 /**
  * @author wangchao4
  * @date 2018/12/318:55
  */

 @Getter
 @Setter
 public class SharesInfoReq extends BaseReq {

     @ApiModelProperty("选择统计日期")
     @NotNull(message = "统计日期不能为空")
     private String countData;

     @ApiModelProperty("最低价格")
     private BigDecimal minPrice;

     @ApiModelProperty("最高价格")
     private BigDecimal maxPrice;

     @ApiModelProperty("股票编码")
     private String shareCode;

     @ApiModelProperty("股票名称")
     private String shareName;

     @ApiModelProperty("股票类型  000深圳  600上海  002中小板 300创业版")
     private Integer type;

     @ApiModelProperty("最低涨幅")
     private BigDecimal minAmountIncrease;

     @ApiModelProperty("最高涨幅")
     private BigDecimal maxAmountIncrease;


     @ApiModelProperty("分页起始位置")
     @NotNull(message = "分页起始位置不能为空")
     private Integer pageIndex;

     @ApiModelProperty("获取数量")
     @NotNull(message = "获取数量不能为空")
     private Integer pageSize;

 }
