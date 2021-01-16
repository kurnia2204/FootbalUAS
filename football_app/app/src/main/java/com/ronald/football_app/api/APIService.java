package com.ronald.football_app.api;

import com.ronald.football_app.data.ResponseSchedule;
import com.ronald.football_app.data.ResponseTeam;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    @GET("lookup_all_teams.php?id=4328")
    Call<ResponseTeam> getTeams();

    @GET("lookupteam.php")
    Call<ResponseTeam> getTeam(@Query("id") Integer id);

    @GET("eventsnextleague.php?id=4328")
    Call<ResponseSchedule> getSchedules();
}
