package com.flockchatconnect.helper;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by shashwat on 29/9/16.
 */
class DB
{
    public static final Logger logger = Logger.getLogger(DB.class.getName());
    private static Connection c = null;
    private static String jdbcDriver = null;
    private static String dbUrl = null;
    private static String DBUSER = null;
    private static String DBPASS = null;
    private static boolean autoCommit = true;

    static
    {
        Properties properties = loadPropertiesFile();
        jdbcDriver = properties.getProperty("JDBC_DRIVER");
        dbUrl = properties.getProperty("DB_URL");
        DBUSER = properties.getProperty("DB_USER");
        DBPASS = properties.getProperty("DB_PASSWORD");
        autoCommit = Boolean.parseBoolean(properties.getProperty("AUTO_COMMIT"));
    }

    static Connection getConnection()
    {
        logger.debug("Getting connection");
        if (c == null)
        {
            synchronized (DB.class)
            {
                if (c == null)
                {
                    try
                    {
                        Class.forName(jdbcDriver);
                        c = DriverManager.getConnection(dbUrl, DBUSER, DBPASS);
                        c.setAutoCommit(autoCommit);
                    } catch (Exception e)
                    {
                        logger.error("Unable to connect to Database!!", e);
                    }
                }
            }
        }
        return c;
    }

    static void executeNonQuery(String sql) throws SQLException
    {
        try (Statement stmt = c.createStatement())
        {
            stmt.executeUpdate(sql);
        }
    }

    static void closeConnection()
    {
        try
        {
            c.close();
        } catch (SQLException e)
        {
            logger.error("From closeConnection", e);
        }
    }

    private static Properties loadPropertiesFile()
    {
        Properties prop = new Properties();
        try
        {
//        InputStream in = new FileInputStream("/db.properties");
            InputStream in = DB.class.getResourceAsStream("/db.properties");
            prop.load(in);
            in.close();
        }
        catch (IOException e)
        {
            logger.error("Cannot read properties file", e);
        }
        return prop;
    }
}
