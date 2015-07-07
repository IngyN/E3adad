package edu.aucegypt.ingyn.e3adad.Adapters;


import edu.aucegypt.ingyn.e3adad.R;
import edu.aucegypt.ingyn.e3adad.network.QueueSingleton;
import edu.aucegypt.ingyn.e3adad.models.submission;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


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



        TextView reading = (TextView) convertView.findViewById(R.id.email);
        TextView price = (TextView) convertView.findViewById(R.id.phone);
        TextView submission_date = (TextView) convertView.findViewById(R.id.verified);

        //TODO use an image or a circle shape and change color value
        TextView is_paid = (TextView) convertView.findViewById(R.id.is_paid);


        // getting contact data for the row
        submission s = SubmissionList.get(position);

        // Reading
        reading.setText(String.valueOf(s.getReading()));

        // Price
        if(s.getPrice() > 0)
        price.setText(String.valueOf(s.getPrice()));
        else
        price.setText("Pending");

        // Date
        submission_date.setText(s.getSubmission_dateString());

        // Payment Status
        if(s.getIs_paid() == true)
            is_paid.setText("Paid");
        else is_paid.setText("Not paid yet");

        return convertView;
    }

}