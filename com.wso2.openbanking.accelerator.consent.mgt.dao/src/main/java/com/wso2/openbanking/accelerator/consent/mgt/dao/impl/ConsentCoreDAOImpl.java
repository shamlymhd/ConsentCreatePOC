package com.wso2.openbanking.accelerator.consent.mgt.dao.impl;


import com.wso2.openbanking.accelerator.consent.mgt.dao.ConsentCoreDAO;
import com.wso2.openbanking.accelerator.consent.mgt.dao.constants.ConsentMgtDAOConstants;
import com.wso2.openbanking.accelerator.consent.mgt.dao.exceptions.OBConsentDataDeletionException;
import com.wso2.openbanking.accelerator.consent.mgt.dao.exceptions.OBConsentDataInsertionException;
import com.wso2.openbanking.accelerator.consent.mgt.dao.exceptions.OBConsentDataRetrievalException;
import com.wso2.openbanking.accelerator.consent.mgt.dao.exceptions.OBConsentDataUpdationException;
import com.wso2.openbanking.accelerator.consent.mgt.dao.models.AuthorizationResource;
import com.wso2.openbanking.accelerator.consent.mgt.dao.models.ConsentAttributes;
//import com.wso2.openbanking.accelerator.consent.mgt.dao.models.ConsentFile;
import com.wso2.openbanking.accelerator.consent.mgt.dao.models.ConsentMappingResource;
import com.wso2.openbanking.accelerator.consent.mgt.dao.models.ConsentResource;
import com.wso2.openbanking.accelerator.consent.mgt.dao.models.ConsentStatusAuditRecord;
import com.wso2.openbanking.accelerator.consent.mgt.dao.models.DetailedConsentResource;
import com.wso2.openbanking.accelerator.consent.mgt.dao.queries.ConsentMgtCommonDBQueries;

import com.wso2.openbanking.accelerator.consent.mgt.dao.utils.ConsentDAOUtils;
//import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class only implements the data access methods for the consent management accelerator. It implements all the
 * methods defined in the ConsentCoreDAO interface and is only responsible for reading and writing data from/to the
 * database. The incoming data are pre-validated in the upper service layer. Therefore, no validations are done in
 * this layer.
 */
public class ConsentCoreDAOImpl implements ConsentCoreDAO {

    private static Log log = LogFactory.getLog(ConsentCoreDAOImpl.class);
    private static final String GROUP_BY_SEPARATOR = "\\|\\|";
    ConsentMgtCommonDBQueries sqlStatements;
    static final Map<String, String> COLUMNS_MAP = new HashMap<String, String>() {
        {
            put(ConsentMgtDAOConstants.CONSENT_IDS, "CONSENT_ID");
            put(ConsentMgtDAOConstants.CLIENT_IDS, "CLIENT_ID");
            put(ConsentMgtDAOConstants.CONSENT_TYPES, "CONSENT_TYPE");
            put(ConsentMgtDAOConstants.CONSENT_STATUSES, "CURRENT_STATUS");
            put(ConsentMgtDAOConstants.USER_IDS, "OCAR.USER_ID");
        }
    };

    public ConsentCoreDAOImpl(ConsentMgtCommonDBQueries sqlStatements) {

        this.sqlStatements = sqlStatements;
    }

    @Override
    public ConsentResource storeConsentResource(Connection connection, ConsentResource consentResource)
            throws OBConsentDataInsertionException {

        int result;
        String consentID = "";
        if (StringUtils.isEmpty(consentResource.getConsentID())) {
            consentID = UUID.randomUUID().toString();
        } else {
            consentID = consentResource.getConsentID();
        }
        // Unix time in seconds
        long createdTime = System.currentTimeMillis() / 1000;
        String storeConsentPrepStatement = sqlStatements.getStoreConsentPreparedStatement();

//        try (PreparedStatement storeConsentPreparedStmt = connection.prepareStatement(storeConsentPrepStatement)) {
//
//            log.debug("Setting parameters to prepared statement to store consent resource");
//
//            storeConsentPreparedStmt.setString(1, consentID);
//            storeConsentPreparedStmt.setString(2, consentResource.getReceipt());
//            storeConsentPreparedStmt.setLong(3, createdTime);
//            storeConsentPreparedStmt.setLong(4, createdTime);
//            storeConsentPreparedStmt.setString(5, consentResource.getClientID());
//            storeConsentPreparedStmt.setString(6, consentResource.getConsentType());
//            storeConsentPreparedStmt.setString(7, consentResource.getCurrentStatus());
//            storeConsentPreparedStmt.setLong(8, consentResource.getConsentFrequency());
//            storeConsentPreparedStmt.setLong(9, consentResource.getValidityPeriod());
//            storeConsentPreparedStmt.setBoolean(10, consentResource.isRecurringIndicator());
//
//            // with result, we can determine whether the insertion was successful or not
//            result = storeConsentPreparedStmt.executeUpdate();
//        } catch (SQLException e) {
//            log.error(ConsentMgtDAOConstants.CONSENT_RESOURCE_STORE_ERROR_MSG, e);
//            throw new OBConsentDataInsertionException(ConsentMgtDAOConstants.CONSENT_RESOURCE_STORE_ERROR_MSG, e);
//        }

        // Confirm that the data are inserted successfully
        if (true) {
            log.debug("Stored the consent resource successfully");
            consentResource.setConsentID(consentID);
            consentResource.setCreatedTime(createdTime);
            consentResource.setUpdatedTime(createdTime);
            return consentResource;
        } else {
            throw new OBConsentDataInsertionException("Failed to store consent data properly.");
        }
    }

    @Override
    public AuthorizationResource storeAuthorizationResource(Connection connection,
                                                            AuthorizationResource authorizationResource)
            throws OBConsentDataInsertionException {

        int result;
        String authorizationID = UUID.randomUUID().toString();

        // Unix time in seconds
        long updatedTime = System.currentTimeMillis() / 1000;
        String storeAuthorizationPrepStatement = sqlStatements.getStoreAuthorizationPreparedStatement();

        try (PreparedStatement storeAuthorizationPreparedStmt =
                     connection.prepareStatement(storeAuthorizationPrepStatement)) {

            log.debug("Setting parameters to prepared statement to store authorization resource");

            storeAuthorizationPreparedStmt.setString(1, authorizationID);
            storeAuthorizationPreparedStmt.setString(2, authorizationResource.getConsentID());
            storeAuthorizationPreparedStmt.setString(3, authorizationResource.getAuthorizationType());
            storeAuthorizationPreparedStmt.setString(4, authorizationResource.getUserID());
            storeAuthorizationPreparedStmt.setString(5, authorizationResource.getAuthorizationStatus());
            storeAuthorizationPreparedStmt.setLong(6, updatedTime);

            // with result, we can determine whether the insertion was successful or not
            result = storeAuthorizationPreparedStmt.executeUpdate();
        } catch (SQLException e) {
            log.error(ConsentMgtDAOConstants.AUTHORIZATION_RESOURCE_STORE_ERROR_MSG, e);
            throw new OBConsentDataInsertionException(ConsentMgtDAOConstants.AUTHORIZATION_RESOURCE_STORE_ERROR_MSG, e);
        }

        // Confirm that the data are inserted successfully
        if (result > 0) {
            log.debug("Stored the authorization resource successfully");
            authorizationResource.setAuthorizationID(authorizationID);
            authorizationResource.setUpdatedTime(updatedTime);
            return authorizationResource;
        } else {
            throw new OBConsentDataInsertionException("Failed to store authorization resource data properly.");
        }
    }

//    @Override
//    public ConsentMappingResource storeConsentMappingResource(Connection connection,
//                                                              ConsentMappingResource consentMappingResource)
//            throws OBConsentDataInsertionException {
//
//        int result;
//        String consentMappingID = UUID.randomUUID().toString();
//
//        String storeConsentMappingPrepStatement = sqlStatements.getStoreConsentMappingPreparedStatement();
//
//        try (PreparedStatement storeConsentMappingPreparedStmt =
//                     connection.prepareStatement(storeConsentMappingPrepStatement)) {
//
//            log.debug("Setting parameters to prepared statement to store consent mapping resource");
//
//            storeConsentMappingPreparedStmt.setString(1, consentMappingID);
//            storeConsentMappingPreparedStmt.setString(2, consentMappingResource.getAuthorizationID());
//            storeConsentMappingPreparedStmt.setString(3, consentMappingResource.getAccountID());
//            storeConsentMappingPreparedStmt.setString(4, consentMappingResource.getPermission());
//            storeConsentMappingPreparedStmt.setString(5, consentMappingResource.getMappingStatus());
//
//            // with result, we can determine whether the insertion was successful or not
//            result = storeConsentMappingPreparedStmt.executeUpdate();
//        } catch (SQLException e) {
//            log.error(ConsentMgtDAOConstants.CONSENT_MAPPING_RESOURCE_STORE_ERROR_MSG, e);
//            throw new OBConsentDataInsertionException(ConsentMgtDAOConstants.CONSENT_MAPPING_RESOURCE_STORE_ERROR_MSG,
//                    e);
//        }
//
//        // Confirm that the data are inserted successfully
//        if (result > 0) {
//            log.debug("Stored the consent mapping resource successfully");
//            consentMappingResource.setMappingID(consentMappingID);
//            return consentMappingResource;
//        } else {
//            throw new OBConsentDataInsertionException("Failed to store consent mapping resource data properly.");
//        }
//    }

    @Override
    public ConsentStatusAuditRecord storeConsentStatusAuditRecord(Connection connection,
                                                                  ConsentStatusAuditRecord consentStatusAuditRecord)
            throws OBConsentDataInsertionException {

        int result;
        String statusAuditID = UUID.randomUUID().toString();

        // Unix time in seconds
        long actionTime = System.currentTimeMillis() / 1000;
        String storeConsentStatusAuditRecordPrepStatement =
                sqlStatements.getStoreConsentStatusAuditRecordPreparedStatement();

        try (PreparedStatement storeConsentStatusAuditRecordPreparedStmt =
                     connection.prepareStatement(storeConsentStatusAuditRecordPrepStatement)) {

            log.debug("Setting parameters to prepared statement to store consent audit record");

            storeConsentStatusAuditRecordPreparedStmt.setString(1, statusAuditID);
            storeConsentStatusAuditRecordPreparedStmt.setString(2, consentStatusAuditRecord
                    .getConsentID());
            storeConsentStatusAuditRecordPreparedStmt.setString(3, consentStatusAuditRecord
                    .getCurrentStatus());
            storeConsentStatusAuditRecordPreparedStmt.setLong(4, actionTime);
            storeConsentStatusAuditRecordPreparedStmt.setString(5, consentStatusAuditRecord.getReason());
            storeConsentStatusAuditRecordPreparedStmt.setString(6, consentStatusAuditRecord
                    .getActionBy());
            storeConsentStatusAuditRecordPreparedStmt.setString(7, consentStatusAuditRecord
                    .getPreviousStatus());

            // with result, we can determine whether the insertion was successful or not
            result = storeConsentStatusAuditRecordPreparedStmt.executeUpdate();
        } catch (SQLException e) {
            log.error(ConsentMgtDAOConstants.AUDIT_RECORD_STORE_ERROR_MSG, e);
            throw new OBConsentDataInsertionException(ConsentMgtDAOConstants.AUDIT_RECORD_STORE_ERROR_MSG, e);
        }

        // Confirm that the data are inserted successfully
        if (result > 0) {
            log.debug("Stored the consent status audit record successfully");
            consentStatusAuditRecord.setStatusAuditID(statusAuditID);
            consentStatusAuditRecord.setActionTime(actionTime);
            return consentStatusAuditRecord;
        } else {
            throw new OBConsentDataInsertionException("Failed to store consent status audit record data properly.");
        }
    }

