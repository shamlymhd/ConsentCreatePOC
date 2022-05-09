package com.wso2.openbanking.accelerator.consent.mgt.dao.exceptions;

import com.wso2.openbanking.accelerator.common.exception.OpenBankingException;

/**
 * OBConsentDataInsertionException.
 */
public class OBConsentDataInsertionException extends OpenBankingException {

    public OBConsentDataInsertionException(String message) {
        super(message);
    }

    public OBConsentDataInsertionException(String message, Throwable e) {
        super(message, e);
    }
}
