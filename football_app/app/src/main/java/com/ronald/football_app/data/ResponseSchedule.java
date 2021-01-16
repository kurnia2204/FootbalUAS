package com.ronald.football_app.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseSchedule {
    @SerializedName("events")
    private List<Schedule> schedules;

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}