    @Override
    public boolean storeConsentAttributes(Connection connection, ConsentAttributes consentAttributes)
            throws OBConsentDataInsertionException {

        int[] result;
        String storeConsentAttributesPrepStatement = sqlStatements.getStoreConsentAttributesPreparedStatement();
        Map<String, String> consentAttributesMap = consentAttributes.getConsentAttributes();

        try (PreparedStatement storeConsentAttributesPreparedStmt =
                     connection.prepareStatement(storeConsentAttributesPrepStatement)) {

            for (Map.Entry<String, String> entry : consentAttributesMap.entrySet()) {
                storeConsentAttributesPreparedStmt.setString(1, consentAttributes.getConsentID());
                storeConsentAttributesPreparedStmt.setString(2, entry.getKey());
                storeConsentAttributesPreparedStmt.setString(3, entry.getValue());
                storeConsentAttributesPreparedStmt.addBatch();
            }

            // with result, we can determine whether the updating was successful or not
            result = storeConsentAttributesPreparedStmt.executeBatch();
        } catch (SQLException e) {
            log.error(ConsentMgtDAOConstants.CONSENT_ATTRIBUTES_STORE_ERROR_MSG, e);
            throw new OBConsentDataInsertionException(ConsentMgtDAOConstants.CONSENT_ATTRIBUTES_STORE_ERROR_MSG, e);
        }

        /*
           An empty array or an array with value -3 means the batch execution is failed.
           If an array contains value -2, it means the command completed successfully but the number of rows affected
           are unknown. Therefore, only checking for the existence of -3.
        */
        if (result.length != 0 && IntStream.of(result).noneMatch(value -> value == -3)) {
            log.debug("Stored the consent attributes successfully");
            return true;
        } else {
            throw new OBConsentDataInsertionException("Failed to store consent attribute data properly.");
        }
    }

//    @Override
//    public boolean storeConsentFile(Connection connection, ConsentFile consentFileResource)
//            throws OBConsentDataInsertionException {
//
//        int result;
//        String storeConsentMappingPrepStatement = sqlStatements.getStoreConsentFilePreparedStatement();
//
//        try (PreparedStatement storeConsentFilePreparedStmt =
//                     connection.prepareStatement(storeConsentMappingPrepStatement)) {
//
//            log.debug("Setting parameters to prepared statement to store consent file resource");
//
//            storeConsentFilePreparedStmt.setString(1, consentFileResource.getConsentID());
//            storeConsentFilePreparedStmt.setString(2, consentFileResource.getConsentFile());
//
//            // with result, we can determine whether the insertion was successful or not
//            result = storeConsentFilePreparedStmt.executeUpdate();
//        } catch (SQLException e) {
//            log.error(ConsentMgtDAOConstants.CONSENT_FILE_STORE_ERROR_MSG, e);
//            throw new OBConsentDataInsertionException(ConsentMgtDAOConstants.CONSENT_FILE_STORE_ERROR_MSG, e);
//        }
//
//        // Confirm that the data are inserted successfully
//        if (result > 0) {
//            log.debug("Stored the consent file resource successfully");
//            return true;
//        } else {
//            throw new OBConsentDataInsertionException("Failed to store consent file resource data properly.");
//        }
//    }
//
//    @Override
//    public ConsentResource updateConsentStatus(Connection connection, String consentID, String newConsentStatus)
//            throws OBConsentDataUpdationException {
//
//        int result;
//        long updatedTime = System.currentTimeMillis() / 1000;
//        ConsentResource consentResource = new ConsentResource();
//        String updateConsentStatusPrepStatement = sqlStatements.getUpdateConsentStatusPreparedStatement();
//
//        try (PreparedStatement updateConsentStatusPreparedStmt =
//                     connection.prepareStatement(updateConsentStatusPrepStatement)) {
//
//            log.debug("Setting parameters to prepared statement to update consent status");
//
//            updateConsentStatusPreparedStmt.setString(1, newConsentStatus);
//            updateConsentStatusPreparedStmt.setLong(2, updatedTime);
//            updateConsentStatusPreparedStmt.setString(3, consentID);
//
//            // with result, we can determine whether the updating was successful or not
//            result = updateConsentStatusPreparedStmt.executeUpdate();
//        } catch (SQLException e) {
//            log.error(ConsentMgtDAOConstants.CONSENT_STATUS_UPDATE_ERROR_MSG, e);
//            throw new OBConsentDataUpdationException(ConsentMgtDAOConstants.CONSENT_STATUS_UPDATE_ERROR_MSG, e);
//        }
//
//        // Confirm that the data are updated successfully
//        if (result > 0) {
//            log.debug("Updated the consent status successfully");
//            consentResource.setConsentID(consentID);
//            consentResource.setCurrentStatus(newConsentStatus);
//            return consentResource;
//        } else {
//            throw new OBConsentDataUpdationException("Failed to update consent status properly.");
//        }
//    }
//
//    @Override
//    public boolean updateConsentMappingStatus(Connection connection, ArrayList<String> mappingIDs, String mappingStatus)
//            throws OBConsentDataUpdationException {
//
//        int[] result;
//        String updateConsentMappingStatusPrepStatement = sqlStatements.getUpdateConsentMappingStatusPreparedStatement();
//
//        try (PreparedStatement updateConsentMappingStatusPreparedStmt =
//                     connection.prepareStatement(updateConsentMappingStatusPrepStatement)) {
//
//            log.debug("Setting parameters to prepared statement to update consent mapping status");
//
//            for (String mappingID : mappingIDs) {
//                updateConsentMappingStatusPreparedStmt.setString(1, mappingStatus);
//                updateConsentMappingStatusPreparedStmt.setString(2, mappingID);
//                updateConsentMappingStatusPreparedStmt.addBatch();
//            }
//
//            // with result, we can determine whether the updating was successful or not
//            result = updateConsentMappingStatusPreparedStmt.executeBatch();
//        } catch (SQLException e) {
//            log.error(ConsentMgtDAOConstants.CONSENT_MAPPING_STATUS_UPDATE_ERROR_MSG, e);
//            throw new OBConsentDataUpdationException(ConsentMgtDAOConstants.CONSENT_MAPPING_STATUS_UPDATE_ERROR_MSG, e);
//        }
//
//        // An empty array or an array with value -3 means the batch execution is failed
//        if (result.length != 0 && IntStream.of(result).noneMatch(value -> value == -3)) {
//            log.debug("Updated the consent mapping statuses of matching records successfully");
//            return true;
//        } else {
//            throw new OBConsentDataUpdationException("Failed to update consent mapping status properly.");
//        }
//    }
//
//    @Override
//    public AuthorizationResource updateAuthorizationStatus(Connection connection, String authorizationID,
//                                                           String newAuthorizationStatus)
//            throws OBConsentDataUpdationException {
//
//        int result;
//
//        // Unix time in seconds
//        long updatedTime = System.currentTimeMillis() / 1000;
//        String updateAuthorizationStatusPrepStatement = sqlStatements.getUpdateAuthorizationStatusPreparedStatement();
//
//        try (PreparedStatement updateAuthorizationStatusPreparedStmt =
//                     connection.prepareStatement(updateAuthorizationStatusPrepStatement)) {
//
//            log.debug("Setting parameters to prepared statement to update authorization status");
//
//            updateAuthorizationStatusPreparedStmt.setString(1, newAuthorizationStatus);
//            updateAuthorizationStatusPreparedStmt.setLong(2, updatedTime);
//            updateAuthorizationStatusPreparedStmt.setString(3, authorizationID);
//
//            // with result, we can determine whether the updating was successful or not
//            result = updateAuthorizationStatusPreparedStmt.executeUpdate();
//        } catch (SQLException e) {
//            log.error(ConsentMgtDAOConstants.CONSENT_AUTHORIZATION_STATUS_UPDATE_ERROR_MSG, e);
//            throw new OBConsentDataUpdationException(ConsentMgtDAOConstants
//                    .CONSENT_AUTHORIZATION_STATUS_UPDATE_ERROR_MSG, e);
//        }
//
//        // Confirm that the data are updated successfully
//        if (result > 0) {
//            log.debug("Updated the authorization status successfully");
//            AuthorizationResource authorizationResource = new AuthorizationResource();
//
//            authorizationResource.setAuthorizationStatus(newAuthorizationStatus);
//            authorizationResource.setAuthorizationID(authorizationID);
//            authorizationResource.setUpdatedTime(updatedTime);
//            return authorizationResource;
//        } else {
//            throw new OBConsentDataUpdationException("Failed to update consent status properly.");
//        }
//    }
//
//    @Override
//    public AuthorizationResource updateAuthorizationUser(Connection connection, String authorizationID, String userID)
//            throws OBConsentDataUpdationException {
//
//        int result;
//
//        // Unix time in seconds
//        long updatedTime = System.currentTimeMillis() / 1000;
//        String updateAuthorizationUserPrepStatement = sqlStatements.getUpdateAuthorizationUserPreparedStatement();
//
//        try (PreparedStatement updateAuthorizationUserPreparedStmt =
//                     connection.prepareStatement(updateAuthorizationUserPrepStatement)) {
//
//            log.debug("Setting parameters to prepared statement to update authorization user");
//
//            updateAuthorizationUserPreparedStmt.setString(1, userID);
//            updateAuthorizationUserPreparedStmt.setLong(2, updatedTime);
//            updateAuthorizationUserPreparedStmt.setString(3, authorizationID);
//
//            // with result, we can determine whether the updating was successful or not
//            result = updateAuthorizationUserPreparedStmt.executeUpdate();
//        } catch (SQLException e) {
//            log.error(ConsentMgtDAOConstants.CONSENT_AUTHORIZATION_USER_UPDATE_ERROR_MSG, e);
//            throw new OBConsentDataUpdationException(ConsentMgtDAOConstants.CONSENT_AUTHORIZATION_USER_UPDATE_ERROR_MSG,
//                    e);
//        }
//
//        // Confirm that the data are updated successfully
//        if (result > 0) {
//            log.debug("Updated the authorization user successfully");
//            AuthorizationResource authorizationResource = new AuthorizationResource();
//
//            authorizationResource.setUserID(userID);
//            authorizationResource.setAuthorizationID(authorizationID);
//            authorizationResource.setUpdatedTime(updatedTime);
//            return authorizationResource;
//        } else {
//            throw new OBConsentDataUpdationException("Failed to update authorization user properly.");
//        }
//    }
//
//    @Override
//    public ConsentFile getConsentFile(Connection connection, String consentID) throws OBConsentDataRetrievalException {
//
//        ConsentFile receivedConsentFileResource = new ConsentFile();
//        String getConsentFilePrepStatement = sqlStatements.getGetConsentFileResourcePreparedStatement();
//
//        try (PreparedStatement getConsentFileResourcePreparedStmt =
//                     connection.prepareStatement(getConsentFilePrepStatement)) {
//
//            log.debug("Setting parameters to prepared statement to retrieve consent file resource");
//
//            getConsentFileResourcePreparedStmt.setString(1, consentID);
//
//            try (ResultSet resultSet = getConsentFileResourcePreparedStmt.executeQuery()) {
//                if (resultSet.next()) {
//                    String storedConsentID = resultSet.getString(ConsentMgtDAOConstants.CONSENT_ID);
//                    String consentFile = resultSet.getString(ConsentMgtDAOConstants.CONSENT_FILE);
//
//                    receivedConsentFileResource.setConsentID(storedConsentID);
//                    receivedConsentFileResource.setConsentFile(consentFile);
//                } else {
//                    log.error("No records are found for consent ID :" + consentID);
//                    throw new OBConsentDataRetrievalException(ConsentMgtDAOConstants.NO_RECORDS_FOUND_ERROR_MSG);
//                }
//            } catch (SQLException e) {
//                log.error("Error occurred while reading consent file resource");
//                throw new OBConsentDataRetrievalException(String.format("Error occurred while retrieving consent file" +
//                        " resource for consent ID : %s", consentID), e);
//            }
//
//            if (log.isDebugEnabled()) {
//                log.debug("Retrieved the consent file resource for consent ID : " + consentID);
//            }
//        } catch (SQLException e) {
//            log.error(ConsentMgtDAOConstants.CONSENT_FILE_RETRIEVE_ERROR_MSG, e);
//            throw new OBConsentDataRetrievalException(ConsentMgtDAOConstants.CONSENT_FILE_RETRIEVE_ERROR_MSG, e);
//        }
//        return receivedConsentFileResource;
//    }
//
//    @Override
//    public ConsentAttributes getConsentAttributes(Connection connection, String consentID,
//                                                  ArrayList<String> consentAttributeKeys)
//            throws OBConsentDataRetrievalException {
//
//        Map<String, String> retrievedConsentAttributesMap = new HashMap<>();
//        ConsentAttributes retrievedConsentAttributesResource;
//        String getConsentAttributesPrepStatement = sqlStatements.getGetConsentAttributesPreparedStatement();
//
//        try (PreparedStatement getConsentAttributesPreparedStmt =
//                     connection.prepareStatement(getConsentAttributesPrepStatement)) {
//
//            log.debug("Setting parameters to prepared statement to retrieve consent attributes");
//
//            getConsentAttributesPreparedStmt.setString(1, consentID);
//
//            try (ResultSet resultSet = getConsentAttributesPreparedStmt.executeQuery()) {
//                if (resultSet.isBeforeFirst()) {
//                    while (resultSet.next()) {
//                        String attributeKey = resultSet.getString(ConsentMgtDAOConstants.ATT_KEY);
//                        String attributeValue = resultSet.getString(ConsentMgtDAOConstants.ATT_VALUE);
//
//                        // Filter the needed attributes
//                        if (consentAttributeKeys.contains(attributeKey)) {
//                            retrievedConsentAttributesMap.put(attributeKey, attributeValue);
//                            if (retrievedConsentAttributesMap.size() == consentAttributeKeys.size()) {
//                                break;
//                            }
//                        }
//                    }
//                    retrievedConsentAttributesResource = new ConsentAttributes();
//                    retrievedConsentAttributesResource.setConsentID(consentID);
//                    retrievedConsentAttributesResource.setConsentAttributes(retrievedConsentAttributesMap);
//                } else {
//                    log.error("No records are found for consent ID : " + consentID + " and consent attribute keys");
//                    throw new OBConsentDataRetrievalException(ConsentMgtDAOConstants.NO_RECORDS_FOUND_ERROR_MSG);
//                }
//            } catch (SQLException e) {
//                log.error("Error occurred while reading consent attributes", e);
//                throw new OBConsentDataRetrievalException(String.format("Error occurred while retrieving consent " +
//                        "attributes for consent ID : %s and provided consent attributes", consentID), e);
//            }
//        } catch (SQLException e) {
//            log.error(ConsentMgtDAOConstants.CONSENT_ATTRIBUTES_RETRIEVE_ERROR_MSG, e);
//            throw new OBConsentDataRetrievalException(ConsentMgtDAOConstants.CONSENT_ATTRIBUTES_RETRIEVE_ERROR_MSG, e);
//        }
//        return retrievedConsentAttributesResource;
//    }
//
//    @Override
//    public ConsentAttributes getConsentAttributes(Connection connection, String consentID)
//            throws OBConsentDataRetrievalException {
//
//        Map<String, String> retrievedConsentAttributesMap = new HashMap<>();
//        ConsentAttributes retrievedConsentAttributesResource;
//        String getConsentAttributesPrepStatement = sqlStatements.getGetConsentAttributesPreparedStatement();
//
//        try (PreparedStatement getConsentAttributesPreparedStmt =
//                     connection.prepareStatement(getConsentAttributesPrepStatement)) {
//
//            log.debug("Setting parameters to prepared statement to retrieve consent attributes");
//
//            getConsentAttributesPreparedStmt.setString(1, consentID);
//
//            try (ResultSet resultSet = getConsentAttributesPreparedStmt.executeQuery()) {
//                if (resultSet.isBeforeFirst()) {
//                    while (resultSet.next()) {
//                        retrievedConsentAttributesMap.put(resultSet.getString(ConsentMgtDAOConstants.ATT_KEY),
//                                resultSet.getString(ConsentMgtDAOConstants.ATT_VALUE));
//                    }
//                    retrievedConsentAttributesResource = new ConsentAttributes();
//                    retrievedConsentAttributesResource.setConsentID(consentID);
//                    retrievedConsentAttributesResource.setConsentAttributes(retrievedConsentAttributesMap);
//                } else {
//                    log.error("No records are found for consent ID :" + consentID);
//                    throw new OBConsentDataRetrievalException(ConsentMgtDAOConstants.NO_RECORDS_FOUND_ERROR_MSG);
//                }
//            } catch (SQLException e) {
//                log.error("Error occurred while reading consent attributes", e);
//                throw new OBConsentDataRetrievalException(String.format("Error occurred while retrieving consent " +
//                        "attributes for consent ID : %s", consentID), e);
//            }
//        } catch (SQLException e) {
//            log.error(ConsentMgtDAOConstants.CONSENT_ATTRIBUTES_RETRIEVE_ERROR_MSG, e);
//            throw new OBConsentDataRetrievalException(ConsentMgtDAOConstants.CONSENT_ATTRIBUTES_RETRIEVE_ERROR_MSG, e);
//        }
//        return retrievedConsentAttributesResource;
//    }
//
//    @Override
//    public Map<String, String> getConsentAttributesByName(Connection connection, String attributeName)
//            throws OBConsentDataRetrievalException {
//
//        Map<String, String> retrievedConsentAttributesMap = new HashMap<>();
//        String getConsentAttributesByNamePrepStatement = sqlStatements.getGetConsentAttributesByNamePreparedStatement();
//
//        try (PreparedStatement getConsentAttributesByNamePreparedStmt =
//                     connection.prepareStatement(getConsentAttributesByNamePrepStatement)) {
//
//            if (log.isDebugEnabled()) {
//                log.debug("Setting parameters to prepared statement to retrieve consent attributes for the provided " +
//                        "key: " + attributeName);
//            }
//            getConsentAttributesByNamePreparedStmt.setString(1, attributeName);
//
//            try (ResultSet resultSet = getConsentAttributesByNamePreparedStmt.executeQuery()) {
//                if (resultSet.isBeforeFirst()) {
//                    while (resultSet.next()) {
//                        retrievedConsentAttributesMap.put(resultSet.getString(ConsentMgtDAOConstants.CONSENT_ID),
//                                resultSet.getString(ConsentMgtDAOConstants.ATT_VALUE));
//                    }
//                } else {
//                    log.error("No records are found for the provided attribute key  :" + attributeName);
//                    throw new OBConsentDataRetrievalException(ConsentMgtDAOConstants.NO_RECORDS_FOUND_ERROR_MSG);
//                }
//            } catch (SQLException e) {
//                log.error("Error occurred while reading consent attributes for the given key: "
//                        + attributeName, e);
//                throw new OBConsentDataRetrievalException(String.format("Error occurred while retrieving consent " +
//                        "attributes for attribute key: %s", attributeName), e);
//            }
//        } catch (SQLException e) {
//            log.error(ConsentMgtDAOConstants.CONSENT_ATTRIBUTES_RETRIEVE_ERROR_MSG, e);
//            throw new OBConsentDataRetrievalException(ConsentMgtDAOConstants.CONSENT_ATTRIBUTES_RETRIEVE_ERROR_MSG, e);
//        }
//        return retrievedConsentAttributesMap;
//    }
//
//    @Override
//    public ArrayList<String> getConsentIdByConsentAttributeNameAndValue(Connection connection, String attributeName,
//                                                                        String attributeValue)
//            throws OBConsentDataRetrievalException {
//
//        ArrayList<String> retrievedConsentIdList = new ArrayList<>();
//        String getConsentIdByConsentAttributeNameAndValuePrepStatement = sqlStatements
//                .getConsentIdByConsentAttributeNameAndValuePreparedStatement();
//
//        try (PreparedStatement getConsentAttributesByNamePreparedStmt =
//                     connection.prepareStatement(getConsentIdByConsentAttributeNameAndValuePrepStatement)) {
//
//            if (log.isDebugEnabled()) {
//                log.debug("Setting parameters to prepared statement to retrieve consent id for the provided " +
//                        "key: " + attributeName + " and value: " + attributeValue);
//            }
//            getConsentAttributesByNamePreparedStmt.setString(1, attributeName);
//            getConsentAttributesByNamePreparedStmt.setString(2, attributeValue);
//
//            try (ResultSet resultSet = getConsentAttributesByNamePreparedStmt.executeQuery()) {
//                if (resultSet.isBeforeFirst()) {
//                    while (resultSet.next()) {
//                        retrievedConsentIdList.add(resultSet.getString(ConsentMgtDAOConstants.CONSENT_ID));
//                    }
//                } else {
//                    log.error("No records are found for the provided attribute key  :" + attributeName +
//                            " and value: " + attributeValue);
//                    throw new OBConsentDataRetrievalException(ConsentMgtDAOConstants.NO_RECORDS_FOUND_ERROR_MSG);
//                }
//            } catch (SQLException e) {
//                log.error("Error occurred while reading consent attributes for the given key: " + attributeName +
//                        " and value: " + attributeValue, e);
//                throw new OBConsentDataRetrievalException(String.format("Error occurred while retrieving consent " +
//                        "attributes for attribute key: %s  and value: %s", attributeName, attributeValue), e);
//            }
//        } catch (SQLException e) {
//            log.error(ConsentMgtDAOConstants.CONSENT_ID_RETRIEVE_ERROR_MSG, e);
//            throw new OBConsentDataRetrievalException(ConsentMgtDAOConstants.CONSENT_ID_RETRIEVE_ERROR_MSG, e);
//        }
//        return retrievedConsentIdList;
//    }
//
//    @Override
//    public ConsentResource getConsentResource(Connection connection, String consentID)
//            throws OBConsentDataRetrievalException {
//
//        ConsentResource retrievedConsentResource = new ConsentResource();
//
//        String getConsentResourcePrepStatement = sqlStatements.getGetConsentPreparedStatement();
//
//        try (PreparedStatement getConsentResourcePreparedStmt =
//                     connection.prepareStatement(getConsentResourcePrepStatement)) {
//
//            log.debug("Setting parameters to prepared statement to retrieve consent resource");
//
//            getConsentResourcePreparedStmt.setString(1, consentID);
//
//            try (ResultSet resultSet = getConsentResourcePreparedStmt.executeQuery()) {
//                if (resultSet.next()) {
//                    setDataToConsentResource(resultSet, retrievedConsentResource);
//                } else {
//                    log.error("No records are found for consent ID :" + consentID);
//                    throw new OBConsentDataRetrievalException(ConsentMgtDAOConstants.NO_RECORDS_FOUND_ERROR_MSG);
//                }
//            } catch (SQLException e) {
//                log.error("Error occurred while reading consent resource", e);
//                throw new OBConsentDataRetrievalException(String.format("Error occurred while retrieving consent " +
//                        "resource for consent ID : %s", consentID), e);
//            }
//
//            if (log.isDebugEnabled()) {
//                log.debug("Retrieved the consent resource from OB_CONSENT table for consent ID : " + consentID);
//            }
//        } catch (SQLException e) {
//            log.error(ConsentMgtDAOConstants.CONSENT_RESOURCE_RETRIEVE_ERROR_MSG, e);
//            throw new OBConsentDataRetrievalException(ConsentMgtDAOConstants.CONSENT_RESOURCE_RETRIEVE_ERROR_MSG, e);
//        }
//        return retrievedConsentResource;
//    }
//
//    @Override
//    public DetailedConsentResource getDetailedConsentResource(Connection connection, String consentID)
//            throws OBConsentDataRetrievalException {
//
//        DetailedConsentResource retrievedDetailedConsentResource = new DetailedConsentResource();
//
//        String getDetailedConsentResourcePrepStatement = sqlStatements.getGetDetailedConsentPreparedStatement();
//
//        try (PreparedStatement getDetailedConsentResourcePreparedStmt = connection
//                .prepareStatement(getDetailedConsentResourcePrepStatement)) {
//
//            log.debug("Setting parameters to prepared statement to retrieve detailed consent resource");
//
//            getDetailedConsentResourcePreparedStmt.setString(1, consentID);
//
//            try (ResultSet resultSet = getDetailedConsentResourcePreparedStmt.executeQuery()) {
//                if (resultSet.isBeforeFirst()) {
//                    setDataToDetailedConsentResource(resultSet, retrievedDetailedConsentResource);
//                } else {
//                    log.error("No records are found for consent ID :" + consentID);
//                    throw new OBConsentDataRetrievalException(ConsentMgtDAOConstants.NO_RECORDS_FOUND_ERROR_MSG);
//                }
//            } catch (SQLException e) {
//                log.error("Error occurred while reading detailed consent resource", e);
//                throw new OBConsentDataRetrievalException(String.format("Error occurred while retrieving " +
//                        "detailed consent resource for consent ID : %s", consentID), e);
//            }
//
//            if (log.isDebugEnabled()) {
//                log.debug("Retrieved the detailed consent resource for consent ID : " +
//                        consentID);
//            }
//        } catch (SQLException e) {
//            log.error(ConsentMgtDAOConstants.DETAILED_CONSENT_RESOURCE_RETRIEVE_ERROR_MSG, e);
//            throw new OBConsentDataRetrievalException(ConsentMgtDAOConstants
//                    .DETAILED_CONSENT_RESOURCE_RETRIEVE_ERROR_MSG, e);
//        }
//        return retrievedDetailedConsentResource;
//    }
//
//    @Override
//    public ConsentResource getConsentResourceWithAttributes(Connection connection, String consentID)
//            throws OBConsentDataRetrievalException {
//
//        Map<String, String> retrievedConsentAttributeMap = new HashMap<>();
//        ConsentResource retrievedConsentResource = new ConsentResource();
//
//        String getConsentResourcePrepStatement = sqlStatements.getGetConsentWithConsentAttributesPreparedStatement();
//
//        try (PreparedStatement getConsentResourcePreparedStmt =
//                     connection.prepareStatement(getConsentResourcePrepStatement, ResultSet.TYPE_SCROLL_INSENSITIVE,
//                             ResultSet.CONCUR_READ_ONLY)) {
//
//            log.debug("Setting parameters to prepared statement to retrieve consent resource with consent attributes");
//
//            getConsentResourcePreparedStmt.setString(1, consentID);
//
//            try (ResultSet resultSet = getConsentResourcePreparedStmt.executeQuery()) {
//                if (resultSet.next()) {
//                    setDataToConsentResource(resultSet, retrievedConsentResource);
//
//                    // Point the cursor to the beginning of the result set to read attributes
//                    resultSet.beforeFirst();
//                    while (resultSet.next()) {
//                        retrievedConsentAttributeMap.put(resultSet.getString(ConsentMgtDAOConstants.ATT_KEY),
//                                resultSet.getString(ConsentMgtDAOConstants.ATT_VALUE));
//                    }
//                    retrievedConsentResource.setConsentAttributes(retrievedConsentAttributeMap);
//                } else {
//                    log.error("No records are found for consent ID :" + consentID);
//                    throw new OBConsentDataRetrievalException(ConsentMgtDAOConstants.NO_RECORDS_FOUND_ERROR_MSG);
//                }
//            } catch (SQLException e) {
//                log.error("Error occurred while reading consent resource with consent attributes", e);
//                throw new OBConsentDataRetrievalException(String.format("Error occurred while retrieving consent " +
//                        "resource with consent attributes for consent ID : %s", consentID), e);
//            }
//
//            if (log.isDebugEnabled()) {
//                log.debug("Retrieved the consent resource with consent attributes for consent ID : " + consentID);
//            }
//        } catch (SQLException e) {
//            log.error(ConsentMgtDAOConstants.CONSENT_ATTRIBUTES_RETRIEVE_ERROR_MSG, e);
//            throw new OBConsentDataRetrievalException(ConsentMgtDAOConstants.CONSENT_ATTRIBUTES_RETRIEVE_ERROR_MSG, e);
//        }
//        return retrievedConsentResource;
//    }
//
//    @Override
//    public AuthorizationResource getAuthorizationResource(Connection connection, String authorizationID)
//            throws OBConsentDataRetrievalException {
//
//        AuthorizationResource retrievedAuthorizationResource = new AuthorizationResource();
//        String getAuthorizationResourcePrepStatement = sqlStatements.getGetAuthorizationResourcePreparedStatement();
//
//        try (PreparedStatement getConsentResourcePreparedStmt =
//                     connection.prepareStatement(getAuthorizationResourcePrepStatement)) {
//
//            log.debug("Setting parameters to prepared statement to retrieve consent authorization resource");
//
//            getConsentResourcePreparedStmt.setString(1, authorizationID);
//
//            try (ResultSet resultSet = getConsentResourcePreparedStmt.executeQuery()) {
//                if (resultSet.next()) {
//                    setAuthorizationData(retrievedAuthorizationResource, resultSet);
//                } else {
//                    log.error("No records are found for authorization ID :" + authorizationID);
//                    throw new OBConsentDataRetrievalException(ConsentMgtDAOConstants.NO_RECORDS_FOUND_ERROR_MSG);
//                }
//            } catch (SQLException e) {
//                log.error("Error occurred while reading consent authorization resource", e);
//                throw new OBConsentDataRetrievalException(String.format("Error occurred while retrieving consent " +
//                        "authorization resource for authorization ID : %s", authorizationID), e);
//            }
//
//            if (log.isDebugEnabled()) {
//                log.debug("Retrieved the consent authorization resource for authorization ID : " + authorizationID);
//            }
//
//        } catch (SQLException e) {
//            log.error(ConsentMgtDAOConstants.CONSENT_AUTHORIZATION_RESOURCE_RETRIEVE_ERROR_MSG, e);
//            throw new OBConsentDataRetrievalException(ConsentMgtDAOConstants.
//                    CONSENT_AUTHORIZATION_RESOURCE_RETRIEVE_ERROR_MSG, e);
//        }
//        return retrievedAuthorizationResource;
//    }
//
//    @Override
//    public ArrayList<ConsentStatusAuditRecord> getConsentStatusAuditRecords(Connection connection, String consentID,
//                                                                            String currentStatus, String actionBy,
//                                                                            Long fromTime, Long toTime,
//                                                                            String statusAuditID)
//            throws OBConsentDataRetrievalException {
//
//        ArrayList<ConsentStatusAuditRecord> retrievedAuditRecords = new ArrayList<>();
//        String getConsentStatusAuditRecordsPrepStatement =
//                sqlStatements.getGetConsentStatusAuditRecordsPreparedStatement();
//
//        try (PreparedStatement getConsentStatusAuditRecordPreparedStmt =
//                     connection.prepareStatement(getConsentStatusAuditRecordsPrepStatement)) {
//
//            if (log.isDebugEnabled()) {
//                log.debug("Setting parameters to prepared statement to retrieve consent status audit records");
//            }
//
//            // consentID
//            if (StringUtils.trimToNull(consentID) != null) {
//                getConsentStatusAuditRecordPreparedStmt.setString(1, consentID);
//            } else {
//                getConsentStatusAuditRecordPreparedStmt.setNull(1, Types.VARCHAR);
//            }
//
//            // currentStatus
//            if (StringUtils.trimToNull(currentStatus) != null) {
//                getConsentStatusAuditRecordPreparedStmt.setString(2, currentStatus);
//            } else {
//                getConsentStatusAuditRecordPreparedStmt.setNull(2, Types.VARCHAR);
//            }
//
//            // actionBy
//            if (StringUtils.trimToNull(actionBy) != null) {
//                getConsentStatusAuditRecordPreparedStmt.setString(3, actionBy);
//            } else {
//                getConsentStatusAuditRecordPreparedStmt.setNull(3, Types.VARCHAR);
//            }
//
//            // statusAuditID
//            if (StringUtils.trimToNull(statusAuditID) != null) {
//                getConsentStatusAuditRecordPreparedStmt.setString(4, statusAuditID);
//            } else {
//                getConsentStatusAuditRecordPreparedStmt.setNull(4, Types.VARCHAR);
//            }
//
//            // fromTime
//            if (fromTime != null) {
//                getConsentStatusAuditRecordPreparedStmt.setLong(5, fromTime);
//            } else {
//                getConsentStatusAuditRecordPreparedStmt.setNull(5, Types.BIGINT);
//            }
//
//            // toTime
//            if (toTime != null) {
//                getConsentStatusAuditRecordPreparedStmt.setLong(6, toTime);
//            } else {
//                getConsentStatusAuditRecordPreparedStmt.setNull(6, Types.BIGINT);
//            }
//
//            try (ResultSet resultSet = getConsentStatusAuditRecordPreparedStmt.executeQuery()) {
//                if (resultSet.isBeforeFirst()) {
//                    while (resultSet.next()) {
//                        ConsentStatusAuditRecord consentStatusAuditRecord = new ConsentStatusAuditRecord();
//                        consentStatusAuditRecord
//                                .setStatusAuditID(resultSet.getString(ConsentMgtDAOConstants.STATUS_AUDIT_ID));
//                        consentStatusAuditRecord.setConsentID(resultSet.getString(ConsentMgtDAOConstants.CONSENT_ID));
//                        consentStatusAuditRecord
//                                .setCurrentStatus(resultSet.getString(ConsentMgtDAOConstants.CURRENT_STATUS));
//                        consentStatusAuditRecord.setActionBy(resultSet.getString(ConsentMgtDAOConstants.ACTION_BY));
//                        consentStatusAuditRecord.setActionTime(resultSet.getLong(ConsentMgtDAOConstants.ACTION_TIME));
//                        consentStatusAuditRecord.setReason(resultSet.getString(ConsentMgtDAOConstants.REASON));
//                        consentStatusAuditRecord
//                                .setPreviousStatus(resultSet.getString(ConsentMgtDAOConstants.PREVIOUS_STATUS));
//                        retrievedAuditRecords.add(consentStatusAuditRecord);
//                    }
//                } else {
//                    log.error("No records are found for the provided inputs");
//                    throw new OBConsentDataRetrievalException(ConsentMgtDAOConstants.NO_RECORDS_FOUND_ERROR_MSG);
//                }
//            } catch (SQLException e) {
//                log.error("Error occurred while reading consent status audit records", e);
//                throw new OBConsentDataRetrievalException(ConsentMgtDAOConstants.AUDIT_RECORDS_RETRIEVE_ERROR_MSG, e);
//            }
//
//            log.debug("Retrieved the consent status audit records successfully");
//
//        } catch (SQLException e) {
//            log.error(ConsentMgtDAOConstants.AUDIT_RECORDS_RETRIEVE_ERROR_MSG, e);
//            throw new OBConsentDataRetrievalException(ConsentMgtDAOConstants.AUDIT_RECORDS_RETRIEVE_ERROR_MSG, e);
//        }
//        return retrievedAuditRecords;
//    }
//
//    @Override
//    public ArrayList<ConsentMappingResource> getConsentMappingResources(Connection connection, String authorizationID)
//            throws OBConsentDataRetrievalException {
//
//        ArrayList<ConsentMappingResource> retrievedConsentMappingResources = new ArrayList<>();
//        String getMappingResourcePrepStatement = sqlStatements.getGetConsentMappingResourcesPreparedStatement();
//
//        try (PreparedStatement getConsentMappingResourcePreparedStmt =
//                     connection.prepareStatement(getMappingResourcePrepStatement)) {
//
//            log.debug("Setting parameters to prepared statement to retrieve consent mapping resources");
//
//            getConsentMappingResourcePreparedStmt.setString(1, authorizationID);
//
//            try (ResultSet resultSet = getConsentMappingResourcePreparedStmt.executeQuery()) {
//                if (resultSet.isBeforeFirst()) {
//                    while (resultSet.next()) {
//                        retrievedConsentMappingResources.add(getConsentMappingResourceWithData(resultSet));
//                    }
//                } else {
//                    log.error("No records are found for authorization ID : " + authorizationID);
//                    throw new OBConsentDataRetrievalException(ConsentMgtDAOConstants.NO_RECORDS_FOUND_ERROR_MSG);
//                }
//            } catch (SQLException e) {
//                log.error("Error occurred while reading consent mapping resources", e);
//                throw new OBConsentDataRetrievalException(String.format("Error occurred while retrieving consent " +
//                        "mapping resources for authorization ID : %s", authorizationID), e);
//            }
//
//            if (log.isDebugEnabled()) {
//                log.debug("Retrieved the consent mapping resources for authorization ID : " + authorizationID);
//            }
//        } catch (SQLException e) {
//            log.error(ConsentMgtDAOConstants.CONSENT_MAPPING_RETRIEVE_ERROR_MSG, e);
//            throw new OBConsentDataRetrievalException(ConsentMgtDAOConstants.CONSENT_MAPPING_RETRIEVE_ERROR_MSG, e);
//        }
//        return retrievedConsentMappingResources;
//    }
//
//    @Override
//    public ArrayList<ConsentMappingResource> getConsentMappingResources(Connection connection, String authorizationID,
//                                                                        String mappingStatus)
//            throws OBConsentDataRetrievalException {
//
//        ArrayList<ConsentMappingResource> retrievedConsentMappingResources = new ArrayList<>();
//        String getMappingResourcePrepStatement
//                = sqlStatements.getGetConsentMappingResourcesForStatusPreparedStatement();
//
//        try (PreparedStatement getConsentMappingResourcePreparedStmt =
//                     connection.prepareStatement(getMappingResourcePrepStatement)) {
//
//            log.debug("Setting parameters to prepared statement to retrieve consent mapping resources");
//
//            getConsentMappingResourcePreparedStmt.setString(1, authorizationID);
//            getConsentMappingResourcePreparedStmt.setString(2, mappingStatus);
//
//            try (ResultSet resultSet = getConsentMappingResourcePreparedStmt.executeQuery()) {
//                if (resultSet.isBeforeFirst()) {
//                    while (resultSet.next()) {
//                        retrievedConsentMappingResources.add(getConsentMappingResourceWithData(resultSet));
//                    }
//                } else {
//                    log.error("No records are found for authorization ID : " + authorizationID + " and mapping " +
//                            "status " + mappingStatus);
//                    throw new OBConsentDataRetrievalException(ConsentMgtDAOConstants.NO_RECORDS_FOUND_ERROR_MSG);
//                }
//            } catch (SQLException e) {
//                log.error("Error occurred while reading consent mapping resources", e);
//                throw new OBConsentDataRetrievalException(String.format("Error occurred while retrieving consent " +
//                                "mapping resources for authorization ID : %s and mapping status : %s", authorizationID,
//                        mappingStatus), e);
//            }
//
//            if (log.isDebugEnabled()) {
//                log.debug("Retrieved the consent mapping resources for authorization ID : " + authorizationID + " and" +
//                        " mapping status : " + mappingStatus);
//            }
//        } catch (SQLException e) {
//            log.error(ConsentMgtDAOConstants.CONSENT_MAPPING_RETRIEVE_ERROR_MSG, e);
//            throw new OBConsentDataRetrievalException(ConsentMgtDAOConstants.CONSENT_MAPPING_RETRIEVE_ERROR_MSG, e);
//        }
//        return retrievedConsentMappingResources;
//    }
//
//    @Override
//    public boolean deleteConsentAttributes(Connection connection, String consentID,
//                                           ArrayList<String> consentAttributeKeys)
//            throws OBConsentDataDeletionException {
//
//        int[] result;
//        String deleteConsentAttributePrepStatement = sqlStatements.getDeleteConsentAttributePreparedStatement();
//
//        try (PreparedStatement deleteConsentAttributesPreparedStmt =
//                     connection.prepareStatement(deleteConsentAttributePrepStatement)) {
//
//            if (log.isDebugEnabled()) {
//                log.debug("Setting parameters to prepared statement to delete the provided consent attributes");
//            }
//
//            for (String key : consentAttributeKeys) {
//                deleteConsentAttributesPreparedStmt.setString(1, consentID);
//                deleteConsentAttributesPreparedStmt.setString(2, key);
//                deleteConsentAttributesPreparedStmt.addBatch();
//            }
//
//            result = deleteConsentAttributesPreparedStmt.executeBatch();
//        } catch (SQLException e) {
//            log.error(ConsentMgtDAOConstants.CONSENT_ATTRIBUTES_DELETE_ERROR_MSG, e);
//            throw new OBConsentDataDeletionException(ConsentMgtDAOConstants.CONSENT_ATTRIBUTES_DELETE_ERROR_MSG, e);
//        }
//
//        if (result.length != 0 && IntStream.of(result).noneMatch(value -> value == -3)) {
//            if (log.isDebugEnabled()) {
//                log.debug("Deleted the consent attribute of key " + consentAttributeKeys);
//            }
//            return true;
//        } else {
//            throw new OBConsentDataDeletionException("Failed to delete consent attribute properly.");
//        }
//    }

