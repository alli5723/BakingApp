package com.omo_lanke.android.bakingapp.misc;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by omo_lanke on 22/06/2017.
 */

public class LinearWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new LinearRemoteViewsFactory(this);
    }
}

