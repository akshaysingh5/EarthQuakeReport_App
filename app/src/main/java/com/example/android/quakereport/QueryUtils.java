package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    /** Sample JSON response for a USGS query */

    private static final String USGS_URL ="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2015-3-27&minmagnitude=5";
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link EarthQuake_data} objects that has been built up from
     * parsing a JSON response.
     *
     *

     */


    public static URL makeUrl(String s) {

        URL url=null;

        try {
             url=new URL(s);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }




    public static ArrayList<EarthQuake_data> MakeHttpRequest()
    {
        URL url=null;

        try {
             url=new URL(USGS_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String JsonResponse="";
        ArrayList<EarthQuake_data> data=null;
        HttpURLConnection connection=null;
        InputStream stream=null;


        try {
            connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.connect();

            if(connection.getResponseCode()==200)
            {
                stream=connection.getInputStream();
                JsonResponse=readFromStream(stream);
                Log.d("JSON RESPONSE","Response=" + JsonResponse);
                data=extractEarthquakes(JsonResponse);
                Log.d("QueryUtils","ArrayList= "+data.get(0).getPlace()+data.get(0).getMagnitude());
                return data;
                  }
            else
            {
                return  null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

       return data;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();


    }


    public static ArrayList<EarthQuake_data> extractEarthquakes(String json) throws JSONException {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<EarthQuake_data> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
          JSONObject data=new JSONObject(json);


        try {


            JSONArray array=data.getJSONArray("features");
            for(int i=0;i<array.length();i++)
            {
               JSONObject object= (JSONObject) array.get(i);
              JSONObject prop= object.getJSONObject( "properties");
                 double v= prop.getDouble("mag");
                 String place=prop.getString("place");
                 Long time=prop.getLong("time");
                long timeInMilliseconds = time;
                String url=prop.getString("url");
                Date dateObject = new Date(timeInMilliseconds);
                                earthquakes.add(new EarthQuake_data(v,place,dateObject,url));
            }
            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.

        }
        catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            e.printStackTrace();
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

}