    @Override
    public ArrayList<DetailedConsentResource> searchConsents(Connection connection, ArrayList<String> consentIDs,
                                                             ArrayList<String> clientIDs,
                                                             ArrayList<String> consentTypes,
                                                             ArrayList<String> consentStatuses,
                                                             ArrayList<String> userIDs, Long fromTime, Long toTime,
                                                             Integer limit, Integer offset)
            throws OBConsentDataRetrievalException {

        boolean shouldLimit = true;
        boolean shouldOffset = true;
        int parameterIndex = 0;
        Map<String, ArrayList> applicableConditionsMap = new HashMap<>();

        validateAndSetSearchConditions(applicableConditionsMap, consentIDs, clientIDs, consentTypes, consentStatuses);

        // Don't limit if either of limit or offset is null
        if (limit == null) {
            shouldLimit = false;
        }
        if (offset == null) {
            shouldOffset = false;
        }

        // logic to set the prepared statement
        log.debug("Constructing the prepared statement");
        String constructedConditions =
                ConsentDAOUtils.constructConsentSearchPreparedStatement(applicableConditionsMap);

        String userIDFilterCondition = "";
        Map<String, ArrayList> userIdMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(userIDs)) {
            userIdMap.put(COLUMNS_MAP.get(ConsentMgtDAOConstants.USER_IDS), userIDs);
            userIDFilterCondition = ConsentDAOUtils.constructUserIdListFilterCondition(userIdMap);
        }

