package com.wso2.openbanking.accelerator.consent.mgt.dao;


import com.wso2.openbanking.accelerator.consent.mgt.dao.exceptions.OBConsentDataDeletionException;
import com.wso2.openbanking.accelerator.consent.mgt.dao.exceptions.OBConsentDataInsertionException;
import com.wso2.openbanking.accelerator.consent.mgt.dao.exceptions.OBConsentDataRetrievalException;
import com.wso2.openbanking.accelerator.consent.mgt.dao.exceptions.OBConsentDataUpdationException;
import com.wso2.openbanking.accelerator.consent.mgt.dao.models.AuthorizationResource;
import com.wso2.openbanking.accelerator.consent.mgt.dao.models.ConsentAttributes;
//import com.wso2.openbanking.accelerator.consent.mgt.dao.models.ConsentFile;
//import com.wso2.openbanking.accelerator.consent.mgt.dao.models.ConsentMappingResource;
import com.wso2.openbanking.accelerator.consent.mgt.dao.models.ConsentResource;
import com.wso2.openbanking.accelerator.consent.mgt.dao.models.ConsentStatusAuditRecord;
import com.wso2.openbanking.accelerator.consent.mgt.dao.models.DetailedConsentResource;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Map;

/**
 * This interface access the data storage layer to retrieve, store, delete and update consent management related
 * resources.
 */
public interface ConsentCoreDAO {

    /**
     * This method is used to store the consent resource in the database. The request consent resource object must
     * contain all data in it without the consent ID. A consent ID will be generated and set to the response object
     * if the insertion is successful.
     *
     * @param connection connection object
     * @param consentResource consent resource with all required data
     * @return returns the consent resource with the consent ID and created time if insertion is successful
     * @throws OBConsentDataInsertionException thrown if a database error occur or an insertion failure
     */
    ConsentResource storeConsentResource(Connection connection, ConsentResource consentResource)
            throws OBConsentDataInsertionException;

    /**
     * This method is used to store the authorization resource in the database. The request authorization resource
     * object must contain data all in it without the authorization ID and the updated time. Both of them will be
     * generated and set to the response object if the insertion is successful.
     *
     * @param connection connection object
     * @param authorizationResource authorization resource with all required data
     * @return returns the authorization resource with the updated time if insertion is successful
     * @throws OBConsentDataInsertionException thrown if a database error occur or an insertion failure
     */
    AuthorizationResource storeAuthorizationResource(Connection connection, AuthorizationResource authorizationResource)
            throws OBConsentDataInsertionException;

//    /**
//     * This method is used to store the consent mapping resource in the database. The request consent mapping object
//     * must contain all the data in it without the consent mapping ID. It will be generated and set to the response
//     * object if the insertion is successful.
//     *
//     * @param connection connection object
//     * @param consentMappingResource consent mapping resource with all required data
//     * @return returns the consent mapping resource if the insertion is successful
//     * @throws OBConsentDataInsertionException thrown if a database error occur or an insertion failure
//     */
//    ConsentMappingResource storeConsentMappingResource(Connection connection,
//                                                       ConsentMappingResource consentMappingResource)
//            throws OBConsentDataInsertionException;
//
//    /**
//     * This method is used to store the consent status audit record in the database. The request consent status audit
//     * record object must contain all the data in it without the status audit ID and actionTime. They will be generated
//     * and set to the response object if the insertion is successful.
//     *
//     * @param connection connection object
//     * @param consentStatusAuditRecord consent status audit record with all required data
//     * @return returns the consent status audit record if the insertion is successful
//     * @throws OBConsentDataInsertionException thrown if a database error occur or an insertion failure
//     */
    ConsentStatusAuditRecord storeConsentStatusAuditRecord(Connection connection,
                                                           ConsentStatusAuditRecord consentStatusAuditRecord)
            throws OBConsentDataInsertionException;

