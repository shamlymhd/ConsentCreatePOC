package com.wso2.openbanking.accelerator.common.exception;

/**
 * Used for handling exceptions in consent management component.
 */
public class ConsentManagementException extends OpenBankingException {

    public ConsentManagementException(String message) {
        super(message);
    }

    public ConsentManagementException(String message, Throwable e) {
        super(message, e);
    }
}

