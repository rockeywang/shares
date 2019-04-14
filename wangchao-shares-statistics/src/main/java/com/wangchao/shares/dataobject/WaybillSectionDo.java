package com.wangchao.shares.dataobject;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.beans.Transient;
import java.util.Date;

@Getter
@Setter
@Table(name="ubs_waybill_section")
public class WaybillSectionDo {


    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createTime;
    private Date updateTime;
    private String createEmp;
    private String updateEmp;


    /** 单号类型. */
    private Integer billCodeType;

    /** 单号长度. */
    private Integer billCodeLen;

    /** 起始单号. */
    private String startNo;

    /** 结束单号. */
    private String endNo;

    /** 数量. */
    private Long qty;

    /** 每次产生数量. */
    private Long perGenerateQty;

    /** 产生开始单号. */
    private String generateStartNo;

    /** 产生结束单号. */
    private String generateEndNo;

    /** 产生数量. */
    private Long generateQty;

    /** 已产生数量. */
    private Long generateSumQty;

    /** 是否使用完. */
    private Boolean finishFlag;

    /** 状态. */
    private Integer status;

    /** 创建机构. */
    private String createOrg;



}
