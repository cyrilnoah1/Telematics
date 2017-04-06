package com.example.rapid.telematics.common;

import android.content.Context;

import com.example.rapid.telematics.server.RetroHelper;
import com.example.rapid.telematics.server.ServiceHelper;

import java.util.HashMap;

/**
 * Utils contains all the required common methods.
 */
public class Utils implements GlobalConstants {


    /**
     * Method to make service calls.
     * @param isIncludeAuthTokenHeader Boolean to determine the inclusion of AuthToken.
     * @param ctx Context.
     * @return
     */
    public ServiceHelper getBaseClassService(boolean isIncludeAuthTokenHeader, Context ctx) {

        HashMap<String, String> headersMap = new HashMap<>();

        headersMap.put("X-APP-VERSION", "1.0.0");
        headersMap.put("os-type", "android");
        headersMap.put("role", "user");


        if (isIncludeAuthTokenHeader) {
            SharedPreferenceHandle sharedPreferenceHandle = SharedPreferenceHandle.getSharedPreferenceHandle(ctx);
            headersMap.put("Authorization", sharedPreferenceHandle.getString(AUTH_TOKEN, ""));
        }

        return RetroHelper.getAdapter(ctx, "", headersMap).create(ServiceHelper.class);
    }
}
