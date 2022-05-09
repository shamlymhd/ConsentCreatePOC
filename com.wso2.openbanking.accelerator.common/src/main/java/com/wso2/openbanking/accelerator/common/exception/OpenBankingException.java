package com.wso2.openbanking.accelerator.common.exception;

/**
 * Used for exceptions in Open Banking components.
 */
public class OpenBankingException extends Exception {

    public OpenBankingException(String message) {
        super(message);
    }

    public OpenBankingException(String message, Throwable e) {
        super(message, e);
    }

}
