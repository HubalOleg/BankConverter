package com.oleg.hubal.bankconverter.global;

import com.oleg.hubal.bankconverter.global.constants.LoadConstants;
import com.oleg.hubal.bankconverter.global.utils.ResponseParseManager;
import com.oleg.hubal.bankconverter.model.data.City;
import com.oleg.hubal.bankconverter.model.data.Currency;
import com.oleg.hubal.bankconverter.model.data.CurrencyAbbr;
import com.oleg.hubal.bankconverter.model.data.Date;
import com.oleg.hubal.bankconverter.model.data.Organization;
import com.oleg.hubal.bankconverter.model.data.Region;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static junit.framework.Assert.assertEquals;

/**
 * Created by User on 20.01.2017.
 */

public class ResponseParseManagerTest {

    private static final String RESPONSE_BODY_TEST = "{\n\"sourceId\":\"currency-cash\",\n\"date\":\"2017-01-19T09:33:33+02:00\",\n\"organizations\":[\n{\n\"id\":\"7oiylpmiow8iy1smaze\",\n\"oldId\":1233,\n\"orgType\":1,\n\"branch\":false,\n\"title\":\"А-Банк\",\n\"regionId\":\"ua,7oiylpmiow8iy1smaci\",\n\"cityId\":\"7oiylpmiow8iy1smadm\",\n\"phone\":\"0800500809\",\n\"address\":\"ул. Батумская, 11\",\n\"link\":\"http://organizations.finance.ua/ru/info/currency/-/7oiylpmiow8iy1smaze/cash\",\n\"currencies\":{\n\"EUR\":{\n\"ask\":\"30.7000\",\n\"bid\":\"29.1000\"\n},\n\"RUB\":{\n\"ask\":\"0.4840\",\n\"bid\":\"0.4520\"\n},\n\"USD\":{\n\"ask\":\"28.7000\",\n\"bid\":\"27.2000\"\n}\n}\n}\n],\n\"currencies\":{\n\"AED\":\"дирхамы ОАЭ\"\n},\n\"regions\":{\n\"ua,7oiylpmiow8iy1smacn\":\"Автономная Республика Крым\"\n},\n\"cities\":{\n\"7oiylpmiow8iy1smadm\":\"Днепр\"\n}\n}";
    public static final String DATE_TEST = "2017-02-19T09:33:33+02:00";

    private ResponseParseManager mResponseParseManager;

    @Before
    public void initTestResponseParseManager() throws IOException, JSONException {
        ResponseBody responseBody = ResponseBody.create(MediaType.parse("text/plain; charset=utf-8"), RESPONSE_BODY_TEST);

        Request request = new Request.Builder()
                .url(LoadConstants.CURRENCY_JSON_URL)
                .build();

        Response response = new Response
                .Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .body(responseBody)
                .build();

        mResponseParseManager = new ResponseParseManager(response);
    }

    @Test
    public void getDate_ReturnExpectedDate() throws JSONException {
        Date date = mResponseParseManager.getDate();

        String expectedDate = "2017-01-19T09:33:33+02:00";
        String actualDate = date.getDate();

        int expectedId = 0;
        int actualId = date.getId();

        assertEquals(expectedDate, actualDate);
        assertEquals(expectedId, actualId);
    }

    @Test
    public void getOrganizationList_CheckFields() throws JSONException {
        List<Organization> organizations = mResponseParseManager.getOrganizationList();
        Organization organization = organizations.get(0);
        Currency currency = organization.getCurrency().get(0);

        assertEquals(organization.getId(), "7oiylpmiow8iy1smaze");
        assertEquals(organization.getTitle(), "А-Банк");
        assertEquals(organization.getRegionId(), "ua,7oiylpmiow8iy1smaci");
        assertEquals(organization.getCityId(), "7oiylpmiow8iy1smadm");
        assertEquals(organization.getPhone(), "0800500809");
        assertEquals(organization.getAddress(), "ул. Батумская, 11");
        assertEquals(organization.getLink(), "http://organizations.finance.ua/ru/info/currency/-/7oiylpmiow8iy1smaze/cash");
        assertEquals(currency.getOrganizationId(), "7oiylpmiow8iy1smaze");
        assertEquals(currency.getNameAbbreviation(), "EUR");
        assertEquals(currency.getAsk(), "30.7000");
        assertEquals(currency.getBid(), "29.1000");
    }

    @Test
    public void getCurrencyAbbrList_CheckFields() throws JSONException {
        List<CurrencyAbbr> currencyAbbrList = mResponseParseManager.getCurrencyAbbrList();
        CurrencyAbbr currencyAbbr = currencyAbbrList.get(0);

        String expectedAbbreviation = "AED";
        String actualAbbreviation = currencyAbbr.getAbbreviation();
        String expectedName = "дирхамы ОАЭ";
        String actualName = currencyAbbr.getName();

        assertEquals(expectedAbbreviation, actualAbbreviation);
        assertEquals(expectedName, actualName);
    }

    @Test
    public void getRegionList_CheckFields() throws JSONException {
        List<Region> regionList = mResponseParseManager.getRegionList();
        Region region = regionList.get(0);

        String expectedRegionId = "ua,7oiylpmiow8iy1smacn";
        String actualRegionId = region.getRegionId();
        String expectedName = "Автономная Республика Крым";
        String actualName = region.getName();

        assertEquals(expectedRegionId, actualRegionId);
        assertEquals(expectedName, actualName);
    }

    @Test
    public void getCityList_CheckFields() throws JSONException {
        List<City> cityList = mResponseParseManager.getCityList();
        City city = cityList.get(0);

        String expectedCityId = "7oiylpmiow8iy1smadm";
        String actualCityId = city.getCityId();
        String expectedName = "Днепр";
        String actualName = city.getName();

        assertEquals(expectedCityId, actualCityId);
        assertEquals(expectedName, actualName);
    }

}