package com.wso2.openbanking.accelerator.consent.mgt.service.listener;

import com.wso2.openbanking.accelerator.common.exception.ConsentManagementException;

import java.util.Map;

/**
 * Consent state change listener interface.
 */
public interface ConsentStateChangeListener {

    /**
     * This method is used to put events to OBEventQueue related to different consent state changes.
     *
     * @param consentID consent ID
     * @param userID user ID
     * @param newConsentStatus new consent status after state change
     * @param previousConsentStatus previous consent status
     * @param reason reason for changing consent state
     * @param clientId client ID
     * @param consentDataMap consent data map holding different consent related data
     * @throws ConsentManagementException thrown if an error occurs
     */
    public void onStateChange(String consentID, String userID, String newConsentStatus,
                              String previousConsentStatus, String reason, String clientId,
                              Map<String, Object> consentDataMap) throws ConsentManagementException;
}
