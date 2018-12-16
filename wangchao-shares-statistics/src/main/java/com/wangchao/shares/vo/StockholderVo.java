package com.wangchao.shares.vo;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;


@Setter
@Getter
public class StockholderVo implements Serializable {

    @JSONField(name="COMPANYCODE")
    private String COMPANYCODE;

    @JSONField(name="SSNAME")
    private String SSNAME;

    @JSONField(name="SHAREHDNAME")
    private String SHAREHDNAME;

    @JSONField(name="SHAREHDTYPE")
    private String SHAREHDTYPE;

    @JSONField(name="SHARESTYPE")
    private String SHARESTYPE;

    @JSONField(name="RANK")
    private Integer RANK;

    @JSONField(name="SCODE")
    private String SCODE;

    @JSONField(name="SNAME")
    private String SNAME;

    @JSONField(name="RDATE")
    private String RDATE;

    @JSONField(name="SHAREHDNUM")
    private Long SHAREHDNUM;

    @JSONField(name="LTAG")
    private BigDecimal LTAG;

    @JSONField(name="ZB")
    private BigDecimal ZB;

    @JSONField(name="NDATE")
    private String NDATE;

    @JSONField(name="BZ")
    private String BZ;

    @JSONField(name="BDBL")
    private String BDBL;

    @JSONField(name="SHAREHDCODE")
    private String SHAREHDCODE;

    @JSONField(name="SHAREHDRATIO")
    private BigDecimal SHAREHDRATIO;

    @JSONField(name="BDSUM")
    private String BDSUM;



}
