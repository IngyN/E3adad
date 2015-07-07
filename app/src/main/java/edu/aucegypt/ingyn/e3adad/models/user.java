package edu.aucegypt.ingyn.e3adad.models;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Home on 7/7/15.
 */
public class user {

    private int id;

    private String username;
    private String first_name;
    private String last_name;
    private String mobile;
    private String address;
    private String email;
    private String national_id;

    private int device_id;

    private Date reg_date;

    private boolean verified;


    //constructors
    public user() { }

    public user( int id, String username, String first_name, String last_name,  String mobile, String address,
                    String email, String national_id, int device_id, String reg_date, boolean verified)
    {
        setId(id);
        setUsername(username);
        setFirst_name(first_name);
        setLast_name(last_name);
        setMobile(mobile);
        setAddress(address);
        setEmail(email);
        setNational_id(national_id);
        setDevice_id(device_id);
        setReg_date(reg_date);
        setVerified(verified);
    }

    //setters
    void setId(int id)
    {
        this.id = id;
    }

    void setUsername(String username)
    {
        this.username = username;
    }

    void setFirst_name(String first_name)
    {
        this.first_name = first_name;
    }

    void setLast_name(String last_name)
    {
        this.last_name = last_name;
    }

    void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    void setAddress(String address)
    {
        this.address = address;
    }

    void setEmail(String email)
    {
        this.email = email;
    }

    void setNational_id(String national_id)
    {
        this.national_id = national_id;
    }

    void setDevice_id(int device_id)
    {
        this.device_id = device_id;
    }


    void setReg_date(String reg_date)       //takes string and stores it as date
    {
        SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-yyyy HH:mm");

        Date inputDate = new Date();
        try {
            inputDate = fmt.parse(reg_date);
        }catch (ParseException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        this.reg_date = inputDate;
    }

    void setVerified(boolean verified)
    {
        this.verified = verified;
    }

    void verify()
    {
        this.verified = true;
    }

    //Assign Device ID to this user and copy this user_id to the device
    void assignDevice(device d)
    {
        this.setDevice_id(d.getId());
        d.setUser_id(this.getId());
    }

    //getters
    int getId()
    {
        return this.id;
    }

    String getUsername()
    {
        return this.username;
    }

    String getFirst_name()
    {
        return this.first_name;
    }

    String getLast_name()
    {
        return this.last_name;
    }

    String getFullname()
    {
        return this.first_name + " " + this.last_name;
    }

    String getMobile()
    {
        return this.mobile;
    }

    String getAddress()
    {
        return this.address;
    }

    String getEmail()
    {
        return this.email;
    }

    String getNational_id()
    {
        return this.national_id;
    }

    int getDevice_id()
    {
        return this.device_id;
    }

    Date getReg_date()
    {
        return this.reg_date;
    }

    String getRegDateString()       //return reg_Date as a string
    {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = fmt.format(this.reg_date);
        return dateString;
    }

    int getVerified()
    {
        if( this.verified )
            return 1;
        else
            return 0;
    }

    boolean isVerified()
    {
        return this.verified;
    }

    // JSON conversion
    JSONObject toJSON (){
        JSONObject obj = new JSONObject();

        try{
            
            obj.put("id", this.getId());
            obj.put("username", this.getUsername());
            obj.put("first_name", this.getFirst_name());
            obj.put("last_name", this.getLast_name());
            obj.put("mobile", this.getMobile());
            obj.put("address", this.getAddress());
            obj.put("email", this.getEmail());
            obj.put("national_id", this.getNational_id());
            obj.put("device_id", this.getDevice_id());
            obj.put("reg_date", this.getRegDateString());
            obj.put("verified", this.getVerified());

        }catch (JSONException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return obj;
    }
}
