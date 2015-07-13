package edu.aucegypt.ingyn.e3adad.activities;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.JavascriptInterface;

import edu.aucegypt.ingyn.e3adad.R;

public class Statistics extends Activity{

    WebView statistic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        statistic = (WebView)findViewById(R.id.webView);
        //webView.addJavascriptInterface(new WebAppInterface(), "Android");

        statistic.getSettings().setJavaScriptEnabled(true);
        statistic.loadUrl("http://baseetta.com/hatem/e3adad/stats.php?user_id=3");
        //statistic.loadUrl("http://baseetta.com/hatem/e3adad/stats.php?user_id="+ user_id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statistics, menu);
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