package com.example.androidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class LandingAdaptateur extends ArrayAdapter<Sports> {
    public LandingAdaptateur(Context context, ArrayList<Sports> personArrayList) {
        super(context, R.layout.landinglist, personArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Sports sp = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.landinglist, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.profile_pic);
        TextView sportName = convertView.findViewById(R.id.personName);
       // TextView id = convertView.findViewById(R.id.number);
       TextView prix = convertView.findViewById(R.id.paye);


        imageView.setImageResource(sp.imageId);
       sportName.setText(sp.name);
        //id.setText("voir plus");
        prix.setText(sp.prix);

        return convertView;

    }

}

