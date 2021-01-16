package com.ronald.football_app.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseTeam {
    @SerializedName("teams")
    private List<Team> teams;

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
