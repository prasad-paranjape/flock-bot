package servlets;

import com.google.gson.Gson;
import core.AppServiceObj;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
        System.out.println("Hitted");
        LOGGER.log(Priority.INFO, request);
        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        System.out.println(body);
        try {
            AppServiceObj obj = new Gson().fromJson(body, AppServiceObj.class);
            obj.save();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        ServletOutputStream out = response.getOutputStream();
        out.print("AppService");

    }
}
