package core;

import helper.DBOperations;

import java.sql.SQLException;

/**
 * Created by prasad.p on 24/09/16.
 */
public class AppServiceObj {
    public String userToken;
    public String name;
    public String userId;
    DBOperations db=new DBOperations();

    public void save() throws SQLException {
        db.saveAppService(this);
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
