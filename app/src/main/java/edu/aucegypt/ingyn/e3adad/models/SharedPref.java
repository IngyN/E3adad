package edu.aucegypt.ingyn.e3adad.models;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hager13 on 08/07/15.
 */
public class SharedPref {
    static Context context;

    static String user_id;
    static String device_id;

    static String last_s_id;
    static String last_submission;
    static String last_price;
    static String last_reading;

    static String not_paid;

    static SharedPreferences user_data;
    static SharedPreferences.Editor editor ;

    public SharedPref (Context context)
    {
        user_data = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
        editor = user_data.edit();
    }
    public SharedPref(Context context,String user_id,String device_id){
        SharedPref.context = context;
        SharedPref.user_id = user_id;
        SharedPref.device_id = device_id;
        user_data = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
        editor = user_data.edit();
    }

    // User and last submission constructor (used in SignIN)
    public SharedPref(Context context,String user_id,String device_id, String last_s_id, String last_submission, String last_price, String last_reading, String not_paid){
        SharedPref.context = context;
        SharedPref.user_id = user_id;
        SharedPref.device_id = device_id;

        SharedPref.last_s_id = last_s_id;
        SharedPref.last_submission = last_submission;
        SharedPref.last_price = last_price;
        SharedPref.last_reading = last_reading;
        SharedPref.not_paid = not_paid;

        user_data = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
        editor = user_data.edit();
    }



    // last submission constructor (used in Camera_scan)
    public SharedPref(Context context, String last_submission, String last_price, String last_reading, String last_paid){
        SharedPref.context = context;
        SharedPref.last_submission = last_submission;
        SharedPref.last_price = last_price;
        SharedPref.last_reading = last_reading;
        SharedPref.not_paid = not_paid;

        user_data = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
        editor = user_data.edit();
    }

     public void saveData(){
         editor.putString("user_id", user_id);
         editor.commit();
         editor.putString("device_id", device_id);
         editor.commit();

         editor.putString("last_s_id", last_s_id);
         editor.commit();
         editor.putString("last_submission", last_submission);
         editor.commit();
         editor.putString("last_price", last_price);
         editor.commit();
         editor.putString("last_reading", last_reading);
         editor.commit();
         editor.putString("not_paid", not_paid);
         editor.commit();
    }

    static public String getUser_id(){
        if (user_data.contains("user_id")) {
            return  user_data.getString("user_id", null);

        }
        else
        {
            return null;
        }
    }
    static public String getDevice_id() {
        if (user_data.contains("device_id")){
            return user_data.getString("device_id", null);
        }
        else return null;

    }
    static public String getLast_submission() {
        if (user_data.contains("last_submission")){
            return user_data.getString("last_submission", null);
        }
        else return null;

    }
    static public String getLast_reading() {
        if (user_data.contains("last_reading")){
            return  user_data.getString("last_reading", null);
        }
        else return null;

    }
    static public String getLast_price() {
        if (user_data.contains("last_price")){
            return user_data.getString("last_price", null);
        }
        else return null;

    }
    static public String getNot_paid() {
        if (user_data.contains("not_paid")) {
            return user_data.getString("not_paid", null);

        } else return null;

    }
    static public void DeleteAll(){
        editor.clear();
        editor.commit();
    }


}
