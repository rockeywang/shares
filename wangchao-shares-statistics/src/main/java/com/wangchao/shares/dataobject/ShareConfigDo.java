package com.wangchao.shares.dataobject;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name="shares_config")
public class ShareConfigDo  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ConfigKey;

    private String ConfigValue;

    private Date createTime;

    private Integer isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfigKey() {
        return ConfigKey;
    }

    public void setConfigKey(String configKey) {
        ConfigKey = configKey;
    }

    public String getConfigValue() {
        return ConfigValue;
    }

    public void setConfigValue(String configValue) {
        ConfigValue = configValue;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
