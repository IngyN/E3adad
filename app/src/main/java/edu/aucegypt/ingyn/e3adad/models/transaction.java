package edu.aucegypt.ingyn.e3adad.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Home on 7/7/15.
 */
public class transaction {

    private int id;

    private int user_id;
    private Date t_date;

    private String other_data;
    private double price;


    //constructors
    public transaction() { }

    public transaction( int id, int user_id, String t_date, String other_data, double price)
    {
        setId(id);
        setUser_id(user_id);
        setT_date(t_date);
        setOther_data(other_data);
        setPrice(price);
    }


    //setters
    public void setId(int id)
    {
        this.id = id;
    }

    public void setUser_id(int user_id)
    {
        this.user_id = user_id;
    }



    public void setT_date(String t_date)       //takes string and stores it as date
    {
        SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-yyyy HH:mm");

        Date inputDate = new Date();
        try {
            inputDate = fmt.parse(t_date);
        }catch (ParseException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        this.t_date = inputDate;
    }

    public void setOther_data(String other_data)
    {
        this.other_data = other_data;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }


    //getters
    public int getId()
    {
        return this.id;
    }

    public int getUser_id()
    {
        return this.user_id;
    }

    public Date getT_date()
    {
        return this.t_date;
    }

    public String getT_dateString()       //return reg_Date as a string
    {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = fmt.format(this.t_date);
        return dateString;
    }

    public String getOther_data()
    {
        return  this.other_data;
    }

    public double getPrice()
    {
        return  this.price;
    }


    // JSON conversion
    JSONObject toJSON (){
        JSONObject obj = new JSONObject();

        try{

            obj.put("id", this.getId());
            obj.put("user_id", this.getUser_id());
            obj.put("t_date", this.getT_dateString());
            obj.put("other_data", this.getOther_data());
            obj.put("price", this.getPrice());


        }catch (JSONException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return obj;
    }
}
