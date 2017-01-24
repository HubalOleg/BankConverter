package com.oleg.hubal.bankconverter.main;

import com.oleg.hubal.bankconverter.DBFlow;
import com.oleg.hubal.bankconverter.global.utils.LoadUtils;
import com.oleg.hubal.bankconverter.model.data.Date;
import com.oleg.hubal.bankconverter.presentation.events.SuccessSynchronizeEvent;
import com.oleg.hubal.bankconverter.presentation.presenter.main.MainPresenter;
import com.oleg.hubal.bankconverter.presentation.view.main.MainView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by User on 18.01.2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml")
public class MainTest {

    @Mock
    private MainView mMainView;

    @Mock
    private LoadUtils mLoadUtils;

    @Mock
    private SuccessSynchronizeEvent mSuccessSynchronizeEvent;

    private MainPresenter mMainPresenter;

    @Rule
    public final DBFlow mDBFlow = DBFlow.create();

    @Before
    public void setupMainPresenter() {
        MockitoAnnotations.initMocks(MainTest.this);

        mMainPresenter = new MainPresenter();
        mMainPresenter.attachView(mMainView);
    }

    @Test
    public void loadBankData_LaunchLoadCurrencyService() {
        mMainPresenter.loadBankData(false);
        verify(mMainView, times(2)).checkInternetConnection();
    }

    @Test
    public void loadBankData_LaunchLoadWhenServiceRunning() {
        mMainPresenter.loadBankData(true);
        verify(mMainView).showProgressBar();
    }

    @Test
    public void successSynchronizeEvent() {
        mMainPresenter.successSynchronizeEvent(mSuccessSynchronizeEvent);
        verify(mMainView).dismissProgressBar();
        verify(mMainView).showOrganizationList();
    }

    @Test
    public void onDeviceOnline_LaunchService() {
        mMainPresenter.onDeviceOnline();
        verify(mMainView).launchLoadCurrencyService();
        verify(mMainView).showProgressBar();
    }

    @Test
    public void onDeviceOffline_EmptyDate() {
        mMainPresenter.onDeviceOffline();
        verify(mMainView).launchLoadCurrencyService();
        verify(mMainView).showProgressBar();
    }

    @Test
    public void onDeviceOffline_WithDate() {
        Date date = new Date("any_date");
        date.save();

        mMainPresenter.onDeviceOffline();
        verify(mMainView).showOrganizationList();
    }
}
