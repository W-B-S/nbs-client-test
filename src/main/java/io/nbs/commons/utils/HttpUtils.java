package io.nbs.commons.utils;

import okhttp3.*;

import java.io.IOException;

/**
 * @Package : io.nbs.commons.utils
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/9-18:16
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class HttpUtils {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient();

    /**
     *
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public static Response post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    /**
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static Response get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        //request.header("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20");

        Response response = client.newCall(request).execute();
        return response;
    }
}
