package com.tyitproject.quare;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeListAdapter extends ArrayAdapter<HomeImageList> {

    private String leftlist_imageName, rightlist_imageName;
   private int leftlist_imageid,rightlist_imageid;
    private Context mcontext;
    private int mresource;

    public HomeListAdapter(Context context, int resource, ArrayList<HomeImageList> objects) {
        super(context, resource, objects);
        mcontext = context;
        mresource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            leftlist_imageName = getItem(position).getLeftlist_name();
            leftlist_imageid = getItem(position).getLeftlist_id();
            rightlist_imageName = getItem(position).getRightlistName();
            rightlist_imageid = getItem(position).getRightlist_id();

            LayoutInflater inflater = LayoutInflater.from(mcontext);
            convertView = inflater.inflate(R.layout.home_listview,parent, false);
            Log.d("LOG","if v==null");

            ImageView imgvw_l = convertView.findViewById(R.id.imgvw_left);
            ImageView imgvw_r = convertView.findViewById(R.id.imgvw_right);
            TextView txtvw_l = convertView.findViewById(R.id.textview_left);
            TextView txtvw_r = convertView.findViewById(R.id.textview_right);

            imgvw_l.setImageResource(leftlist_imageid);
            imgvw_r.setImageResource(rightlist_imageid);
            txtvw_l.setText(leftlist_imageName);
            txtvw_r.setText(rightlist_imageName);

        return convertView;
    }

}
