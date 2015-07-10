package edu.aucegypt.ingyn.e3adad.models;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hager13 on 08/07/15.
 */
public class SharedPref {
    static String user_id;
    static String device_id;
    static Context context;
    static float last_estimation;
    static String last_update;

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
    public SharedPref(Context context,String last_update){
        SharedPref.last_update = last_update;
        user_data = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
        editor = user_data.edit();
    }

    public SharedPref(Context context,float last_estimation){
        SharedPref.last_estimation = last_estimation;
        user_data = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
        editor = user_data.edit();
    }
     public void saveData(){
        editor.putString("user_id", user_id);
        editor.commit();
        editor.putString("device_id", device_id);
        editor.commit();
    }
    public void saveEstimate(){
        editor.putFloat("last_estimation", last_estimation);
        editor.commit();
    }
    public void saveLastUpdate(){
        editor.putString("last_update", last_update);
        editor.commit();
    }
    static public String getUser_id(){
        if (user_data.contains("user_id")) {
            user_id = user_data.getString("user_id", null);
            return user_id;
        }
        else
        {
            return null;
        }
    }
    static public String getDevice_id() {
        if (user_data.contains("device_id")){
            device_id = user_data.getString("device_id", null);
            return device_id;
            }
        else return null;

    }
    static public float getLast_estimation() {
        if (user_data.contains("last_estimation")){
            last_estimation = user_data.getFloat("last_estimation", 0);
            return last_estimation;
        }
        else return 0;

    }
    static public String getLast_Update() {
        if (user_data.contains("last_update")){
            last_update = user_data.getString("last_update", null);
            return last_update;
        }
        else return null;

    }

}
