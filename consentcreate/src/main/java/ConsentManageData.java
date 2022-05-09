import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Data wrapper for consent manage data.
 */
public class ConsentManageData {

    private Map<String, String> headers;
    //Payload can either be a JSONObject or a JSONArray
    private Object payload;
    private Map queryParams;
    private String requestPath;
    private String clientId;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ResponseStatus responseStatus;
    private Object responsePayload;

    public ConsentManageData(Map<String, String> headers, Object payload, Map queryParams,
                             String requestPath, HttpServletRequest request, HttpServletResponse response) {
        this.headers = headers;
        this.payload = payload;
        this.queryParams = queryParams;
        this.requestPath = requestPath;
        this.request = request;
        this.response = response;
    }

    public ConsentManageData(Map<String, String> headers, Map queryParams,
                             String requestPath, HttpServletRequest request, HttpServletResponse response) {
        this.headers = headers;
        this.requestPath = requestPath;
        payload = null;
        this.queryParams = queryParams;
        this.request = request;
        this.response = response;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public Object getPayload() {
        return payload;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public void setResponsePayload(Object responsePayload) {
        this.responsePayload = responsePayload;
    }

    public Object getResponsePayload() {
        return responsePayload;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseHeader(String key, String value) {
        response.setHeader(key, value);
    }

    public void setResponseHeaders(Map<String, String> headers) {
        for (Map.Entry<String, String> header : headers.entrySet()) {
            setResponseHeader(header.getKey(), header.getValue());
        }
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public void setQueryParams(Map queryParams) {
        this.queryParams = queryParams;
    }
}
