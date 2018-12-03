package com.wangchao.shares.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @Auther: wangchang
 * @Date: 2018/10/30 14:51
 * @Description:
 */
public class BaseResp<T> extends Printable implements Serializable {

    private static final Logger log              = LoggerFactory.getLogger(BaseResp.class);
    private static final long serialVersionUID = -2519005379002657800L;
    /**
     * 返回code
     */
    private Integer             code       = 10000;                                  //默认成功

    /**
     * 返回code 描述
     */
    private String              message;

    private T                   data;

    public static <E extends BaseResp> E buildFailResp(String msg, Class<E> clazz) {

        return buildBaseResp(ReturnCodeEnum.FAIL.getCode(), msg, clazz);
    }

    public static <E extends BaseResp> E buildFailResp(BaseEnum code, String msg, Class<E> clazz) {

        return buildBaseResp(code.getCode(), String.format("%s,%s", code.getDesc(), msg), clazz);
    }

    public static <E extends BaseResp> E buildFailResp(ReturnCodeEnum code, String msg, Class<E> clazz) {

        return buildBaseResp(code.getCode(), String.format("%s,%s", code.getDesc(), msg), clazz);
    }

    public static <E extends BaseResp> E buildFailResp(BaseException t, Class<E> clazz) {

        return buildBaseResp(t.getReturnCode(), t.getReturnMsg(), clazz);
    }

    public static <E extends BaseResp> E buildBaseResp(int returnCode, String returnMsg, Class<E> clazz) {
        E e = null;
        try {
            e = clazz.newInstance();
            e.setCode(returnCode);
            e.setMessage(returnMsg);
        } catch (InstantiationException e1) {
            log.warn("", e1.getMessage());
        } catch (IllegalAccessException e1) {
            log.warn("", e1.getMessage());
        }
        return e;
    }

    public void fail(Throwable t) {
        if (t instanceof BaseException) {
            BaseException e = (BaseException) t;
            this.setCode(e.getReturnCode());
            this.setMessage(e.getReturnMsg());
        } else {
            this.setCode(ReturnCodeEnum.FAIL.getCode());
            this.setMessage(ReturnCodeEnum.FAIL.getDesc());
        }
    }

    /**
     * 构建成功结果
     *
     * @return
     */
    public static <E extends BaseResp> E buildSuccessResp(Class<E> clazz) {
        return buildBaseResp(ReturnCodeEnum.SUCCESS.getCode(), ReturnCodeEnum.SUCCESS.getDesc(), clazz);
    }

    /**
     * 构建执行失败结果
     *
     * @param t
     * @param funcName
     * @param clazz
     * @param <E>
     * @return
     */
    public static <E extends BaseResp> E buildFailResp(Throwable t, String funcName, Class<E> clazz) {
        E e = null;

        if (t instanceof IllegalArgumentException) {
            e = buildFailResp(ReturnCodeEnum.FAIL, "系统异常", clazz);
        } else if(t instanceof BaseException){
            e = buildFailResp((BaseException) t, clazz);
        } else if (t instanceof BaseException) {
            e = buildFailResp((BaseException) t, clazz);
        } else {
            e = buildFailResp(String.format("%s,%s", funcName, t.getMessage()), clazz);
        }
        return e;
    }

    /**
     * 执行是否成功
     *
     * @return
     */
    @JsonIgnore
    public boolean isSuccess() {
        return ReturnCodeEnum.SUCCESS.getCode() == code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public BaseResp setData(T data) {
        this.data = data;
        return this;
    }
}

