package edu.aucegypt.ingyn.e3adad.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import edu.aucegypt.ingyn.e3adad.Adapters.HistoryAdapter;
import edu.aucegypt.ingyn.e3adad.R;
import edu.aucegypt.ingyn.e3adad.models.SharedPref;
import edu.aucegypt.ingyn.e3adad.models.submission;
import edu.aucegypt.ingyn.e3adad.network.QueueSingleton;

public class PaymentHistory extends Activity {

    final private String API_URL = "http://baseetta.com/hatem/e3adad/history.php";

    private List<submission> submissionList = new ArrayList<>();
    private ListView listView;
    private HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);

        //A ProgressDialog object
        final ProgressDialog progress = new ProgressDialog(PaymentHistory.this);
        progress.setMessage(" Loading ");progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();


        String user_id =SharedPref.getUser_id();


        StringBuilder paramsBuilder = new StringBuilder();
        try {
            paramsBuilder.append("?");
            paramsBuilder.append("user_id=");
            paramsBuilder.append(URLEncoder.encode(user_id, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        }

        String encodedParams = paramsBuilder.toString();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API_URL + encodedParams, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.has("error")) {
                            progress.dismiss();
                            Toast.makeText(PaymentHistory.this, "Error: " + response.optString("error", ""), Toast.LENGTH_LONG).show();
                        }else {
                            progress.dismiss();
                            publish(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progress.dismiss();
                Toast.makeText(PaymentHistory.this, "Network error: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        QueueSingleton.getInstance(this).addToRequestQueue(request);
    }

    public void publish(JSONObject response)
    {

        submissionList.clear();

        try {


            JSONArray arr =  response.getJSONArray("results");

            if(arr.length() == 0)
                Toast.makeText(PaymentHistory.this, "No submissions found.", Toast.LENGTH_LONG).show();

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);

                submission s = new submission();

                s.setId(String.valueOf(obj.getInt("submission_id")));
                s.setReading(String.valueOf(obj.getInt("reading")));
                s.setSubmission_date(String.valueOf(obj.get("submission_date")));
                s.setIs_paid(obj.getInt("is_paid"));


                submissionList.add(s);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        listView = (ListView) findViewById(R.id.list_item);
        adapter = new HistoryAdapter(this, submissionList);

        listView.setAdapter(adapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_payment_history, menu);
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
