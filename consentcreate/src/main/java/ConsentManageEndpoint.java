import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ConsentManageEndpoint {

    private static final Log log = LogFactory.getLog(ConsentManageEndpoint.class);
    private static final String CLIENT_ID_HEADER = "x-wso2-client-id";



    @POST
    @Path("/{s:.*}")
    @Consumes({"application/json; charset=utf-8"})
    @Produces({"application/json; charset=utf-8"})
    public Response managePost(@Context HttpServletRequest request, @Context HttpServletResponse response,
                               @Context UriInfo uriInfo) throws ConsentException {

        ConsentManageData consentManageData = new ConsentManageData(ConsentUtils.getHeaders(request),
                ConsentUtils.getPayload(request), uriInfo.getQueryParameters(),
                uriInfo.getPathParameters().getFirst("s"), request, response);
        consentManageData.setClientId("KXugxO6e2ycVotKNwzG21J2QqEQa");

        DefaultConsentManageHandler.handlePost(consentManageData); //changed
//        return Response.status(200).
//                entity("shamly").build();
       return sendResponse(consentManageData);
    }
    private Response sendResponse(ConsentManageData consentManageData) throws ConsentException {
        if (consentManageData.getPayload() != null
                || consentManageData.getResponseStatus() != null
        ) {
            return Response.status(consentManageData.getResponseStatus().getStatusCode()).entity(consentManageData.getResponsePayload().toString()).build();
        }
        else {
            log.debug("Response status or payload unavailable. Throwing exception");
            throw new ConsentException(ResponseStatus.INTERNAL_SERVER_ERROR, "Response data unavailable");
        }
    }



}
