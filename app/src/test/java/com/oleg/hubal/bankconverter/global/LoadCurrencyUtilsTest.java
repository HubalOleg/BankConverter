package com.oleg.hubal.bankconverter.global;

import com.oleg.hubal.bankconverter.global.constants.LoadConstants;
import com.oleg.hubal.bankconverter.global.utils.LoadCurrencyUtils;
import com.oleg.hubal.bankconverter.model.Organization;

import org.json.JSONException;
import org.json.JSONObject;
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

public class LoadCurrencyUtilsTest {

    private static final String RESPONSE_BODY_TEST = "{\n\"sourceId\":\"currency-cash\",\n\"date\":\"2017-01-19T09:33:33+02:00\",\n\"organizations\":[\n{\n\"id\":\"7oiylpmiow8iy1smaze\",\n\"oldId\":1233,\n\"orgType\":1,\n\"branch\":false,\n\"title\":\"\\u0410-\\u0411\\u0430\\u043d\\u043a\",\n\"regionId\":\"ua,7oiylpmiow8iy1smaci\",\n\"cityId\":\"7oiylpmiow8iy1smadm\",\n\"phone\":\"0800500809\",\n\"address\":\"\\u0443\\u043b. \\u0411\\u0430\\u0442\\u0443\\u043c\\u0441\\u043a\\u0430\\u044f, 11\",\n\"link\":\"http:\\/\\/organizations.finance.ua\\/ru\\/info\\/currency\\/-\\/7oiylpmiow8iy1smaze\\/cash\",\n\"currencies\":{\n\"EUR\":{\n\"ask\":\"30.7000\",\n\"bid\":\"29.1000\"\n},\n\"RUB\":{\n\"ask\":\"0.4840\",\n\"bid\":\"0.4520\"\n},\n\"USD\":{\n\"ask\":\"28.7000\",\n\"bid\":\"27.2000\"\n}\n}\n}\n],\n\"currencies\":{\n\"AED\":\"\\u0434\\u0438\\u0440\\u0445\\u0430\\u043c\\u044b \\u041e\\u0410\\u042d\"\n},\n\"regions\":{\n\"ua,7oiylpmiow8iy1smacn\":\"\\u0410\\u0432\\u0442\\u043e\\u043d\\u043e\\u043c\\u043d\\u0430\\u044f \\u0420\\u0435\\u0441\\u043f\\u0443\\u0431\\u043b\\u0438\\u043a\\u0430 \\u041a\\u0440\\u044b\\u043c\"\n},\n\"cities\":{\n\"7oiylpmiow8iy1smadm\":\"\\u0414\\u043d\\u0435\\u043f\\u0440\"\n}\n}";


    @Test
    public void getResponseFromRequest_CheckResponseSuccess() throws IOException {
        Response response = LoadCurrencyUtils.getResponseFromRequest();

        assertTrue(response.isSuccessful());
    }

    @Test
    public void getResponseFromRequest_CheckResponseSourceId() throws IOException, JSONException {
        Response response = LoadCurrencyUtils.getResponseFromRequest();

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

        String actualDate = LoadCurrencyUtils.getDataDate(RESPONSE_BODY_TEST);

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

    @Test
    public void getArrayFromResponse_IsValidArray() throws JSONException {
        String expectedArray = "[{\"orgType\":1,\"address\":\"ул. Батумская, 11\",\"regionId\":\"ua,7oiylpmiow8iy1smaci\",\"phone\":\"0800500809\",\"link\":\"http://organizations.finance.ua/ru/info/currency/-/7oiylpmiow8iy1smaze/cash\",\"id\":\"7oiylpmiow8iy1smaze\",\"cityId\":\"7oiylpmiow8iy1smadm\",\"oldId\":1233,\"title\":\"А-Банк\",\"branch\":false,\"currencies\":{\"EUR\":{\"ask\":\"30.7000\",\"bid\":\"29.1000\"},\"USD\":{\"ask\":\"28.7000\",\"bid\":\"27.2000\"},\"RUB\":{\"ask\":\"0.4840\",\"bid\":\"0.4520\"}}}]";
        String actualArray = LoadCurrencyUtils.getArrayFromResponse(RESPONSE_BODY_TEST, LoadConstants.KEY_ORGANIZATIONS_JSON);
        assertEquals(expectedArray ,actualArray);
    }

    @Test
    public void getOrganizations_CheckSize() throws JSONException {
        List<Organization> organizations = LoadCurrencyUtils.getOrganizations(RESPONSE_BODY_TEST);

        int expectedSize = 1;
        int actualSize = organizations.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void getOrganizations_CheckFields() throws JSONException {
        List<Organization> organizations = LoadCurrencyUtils.getOrganizations(RESPONSE_BODY_TEST);
        Organization organization = organizations.get(0);

        assertEquals(organization.getId(), "7oiylpmiow8iy1smaze");
        assertEquals(organization.getTitle(), "А-Банк");
        assertEquals(organization.getRegionId(), "ua,7oiylpmiow8iy1smaci");
        assertEquals(organization.getCityId(), "7oiylpmiow8iy1smadm");
        assertEquals(organization.getPhone(), "0800500809");
        assertEquals(organization.getAddress(), "ул. Батумская, 11");
        assertEquals(organization.getLink(), "http://organizations.finance.ua/ru/info/currency/-/7oiylpmiow8iy1smaze/cash");


    }
}
