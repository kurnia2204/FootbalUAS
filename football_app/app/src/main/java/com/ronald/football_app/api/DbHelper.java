package com.ronald.football_app.api;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ronald.football_app.data.Team;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "football";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_FAV_TEAM = "favourite_teams";
    private static final String KEY_ID = "id";
    private static final String KEY_ID_TEAM = "id_team";
    private static final String KEY_NAME = "name";
    private static final String KEY_STADIUM_NAME = "stadiumName";
    private static final String KEY_TEAM_DESCRIPTION = "teamDescription";
    private static final String KEY_STADIUM_DESCRIPTION = "stadiumDescription";
    private static final String KEY_BADGE = "badge";
    private static final String KEY_FORMED_YEAR = "formedYear";

    private static final String CREATE_TABLE_FAV_TEAM = "CREATE TABLE "
            + TABLE_FAV_TEAM + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_ID_TEAM + " INTEGER,"
            + KEY_NAME + " TEXT,"
            + KEY_STADIUM_NAME + " TEXT,"
            + KEY_TEAM_DESCRIPTION + " TEXT,"
            + KEY_STADIUM_DESCRIPTION + " TEXT,"
            + KEY_BADGE + " TEXT,"
            + KEY_FORMED_YEAR + " TEXT );";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_FAV_TEAM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TABLE_FAV_TEAM + "'");
        onCreate(sqLiteDatabase);
    }

    public long addFavTeam(Team team) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_TEAM, team.getId());
        values.put(KEY_NAME, team.getName());
        values.put(KEY_STADIUM_NAME, team.getStadiumName());
        values.put(KEY_TEAM_DESCRIPTION, team.getTeamDescription());
        values.put(KEY_STADIUM_DESCRIPTION, team.getStadiumDescription());
        values.put(KEY_BADGE, team.getBadge());
        values.put(KEY_FORMED_YEAR, team.getFormedYear());
        long addFavTeam = sqLiteDatabase.insert(TABLE_FAV_TEAM, null, values);

        return addFavTeam;
    }

    public void deleteFavTeam(int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_FAV_TEAM, KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public List<Team> getAllFavTeam() {
        List<Team> teams = new ArrayList<Team>();

        String query = "SELECT * FROM " + TABLE_FAV_TEAM;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Team team = new Team();
                team.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID_TEAM)));
                team.setIdDb(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                team.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                team.setStadiumName(cursor.getString(cursor.getColumnIndex(KEY_STADIUM_NAME)));
                team.setTeamDescription(cursor.getString(cursor.getColumnIndex(KEY_TEAM_DESCRIPTION)));
                team.setStadiumDescription(cursor.getString(cursor.getColumnIndex(KEY_STADIUM_DESCRIPTION)));
                team.setBadge(cursor.getString(cursor.getColumnIndex(KEY_BADGE)));
                team.setFormedYear(cursor.getString(cursor.getColumnIndex(KEY_FORMED_YEAR)));

                teams.add(team);
            } while (cursor.moveToNext());
        }

        return teams;
    }

    public Boolean isFavourite(int idTeam) {
        String query = "SELECT * FROM " + TABLE_FAV_TEAM + " WHERE " + KEY_ID_TEAM + " = " + idTeam;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }

        cursor.close();
        return true;
    }
}
