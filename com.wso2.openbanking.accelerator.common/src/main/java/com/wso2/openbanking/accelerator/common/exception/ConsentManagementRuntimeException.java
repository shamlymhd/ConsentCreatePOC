package com.wso2.openbanking.accelerator.common.exception;


/**
 * Used for runtime exceptions in consent management component.
 */
public class ConsentManagementRuntimeException extends OpenBankingRuntimeException {

    public ConsentManagementRuntimeException(String errorCode, Throwable cause) {

        super(errorCode, cause);
    }

    public ConsentManagementRuntimeException(String errorCode) {

        super(errorCode);
    }
}
