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

public class DiseaseSearchListAdapter extends ArrayAdapter<DiseaseSearchData> {
    private int aboutImage, resource;
    private String diseaseName, symptoms, medicines, precautions;
    private Context mcontext;

    public DiseaseSearchListAdapter(Context context, int resource, ArrayList<DiseaseSearchData> objects) {
        super(context, resource, objects);
        mcontext = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        diseaseName = getItem(position).getDiseaseName();
        symptoms = getItem(position).getSymptoms();
        medicines = getItem(position).getMedicines();
        precautions = getItem(position).getPrecautions();
        aboutImage = getItem(position).getAboutImage();

        LayoutInflater inflater = LayoutInflater.from(mcontext);
        convertView = inflater.inflate(R.layout.disease_listview,parent, false);

        TextView dName = convertView.findViewById(R.id.disease_name);
        TextView  dSymptoms = convertView.findViewById(R.id.symptoms_content);
        TextView dMedicines = convertView.findViewById(R.id.medicine_content);
        TextView dPrecautions = convertView.findViewById(R.id.precaution_content);
        ImageView dAboutImage = convertView.findViewById(R.id.about_disease_image);

        dAboutImage.setImageResource(aboutImage);
        dName.setText(diseaseName);
        Log.d("LOG","symptoms[] = "+symptoms);
        dSymptoms.setText(symptoms);
        dMedicines.setText(medicines);
        dPrecautions.setText(precautions);

        return convertView;
    }

}
