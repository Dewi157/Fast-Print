package com.hikmah.dewi.fastprint.config;

import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * Created by dewi on 9/21/2017.
 */

public class NoActionBarConfig extends AppCompatActivity {
    public NoActionBarConfig() {}

    public void fullScreen(AppCompatActivity appCompatActivity){
        appCompatActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
