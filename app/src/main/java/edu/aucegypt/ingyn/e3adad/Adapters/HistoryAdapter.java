package edu.aucegypt.ingyn.e3adad.Adapters;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.aucegypt.ingyn.e3adad.R;
import edu.aucegypt.ingyn.e3adad.models.submission;


public class HistoryAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<submission> SubmissionList;


    public HistoryAdapter(Activity activity, List<submission> SubmissionList) {
        this.activity = activity;
        this.SubmissionList = SubmissionList;
    }

    @Override
    public int getCount() {
        return SubmissionList.size();
    }

    @Override
    public Object getItem(int location) {
        return SubmissionList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        //ImageView status = (ImageView) convertView.findViewById(R.id.status);
        TextView payment_date = (TextView) convertView.findViewById(R.id.paid);
        TextView price = (TextView) convertView.findViewById(R.id.amount);
        TextView submission_date = (TextView) convertView.findViewById(R.id.recorded);
        TextView month = (TextView) convertView.findViewById(R.id.month);


        // getting contact data for the row
        submission s = SubmissionList.get(position);

        // Reading
        price.setText(String.valueOf(s.getReading())+" EGP");
        Drawable myIcon = convertView.getResources().getDrawable(R.drawable.status_button);

        // Payment Status
        if(s.isPending())
        {
            //status.mutate().setColorFilter(R.color.pending, PorterDuff.Mode.MULTIPLY);
            myIcon.setColorFilter(Color.parseColor("#ffadadad"), PorterDuff.Mode.SRC_ATOP);
            ((ImageView)convertView.findViewById(R.id.status)).setImageDrawable(myIcon);
        }
        else if(s.isPaid())
        {
            //status.mutate().setColorFilter(R.color.paid, PorterDuff.Mode.MULTIPLY);
            myIcon.setColorFilter(Color.parseColor("#ff046657"), PorterDuff.Mode.SRC_ATOP);
            ((ImageView)convertView.findViewById(R.id.status)).setImageDrawable(myIcon);
        }
        else if (s.isLate())
        {

//            Resources res = convertView.getResources();
//            final Drawable drawable = res.getDrawable(R.drawable.status_button);
//            drawable.setColorFilter(R.color.late, PorterDuff.Mode.SRC_ATOP);
//            ImageView img = (ImageView)convertView.findViewById(R.id.status);
//            img.setBackgroundDrawable(drawable);
//            Drawable myIcon = convertView.getResources().getDrawable(R.drawable.status_button );
//            ColorFilter filter = new LightingColorFilter(Color.RED, Color.RED );
//            myIcon.setColorFilter(filter);
//            status.setImageDrawable(myIcon);
           // Drawable myIcon = convertView.getResources().getDrawable(R.drawable.status_button);
            myIcon.setColorFilter(Color.parseColor("#ffb81d27"), PorterDuff.Mode.SRC_ATOP);
            ((ImageView)convertView.findViewById(R.id.status)).setImageDrawable(myIcon);

        }
        // Date
        submission_date.setText(s.getSubmission_dateString());

       // month

        month.setText(s.getSubmission_month());

        payment_date.setText(s.getPayment_dateString());

        return convertView;
    }

}