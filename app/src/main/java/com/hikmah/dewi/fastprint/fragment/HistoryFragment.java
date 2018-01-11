package com.hikmah.dewi.fastprint.fragment;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hikmah.dewi.fastprint.R;
import com.hikmah.dewi.fastprint.adapter.AdapterHistory;
import com.hikmah.dewi.fastprint.config.ParameterConfig;
import com.hikmah.dewi.fastprint.config.URLConfig;
import com.hikmah.dewi.fastprint.controller.AppController;
import com.hikmah.dewi.fastprint.model.History;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HistoryFragment extends Fragment implements  SwipeRefreshLayout.OnRefreshListener {
  View rootView;
  @BindView(R.id.history_list)
    RecyclerView mRecyclerView;
  @BindView(R.id.coodinator_histoty)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.swipe_histor)
    SwipeRefreshLayout swipe;
    private ArrayList<History> mHistroyData;
    private AdapterHistory mAdapter;
    private static final String TAGG = HistoryFragment.class.getSimpleName();
    String id_user;
    public HistoryFragment() {
        // Required empty public constructor
    }


    public static HistoryFragment newInstance(){
        return new HistoryFragment();
    }
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, rootView);
        getActivity().setTitle("History");
        swipe.setOnRefreshListener(HistoryFragment.this);
        SharedPreferences preference =
                getActivity().getApplicationContext().getSharedPreferences("data", 0);
         id_user = preference.getString("id_user", null);
        //Set the Layout Manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        //Initialize the ArrayLIst that will contain the data
        mHistroyData = new ArrayList<>();
        //Initialize the adapter and set it ot the RecyclerView
        mAdapter = new AdapterHistory(mHistroyData, this.getActivity());
        mRecyclerView.setAdapter(mAdapter);
        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(false);
                mHistroyData.clear();
                callVolley();

            }
        });
        return rootView;
    }

    private void callVolley() {
        mHistroyData.clear();
        mAdapter.notifyDataSetChanged();
        final ProgressDialog loading = new ProgressDialog(this.getActivity());
        loading.setMessage("Please Wait...");
        loading.show();
        // membuat request JSON
        StringRequest jArr = new StringRequest(Request.Method.POST, URLConfig.URL_HISTORY_RP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAGG, response.toString());

                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONArray Jarr = new JSONArray(response);
                        JSONObject obj = Jarr.getJSONObject(i);
                        History item = new History();
                        item.setTanggal_rp(obj.getString(ParameterConfig.TAG_TANGGAL_ORDER));
                        item.setBayar_rp(obj.getString(ParameterConfig.TAG_TOTAL_BAYAR));
                        item.setStatus_rp(obj.getString(ParameterConfig.TAG_STATUS));
                        // menambah item ke array
                        mHistroyData.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // notifikasi adanya perubahan data pada adapter
                mAdapter.notifyDataSetChanged();
            loading.dismiss();
            }
        }, new Response.ErrorListener() {

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
        }){
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put(ParameterConfig.TAG_ID_USER, id_user);
                return params;
            }
        };
        // menambah request ke request queue
        AppController.getInstance().addToRequestQueue(jArr, ParameterConfig.tag_json_obj);
    }

    @Override
    public void onRefresh() {
        swipe.setRefreshing(false);
        mHistroyData.clear();
        callVolley();
    }
}
