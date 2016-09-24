package servlets;

import helper.DBOperations;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by moiz.p on 24/09/16.
 */
public class SaveMessage extends HttpServlet
{
    private static final Logger LOGGER = Logger.getLogger(SaveMessage.class);
    static DBOperations db=new DBOperations();
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
        System.out.println("In SaveMessage ");
        LOGGER.log(Priority.INFO, request);

        String serviceId = request.getParameter("service_id");
        String companyId = request.getParameter("company_id");
        String sender_id = request.getParameter("sender_id");
        String message = request.getParameter("message");

//        db.getSaveFacebookMessage(companyId,serviceId);


        ServletOutputStream out = response.getOutputStream();
        out.print("Leaving CompanyServiceParams");

    }
}
