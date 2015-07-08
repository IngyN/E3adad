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
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import edu.aucegypt.ingyn.e3adad.R;


public class MainScreen extends Activity {
    private ImageButton take_photo;
    private ImageView payment,statistics;
    final int REQUEST_PHOTO = 1;
    final int PIC_CROP = 2;
  //  final int startDay = 20, endDay = 31;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(SignIN.active){
            SignIN.active = false;
            SignIN.SIGNIN.finish();
        }
        if(SplashScreen.active){
            SplashScreen.active = false;
            SplashScreen.Splash.finish();
        }

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

        // get action bar
       ActionBar actionBar = getActionBar();

        // Enabling Up / Back navigation
        actionBar.setHomeButtonEnabled(true);

        actionBar.setTitle("E-3adad");

        actionBar.setBackgroundDrawable(getDrawable(R.color.darkprimary));

        Window w = getWindow();
        w.setStatusBarColor(getResources().getColor(R.color.darkerprimary));
        w.setNavigationBarColor(getResources().getColor(R.color.darkerprimary));


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
