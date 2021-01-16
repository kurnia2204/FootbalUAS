package com.ronald.football_app.data;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ronald.football_app.R;
import com.ronald.football_app.ui.teams.TeamFragment;

import java.util.List;

public class ListTeamAdapter extends BaseAdapter {
    private final List<Team> teams;
    private final Context context;

    public ListTeamAdapter(Context context, List<Team> teams){
        this.context = context;
        this.teams = teams;
    }

    @Override
    public int getCount() {
        return this.teams.size();
    }

    @Override
    public Object getItem(int i) {
        return this.teams.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.model_team,viewGroup,false);
        }

        ImageView imgBadge = view.findViewById(R.id.img_badge);
        TextView txtTeamName = view.findViewById(R.id.tv_team_name);

        final Team team = teams.get(i);

        txtTeamName.setText(team.getName());
        Glide.with(view.getContext())
                .load(team.getBadge())
                .apply(new RequestOptions().override(100, 100))
                .into(imgBadge);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();

                bundle.putInt("ID", team.getId());

                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                Fragment teamFragment = TeamFragment.teamFragmentInstance(bundle);

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, teamFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}
