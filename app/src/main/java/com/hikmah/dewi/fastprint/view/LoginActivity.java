package com.hikmah.dewi.fastprint.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

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
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hikmah.dewi.fastprint.R;
import com.hikmah.dewi.fastprint.config.ParameterConfig;
import com.hikmah.dewi.fastprint.config.NoActionBarConfig;
import com.hikmah.dewi.fastprint.config.URLConfig;
import com.hikmah.dewi.fastprint.controller.AppController;
import com.hikmah.dewi.fastprint.session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements Animation.AnimationListener {
    Animation bounce;
    @BindView(R.id.card)
    CardView cardView;
    @BindView(R.id.coordinator_login)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.password)
    EditText password;
    SharedPreferences preference;
    SessionManager session;
    String tokenku;
    int id_user=0;
    int success=0;
    String id_user1="";
    private static final String TAG = LoginActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        NoActionBarConfig actionBarConfig = new NoActionBarConfig();
        actionBarConfig.fullScreen(this);
        ButterKnife.bind(this);
        FirebaseApp.initializeApp(this);
        bounce = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bounce);
        cardView.startAnimation(bounce);
        session = new SessionManager(getApplicationContext());
        if (session.isLoggedIn()) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

    }

    @OnClick(R.id.tanya)
    void tanya(){
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    }
    @OnClick(R.id.btn_login)
    void  btn_login(){


        if ((phone.getText().toString().equals("")) && (password.getText().toString().equals(""))){
            Snackbar snacka = Snackbar.make(coordinatorLayout, R.string.phonenpass, Snackbar.LENGTH_LONG);
            snacka.show();
        }else if (phone.getText().toString().equals("")){
            Snackbar snackb = Snackbar.make(coordinatorLayout, R.string.ephone, Snackbar.LENGTH_LONG);
            snackb.show();
        }else if (password.getText().toString().equals("")){
            Snackbar snackc = Snackbar.make(coordinatorLayout, R.string.epassword, Snackbar.LENGTH_LONG);
            snackc.show();
        }else {
            tokenku = FirebaseInstanceId.getInstance().getToken();
            login(phone.getText().toString(), password.getText().toString());

        }
    }

    @Override
    public void onAnimationStart(Animation animation) {


    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == bounce) {
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


    private void login(final String no_hp, final String password_login) {
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setMessage("Cek Biodata");
        loading.show();
        StringRequest strReq = new StringRequest(Request.Method.POST, URLConfig.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(ParameterConfig.TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("get edit data", jObj.toString());
                        id_user1 = jObj.getString(ParameterConfig.TAG_ID_USER);
                        String nama = jObj.getString(ParameterConfig.TAG_NAMA);
                        String alamat = jObj.getString(ParameterConfig.TAG_ALAMAT);
                        String no_hp = jObj.getString(ParameterConfig.TAG_NO_HP);
                        String email = jObj.getString(ParameterConfig.TAG_EMAIL);
                        String password = jObj.getString(ParameterConfig.TAG_PASSWORD_LOGIN);
                        preference =
                                getApplicationContext().getSharedPreferences("data", 0);
                        SharedPreferences.Editor editor = preference.edit();
                        editor.putString("id_user", id_user1);
                        editor.putString("nama", nama);
                        editor.putString("alamat", alamat);
                        editor.putString("no_hp", no_hp);
                        editor.putString("email", email);
                        editor.putString("password", password);
                        editor.commit();
                        loading.dismiss();
                        session.createLoginSession(no_hp, password_login);
                        userlogin( );
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
                params.put(ParameterConfig.TAG_NO_HP, no_hp);
                params.put(ParameterConfig.TAG_PASSWORD, password_login);
                return params;
            }

        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                120000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(strReq, ParameterConfig.tag_json_obj);
    }

    private void userlogin() {
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setMessage("Proses Login...");
        loading.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLConfig.URL_SAVELOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response: " + response.toString());

                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(ParameterConfig.TAG_SUCCESS);
                            if (success == 1) {
                                Log.d("v Add", jObj.toString());
                                Snackbar snack = Snackbar.make(coordinatorLayout, jObj.getString(ParameterConfig.TAG_MESSAGE), Snackbar.LENGTH_LONG);
                                snack.show();
                                kosong();
                                loading.dismiss();
                            } else {
                                Snackbar snack1 = Snackbar.make(coordinatorLayout, jObj.getString(ParameterConfig.TAG_MESSAGE), Snackbar.LENGTH_LONG);
                                snack1.show();
                                loading.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if (volleyError instanceof NetworkError) {
                            Snackbar snacka = Snackbar.make(coordinatorLayout, R.string.networkerror, Snackbar.LENGTH_LONG);
                            snacka.show();
                        } else if (volleyError instanceof ServerError) {
                            Snackbar snackb = Snackbar.make(coordinatorLayout, R.string.ServerError, Snackbar.LENGTH_LONG);
                            snackb.show();
                        } else if (volleyError instanceof AuthFailureError) {
                            Snackbar snackc = Snackbar.make(coordinatorLayout, R.string.AuthFailureError, Snackbar.LENGTH_LONG);
                            snackc.show();
                        } else if (volleyError instanceof ParseError) {
                            Snackbar snackd = Snackbar.make(coordinatorLayout, R.string.ParseError, Snackbar.LENGTH_LONG);
                            snackd.show();
                        } else if (volleyError instanceof NoConnectionError) {
                            Snackbar snacke = Snackbar.make(coordinatorLayout, R.string.NoConnectionError, Snackbar.LENGTH_LONG);
                            snacke.show();
                        } else if (volleyError instanceof TimeoutError) {
                            Snackbar snackf = Snackbar.make(coordinatorLayout, R.string.TimeoutError, Snackbar.LENGTH_LONG);
                            snackf.show();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                //membuat parameters
                Map<String, String> params = new HashMap<String, String>();
                //menambah parameter yang di kirim ke web servis
                params.put(ParameterConfig.TAG_ID, String.valueOf(id_user));
                params.put(ParameterConfig.TAG_ID_USER, id_user1);
                params.put(ParameterConfig.TAG_TOKEN, tokenku);
                params.put(ParameterConfig.TAG_NO_HP_LOGIN, phone.getText().toString().trim());
                //kembali ke parameters
                Log.d(TAG, "" + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                120000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest, ParameterConfig.tag_json_obj);
    }

    public void kosong() {
        phone.setText("");
        password.setText("");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
