package com.example.abbinizar.asynctaskloader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.entity.mime.Header;

public class MyAsyncTaskLoader extends AsyncTaskLoader<ArrayList<weather_items>> {
    private ArrayList<weather_items> mData;
    private boolean mHasResult = false;

    private String mKumpulanKota;

    public MyAsyncTaskLoader(final Context context, String kumpulanKota) {
        super(context);

        onContentChanged();
        this.mKumpulanKota = kumpulanKota;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<weather_items> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            onReleaseResources(mData);
            mData = null;
            mHasResult = false;
        }
    }

    private static final String API_KEY = "Isikan API KEY anda...";

    // Format search kota url JAKARTA = 1642911 ,BANDUNG = 1650357, SEMARANG = 1627896
    // http://api.openweathermap.org/data/2.5/group?id=1642911,1650357,1627896&units=metric&appid=API_KEY

    @Override
    public ArrayList<weather_items> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<weather_items> weatherItemses = new ArrayList<>();
        String url = "http://api.openweathermap.org/data/2.5/group?id=" +
                mKumpulanKota+ "&units=metric&appid=" + API_KEY;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("list");

                    for (int i = 0 ; i < list.length() ; i++){
                        JSONObject weather = list.getJSONObject(i);
                        weather_items weatherItems = new weather_items(weather);
                        weatherItemses.add(weatherItems);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

            }

        });

        return weatherItemses;
    }

    protected void onReleaseResources(ArrayList<weather_items> data) {
        //nothing to do.
    }
}