        String searchConsentsPreparedStatement =
                sqlStatements.getSearchConsentsPreparedStatement(constructedConditions, shouldLimit, shouldOffset,
                        userIDFilterCondition);

        try (PreparedStatement searchConsentsPreparedStmt =
                     connection.prepareStatement(searchConsentsPreparedStatement, ResultSet.TYPE_SCROLL_INSENSITIVE,
                             ResultSet.CONCUR_UPDATABLE)) {

            /* Since we don't know the order of the set condition clauses, have to determine the order of them to set
               the actual values to the  prepared statement */
            Map<Integer, ArrayList> orderedParamsMap = ConsentDAOUtils
                    .determineOrderOfParamsToSet(constructedConditions, applicableConditionsMap, COLUMNS_MAP);

            log.debug("Setting parameters to prepared statement to search consents");

            parameterIndex = setDynamicConsentSearchParameters(searchConsentsPreparedStmt, orderedParamsMap,
                    ++parameterIndex);
            parameterIndex = parameterIndex - 1;

            //determine order of user Ids to set
            if (CollectionUtils.isNotEmpty(userIDs)) {
                Map<Integer, ArrayList> orderedUserIdsMap = ConsentDAOUtils
                        .determineOrderOfParamsToSet(userIDFilterCondition, userIdMap, COLUMNS_MAP);
                parameterIndex = setDynamicConsentSearchParameters(searchConsentsPreparedStmt, orderedUserIdsMap,
                        ++parameterIndex);
                parameterIndex = parameterIndex - 1;
            }

            if (fromTime != null) {
                searchConsentsPreparedStmt.setLong(++parameterIndex, fromTime);
            } else {
                searchConsentsPreparedStmt.setNull(++parameterIndex, Types.BIGINT);
            }

            if (toTime != null) {
                searchConsentsPreparedStmt.setLong(++parameterIndex, toTime);
            } else {
                searchConsentsPreparedStmt.setNull(++parameterIndex, Types.BIGINT);
            }

            if (limit != null && offset != null) {
                searchConsentsPreparedStmt.setInt(++parameterIndex, limit);
                searchConsentsPreparedStmt.setInt(++parameterIndex, offset);
            } else if (limit != null) {
                searchConsentsPreparedStmt.setInt(++parameterIndex, limit);
            }
            ArrayList<DetailedConsentResource> detailedConsentResources = new ArrayList<>();

            try (ResultSet resultSet = searchConsentsPreparedStmt.executeQuery()) {
                if (resultSet.isBeforeFirst()) {
                    int resultSetSize = getResultSetSize(resultSet);
                    detailedConsentResources = constructDetailedConsentsSearchResult(resultSet, resultSetSize);
                }
                return detailedConsentResources;
            } catch (SQLException e) {
                log.error("Error occurred while searching detailed consent resources", e);
                throw new OBConsentDataRetrievalException("Error occurred while searching detailed " +
                        "consent resources", e);
            }
        } catch (SQLException e) {
            log.error(ConsentMgtDAOConstants.CONSENT_SEARCH_ERROR_MSG, e);
            throw new OBConsentDataRetrievalException(ConsentMgtDAOConstants.CONSENT_SEARCH_ERROR_MSG);
        }
    }

