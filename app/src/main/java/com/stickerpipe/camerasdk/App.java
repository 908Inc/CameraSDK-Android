package com.stickerpipe.camerasdk;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.crashlytics.android.Crashlytics;
import com.stickerpipe.camerasdk.network.NetworkManager;

import io.fabric.sdk.android.Fabric;

/**
 * @author Dmitry Nezhydenko (dehimb@gmail.com)
 */

public class App extends Application {
    public static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }
        StorageManager.init(this);
        NetworkManager.init(this, BuildConfig.DEBUG
                ? BuildConfig.API_KEY_DEBUG
                : BuildConfig.API_KEY_PROD);

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        BroadcastReceiver networkStateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Utils.isNetworkAvailable(context)) {
                    NetworkManager.getInstance().requestStoriesUpdate();
                    NetworkManager.getInstance().requestStampsUpdate();
                }
            }
        };
        registerReceiver(networkStateReceiver, filter);
    }
}
