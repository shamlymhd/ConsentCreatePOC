package com.wso2.openbanking.accelerator.consent.mgt.dao.exceptions;


import com.wso2.openbanking.accelerator.common.exception.OpenBankingException;

/**
 * OBConsentDataUpdationException.
 */
public class OBConsentDataUpdationException extends OpenBankingException {

    public OBConsentDataUpdationException(String message) {
        super(message);
    }

    public OBConsentDataUpdationException(String message, Throwable e) {
        super(message, e);
    }
}
