package WebServicesTest;

import Exceptions.WebServicesException;
import db.DBInfo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.apache.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.ArrayList;

/**
 * Represents a web-service obtaining the person's phone number and profile by cell_id
 */
@Path("DetailService")
public class DetailService {

    private final static Logger logger = Logger.getLogger(DetailService.class);

    public DetailService() throws Exception {
        // Load DBMS driver
        Class.forName(DBInfo.getInstance().getDriver()).newInstance();
    }

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "application/json" media type.
     * request format: /DetailService?cell_id=11111111
     * @see Exceptions.WebServicesException if no cell_id was specified.
     *
     * @return an application/json response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public Response getProfiles(@QueryParam("cell_id") String cell_id) throws WebServicesException {
        if (cell_id == null || cell_id.equals("")) throw new WebServicesException(404, "No cell_id provides!");
        logger.debug("Processing cell_id: "+cell_id);
        try {

            // Retrieving data from database
            Connection connection = DriverManager.getConnection(DBInfo.getInstance().getJdbc());
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ctn FROM sessions WHERE cell_id = " + cell_id);
            ArrayList<String> array = new ArrayList<>();

            // Put it to array
            while (resultSet.next()) {
                array.add(resultSet.getString(1));
            }

            connection.close();
            statement.close();
            resultSet.close();

            JSONArray results = new JSONArray();

            ProfileService service = new ProfileService();
            array.parallelStream().forEach((phone) -> results.add(service.getProfile(phone).getEntity()));

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("total", results.size());
            jsonObject.put("results", results);

            return Response.status(200).entity(jsonObject).build();
        } catch (Exception e) {
            logger.error(e.toString());
            return Response.status(500).entity(e.toString()).build();
        }
    }

}
