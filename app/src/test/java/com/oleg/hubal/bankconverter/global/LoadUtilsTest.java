package com.oleg.hubal.bankconverter.global;

import com.oleg.hubal.bankconverter.global.constants.LoadConstants;
import com.oleg.hubal.bankconverter.global.utils.LoadUtils;
import com.oleg.hubal.bankconverter.model.Currency;
import com.oleg.hubal.bankconverter.model.CurrencyAbbr;
import com.oleg.hubal.bankconverter.model.Organization;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import okhttp3.Response;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by User on 18.01.2017.
 */

public class LoadUtilsTest {

    private static final String RESPONSE_BODY_TEST = "{\n\"sourceId\":\"currency-cash\",\n\"date\":\"2017-01-19T09:33:33+02:00\",\n\"organizations\":[\n{\n\"id\":\"7oiylpmiow8iy1smaze\",\n\"oldId\":1233,\n\"orgType\":1,\n\"branch\":false,\n\"title\":\"А-Банк\",\n\"regionId\":\"ua,7oiylpmiow8iy1smaci\",\n\"cityId\":\"7oiylpmiow8iy1smadm\",\n\"phone\":\"0800500809\",\n\"address\":\"ул. Батумская, 11\",\n\"link\":\"http://organizations.finance.ua/ru/info/currency/-/7oiylpmiow8iy1smaze/cash\",\n\"currencies\":{\n\"EUR\":{\n\"ask\":\"30.7000\",\n\"bid\":\"29.1000\"\n},\n\"RUB\":{\n\"ask\":\"0.4840\",\n\"bid\":\"0.4520\"\n},\n\"USD\":{\n\"ask\":\"28.7000\",\n\"bid\":\"27.2000\"\n}\n}\n}\n],\n\"currencies\":{\n\"AED\":\"дирхамы ОАЭ\"\n},\n\"regions\":{\n\"ua,7oiylpmiow8iy1smacn\":\"Автономная Республика Крым\"\n},\n\"cities\":{\n\"7oiylpmiow8iy1smadm\":\"Днепр\"\n}\n}";
    private JSONObject mResponseJSON;

    @Before
    public void initJSONObject() throws JSONException {
        mResponseJSON = new JSONObject(RESPONSE_BODY_TEST);
    }

    @Test
    public void loadUpdatedOrganizationList_LoadWithNewDate() throws IOException, JSONException {
        String currentDate = "2017-01-19T09:33:33+02:00";
        List<Organization> organizationList = LoadUtils.loadUpdatedOrganizationList(currentDate);
        assertTrue(organizationList.size() > 0);
    }

    @Test
    public void loadCurrencyAbbrList_CheckSize() throws IOException, JSONException {
        List<CurrencyAbbr> currencyAbbrList = LoadUtils.loadCurrencyAbbreviation();
        assertTrue(currencyAbbrList.size() > 0);
    }

    @Test
    public void getResponseFromRequest_CheckResponseSuccess() throws IOException {
        Response response = LoadUtils.getResponseFromRequest();

        assertTrue(response.isSuccessful());
    }

    @Test
    public void getResponseFromRequest_CheckResponseSourceId() throws IOException, JSONException {
        Response response = LoadUtils.getResponseFromRequest();

        String actualSourceId = getSourceIdFromResponse(response);
        String expectedSourceId = "currency-cash";

        assertEquals(expectedSourceId, actualSourceId);
    }

    private String getSourceIdFromResponse(Response response) throws IOException, JSONException {
        String responseBody = response.body().string();

        JSONObject jsonObject = new JSONObject(responseBody);

        return jsonObject.getString(LoadConstants.KEY_SOURCE_ID_JSON);
    }

    @Test
    public void getDataDate_ReturnExpectedDate() throws JSONException {
        String expectedDate = "2017-01-19T09:33:33+02:00";

        String actualDate = LoadUtils.getDataDate(mResponseJSON);

        assertEquals(expectedDate, actualDate);
    }

    @Test
    public void isDataUpdated_DataUpdated() {
        String currentDate = "2017-01-19T09:33:33+02:00";
        String responseUpdatedDate = "2017-02-19T09:33:33+02:00";

        boolean isUpdated = LoadUtils.isDataUpdated(currentDate, responseUpdatedDate);

        assertTrue(isUpdated);
    }

