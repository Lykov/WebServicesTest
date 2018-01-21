package Exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class NoPhoneNumberProvidesException extends WebApplicationException
{
    private static final long serialVersionUID = 1L;

    public NoPhoneNumberProvidesException() {
        super(Response.status(404).entity("Error: no phone number provides!!")
                .type("text/plain").build());
    }
}
