package com.tyitproject.quare;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class BloodpressureListAdapter extends ArrayAdapter<BloodPressure> {
    private int resource;
    private String date, time, systolic, diastolic;
    private Context mcontext;

    public BloodpressureListAdapter(Context context, int resource, ArrayList<BloodPressure> objects) {
        super(context, resource, objects);
        mcontext = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        date = getItem(position).getDate();
        time = getItem(position).getTime();
        systolic = getItem(position).getSystol();
        diastolic = getItem(position).getDiastol();

        LayoutInflater inflater = LayoutInflater.from(mcontext);
        convertView = inflater.inflate(R.layout.bloodpressure_listview_content,parent, false);

        TextView systoholicop = convertView.findViewById(R.id.systolic_data);
        TextView diastoholicop = convertView.findViewById(R.id.diastolic_data);
        TextView  timeop = convertView.findViewById(R.id.time_data);
        TextView dateop = convertView.findViewById(R.id.date_data);

        systoholicop.setText(systolic);
        diastoholicop.setText(diastolic);
        timeop.setText(time);
        dateop.setText(date);


        return convertView;
    }

}
