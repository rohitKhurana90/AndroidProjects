package com.isha.delhi.attendance;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class IshaHttp {

    private static final String MAIN_URL = "https://script.google.com/macros/s/AKfycbxZHFYO5ibZpcJMCMBekYB3f_FziOFEjIXje3DO440AIxt283c/exec";
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private IshaHttp(){}

    public static JsonObject postexecute(Object requestBody, Map<String, String> queryParamsMap) {

        String json = new Gson().toJson(requestBody);
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(getHttpRequestBuilder(MAIN_URL, queryParamsMap).build()).post(body).build();
        return execute(request);

    }


    public static JsonObject getexecute(Map<String, String> queryParamsMap) {
        Request request = new Request.Builder().url(getHttpRequestBuilder(MAIN_URL, queryParamsMap).build()).get().build();
        return execute(request);
    }

    private static JsonObject execute(Request request) {
        try {
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(response.body().string()).getAsJsonObject();
            return jsonObject;
        } catch (IOException e) {

        }
        return null;
    }

    private static HttpUrl.Builder getHttpRequestBuilder(String url, Map<String, String> params) {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder httpBuider = HttpUrl.parse(url).newBuilder();
        if (params != null) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                httpBuider.addQueryParameter(param.getKey(), param.getValue());
            }
        }
        return httpBuider;
    }


}
