package com.hikmah.dewi.fastprint.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hikmah.dewi.fastprint.R;
import com.hikmah.dewi.fastprint.config.BaseActivityConfig;
import com.hikmah.dewi.fastprint.config.ParameterConfig;
import com.hikmah.dewi.fastprint.config.URLConfig;
import com.hikmah.dewi.fastprint.controller.AppController;
import com.hikmah.dewi.fastprint.fragment.AboutFragment;
import com.hikmah.dewi.fastprint.fragment.CariFragment;
import com.hikmah.dewi.fastprint.fragment.HistoryFragment;
import com.hikmah.dewi.fastprint.fragment.ProfileFragment;
import com.hikmah.dewi.fastprint.session.SQLiteHandler;
import com.hikmah.dewi.fastprint.session.SessionManager;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends BaseActivityConfig implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    SQLiteHandler db;
    SessionManager session;
    String id_pelanggan;
    int success = 0;
    String nohp;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preference =
                getApplicationContext().getSharedPreferences("data", 0);
        String nama = preference.getString("nama", null);
        nohp = preference.getString("no_hp", null);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        TextView name = (TextView) header.findViewById(R.id.textView1);
        TextView email = (TextView) header.findViewById(R.id.textView);
        name.setText(nama);
        email.setText(nohp);
        db = new SQLiteHandler(getApplicationContext());
        // session manager
        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn()) {
            logoutUser();
        }
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        setupDrawerContent(navigationView);
        actionBarDrawerToggle = setupDrawerToggle();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        selectDrawerItem(navigationView.getMenu().getItem(0));

    }

    private void setupDrawerContent(final NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, drawerLayout, getToolbar(), R.string.drawer_open, R.string.drawer_close);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.cari:
                fragment = CariFragment.newInstance();
                break;
            case R.id.history:
                fragment = HistoryFragment.newInstance();
                break;
            case R.id.profile:
                fragment = ProfileFragment.newInstance();
                break;
            case R.id.about:
                fragment = AboutFragment.newInstance();
                break;
            case R.id.logout:
                fragment = AboutFragment.newInstance();
                delete(nohp);
                break;
            default:
                fragment = CariFragment.newInstance();
                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        menuItem.setChecked(true);
        getToolbar().setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        this.finish();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Ask the user if they want to quit
        this.finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    private void logoutUser() {
        session.logoutUser();
        db.deleteUsers();
        // Launching the login activity
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    private void delete(final String idx) {
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setMessage("Proses Logout ....");
        loading.show();
        StringRequest strReq = new StringRequest(Request.Method.POST, URLConfig.URL_LOGOUT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(ParameterConfig.TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("delete", jObj.toString());
                        logoutUser();
                        Toast.makeText(MainActivity.this, jObj.getString(ParameterConfig.TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(MainActivity.this, jObj.getString(ParameterConfig.TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        loading.dismiss();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                loading.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("no_hp", idx);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, ParameterConfig.tag_json_obj);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}