//    @Override
//    public ArrayList<AuthorizationResource> searchConsentAuthorizations(Connection connection, String consentID,
//                                                                        String userID)
//            throws OBConsentDataRetrievalException {
//
//        ArrayList<AuthorizationResource> retrievedAuthorizationResources = new ArrayList<>();
//        Map<String, String> conditions = new HashMap<>();
//        if (StringUtils.trimToNull(consentID) != null) {
//            conditions.put("CONSENT_ID", consentID);
//        }
//        if (StringUtils.trimToNull(userID) != null) {
//            conditions.put("USER_ID", userID);
//        }
//        String whereClause = ConsentDAOUtils.constructAuthSearchPreparedStatement(conditions);
//        String searchAuthorizationResourcesPrepStatement =
//                sqlStatements.getSearchAuthorizationResourcesPreparedStatement(whereClause);
//
//        try (PreparedStatement getSearchAuthorizationResourcesPreparedStmt =
//                     connection.prepareStatement(searchAuthorizationResourcesPrepStatement)) {
//
//            if (log.isDebugEnabled()) {
//                log.debug("Setting parameters to prepared statement to search authorization resources");
//            }
//
//            Iterator<Map.Entry<String, String>> conditionIterator = conditions.entrySet().iterator();
//
//            for (int count = 1; count <= conditions.size(); count++) {
//                getSearchAuthorizationResourcesPreparedStmt.setString(count, conditionIterator.next().getValue());
//            }
//
//            try (ResultSet resultSet = getSearchAuthorizationResourcesPreparedStmt.executeQuery()) {
//                if (resultSet.isBeforeFirst()) {
//                    while (resultSet.next()) {
//                        AuthorizationResource authorizationResource = new AuthorizationResource();
//                        authorizationResource
//                                .setAuthorizationID(resultSet.getString(ConsentMgtDAOConstants.AUTH_ID));
//                        authorizationResource.setConsentID(resultSet.getString(ConsentMgtDAOConstants.CONSENT_ID));
//                        authorizationResource
//                                .setUserID(resultSet.getString(ConsentMgtDAOConstants.USER_ID));
//                        authorizationResource.setAuthorizationStatus(resultSet
//                                .getString(ConsentMgtDAOConstants.AUTH_STATUS));
//                        authorizationResource
//                                .setAuthorizationType(resultSet.getString(ConsentMgtDAOConstants.AUTH_TYPE));
//                        authorizationResource.setUpdatedTime(resultSet.getLong(ConsentMgtDAOConstants.UPDATED_TIME));
//                        retrievedAuthorizationResources.add(authorizationResource);
//                    }
//                } else {
//                    log.error("No records are found for the provided inputs");
//                    throw new OBConsentDataRetrievalException(ConsentMgtDAOConstants.NO_RECORDS_FOUND_ERROR_MSG);
//                }
//            } catch (SQLException e) {
//                log.error("Error occurred while searching authorization resources", e);
//                throw new OBConsentDataRetrievalException(ConsentMgtDAOConstants
//                        .CONSENT_AUTHORIZATION_RESOURCE_RETRIEVE_ERROR_MSG, e);
//            }
//            log.debug("Retrieved the authorization resources successfully");
//        } catch (SQLException e) {
//            log.error(ConsentMgtDAOConstants.CONSENT_AUTHORIZATION_RESOURCE_RETRIEVE_ERROR_MSG, e);
//            throw new OBConsentDataRetrievalException(ConsentMgtDAOConstants
//                    .CONSENT_AUTHORIZATION_RESOURCE_RETRIEVE_ERROR_MSG, e);
//        }
//        return retrievedAuthorizationResources;
//    }
//
//    /**
//     * Set data from the result set to ConsentResource object.
//     *
//     * @param resultSet       result set
//     * @param consentResource consent resource
//     * @throws SQLException thrown if an error occurs when getting data from the result set
//     */
//    private void setDataToConsentResource(ResultSet resultSet, ConsentResource consentResource) throws SQLException {
//
//        consentResource.setConsentID(resultSet.getString(ConsentMgtDAOConstants.CONSENT_ID));
//        consentResource.setReceipt(resultSet.getString(ConsentMgtDAOConstants.RECEIPT));
//        consentResource.setCreatedTime(resultSet.getLong(ConsentMgtDAOConstants.CREATED_TIME));
//        consentResource.setUpdatedTime(resultSet.getLong(ConsentMgtDAOConstants.UPDATED_TIME));
//        consentResource.setClientID(resultSet.getString(ConsentMgtDAOConstants.CLIENT_ID));
//        consentResource.setConsentType(resultSet.getString(ConsentMgtDAOConstants.CONSENT_TYPE));
//        consentResource.
//                setCurrentStatus(resultSet.getString(ConsentMgtDAOConstants.CURRENT_STATUS));
//        consentResource.setConsentFrequency(resultSet
//                .getInt(ConsentMgtDAOConstants.CONSENT_FREQUENCY));
//        consentResource.setValidityPeriod(resultSet.getLong(ConsentMgtDAOConstants.VALIDITY_TIME));
//        consentResource.setRecurringIndicator(resultSet.getBoolean(
//                ConsentMgtDAOConstants.RECURRING_INDICATOR));
//    }
//
//    /**
//     * Set data from the result set to DetaildConsentResource object.
//     *
//     * @param resultSet               result set
//     * @param detailedConsentResource consent resource
//     * @throws SQLException thrown if an error occurs when getting data from the result set
//     */
//    private void setDataToDetailedConsentResource(ResultSet resultSet, DetailedConsentResource detailedConsentResource)
//            throws SQLException {
//
//        Map<String, String> consentAttributesMap = new HashMap<>();
//        ArrayList<AuthorizationResource> authorizationResources = new ArrayList<>();
//        ArrayList<ConsentMappingResource> consentMappingResources = new ArrayList<>();
//        ArrayList<String> authIds = new ArrayList<>();
//        ArrayList<String> consentMappingIds = new ArrayList<>();
//
//        while (resultSet.next()) {
//            // Set data related to the consent resource
//            setConsentDataToDetailedConsentResource(resultSet, detailedConsentResource);
//
//            // Set data related to consent attributes
//            if (StringUtils.isNotBlank(resultSet.getString(ConsentMgtDAOConstants.ATT_KEY))) {
//                consentAttributesMap.put(resultSet.getString(ConsentMgtDAOConstants.ATT_KEY),
//                        resultSet.getString(ConsentMgtDAOConstants.ATT_VALUE));
//            }
//
//            // Set data related to authorization resources
//            if (authIds.isEmpty()) {
//                AuthorizationResource authorizationResource = new AuthorizationResource();
//                authorizationResource.setAuthorizationID(resultSet.getString(ConsentMgtDAOConstants.AUTH_ID));
//                authorizationResource.setConsentID(resultSet.getString(ConsentMgtDAOConstants.CONSENT_ID));
//                authorizationResource.setAuthorizationStatus(resultSet.getString(ConsentMgtDAOConstants.AUTH_STATUS));
//                authorizationResource.setAuthorizationType(resultSet.getString(ConsentMgtDAOConstants.AUTH_TYPE));
//                authorizationResource.setUserID(resultSet.getString(ConsentMgtDAOConstants.USER_ID));
//                authorizationResource.setUpdatedTime(resultSet.getLong(ConsentMgtDAOConstants.AUTH_UPDATED_TIME));
//                authorizationResources.add(authorizationResource);
//                authIds.add(authorizationResource.getAuthorizationID());
//            } else {
//                if (!authIds.contains(resultSet.getString(ConsentMgtDAOConstants.AUTH_ID))) {
//                    AuthorizationResource authorizationResource = new AuthorizationResource();
//                    authorizationResource.setAuthorizationID(resultSet.getString(ConsentMgtDAOConstants.AUTH_ID));
//                    authorizationResource.setConsentID(resultSet.getString(ConsentMgtDAOConstants.CONSENT_ID));
//                    authorizationResource
//                            .setAuthorizationStatus(resultSet.getString(ConsentMgtDAOConstants.AUTH_STATUS));
//                    authorizationResource.setAuthorizationType(resultSet.getString(ConsentMgtDAOConstants.AUTH_TYPE));
//                    authorizationResource.setUserID(resultSet.getString(ConsentMgtDAOConstants.USER_ID));
//                    authorizationResource.setUpdatedTime(resultSet.getLong(ConsentMgtDAOConstants.AUTH_UPDATED_TIME));
//                    authorizationResources.add(authorizationResource);
//                    authIds.add(authorizationResource.getAuthorizationID());
//                }
//            }
//
//            // Set data related to consent account mappings
//            if (consentMappingIds.isEmpty() && resultSet.getString(ConsentMgtDAOConstants.MAPPING_ID) != null) {
//                ConsentMappingResource consentMappingResource = new ConsentMappingResource();
//                consentMappingResource.setAuthorizationID(resultSet.getString(ConsentMgtDAOConstants.AUTH_ID));
//                consentMappingResource.setAccountID(resultSet.getString(ConsentMgtDAOConstants.ACCOUNT_ID));
//                consentMappingResource.setMappingID(resultSet.getString(ConsentMgtDAOConstants.MAPPING_ID));
//                consentMappingResource.setMappingStatus(resultSet.getString(ConsentMgtDAOConstants.MAPPING_STATUS));
//                consentMappingResource.setPermission(resultSet.getString(ConsentMgtDAOConstants.PERMISSION));
//                consentMappingResources.add(consentMappingResource);
//                consentMappingIds.add(consentMappingResource.getMappingID());
//            } else {
//                if (!consentMappingIds.contains(resultSet.getString(ConsentMgtDAOConstants.MAPPING_ID)) &&
//                        resultSet.getString(ConsentMgtDAOConstants.MAPPING_ID) != null) {
//                    ConsentMappingResource consentMappingResource = new ConsentMappingResource();
//                    consentMappingResource.setAuthorizationID(resultSet.getString(ConsentMgtDAOConstants.AUTH_ID));
//                    consentMappingResource.setAccountID(resultSet.getString(ConsentMgtDAOConstants.ACCOUNT_ID));
//                    consentMappingResource.setMappingID(resultSet.getString(ConsentMgtDAOConstants.MAPPING_ID));
//                    consentMappingResource.setMappingStatus(resultSet.getString(ConsentMgtDAOConstants.MAPPING_STATUS));
//                    consentMappingResource.setPermission(resultSet.getString(ConsentMgtDAOConstants.PERMISSION));
//                    consentMappingResources.add(consentMappingResource);
//                    consentMappingIds.add(consentMappingResource.getMappingID());
//                }
//            }
//        }
//
//        // Set consent attributes, auth resources and account mappings to detailed consent resource
//        detailedConsentResource.setConsentAttributes(consentAttributesMap);
//        detailedConsentResource.setAuthorizationResources(authorizationResources);
//        detailedConsentResource.setConsentMappingResources(consentMappingResources);
//    }

    void setConsentDataToDetailedConsentResource(ResultSet resultSet,
                                                 DetailedConsentResource detailedConsentResource)
            throws SQLException {

        detailedConsentResource.setConsentID(resultSet.getString(ConsentMgtDAOConstants.CONSENT_ID));
        detailedConsentResource.setClientID(resultSet.getString(ConsentMgtDAOConstants.CLIENT_ID));
        detailedConsentResource.setReceipt(resultSet.getString(ConsentMgtDAOConstants.RECEIPT));
        detailedConsentResource.setCreatedTime(resultSet.getLong(ConsentMgtDAOConstants.CONSENT_CREATED_TIME));
        detailedConsentResource.setUpdatedTime(resultSet.getLong(ConsentMgtDAOConstants.CONSENT_UPDATED_TIME));
        detailedConsentResource.setConsentType(resultSet.getString(ConsentMgtDAOConstants.CONSENT_TYPE));
        detailedConsentResource.setCurrentStatus(resultSet.getString(ConsentMgtDAOConstants.CURRENT_STATUS));
        detailedConsentResource.setConsentFrequency(resultSet.getInt(ConsentMgtDAOConstants.CONSENT_FREQUENCY));
        detailedConsentResource.setValidityPeriod(resultSet.getLong(ConsentMgtDAOConstants.VALIDITY_TIME));
        detailedConsentResource.setRecurringIndicator(resultSet
                .getBoolean(ConsentMgtDAOConstants.RECURRING_INDICATOR));
    }

