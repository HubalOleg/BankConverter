package com.oleg.hubal.bankconverter.global;

import com.path.android.jobqueue.JobManager;

import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertTrue;

/**
 * Created by User on 18.01.2017.
 */

public class ApplicationTest {

    @Mock
    private BankConverterApplication mBankConverterApplication;

    @Test(expected = NullPointerException.class)
    public void getJobManager_IsValidInstance() {
        Object testObject = mBankConverterApplication.getJobManager();
        assertTrue(testObject instanceof JobManager);
    }
}
