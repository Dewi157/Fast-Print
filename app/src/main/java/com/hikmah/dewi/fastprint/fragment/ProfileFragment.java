package com.hikmah.dewi.fastprint.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.android.volley.toolbox.StringRequest;
import com.hikmah.dewi.fastprint.R;
import com.hikmah.dewi.fastprint.config.ParameterConfig;
import com.hikmah.dewi.fastprint.config.URLConfig;
import com.hikmah.dewi.fastprint.controller.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ProfileFragment extends Fragment {
    View rootView;
    private static final String TAG1 = ProfileFragment.class.getSimpleName();
    @BindView(R.id.nameku)
    TextView namaku;
    @BindView(R.id.phoneku)
    TextView phoneku;
    @BindView(R.id.addressku)
    TextView addressku;
    @BindView(R.id.emailku)
    TextView emailku;
    SharedPreferences preference;
    AlertDialog.Builder dialog;
    String id_user1= "", password1 = "", nama1 = "", alamat1 = "", email1 = "", hp1 = "";
    LayoutInflater inflater;
    View dialogView;
    int success = 0;
    @BindView(R.id.coordinator_edit)
    CoordinatorLayout coordinatorLayout;
    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        getActivity().setTitle("Profile");
        ButterKnife.bind(this, rootView);
        preference =
                getActivity().getApplicationContext().getSharedPreferences("data", 0);
        id_user1 = preference.getString("id_user", null);
        nama1 = preference.getString("nama", null);
        alamat1 = preference.getString("alamat", null);
        hp1 = preference.getString("no_hp", null);
        email1 = preference.getString("email", null);
        password1 = preference.getString("password", null);
        namaku.setText(nama1);
        emailku.setText(email1);
        phoneku.setText(hp1);
        addressku.setText(alamat1);
        return rootView;
    }

    @OnClick(R.id.edit_profile)
    void btn_edit() {
        isi();
    }

    private void isi() {
        dialog = new AlertDialog.Builder(this.getActivity());
        inflater = getActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.isi_profile, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        final EditText editnama = (EditText) dialogView.findViewById(R.id.nama_isi);
        final EditText editalamat = (EditText) dialogView.findViewById(R.id.alamat_isi);
        final EditText editphone = (EditText) dialogView.findViewById(R.id.phone_isi);
        final EditText editemail = (EditText) dialogView.findViewById(R.id.email_isi);
        final EditText editpassword = (EditText) dialogView.findViewById(R.id.password_isi);
        editnama.setText(nama1);
        editalamat.setText(alamat1);
        editphone.setText(hp1);
        editemail.setText(email1);
        editpassword.setText(password1);
        dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                editprofile(id_user1,editnama.getText().toString(), editalamat.getText().toString(),
                        editphone.getText().toString(), editemail.getText().toString(),
                        editpassword.getText().toString());
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    private void editprofile(final String id_user, final String nama, final String alamat, final String no_hp,
                             final String email, final String password) {
        final ProgressDialog loading =new  ProgressDialog(this.getActivity());
        loading.setMessage("Ubah Profile");
        loading.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLConfig.URL_UPADATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG1, "Response: " + response.toString());

                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(ParameterConfig.TAG_SUCCESS);

                            if ( success == 1) {
                                Log.d("v Add", jObj.toString());
                                preference =
                                        getActivity().getApplicationContext().getSharedPreferences("data", 0);
                                SharedPreferences.Editor editor = preference.edit();
                                editor.putString("id_user", id_user);
                                editor.putString("nama", nama);
                                editor.putString("alamat", alamat);
                                editor.putString("no_hp", no_hp);
                                editor.putString("email", email);
                                editor.putString("password", password);
                                editor.commit();
                                namaku.setText(nama);
                                emailku.setText(email);
                                phoneku.setText(no_hp);
                                addressku.setText(alamat);
                                Snackbar snack = Snackbar.make(coordinatorLayout, jObj.getString(ParameterConfig.TAG_MESSAGE), Snackbar.LENGTH_LONG);
                                snack.show();
                                loading.dismiss();
                            } else {
                                Snackbar snack = Snackbar.make(coordinatorLayout, jObj.getString(ParameterConfig.TAG_MESSAGE), Snackbar.LENGTH_LONG);
                                snack.show();
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
                    public void onErrorResponse(VolleyError volleyError) {
                        if (volleyError instanceof NetworkError){
                            Snackbar snacka = Snackbar.make(coordinatorLayout, R.string.networkerror, Snackbar.LENGTH_LONG);
                            snacka.show();
                            loading.dismiss();
                        } else if (volleyError instanceof ServerError){
                            Snackbar snackb = Snackbar.make(coordinatorLayout, R.string.ServerError, Snackbar.LENGTH_LONG);
                            snackb.show();
                            loading.dismiss();
                        } else if (volleyError instanceof AuthFailureError){
                            Snackbar snackc = Snackbar.make(coordinatorLayout, R.string.AuthFailureError, Snackbar.LENGTH_LONG);
                            snackc.show();
                            loading.dismiss();
                        } else if (volleyError instanceof ParseError){
                            Snackbar snackd = Snackbar.make(coordinatorLayout, R.string.ParseError, Snackbar.LENGTH_LONG);
                            snackd.show();
                            loading.dismiss();
                        } else if (volleyError instanceof NoConnectionError){
                            Snackbar snacke = Snackbar.make(coordinatorLayout, R.string.NoConnectionError, Snackbar.LENGTH_LONG);
                            snacke.show();
                            loading.dismiss();
                        } else if (volleyError instanceof TimeoutError){
                            Snackbar snackf = Snackbar.make(coordinatorLayout, R.string.TimeoutError, Snackbar.LENGTH_LONG);
                            snackf.show();
                            loading.dismiss();
                        }
                        loading.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                //membuat parameters
                Map<String, String> params = new HashMap<String, String>();


                //menambah parameter yang di kirim ke web servis
                params.put(ParameterConfig.TAG_ID_USER, id_user);
                params.put(ParameterConfig.TAG_NAMA,nama);
                params.put(ParameterConfig.TAG_ALAMAT, alamat);
                params.put(ParameterConfig.TAG_NO_HP, no_hp);
                params.put(ParameterConfig.TAG_EMAIL, email);
                params.put(ParameterConfig.TAG_PASSWORD, password);
                //kembali ke parameters
                Log.d(TAG1, "" + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                120000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest, ParameterConfig.tag_json_obj);
    }

}
