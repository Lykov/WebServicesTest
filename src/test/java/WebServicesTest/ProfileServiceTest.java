package WebServicesTest;

import Exceptions.WebServicesException;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ProfileServiceTest {

    @Test
    public void singleRequest() {
        assertTrue(new ProfileService().getProfile("1234567890").getStatus() == 200);
    }

    @Test (expected = WebServicesException.class)
    public void illegalRequest() {
        try {
            assertTrue(new ProfileService().getProfile("").getStatus() != 200);
            fail("No WebServicesException was thrown");
        }
        catch (WebServicesException wse) {
            // That's just what we want - let the test pass with null
            assertTrue(new ProfileService().getProfile(null).getStatus() != 200);
            fail("No WebServicesException was thrown");
        }

    }
}
