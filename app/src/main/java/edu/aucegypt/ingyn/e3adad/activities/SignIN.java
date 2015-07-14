package edu.aucegypt.ingyn.e3adad.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aucegypt.ingyn.e3adad.R;
import edu.aucegypt.ingyn.e3adad.models.SharedPref;
import edu.aucegypt.ingyn.e3adad.models.user;
import edu.aucegypt.ingyn.e3adad.network.QueueSingleton;

public class SignIN extends Activity {
    private EditText nationalID_in,serialNumber_in,email_in;
    private Button login_user;
    private String nationalID,serialNumber,email;
    private String API_URL = "http://baseetta.com/hatem/e3adad/login.php";
    private String user_id,device_id, last_s_id, last_submission, not_paid, last_price, last_reading;
    public static Activity SIGNIN;
    static boolean active = false;
    private TextView redirect;
    user newUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SIGNIN = this;
        active = true;

        if(Register.active){
            Register.active = false;
            Register.REGISTER.finish();
        }

        if(MainScreen.active){
            MainScreen.active = false;
            MainScreen.MAIN.finish();
        }

        ActionBar bar = this.getActionBar();

        bar.setTitle("E-3adad");
//        ActionBar bar = getActionBar();
//        bar.setBackgroundDrawable(getDrawable(R.color.darkprimary));

//        Window w = getWindow();
//        w.setStatusBarColor(getResources().getColor(R.color.darkerprimary));
//        w.setNavigationBarColor(getResources().getColor(R.color.darkerprimary));

//        bar.setHomeButtonEnabled(true);
//        bar.setDisplayOptions(ActionBar.DISPLAY_USE_LOGO);

        nationalID_in = (EditText)findViewById(R.id.national_id);
        serialNumber_in = (EditText)findViewById(R.id.serial_number);
        email_in = (EditText)findViewById(R.id.email);
        redirect = (TextView) findViewById(R.id.redirect);
        redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regToMain = new Intent(SignIN.this, Register.class);
                startActivity(regToMain);
            }
        });


        login_user = (Button) findViewById(R.id.login);
        login_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nationalID = nationalID_in.getText().toString();
                serialNumber = serialNumber_in.getText().toString();
                email = email_in.getText().toString();
                if (nationalID.length() != 14) {
                    nationalID_in.setError("Wrong Nation ID");
//                    Toast.makeText(SignIN.this, "Wrong National ID", Toast.LENGTH_SHORT).show();
                }
                if(serialNumber.length() != 10) {
                    serialNumber_in.setError("Invalid Serial Number");
//                    Toast.makeText(SignIN.this, "Invalid Serial Number", Toast.LENGTH_SHORT).show();
                }
                if(nationalID.length()==14 && serialNumber.length()==10) {
                    // Maybe we should keep it as a string
                    // the number is too big....
                    RegisterNewUser();
                }
            }
        });

    }



    private void RegisterNewUser(){

       newUser = new user(serialNumber,nationalID,email);

        // POST Request to send Data to the database
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, API_URL, newUser.toJSON() ,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(SignIN.this, response.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Volley response Sign In", response.toString());
                try {


                    user_id = String.valueOf(response.getInt("user_id"));
                    device_id = String.valueOf(response.getInt("device_id"));

                    newUser.setDevice_id(device_id);
                    newUser.setId(user_id);

                    last_s_id = String.valueOf(response.getString("last_s_id"));
                    last_submission = String.valueOf(response.getString("last_submission"));
                    last_price = String.valueOf(response.getString("last_price"));
                    last_reading = String.valueOf(response.getString("last_reading"));
                    not_paid = String.valueOf(response.getString("not_paid"));


                    SharedPref s = new SharedPref(SignIN.this, user_id, device_id, last_s_id, last_submission, last_price, last_reading, not_paid);
                    s.saveData();



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(!(response.has("ERROR"))) {
                    Intent regToMain = new Intent(SignIN.this, MainScreen.class);
                    startActivity(regToMain);

                }else Toast.makeText(SignIN.this, "This user doesn't exist. Please make sure you typed all your info correctly. ", Toast.LENGTH_LONG).show();

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
