package com.flockchatconnect.servlets;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by moiz.p on 24/09/16.
 */
public class Canned extends HttpServlet
{
    Logger LOGGER= Logger.getLogger("Config");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        processRequest( request,  response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        processRequest( request,  response);
    }
    void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        LOGGER.log(Priority.INFO, request);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/Canned.jsp");
        requestDispatcher.forward(request,response);

    }
}
