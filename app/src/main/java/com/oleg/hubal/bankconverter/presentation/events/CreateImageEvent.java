package com.oleg.hubal.bankconverter.presentation.events;

import android.net.Uri;

/**
 * Created by User on 25.01.2017.
 */

public class CreateImageEvent {

    public final Uri uri;

    public CreateImageEvent(Uri uri) {
        this.uri = uri;
    }
}
