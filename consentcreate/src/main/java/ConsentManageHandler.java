/**
 * Consent manage handler interface.
 */
public interface ConsentManageHandler {

    /**
     * Function to handle POST requests received to the consent manage endpoint.
     *
     * @param consentManageData Object containing data regarding the request
     * @throws ConsentException Error object with data required for the error response
     */
    public void handlePost(ConsentManageData consentManageData) throws ConsentException;

}
