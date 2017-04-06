package com.example.rapid.telematics.server;


import android.content.Context;
import android.util.Log;


import com.example.rapid.telematics.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroHelper {
    private static final String TAG = RetroHelper.class.getSimpleName();

    public static Retrofit getAdapter(Context context, String serverUrl, HashMap<String, String> headers) {

        String url = context.getResources().getString(R.string.server_url) + serverUrl;
        Log.e("RetroHelper", "url : " + url);
        OkHttpClient client = getRequestInterceptor(headers);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


        return retrofit;
    }

    private static OkHttpClient getRequestInterceptor(final HashMap<String, String> headers) {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder();


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.interceptors().add(logging);

        httpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request.Builder requestBuilder = chain.request().newBuilder();
                Iterator<Map.Entry<String, String>> entrysList = headers.entrySet().iterator();
                while (entrysList.hasNext()) {
                    Map.Entry<String, String> entry = entrysList.next();
                    requestBuilder.addHeader(entry.getKey(), entry.getValue()).build();
                    Log.d(TAG, "intercept: " + "Key = " + entry.getKey() + ", Value = " + entry.getValue());
                }
                return chain.proceed(requestBuilder.build());
            }
        });
        return httpClient.build();
    }

}
