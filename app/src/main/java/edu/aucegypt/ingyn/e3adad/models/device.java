package edu.aucegypt.ingyn.e3adad.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Home on 7/7/15.
 */
public class device {

    private int id;

    private int user_id;
    private int serial;

    private Date reg_date;


    //constructors
    public device() { }

    public device( int id, int user_id, int serial, String reg_date)
    {
        setId(id);
        setUser_id(user_id);
        setSerial(serial);
        setReg_date(reg_date);
    }

    //setters
    void setId(int id)
    {
        this.id = id;
    }

    void setUser_id(int user_id)
    {
        this.user_id = user_id;
    }

    void setSerial(int serial)
    {
        this.serial = serial;
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


    //getters
    int getId()
    {
        return this.id;
    }

    int getUser_id()
    {
        return this.user_id;
    }

    int getSerial()
    {
        return this.serial;
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


    // JSON conversion
    JSONObject toJSON (){
        JSONObject obj = new JSONObject();

        try{

            obj.put("id", this.getId());
            obj.put("user_id", this.getUser_id());
            obj.put("serial", this.getSerial());
            obj.put("reg_date", this.getRegDateString());

        }catch (JSONException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return obj;
    }
}
