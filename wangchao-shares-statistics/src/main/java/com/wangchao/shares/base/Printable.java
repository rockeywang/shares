package com.wangchao.shares.base;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @Auther: wangchang
 * @Date: 2018/10/30 14:52
 * @Description:
 */
public class Printable implements Serializable {


    private static final long serialVersionUID = 462540746001308536L;

    public Printable() {
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    public String buildMDCKey() {
        return "";
    }
}
