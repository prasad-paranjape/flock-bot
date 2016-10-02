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
public class DB
{
    public static final Logger logger = Logger.getLogger(DB.class.getName());
    public static Connection c = null;
    public static String jdbcDriver = null;
    public static String dbUrl = null;
    public static String DBUSER = null;
    private static String DBPASS = null;

    static
    {
        try
        {
            Properties properties = loadPropertiesFile();
            jdbcDriver = properties.getProperty("JDBC_DRIVER");
            dbUrl = properties.getProperty("DB_URL");
            DBUSER = properties.getProperty("DB_USER");
            DBPASS = properties.getProperty("DB_PASSWORD");
        } catch (IOException e)
        {
            logger.error("Error in reading propertie", e);
        }
    }

    public static Connection getConnection()
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
                    } catch (Exception e)
                    {
                        logger.error("Unable to connect to Database!!", e);
                    }
                }
            }
        }
        return c;
    }

    public static void executeNonQuery(String sql) throws SQLException
    {
        try (Statement stmt = c.createStatement())
        {
            stmt.executeUpdate(sql);
        }
    }

    public static void closeConnection()
    {
        try
        {
            c.close();
        } catch (SQLException e)
        {
            logger.error("From closeConnection", e);
        }
    }

    public static Properties loadPropertiesFile() throws IOException
    {
        Properties prop = new Properties();
//        InputStream in = new FileInputStream("/db.properties");
        InputStream in = DB.class.getResourceAsStream("db.properties");
        prop.load(in);
        in.close();
        return prop;
    }
}
