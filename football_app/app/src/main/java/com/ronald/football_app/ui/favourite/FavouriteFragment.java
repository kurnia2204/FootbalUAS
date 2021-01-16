package com.ronald.football_app.ui.favourite;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ronald.football_app.R;
import com.ronald.football_app.api.DbHelper;
import com.ronald.football_app.data.ListTeamAdapter;
import com.ronald.football_app.data.Team;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment {

    private List<Team> teamList = new ArrayList();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favourite, container, false);

        DbHelper dbHelper = new DbHelper(root.getContext());
        List<Team> teams = dbHelper.getAllFavTeam();

        if (teams.size() > 0) {
            this.teamList = teams;
            ListView teamListView = root.findViewById(R.id.teams_list_view);
            ListTeamAdapter listTeamAdapter = new ListTeamAdapter(getContext(), teams);

            teamListView.setAdapter(listTeamAdapter);
            teamListView.setLongClickable(true);
            teamListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {
                    showAddDeleteDialog(pos);
                    return true;
                }
            });
        } else {
            Toast.makeText(getContext(), "You have no Favourite Team", Toast.LENGTH_LONG).show();
        }

        return root;
    }

    public void showAddDeleteDialog(int pos){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setCancelable(true);
        dialog.setTitle("Delete");
        dialog.setMessage("Are you sure you want to delete this entry from favourite?" );
        dialog
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        DbHelper dbHelper = new DbHelper(getContext());
                        dbHelper.deleteFavTeam(teamList.get(pos).getIdDb());

                        AppCompatActivity activity = (AppCompatActivity) getContext();

                        activity.getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.nav_host_fragment, new FavouriteFragment())
                                .addToBackStack(null)
                                .commit();

                        Toast.makeText(getContext(), "Team deleted from favourite", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
        ;

        final AlertDialog alert = dialog.create();
        alert.show();
    }
}