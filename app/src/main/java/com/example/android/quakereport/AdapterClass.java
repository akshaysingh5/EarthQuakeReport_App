package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdapterClass extends ArrayAdapter {


    public AdapterClass(@NonNull Context context, ArrayList<EarthQuake_data> list) {
        super(context, 0, list);
    }

    private int getMagnitudeColor(double mag)

    {
        switch ((int)mag)
        {
            case 1:
                return ContextCompat.getColor(getContext(), R.color.magnitude1);
            case 2:
                return ContextCompat.getColor(getContext(), R.color.magnitude2);
            case 3:
                return ContextCompat.getColor(getContext(), R.color.magnitude3);
            case 4:
                return ContextCompat.getColor(getContext(), R.color.magnitude4);
            case 5:
                return ContextCompat.getColor(getContext(), R.color.magnitude5);
            case 6:
                return ContextCompat.getColor(getContext(), R.color.magnitude6);
            case 7:
                return ContextCompat.getColor(getContext(), R.color.magnitude7);

            case 8:
                return ContextCompat.getColor(getContext(), R.color.magnitude8);
            case 9:
                return ContextCompat.getColor(getContext(), R.color.magnitude9);
            default:
                return ContextCompat.getColor(getContext(), R.color.magnitude10plus);

        }

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitems = convertView;

        if (listitems == null) {
            listitems = LayoutInflater.from(getContext()).inflate(R.layout.listviewlayout, parent, false);
        }

        EarthQuake_data data = (EarthQuake_data) getItem(position);

        TextView magnitude = (TextView) listitems.findViewById(R.id.magnitude);
        TextView place = (TextView) listitems.findViewById(R.id.place);
        TextView date = (TextView) listitems.findViewById(R.id.date);
        TextView time = (TextView) listitems.findViewById(R.id.time);
        TextView city = (TextView) listitems.findViewById(R.id.city);
        DecimalFormat formatter = new DecimalFormat("0.00");
        String output = formatter.format(data.getMagnitude());
        magnitude.setText(("" + output));


        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitude.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(data.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);
        Log.d("Quake","data in Adapter="+data.getMagnitude());

        String str = data.getPlace();

        String [] arrOfStr = str.split("of", 2);
        if(arrOfStr.length>1)
        {
            place.setText(arrOfStr[0]);
            city.setText(arrOfStr[1]);

        }
        else{
            place.setText("Near");
            city.setText(data.getPlace());

        }

        Log.d("Quake","data in Adapter="+data.getPlace());
        Log.d("Quake","data in Adapter="+data.getDate());
        Log.d("Quake","data in Adapter="+data.getUrl());

        Date dateObject = data.getDate();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD, yyyy");
        String dateToDisplay = dateFormatter.format(dateObject);
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        String timed= timeFormat.format(dateObject);
        date.setText(dateToDisplay);
        time.setText(timed);


        return listitems;

    }
}
