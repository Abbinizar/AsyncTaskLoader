package com.example.abbinizar.asynctaskloader;

import android.support.v7.app.AppCompatActivity;

import org.json.JSONObject;

import java.text.DecimalFormat;

public class weather_items extends AppCompatActivity {
    public int id;
    public String nama, currentWeather, description, temperature;

    public weather_items(JSONObject object){
        try{
            int id = object.getInt("id");
            String name = object.getString("name");
            String currentWeather = object.getJSONArray("weather").getJSONObject(0).getString("main");
            String description = object.getJSONArray("weather").getJSONObject(0).getString("description");
            double tempInKelvin = object.getJSONObject("main").getDouble("temp");
            double tempInCelcius = tempInKelvin - 273;
            String temperature = new DecimalFormat("##.##").format(tempInCelcius);
            this.id = id;
            this.nama = name;
            this.currentWeather = currentWeather;
            this.description = description;
            this.temperature = temperature;
        }catch (Exception e)    {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public String getCurrentWeather() {
        return currentWeather;
    }
    public void setCurrentWeather(String currentWeather) {
        this.currentWeather = currentWeather;
    }
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTemperature() {
        return temperature;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
