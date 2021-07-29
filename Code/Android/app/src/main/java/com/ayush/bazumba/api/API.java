package com.ayush.bazumba.api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import cz.msebera.android.httpclient.Header;

public class API {

    public void get(String url, RequestParams params, final APIResponse handler) {
        AsyncHttpClient client
                = new AsyncHttpClient();
        client.get(url, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String response = new String(responseBody);
                        System.out.println(response);
                        handler.response(response, null);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        System.out.println(error.getLocalizedMessage());
                        handler.response(null, error);
                    }
                });
    }

}
