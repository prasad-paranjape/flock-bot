package core;

import com.google.gson.Gson;
import helper.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by shashwat.ku on 25/9/16.
 */
public class Bot {
    public static String url = "https://api.flock.co/v1/chat.sendMessage";
    public static String TOKEN = "f737dcfd-9c17-4a94-8a04-9470e02bd78f";

    public String sendMessage(String userId, ArrayList<String> message)
    {
        StringBuilder msgs = new StringBuilder();
        for(String msg : message)
        {
            msgs.append(msg + "\n");
        }
        Hashtable jsonMap = new Hashtable();
        Hashtable innermap = new Hashtable();
        innermap.put("to", userId);
        innermap.put("text", msgs.toString());
        jsonMap.put("message", innermap);

        try {
            String messageId = Util.sendJsonPostRequest(url, new Gson().toJson(jsonMap));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
