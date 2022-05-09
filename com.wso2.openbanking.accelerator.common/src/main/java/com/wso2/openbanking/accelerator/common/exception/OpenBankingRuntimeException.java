package com.wso2.openbanking.accelerator.common.exception;

/**
 * Used for creating runtime exceptions for Open-banking modules.
 */
public class OpenBankingRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -5686395831712095972L;
    private String errorCode;

    public OpenBankingRuntimeException(String errorCode, Throwable cause) {

        super(cause);
        this.errorCode = errorCode;
    }

    public OpenBankingRuntimeException(String errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public String getErrorCode() {

        return errorCode;
    }

    public void setErrorCode(String errorCode) {

        this.errorCode = errorCode;
    }

}
