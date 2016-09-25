package servlets;

import com.google.gson.Gson;
import core.AppServiceObj;
import helper.AppServices;
import helper.Util;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by moiz.p on 24/09/16.
 */
public class AppService extends HttpServlet
{
    private static final Logger LOGGER = Logger.getLogger(AppService.class);

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
        String body = Util.getRequestBody(request);
        System.out.println(body);
        try {
            AppServiceObj obj = new Gson().fromJson(body, AppServiceObj.class);
            if(obj.getName().equals(AppServices.APP_INSTALL.toString())) {
                obj.save();
            }
            else if(obj.getName().equals(AppServices.APP_UNINSTALL))
            {
                obj.remove();
            }
        }
        catch (Exception e)
        {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }

        ServletOutputStream out = response.getOutputStream();
        out.print("AppService");

    }

}
