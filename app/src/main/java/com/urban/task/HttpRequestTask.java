package com.urban.task;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.urban.appl.Settings;
import com.urban.data.ResponseError;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class HttpRequestTask extends AsyncTask<String, Void, Boolean> {

    private HttpTask task;

    private static final String ATTR_ERROR_CODE = "errorCode";
    private static final String ATTR_ERROR_TEXT = "errorText";
    private static final String ATTR_ERROR_TYPE = "errorType";

    public HttpRequestTask(HttpTask task) {
        super();
        this.task = task;
    }

    @Override
    protected Boolean doInBackground(String... params) {

        String json = getJSON(task.prepareRequestData(params));
        String response = null;
        try {
            response = POST(json);

            Gson gson = new Gson();
            Type collectionType = new TypeToken<HashMap<String, Object>>(){}.getType();
            HashMap<String, Object> map = gson.fromJson(response, collectionType);
            if (map.containsKey(ATTR_ERROR_CODE)) {
                ResponseError error = new JSONDeserializer<ResponseError>().deserialize(response, ResponseError.class);
                throw new UrbanServiceException(error);
            }

            task.handleResponse(response);
        } catch (UrbanServiceOfflineException e) {
            task.registerError(e.getMessage());
        } catch (UrbanServiceException e) {
            task.registerError(e.getMessage());
        } catch (Exception e) {
            task.registerError(e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        task.onPostExecute();
        super.onPostExecute(result);
    }

    private String POST(String json) throws UrbanServiceOfflineException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(Settings.getURLBase() + task.getURL());

        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        StringEntity se = null;
        HttpResponse httpResponse = null;
        try {
            se = new StringEntity(json);
            httpPost.setEntity(se);
            httpResponse = httpClient.execute(httpPost);
        } catch (Throwable e) {
            e.printStackTrace();
            throw new UrbanServiceOfflineException("Server is offline");
        }

        return readResponce(httpResponse);
    }

    private static String getJSON(Object obj) {
        return new JSONSerializer().exclude("*.class").deepSerialize(obj);
    }

    private static String readResponce(HttpResponse httpResponse) {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            String line = null;
            while((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }
}
