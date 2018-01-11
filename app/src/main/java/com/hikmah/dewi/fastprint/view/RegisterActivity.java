package com.hikmah.dewi.fastprint.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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

public class RegisterActivity extends AppCompatActivity implements Animation.AnimationListener {
    private static final String TAG1 = RegisterActivity.class.getSimpleName();
    Animation bounce;
    @BindView(R.id.card_register)
    CardView cardView;
    @BindView(R.id.password_register)
    EditText p_register;
    @BindView(R.id.nama)
    EditText n_register;
    @BindView(R.id.phone_register)
    EditText ph_register;
    @BindView(R.id.coordinator_register)
    CoordinatorLayout coordinatorLayout;
    int iduser = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        NoActionBarConfig noActionBarConfig = new NoActionBarConfig();
        noActionBarConfig.fullScreen(this);
        ButterKnife.bind(this);
        bounce = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bounce);
        cardView.startAnimation(bounce);
    }

    @OnClick(R.id.tanya_register)
    void tayan_register() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    @OnClick(R.id.register)
    void btn_register() {
        if ((ph_register.getText().toString().equals("")) && (p_register.getText().toString().equals("")) && (n_register.getText().toString().equals(""))) {
            Snackbar snacka = Snackbar.make(coordinatorLayout, R.string.phonenpass, Snackbar.LENGTH_LONG);
            snacka.show();
        } else if (ph_register.getText().toString().equals("")) {
            Snackbar snacka = Snackbar.make(coordinatorLayout, R.string.ephone, Snackbar.LENGTH_LONG);
            snacka.show();
        } else if (p_register.getText().toString().equals("")) {
            Snackbar snacka = Snackbar.make(coordinatorLayout, R.string.epassword, Snackbar.LENGTH_LONG);
            snacka.show();
        } else if (n_register.getText().toString().equals("")) {
            Snackbar snacka = Snackbar.make(coordinatorLayout, R.string.ename, Snackbar.LENGTH_LONG);
            snacka.show();
        } else {
            register(iduser);
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

    private void register(final int id) {
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setMessage("Proses Registrasi");
        loading.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLConfig.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG1, "Response: " + response.toString());

                        try {
                            JSONObject jObj = new JSONObject(response);
                            ParameterConfig.success = jObj.getInt(ParameterConfig.TAG_SUCCESS);
                            if (ParameterConfig.success == 1) {
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
                        //menghilangkan progress dialog
                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //menghilangkan progress dialog
                        loading.dismiss();
                        //menampilkan toast
                        Toast.makeText(getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        Log.d(TAG1, error.getMessage().toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                //membuat parameters
                Map<String, String> params = new HashMap<String, String>();
                //menambah parameter yang di kirim ke web servis
                params.put(ParameterConfig.TAG_ID_USER, String.valueOf(id));
                params.put(ParameterConfig.TAG_NAMA, n_register.getText().toString().trim());
                params.put(ParameterConfig.TAG_ALAMAT, "-");
                params.put(ParameterConfig.TAG_NO_HP, ph_register.getText().toString().trim());
                params.put(ParameterConfig.TAG_EMAIL, "-");
                params.put(ParameterConfig.TAG_PASSWORD, p_register.getText().toString().trim());
                //kembali ke parameters
                Log.d(TAG1, "" + params);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest, ParameterConfig.tag_json_obj);
    }

    public void kosong() {
        n_register.setText("");
        p_register.setText("");
        ph_register.setText("");
    }
}
