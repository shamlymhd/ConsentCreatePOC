import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.helpers.IOUtils;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ConsentUtils {

    private static final Log log = LogFactory.getLog(ConsentUtils.class);

    public static Object getPayload(HttpServletRequest request) {
        try {
            Object payload = new JSONParser(JSONParser.MODE_PERMISSIVE).parse(getStringPayload(request));
            if (payload == null) {
                log.debug("Payload is empty. Returning null");
                return null;
            }
            if (!(payload instanceof JSONObject || payload instanceof JSONArray)) {
                //Not throwing error since error should be formatted by manage toolkit
                log.error("Payload is not a JSON. Returning null");
                return null;
            }
            return payload;
//        } catch (ParseException e) {
//            //Not throwing error since error should be formatted by manage toolkit
//           // log.error(ConsentConstants.ERROR_PAYLOAD_PARSE + ". Returning null", e);
//            log.error( ". Returning null");
//            return null;
        } catch (Exception e) {
            //Not throwing error since error should be formatted by manage toolkit
            //log.error(e.getMessage() + ". Returning null", e);
            log.error( ". Returning null");
            return null;
        }
    }

    public static String getStringPayload(HttpServletRequest request)  {
        try {
            return IOUtils.toString(request.getInputStream());
        } catch (IOException e) {
           // log.error(ConsentConstants.ERROR_PAYLOAD_READ, e);
            log.error( ". Returning null");
            //throw new ConsentException(ResponseStatus.INTERNAL_SERVER_ERROR, ConsentConstants.ERROR_PAYLOAD_READ);
        }
        return null;
    }

    public static Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        return headers;
    }


}
