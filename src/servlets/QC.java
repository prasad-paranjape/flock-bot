package servlets;

import com.google.gson.Gson;
import core.AppServiceObj;
import core.LauncherEventObj;
import helper.Util;
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
public class QC extends HttpServlet
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
    void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        LOGGER.log(Priority.INFO, request);
        String jsonParams = java.net.URLDecoder.decode(request.getParameter("flockEvent"));
//        String body = Util.getRequestBody(request);
        LauncherEventObj obj = new Gson().fromJson(jsonParams, LauncherEventObj.class);

        ServletOutputStream out = response.getOutputStream();
        out.print("QC");

    }
}
