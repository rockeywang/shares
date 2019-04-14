package com.wangchao.shares.util;

/**
 * 异常接口定义
 * author：zhangd
 * date：2017-03-01 09:30
 */
public interface IException {

    /**
     * 获取code
     * @return String
     */
    String getCode();

    /**
     * 获取异常信息
     * @return String
     */
    String getNativeMessage();

    /**
     * 设置异常参数
     * @param objects 异常参数
     */
    void setArguments(Object... objects);

    /**
     * 获取异常参数
     * @return 异常参数
     */
    Object[] getArguments();
}
