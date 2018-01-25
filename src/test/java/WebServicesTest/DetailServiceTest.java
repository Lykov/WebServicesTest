package WebServicesTest;

import Exceptions.WebServicesException;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DetailServiceTest {

    private DetailService service;

    public DetailServiceTest() throws Exception {
        service = new DetailService();
    }

    /*
    This test checks actual DetailService functionality: it performs several parallel requests to
    ProfileService (phone numbers are generating in loop)
     */
    @Test
    public void multipleRequest() {
        ArrayList<String> array = new ArrayList<>();
        for (int i = 100000; i < 100010; i++)
            array.add(i+"");
        ProfileService service = new ProfileService();
        array.parallelStream().forEach((phone) -> assertTrue(service.getProfile(phone).getStatus() == 200));
    }

    @Test (expected = WebServicesException.class)
    public void illegalRequest() {
        try {
            assertTrue(service.getProfiles("").getStatus() != 200);
            fail("No WebServicesException was thrown");
        }
        catch (WebServicesException wse) {
            // That's just what we want - let the test pass with null
            assertTrue(service.getProfiles(null).getStatus() != 200);
            fail("No WebServicesException was thrown");
        }

    }
}
