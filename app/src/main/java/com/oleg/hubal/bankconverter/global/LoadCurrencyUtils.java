package com.oleg.hubal.bankconverter.global;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by User on 18.01.2017.
 */

public class LoadCurrencyUtils {

    public static Response getResponseFromRequest(String requestUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(requestUrl)
                .build();

        Response response = client.newCall(request).execute();
        return response;
    }

    public static String getDateFromResponseBody(String responseBody) throws JSONException {
        JSONObject responseJSON = new JSONObject(responseBody);
        String currencyDate = responseJSON.getString(Constants.KEY_DATE_JSON);
        return currencyDate;
    }

    public static boolean isDataUpdated(String currentDate, String responseDate) {
        return !currentDate.equals(responseDate);
    }

}
