package edu.aucegypt.ingyn.e3adad.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import edu.aucegypt.ingyn.e3adad.R;
import edu.aucegypt.ingyn.e3adad.models.SharedPref;
import edu.aucegypt.ingyn.e3adad.network.QueueSingleton;

public class Statistics extends Activity{

    private String user_id;
    private String API_URL = "http://baseetta.com/hatem/e3adad/consumption.php";
    private String progress, AvgCost, AvgCons, TotalCons, diff;
    private TextView tProgress, tAvgCost, tAvgCons, tTotalCons;
    WebView statistic;
    static File new_directory = new File(Environment.getExternalStorageDirectory() + "/E3adad images");
    private static String filename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);


        statistic = (WebView)findViewById(R.id.webView);
        tAvgCost = (TextView) findViewById(R.id.AvgCost);
        tAvgCons = (TextView) findViewById(R.id.AvgCons);
        tTotalCons = (TextView) findViewById(R.id.TotalCons);
        tProgress = (TextView) findViewById(R.id.progress);


        SharedPref shpr = new SharedPref(this);
        user_id= SharedPref.getUser_id();

        statistic.getSettings().setJavaScriptEnabled(true);
        statistic.loadUrl("http://baseetta.com/hatem/e3adad/stats.php?user_id=" + user_id);


        StringBuilder paramsBuilder = new StringBuilder();
        try {
            paramsBuilder.append("?");
            paramsBuilder.append("user_id=");
            paramsBuilder.append(URLEncoder.encode(user_id, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Toast.makeText(Statistics.this, "StringBuilder exception", Toast.LENGTH_LONG).show();
        }
        String encodedParams = paramsBuilder.toString();

        // GET Request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API_URL + encodedParams, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.has("error")) {
                            Toast.makeText(Statistics.this, "Error: " + response.optString("error", ""), Toast.LENGTH_LONG).show();
                        }else {
                            try {
                                AvgCost = response.getString("AvgCost");
                                AvgCons = response.getString("AvgCons");
                                TotalCons = response.getString("TotalCons");
                                progress = response.getString("progress");
                                diff = response.getString("diff");


                                tAvgCost.setText(AvgCost + " LE");
                                tAvgCons.setText(AvgCons + " kW");
                                tTotalCons.setText(TotalCons + " kW");

                                int progressINT = Integer.parseInt(progress);

                                if(progressINT > 0)
                                tProgress.setText(progress + " % better / " + diff + " kW saved");
                                else tProgress.setText(progress + " % worse / " + diff + " kW wasted");


                            } catch (JSONException e) {
                                Toast.makeText(Statistics.this, "JSON Exception", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(Statistics.this, "Network error: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        QueueSingleton.getInstance(this).addToRequestQueue(request);

    }
    public static void takeScreenshot(Activity activity) {

        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(false);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();
        Bitmap screenshot = Bitmap.createBitmap(bitmap, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();

        SaveImage(screenshot);
    }
    private static void SaveImage(Bitmap bm){
        new_directory.mkdirs();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
        File f = new File(new_directory+ File.separator + filename + ".jpg");
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statistics, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String datestring = dateFormat.format(date);
        filename = "image"+ datestring;

        switch (item.getItemId()) {
            case R.id.share:
                takeScreenshot(Statistics.this);
                Uri uri = Uri.fromFile(new File(new_directory+ File.separator + filename + ".jpg"));
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                shareIntent.setType("image/JPEG");
                startActivity(Intent.createChooser(shareIntent, "Share to "));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
