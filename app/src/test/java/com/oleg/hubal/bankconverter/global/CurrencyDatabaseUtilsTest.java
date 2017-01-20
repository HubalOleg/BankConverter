package com.oleg.hubal.bankconverter.global;

import com.oleg.hubal.bankconverter.model.data.Organization;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

/**
 * Created by User on 20.01.2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml")
public class CurrencyDatabaseUtilsTest {

    private List<Organization> mOrganizationList;

    @Rule
    public final DBFlow mDBFlow = DBFlow.create();

    @Before
    public void initCurrencyData() {
        Organization organization = new Organization();
    }

    @Test
    public void saveOrganizationList_TestFields() {

    }
}
