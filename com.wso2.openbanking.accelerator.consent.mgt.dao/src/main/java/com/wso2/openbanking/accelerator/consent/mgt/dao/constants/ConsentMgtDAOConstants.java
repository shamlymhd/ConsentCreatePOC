package com.wso2.openbanking.accelerator.consent.mgt.dao.constants;


/**
 * This class contains all the constants needed for the consent management DAO layer.
 */
public class ConsentMgtDAOConstants {

    public static final String CONSENT_ID = "CONSENT_ID";
    public static final String CONSENT_FILE = "CONSENT_FILE";
    public static final String ATT_KEY = "ATT_KEY";
    public static final String ATT_VALUE = "ATT_VALUE";
    public static final String RECEIPT = "RECEIPT";
    public static final String CREATED_TIME = "CREATED_TIME";
    public static final String CONSENT_CREATED_TIME = "CONSENT_CREATED_TIME";
    public static final String CONSENT_UPDATED_TIME = "CONSENT_UPDATED_TIME";
    public static final String CLIENT_ID = "CLIENT_ID";
    public static final String CONSENT_TYPE = "CONSENT_TYPE";
    public static final String CURRENT_STATUS = "CURRENT_STATUS";
    public static final String CONSENT_FREQUENCY = "CONSENT_FREQUENCY";
    public static final String VALIDITY_TIME = "VALIDITY_TIME";
    public static final String RECURRING_INDICATOR = "RECURRING_INDICATOR";
    public static final String AUTH_ID = "AUTH_ID";
    public static final String AUTH_TYPE = "AUTH_TYPE";
    public static final String USER_ID = "USER_ID";
    public static final String AUTH_STATUS = "AUTH_STATUS";
    public static final String UPDATED_TIME = "UPDATED_TIME";
    public static final String AUTH_UPDATED_TIME = "AUTH_UPDATED_TIME";
    public static final String MAPPING_ID = "MAPPING_ID";
    public static final String ACCOUNT_ID = "ACCOUNT_ID";
    public static final String PERMISSION = "ACCOUNT_ID";
    public static final String MAPPING_STATUS = "MAPPING_STATUS";
    public static final String ACTION_BY = "ACTION_BY";
    public static final String ACTION_TIME = "ACTION_TIME";
    public static final String STATUS_AUDIT_ID = "STATUS_AUDIT_ID";
    public static final String PREVIOUS_STATUS = "PREVIOUS_STATUS";
    public static final String REASON = "REASON";
    public static final String CONSENT_IDS = "consentIDs";
    public static final String CLIENT_IDS = "clientIDs";
    public static final String CONSENT_TYPES = "consentTypes";
    public static final String CONSENT_STATUSES = "consentStatuses";
    public static final String USER_IDS = "userIDs";
    public static final String IN = "inOperator";
    public static final String AND = "andOperator";
    public static final String WHERE = "where";
    public static final String PLACEHOLDER = "placeholder";
    public static final String EQUALS = "equals";
    public static final String CONSENT_EXPIRY_TIME_ATTRIBUTE = "ExpirationDateTime";
    public static final String AUTH_MAPPING_ID = "AUTH_MAPPING_ID";

    public static final String CONSENT_MAPPING_RETRIEVE_ERROR_MSG = "Error occurred while retrieving consent mapping " +
            "resources from the database";
    public static final String CONSENT_ATTRIBUTES_RETRIEVE_ERROR_MSG = "Error occurred while retrieving consent " +
            "attributes from the database for the given consent ID and attribute keys";
    public static final String NO_RECORDS_FOUND_ERROR_MSG = "No records are found for the given inputs";
    public static final String CONSENT_RESOURCE_STORE_ERROR_MSG = "Error occurred while storing consent resource in " +
            "the database";
    public static final String AUTHORIZATION_RESOURCE_STORE_ERROR_MSG = "Error occurred while storing authorization " +
            "resource in the database";
    public static final String CONSENT_MAPPING_RESOURCE_STORE_ERROR_MSG = "Error occurred while storing consent " +
            "mapping resource in the database";
    public static final String AUDIT_RECORD_STORE_ERROR_MSG = "Error occurred while storing consent status audit " +
            "record in the database";
    public static final String CONSENT_ATTRIBUTES_STORE_ERROR_MSG = "Error occurred while storing consent attributes " +
            "in the database";
    public static final String CONSENT_FILE_STORE_ERROR_MSG = "Error occurred while storing consent file resource in " +
            "the database";
    public static final String CONSENT_STATUS_UPDATE_ERROR_MSG = "Error occurred while updating consent status in the" +
            " database";
    public static final String CONSENT_MAPPING_STATUS_UPDATE_ERROR_MSG = "Error occurred while updating consent " +
            "mapping status in the database";
    public static final String CONSENT_AUTHORIZATION_STATUS_UPDATE_ERROR_MSG = "Error occurred while updating " +
            "authorization status in the database";
    public static final String CONSENT_AUTHORIZATION_USER_UPDATE_ERROR_MSG = "Error occurred while updating " +
            "authorization user in the database";
    public static final String CONSENT_FILE_RETRIEVE_ERROR_MSG = "Error occurred while retrieving consent file " +
            "resource from the database";
    public static final String CONSENT_RESOURCE_RETRIEVE_ERROR_MSG = "Error occurred while retrieving consent " +
            "resource from the database";
    public static final String DETAILED_CONSENT_RESOURCE_RETRIEVE_ERROR_MSG = "Error occurred while retrieving " +
            "detailed consent resource from the database";
    public static final String CONSENT_AUTHORIZATION_RESOURCE_RETRIEVE_ERROR_MSG = "Error occurred while retrieving " +
            "consent authorization resource from the database";
    public static final String AUDIT_RECORDS_RETRIEVE_ERROR_MSG = "Error occurred while retrieving consent status " +
            "audit records";
    public static final String CONSENT_ATTRIBUTES_DELETE_ERROR_MSG = "Error occurred while deleting consent " +
            "attributes in the database";
    public static final String CONSENT_SEARCH_ERROR_MSG = "Error occurred while searching consents";
    public static final String CONSENT_ID_RETRIEVE_ERROR_MSG = "Error occurred while retrieving consent id from the " +
            "database for the given attribute key and attribute value";
}
