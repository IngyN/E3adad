package edu.aucegypt.ingyn.e3adad.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Space;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aucegypt.ingyn.e3adad.R;
import edu.aucegypt.ingyn.e3adad.models.user;
import edu.aucegypt.ingyn.e3adad.network.QueueSingleton;

public class SignIN extends Activity {
    private EditText nationalID_in,serialNumber_in,email_in;
    private Button register_user;
    private String nationalID,serialNumber,email;
    private String API_URL = "";
    private int user_id,device_id;
    public static Activity SIGNIN;
    static boolean active = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SIGNIN = this;
        active = true;
        if(SplashScreen.active){
            SplashScreen.active = false;
            SplashScreen.Splash.finish();
        }
        ActionBar bar = this.getActionBar();

        bar.setTitle("E-3adad");
//        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(getDrawable(R.color.darkprimary));

        Window w = getWindow();
        w.setStatusBarColor(getResources().getColor(R.color.darkprimary));
        w.setNavigationBarColor(getResources().getColor(R.color.darkprimary));

//        bar.setHomeButtonEnabled(true);
//        bar.setDisplayOptions(ActionBar.DISPLAY_USE_LOGO);

        nationalID_in = (EditText)findViewById(R.id.national_id);
        serialNumber_in = (EditText)findViewById(R.id.serial_number);
        email_in = (EditText)findViewById(R.id.email);

        register_user = (Button) findViewById(R.id.register);
        register_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nationalID = nationalID_in.getText().toString();
                serialNumber = serialNumber_in.getText().toString();
                email = email_in.getText().toString();
                if (nationalID.length() <= 14) {
                    Toast.makeText(SignIN.this, "Wrong National ID", Toast.LENGTH_SHORT).show();
                } else if(serialNumber.length()<= 10) {
                    Toast.makeText(SignIN.this, "Invalid Serial Number", Toast.LENGTH_SHORT).show();
                }
                else {
                    // Maybe we should keep it as a string
                    // the number is too big....

                 //   RegisterNewUser();
                    Intent regToMain = new Intent(SignIN.this, MainScreen.class);
                    startActivity(regToMain);
                }
            }
        });

    }
    private void RegisterNewUser(){

        user newUser = new user(email,nationalID,serialNumber);

        // POST Request to send Data to the database
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, API_URL,newUser.toJSON() ,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(SignIN.this, response.toString(), Toast.LENGTH_SHORT).show();
                try {
                    user_id = response.getInt("user_id");
                    device_id = response.getInt("device_id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(SignIN.this, "Network error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        QueueSingleton.getInstance(this).addToRequestQueue(postRequest);
    }
    private void SaveToSharedPreferences(){
        SharedPreferences.Editor editor = getSharedPreferences("user_data", Context.MODE_PRIVATE).edit();
        editor.putInt("user_id", user_id);
        editor.putInt("device_id", device_id);
        editor.apply();
    }
    private void GetFromSharedPreferences(int user,int device){
        SharedPreferences user_data = getSharedPreferences("user_data", MODE_PRIVATE);
        user = user_data.getInt("user_id",0);
        device = user_data.getInt("device_id",0);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
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