    @Test
    public void isDataUpdated_EmptyCurrentDate() {
        String currentDate = "";
        String responseDate = "2017-02-19T09:33:33+02:00";

        boolean isUpdated = LoadUtils.isDataUpdated(currentDate, responseDate);

        assertTrue(isUpdated);
    }

    @Test
    public void isDataUpdate_SameDate() {
        String currentDate = "2017-02-19T09:33:33+02:00";
        String responseDate = "2017-02-19T09:33:33+02:00";

        boolean isUpdated = LoadUtils.isDataUpdated(currentDate, responseDate);

        assertFalse(isUpdated);
    }

    @Test
    public void getArrayFromResponse_IsValidArray() throws JSONException {
        String expectedArray = "[{\"orgType\":1,\"address\":\"ул. Батумская, 11\",\"regionId\":\"ua,7oiylpmiow8iy1smaci\",\"phone\":\"0800500809\",\"link\":\"http://organizations.finance.ua/ru/info/currency/-/7oiylpmiow8iy1smaze/cash\",\"id\":\"7oiylpmiow8iy1smaze\",\"cityId\":\"7oiylpmiow8iy1smadm\",\"oldId\":1233,\"title\":\"А-Банк\",\"branch\":false,\"currencies\":{\"EUR\":{\"ask\":\"30.7000\",\"bid\":\"29.1000\"},\"USD\":{\"ask\":\"28.7000\",\"bid\":\"27.2000\"},\"RUB\":{\"ask\":\"0.4840\",\"bid\":\"0.4520\"}}}]";

        String actualArray = LoadUtils.getArrayFromResponse(mResponseJSON, LoadConstants.KEY_ORGANIZATIONS_JSON);
        assertEquals(expectedArray ,actualArray);
    }

    @Test
    public void getOrganizationList_CheckSize() throws JSONException {
        List<Organization> organizations = LoadUtils.getOrganizationList(mResponseJSON);

        int expectedSize = 1;
        int actualSize = organizations.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void getOrganizationList_CheckFields() throws JSONException {
        List<Organization> organizations = LoadUtils.getOrganizationList(mResponseJSON);
        Organization organization = organizations.get(0);

        assertEquals(organization.getId(), "7oiylpmiow8iy1smaze");
        assertEquals(organization.getTitle(), "А-Банк");
        assertEquals(organization.getRegionId(), "ua,7oiylpmiow8iy1smaci");
        assertEquals(organization.getCityId(), "7oiylpmiow8iy1smadm");
        assertEquals(organization.getPhone(), "0800500809");
        assertEquals(organization.getAddress(), "ул. Батумская, 11");
        assertEquals(organization.getLink(), "http://organizations.finance.ua/ru/info/currency/-/7oiylpmiow8iy1smaze/cash");
        assertEquals(organization.getCurrency().size(), 3);
    }

    @Test
    public void getCurrencyList_CheckFields() throws JSONException {
        JSONArray jsonArray = mResponseJSON.getJSONArray(LoadConstants.KEY_ORGANIZATIONS_JSON);
        JSONObject organizationObject = jsonArray.getJSONObject(0);

        List<Currency> currencyList = LoadUtils.getCurrencyList(organizationObject);

        Currency currency = currencyList.get(0);

        assertEquals(currencyList.size(), 3);
        assertEquals(currency.getOrganizationId(), "7oiylpmiow8iy1smaze");
        assertEquals(currency.getNameAbbreviation(), "EUR");
        assertEquals(currency.getAsk(), "30.7000");
        assertEquals(currency.getBid(), "29.1000");
    }

    @Test
    public void getCurrencyAbbrList_CheckFields() throws JSONException {
        List<CurrencyAbbr> currencyAbbrList = LoadUtils.getCurrencyAbbrList(mResponseJSON);
        CurrencyAbbr currencyAbbr = currencyAbbrList.get(0);

        String expectedAbbreviation = "AED";
        String actualAbbreviation = currencyAbbr.getAbbreviation();
        String expectedName = "дирхамы ОАЭ";
        String actualName = currencyAbbr.getName();

        assertEquals(expectedAbbreviation, actualAbbreviation);
        assertEquals(expectedName, actualName);
    }
}
