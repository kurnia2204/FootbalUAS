package com.ronald.football_app.ui.schedule;

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
import com.ronald.football_app.data.ListScheduleAdapter;
import com.ronald.football_app.data.ResponseSchedule;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_schedule, container, false);
        final ProgressBar scheduleProgressBar = root.findViewById(R.id.schedule_progress_bar);

        scheduleProgressBar.setIndeterminate(true);
        scheduleProgressBar.setVisibility(View.VISIBLE);

        APIService apiService = APIInstance.getRetrofitInstance().create(APIService.class);

        Call<ResponseSchedule> call = apiService.getSchedules();

        call.enqueue(new Callback<ResponseSchedule>() {
            @Override
            public void onResponse(Call<ResponseSchedule> call, Response<ResponseSchedule> response) {
                scheduleProgressBar.setVisibility(View.GONE);

                ListView scheduleListView = root.findViewById(R.id.schedule_list_view);
                ListScheduleAdapter listScheduleAdapter = new ListScheduleAdapter(getContext(), response.body().getSchedules());

                scheduleListView.setAdapter(listScheduleAdapter);
            }

            @Override
            public void onFailure(Call<ResponseSchedule> call, Throwable throwable) {
                scheduleProgressBar.setVisibility(View.GONE);

                Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }
}