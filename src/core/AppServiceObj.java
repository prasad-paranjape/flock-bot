package core;

import co.flock.www.FlockApiClient;
import co.flock.www.model.User;
import helper.DBOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by prasad.p on 24/09/16.
 */
public class AppServiceObj {
    public String userToken;
    public String name;
    public String userId;

    public void save() throws SQLException {
        DBOperations db=new DBOperations();
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

}
