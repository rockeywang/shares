package com.wangchao.shares.base;

/**
 * @Auther: wangchang
 * @Date: 2018/10/30 14:48
 * @Description:
 */
public class BaseException extends RuntimeException {


    private static final long serialVersionUID = -1306396914227081121L;
    private int               returnCode;
    private String            returnMsg;

    public BaseException() {
    }

    public BaseException(int code, String msg) {
        this.returnCode = code;
        this.returnMsg = msg;
    }

    public BaseException(BaseEnum code, String msg) {
        this.returnCode = code.getCode();
        this.returnMsg = msg;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }
}
