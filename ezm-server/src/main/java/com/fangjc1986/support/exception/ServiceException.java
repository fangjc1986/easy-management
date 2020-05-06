package com.fangjc1986.support.exception;


public class ServiceException extends RuntimeException {

    private int code;

    public ServiceException(String msg) {
        super(msg);
        this.code = 100;
    }

    public ServiceException(String msg, int code) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
