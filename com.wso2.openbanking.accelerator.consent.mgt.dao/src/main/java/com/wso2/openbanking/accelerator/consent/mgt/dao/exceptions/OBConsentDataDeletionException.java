package com.wso2.openbanking.accelerator.consent.mgt.dao.exceptions;

import com.wso2.openbanking.accelerator.common.exception.OpenBankingException;

/**
 * OBConsentDataDeletionException.
 */
public class OBConsentDataDeletionException extends OpenBankingException {

    public OBConsentDataDeletionException(String message) {
        super(message);
    }

    public OBConsentDataDeletionException(String message, Throwable e) {
        super(message, e);
    }
}
