package com.oleg.hubal.bankconverter.utils;

import com.oleg.hubal.bankconverter.global.Constants;
import com.oleg.hubal.bankconverter.global.CurrencyJsonUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

import okhttp3.Response;

import static com.oleg.hubal.bankconverter.global.CurrencyJsonUtils.getResponseWithRequest;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by User on 18.01.2017.
 */

public class CurrencyJsonUtilsTest {

    @Test
    public void getResponseWithRequest_CheckResponseSuccess() throws IOException {
        Response response = getResponseWithRequest(Constants.CURRENCY_JSON_URL);

        assertTrue(response.isSuccessful());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getResponseWithRequest_InvalidRequest() throws IOException {
        getResponseWithRequest("anyurl.com");
    }

    @Test(expected = JSONException.class)
    public void getResponseWithRequest_WrongUrl() throws IOException, JSONException {
        Response response = CurrencyJsonUtils.getResponseWithRequest("https://t2dev.firebaseio.com/CATEGORY.json");

        getSourceIdFromResponse(response);
    }

    @Test
    public void getResponseWithRequest_CheckResponseSourceId() throws IOException, JSONException {
        Response response = getResponseWithRequest(Constants.CURRENCY_JSON_URL);

        String actualSourceId = getSourceIdFromResponse(response);
        String expectedSourceId = "currency-cash";

        assertEquals(expectedSourceId, actualSourceId);
    }

    private String getSourceIdFromResponse(Response response) throws IOException, JSONException {
        String responseBody = response.body().string();

        JSONObject jsonObject = new JSONObject(responseBody);

        return jsonObject.getString("sourceId");
    }
}
