package com.wso2.openbanking.accelerator.consent.mgt.dao.models;


import com.wso2.openbanking.accelerator.common.util.Generated;

/**
 * Model for consent mapping resource.
 */
public class ConsentMappingResource {

    private String mappingID;
    private String authorizationID;
    private String accountID;
    private String permission;
    private String mappingStatus;

    public ConsentMappingResource() {

    }

    @Generated(message = "Excluding constructor because setter methods are explicitly called")
    public ConsentMappingResource(String authorizationID, String accountID, String permission,
                                  String mappingStatus) {
        this.authorizationID = authorizationID;
        this.accountID = accountID;
        this.permission = permission;
        this.mappingStatus = mappingStatus;
    }

    public String getMappingID() {

        return mappingID;
    }

    public void setMappingID(String mappingID) {

        this.mappingID = mappingID;
    }

    public String getAuthorizationID() {

        return authorizationID;
    }

    public void setAuthorizationID(String authorizationID) {

        this.authorizationID = authorizationID;
    }

    public String getAccountID() {

        return accountID;
    }

    public void setAccountID(String accountID) {

        this.accountID = accountID;
    }

    public String getPermission() {

        return permission;
    }

    public void setPermission(String permission) {

        this.permission = permission;
    }

    public String getMappingStatus() {

        return mappingStatus;
    }

    public void setMappingStatus(String mappingStatus) {

        this.mappingStatus = mappingStatus;
    }
}