//    /**
//     * Return a consent mapping resource with data set from the result set.
//     *
//     * @param resultSet result set
//     * @return a consent mapping resource
//     * @throws SQLException thrown if an error occurs when getting data from the result set
//     */
//    private ConsentMappingResource getConsentMappingResourceWithData(ResultSet resultSet) throws SQLException {
//
//        ConsentMappingResource consentMappingResource = new ConsentMappingResource();
//        consentMappingResource.setAuthorizationID(resultSet.getString(ConsentMgtDAOConstants.AUTH_ID));
//        consentMappingResource.setMappingID(resultSet.getString(ConsentMgtDAOConstants.MAPPING_ID));
//        consentMappingResource.setAccountID(resultSet.getString(ConsentMgtDAOConstants.ACCOUNT_ID));
//        consentMappingResource.setPermission(resultSet.getString(ConsentMgtDAOConstants.PERMISSION));
//        consentMappingResource.setMappingStatus(resultSet.getString(ConsentMgtDAOConstants.MAPPING_STATUS));
//
//        return consentMappingResource;
//    }
//
//    /**
//     * Sets search parameters to dynamically constructed prepared statement. The outer loop is used to iterate the
//     * different AND clauses and the inner loop is to iterate the number of placeholders of the current AND clause.
//     *
//     * @param preparedStatement dynamically constructed prepared statement
//     * @param orderedParamsMap  map with ordered AND conditions
//     * @param parameterIndex    index which the parameter should be set
//     * @return the final parameter index
//     * @throws SQLException thrown if an error occurs in the process
//     */
    int setDynamicConsentSearchParameters(PreparedStatement preparedStatement, Map<Integer, ArrayList> orderedParamsMap,
                                          int parameterIndex)
            throws SQLException {

        for (Map.Entry<Integer, ArrayList> entry : orderedParamsMap.entrySet()) {
            for (int valueIndex = 0; valueIndex < entry.getValue().size(); valueIndex++) {
                preparedStatement.setString(parameterIndex, ((String) entry.getValue().get(valueIndex)).trim());
                parameterIndex++;
            }
        }
        return parameterIndex;
    }

    int getResultSetSize(ResultSet resultSet) throws SQLException {

        resultSet.last();
        int resultSetSize = resultSet.getRow();

        // Point result set back before first
        resultSet.beforeFirst();
        return resultSetSize;
    }

