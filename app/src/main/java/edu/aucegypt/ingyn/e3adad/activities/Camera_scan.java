package edu.aucegypt.ingyn.e3adad.activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import edu.aucegypt.ingyn.e3adad.R;
import edu.aucegypt.ingyn.e3adad.models.SharedPref;
import edu.aucegypt.ingyn.e3adad.models.submission;
import edu.aucegypt.ingyn.e3adad.network.QueueSingleton;

public class Camera_scan extends Activity{
    private String read_image;
    private TextView put_value,put_price;
    private int final_reading, submission_id;
    private double price;
    final private String API_URL = "http://baseetta.com/hatem/e3adad/submit.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_scan);

        read_image = getIntent().getExtras().getString("Reading");
        ImageView imageView;
        imageView = (ImageView) findViewById(R.id.photo);
        imageView.setImageBitmap(decodeBase64(read_image));
        put_value = (TextView) findViewById(R.id.tvRead);
        put_price = (TextView) findViewById(R.id.tvPrice);
        getReading();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_camera_scan, menu);
        return true;
    }
    private void getReading(){
        String imageTosend = read_image;
        SharedPref sP = new SharedPref(Camera_scan.this);
        final submission newSub = new submission(sP.getUser_id(),sP.getDevice_id(),imageTosend);

        JsonObjectRequest request_reading = new JsonObjectRequest(Request.Method.POST ,API_URL , null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response.has("error")) {
                            Toast.makeText(Camera_scan.this, "Error: " + response.optString("error", ""), Toast.LENGTH_SHORT).show();
                        }else{
                            try {
                                final_reading = response.getInt("reading");
                                price = response.getDouble("price");
                                submission_id = response.getInt("submission_id");

                                put_value.setText(String.valueOf(final_reading));
                                put_price.setText(String.valueOf(price));

                                newSub.setReading(String.valueOf(final_reading));
                                newSub.setPrice(String.valueOf(price));
                                newSub.setSubmission_id(String.valueOf(submission_id));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(Camera_scan.this, "Network error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        QueueSingleton.getInstance(this).addToRequestQueue(request_reading);
    }

    private static Bitmap decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
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
    /* In PHP ,
     echo base64_decode($data);
      header("Content-type: image/gif"); */
}
