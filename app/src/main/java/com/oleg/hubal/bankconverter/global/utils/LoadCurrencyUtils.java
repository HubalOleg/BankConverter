package com.oleg.hubal.bankconverter.global.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oleg.hubal.bankconverter.global.constants.LoadConstants;
import com.oleg.hubal.bankconverter.model.Organization;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by User on 18.01.2017.
 */

public class LoadCurrencyUtils {

    private static final String TAG = "LoadCurrencyUtils";

    public static Response getResponseFromRequest() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(LoadConstants.CURRENCY_JSON_URL)
                .build();

        Response response = client.newCall(request).execute();
        return response;
    }

    public static String getDataDate(String responseBody) throws JSONException {
        JSONObject responseJSON = new JSONObject(responseBody);
        String currencyDate = responseJSON.getString(LoadConstants.KEY_DATE_JSON);
        return currencyDate;
    }

    public static boolean isDataUpdated(String currentDate, String responseDate) {
        return !currentDate.equals(responseDate);
    }

    public static List<Organization> getOrganizations(String responseBody) throws JSONException {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Organization>>(){}.getType();

        String organizationArray = getArrayFromResponse(responseBody, LoadConstants.KEY_ORGANIZATIONS_JSON);
        List<Organization> organizations = (List<Organization>) gson.fromJson(organizationArray, listType);

        return organizations;
    }

    public static String getArrayFromResponse(String response, String arrayKey) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray(arrayKey);

        return jsonArray.toString();
    }

}
