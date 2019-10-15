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

class BmiListAdapter extends ArrayAdapter<BMI> {
    private int resource;
    private String date, time, bmi_units;
    private Context mcontext;

    public BmiListAdapter(Context context, int resource, ArrayList<BMI> objects) {
        super(context, resource, objects);
        mcontext = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        date = getItem(position).getDate();
        time = getItem(position).getTime();
        bmi_units = getItem(position).getBmi_data();

        LayoutInflater inflater = LayoutInflater.from(mcontext);
        convertView = inflater.inflate(R.layout.bmi_listview_content,parent, false);

        TextView bmiop = convertView.findViewById(R.id.bmi_data);
        TextView  timeop = convertView.findViewById(R.id.time_data);
        TextView dateop = convertView.findViewById(R.id.date_data);

        bmiop.setText(bmi_units);
        timeop.setText(time);
        dateop.setText(date);


        return convertView;
    }

}
