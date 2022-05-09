package com.wso2.openbanking.accelerator.consent.mgt.dao.models;


import com.wso2.openbanking.accelerator.common.util.Generated;

import java.util.Map;

/**
 * Model for the consent resource.
 */
public class ConsentResource {

    private String consentID;
    private String clientID;
    private String receipt;
    private String consentType;
    private int consentFrequency;
    private long validityPeriod;
    private boolean recurringIndicator;
    private String currentStatus;
    private long createdTime;
    private long updatedTime;

    public ConsentResource() {
    }

    public ConsentResource(String clientID, String receipt, String consentType, String currentStatus) {
        this.clientID = clientID;
        this.receipt = receipt;
        this.consentType = consentType;
        this.currentStatus = currentStatus;
    }

    @Generated(message = "Excluding constructor because setter methods are explicitly called")
    public ConsentResource(String consentID, String clientID, String receipt, String consentType,
                           int consentFrequency, long validityPeriod, boolean recurringIndicator,
                           String currentStatus, long createdTime, long updatedTime) {
        this.consentID = consentID;
        this.clientID = clientID;
        this.receipt = receipt;
        this.consentType = consentType;
        this.consentFrequency = consentFrequency;
        this.validityPeriod = validityPeriod;
        this.recurringIndicator = recurringIndicator;
        this.currentStatus = currentStatus;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    private Map<String, String> consentAttributes;

    public long getUpdatedTime() {

        return updatedTime;
    }

    public void setUpdatedTime(long updatedTime) {

        this.updatedTime = updatedTime;
    }

    public Map<String, String> getConsentAttributes() {

        return consentAttributes;
    }

    public void setConsentAttributes(Map<String, String> consentAttributes) {

        this.consentAttributes = consentAttributes;
    }

    public String getConsentID() {

        return consentID;
    }

    public void setConsentID(String consentID) {

        this.consentID = consentID;
    }

    public String getClientID() {

        return clientID;
    }

    public void setClientID(String clientID) {

        this.clientID = clientID;
    }

    public String getReceipt() {

        return receipt;
    }

    public void setReceipt(String receipt) {

        this.receipt = receipt;
    }

    public String getConsentType() {

        return consentType;
    }

    public void setConsentType(String consentType) {

        this.consentType = consentType;
    }

    public int getConsentFrequency() {

        return consentFrequency;
    }

    public void setConsentFrequency(int consentFrequency) {

        this.consentFrequency = consentFrequency;
    }

    public long getValidityPeriod() {

        return validityPeriod;
    }

    public void setValidityPeriod(long validityPeriod) {

        this.validityPeriod = validityPeriod;
    }

    public boolean isRecurringIndicator() {

        return recurringIndicator;
    }

    public void setRecurringIndicator(boolean recurringIndicator) {

        this.recurringIndicator = recurringIndicator;
    }

    public String getCurrentStatus() {

        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {

        this.currentStatus = currentStatus;
    }

    public long getCreatedTime() {

        return createdTime;
    }

    public void setCreatedTime(long createdTime) {

        this.createdTime = createdTime;
    }
}
