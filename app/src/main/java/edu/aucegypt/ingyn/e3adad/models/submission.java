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

    private String id;

    private String user_id;
    private String transaction_id;
    private String device_id;

    private String reading;
    private double price;

    private Date submission_date;
    private Date payment_date;

    private int is_paid;
    // 0=> not paid
    //1=> pending
    //2=> paid

    //constructors
    public submission() { }

    public submission( String id, String user_id, String transaction_id, String device_id, String reading, double price, String submission_date, String payment_date, int is_paid)
    {
        setId(id);
        setUser_id(user_id);
        setTransaction_id(transaction_id);
        setDevice_id(device_id);
        setReading(reading);
        setPrice(price);
        setSubmission_date(submission_date);
        setIs_paid(is_paid);
        setPayment_date(payment_date);


    }

    public submission (String user_id, String device_id)
    {
        this.user_id= user_id;
        this.device_id= device_id;
    }


    //setters
    public void setId(String id)
    {
        this.id = id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public void setTransaction_id(String transaction_id)
    {
        this.transaction_id = transaction_id;
    }

    public void setDevice_id(String device_id) { this.device_id = device_id; }

    public void setReading(String reading) { this.reading = reading; }

    public void setPrice(double price) { this.price = price; }

    public void setSubmission_date(String submission_date)       //takes string and stores it as date
    {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

    public void setPayment_date(String payment_date)       //takes string and stores it as date
    {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date inputDate = new Date();
        try {
            inputDate = fmt.parse(payment_date);
        }catch (ParseException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        this.payment_date = inputDate;
    }

     //public void setPayment_dateString ()

    public void setIs_paid(int is_paid)
    {
        this.is_paid = is_paid;
    }


    //getters
    public String getId()
    {
        return this.id;
    }

    public String getUser_id()
    {
        return this.user_id;
    }

    public String getTransaction_id()
    {
        return this.transaction_id;
    }

    public String getSubmission_month()
    {
        if (submission_date!=null) {
            String month = String.valueOf(submission_date.getMonth());
            String stringMonth = (String) android.text.format.DateFormat.format("MMM", submission_date);
            return stringMonth;
        }
        else return null;
    }

    public String getDevice_id() { return this.device_id; }

    public String getReading() {return this.reading;}

    public double getPrice() {return this.price; }


    public String getSubmission_dateString()       //return reg_Date as a string
    {
        SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yy");
        String dateString = fmt.format(this.submission_date);
        return dateString;
    }

    public String getPayment_dateString()       //return reg_Date as a string
    {
        if(payment_date!= null) {
            SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yy");
            String dateString = fmt.format(this.payment_date);
            return dateString;
        }
        else return "   - -  ";
    }

    public int getIs_paid() { return this.is_paid;}

    public int getIs_paidInt()
    {
        return is_paid;
    }

    // JSON conversion
    public JSONObject toJSON (){
        JSONObject obj = new JSONObject();

        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            obj.put("id", this.getId());
            obj.put("user_id", this.getUser_id());
            obj.put("transaction_id", this.getTransaction_id());
            obj.put("device_id", this.getDevice_id());
            obj.put("reading", this.getReading());
            obj.put("price", this.getPrice());
            obj.put("submission_date", sdf.format(submission_date));
            obj.put("is_paid", this.getIs_paidInt());

            obj.put("payment_date",sdf.format(payment_date));

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

    public boolean isLate ()
    {
        return  (is_paid==0);
    }
}