//    void setAuthorizationData(AuthorizationResource authorizationResource, ResultSet resultSet)
//            throws SQLException {
//
//        authorizationResource.setAuthorizationID(resultSet
//                .getString(ConsentMgtDAOConstants.AUTH_ID));
//        authorizationResource.setConsentID(resultSet.getString(ConsentMgtDAOConstants.CONSENT_ID));
//        authorizationResource.setAuthorizationType(resultSet
//                .getString(ConsentMgtDAOConstants.AUTH_TYPE));
//        authorizationResource.setAuthorizationStatus(resultSet
//                .getString(ConsentMgtDAOConstants.AUTH_STATUS));
//        authorizationResource.setUpdatedTime(resultSet
//                .getLong(ConsentMgtDAOConstants.UPDATED_TIME));
//        authorizationResource.setUserID(resultSet.getString(ConsentMgtDAOConstants.USER_ID));
//    }

    protected void setAuthorizationDataInResponseForGroupedQuery(ArrayList<AuthorizationResource>
                                                                         authorizationResources,
                                                                 ResultSet resultSet, String consentId)
            throws SQLException {

        //identify duplicate auth data
        Set<String> authIdSet = new HashSet<>();

        // fetch values from group_concat
        String[] authIds = resultSet.getString(ConsentMgtDAOConstants.AUTH_ID) != null ?
                resultSet.getString(ConsentMgtDAOConstants.AUTH_ID).split(GROUP_BY_SEPARATOR) : null;
        String[] authTypes = resultSet.getString(ConsentMgtDAOConstants.AUTH_TYPE) != null ?
                resultSet.getString(ConsentMgtDAOConstants.AUTH_TYPE).split(GROUP_BY_SEPARATOR) : null;
        String[] authStatues = resultSet.getString(ConsentMgtDAOConstants.AUTH_STATUS) != null ?
                resultSet.getString(ConsentMgtDAOConstants.AUTH_STATUS).split(GROUP_BY_SEPARATOR) : null;
        String[] updatedTimes = resultSet.getString(ConsentMgtDAOConstants.UPDATED_TIME) != null ?
                resultSet.getString(ConsentMgtDAOConstants.UPDATED_TIME).split(GROUP_BY_SEPARATOR) : null;
        String[] userIds = resultSet.getString(ConsentMgtDAOConstants.USER_ID) != null ?
                resultSet.getString(ConsentMgtDAOConstants.USER_ID).split(GROUP_BY_SEPARATOR) : null;

        for (int index = 0; index < (authIds != null ? authIds.length : 0); index++) {
            if (!authIdSet.contains(authIds[index])) {
                AuthorizationResource authorizationResource = new AuthorizationResource();
                authIdSet.add(authIds[index]);
                authorizationResource.setAuthorizationID(authIds[index]);
                authorizationResource.setConsentID(consentId);
                if (authTypes != null && authTypes.length > index) {
                    authorizationResource.setAuthorizationType(authTypes[index]);
                }
                if (authStatues != null && authStatues.length > index) {
                    authorizationResource.setAuthorizationStatus(authStatues[index]);
                }
                if (updatedTimes != null && updatedTimes.length > index) {
                    authorizationResource.setUpdatedTime(Long.parseLong(updatedTimes[index]));
                }
                if (userIds != null && userIds.length > index) {
                    authorizationResource.setUserID(userIds[index]);
                }
                authorizationResources.add(authorizationResource);
            }
        }

    }

    protected void setAccountConsentMappingDataInResponse(ArrayList<ConsentMappingResource> consentMappingResources,
                                                          ResultSet resultSet) throws SQLException {

        //identify duplicate mappingIds
        Set<String> mappingIdSet = new HashSet<>();

        // fetch values from group_concat
        String[] authIds = resultSet.getString(ConsentMgtDAOConstants.AUTH_MAPPING_ID) != null ?
                resultSet.getString(ConsentMgtDAOConstants.AUTH_MAPPING_ID).split(GROUP_BY_SEPARATOR) : null;
        String[] mappingIds = resultSet.getString(ConsentMgtDAOConstants.MAPPING_ID) != null ?
                resultSet.getString(ConsentMgtDAOConstants.MAPPING_ID).split(GROUP_BY_SEPARATOR) : null;
        String[] accountIds = resultSet.getString(ConsentMgtDAOConstants.ACCOUNT_ID) != null ?
                resultSet.getString(ConsentMgtDAOConstants.ACCOUNT_ID).split(GROUP_BY_SEPARATOR) : null;
        String[] mappingStatues = resultSet.getString(ConsentMgtDAOConstants.MAPPING_STATUS) != null ?
                resultSet.getString(ConsentMgtDAOConstants.MAPPING_STATUS).split(GROUP_BY_SEPARATOR) : null;
        String[] permissions = resultSet.getString(ConsentMgtDAOConstants.PERMISSION) != null ?
                resultSet.getString(ConsentMgtDAOConstants.PERMISSION).split(GROUP_BY_SEPARATOR) : null;

        for (int index = 0; index < (mappingIds != null ? mappingIds.length : 0); index++) {
            if (!mappingIdSet.contains(mappingIds[index])) {
                ConsentMappingResource consentMappingResource = new ConsentMappingResource();
                if (authIds != null && authIds.length > index) {
                    consentMappingResource.setAuthorizationID(authIds[index]);
                }
                consentMappingResource.setMappingID(mappingIds[index]);
                if (accountIds != null && accountIds.length > index) {
                    consentMappingResource.setAccountID(accountIds[index]);
                }
                if (mappingStatues != null && mappingStatues.length > index) {
                    consentMappingResource.setMappingStatus(mappingStatues[index]);
                }
                if (permissions != null && permissions.length > index) {
                    consentMappingResource.setPermission(permissions[index]);
                }
                consentMappingResources.add(consentMappingResource);
                mappingIdSet.add(mappingIds[index]);
            }
        }

    }

    void validateAndSetSearchConditions(Map<String, ArrayList> applicableConditionsMap, ArrayList<String> consentIDs,
                                        ArrayList<String> clientIDs,
                                        ArrayList<String> consentTypes,
                                        ArrayList<String> consentStatuses) {

        log.debug("Validate applicable search conditions");

        if (CollectionUtils.isNotEmpty(consentIDs)) {
            applicableConditionsMap.put(COLUMNS_MAP.get(ConsentMgtDAOConstants.CONSENT_IDS), consentIDs);
        }
        if (CollectionUtils.isNotEmpty(clientIDs)) {
            applicableConditionsMap.put(COLUMNS_MAP.get(ConsentMgtDAOConstants.CLIENT_IDS), clientIDs);
        }
        if (CollectionUtils.isNotEmpty(consentTypes)) {
            applicableConditionsMap.put(COLUMNS_MAP.get(ConsentMgtDAOConstants.CONSENT_TYPES), consentTypes);
        }
        if (CollectionUtils.isNotEmpty(consentStatuses)) {
            applicableConditionsMap.put(COLUMNS_MAP.get(ConsentMgtDAOConstants.CONSENT_STATUSES), consentStatuses);
        }
    }

    ArrayList<DetailedConsentResource> constructDetailedConsentsSearchResult(ResultSet resultSet, int resultSetSize)
            throws SQLException {

        ArrayList<DetailedConsentResource> detailedConsentResources = new ArrayList<>();

        while (resultSet.next()) {

            Map<String, String> consentAttributesMap = new HashMap<>();
            ArrayList<ConsentMappingResource> consentMappingResources = new ArrayList<>();
            ArrayList<AuthorizationResource> authorizationResources = new ArrayList<>();
            DetailedConsentResource detailedConsentResource = new DetailedConsentResource();

            setConsentDataToDetailedConsentResource(resultSet, detailedConsentResource);

            // Set consent attributes to map if available
            if (resultSet.getString(ConsentMgtDAOConstants.ATT_KEY) != null &&
                    StringUtils.isNotBlank(resultSet.getString(ConsentMgtDAOConstants.ATT_KEY))
                    && StringUtils.isNotBlank(resultSet.getString(ConsentMgtDAOConstants.ATT_VALUE))) {
                // fetch attribute keys and values from group_concat
                String[] attributeKeys = resultSet.getString(ConsentMgtDAOConstants.ATT_KEY).split(GROUP_BY_SEPARATOR);
                String[] attributeValues = resultSet
                        .getString(ConsentMgtDAOConstants.ATT_VALUE).split(GROUP_BY_SEPARATOR);
                // check if all attribute keys has values
                if (attributeKeys.length == attributeValues.length) {
                    for (int index = 0; index < attributeKeys.length; index++) {
                        consentAttributesMap.put(attributeKeys[index], attributeValues[index]);
                    }
                }
            }
            // Set authorization data
            setAuthorizationDataInResponseForGroupedQuery(authorizationResources, resultSet,
                    detailedConsentResource.getConsentID());
            // Set consent account mapping data if available
            setAccountConsentMappingDataInResponse(consentMappingResources, resultSet);

            detailedConsentResource.setConsentAttributes(consentAttributesMap);
            detailedConsentResource.setAuthorizationResources(authorizationResources);
            detailedConsentResource.setConsentMappingResources(consentMappingResources);

            detailedConsentResources.add(detailedConsentResource);

        }
        return detailedConsentResources;
    }

