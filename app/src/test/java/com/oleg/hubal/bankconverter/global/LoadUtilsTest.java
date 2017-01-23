package com.oleg.hubal.bankconverter.global;

import com.oleg.hubal.bankconverter.global.constants.LoadConstants;
import com.oleg.hubal.bankconverter.global.utils.LoadUtils;
import com.oleg.hubal.bankconverter.model.data.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

import okhttp3.Response;

import static com.oleg.hubal.bankconverter.global.ResponseParseManagerTest.DATE_TEST;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by User on 18.01.2017.
 */

public class LoadUtilsTest {

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

        return jsonObject.getString(LoadConstants.KEY_JSON_SOURCE_ID);
    }

    @Test
    public void isDataUpdated_DataUpdated() {
        Date date = new Date("2017-01-19T09:33:33+02:00");
        Date responseDate = new Date(DATE_TEST);

        boolean isUpdated = LoadUtils.isDataUpdated(date, responseDate);

        assertTrue(isUpdated);
    }

    @Test
    public void isDataUpdated_EmptyCurrentDate() {
        Date date = new Date("");
        Date responseDate = new Date(DATE_TEST);

        boolean isUpdated = LoadUtils.isDataUpdated(date, responseDate);

        assertTrue(isUpdated);
    }

    @Test
    public void isDataUpdate_SameDate() {
        Date date = new Date(DATE_TEST);
        Date responseDate = new Date(DATE_TEST);

        boolean isUpdated = LoadUtils.isDataUpdated(date, responseDate);

        assertFalse(isUpdated);
    }

}
