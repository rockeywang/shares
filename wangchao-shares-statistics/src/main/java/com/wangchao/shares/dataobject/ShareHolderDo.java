package com.wangchao.shares.dataobject;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name="share_holder")
public class ShareHolderDo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String shareName;

    private String shareCode;

    private String shareHolderName;

    private String shareHolderType;

    private BigDecimal shareHolderBaifenbi;

    private  Long shareHolderAmount;

    private String shareHolderChange;

    private BigDecimal changeBaifenbi;

    private String shareType;

    private Long sumAmountHolder;

    private Date countData;

    private Date createData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShareName() {
        return shareName;
    }

    public void setShareName(String shareName) {
        this.shareName = shareName;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public String getShareHolderName() {
        return shareHolderName;
    }

    public void setShareHolderName(String shareHolderName) {
        this.shareHolderName = shareHolderName;
    }

    public String getShareHolderType() {
        return shareHolderType;
    }

    public void setShareHolderType(String shareHolderType) {
        this.shareHolderType = shareHolderType;
    }

    public BigDecimal getShareHolderBaifenbi() {
        return shareHolderBaifenbi;
    }

    public void setShareHolderBaifenbi(BigDecimal shareHolderBaifenbi) {
        this.shareHolderBaifenbi = shareHolderBaifenbi;
    }

    public Long getShareHolderAmount() {
        return shareHolderAmount;
    }

    public void setShareHolderAmount(Long shareHolderAmount) {
        this.shareHolderAmount = shareHolderAmount;
    }

    public String getShareHolderChange() {
        return shareHolderChange;
    }

    public void setShareHolderChange(String shareHolderChange) {
        this.shareHolderChange = shareHolderChange;
    }

    public BigDecimal getChangeBaifenbi() {
        return changeBaifenbi;
    }

    public void setChangeBaifenbi(BigDecimal changeBaifenbi) {
        this.changeBaifenbi = changeBaifenbi;
    }

    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    public Long getSumAmountHolder() {
        return sumAmountHolder;
    }

    public void setSumAmountHolder(Long sumAmountHolder) {
        this.sumAmountHolder = sumAmountHolder;
    }

    public Date getCountData() {
        return countData;
    }

    public void setCountData(Date countData) {
        this.countData = countData;
    }

    public Date getCreateData() {
        return createData;
    }

    public void setCreateData(Date createData) {
        this.createData = createData;
    }
}
