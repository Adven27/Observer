package com.urban.data;

public class ResponseError {

    private String errorText;
    private int errorCode;

    public ResponseError(int code, String text) {
        this.errorCode = code;
        this.errorText = text;
    }

    private ResponseError() {}

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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Service response error has occurred.");
        builder.append(" ErrorCode: ")
                .append(errorCode)
                .append(", errorText: ")
                .append(errorText);

        return builder.toString();
    }
}
