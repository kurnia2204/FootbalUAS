package com.ronald.football_app.data;

import com.google.gson.annotations.SerializedName;

public class Schedule {
    @SerializedName("idEvent")
    private int id;

    @SerializedName("strHomeTeam")
    private String teamHome;

    @SerializedName("strAwayTeam")
    private String teamAway;

    @SerializedName("strTimestamp")
    private String scheduleDate;

    @SerializedName("strPostponed")
    private String isPostponed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamHome() {
        return teamHome;
    }

    public void setTeamHome(String teamHome) {
        this.teamHome = teamHome;
    }

    public String getTeamAway() {
        return teamAway;
    }

    public void setTeamAway(String teamAway) {
        this.teamAway = teamAway;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getIsPostponed() {
        return isPostponed;
    }

    public void setIsPostponed(String isPostponed) {
        this.isPostponed = isPostponed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("strStatus")
    private String status;
}
