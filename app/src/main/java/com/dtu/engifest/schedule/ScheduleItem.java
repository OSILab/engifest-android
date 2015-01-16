package com.dtu.engifest.schedule;

/**
 * Created by naman on 16-01-2015.
 */
public class ScheduleItem {
    private int id;
    private String name, location, image, profilePic, date, time;

    public ScheduleItem() {
    }

    public ScheduleItem(int id, String name, String image, String status,
                        String profilePic, String timeStamp, String url) {
        super();
        this.id = id;
        this.name = name;
        
        this.location = location;
       
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}