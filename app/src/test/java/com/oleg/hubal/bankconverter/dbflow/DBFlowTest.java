package com.oleg.hubal.bankconverter.dbflow;

import org.junit.Rule;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by User on 19.01.2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml")
public class DBFlowTest {

    @Rule
    public final DBFlow mDBFlow = DBFlow.create();



}
