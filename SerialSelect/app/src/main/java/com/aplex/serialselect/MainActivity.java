package com.aplex.serialselect;

import android.app.Activity;
import android.os.Bundle;

import com.example.aplex.serialselect.R;
import com.aplex.serialselect.utils.SPUtils;

public class MainActivity extends Activity {

    //static Application mApplication = MainActivity.getApplication();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //SPUtils.testSPUtils(getApplication());
        //getApplication()
    }
}
