package helper;

import core.AppServiceObj;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by moiz.p on 24/09/16.
 */
public class DBOperations
{
    public static void closeConnection()
    {
        DB.closeConnection();
    }

    public static ArrayList<ArrayList<String>> getArrayListFromDB(String sql) throws SQLException
    {
        try (Statement st = DB.getConnection().createStatement(); ResultSet rs = st.executeQuery(sql))
        {
            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();
            ArrayList<ArrayList<String>> list = new ArrayList<>();
            while (rs.next())
            {
                ArrayList<String> row = new ArrayList<>();

                for (int i = 1; i <= cols; i++)
                {
                    row.add(rs.getString(i));
                }
                list.add(row);
            }

            return list;
        }
    }

    public static ArrayList<String> getListFromDB(String sql) throws SQLException
    {
        try (Statement st = DB.getConnection().createStatement())
        {
            try (ResultSet rs = st.executeQuery(sql))
            {
                ArrayList<String> list = new ArrayList<>();
                while (rs.next())
                {
                    list.add(rs.getString(1));
                }
                return list;
            }
        }
    }

    public static void saveAppService(AppServiceObj appServiceObj) throws SQLException
    {
        {
            if (!agentExists(appServiceObj.userId))
            {
                String sql = "insert into agent (flockUserid, flockUsertoken, flockName, companyId) values(?,?,?,?)";
                try (PreparedStatement preparedStatement = DB.getConnection().prepareStatement(sql))
                {
                    preparedStatement.setString(1, appServiceObj.userId);
                    preparedStatement.setString(2, appServiceObj.userToken);
                    preparedStatement.setString(3, appServiceObj.name);
                    preparedStatement.setInt(4, 1);
                    int i = preparedStatement.executeUpdate();
                    System.out.println(i + " records inserted");
                }
            }
        }
    }

