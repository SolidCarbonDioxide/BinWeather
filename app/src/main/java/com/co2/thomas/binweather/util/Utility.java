package com.co2.thomas.binweather.util;

import android.text.TextUtils;

import com.co2.thomas.binweather.model.BinWeatherDB;
import com.co2.thomas.binweather.model.City;
import com.co2.thomas.binweather.model.County;
import com.co2.thomas.binweather.model.Province;

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

}