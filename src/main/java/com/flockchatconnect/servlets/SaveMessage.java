package com.flockchatconnect.servlets;

import com.flockchatconnect.core.Bot;
import com.flockchatconnect.helper.DBOperations;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by moiz.p on 24/09/16.
 */
public class SaveMessage extends HttpServlet
{
    private static final Logger LOGGER = Logger.getLogger(SaveMessage.class);
    String homeUrl = "http://52.25.85.77";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try {
            processRequest(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try {
            processRequest(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
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
        try
        {
            if (DBOperations.saveFacebookMessage(companyId, serviceName, sender_id, name, message))
            {
                String facebookCustomerId = DBOperations.getFacebookCustomerId(sender_id);

                String flockIdFromAgentId = DBOperations.getAgentIdFromCustomerId(facebookCustomerId);
                ArrayList<String> array = new ArrayList<String>();
                array.add(message);
                new Bot().sendMessage(flockIdFromAgentId, array);

            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }


        ServletOutputStream out = response.getOutputStream();
        out.print("Leaving CompanyServiceParams");

    }
}