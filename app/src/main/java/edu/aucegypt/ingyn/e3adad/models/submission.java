package edu.aucegypt.ingyn.e3adad.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Home on 7/7/15.
 */
public class submission {

    private int id;

    private int user_id;
    private int transaction_id;
    private int device_id;

    private String reading;
    private double price;

    private Date submission_date;

    private int is_paid;
    // 0=> not paid
    //1=> pending
    //2=> paid

    //constructors
    public submission() { }

    public submission(int user_id,int device_id, String reading)
    {
      //  setId(id);
        setUser_id(user_id);
       // setTransaction_id(transaction_id);
        setDevice_id(device_id);
        setReading(reading);
      //  setPrice(price);
       // setSubmission_date(submission_date);
       // setIs_paid(is_paid);

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

    public void setTransaction_id(int transaction_id)
    {
        this.transaction_id = transaction_id;
    }

    public void setDevice_id(int device_id) { this.device_id = device_id; }

    public void setReading(String reading) { this.reading = reading; }

    public void setPrice(double price) { this.price = price; }

    public void setSubmission_date(String submission_date)       //takes string and stores it as date
    {
        SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-yyyy HH:mm");

        Date inputDate = new Date();
        try {
            inputDate = fmt.parse(submission_date);
        }catch (ParseException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        this.submission_date = inputDate;
    }

    public void setIs_paid(int is_paid)
    {
        this.is_paid = is_paid;
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

    public int getTransaction_id()
    {
        return this.transaction_id;
    }

    public Date getSubmission_date()
    {
        return this.submission_date;
    }

    public int getDevice_id() { return this.device_id; }

    public String getReading() {return this.reading;}

    public double getPrice() {return this.price; }


    public String getSubmission_dateString()       //return reg_Date as a string
    {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = fmt.format(this.submission_date);
        return dateString;
    }

    public int getIs_paid() { return this.is_paid;}

    public int getIs_paidInt()
    {
        return is_paid;
    }

    // JSON conversion
    JSONObject toJSON (){
        JSONObject obj = new JSONObject();

        try{

       //     obj.put("id", this.getId());
            obj.put("user_id", this.getUser_id());
       //     obj.put("transaction_id", this.getTransaction_id());
            obj.put("device_id", this.getDevice_id());
            obj.put("reading", this.getReading());
       //     obj.put("price", this.getPrice());
        //    obj.put("submission_date", this.getSubmission_dateString());
         //   obj.put("is_paid", this.getIs_paidInt());

        }catch (JSONException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return obj;
    }

    // 0=> not paid
    //1=> pending
    //2=> paid

    public boolean isPaid (){

        return (is_paid==2);
    }

    public boolean isPending (){

        return (is_paid==1);
    }
}
