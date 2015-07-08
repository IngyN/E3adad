package edu.aucegypt.ingyn.e3adad.activities;

import edu.aucegypt.ingyn.e3adad.activities.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import edu.aucegypt.ingyn.e3adad.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class SplashScreen extends Activity {
    public static Activity Splash;
    static boolean active = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        Splash = this;
        active = true;
        Thread t=new Thread()
        {
            public void run()
            {
                try {
                    sleep(3000);
                    finish();
                    Intent cv=new Intent(SplashScreen.this, SignIN.class);
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
