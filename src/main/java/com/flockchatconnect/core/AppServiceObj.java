package com.flockchatconnect.core;

import com.flockchatconnect.helper.DBOperations;
import org.apache.log4j.Logger;

import java.sql.SQLException;

/**
 * Created by prasad.p on 24/09/16.
 */
public class AppServiceObj {
    private static Logger logger = Logger.getLogger(AppServiceObj.class.getName());
    public String userToken;
    public String name;
    public String userId;
    DBOperations db=new DBOperations();

    public void save() {
        try
        {
            db.saveAppService(this);
        } catch (SQLException e)
        {
            logger.debug("Error message in saving ", e);
        }
    }

    public String getUserToken() {
        return userToken;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public void remove() throws SQLException
    {
        db.uninstallAppService(userId);
    }
}
