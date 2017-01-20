package com.oleg.hubal.bankconverter.global.utils;

import com.oleg.hubal.bankconverter.global.constants.LoadConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by User on 18.01.2017.
 */

public class LoadUtils {

    public static Response getResponseFromRequest() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(LoadConstants.CURRENCY_JSON_URL)
                .build();

        Response response = client.newCall(request).execute();
        return response;
    }

    public static JSONObject getJSONObjectFromResponse(Response response) throws IOException, JSONException {
        String responseBody = response.body().string();

        return new JSONObject(responseBody);
    }
    public static boolean isDataUpdated(String currentDate, String responseDate) {
        return !currentDate.equals(responseDate);
    }

}
