//import com.wso2.openbanking.accelerator.common.exception.ConsentManagementException;
//import com.wso2.openbanking.accelerator.consent.extensions.common.ConsentException;
//import com.wso2.openbanking.accelerator.consent.extensions.common.ResponseStatus;
//import com.wso2.openbanking.accelerator.consent.extensions.internal.ConsentExtensionsDataHolder;
//import com.wso2.openbanking.accelerator.consent.extensions.manage.model.ConsentManageData;
//import com.wso2.openbanking.accelerator.consent.extensions.manage.model.ConsentManageHandler;
//import com.wso2.openbanking.accelerator.consent.mgt.dao.constants.ConsentMgtDAOConstants;
//import com.wso2.openbanking.accelerator.consent.mgt.dao.models.ConsentResource;
//import com.wso2.openbanking.accelerator.consent.mgt.dao.models.DetailedConsentResource;
//import com.wso2.openbanking.accelerator.consent.mgt.service.constants.ConsentCoreServiceConstants;
import com.wso2.openbanking.accelerator.common.exception.ConsentManagementException;
import com.wso2.openbanking.accelerator.consent.mgt.dao.constants.ConsentMgtDAOConstants;
import com.wso2.openbanking.accelerator.consent.mgt.dao.models.ConsentResource;
import com.wso2.openbanking.accelerator.consent.mgt.dao.models.DetailedConsentResource;
import com.wso2.openbanking.accelerator.consent.mgt.service.constants.ConsentCoreService;
import internal.ConsentCoreserviceoader;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Consent manage handler default implementation.
 */
public class DefaultConsentManageHandler  {

    private static final Log log = LogFactory.getLog(DefaultConsentManageHandler.class);

    private static final String ACCOUNT_CONSENT_CREATE_PATH = "account-access-consents";
    private static final String ACCOUNT_CONSENT_GET_PATH = "account-access-consents/";
    private static final String ACCOUNT_CONSENT_DELETE_PATH = "account-access-consents/";
    private static final String UUID_REGEX =
            "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}";
    private static final String ACCOUNTS_TYPE = "accounts";
    private static final String CREATED_STATUS = "created";
    private static final String REVOKED_STATUS = "revoked";
    private static final String AWAITING_AUTH_STATUS = "awaitingAuthorisation";
    private static final String AUTH_TYPE_AUTHORIZATION = "authorization";
    private static final String INTERACTION_ID_HEADER = "x-fapi-interaction-id";
    private static final List<String> validPermissions = Arrays.asList(
            "ReadAccountsDetail",
            "ReadTransactionsDetail",
            "ReadBalances");


