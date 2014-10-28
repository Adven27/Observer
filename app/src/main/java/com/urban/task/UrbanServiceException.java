package com.urban.task;

public class UrbanServiceException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 5294866345833807558L;

    private String detailMessage;

    public UrbanServiceException(String detailMessage) {
        super(detailMessage);
    }

}
