package com.wso2.openbanking.accelerator.consent.mgt.dao.exceptions;

import com.wso2.openbanking.accelerator.common.exception.OpenBankingException;

/**
 * OBConsentDataRetrievalException.
 */
public class OBConsentDataRetrievalException extends OpenBankingException {

    public OBConsentDataRetrievalException(String message) {
        super(message);
    }

    public OBConsentDataRetrievalException(String message, Throwable e) {
        super(message, e);
    }
}
