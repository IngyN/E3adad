package edu.aucegypt.ingyn.e3adad.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import edu.aucegypt.ingyn.e3adad.R;
import edu.aucegypt.ingyn.e3adad.activities.util.SystemUiHider;
import edu.aucegypt.ingyn.e3adad.models.SharedPref;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class SplashScreen extends Activity {
    public static Activity Splash;
    static boolean active = false;
    public String device_id;
    public String  user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        SharedPref p = new SharedPref(this);

        Splash = this;
        active = true;
        Thread t=new Thread()
        {
            public void run()
            {
                try {
                    device_id=SharedPref.getDevice_id();
                    user_id= SharedPref.getUser_id();
                    sleep(3000);
                    //finish();
                    Intent cv;
                    if(device_id==null|| user_id==null) {
                        cv = new Intent(SplashScreen.this, SignIN.class);

                    }
                    else {
                        cv = new Intent (SplashScreen.this, MainScreen.class);
                    }
                    startActivity(cv);
                }

                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }


}
