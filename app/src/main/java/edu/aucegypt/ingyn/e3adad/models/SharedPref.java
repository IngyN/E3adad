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

    static SharedPreferences user_data;
    static SharedPreferences.Editor editor ;

     public void saveData(){
        editor.putString("user_id", user_id);
        editor.commit();
        editor.putString("device_id", device_id);
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

}
