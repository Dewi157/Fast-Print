package com.hikmah.dewi.fastprint.config;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.hikmah.dewi.fastprint.R;


/**
 * Created by dewi on 9/20/2017.
 */

public class BaseActivityConfig extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setupToolbar();
    }

    protected void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
