package com.co2.thomas.binweather.model;

/**
 * Created by Thomas on 2017/4/4.
 */
public class County {
    private int id;
    private String countyName;
    private String countyCode;
    private int cityId;

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public String getCountyName(){
        return countyName;
    }

    public void setCountyName(String countyName){
        this.countyName = countyName;
    }

    public String getCountyCode(){
        return countyCode;
    }

    public void setCountyCode(String countyCode){
        this.countyCode = countyCode;
    }

    public int getCityId(){
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
