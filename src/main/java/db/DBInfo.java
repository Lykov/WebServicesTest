package db;

import java.io.*;
import java.net.URL;
import java.util.Properties;

/**
 * Represents a singleton that stores a database connection information.
 * Thread-safe implementation using double-checked locking.
 */
public class DBInfo {
    static private DBInfo instance = null;
    private String port = null;
    private String database = null;
    private String ip = null;
    private String user = null;
    private String pass = null;
    private String jdbc = null;
    private String driver = null;

    private DBInfo() throws Exception {
        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("db.properties");
        if (resource == null)
            throw new Exception("Unable to find database configuration file");
        InputStream file = new FileInputStream(new File(resource.getFile())) ;
        Properties props = new Properties();
        props.load(file);
        port = props.getProperty("PORT");
        ip = props.getProperty("IP");
        database = props.getProperty("DATABASE");
        user = props.getProperty("USER");
        pass = props.getProperty("PASS");
        jdbc = props.getProperty("JDBC");
        driver = props.getProperty("DRIVERNAME");

        // All properties are not null and not empty
        boolean nullProp = database == null || user == null || pass == null || jdbc == null || driver == null;
        if (nullProp || database.equals("") || user.equals("") || pass.equals("") || jdbc.equals("") || driver.equals(""))
            throw new Exception("Invalid database configuration file syntax");

        file.close();
    }

    public static DBInfo getInstance() throws Exception {
        // Double-checked locking
        if(instance == null){
            synchronized (DBInfo.class) {
                if(instance == null){
                    instance = new DBInfo();
                }
            }
        }
        return instance;
    }
    //-------------------------Accessors---------------------
    public String getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getIp() {
        return ip;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getJdbc() {
        return jdbc;
    }

    public String getDriver() {
        return driver;
    }
}