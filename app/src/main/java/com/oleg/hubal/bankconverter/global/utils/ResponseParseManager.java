package com.oleg.hubal.bankconverter.global.utils;

import com.google.gson.Gson;
import com.oleg.hubal.bankconverter.global.constants.LoadConstants;
import com.oleg.hubal.bankconverter.model.data.City;
import com.oleg.hubal.bankconverter.model.data.Currency;
import com.oleg.hubal.bankconverter.model.data.CurrencyAbbr;
import com.oleg.hubal.bankconverter.model.data.Date;
import com.oleg.hubal.bankconverter.model.data.Organization;
import com.oleg.hubal.bankconverter.model.data.Region;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Response;

/**
 * Created by User on 20.01.2017.
 */

public class ResponseParseManager {

    private JSONObject mResponseJSON;

    public ResponseParseManager(Response response) throws IOException, JSONException {
        mResponseJSON = LoadUtils.getJSONObjectFromResponse(response);
    }

    public List<Organization> getOrganizationList() throws JSONException {
        Gson gson =  new Gson();
        List<Organization> organizationList = new ArrayList<>();

        JSONArray organizationArray = mResponseJSON.getJSONArray(LoadConstants.KEY_JSON_ORGANIZATIONS);

        for (int i = 0; i < organizationArray.length(); i++) {
            JSONObject organizationJSONObject = organizationArray.getJSONObject(i);

            Organization organization = gson.fromJson(organizationJSONObject.toString(), Organization.class);

            List<Currency> currencyList = getCurrencyList(organizationJSONObject);
            organization.setCurrency(currencyList);

            organizationList.add(organization);
        }

        return organizationList;
    }

    private List<Currency> getCurrencyList(JSONObject organizationJSONObject) throws JSONException {
        Gson gson =  new Gson();
        List<Currency> currencyList = new ArrayList<>();

        String organizationId = organizationJSONObject.getString(LoadConstants.KEY_JSON_ID);

        JSONObject currenciesJSONObject = organizationJSONObject.getJSONObject(LoadConstants.KEY_JSON_CURRENCIES);
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

    public List<CurrencyAbbr> getCurrencyAbbrList() throws JSONException {
        List<CurrencyAbbr> currencyAbbrList = new ArrayList<>();

        JSONObject currencyAbbrJSON = mResponseJSON.getJSONObject(LoadConstants.KEY_JSON_CURRENCIES);

        Iterator<String> keysIterator = currencyAbbrJSON.keys();

        while (keysIterator.hasNext()) {
            String key = keysIterator.next();

            CurrencyAbbr currencyAbbr = new CurrencyAbbr();
            currencyAbbr.setAbbreviation(key);
            currencyAbbr.setName(currencyAbbrJSON.getString(key));
            currencyAbbrList.add(currencyAbbr);
        }

        return currencyAbbrList;
    }


    public List<Region> getRegionList() throws JSONException {
        List<Region> regionList = new ArrayList<>();

        JSONObject regionsJSON = mResponseJSON.getJSONObject(LoadConstants.KEY_JSON_REGIONS);

        Iterator<String> keysIterator = regionsJSON.keys();

        while (keysIterator.hasNext()) {
            String key = keysIterator.next();

            Region region = new Region();
            region.setRegionId(key);
            region.setName(regionsJSON.getString(key));
            regionList.add(region);
        }

        return regionList;
    }


    public List<City> getCityList() throws JSONException {
        List<City> cityList = new ArrayList<>();

        JSONObject citiesJSON = mResponseJSON.getJSONObject(LoadConstants.KEY_JSON_CITIES);

        Iterator<String> keysIterator = citiesJSON.keys();

        while (keysIterator.hasNext()) {
            String key = keysIterator.next();

            City city = new City();
            city.setCityId(key);
            city.setName(citiesJSON.getString(key));
            cityList.add(city);
        }

        return cityList;
    }

    public Date getDate() throws JSONException {
        Gson gson = new Gson();

        Date date = gson.fromJson(mResponseJSON.toString(), Date.class);
        return date;
    }
}
