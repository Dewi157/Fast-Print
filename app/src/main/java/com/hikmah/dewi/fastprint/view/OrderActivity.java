package com.hikmah.dewi.fastprint.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.hikmah.dewi.fastprint.config.FilePathConfig;
import com.hikmah.dewi.fastprint.config.NoActionBarConfig;
import com.hikmah.dewi.fastprint.config.ParameterConfig;
import com.hikmah.dewi.fastprint.config.URLConfig;
import com.hikmah.dewi.fastprint.controller.AppController;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderActivity extends AppCompatActivity {
    private static final String TAG = OrderActivity.class.getSimpleName();

    public static int GET_FILE_PATH = 1;
    public Uri uri;
    SharedPreferences preference;
    String path = "", path1 = "", id_rp = "", id_user = "", date = "";
    @BindView(R.id.coordinator_order)
    CoordinatorLayout coordinatorLayout;
    private int pages = 0, pagescolor = 0, currPage = 0;
    @BindView(R.id.imagepdf)
    ImageView imageView;
    @BindView(R.id.nama_rental)
    TextView nama_rental;
    @BindView(R.id.info_order)
    TextView info_order;
    @BindView(R.id.spjilid)
    Spinner jilid;
    @BindView(R.id.spjeniscover)
    Spinner cover;
    @BindView(R.id.spwarna)
    Spinner warna;
    private int x = 255;
    private int y = 255;
    Bitmap bitmap;
    int harga_wb = 0, harga_color = 0;
    int pixel = 0, redValue = 0, blueValue = 0, greenValue = 0, success;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        NoActionBarConfig noActionBarConfig = new NoActionBarConfig();
        noActionBarConfig.fullScreen(this);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        cover.setEnabled(false);
        warna.setEnabled(false);
        id_rp = getIntent().getStringExtra("idpr");
        if (id_rp != null) {
            detail(id_rp);
            preference =
                    getApplicationContext().getSharedPreferences("data", 0);
            id_user = preference.getString("id_user", null);

        } else {
            Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.id_null, Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
       jilid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int posisi, long l) {
               if(posisi ==1){
                   cover.setEnabled(true);
                   warna.setEnabled(true);
               }else {
                   cover.setEnabled(false);
                   warna.setEnabled(false);
               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });
    }


    @OnClick(R.id.btn_view)
    void view() {
        try {
            Intent intent = new Intent(getApplicationContext(), Viewer.class);
            intent.putExtra("uri", uri.toString());
            startActivity(intent);
        } catch (Exception e) {
            Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.path_null, Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

    }

    @OnClick(R.id.btn_attach)
    void attach() {
        pages = 0;
        pagescolor = 0;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(Intent.createChooser(intent, "Open"), GET_FILE_PATH);
        Toast.makeText(getApplicationContext(), "Please pick pdf file", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_order)
    void order() {
        date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        pertanyaan(id_rp, pages, pagescolor, harga_wb, harga_color);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == GET_FILE_PATH && resultCode == RESULT_OK) {
            uri = intent.getData();
            path = uri.toString();
            if (path != null) {
                Snackbar snacka = Snackbar.make(coordinatorLayout, R.string.berhasil, Snackbar.LENGTH_LONG);
                snacka.show();
                renderPage(currPage);
                filtering();
                path1 = FilePathConfig.getPath(this, uri);
                Toast.makeText(getApplicationContext(), pagescolor + " Pages color from " + pages + " pages", Toast.LENGTH_SHORT).show();
            }

        } else {
            Snackbar snackb = Snackbar.make(coordinatorLayout, R.string.gagal, Snackbar.LENGTH_LONG);
            snackb.show();
        }


    }

    public void renderPage(int pageNumber) {
        Matrix matrix = imageView.getImageMatrix();
        imageView.measure(imageView.getMeasuredWidth(), imageView.getMeasuredHeight());
        try {
            ParcelFileDescriptor parcelFileDescriptor = getApplicationContext().getContentResolver().openFileDescriptor(uri, "r");
            PdfRenderer pdfRenderer = new PdfRenderer(parcelFileDescriptor);
            PdfRenderer.Page page = pdfRenderer.openPage(pageNumber);
            Bitmap bitmap = Bitmap.createBitmap(page.getWidth(), page.getHeight(), Bitmap.Config.ARGB_8888);
            page.render(bitmap, new Rect(0, 0, page.getWidth(), page.getHeight()), matrix, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            if (pages == 0)
                pages = pdfRenderer.getPageCount();
            imageView.setMaxWidth(page.getWidth());
            imageView.setMaxHeight(page.getHeight());
            imageView.setImageBitmap(bitmap);
            imageView.invalidate();
            page.close();
            pdfRenderer.close();
            parcelFileDescriptor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void filtering() {
        for (int i = 0; i < pages; i++) {
            renderPage(i);
            bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            pixel = bitmap.getPixel(x, y);
            redValue = Color.red(pixel);
            blueValue = Color.blue(pixel);
            greenValue = Color.green(pixel);
            if ((pixel != greenValue) || (pixel != blueValue) || (pixel != redValue)) {
                pagescolor = pagescolor + 1;
            } else if ((redValue == Color.RED) && (greenValue == Color.GREEN) && (blueValue == Color.BLUE)) {
                System.out.print("Hitam Putih");
            }
        }
    }

    private void detail(final String id_rp) {
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setMessage("Please Wait...");
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
                        nama_rental.setText(nama);
                        harga_wb = Integer.parseInt(hp);
                        harga_color = Integer.parseInt(warna);
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
                params.put(ParameterConfig.TAG_ID_RP, id_rp);
                return params;
            }

        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                120000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(strReq, ParameterConfig.tag_json_obj);
    }


    public void kosong() {
        info_order.setText("");
        date = "";
    }

    private void pertanyaan(final String id_rpa, int hlm_wba, int hlm_wrna, int hrg_wba, int hrg_wrna) {
        int total_warna;
        int total_wb;
        int total_semuaa;
        dialog = new AlertDialog.Builder(OrderActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.pertanyaan, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Information");
        total_wb = (hlm_wba - hlm_wrna) * hrg_wba;
        total_warna = hlm_wrna * hrg_wrna;
        total_semuaa = total_wb + total_warna;
        if (jilid.getSelectedItem().toString().equals("Yes") || jilid.getSelectedItem().toString().equals("Yes")) {
            if (cover.getSelectedItem().toString().equals("Regular")) {
                total_semuaa = total_semuaa + 2500;
            } else if (cover.getSelectedItem().toString().equals("Soft")) {
                total_semuaa = total_semuaa + 5000;
            } else if (cover.getSelectedItem().toString().equals("Hard")) {
                total_semuaa = total_semuaa + 10000;
            }
        }
        dialog.setMessage("Pages Color " + hlm_wrna + " From " + hlm_wba + " Pages" +
                "\nTotal Rp." + total_semuaa + " Are You Sure For Order?");
        final int total = total_semuaa;
        dialog.setPositiveButton("Continue", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                uploadMultipart(id_rpa, total);
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }

    public void uploadMultipart(final String id_rp, int total_semua) {

        //getting the actual path of the pdf
        String fileku = FilePathConfig.getPath(this, uri);

        if (path == null) {
            Snackbar snack = Snackbar.make(coordinatorLayout, "File Null", Snackbar.LENGTH_LONG);
            snack.show();
        } else {
            //Uploading code
            try {
                String uploadId = UUID.randomUUID().toString();
                //Creating a multi part request
                new MultipartUploadRequest(this, uploadId, URLConfig.URL_ORDER1_RP)
                        .addFileToUpload(fileku, "pdf") //Adding file
                        .addParameter("id_user", id_user)
                        .addParameter("id_rp", id_rp)
                        .addParameter("tgl_order", date)
                        .addParameter("jml_hlm", String.valueOf(pages))
                        .addParameter("jilid", jilid.getSelectedItem().toString())
                        .addParameter("warna", warna.getSelectedItem().toString())
                        .addParameter("cover", cover.getSelectedItem().toString())
                        .addParameter("total_bayar", String.valueOf(total_semua))
                        .addParameter("keterangan", info_order.getText().toString())
                        .addParameter("status", "Belum Dikerjakan")
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2)
                        .startUpload(); //Starting the upload
                Log.d(fileku, "aya");
                kosong();
                Snackbar snack = Snackbar.make(coordinatorLayout, "Please wait file has Upload", Snackbar.LENGTH_LONG);
                snack.show();
            } catch (Exception exc) {
                Snackbar snack = Snackbar.make(coordinatorLayout, exc.getMessage(), Snackbar.LENGTH_LONG);
                snack.show();
            }
        }
    }
}
