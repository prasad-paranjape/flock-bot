package com.flockchatconnect.helper;

import com.flockchatconnect.core.Bot;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by shashwat.ku on 25/9/16.
 */
public class Util {
    private static String homeUrl = "http://52.25.85.77";

    public static String getRequestBody(HttpServletRequest request) throws IOException {
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
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }

        body = stringBuilder.toString();
        return body;
    }
    private static boolean sendRequest(String endpoint) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(endpoint);
        HttpResponse response = (HttpResponse) client.execute(httpGet);

        int status = response.getStatusLine().getStatusCode();
        if (status != 200) {
            System.out.println("http get call failed");
            return false;
        }
        return true;
    }

    public static String sendJsonPostRequest(String url, String data) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);

        StringEntity se = new StringEntity(data, ContentType.APPLICATION_JSON);

        post.setEntity(se);
        post.addHeader("Content-Type", "application/json");
        post.addHeader("X-Flock-User-Token", Bot.TOKEN);

        HttpResponse response = client.execute(post);
        return EntityUtils.toString(response.getEntity());
    }

    public static void acknowledgeFacebookSave(String companyId, String serviceName, String sender_id, String acknowledged) throws SQLException, IOException
    {
        String token = getCompanyFacebookToken(companyId);
        String url = homeUrl + "/out?service_name=" + URLEncoder.encode(serviceName, StandardCharsets.UTF_8.toString()) + "&data[app_id]=1233409356702568&data[sender_id]=" + URLEncoder.encode(sender_id, StandardCharsets.UTF_8.toString()) + "&data[message]=" + URLEncoder.encode(acknowledged, StandardCharsets.UTF_8.toString()) + "&data[token]=" + URLEncoder.encode(token, StandardCharsets.UTF_8.toString()) + "&data[company_id]=" + URLEncoder.encode(companyId, StandardCharsets.UTF_8.toString());
        Util.sendRequest(url);
    }

    private static String getCompanyFacebookToken(String companyId) throws SQLException
    {
        ArrayList<String> listFromDB = DBOperations.getListFromDB("SELECT token FROM serviceFacebook WHERE companyId=" + companyId);
        return listFromDB.size()>0?listFromDB.get(0):"";
    }
}
