package edu.aucegypt.ingyn.e3adad.models;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hager13 on 08/07/15.
 */
public class SharedPref {
    static int user_id;
    static int device_id;
    static Context context;
    public SharedPref(Context context,int user_id,int device_id){
        this.context = context;
        this.user_id = user_id;
        this.device_id = device_id;
    }
    static SharedPreferences user_data = context.getSharedPreferences("user_data", context.MODE_PRIVATE);
    static SharedPreferences.Editor editor = user_data.edit();
    static public void SaveUser_id(){
        editor.putInt("user_id", user_id);
        editor.commit();
    }
    static public void SaveDevice_id(){
        editor.putInt("device_id", device_id);
        editor.commit();
    }
    static public int GetUser_id(){
        user_id = user_data.getInt("user_id",0);
        return user_id;
    }
    static public int GetDevice_id(){
        device_id = user_data.getInt("device_id",0);
        return device_id;
    }
}
