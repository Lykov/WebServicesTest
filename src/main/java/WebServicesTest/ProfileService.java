package WebServicesTest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Exceptions.NoPhoneNumberProvidesException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

/**
 * Represents a web-service obtaining the person's profile by phone number
 */
@Path("ProfileService")
public class ProfileService {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "application/json" media type.
     * request format: /ProfileService?phone=11111111
     * @link Exceptions.NoPhoneNuumberException if no phone number was specified.
     *
     * @return String that will be returned as an application/json response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public Response getProfile(@QueryParam("phone") String phone) throws NoPhoneNumberProvidesException{
        if (phone == null || phone.equals("")) throw new NoPhoneNumberProvidesException();
        System.out.println(phone);
        Client client = Client.create();
        final String RANDOM_USER_API = "https://randomuser.me/api/?phone=+%s&inc=name,email";
        WebResource webResource = client.resource(String.format(RANDOM_USER_API, phone));

        ClientResponse response = webResource.accept("application/json")
                .get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

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
            result.put("name", name.get("last") + " " + name.get("first"));
            result.put("email", email);

            // And give it to client
            return Response.status(200).entity(result).build();
        } catch (ParseException ex) {
            System.out.println("position: " + ex.getPosition());
            return Response.status(500).entity(ex.getMessage()).build();
        }
    }
}