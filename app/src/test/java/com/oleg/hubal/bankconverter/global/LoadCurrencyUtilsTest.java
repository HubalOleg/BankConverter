package com.oleg.hubal.bankconverter.global;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

import okhttp3.Response;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by User on 18.01.2017.
 */

public class LoadCurrencyUtilsTest {

    @Test
    public void getResponseFromRequest_CheckResponseSuccess() throws IOException {
        Response response = LoadCurrencyUtils.getResponseFromRequest(Constants.CURRENCY_JSON_URL);

        assertTrue(response.isSuccessful());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getResponseFromRequest_InvalidRequest() throws IOException {
        LoadCurrencyUtils.getResponseFromRequest("anyurl.com");
    }

    @Test(expected = JSONException.class)
    public void getResponseFromRequest_WrongUrl() throws IOException, JSONException {
        Response response = LoadCurrencyUtils.getResponseFromRequest("https://t2dev.firebaseio.com/CATEGORY.json");

        getSourceIdFromResponse(response);
    }

    @Test
    public void getResponseFromRequest_CheckResponseSourceId() throws IOException, JSONException {
        Response response = LoadCurrencyUtils.getResponseFromRequest(Constants.CURRENCY_JSON_URL);

        String actualSourceId = getSourceIdFromResponse(response);
        String expectedSourceId = "currency-cash";

        assertEquals(expectedSourceId, actualSourceId);
    }

    private String getSourceIdFromResponse(Response response) throws IOException, JSONException {
        String responseBody = response.body().string();

        JSONObject jsonObject = new JSONObject(responseBody);

        return jsonObject.getString("sourceId");
    }

    @Test
    public void getDateFromResponseBody_ReturnExpectedDate() throws JSONException {
        String responseBodyTest = "{\"date\":\"2017-01-19T09:33:33+02:00\"}";
        String expectedDate = "2017-01-19T09:33:33+02:00";

        String actualDate = LoadCurrencyUtils.getDateFromResponseBody(responseBodyTest);

        assertEquals(expectedDate, actualDate);
    }

    @Test
    public void isDataUpdated_DataUpdated() {
        String currentDate = "2017-01-19T09:33:33+02:00";
        String responseUpdatedDate = "2017-02-19T09:33:33+02:00";

        boolean isUpdated = LoadCurrencyUtils.isDataUpdated(currentDate, responseUpdatedDate);

        assertTrue(isUpdated);
    }

    @Test
    public void isDataUpdated_EmptyCurrentDate() {
        String currentDate = "";
        String responseDate = "2017-02-19T09:33:33+02:00";

        boolean isUpdated = LoadCurrencyUtils.isDataUpdated(currentDate, responseDate);

        assertTrue(isUpdated);
    }

    @Test
    public void isDataUpdate_SameDate() {
        String currentDate = "2017-02-19T09:33:33+02:00";
        String responseDate = "2017-02-19T09:33:33+02:00";

        boolean isUpdated = LoadCurrencyUtils.isDataUpdated(currentDate, responseDate);

        assertFalse(isUpdated);
    }
}