//    @Override
//    public boolean updateConsentReceipt(Connection connection, String consentID, String consentReceipt)
//            throws OBConsentDataUpdationException {
//
//        int result;
//        String updateConsentReceiptPrepStatement = sqlStatements.getUpdateConsentReceiptPreparedStatement();
//
//        try (PreparedStatement updateConsentReceiptPreparedStmt =
//                     connection.prepareStatement(updateConsentReceiptPrepStatement)) {
//
//            log.debug("Setting parameters to prepared statement to update consent receipt");
//
//            updateConsentReceiptPreparedStmt.setString(1, consentReceipt);
//            updateConsentReceiptPreparedStmt.setString(2, consentID);
//
//            // with result, we can determine whether the updating was successful or not
//            result = updateConsentReceiptPreparedStmt.executeUpdate();
//        } catch (SQLException e) {
//            log.error("Error while updating consent receipt", e);
//            throw new OBConsentDataUpdationException("Error while updating consent receipt for consent ID: "
//                    + consentID, e);
//        }
//
//        // Confirm that the data are updated successfully
//        if (result > 0) {
//            return true;
//        } else {
//            throw new OBConsentDataUpdationException("Failed to update consent receipt properly.");
//        }
//    }
//
//    @Override
//    public boolean updateConsentValidityTime(Connection connection, String consentID, long validityTime)
//            throws OBConsentDataUpdationException {
//
//        int result;
//        String updateConsentReceiptPrepStatement = sqlStatements.getUpdateConsentValidityTimePreparedStatement();
//
//        try (PreparedStatement updateConsentValidityTimePreparedStmt =
//                     connection.prepareStatement(updateConsentReceiptPrepStatement)) {
//
//            log.debug("Setting parameters to prepared statement to update consent receipt");
//
//            updateConsentValidityTimePreparedStmt.setLong(1, validityTime);
//            updateConsentValidityTimePreparedStmt.setString(2, consentID);
//
//            // with result, we can determine whether the updating was successful or not
//            result = updateConsentValidityTimePreparedStmt.executeUpdate();
//        } catch (SQLException e) {
//            log.error("Error while updating consent validity time", e);
//            throw new OBConsentDataUpdationException("Error while updating consent validity time for consent ID: "
//                    + consentID, e);
//        }
//
//        // Confirm that the data are updated successfully
//        if (result > 0) {
//            return true;
//        } else {
//            throw new OBConsentDataUpdationException("Failed to update consent validity time properly.");
//        }
//    }
//
//    public ArrayList<DetailedConsentResource> getExpiringConsents(Connection connection,
//                                                                  String statusesEligibleForExpiration)
//            throws OBConsentDataRetrievalException {
//
//        List<String> statusesEligibleForExpirationList = Arrays.asList(statusesEligibleForExpiration.split(","))
//                .stream().filter(status -> !status.isEmpty())
//                .collect(Collectors.toList());
//
//        String statusesEligibleForExpirationCondition = ConsentDAOUtils.constructStatusesEligibleForExpirationCondition(
//                statusesEligibleForExpirationList);
//        String expiringConsentStatement = sqlStatements.getSearchExpiringConsentPreparedStatement(
//                statusesEligibleForExpirationCondition);
//
//        try (PreparedStatement preparedStatement =
//                     connection.prepareStatement(expiringConsentStatement)) {
//
//            log.debug("Setting parameters to prepared statement to fetch consents eligible for expiration");
//
//            ArrayList<String> consentIdList = new ArrayList<>();
//
//            // populate prepared statement
//            int parameterIndex = 0;
//            preparedStatement.setString(++parameterIndex, ConsentMgtDAOConstants.CONSENT_EXPIRY_TIME_ATTRIBUTE);
//            for (String status : statusesEligibleForExpirationList) {
//                preparedStatement.setString(++parameterIndex, status);
//            }
//
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                if (resultSet.isBeforeFirst()) {
//                    while (resultSet.next()) {
//                        consentIdList.add(resultSet.getString(ConsentMgtDAOConstants.CONSENT_ID));
//                    }
//                } else {
//                    log.debug("No consents found for expiration check eligibility.");
//                }
//                if (!consentIdList.isEmpty()) {
//                    return searchConsents(connection, consentIdList, null, null, null,
//                            null, null, null, null, null);
//                } else {
//                    return new ArrayList<>();
//                }
//
//            } catch (SQLException e) {
//                log.error("Error occurred while searching consents eligible for expiration", e);
//                throw new OBConsentDataRetrievalException("Error occurred while searching consents" +
//                        " eligible for expiration", e);
//            }
//        } catch (SQLException e) {
//            log.error("Error while searching consents eligible for expiration", e);
//            throw new OBConsentDataRetrievalException("Error while updating searching consents eligible for" +
//                    " expiration", e);
//        }
//    }
}
