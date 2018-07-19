package com.example.abbinizar.asynctaskloader;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<weather_items>>, View.OnClickListener {
    ListView listView;
    WeatherAdapter adapter;
    EditText editKota;
    Button buttonCari;
    static final String EXTRAS_CITY = "EXTRAS_CITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new WeatherAdapter(this);
        adapter.notifyDataSetChanged();
        listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(adapter);

        editKota = (EditText) findViewById(R.id.edit_kota);
        buttonCari = (Button) findViewById(R.id.btn_kota);

        buttonCari.setOnClickListener((View.OnClickListener) this);

        String kota = editKota.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_CITY, kota);

        getLoaderManager().initLoader(0, bundle, this);
    }

    @Override
    public Loader<ArrayList<weather_items>> onCreateLoader(int id, Bundle args) {
        String kumpulankota = "";
        if (args != null) {
            kumpulankota = args.getString(EXTRAS_CITY);
        }
        return new MyAsyncTaskLoader(this, kumpulankota);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<weather_items>> loader, ArrayList<weather_items> data) {
        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<weather_items>> loader) {
        adapter.setData(null);

    }

    @Override
    public void onClick(View v) {

            String kota = editKota.getText().toString();

            if (TextUtils.isEmpty(kota))return;

            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_CITY,kota);
            getLoaderManager().restartLoader(0,bundle,MainActivity.this);
    }
}