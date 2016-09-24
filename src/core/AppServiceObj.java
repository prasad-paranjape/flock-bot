package core;

import helper.DBOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by prasad.p on 24/09/16.
 */
public class AppServiceObj {
    private String userToken;
    private String name;
    private String userId;

    public void save() throws SQLException {
        Connection connection = DBOperations.DB.getConnection();
        String sql="insert into agents (flock_userid, flock_usertoken, flock_name) values(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,userId);
        preparedStatement.setString(2,userToken);
        preparedStatement.setString(3,name);
        int i = preparedStatement.executeUpdate();
        System.out.println(i + " records inserted");
    }

}
