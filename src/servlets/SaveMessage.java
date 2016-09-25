package servlets;

import helper.DBOperations;
import helper.Util;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by moiz.p on 24/09/16.
 */
public class SaveMessage extends HttpServlet
{
    private static final Logger LOGGER = Logger.getLogger(SaveMessage.class);
    String homeUrl = "https://shashwatkumar.com";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        processRequest(request, response);
    }

    void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        System.out.println("In SaveMessage ");
        LOGGER.log(Priority.INFO, request);

        String serviceName = request.getParameter("service_name");
        String companyId = request.getParameter("company_id");
        String sender_id = request.getParameter("sender_id");
        String name = "";//request.getParameter("name");
        String message = request.getParameter("message");
        if (serviceName == null || sender_id == null || companyId == null || message == null)
        {
            return;
        }
        try {
            if (DBOperations.saveFacebookMessage(companyId, serviceName, sender_id, name, message))
            {
                try
                {
                    acknowledgeFacebookSave(companyId, serviceName, sender_id, "Acknowledged");
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        ServletOutputStream out = response.getOutputStream();
        out.print("Leaving CompanyServiceParams");

    }

    private void acknowledgeFacebookSave(String companyId, String serviceName, String sender_id, String acknowledged) throws SQLException, IOException
    {
        String token = getCompanyFacebookToken(companyId);
        String url = homeUrl + "/out?service_name=" + URLEncoder.encode(serviceName) + "&data[app_id]=1233409356702568&data[sender_id]=" + URLEncoder.encode(sender_id) + "&data[message]=" + URLEncoder.encode(acknowledged) + "&data[token]=" + URLEncoder.encode(token) + "&data[company_id]=" + URLEncoder.encode(companyId);
        Util.sendRequest(url);
    }

    private String getCompanyFacebookToken(String companyId) throws SQLException
    {
        ArrayList<String> listFromDB = DBOperations.getListFromDB("SELECT token FROM serviceFacebook WHERE companyId=" + companyId);
        return listFromDB.size()>0?listFromDB.get(0):"";
    }
}