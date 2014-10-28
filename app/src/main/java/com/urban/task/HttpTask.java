package com.urban.task;



public interface HttpTask {

    public Object prepareRequestData(String... params);

    public void handleResponse(String response);

    public String getURL();

    public void onPostExecute();

    public void registerError(String error);
}
