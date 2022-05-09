package com.wso2.openbanking.accelerator.consent.mgt.dao.models;

import com.wso2.openbanking.accelerator.common.util.Generated;

import java.util.Map;

/**
 * Model for consent attributes.
 */
public class ConsentAttributes {

    private String consentID;
    private Map<String, String> consentAttributes;

    public ConsentAttributes(){

    }

    @Generated(message = "Excluding constructor because setter methods are explicitly called")
    public ConsentAttributes(String consentID, Map<String, String> consentAttributes) {
        this.consentID = consentID;
        this.consentAttributes = consentAttributes;
    }

    public String getConsentID() {

        return consentID;
    }

    public void setConsentID(String consentID) {

        this.consentID = consentID;
    }

    public Map<String, String> getConsentAttributes() {

        return consentAttributes;
    }

    public void setConsentAttributes(Map<String, String> consentAttributes) {

        this.consentAttributes = consentAttributes;
    }
}
