package com.oleg.hubal.bankconverter.utils;

import com.oleg.hubal.bankconverter.global.BankConverterApplication;
import com.path.android.jobqueue.JobManager;

import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertTrue;

/**
 * Created by User on 18.01.2017.
 */

public class UtilityTest {

    @Mock
    private BankConverterApplication mBankConverterApplication;

    @Test(expected = NullPointerException.class)
    public void getJobManager_IsValidInstance() {
        Object testObject = mBankConverterApplication.getJobManager();
        assertTrue(testObject instanceof JobManager);
    }
}
