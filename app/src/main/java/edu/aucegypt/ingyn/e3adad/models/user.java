package edu.aucegypt.ingyn.e3adad.models;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Home on 7/7/15.
 */
public class user {

    static private String id;

//    static private String username;
    static private String first_name;
    static private String last_name;
    static private String mobile;
    static private String address;
    static private String email;
    static private String national_id;
    static private String serial_number;
    static private String device_id;

    static private Date reg_date;

    static private boolean verified;


    //constructors
    public user() { }

//    public user( int id, String username, String first_name, String last_name,  String mobile, String address,
//                    String email, String national_id, int device_id, String reg_date, boolean verified)
//    {
    public user(String serial_number,String national_id, String email){
     //   setId(id);
       // setUsername(username);
        //setFirst_name(first_name);
        //setLast_name(last_name);
       // setMobile(mobile);
       // setAddress(address);
        setEmail(email);
        setNational_id(national_id);
        setSerial_number(serial_number);
     //   setDevice_id(device_id);
       // setReg_date(reg_date);
        //setVerified(verified);
    }

    //setters
    public void setId(String id)
    {
        user.id = id;
    }

//    public void setUsername(String username)
//    {
//        this.username = username;
//    }

    public void setFirst_name(String first_name)
    {
        user.first_name = first_name;
    }

    public void setLast_name(String last_name)
    {
        user.last_name = last_name;
    }

    public void setMobile(String mobile)
    {
        user.mobile = mobile;
    }

    public void setAddress(String address)
    {
        user.address = address;
    }

    public void setEmail(String email)
    {
        user.email = email;
    }

    public void setNational_id(String national_id)
    {
        user.national_id = national_id;
    }

    public void setSerial_number(String serial_number)
    {
        user.serial_number = serial_number;
    }

    public void setDevice_id(String device_id)
    {
        user.device_id = device_id;
    }


    public void setReg_date(String reg_date)       //takes string and stores it as date
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
        user.reg_date = inputDate;
    }

    public void setVerified(boolean verified)
    {
        user.verified = verified;
    }

    public void verify()
    {
        verified = true;
    }


    public void assignDevice(device d)         //Assign Device ID to this user and copy this user_id to the device
    {
//        this.setDevice_id(d.getId());
//        d.setUser_id(this.getId());
    }

    //getters
    public String getId()
    {
        return id;
    }

//    public String getUsername()
//    {
//        return this.username;
//    }

    public String getFirst_name()
    {
        return first_name;
    }

    public String getLast_name()
    {
        return last_name;
    }

    public String getFullname()
    {
        return first_name + " " + last_name;
    }

    public String getMobile()
    {
        return mobile;
    }

    public String getAddress()
    {
        return address;
    }

    public String getEmail()
    {
        return email;
    }

    public String getNational_id()
    {
        return national_id;
    }

    public String getDevice_id()
    {
        return device_id;
    }

    public String getSerial_number()
    {
        return serial_number;
    }

    public Date getReg_date()
    {
        return reg_date;
    }

    public String getRegDateString()       //return reg_Date as a string
    {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = fmt.format(reg_date);
        return dateString;
    }

    public int getVerified()
    {
        if( verified )
            return 1;
        else
            return 0;
    }

    public boolean isVerified()
    {
        return verified;
    }


    // JSON conversion
    public JSONObject toJSON (){
        JSONObject obj = new JSONObject();

        try{
            
       /*     obj.put("id", this.getId());
            obj.put("username", this.getUsername());
            obj.put("first_name", this.getFirst_name());
            obj.put("last_name", this.getLast_name());
            obj.put("mobile", this.getMobile());
            obj.put("address", this.getAddress()); */
            obj.put("serial", this.getSerial_number());
            obj.put("national_id", this.getNational_id());
            obj.put("email", this.getEmail());
//            obj.put("reg_date", this.getRegDateString());
//            obj.put("verified", this.getVerified());

        }catch (JSONException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return obj;
    }

}
