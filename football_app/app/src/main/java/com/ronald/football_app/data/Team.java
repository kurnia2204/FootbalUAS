package com.ronald.football_app.data;

import com.google.gson.annotations.SerializedName;

public class Team {
    private int idDb;

    @SerializedName("idTeam")
    private int id;

    @SerializedName("strTeam")
    private String name;

    @SerializedName("strStadium")
    private String stadiumName;

    @SerializedName("strDescriptionEN")
    private String teamDescription;

    @SerializedName("strStadiumDescription")
    private String stadiumDescription;

    @SerializedName("strTeamBadge")
    private String badge;

    @SerializedName("intFormedYear")
    private String formedYear;

    public int getIdDb() {
        return idDb;
    }

    public void setIdDb(int idDb) {
        this.idDb = idDb;
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

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public String getTeamDescription() {
        return teamDescription;
    }

    public void setTeamDescription(String teamDescription) {
        this.teamDescription = teamDescription;
    }

    public String getStadiumDescription() {
        return stadiumDescription;
    }

    public void setStadiumDescription(String stadiumDescription) {
        this.stadiumDescription = stadiumDescription;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getFormedYear() {
        return formedYear;
    }

    public void setFormedYear(String formedYear) {
        this.formedYear = formedYear;
    }
}
