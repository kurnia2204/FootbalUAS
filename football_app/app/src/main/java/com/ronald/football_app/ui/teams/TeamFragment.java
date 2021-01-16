package com.ronald.football_app.ui.teams;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ronald.football_app.MainActivity;
import com.ronald.football_app.NotificationUtils;
import com.ronald.football_app.R;
import com.ronald.football_app.api.APIInstance;
import com.ronald.football_app.api.APIService;
import com.ronald.football_app.api.DbHelper;
import com.ronald.football_app.data.ResponseTeam;
import com.ronald.football_app.data.Team;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamFragment extends Fragment {

    private Boolean isFavourite;

    public static final int NOTIF_ID = 1;
    PendingIntent pendingIntent;

    public static Fragment teamFragmentInstance(Bundle bundle) {
        TeamFragment teamFragment = new TeamFragment();
        teamFragment.setArguments(bundle);

        return teamFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_team, container, false);
        APIService apiService = APIInstance.getRetrofitInstance().create(APIService.class);

        Integer id = getArguments().getInt("ID");
        Call<ResponseTeam> call = apiService.getTeam(id);

        call.enqueue(new Callback<ResponseTeam>() {
            @Override
            public void onResponse(Call<ResponseTeam> call, Response<ResponseTeam> response) {
                List<Team> team = response.body().getTeams();
                Team teamSingle = team.get(0);

                ImageView imgBadge = root.findViewById(R.id.img_badge);
                TextView txtTeamName = root.findViewById(R.id.tv_team_name);
                TextView txtTeamFormedYear = root.findViewById(R.id.tv_team_formed_year);
                TextView txtTeamDescription = root.findViewById(R.id.tv_team_description);
                TextView txtStadiumName = root.findViewById(R.id.tv_stadium_name);
                TextView txtStadiumDescription = root.findViewById(R.id.tv_stadium_description);
                ImageButton btnFavourite = root.findViewById(R.id.btn_favourite);

                txtTeamName.setText(teamSingle.getName());
                txtTeamFormedYear.setText(teamSingle.getFormedYear());
                txtTeamDescription.setText(teamSingle.getTeamDescription());
                txtStadiumName.setText(teamSingle.getStadiumName());
                txtStadiumDescription.setText(teamSingle.getStadiumDescription());

                DbHelper dbHelper = new DbHelper(root.getContext());
                isFavourite = dbHelper.isFavourite(teamSingle.getId());

                Glide.with(root.getContext())
                        .load(teamSingle.getBadge())
                        .apply(new RequestOptions().override(240, 240))
                        .into(imgBadge);

                btnFavourite.setSelected(isFavourite);
                btnFavourite.setOnClickListener(v -> {
                     if (! isFavourite) {
                         dbHelper.addFavTeam(teamSingle);

                         isFavourite = true;
                         btnFavourite.setSelected(isFavourite);

                         Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                         intent.putExtra("preloadFragment", "FavouriteFragment");
                         pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                             showNotifOreo(teamSingle);
                         } else {
                             showNotifDefault(teamSingle);
                         }
                     }
                });
            }

            @Override
            public void onFailure(Call<ResponseTeam> call, Throwable throwable) {
                Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }

    public void showNotifDefault(Team team){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                .setSmallIcon(R.drawable.ic_star_on)
                .setContentIntent(pendingIntent)
                .setContentTitle("Favourite Team")
                .setContentText(team.getName() + " successfully added to the Favourite List")
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
        notificationManager.notify(NOTIF_ID, builder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showNotifOreo(Team team){
        Notification.Builder notifBuilder = new Notification.Builder(getActivity(), NotificationUtils.ANDROID_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_star_on)
                .setContentIntent(pendingIntent)
                .setContentTitle("Favourite Team")
                .setContentText(team.getName() + " successfully added to the Favourite List")
                .setAutoCancel(true);

        NotificationUtils utils = new NotificationUtils(getActivity());
        utils.getManager().notify(NOTIF_ID, notifBuilder.build());
    }
}
