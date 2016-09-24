package core;

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

}
