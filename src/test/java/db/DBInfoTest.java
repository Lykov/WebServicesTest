package db;

import org.junit.Before;
import org.junit.Test;
import sun.security.pkcs11.Secmod;

import static org.junit.Assert.*;

public class DBInfoTest {

    private DBInfo dbInfo;

    public DBInfoTest() throws Exception {
        dbInfo = DBInfo.getInstance();
    }

    @Before
    public void before(){
        assertNotNull(dbInfo);
    }

    @Test
    public void getPort() {
        assertNotNull(dbInfo.getPort());
    }

    @Test
    public void getDatabase() {
        assertNotNull(dbInfo.getDatabase());
    }

    @Test
    public void getIp() {
        assertNotNull(dbInfo.getIp());
    }

    @Test
    public void getUser() {
        assertNotNull(dbInfo.getUser());
    }

    @Test
    public void getPass() {
        assertNotNull(dbInfo.getPass());
    }

    @Test
    public void getJdbc() {
        assertNotNull(dbInfo.getJdbc());
    }

    @Test
    public void getDriver() {
        assertNotNull(dbInfo.getDriver());
    }
}