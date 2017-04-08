package com.co2.thomas.binweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.co2.thomas.binweather.model.BinWeatherDB;
import com.co2.thomas.binweather.model.City;
import com.co2.thomas.binweather.model.County;
import com.co2.thomas.binweather.model.Province;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Thomas on 2017/4/6.
 */
public class Utility {

    //获取并存储省级数据
    public synchronized static boolean handleProvincesResponse (BinWeatherDB binWeatherDB,
                                                                String response){
        if(!TextUtils.isEmpty(response)){
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length > 0) {
                for (String p : allProvinces) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    binWeatherDB.saveProvince(province);
                }
                return  true;
            }
        }
        return false;
    }

    //获取并存储市级数据
    public synchronized static boolean handleCitiesResponse (BinWeatherDB binWeatherDB, String
            response, int ProvinceId) {
        if (! TextUtils.isEmpty(response)) {
            String[] allCities = response.split(",");
            if (allCities != null && allCities.length > 0) {
                for (String p : allCities) {
                    String[] array = p.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(ProvinceId);
                    binWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    //获取并存储县级数据
    public synchronized static boolean handleCountiesResponse (BinWeatherDB binWeatherDB, String
            response, int cityId) {
        if (! TextUtils.isEmpty(response)) {
            String[] allCounties = response.split(",");
            if (allCounties != null && allCounties.length > 0) {
                for (String p : allCounties) {
                    String[] array = p.split("\\|");
                    County county = new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
                    binWeatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }

    //解析从服务器得到的json数据，并存储到本地
    public static void handleWeatherResponse (Context context, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
            String cityName = weatherInfo.getString("city");
            String weatherCode = weatherInfo.getString("cityid");
            String temp1 = weatherInfo.getString("temp1");
            String temp2 = weatherInfo.getString("temp2");
            String weatherDeps = weatherInfo.getString("weather");
            String publishTime = weatherInfo.getString("ptime");
            saveWeatherInfo (context, cityName, weatherCode, temp1, temp2, weatherDeps,
                    publishTime);
        }  catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void saveWeatherInfo (Context context, String cityName, String weatherCode,
                                        String temp1, String temp2, String weatherDesp, String
                                                publishTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context)
                .edit();
        editor.putBoolean("city_selected", true);
        editor.putString("city_name", cityName);
        editor.putString("weather_code", weatherCode);
        editor.putString("temp1", temp1);
        editor.putString("temp2", temp2);
        editor.putString("weather_desp", weatherDesp);
        editor.putString("publish_time", publishTime);
        editor.putString("current_date", sdf.format(new Date()));
        editor.commit();

    }

}