package edu.aucegypt.ingyn.e3adad.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.JavascriptInterface;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import edu.aucegypt.ingyn.e3adad.R;
import edu.aucegypt.ingyn.e3adad.models.SharedPref;
import edu.aucegypt.ingyn.e3adad.models.user;
import edu.aucegypt.ingyn.e3adad.network.QueueSingleton;

public class Statistics extends Activity{

    private String user_id;
    private String API_URL = "http://baseetta.com/hatem/e3adad/consumption.php";
    private String AvgCost, AvgCons, TotalCons;
    private TextView tAvgCost, tAvgCons, tTotalCons;
    WebView statistic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        statistic = (WebView)findViewById(R.id.webView);
        tAvgCost = (TextView) findViewById(R.id.AvgCost);
        tAvgCons = (TextView) findViewById(R.id.AvgCons);
        tTotalCons = (TextView) findViewById(R.id.TotalCons);


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


                                tAvgCost.setText(AvgCost + " LE");
                                tAvgCons.setText(AvgCons + " kW");
                                tTotalCons.setText(TotalCons + " kW");


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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
