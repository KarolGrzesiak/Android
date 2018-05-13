package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by karolgrzesiak on 24.07.17.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOCATION_SEPARATOR = "of";
    EarthquakeAdapter(Context context, List<Earthquake> earthquakes){
        super(context,0,earthquakes);
    }

    private String formatDate(Date dateObject){
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject){
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
    private String formatMagnitude(double magnitude){
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude){
        int magnitudeFloor = (int) Math.floor(magnitude);
        int magnitudeColorId;
        switch (magnitudeFloor){
            case 0:
            case 1:
                magnitudeColorId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorId = R.color.magnitude9;
                break;
            default:
                magnitudeColorId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(),magnitudeColorId);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Earthquake currentEarthQuake = getItem(position);
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        TextView locationTextView = (TextView) convertView.findViewById(R.id.location_text_view);
        TextView offsetLocationTextView = (TextView) convertView.findViewById(R.id.offset_location_text_view);
        TextView magnitudeTextView = (TextView) convertView.findViewById(R.id.magnitude_text_view);
        TextView dateTextView = (TextView) convertView.findViewById(R.id.date_text_view);
        TextView timeTextView = (TextView) convertView.findViewById(R.id.time_text_view);

        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();
        int magnitudeColor = getMagnitudeColor(currentEarthQuake.getMagnitude());

        magnitudeCircle.setColor(magnitudeColor);

        Date dateObject = new Date(currentEarthQuake.getTimeInMiliseconds());

        String location = currentEarthQuake.getLocation();
        int indexOfLocationSeparator = location.indexOf(LOCATION_SEPARATOR);
        if(indexOfLocationSeparator==-1){
            offsetLocationTextView.setText(R.string.near_the);
            locationTextView.setText(location);
        }
        else{
            offsetLocationTextView.setText(location.substring(0,indexOfLocationSeparator+2));
            locationTextView.setText(location.substring(indexOfLocationSeparator+3,location.length()));
        }
        magnitudeTextView.setText(formatMagnitude(currentEarthQuake.getMagnitude()));
        dateTextView.setText(formatDate(dateObject));
        timeTextView.setText(formatTime(dateObject));

        return convertView;
    }
}
