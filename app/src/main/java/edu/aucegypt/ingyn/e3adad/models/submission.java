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

    private String user_id;
    private String transaction_id;
    private String device_id;

    private String reading_image;
    private String reading_int;
    private String price;
    private String submission_id;

    private Date submission_date;

    private int is_paid;
    // 0=> not paid
    //1=> pending
    //2=> paid

    //constructors
    public submission() { }

    public submission(String user_id,String device_id, String reading_image)
    {
      //  setId(id);
        setUser_id(user_id);
       // setTransaction_id(transaction_id);
        setDevice_id(device_id);
        setImage(reading_image);
      //  setPrice(price);
       // setSubmission_date(submission_date);
       // setIs_paid(is_paid);

    }


    //setters
    public void setId(int id)
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

    public void setSubmission_id(String submission_id)
    {
        this.submission_id = submission_id;
    }

    public void setDevice_id(String device_id) { this.device_id = device_id; }

    public void setImage(String reading_image) { this.reading_image = reading_image; }

    public void setReading(String reading_int) { this.reading_int = reading_int; }

    public void setPrice(String price) { this.price = price; }

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

    public String getUser_id()
    {
        return this.user_id;
    }

    public String getTransaction_id()
    {
        return this.transaction_id;
    }

    public Date getSubmission_date()
    {
        return this.submission_date;
    }

    public String getSubmission_id()
    {
        return this.submission_id;
    }

    public String getDevice_id() { return this.device_id; }

    public String getReading() {return this.reading_int;}

    public String getImage() {return this.reading_image;}

    public String getPrice() {return this.price; }


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
            obj.put("reading", this.getImage());
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