    public static void handlePost(ConsentManageData consentManageData) throws ConsentException {

        if (consentManageData.getHeaders().containsKey(INTERACTION_ID_HEADER)) {
            consentManageData.setResponseHeader(INTERACTION_ID_HEADER,
                    consentManageData.getHeaders().get(INTERACTION_ID_HEADER));
        } else {
            consentManageData.setResponseHeader(INTERACTION_ID_HEADER, UUID.randomUUID().toString());
        }

        Object request = consentManageData.getPayload();
        if (request == null || request instanceof JSONArray) {
            throw new ConsentException(ResponseStatus.BAD_REQUEST, "Payload not a JSON object");
        }
        JSONObject response = (JSONObject) request;

        if (consentManageData.getRequestPath().equals(ACCOUNT_CONSENT_CREATE_PATH)) {
            JSONObject requestObject = (JSONObject) request;

            if (!validateInitiation(requestObject)) {
                throw new ConsentException(ResponseStatus.BAD_REQUEST, "Consent validation failed");
            }
            String consentID = UUID.randomUUID().toString();
            ConsentResource requestedConsent = new ConsentResource(consentManageData.getClientId(),
                    requestObject.toJSONString(), ACCOUNTS_TYPE, AWAITING_AUTH_STATUS);
            requestedConsent.setConsentID(consentID);
            DetailedConsentResource createdConsent;
            // Add consent expiration timestamp to the it's attributes map
            appendConsentExpirationTimestampAttribute(requestedConsent);
            try {

//                createdConsent = ConsentExtensionsDataHolder.getInstance().getConsentCoreService()
//                        .createAuthorizableConsent(requestedConsent, null,
//                                CREATED_STATUS, AUTH_TYPE_AUTHORIZATION, true);

                ServiceLoader<ConsentCoreService> serviceLoader = ServiceLoader.load(ConsentCoreService.class);
                // load the available providers of this loader's service.
                Iterator<ConsentCoreService> iterator = serviceLoader.iterator();
                createdConsent = iterator.next().createAuthorizableConsent(requestedConsent, null, CREATED_STATUS, AUTH_TYPE_AUTHORIZATION, true);


            } catch (ConsentManagementException e) {
                throw new ConsentException(ResponseStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
            JSONObject data = (JSONObject) response.get("Data");
            data.put("ConsentId", createdConsent.getConsentID());
            data.appendField("CreationDateTime", convertEpochDateTime(createdConsent.getCreatedTime()));
            data.appendField("StatusUpdateDateTime", convertEpochDateTime(createdConsent.getUpdatedTime()));
            response.put("Data", data);
            consentManageData.setResponsePayload(response);
            consentManageData.setResponseStatus(ResponseStatus.CREATED);

        } else {
            throw new ConsentException(ResponseStatus.BAD_REQUEST, "Request path invalid");
        }
    }

    private static boolean validateInitiation(JSONObject initiation) {

        if (!initiation.containsKey("Data") || !(initiation.get("Data") instanceof JSONObject)) {
            return false;
        }

        JSONObject data = (JSONObject) initiation.get("Data");

        if (!data.containsKey("Permissions") || !(data.get("Permissions") instanceof JSONArray)) {
            return false;
        }

        JSONArray permissions = (JSONArray) data.get("Permissions");
        for (Object permission : permissions) {
            if (!(permission instanceof String)) {
                return false;
            }
            String permissionString = (String) permission;
            if (!validPermissions.contains(permissionString)) {
                return false;
            }
        }

        if (!data.containsKey("ExpirationDateTime") || !(data.get("ExpirationDateTime") instanceof String)) {
            return false;
        }

        if (!isConsentExpirationTimeValid(data.getAsString("ExpirationDateTime"))) {
            return false;
        }

        if (!data.containsKey("TransactionFromDateTime") || !(data.get("TransactionFromDateTime") instanceof String)) {
            return false;
        }

        if (!data.containsKey("TransactionToDateTime") || !(data.get("TransactionToDateTime") instanceof String)) {
            return false;
        }

        if (!isTransactionFromToTimeValid(data.getAsString("TransactionFromDateTime"),
                data.getAsString("TransactionToDateTime"))) {
            return false;
        }

        return true;
    }

    private static boolean isConsentExpirationTimeValid(String expDateVal) {

        if (expDateVal == null) {
            return true;
        }
        try {
            OffsetDateTime expDate = OffsetDateTime.parse(expDateVal);
            OffsetDateTime currDate = OffsetDateTime.now(expDate.getOffset());

            if (log.isDebugEnabled()) {
                log.debug("Provided expiry date is: " + expDate + " current date is: " + currDate);
            }

            return expDate.compareTo(currDate) > 0;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private static boolean isTransactionFromToTimeValid(String fromDateVal, String toDateVal) {

        if (fromDateVal == null || toDateVal == null) {
            return true;
        }
        try {
            OffsetDateTime fromDate = OffsetDateTime.parse(fromDateVal);
            OffsetDateTime toDate = OffsetDateTime.parse(toDateVal);

            // From date is earlier than To date
            return (fromDate.compareTo(toDate) <= 0);
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    private static String convertEpochDateTime(long epochTime) {

        int nanoOfSecond = 0;
        ZoneOffset offset = ZoneOffset.UTC;
        LocalDateTime ldt = LocalDateTime.ofEpochSecond(epochTime, nanoOfSecond, offset);
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").format(ldt);
    }

    /**
     * Method to append the consent expiration time (UTC) as a consent attribute.
     *
     * @param requestedConsent
     */
    private static void appendConsentExpirationTimestampAttribute(ConsentResource requestedConsent) {

        Map<String, String> consentAttributes = requestedConsent.getConsentAttributes();
        JSONObject receiptJSON = null;
        try {
            receiptJSON = (JSONObject) new JSONParser(JSONParser.MODE_PERMISSIVE).
                    parse(requestedConsent.getReceipt());
            JSONObject data = null;
            if (receiptJSON.containsKey("Data")) {
                data = (JSONObject) receiptJSON.get("Data");
            }
            if (data != null && data.containsKey(ConsentMgtDAOConstants.CONSENT_EXPIRY_TIME_ATTRIBUTE)) {
                String expireTime = data.get(ConsentMgtDAOConstants.CONSENT_EXPIRY_TIME_ATTRIBUTE).toString();
                ZonedDateTime zonedDateTime = ZonedDateTime.parse(expireTime);
                // Retrieve the UTC timestamp in long from expiry time.
                long expireTimestamp = Instant.from(zonedDateTime).getEpochSecond();
                if (consentAttributes == null) {
                    consentAttributes = new HashMap<String, String>();
                }
                consentAttributes.put(ConsentMgtDAOConstants.CONSENT_EXPIRY_TIME_ATTRIBUTE,
                        Long.toString(expireTimestamp));
                requestedConsent.setConsentAttributes(consentAttributes);
            }
        } catch (ParseException e) {
            log.error("Invalid consent receipt received to append expiration time. : "
                    + requestedConsent.getConsentID());
        }
    }

}


