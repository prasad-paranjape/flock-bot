package servlets;

import helper.DBOperations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

        //TODO  
        int mapId = DBOperations.initiateChat(userId, customerId);
        DBOperations.getConversations(mapId);
        return ;

    }
}
