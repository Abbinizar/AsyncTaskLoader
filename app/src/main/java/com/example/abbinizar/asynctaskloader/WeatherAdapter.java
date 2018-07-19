package com.example.abbinizar.asynctaskloader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class WeatherAdapter extends BaseAdapter {
    private ArrayList<weather_items> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public WeatherAdapter (Context context){
        this.context = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData (ArrayList<weather_items> items){
        mData = items;
        notifyDataSetChanged();
    }
    public void addItem(final weather_items items){
        mData.add(items);
        notifyDataSetChanged();
    }
    public void clearData (){
        mData.clear();
    }

    public int getItemViewType (int position){
        return 0;
    }
    public int getViewTypeCount(){
        return 1;
    }
    @Override
    public int getCount() {
        if (mData == null)
        return 0;
        return mData.size();
    }

    @Override
    public weather_items getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder= new ViewHolder();
            convertView = mInflater.inflate(R.layout.weather_items, null);
            holder.textViewNamaKota= (TextView)convertView.findViewById(R.id.textKota);
            holder.textViewTemperature = (TextView)convertView.findViewById(R.id.textTemp);
            holder.textViewDescription = (TextView)convertView.findViewById(R.id.textDesc);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textViewNamaKota.setText(mData.get(position).getNama());
        holder.textViewTemperature.setText(mData.get(position).getTemperature());
        holder.textViewDescription.setText(mData.get(position).getDescription());
        return convertView;
    }

    private class ViewHolder {
        public TextView textViewNamaKota;
        TextView textViewTemperature;
        TextView textViewDescription;
    }
}
