package com.wso2.openbanking.accelerator.consent.mgt.dao.models;

import com.wso2.openbanking.accelerator.common.util.Generated;

/**
 * Model for the Authorization resource.
 */
public class AuthorizationResource {

    private String authorizationID;
    private String consentID;
    private String userID;
    private String authorizationStatus;
    private String authorizationType;
    private long updatedTime;

    public AuthorizationResource() {

    }

    @Generated(message = "Excluding constructor because setter methods are explicitly called")
    public AuthorizationResource(String consentID, String userID, String authorizationStatus,
                                 String authorizationType, long updatedTime) {
        this.consentID = consentID;
        this.userID = userID;
        this.authorizationStatus = authorizationStatus;
        this.authorizationType = authorizationType;
        this.updatedTime = updatedTime;

    }
    public String getAuthorizationID() {

        return authorizationID;
    }

    public String getAuthorizationType() {

        return authorizationType;
    }

    public void setAuthorizationType(String authorizationType) {

        this.authorizationType = authorizationType;
    }

    public void setAuthorizationID(String authorizationID) {

        this.authorizationID = authorizationID;
    }

    public String getConsentID() {

        return consentID;
    }

    public void setConsentID(String consentID) {

        this.consentID = consentID;
    }

    public String getUserID() {

        return userID;
    }

    public void setUserID(String userID) {

        this.userID = userID;
    }

    public String getAuthorizationStatus() {

        return authorizationStatus;
    }

    public void setAuthorizationStatus(String authorizationStatus) {

        this.authorizationStatus = authorizationStatus;
    }

    public long getUpdatedTime() {

        return updatedTime;
    }

    public void setUpdatedTime(long updatedTime) {

        this.updatedTime = updatedTime;
    }
}