    /**
     * This method is used to store the consent attributes in the database. The request consent attributes object
     * must be set with a consent ID and consent attribute map.
     *
     * @param connection connection object
     * @param consentAttributes consent attributes object with consent ID and attributes map
     * @return returns true if insertion is successful
     * @throws OBConsentDataInsertionException thrown if a database error occur or an insertion failure
     */
    boolean storeConsentAttributes(Connection connection, ConsentAttributes consentAttributes)
            throws OBConsentDataInsertionException;

//    /**
//     * This method is used to store the consent file in the database. The request consent file object must be set
//     * with a consent ID and the file that needed to be stored.
//     *
//     * @param connection connection object
//     * @param consentFileResource consent file resource with consent ID and the file content
//     * @return returns true if insertion is successful
//     * @throws OBConsentDataInsertionException thrown if a database error occur or an insertion failure
//     */
//    boolean storeConsentFile(Connection connection, ConsentFile consentFileResource)
//            throws OBConsentDataInsertionException;
//
//    /**
//     * This method is used to update the status of a consent resource. The request consent resource object must be
//     * set with a consent ID and the new consent status.
//     *
//     * @param connection connection object
//     * @param consentID consent ID of the consent needed to be updated
//     * @param consentStatus the new status that should be updated with
//     * @return returns the consent resource with the consent ID and the new status
//     * @throws OBConsentDataUpdationException thrown if a database error occur or an update failure
//     */
//    ConsentResource updateConsentStatus(Connection connection, String consentID, String consentStatus)
//            throws OBConsentDataUpdationException;
//
//    /**
//     * This method is used to update given consent mapping resources. All the mapping resources of provided mapping
//     * IDs will be updated with the new mapping status provided.
//     *
//     * @param connection connection object
//     * @param mappingIDs a list of mapping IDs that needed to be updated
//     * @param mappingStatus the new mapping status that should be updated with
//     * @return returns true if the update is successful
//     * @throws OBConsentDataUpdationException thrown if a database error occur or an update failure
//     */
//    boolean updateConsentMappingStatus(Connection connection, ArrayList<String> mappingIDs,
//                                       String mappingStatus)
//            throws OBConsentDataUpdationException;
//
//    /**
//     * This method is used to update a given authorization object. The status of the authorization resource provided
//     * will be updated with the new status.
//     *
//     * @param connection connection object
//     * @param authorizationID authorization ID of the resource needed to be updated
//     * @param newAuthorizationStatus the new authorization status that should be updated with
//     * @return returns authorization resource with authorizationID, new status and updated time
//     * @throws OBConsentDataUpdationException thrown if a database error occur or an update failure
//     */
//    AuthorizationResource updateAuthorizationStatus(Connection connection, String authorizationID,
//                                                    String newAuthorizationStatus)
//            throws OBConsentDataUpdationException;
//
//    /**
//     * This method is used for updating the user of a given authorization resource. The user ID of the authorization
//     * resource provided will be updated with the new user ID.
//     *
//     * @param connection connection object
//     * @param authorizationID authorization ID of the resource needed to be updated
//     * @param userID the new user ID that should be updated with
//     * @return returns authorization resource with authorization ID, user ID and updated time
//     * @throws OBConsentDataUpdationException thrown if a database error occur or an update failure
//     */
//    AuthorizationResource updateAuthorizationUser(Connection connection, String authorizationID, String userID)
//            throws OBConsentDataUpdationException;
//
//    /**
//     * This method is used to retrieve the consent file from the database.
//     *
//     * @param connection connection object
//     * @param consentID consent ID of the file needed to be retrieved
//     * @return returns the requested consent file resource
//     * @throws OBConsentDataRetrievalException thrown if a database error occur or an retrieval failure
//     */
//    ConsentFile getConsentFile(Connection connection, String consentID) throws OBConsentDataRetrievalException;
//
//    /**
//     * This method is used to retrieve the consent attributes from the database for given attribute keys.
//     *
//     * @param connection connection object
//     * @param consentID consent ID
//     * @param consentAttributeKeys the keys of the consent attributes that need to be retrieved
//     * @return returns the consent attributes that matches the provided consentID and consent attribute keys
//     * @throws OBConsentDataRetrievalException thrown if a database error occurs
//     */
//    ConsentAttributes getConsentAttributes(Connection connection, String consentID,
//                                           ArrayList<String> consentAttributeKeys)
//            throws OBConsentDataRetrievalException;
//
//    /**
//     * This method is used to retrieve all the consent attributes from the database for the given consent ID.
//     *
//     * @param connection connection object
//     * @param consentID consent ID
//     * @return returns the consent attributes that matches the provided consentID and consent attribute keys
//     * @throws OBConsentDataRetrievalException thrown if a database error occurs
//     */
//    ConsentAttributes getConsentAttributes(Connection connection, String consentID)
//            throws OBConsentDataRetrievalException;
//
//    /**
//     * This method is used to retrieve consent attribute using the attribute name.
//     *
//     * @param connection connection object
//     * @param attributeName attribute name
//     * @return a map with the conesnt ID and the related attribute value
//     * @throws OBConsentDataRetrievalException
//     */
//    Map<String, String> getConsentAttributesByName(Connection connection, String attributeName)
//            throws OBConsentDataRetrievalException;
//
//    /**
//     * This method is used to retrieve consent id using the attribute name and value.
//     *
//     * @param connection connection object
//     * @param attributeName attribute name
//     * @param attributeValue attribute value
//     * @return Consent ID related to the given attribute key and value
//     * @throws OBConsentDataRetrievalException
//     */
//    ArrayList<String> getConsentIdByConsentAttributeNameAndValue(Connection connection, String attributeName,
//                                                                 String attributeValue)
//            throws OBConsentDataRetrievalException;
//
//    /**
//     * This method is used to retrieve a consent resource for the provided consent ID (without associated consent
//     * attributes).
//     *
//     * @param connection connection object
//     * @param consentID consent ID
//     * @return returns the consent resource related to the provided consent ID with additional consent attributes or
//     * not
//     * @throws OBConsentDataRetrievalException thrown if a database error occurs
//     */
//    ConsentResource getConsentResource(Connection connection, String consentID)
//            throws OBConsentDataRetrievalException;
//
//    /**
//     * This method is used to retrieve a detailed consent resource for the provided consent ID (includes
//     * authorization resources, account mapping resources and consent attributes).
//     *
//     * @param connection connection object
//     * @param consentID consent ID
//     * @return returns a detailed consent resource related to the provided consent ID
//     * @throws OBConsentDataRetrievalException thrown if a database error occurs
//     */
//    DetailedConsentResource getDetailedConsentResource(Connection connection, String consentID)
//            throws OBConsentDataRetrievalException;
//
//    /**
//     * This method is used to retrieve a consent resource for the provided consent ID with associated attributes.
//     *
//     * @param connection connection object
//     * @param consentID consent ID
//     * @return returns the consent resource related to the provided consent ID with additional consent attributes or
//     * not
//     * @throws OBConsentDataRetrievalException thrown if a database error occurs
//     */
//    ConsentResource getConsentResourceWithAttributes(Connection connection, String consentID)
//            throws OBConsentDataRetrievalException;
//
//    /**
//     * This method is used to retrieve an authorization resource for the provided authorization ID.
//     *
//     * @param connection connection object
//     * @param authorizationID authorization ID
//     * @return the relevant authorization resource
//     * @throws OBConsentDataRetrievalException thrown if a database error occurs
//     */
//    AuthorizationResource getAuthorizationResource(Connection connection, String authorizationID)
//            throws OBConsentDataRetrievalException;
//
//    /**
//     * This method is used to retrieve consent status audit records. It queries the consent audit records by the
//     * parameter that is provided. All parameters are optional. If no parameters are provided, all the records will
//     * be queried.
//     *
//     * @param connection connection object
//     * @param consentID consent ID
//     * @param currentStatus current status of the consent
//     * @param actionBy the user who performed the status update
//     * @param fromTime lower bound of the time of the needed records
//     * @param toTime upper bound of the time of the needed records
//     * @param statusAuditID status audit ID
//     * @return a list of retrieved audit records that matches the provided parameters
//     * @throws OBConsentDataRetrievalException thrown if a database error occurs
//     */
//    ArrayList<ConsentStatusAuditRecord> getConsentStatusAuditRecords(Connection connection, String consentID,
//                                                                     String currentStatus, String actionBy,
//                                                                     Long fromTime, Long toTime,
//                                                                     String statusAuditID)
//            throws OBConsentDataRetrievalException;
//
//    /**
//     * This method is used to retrieve consent mapping resources for a given authorization ID.
//     *
//     * @param connection connection object
//     * @param authorizationID authorization ID
//     * @return a list of all consent mapping resources for the given authorization ID
//     * @throws OBConsentDataRetrievalException thrown if a database error occurs
//     */
//    ArrayList<ConsentMappingResource> getConsentMappingResources(Connection connection, String authorizationID)
//            throws OBConsentDataRetrievalException;
//
//    /**
//     * This method is used to retrieve consent mapping resources for a given authorization ID and mapping status.
//     *
//     * @param connection connection object
//     * @param authorizationID authorization ID
//     * @param mappingStatus mapping status
//     * @return a list of all consent mapping resources for the given authorization ID
//     * @throws OBConsentDataRetrievalException thrown if a database error occurs
//     */
//    ArrayList<ConsentMappingResource> getConsentMappingResources(Connection connection, String authorizationID,
//                                                                 String mappingStatus)
//            throws OBConsentDataRetrievalException;
//
//    /**
//     * This method is used to delete a given list of consent attributes.
//     *
//     * @param connection connection object
//     * @param consentID consent ID
//     * @param consentAttributeKeys a list of attribute keys that should be deleted
//     * @return true if the deletion is successful
//     * @throws OBConsentDataDeletionException thrown if a database error occurs
//     */
//    boolean deleteConsentAttributes(Connection connection, String consentID, ArrayList<String> consentAttributeKeys)
//            throws OBConsentDataDeletionException;
//
//    /**
//     * This method is used to search detailed consents for the given lists of parameters. The search will be
//     * performed according to the provided input. Any list can contain any number of elements. The conjunctive result
//     * will be returned. If all lists are passed as null, all the consents related to other search parameters will be
//     * returned. "fromTime" and "toTime" are also optional. "limit" and "offset" are optional combined. If all
//     * parameters are null, all the consents will be returned.
//     *
//     * @param connection connection object
//     * @param consentIDs consent IDs optional list
//     * @param clientIDs client IDs optional list
//     * @param consentTypes consent types optional list
//     * @param consentStatuses consent statuses optional list
//     * @param userIDs user IDs optional list
//     * @param fromTime from time
//     * @param toTime to time
//     * @param limit limit
//     * @param offset offset
//     * @return a list of detailed consent resources according to the provided parameters or the list of all consents
//     * if all parameters are null
//     * @throws OBConsentDataRetrievalException thrown if any error occur
//     */
    ArrayList<DetailedConsentResource> searchConsents(Connection connection, ArrayList<String> consentIDs,
                                                      ArrayList<String> clientIDs, ArrayList<String> consentTypes,
                                                      ArrayList<String> consentStatuses, ArrayList<String> userIDs,
                                                      Long fromTime, Long toTime, Integer limit, Integer offset)
            throws OBConsentDataRetrievalException;

//    /**
//     * This method is used to search authorization resources using following optional parameters. If all the input
//     * parameters are null, all the relevant authorization resources will be returned.
//     *
//     * 1. Consent ID
//     * 2. User ID
//     *
//     * @param connection connection object
//     * @param consentID consent ID (optional)
//     * @param userID user ID (optional)
//     * @return a list of authorization resources
//     * @throws OBConsentDataRetrievalException thrown if an error occurs in the process
//     */
//    ArrayList<AuthorizationResource> searchConsentAuthorizations(Connection connection, String consentID, String userID)
//            throws OBConsentDataRetrievalException;
//
//    /**
//     * This method is used to update consent receipt.
//     *
//     * @param connection connection object
//     * @param consentID ID of the consent to be amended
//     * @param consentReceipt new consent receipt
//     * @throws OBConsentDataUpdationException thrown if an error occur in the process
//     */
//    boolean updateConsentReceipt(Connection connection, String consentID, String consentReceipt)
//            throws OBConsentDataUpdationException;
//
//    /**
//     * This method is used to update consent validity time.
//     *
//     * @param connection connection object
//     * @param consentID consent ID
//     * @param validityTime new validity time
//     * @return true if update successful
//     * @throws OBConsentDataUpdationException thrown if any error occurs in the process
//     */
//    boolean updateConsentValidityTime(Connection connection, String consentID, long validityTime)
//            throws OBConsentDataUpdationException;
//
//    /**
//     * This method is used to fetch consents which has a expiring time as a consent attribute
//     * (eligible for expiration).
//     * @throws OBConsentDataRetrievalException thrown if any error occurs in the process
//     */
//    ArrayList<DetailedConsentResource> getExpiringConsents(Connection connection,
//                                                           String statusesEligibleForExpiration)
//            throws OBConsentDataRetrievalException;
}
