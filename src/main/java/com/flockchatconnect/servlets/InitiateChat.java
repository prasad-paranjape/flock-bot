package com.flockchatconnect.servlets;

import com.flockchatconnect.core.Bot;
import com.flockchatconnect.helper.DBOperations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by shashwat.ku on 25/9/16.
 */
public class InitiateChat extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        processRequest( request,  response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        processRequest( request,  response);
    }

    void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String userId = request.getParameter("userId");
        String customerId = request.getParameter("customerId");

        ArrayList<String> messages = null;
        try {
            messages = DBOperations.initiateChat(userId, customerId);
            messages.add("Hi");
            messages.add("How are you");
            new Bot().sendMessage(userId, messages);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        ArrayList<String> messages = new ArrayList<>();
        return ;

    }
}
