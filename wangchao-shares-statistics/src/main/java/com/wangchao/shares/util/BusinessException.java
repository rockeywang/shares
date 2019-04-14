package com.wangchao.shares.util;

public class BusinessException extends RuntimeException implements IException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4286015280221509266L;

    /**
     * 异常code
     */
    protected String code;

    /**
     * 异常信息
     */
    private String nativeMessage;

    /**
     * 异常 arguments
     */
    private Object[] arguments;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BusinessException(String code, String message, String nativeMessage) {
        super(message);
        this.code = code;
        this.nativeMessage = nativeMessage;
    }

    public BusinessException(String code, String message, String nativeMessage, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.nativeMessage = nativeMessage;
    }

    public BusinessException(String code, Object... arguments) {
        super();
        this.code = code;
        this.arguments = arguments;
    }

    public BusinessException(String code, String msg, Object... arguments) {
        super(msg);
        this.code = code;
        this.arguments = arguments;
    }

    @Override
    public void setArguments(Object... arguments) {
        this.arguments = arguments;
    }

    @Override
    public Object[] getArguments() {
        return this.arguments;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getNativeMessage() {
        return this.nativeMessage;
    }

}
