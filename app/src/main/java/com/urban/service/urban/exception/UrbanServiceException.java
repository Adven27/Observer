package com.urban.service.urban.exception;

import com.urban.data.ResponseError;

public class UrbanServiceException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 5294866345833807558L;

    private String detailMessage;

    public UrbanServiceException(String detailMessage) {
        super(detailMessage);
    }

    public  UrbanServiceException(ResponseError error) {
        super(error.toString());
    }

}
