package edu.aucegypt.ingyn.e3adad.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import edu.aucegypt.ingyn.e3adad.R;
import edu.aucegypt.ingyn.e3adad.network.QueueSingleton;

public class Camera_scan extends ActionBarActivity {
    private Bitmap read_image;
    private TextView put_value;
    private int final_reading = 0;
    final private String API_URL = "http://baseetta.com/hatem/e3adad/submit.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_scan);

        read_image = (Bitmap) getIntent().getExtras().get("Reading");
        ImageView imageView;
        imageView = (ImageView) findViewById(R.id.photo);
        imageView.setImageBitmap(read_image);
        put_value = (TextView) findViewById(R.id.tvRead);
        // not sure about this
    /*    getReading();
        if(final_reading!=0){
            put_value.setText(final_reading);
        }*/
//captured picture uri
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_camera_scan, menu);
        return true;
    }
    private void getReading(){
        String imageTosend = encodeTobase64(read_image);
        JSONObject imageObject = new JSONObject();
        /*
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();*/
        try {
            imageObject.put("image",imageTosend);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request_reading = new JsonObjectRequest(Request.Method.POST,API_URL , null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response.has("error")) {
                            Toast.makeText(Camera_scan.this, "Error: " + response.optString("error", ""), Toast.LENGTH_SHORT).show();
                        }else{
                            try {
                                final_reading = response.getInt("value");
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
    private static String encodeTobase64(Bitmap image)
    {
        Bitmap imagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);

        return imageEncoded;
    }
   /* private static Bitmap decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }*/

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
