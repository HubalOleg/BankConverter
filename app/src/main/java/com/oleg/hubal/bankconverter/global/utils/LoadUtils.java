package com.oleg.hubal.bankconverter.global.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.oleg.hubal.bankconverter.global.constants.LoadConstants;
import com.oleg.hubal.bankconverter.model.data.Date;

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

    public static boolean isDataUpdated(Date currentDate, Date responseDate) {
        return !currentDate.getDate().equals(responseDate.getDate());
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
