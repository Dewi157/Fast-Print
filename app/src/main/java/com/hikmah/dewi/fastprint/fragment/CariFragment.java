package com.hikmah.dewi.fastprint.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.hikmah.dewi.fastprint.view.DetailActivity;
import com.hikmah.dewi.fastprint.R;
import com.hikmah.dewi.fastprint.adapter.AdapterPrinter;
import com.hikmah.dewi.fastprint.config.URLConfig;
import com.hikmah.dewi.fastprint.controller.AppController;
import com.hikmah.dewi.fastprint.model.Jarak;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CariFragment extends Fragment implements LocationListener,
        SwipeRefreshLayout.OnRefreshListener {
    View rootView;
    @BindView(R.id.coodinator_cari)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.listView)
    ListView list;
    List<Jarak> itemList = new ArrayList<>();
    Double latitude, longitude;
    Criteria criteria;
    Location location;
    AdapterPrinter adapter;
    LocationManager locationManager;
    String provider;
    ProgressDialog loading = null;
    private static final String TAG = CariFragment.class.getSimpleName();

    public CariFragment() {
        // Required empty public constructor
    }

    public static CariFragment newInstance() {
        return new CariFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_cari, container, false);
        getActivity().setTitle("Search Rental Printer");
        ButterKnife.bind(this, rootView);
        loading = new ProgressDialog(this.getActivity());
        itemList.clear();
        adapter = new AdapterPrinter(CariFragment.this, itemList);
        list.setAdapter(adapter);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        swipe.setOnRefreshListener(CariFragment.this);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
            builder.setMessage(R.string.gps_disabled_message)
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        list.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String idx = itemList.get(position).getId_rp();
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("id_rp",idx);
                startActivity(intent);
            }
        });
        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(false);
                           itemList.clear();
                           lokasi();

                       }
                   }
        );
        return rootView;
    }

    @SuppressLint("MissingPermission")
    private void lokasi() {
        location = locationManager.getLastKnownLocation(provider);
        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.requestLocationUpdates(provider, 10000, 3, this);

        if (location != null) {
            onLocationChanged(location);
        } else {
            callListVolley(-6.920957, 107.720645);
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        Log.e(TAG, "User location latitude:" + latitude + ", longitude:" + longitude);
        itemList.clear();
        callListVolley(latitude, longitude);
    }

    @Override
    public void onStatusChanged(String provider, int i, Bundle bundle) {

    }
    @Override
    public void onProviderEnabled(String provider) {

    }
    @Override
    public void onProviderDisabled(String provider) {
    }
    @Override
    public void onRefresh() {
        swipe.setRefreshing(false);
        itemList.clear();
        lokasi();
    }

    private void callListVolley(double lat, double lng) {
        itemList.clear();
        adapter.notifyDataSetChanged();

        loading.setMessage("Refreshing Location...");
        loading.show();
        JsonArrayRequest jArr = new JsonArrayRequest(URLConfig.URL_LIST_PRINTER + lat + "&lng=" + lng,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, response.toString());
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Jarak j = new Jarak();
                                j.setNama(obj.getString("nama"));
                                j.setAlamat(obj.getString("alamat"));
                                double jarak = Double.parseDouble(obj.getString("jarak"));
                                j.setJarak("" + round(jarak, 2));
                                j.setId_rp(obj.getString("id_rp"));
                                itemList.add(j);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        // memberitahu adapter jika ada perubahan data
                        adapter.notifyDataSetChanged();
                        loading.dismiss();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.e(TAG, "Error: " + volleyError.getMessage());
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
        });
        jArr.setRetryPolicy(new DefaultRetryPolicy(
                120000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(jArr);
    }

}
