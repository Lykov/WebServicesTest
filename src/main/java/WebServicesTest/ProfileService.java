package WebServicesTest;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Exceptions.WebServicesException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

/**
 * Represents a web-service obtaining the person's profile by phone number
 */

// Private service - because our api should contains only one method - DetailService.
//@Path("ProfileService")
public class ProfileService {

    private final static Logger logger = Logger.getLogger(DetailService.class);
    private final Client client;

    @SuppressWarnings("WeakerAccess")
    public ProfileService() {
        client = Client.create();
    }

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "application/json" media type.
     * request format: /ProfileService?phone=11111111
     * @link Exceptions.WebServicesException if no phone number was specified.
     *
     * @return an application/json response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public Response getProfile(@QueryParam("phone") String phone) throws WebServicesException {
        if (phone == null || phone.equals("")) throw new WebServicesException(404, "No phone number provides!");
        logger.debug("Processing phone: " + phone);
        final String RANDOM_USER_API = "https://randomuser.me/api/?phone=+%s&inc=name,email";
        WebResource webResource = client.resource(String.format(RANDOM_USER_API, phone));
        ClientResponse response = webResource.accept("application/json")
                .get(ClientResponse.class);

        // Just because if I try to request randomuser.me from a several threads simultaneously, sometimes it give
        // me 503 Error with very informative message "Uh oh, something bad happened". Obviously, this service have
        // some kind of restrictions (because if I try to retrieve responses from google.com - all works just fine).

        // set up tries limit
        int tries = 10;
        while (response.getStatus() != 200 || tries-- == 0) {
            response = webResource.accept("application/json")
                    .get(ClientResponse.class);
        }

        // After several tries we couldn't get answer from randomuser.me
        if (response.getStatus() != 200 && tries == 0)
            return Response.status(response.getStatus()).entity("Bad request from randomuser.me").build();

        // All good
        try {
            // Parsing a response to JSONObject
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(response.getEntity(String.class));

            // Get name and email of person
            JSONObject person = (JSONObject) (((JSONArray) obj.get("results")).get(0));
            JSONObject name = (JSONObject) person.get("name");
            String email = (String) person.get("email");

            // Put them to json
            JSONObject result = new JSONObject();
            result.put("ctn", phone);
            result.put("name", name.get("last") + " " + name.get("first"));
            result.put("email", email);

            // And give it to client
            return Response.status(200).entity(result).build();
        } catch (ParseException ex) {
            logger.error("JSON ParseException at position " + ex.getPosition());
            return Response.status(500).entity(ex.getMessage()).build();
        }
    }
}