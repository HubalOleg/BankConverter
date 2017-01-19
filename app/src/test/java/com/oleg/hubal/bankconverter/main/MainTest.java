package com.oleg.hubal.bankconverter.main;

import com.oleg.hubal.bankconverter.global.LoadCurrencyUtils;
import com.oleg.hubal.bankconverter.presentation.presenter.main.MainPresenter;
import com.oleg.hubal.bankconverter.presentation.view.main.MainView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by User on 18.01.2017.
 */

public class MainTest {

    @Mock
    private MainView mMainView;

    @Mock
    private LoadCurrencyUtils mLoadCurrencyUtils;

    private MainPresenter mMainPresenter;

    @Before
    public void setupMainPresenter() {
        MockitoAnnotations.initMocks(MainTest.this);

        mMainPresenter = new MainPresenter();
        mMainPresenter.attachView(mMainView);
    }

    @Test
    public void loadBankData_LaunchLoadBankDataJob() {
        mMainPresenter.loadBankData();
        verify(mMainView, times(2)).launchLoadBankDataJob();
    }
}
