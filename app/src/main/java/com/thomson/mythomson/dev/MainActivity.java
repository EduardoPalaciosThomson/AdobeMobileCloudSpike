package com.thomson.mythomson.dev;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.adobe.mobile.Config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static com.adobe.mobile.Config.MobileDataEvent.MOBILE_EVENT_ACQUISITION_INSTALL;
import static com.adobe.mobile.Config.MobileDataEvent.MOBILE_EVENT_ACQUISITION_LAUNCH;
import static com.adobe.mobile.Config.MobileDataEvent.MOBILE_EVENT_LIFECYCLE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.i("ADBMobile", "onCreate: applicationId:" + getPackageName());

        // Allow the SDK access to the application context
        Config.setContext(this.getApplicationContext());
        Config.setDebugLogging(true);

        // overriding config file
        try {
            Log.i("ADBMobile", "ADBMobile - Loading adobe json file: ADBMobileConfig.json");
            InputStream is = getResources().getAssets().open("ADBMobileConfig.json");
            Config.overrideConfigStream(is);
        } catch (IOException e) {
            Log.e("ADBMobile", "ADBMobile - Adobe custom config load failed: ", e );
        }


        Config.registerAdobeDataCallback(new Config.AdobeDataCallback() {
            @Override
            public void call(Config.MobileDataEvent mobileDataEvent, Map<String, Object> map) {
                switch (mobileDataEvent) {
                    case MOBILE_EVENT_LIFECYCLE:
                        // this event will fire when the Adobe sdk finishes processing lifecycle information
                        Log.i("ADBMobile", "setAdobeDataCallBack: " + MOBILE_EVENT_LIFECYCLE.name());
                        break;
                    case MOBILE_EVENT_ACQUISITION_INSTALL:
                        // this event will fire on the first launch of the application after install when installed via an Adobe acquisition link
                        Log.i("ADBMobile", "setAdobeDataCallBack: " + MOBILE_EVENT_ACQUISITION_INSTALL.name());
                        break;
                    case MOBILE_EVENT_ACQUISITION_LAUNCH:
                        // this event will fire on the subsequent launches after the application was installed via an Adobe acquisition link
                        Log.i("ADBMobile", "setAdobeDataCallBack: " + MOBILE_EVENT_ACQUISITION_LAUNCH.name());
                        break;
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Config.collectLifecycleData(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Config.pauseCollectingLifecycleData();
    }
}
