package com.ronald.football_app.ui.teams;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ronald.football_app.R;
import com.ronald.football_app.api.APIInstance;
import com.ronald.football_app.api.APIService;
import com.ronald.football_app.data.ListTeamAdapter;
import com.ronald.football_app.data.ResponseTeam;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_teams, container, false);
        final ProgressBar teamsProgressBar = root.findViewById(R.id.teams_progress_bar);

        teamsProgressBar.setIndeterminate(true);
        teamsProgressBar.setVisibility(View.VISIBLE);

        APIService apiService = APIInstance.getRetrofitInstance().create(APIService.class);

        Call<ResponseTeam> call = apiService.getTeams();

        call.enqueue(new Callback<ResponseTeam>() {
            @Override
            public void onResponse(Call<ResponseTeam> call, Response<ResponseTeam> response) {
                teamsProgressBar.setVisibility(View.GONE);

                ListView teamListView = root.findViewById(R.id.teams_list_view);
                ListTeamAdapter listTeamAdapter = new ListTeamAdapter(getContext(), response.body().getTeams());

                teamListView.setAdapter(listTeamAdapter);
            }

            @Override
            public void onFailure(Call<ResponseTeam> call, Throwable throwable) {
                teamsProgressBar.setVisibility(View.GONE);

                Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }
}