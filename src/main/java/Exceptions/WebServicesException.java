package Exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * Show a client an error message with specific status
 */
@Provider
public class WebServicesException extends WebApplicationException
{
    private static final long serialVersionUID = 1L;

    public WebServicesException(int status, String message) {
        super(Response.status(status).entity("Error: "+message)
                .type("text/plain").build());
    }
}
