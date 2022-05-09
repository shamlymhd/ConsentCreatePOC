package com.wso2.openbanking.accelerator.consent.mgt.service.constants;


/**
 * Consent Core Service Constants.
 */
public class ConsentCoreServiceConstants {

    public static final String CONSENT_ATTRIBUTES_DELETE_ERROR_MSG = "Error occurred while deleting consent " +
            "attributes in the database";
    public static final String DATA_INSERTION_ROLLBACK_ERROR_MSG = "Error occurred while inserting data. Rolling back" +
            " the transaction";
    public static final String DATA_RETRIEVE_ERROR_MSG = "Error occurred while retrieving data";
    public static final String DATA_UPDATE_ROLLBACK_ERROR_MSG = "Error occurred while updating consent data. Rolling " +
            "back the transaction";
    public static final String DATA_DELETE_ERROR_MSG = "Error occurred while deleting data";
    public static final String DATA_DELETE_ROLLBACK_ERROR_MSG = "Error occurred while deleting consent data. Rolling " +
            "back the transaction";
    public static final String NEW_CONSENT_STATUS_OR_APPLICABLE_STATUS_MISSING_ERROR = "New consent status or " +
            "applicable status for file upload is missing. Cannot proceed";
    public static final String CREATE_EXCLUSIVE_CONSENT_MANDATORY_PARAMETER_MISSING_ERROR = "One or more of following" +
            " data are missing (Client ID, receipt, consent type, consent status, auth status, auth type, applicable " +
            "existing consent status, new existing consent status, new current consent status), cannot proceed";

    public static final String TRANSACTION_COMMITTED_LOG_MSG = "Transaction committed";
    public static final String DATABASE_CONNECTION_CLOSE_LOG_MSG = "Closing database connection";

    public static final String CONSENT_REVOKE_FROM_DASHBOARD_REASON = "Revoke the consent from dashboard";
    public static final String CONSENT_REVOKE_REASON = "Revoke the consent";
    public static final String CONSENT_FILE_UPLOAD_REASON = "Upload consent file";
    public static final String CREATE_CONSENT_REASON = "Create consent";
    public static final String CREATE_EXCLUSIVE_AUTHORIZATION_CONSENT_REASON = "Create exclusive authorization consent";
    public static final String USER_ACCOUNTS_BINDING_REASON = "Bind user accounts to consent";
    public static final String CONSENT_REAUTHORIZE_REASON = "Reauthorize consent";
    public static final String CONSENT_AMEND_REASON = "Amend consent";
    public static final String SUBMISSION_RECEIVED_REASON = "Receive submission request for the consent";

    public static final String ACTIVE_MAPPING_STATUS = "active";
    public static final String INACTIVE_MAPPING_STATUS = "inactive";
    public static final String CONSENT_AMENDED_STATUS = "amended";
}
