package com.urban.data;

public class ResponseError {

    private String errorText;
    private int errorCode;

    public ResponseError(int code, String text) {
        this.errorCode = code;
        this.errorText = text;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

}
