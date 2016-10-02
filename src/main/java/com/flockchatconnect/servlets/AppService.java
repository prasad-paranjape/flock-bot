package com.flockchatconnect.servlets;

import com.google.gson.Gson;
import com.flockchatconnect.core.AgentReplyObj;
import com.flockchatconnect.core.AppServiceObj;
import com.flockchatconnect.core.SlashCommandObj;
import com.flockchatconnect.helper.AppServices;
import com.flockchatconnect.helper.DBOperations;
import com.flockchatconnect.helper.Util;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

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
            Map obj = new Gson().fromJson(body, Map.class);
            if(obj.get("name").equals(AppServices.APP_INSTALL.toString())) {
                AppServiceObj obj1 = new Gson().fromJson(body, AppServiceObj.class);
                obj1.save();
            }
            else if(obj.get("name").equals(AppServices.APP_UNINSTALL.toString()))
            {
                AppServiceObj obj1 = new Gson().fromJson(body, AppServiceObj.class);
                obj1.remove();
            }
            else if(obj.get("name").equals(AppServices.CHAT_RECEIVE.toString()))
            {
                AgentReplyObj obj1 = new Gson().fromJson(body, AgentReplyObj.class);
                String agentIdFromflockId = DBOperations.getAgentIdFromflockId(obj1.getUserId());
                String senderId = DBOperations.getCustomerSenderId(agentIdFromflockId);
                Util.acknowledgeFacebookSave("9876", "facebook", senderId, obj1.getMessage().getText());


            }
            else if(obj.get("name").equals(AppServices.SLASH_COMMAND.toString()))
            {
                SlashCommandObj obj1 = new Gson().fromJson(body, SlashCommandObj.class);
                if(obj1.getCommand().equals("anon") && obj1.getText().equals("end")) {
                    String aid = DBOperations.getAgentIdFromflockId(obj1.getUserId());
                    String mapId = DBOperations.getMapIdFromAgentId(aid);
                    DBOperations.updateMapStatus(mapId, aid, "Completed");
                }
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
