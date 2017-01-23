package com.oleg.hubal.bankconverter.presentation.events;

import okhttp3.Response;

/**
 * Created by User on 23.01.2017.
 */

public class LoadResponseEvent {

    public final Response response;

    public LoadResponseEvent(Response response) {
        this.response = response;
    }
}
