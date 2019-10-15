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

class HeartrateListAdapter extends ArrayAdapter<Heartrate> {
    private int resource;
    private String date, time, bpm_units;
    private Context mcontext;

    public HeartrateListAdapter(Context context, int resource, ArrayList<Heartrate> objects) {
        super(context, resource, objects);
        mcontext = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        date = getItem(position).getDate();
        time = getItem(position).getTime();
        bpm_units = getItem(position).getHeartrate_data();

        LayoutInflater inflater = LayoutInflater.from(mcontext);
        convertView = inflater.inflate(R.layout.heartrate_listview_content,parent, false);

        TextView bpmop = convertView.findViewById(R.id.bpm_data);
        TextView  timeop = convertView.findViewById(R.id.time_data);
        TextView dateop = convertView.findViewById(R.id.date_data);

        bpmop.setText(bpm_units);
        timeop.setText(time);
        dateop.setText(date);


        return convertView;
    }

}
