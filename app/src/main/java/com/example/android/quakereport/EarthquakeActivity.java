/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {
    AdapterClass adapter;
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.
         ArrayList<EarthQuake_data> list=null;
         donetworking task=new donetworking();
         task.execute("hella");

        //  list.add(new EarthQuake_data(7,"SanFrancisco","3rd August 2018"));
//        list.add(new EarthQuake_data(4,"SanFrancisco","3rd August 2018"));
//        list.add(new EarthQuake_data(5,"SanFrancisco","3rd August 2018"));
//        list.add(new EarthQuake_data(3,"SanFrancisco","3rd August 2018"));
//        list.add(new EarthQuake_data(2,"SanFrancisco","3rd August 2018"));
//        list.add(new EarthQuake_data(1,"SanFrancisco","3rd August 2018"));
//        list.add(new EarthQuake_data(67,"SanFrancisco","3rd August 2018"));



    }

    private class donetworking extends AsyncTask<String,Void,ArrayList<EarthQuake_data>>
    {
        @Override
        protected ArrayList<EarthQuake_data> doInBackground(String... strings) {

            Log.d("App==","Message="+strings[0]);
            ArrayList<EarthQuake_data>data=QueryUtils.MakeHttpRequest();
            return data;
        }

        @Override
        protected void onPostExecute(ArrayList<EarthQuake_data> earthQuake_data) {



            // Find a reference to the {@link ListView} in the layout
            ListView earthquakeListView = (ListView) findViewById(R.id.list);
// Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface


            // Create a new {@link ArrayAdapter} of earthquakes
            adapter=new AdapterClass(getApplicationContext(),earthQuake_data);
            earthquakeListView.setAdapter(adapter);



            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // Find the current earthquake that was clicked on
                    EarthQuake_data currentEarthquake = (EarthQuake_data) adapter.getItem(position);

                    // Convert the String URL into a URI object (to pass into the Intent constructor)
                    Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                    // Create a new intent to view the earthquake URI
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                    // Send the intent to launch a new activity
                    startActivity(websiteIntent);
                }
            });

        }
    }





}