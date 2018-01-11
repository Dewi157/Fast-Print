package com.hikmah.dewi.fastprint.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.hikmah.dewi.fastprint.R;
import com.hikmah.dewi.fastprint.config.ParameterConfig;
import com.hikmah.dewi.fastprint.config.NoActionBarConfig;
import com.hikmah.dewi.fastprint.config.URLConfig;
import com.hikmah.dewi.fastprint.controller.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends AppCompatActivity {
    String id_rp;
    ImageLoader imageLoader;
    @BindView(R.id.coordinator_detail)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.nama_rental)
    TextView nama_rental;
    @BindView(R.id.alamat_rental)
    TextView alamat_rental;
    @BindView(R.id.harga_hp)
    TextView harga_hp;
    @BindView(R.id.harga_warna)
    TextView harga_warna;
    @BindView(R.id.imagerp)
    NetworkImageView imageRP;
    private static final String TAG = LoginActivity.class.getSimpleName();
    int success =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        NoActionBarConfig noActionBarConfig = new NoActionBarConfig();
        noActionBarConfig.fullScreen(this);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        id_rp = getIntent().getStringExtra("id_rp");
        if (id_rp!= null){
            detail(id_rp);

        }else {
            Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.id_null, Snackbar.LENGTH_SHORT);
            snackbar.show();
        }


    }

    @OnClick(R.id.btn_order)
    void order(){
        Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
        intent.putExtra("idpr", id_rp);
        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void detail(final String id_rp){
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setMessage("Cehck ID...");
        loading.show();
        StringRequest strReq = new StringRequest(Request.Method.POST, URLConfig.URL_DETAIL_RP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(ParameterConfig.TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("get edit data", jObj.toString());
                        String id = jObj.getString(ParameterConfig.TAG_ID_RP);
                        String nama = jObj.getString(ParameterConfig.TAG_NAMA_RP);
                        String alamat = jObj.getString(ParameterConfig.TAG_ALAMAT_RP);
                        String hp = jObj.getString(ParameterConfig.TAG_HARGA_HP);
                        String warna = jObj.getString(ParameterConfig.TAG_HARGA_WARNA);
                        String foto = jObj.getString(ParameterConfig.TAG_FOTO);
                        nama_rental.setText(nama);
                        alamat_rental.setText(alamat);
                        harga_hp.setText("RP "+hp);
                        harga_warna.setText("RP "+warna);
                        if (imageLoader==null)
                            imageLoader = AppController.getInstance().getImageLoader();
                        imageRP.setImageUrl(URLConfig.URL_FOTO+foto, imageLoader);
                        loading.dismiss();
                    } else {
                        Snackbar snacka = Snackbar.make(coordinatorLayout, jObj.getString(ParameterConfig.TAG_MESSAGE), Snackbar.LENGTH_LONG);
                        snacka.show();
                        loading.dismiss();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError instanceof NetworkError) {
                    Snackbar snacka = Snackbar.make(coordinatorLayout, R.string.networkerror, Snackbar.LENGTH_LONG);
                    snacka.show();
                    loading.dismiss();
                } else if (volleyError instanceof ServerError) {
                    Snackbar snackb = Snackbar.make(coordinatorLayout, R.string.ServerError, Snackbar.LENGTH_LONG);
                    snackb.show();
                    loading.dismiss();
                } else if (volleyError instanceof AuthFailureError) {
                    Snackbar snackc = Snackbar.make(coordinatorLayout, R.string.AuthFailureError, Snackbar.LENGTH_LONG);
                    snackc.show();
                    loading.dismiss();
                } else if (volleyError instanceof ParseError) {
                    Snackbar snackd = Snackbar.make(coordinatorLayout, R.string.ParseError, Snackbar.LENGTH_LONG);
                    snackd.show();
                    loading.dismiss();
                } else if (volleyError instanceof NoConnectionError) {
                    Snackbar snacke = Snackbar.make(coordinatorLayout, R.string.NoConnectionError, Snackbar.LENGTH_LONG);
                    snacke.show();
                    loading.dismiss();
                } else if (volleyError instanceof TimeoutError) {
                    Snackbar snackf = Snackbar.make(coordinatorLayout, R.string.TimeoutError, Snackbar.LENGTH_LONG);
                    snackf.show();
                    loading.dismiss();
                }
                loading.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put(ParameterConfig.TAG_ID_RP,id_rp );
                return params;
            }

        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                120000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(strReq, ParameterConfig.tag_json_obj);
    }
}
