package com.wso2.openbanking.accelerator.consent.mgt.dao.models;

import com.wso2.openbanking.accelerator.common.util.Generated;

/**
 * Model for consent status audit record resource.
 */
public class ConsentStatusAuditRecord {

    private String statusAuditID;
    private String consentID;
    private String currentStatus;
    private long actionTime;
    private String reason;
    private String actionBy;
    private String previousStatus;

    public ConsentStatusAuditRecord() {

    }

    @Generated(message = "Excluding constructor because setter methods are explicitly called")
    public ConsentStatusAuditRecord(String consentID, String currentStatus, long actionTime,
                                    String reason, String actionBy, String previousStatus) {
        this.consentID = consentID;
        this.currentStatus = currentStatus;
        this.actionTime = actionTime;
        this.reason = reason;
        this.actionBy = actionBy;
        this.previousStatus = previousStatus;
    }

    public String getStatusAuditID() {

        return statusAuditID;
    }

    public void setStatusAuditID(String statusAuditID) {

        this.statusAuditID = statusAuditID;
    }

    public String getConsentID() {

        return consentID;
    }

    public void setConsentID(String consentID) {

        this.consentID = consentID;
    }

    public String getCurrentStatus() {

        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {

        this.currentStatus = currentStatus;
    }

    public long getActionTime() {

        return actionTime;
    }

    public void setActionTime(long actionTime) {

        this.actionTime = actionTime;
    }

    public String getReason() {

        return reason;
    }

    public void setReason(String reason) {

        this.reason = reason;
    }

    public String getActionBy() {

        return actionBy;
    }

    public void setActionBy(String actionBy) {

        this.actionBy = actionBy;
    }

    public String getPreviousStatus() {

        return previousStatus;
    }

    public void setPreviousStatus(String previousStatus) {

        this.previousStatus = previousStatus;
    }
}
