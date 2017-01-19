package com.oleg.hubal.bankconverter.global.utils;

import com.google.gson.Gson;
import com.oleg.hubal.bankconverter.global.constants.LoadConstants;
import com.oleg.hubal.bankconverter.model.Currency;
import com.oleg.hubal.bankconverter.model.Organization;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by User on 18.01.2017.
 */

public class LoadUtils {

    public static List<Organization> loadOrganizationList(String currentDate) throws IOException, JSONException {
        List<Organization> organizationList;

        Response response = getResponseFromRequest();

        if (response.isSuccessful()) {
            return null;
        }

        String responseBody = response.body().string();
        JSONObject responseJSON = new JSONObject(responseBody);
        String responseDate = getDataDate(responseJSON);

        if (!isDataUpdated(currentDate, responseDate)) {
            return null;
        }

        return null;
    }

    public static Response getResponseFromRequest() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(LoadConstants.CURRENCY_JSON_URL)
                .build();

        Response response = client.newCall(request).execute();
        return response;
    }

    public static String getDataDate(JSONObject responseJSON) throws JSONException {
        String currencyDate = responseJSON.getString(LoadConstants.KEY_DATE_JSON);
        return currencyDate;
    }

    public static boolean isDataUpdated(String currentDate, String responseDate) {
        return !currentDate.equals(responseDate);
    }

    public static List<Organization> getOrganizationList(JSONObject responseJSON) throws JSONException {
        Gson gson =  new Gson();
        List<Organization> organizationList = new ArrayList<>();

        JSONArray organizationArray = responseJSON.getJSONArray(LoadConstants.KEY_ORGANIZATIONS_JSON);

        for (int i = 0; i < organizationArray.length(); i++) {
            JSONObject organizationJSONObject = organizationArray.getJSONObject(i);

            Organization organization = gson.fromJson(organizationJSONObject.toString(), Organization.class);

            List<Currency> currencyList = getCurrencyList(organizationJSONObject);
            organization.setCurrency(currencyList);

            organizationList.add(organization);
        }

        return organizationList;
    }

    public static List<Currency> getCurrencyList(JSONObject organizationJSONObject) throws JSONException {
        Gson gson =  new Gson();
        List<Currency> currencyList = new ArrayList<>();

        String organizationId = organizationJSONObject.getString(LoadConstants.KEY_ID_JSON);

        JSONObject currenciesJSONObject = organizationJSONObject.getJSONObject(LoadConstants.KEY_CURRENCIES_JSON);
        Iterator<String> keysIterator = currenciesJSONObject.keys();

        while (keysIterator.hasNext()) {
            String key = keysIterator.next();
            JSONObject currencyJSONObject = currenciesJSONObject.getJSONObject(key);
            Currency currency = gson.fromJson(currencyJSONObject.toString(), Currency.class);
            currency.setNameAbbreviation(key);
            currency.setOrganizationId(organizationId);
            currencyList.add(currency);
        }

        return currencyList;
    }

    public static String getArrayFromResponse(JSONObject responseJSON, String arrayKey) throws JSONException {
        JSONArray jsonArray = responseJSON.getJSONArray(arrayKey);

        return jsonArray.toString();
    }

}
