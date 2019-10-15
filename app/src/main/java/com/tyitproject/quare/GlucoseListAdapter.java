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

class GlucoseListAdapter extends ArrayAdapter<Glucose> {
    private int resource;
    private String date, time, glucose_units, mealtime_units;
    private Context mcontext;

    public GlucoseListAdapter(Context context, int resource, ArrayList<Glucose> objects) {
        super(context, resource, objects);
        mcontext = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        date = getItem(position).getDate();
        time = getItem(position).getTime();
        glucose_units = getItem(position).getGlucose_data();
        mealtime_units = getItem(position).getMealtime_data();

        LayoutInflater inflater = LayoutInflater.from(mcontext);
        convertView = inflater.inflate(R.layout.glucose_listview_content,parent, false);

        TextView glucoseop = convertView.findViewById(R.id.glucose);
        TextView mealtimeop = convertView.findViewById(R.id.mt);
        TextView  timeop = convertView.findViewById(R.id.time_data);
        TextView dateop = convertView.findViewById(R.id.date_data);

        glucoseop.setText(glucose_units);
        mealtimeop.setText(mealtime_units);
        timeop.setText(time);
        dateop.setText(date);


        return convertView;
    }

}
