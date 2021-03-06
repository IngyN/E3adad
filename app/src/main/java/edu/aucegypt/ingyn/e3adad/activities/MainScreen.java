package edu.aucegypt.ingyn.e3adad.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
import java.util.Calendar;

import edu.aucegypt.ingyn.e3adad.R;
import edu.aucegypt.ingyn.e3adad.models.SharedPref;
import edu.aucegypt.ingyn.e3adad.network.QueueSingleton;


public class MainScreen extends Activity {
    private ImageButton take_photo,Bluetooth_pair;
    private ImageView payment,statistics;
    private TextView last_submission,last_payment;
    private String last_s_id, last_submission_date, not_paid, last_price, last_reading;
    Button pay;

    public static Activity MAIN;
    static boolean active = false;

    final int REQUEST_PHOTO = 1;
    final int PIC_CROP = 2;
  //  final int startDay = 20, endDay = 31;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        active=true;
        MAIN = this;

        if(SignIN.active){
            SignIN.active = false;
            SignIN.SIGNIN.finish();
        }
        if(SplashScreen.active){
            SplashScreen.active = false;
            SplashScreen.Splash.finish();
        }

        get_status();


        Calendar calendar = Calendar.getInstance();
        final int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
      //  Context context = getActivity();
        take_photo = (ImageButton)findViewById(R.id.take_photo);
        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the user is allowed to take a photo
         /*       if(currentDay >= startDay && currentDay<= endDay) {
                    TakePhoto(v);
                }
                else{
                    Toast.makeText(MainScreen.this,"You are not currently allowed to take a picture. "+ (startDay - currentDay)+" days are left.", Toast.LENGTH_LONG).show();
                }*/
                TakePhoto(v);
            }
        });

        // Sign out button


        payment = (ImageView) findViewById(R.id.pay);
        payment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent mainToPay = new Intent(MainScreen.this, PaymentHistory.class);
                startActivity(mainToPay);
            }

        });
        statistics = (ImageView) findViewById(R.id.stat);
        statistics.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent mainToStat = new Intent(MainScreen.this, Statistics.class);
                startActivity(mainToStat);
            }

        });
        // Bluetooth Activity
        Bluetooth_pair = (ImageButton) findViewById(R.id.bt);
        Bluetooth_pair.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent mainToBt= new Intent(MainScreen.this, Bluetooth.class);
                startActivity(mainToBt);
            }
        });

        // get action bar
       ActionBar actionBar = getActionBar();

        // Enabling Up / Back navigation
        actionBar.setHomeButtonEnabled(true);

        actionBar.setTitle("E-3adad");

        //actionBar.



    }
    public void TakePhoto(View v){
        PackageManager packageManager = this.getPackageManager();
        if(!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Toast.makeText(this, "This device does not have a camera.", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        try{
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            Log.d("StartingCam!", "1");
            startActivityForResult(cameraIntent, REQUEST_PHOTO);
        } catch(ActivityNotFoundException anfe){
            String errorMessage = "Oops..Your device doesn't support capturing images!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("Returning from Cam", "hello");
        if (requestCode == REQUEST_PHOTO && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
        //    performCrop(data.getData());
         //   String t= "Hello OCR";
            Intent mainToCam = new Intent(MainScreen.this, Camera_scan.class);
            mainToCam.putExtra("Reading",encodeTobase64(photo));
            startActivity(mainToCam);
        }
/*
       else if(requestCode == PIC_CROP){
                Bundle extras = data.getExtras();
                Bitmap thePic = extras.getParcelable("data");
                Intent mainToCam =  new Intent(MainScreen.this , Camera_scan.class);
                mainToCam.putExtra("Reading",thePic);
                startActivity(mainToCam);
        }*/
        else {
            startActivity(new Intent(this, MainScreen.class));
        }
    }
    private static String encodeTobase64(Bitmap image)
    {
        Bitmap imagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        return imageEncoded;
    }
    private void performCrop(Uri picUri){
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        }
        catch(ActivityNotFoundException anfe){
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);

        menu.add(0, R.id.logout, 0, "Logout").setIcon(R.drawable.logout)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.logout:
                Intent mainToOut = new Intent(MainScreen.this, SignIN.class);
                // DO i need to remove the shared preferences thing?
                SharedPref sp = new SharedPref(MainScreen.this);
                SharedPref.DeleteAll();
                startActivity(mainToOut);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void get_status(){

        //API
        String API_URL = "http://baseetta.com/hatem/e3adad/main.php";

        //user_id
        SharedPref shpr = new SharedPref(this);
        JSONObject user = new JSONObject();
        try {
            user.put("user_id", SharedPref.getUser_id());
        }catch (JSONException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // POST Request to send Data to the database
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, API_URL, user ,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(MainScreen.this, response.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Volley response Sign In", response.toString());
                try {


                    last_s_id = String.valueOf(response.getString("last_s_id"));
                    last_submission_date = String.valueOf(response.getString("last_submission"));
                    last_price = String.valueOf(response.getString("last_price"));
                    last_reading = String.valueOf(response.getString("last_reading"));
                    not_paid = String.valueOf(response.getString("not_paid"));


                    update_status();



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if((response.has("ERROR")))
                    Toast.makeText(MainScreen.this, "This user doesn't exist. Please make sure you typed all your info correctly. ", Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MainScreen.this, "Network error: " + error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        QueueSingleton.getInstance(this).addToRequestQueue(postRequest);
    }

    private void update_status()
    {

        //STATUS
        last_submission = (TextView)findViewById(R.id.last_submission);
        last_payment = (TextView)findViewById(R.id.last_payment);

        if(last_submission== null){
            last_submission.setText("You don't have any previous submissions.");

            last_payment.setText("You don't have any pending payments.");
            last_payment.setTextColor(this.getResources().getColor(R.color.primary));
        } else{
            last_submission.setText("Your last reading was ");
            last_submission.append(last_reading);
            last_submission.append(" KW on ");
            last_submission.append(last_submission_date);

            if(not_paid !=  "0")
            {
                last_payment.setText("You have " + not_paid +  " unpaid submissions.");
                last_payment.setTextColor(this.getResources().getColor(R.color.late));
            }else{
                last_payment.setText("You don't have any pending payments.");
                last_payment.setTextColor(this.getResources().getColor(R.color.primary));
            }


        }
    }

}