    public static boolean agentExists(String userId) throws SQLException
    {
        {
            String sql = "SELECT count(*) FROM agent WHERE flockUserid=?";
            try (PreparedStatement preparedStatement = DB.getConnection().prepareStatement(sql))
            {
                preparedStatement.setString(1, userId);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next())
                {
                    return "1".equals(resultSet.getString(1));
                }
                System.out.println(userId + "exists");
            }
        }
        return false;
    }

    public static void getCompanyServiceParams(String companyId, String serviceId)
    {
        //TODO if required
    }

    public static void uninstallAppService(String userId) throws SQLException
    {

        {
            String sql = "UPDATE agent SET status='InActive' WHERE flockUserid= ?";
            try (PreparedStatement preparedStatement = DB.getConnection().prepareStatement(sql))
            {
                preparedStatement.setString(1, userId);
                int i = preparedStatement.executeUpdate();
                System.out.println(i + " record updated");
            }
        }
    }

    public static boolean saveFacebookMessage(String companyId, String serviceName, String sender_id, String name, String message) throws SQLException
    {
        String customerId=getFacebookCustomerId(sender_id);

        if(customerId==null){
            String serviceId=getServiceId(serviceName);
            customerId=insertCustomer(serviceId,name);
            insertFacebookCustomer(customerId,sender_id);
        }
        if(isChatting(customerId, serviceName, companyId)){

            saveMessageData(customerId,message,1,"Chatting");
        }
        else if(isWaiting(customerId, serviceName, companyId)){
            saveMessageData(customerId,message,1,"Not Started");

        }
        else{
            insertFirstInMap(customerId,"Not Started");
            saveMessageData(customerId,message,1,"Not Started");
        }

        return true;
    }

    public static void insertFirstInMap(String customerId, String status) throws SQLException
    {
        String sql="INSERT INTO messageMap(customerId,status) VALUES (?,?)";
        try (PreparedStatement preparedStatement = DB.getConnection().prepareStatement(sql))
        {
            preparedStatement.setInt(1, Integer.parseInt(customerId));
            preparedStatement.setString(2, status);
            int i = preparedStatement.executeUpdate();
        }
    }

    public static void insertFacebookCustomer(String customerId, String sender_id) throws SQLException
    {

        {
            String sql="INSERT INTO customerFacebook(facebookCustomerId,senderId) VALUES (?,?)";

            try (PreparedStatement preparedStatement = DB.getConnection().prepareStatement(sql))
            {
                preparedStatement.setInt(1, Integer.parseInt(customerId));
                preparedStatement.setString(2, sender_id);
                int i = preparedStatement.executeUpdate();
            }
        }
    }

    public static String insertCustomer(String serviceId, String name) throws SQLException
    {

        {
            String sql="INSERT INTO customer(serviceId,customerName) VALUES (?,?)";

            try (PreparedStatement preparedStatement = DB.getConnection().prepareStatement(sql))
            {
                preparedStatement.setInt(1, Integer.parseInt(serviceId));
                preparedStatement.setString(2, name);
                int i = preparedStatement.executeUpdate();
                String s = getListFromDB("SELECT max(customerId) FROM customer").get(0);

                return s;
            }
        }
    }

    public static String getServiceId(String serviceName) throws SQLException
    {
        return getListFromDB("SELECT serviceId FROM service WHERE serviceName='"+serviceName+"'").get(0);
    }

    public static boolean isWaiting(String customerId, String serviceName, String companyId) throws SQLException
    {
        if("facebook".equals(serviceName)){

            if(customerId==null){
                return false;
            }
            String sql="SELECT count(mapId) FROM messageMap WHERE customerId="+customerId + " AND status='Not Started'";
            return  "1".equals(getListFromDB(sql).get(0));
        }
        return false;
    }

    public static void saveMessageData(String customerId, String message, int inout, String status) throws SQLException
    {
        String sql="SELECT mapId FROM messageMap WHERE customerId="+customerId + " AND status='"+status+"'";
        ArrayList<String> listFromDB = getListFromDB(sql);
        String mapid=listFromDB.size()>0?listFromDB.get(0):null;


        {
            sql="INSERT INTO messageData(messageMapId,msg,inoutstatus) VALUES (?,?,?)";
            try (PreparedStatement preparedStatement = DB.getConnection().prepareStatement(sql))
            {
                preparedStatement.setString(1, mapid);
                preparedStatement.setString(2, message);
                preparedStatement.setInt(3, inout);
                int i = preparedStatement.executeUpdate();

            }
        }
    }

    public static boolean isChatting(String customerId, String serviceName, String companyId) throws SQLException
    {
        if("facebook".equals(serviceName)){

            if(customerId==null){
                return false;
            }
            String sql="SELECT count(mapId) FROM messageMap WHERE customerId="+customerId + " AND status='Chatting'";
            return  "1".equals(getListFromDB(sql).get(0));
        }
        return false;
    }

    public static String getCustomerSenderId(String agentFlockId) throws SQLException
    {
        String sql="SELECT customerId from messageMap WHERE agentId="+agentFlockId + " and status = "+ "'Chatting'";
        String cid = getListFromDB(sql).get(0);
        return getFacebookSenderId(cid);
    }

    public static String getMapIdFromAgentId(String agentFlockId) throws SQLException
    {
        String sql="SELECT mapId from messageMap WHERE agentId="+agentFlockId + " and status = "+ "'Chatting'";
        String mapId = getListFromDB(sql).get(0);
        return mapId;
    }

    public static String getAgentIdFromCustomerId(String customerId) throws SQLException
    {
        String sql="SELECT agentId from messageMap WHERE customerId="+customerId + " and status = "+ "'Chatting'";
        String aid = getListFromDB(sql).get(0);
        return getFlockIdFromAgentId(aid);
    }
    public static String getFacebookCustomerId(String sender_id) throws SQLException
    {
        ArrayList<String> listFromDB = getListFromDB("SELECT facebookCustomerId FROM customerFacebook WHERE senderId='" + sender_id + "'");
        return listFromDB.size()>0?listFromDB.get(0):null;
    }

    public static String getFacebookSenderId(String customer_id) throws SQLException
    {
        ArrayList<String> listFromDB = getListFromDB("SELECT senderId FROM customerFacebook WHERE facebookCustomerId=" + customer_id );
        return listFromDB.size()>0?listFromDB.get(0):null;
    }

    public static List<String> getQueuedCustomerList() throws SQLException
    {
        return getListFromDB("SELECT customerId, serviceId, customerName FROM customer WHERE customerId IN (SELECT customerId FROM messageMap WHERE status='Not Started')");
    }

    public static ArrayList<String> initiateChat(String agentFlockId, String customerId) throws SQLException
    {
        ArrayList<String> listFromDB = getListFromDB("SELECT mapId FROM messageMap WHERE status='Not Started' AND  customerId=" + customerId);
        String mapId = (listFromDB.size() > 0) ? listFromDB.get(0) : null;
        if (mapId == null)
        {
            return new ArrayList<>();
        }

        updateMapStatus(mapId, agentFlockId, "Chatting");
        return getUnreadChats(mapId);
    }

    public static ArrayList<String> getUnreadChats(String mapId) throws SQLException
    {
        return getListFromDB("SELECT msg FROM messageData WHERE messageMapId=" + mapId + " order by createdAt asc");
    }

    public static void updateMapStatus(String mapId, String agentFlockId, String status) throws SQLException
    {
        String agentId=getAgentIdFromflockId(agentFlockId);

        String sql = "UPDATE messageMap SET status='" + status + "' ,agentId="+agentId+"  WHERE mapId=" + mapId;
        DB.executeNonQuery(sql);
    }

    public static String getAgentIdFromflockId(String agentFlockId) throws SQLException
    {
        ArrayList<String> listFromDB = getListFromDB("SELECT agentId FROM agent WHERE flockUserId='" + agentFlockId + "'");
        return listFromDB.size()>0?listFromDB.get(0):null;
    }

    public static String getFlockIdFromAgentId(String aid) throws SQLException
    {
        ArrayList<String> listFromDB = getListFromDB("SELECT flockUserId FROM agent WHERE agentId=" + aid);
        return listFromDB.size()>0?listFromDB.get(0):null;
    }

}