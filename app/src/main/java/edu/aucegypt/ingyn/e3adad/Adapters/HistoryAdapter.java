package edu.aucegypt.ingyn.e3adad.Adapters;


import android.app.Activity;
import android.content.Context;
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

        ImageView status = (ImageView) convertView.findViewById(R.id.status);
        TextView payment_date = (TextView) convertView.findViewById(R.id.paid);
        TextView price = (TextView) convertView.findViewById(R.id.amount);
        TextView submission_date = (TextView) convertView.findViewById(R.id.recorded);
        TextView month = (TextView) convertView.findViewById(R.id.month);


        // getting contact data for the row
        submission s = SubmissionList.get(position);

        // Reading
        price.setText(String.valueOf(s.getReading()));

        // Payment Status
        if(s.isPending())
        {
            //status.mutate().setColorFilter(R.color.pending, PorterDuff.Mode.MULTIPLY);
        }
        else if(s.isPaid())
        {
            //status.mutate().setColorFilter(R.color.paid, PorterDuff.Mode.MULTIPLY);
        }
        else if (s.isLate())
        {

            //tatus.mutate().setColorFilter(Color.parseColor(convertView.getResources().getColor(R.color.late));
           Drawable st =  status.getDrawable();
            int late= convertView.getResources().getColor(R.color.late);
            st.setColorFilter(late, PorterDuff.Mode.MULTIPLY);
        }
        // Date
        submission_date.setText(s.getSubmission_dateString());

       // month

        month.setText(s.getSubmission_month());

        payment_date.setText(s.getPayment_dateString());

        return convertView;
    }

}