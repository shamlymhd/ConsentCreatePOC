package com.wso2.openbanking.accelerator.consent.mgt.service.constants.impl;


//import com.wso2.openbanking.accelerator.common.event.executor.OBEventQueue;
//import com.wso2.openbanking.accelerator.common.event.executor.model.OBEvent;
import com.wso2.openbanking.accelerator.common.exception.ConsentManagementException;
//import com.wso2.openbanking.accelerator.consent.mgt.service.internal.ConsentManagementDataHolder;
import com.wso2.openbanking.accelerator.consent.mgt.service.listener.ConsentStateChangeListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Consent state change listener implementation.
 */
public class ConsentStateChangeListenerImpl implements ConsentStateChangeListener {

    private static volatile ConsentStateChangeListenerImpl instance;

    private ConsentStateChangeListenerImpl() {

    }

    public static ConsentStateChangeListenerImpl getInstance() {

        if (instance == null) {
            synchronized (ConsentStateChangeListenerImpl.class) {
                if (instance == null) {
                    instance = new ConsentStateChangeListenerImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void onStateChange(String consentID, String userID, String newConsentStatus, String previousConsentStatus,
                              String reason, String clientId, Map<String, Object> consentDataMap)
            throws ConsentManagementException {

//        OBEventQueue obEventQueue = ConsentManagementDataHolder.getInstance().getOBEventQueue();
//
//        Map<String, Object> eventData = new HashMap<>();
//        eventData.put("ConsentId", consentID);
//        eventData.put("UserId", userID);
//        eventData.put("PreviousConsentStatus", previousConsentStatus);
//        eventData.put("Reason", reason);
//        eventData.put("ClientId", clientId);
//        eventData.put("ConsentDataMap", consentDataMap);
//
//        obEventQueue.put(new OBEvent(newConsentStatus, eventData));

    }
}
