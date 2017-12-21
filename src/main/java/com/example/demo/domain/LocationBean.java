package com.example.demo.domain;

/**
 * Created by 寇含尧 on 2017/11/5.
 */
public class LocationBean {
    private String place;
    private String year;

    public LocationBean(String place, String year) {
        super();
        this.place = place;
        this.year = year;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
