package com.wangchao.shares.vo;

import java.io.Serializable;
import java.util.Date;





public class WaybillBufferVo  implements Serializable{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** ID. */
    private Long id;

    /** 运单号. */
    private String billCode;

    /** 单号类型. */
    private Integer billCodeType;

    /** 号段ID. */
    private Long sectionId;

    /** 状态. */
    private Integer status;

    private int currentPage = 0;
	private int pageSize = 1000;

	private String remark;
	private Boolean deleteFlag;
	private Integer version;
	private String createEmp;
	private Integer createOrg;
	private Date createTime;
	private String updateEmp;
	private Integer updateOrg;
	private Date updateTime;
	

    public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getCreateEmp() {
		return createEmp;
	}

	public void setCreateEmp(String createEmp) {
		this.createEmp = createEmp;
	}

	public Integer getCreateOrg() {
		return createOrg;
	}

	public void setCreateOrg(Integer createOrg) {
		this.createOrg = createOrg;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateEmp() {
		return updateEmp;
	}

	public void setUpdateEmp(String updateEmp) {
		this.updateEmp = updateEmp;
	}

	public Integer getUpdateOrg() {
		return updateOrg;
	}

	public void setUpdateOrg(Integer updateOrg) {
		this.updateOrg = updateOrg;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/** 取得 ID. */
    public Long getId() {
        return this.id;
    }

    /** 设置 ID. */
    public void setId(Long id) {
        this.id = id;
    }
    /** 取得 运单号. */
    public String getBillCode() {
        return this.billCode;
    }

    /** 设置 运单号. */
    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }
    /** 取得 单号类型. */
    public Integer getBillCodeType() {
        return this.billCodeType;
    }

    /** 设置 单号类型. */
    public void setBillCodeType(Integer billCodeType) {
        this.billCodeType = billCodeType;
    }
    /** 取得 号段ID. */
    public Long getSectionId() {
        return this.sectionId;
    }

    /** 设置 号段ID. */
    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }
    /** 取得 状态. */
    public Integer getStatus() {
        return this.status;
    }

    /** 设置 状态. */
    public void setStatus(Integer status) {
        this.status = status;
    }


    public Long getPrimaryKey() {
        return this.id;
    }

    public void setPrimaryKey(Object pk) {
        this.id = (Long) pk;
    }
}
