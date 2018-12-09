package com.wangchao.shares.vo;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;


@Setter
@Getter
public class StockholderVo implements Serializable {

    private String COMPANYCODE;

    private String SSNAME;

    private String SHAREHDNAME;

    private String SHAREHDTYPE;

    private String RANK;

    private String SCODE;

    private String SNAME;

    private String RDATE;

    private Long SHAREHDNUM;

    private BigDecimal LTAG;

    private BigDecimal ZB;

    private String NDATE;

    private String BZ;

    private String BDBL;

    private String SHAREHDCODE;

    private BigDecimal SHAREHDRATIO;

    private String BDSUM;



}
