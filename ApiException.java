package com.smz.lexunuser.base.net;

public class ApiException extends RuntimeException {
    private int mErrorCode;

    public ApiException(int errorCode, String errorMessage, Object result) {
        super(errorMessage);

        mErrorCode = errorCode;
    }

    /**
     * 判断是否是token失效
     *
     * @return 失效返回true, 否则返回false;
     */

}