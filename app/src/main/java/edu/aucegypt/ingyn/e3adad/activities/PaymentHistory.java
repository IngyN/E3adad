package edu.aucegypt.ingyn.e3adad.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    PayPalConfiguration config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK).clientId("<YOUR_CLIENT_ID>");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);

        Intent intent = new Intent(PaymentHistory.this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
        //A ProgressDialog object
        final ProgressDialog progress = new ProgressDialog(PaymentHistory.this);
        progress.setMessage(" Loading ");progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();

        ActionBar bar = this.getActionBar();
//        bar.setBackgroundDrawable(getDrawable(R.color.darkprimary));
        bar.setTitle("Payment History");
//
//        Window w = getWindow();
//        w.setStatusBarColor(getResources().getColor(R.color.darkerprimary));
//        w.setNavigationBarColor(getResources().getColor(R.color.darkerprimary));

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
                Log.d("Error Volley", error.toString());

                Toast.makeText(PaymentHistory.this, "Network error: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        QueueSingleton.getInstance(this).addToRequestQueue(request);
    }
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    public void publish(JSONObject response)
    {

        submissionList.clear();

        try {


            JSONArray arr =  response.getJSONArray("results");

            if(arr.length() == 0)
                Toast.makeText(PaymentHistory.this, "No submissions found.", Toast.LENGTH_LONG).show();

            submissionList.clear();

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);

                submission s = new submission();

                s.setId(String.valueOf(obj.getInt("submission_id")));
                s.setReading(String.valueOf(obj.getInt("reading")));
                s.setSubmission_date(String.valueOf(obj.get("submission_date")));
                s.setIs_paid(obj.getInt("is_paid"));
                s.setPayment_date(obj.getString("submission_date"));


                submissionList.add(s);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        listView = (ListView) findViewById(R.id.listView);
        adapter = new HistoryAdapter(this, submissionList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (submissionList.get(position).isPaid()) {
                    Toast.makeText(PaymentHistory.this, "You already paid for this", Toast.LENGTH_SHORT).show();
                }
                else if (submissionList.get(position).isPending()) {
                    Toast.makeText(PaymentHistory.this, "The price is not finalized yet", Toast.LENGTH_SHORT).show();
                }
                else if (submissionList.get(position).isLate()) {

                    PayPalPayment payment = new PayPalPayment(new BigDecimal("50"), "USD", "your Consumption:",
                            PayPalPayment.PAYMENT_INTENT_SALE);

                    Intent i = new Intent(PaymentHistory.this, PaymentActivity.class);

                    // send the same configuration for restart resiliency
                    i.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
                    i.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
                    submissionList.get(position).setIs_paid(2);

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd:MMMM:yyyy HH:mm:ss a");
                    String strDate = sdf.format(c.getTime());
                    SharedPref s = new SharedPref(PaymentHistory.this,strDate);
                    s.saveLastUpdate();

                    startActivityForResult(i, 0);
                }
            }


        });

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
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i("paymentExample", confirm.toJSONObject().toString(4));

                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details.

                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        }
        else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }
}
