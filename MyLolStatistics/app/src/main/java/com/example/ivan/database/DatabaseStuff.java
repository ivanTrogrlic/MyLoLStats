package com.example.ivan.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ivan.entitet.GameStatistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class DatabaseStuff extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "GameStatisticsDB";

    public DatabaseStuff(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_BOOK_TABLE = "CREATE TABLE statistics ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "champion TEXT, "+
                "gameOutcome TEXT, " +
                "kills INTEGER, " +
                "deaths INTEGER, " +
                "assists INTEGER, " +
                "kda DECIMAL(2,2))";

        db.execSQL(CREATE_BOOK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS statistics");

        this.onCreate(db);
    }

    private static final String TABLE_STATISTICS = "statistics";

    private static final String KEY_ID = "id";
    private static final String KEY_CHAMPION = "champion";
    private static final String KEY_GAMEOUTCOME = "gameOutcome";
    private static final String KEY_KILLS = "kills";
    private static final String KEY_DEATHS = "deaths";
    private static final String KEY_ASSISTS = "assists";
    private static final String KEY_KDA = "kda";

    private static final String[] COLUMNS = {KEY_ID,KEY_CHAMPION, KEY_GAMEOUTCOME, KEY_KILLS, KEY_DEATHS, KEY_ASSISTS, KEY_KDA};

    public void addGame(GameStatistics game){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CHAMPION, game.getChampionName());
        values.put(KEY_GAMEOUTCOME, game.getGameOutcome());
        values.put(KEY_KILLS, game.getKills());
        values.put(KEY_DEATHS, game.getDeaths());
        values.put(KEY_ASSISTS, game.getAssists());
        values.put(KEY_KDA, game.getKda());

        db.insert(TABLE_STATISTICS,
                null,
                values);

        db.close();
    }

    public List<GameStatistics> getAllStatistics() {
        List<GameStatistics> statistics = new ArrayList<>();

        String query = "SELECT  * FROM " + TABLE_STATISTICS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        GameStatistics game;
        if (cursor.moveToFirst()) {
            do {
                game = new GameStatistics();
                game.setId(Integer.parseInt(cursor.getString(0)));
                game.setChampionName(cursor.getString(1));
                game.setGameOutcome(cursor.getString(2));
                game.setKills(Integer.parseInt(cursor.getString(3)));
                game.setDeaths(Integer.parseInt(cursor.getString(4)));
                game.setAssists(Integer.parseInt(cursor.getString(5)));
                game.setKda(Double.parseDouble(cursor.getString(6)));

                statistics.add(game);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return statistics;
    }

    public void deleteGame(String id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_STATISTICS,
                KEY_ID + " = ?",
                new String[]{id});

        db.close();

    }

    public int updateGame(GameStatistics game) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CHAMPION, game.getChampionName());
        values.put(KEY_GAMEOUTCOME, game.getGameOutcome());
        values.put(KEY_KILLS, game.getKills());
        values.put(KEY_DEATHS, game.getDeaths());
        values.put(KEY_ASSISTS, game.getAssists());
        values.put(KEY_KDA, game.getKda());

        int i = db.update(TABLE_STATISTICS,
                values,
                KEY_ID+" = ?",
                new String[] { String.valueOf(game.getId()) });

        db.close();

        return i;

    }

    public GameStatistics getGame(String id){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_STATISTICS,
                        COLUMNS,
                        " id = ?",
                        new String[]{id},
                        null,
                        null,
                        null,
                        null);

        GameStatistics game = new GameStatistics();

        if (cursor != null) {
            cursor.moveToFirst();

            game.setId(Integer.parseInt(id));
            game.setChampionName(cursor.getString(1));
            game.setGameOutcome(cursor.getString(2));
            game.setKills(Integer.parseInt(cursor.getString(3)));
            game.setDeaths(Integer.parseInt(cursor.getString(4)));
            game.setAssists(Integer.parseInt(cursor.getString(5)));
            game.setKda(Double.parseDouble(cursor.getString(6)));

            cursor.close();
        }

        db.close();

        return game;
    }

    public void deleteEverything() {

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from " + TABLE_STATISTICS);

        db.close();

    }

    public List<Double> checkKda(String champion) {
        List<Double> kda = new ArrayList<>();

        String query = "SELECT  * FROM " + TABLE_STATISTICS + " WHERE champion=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{champion});

        if (cursor.moveToFirst()) {
            do {

                kda.add(Double.parseDouble(cursor.getString(6)));

            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return kda;
    }

    public double checkWinRate(String champion) {
        double win = 0;
        double games = 0;
        double winRate;

        String query = "SELECT  * FROM " + TABLE_STATISTICS + " WHERE champion=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{champion});

        if (cursor.moveToFirst()) {
            do {

                if(cursor.getString(2).equals("Win")){
                    win++;
                    games++;
                }
                else{
                    games++;
                }

            } while (cursor.moveToNext());
        }

        winRate = ((win/games)*100);


        db.close();
        cursor.close();

        return winRate;
    }

    public Map<String, Double> bestKda() {
        Map<String, Double> kda = new TreeMap<>();

        String query = "SELECT  * FROM " + TABLE_STATISTICS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                if(kda.containsKey(cursor.getString(1))){
                    Double n = (kda.get(cursor.getString(1))+cursor.getDouble(6))/2;
                    kda.put(cursor.getString(1), n);
                }else{
                    kda.put(cursor.getString(1), Double.valueOf(cursor.getString(6)));
                }

            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return kda;
    }

    public Map<String, Double> bestWinRate() {
        Map<String, Double> winRate = new TreeMap<>();

        String query = "SELECT  * FROM " + TABLE_STATISTICS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                if(winRate.containsKey(cursor.getString(1))){
                    Double n = (winRate.get(cursor.getString(1))+1);
                    winRate.put(cursor.getString(1), n);

                }else{
                    winRate.put(cursor.getString(1), 0.0);
                }

            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return winRate;
    }

    public List<GameStatistics> getSpecificChampionStatistics(String champion) {
        List<GameStatistics> statistics = new ArrayList<>();

        String query = "SELECT  * FROM " + TABLE_STATISTICS + " WHERE champion=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{champion});

        GameStatistics game;
        if (cursor.moveToFirst()) {
            do {
                game = new GameStatistics();
                game.setId(Integer.parseInt(cursor.getString(0)));
                game.setChampionName(cursor.getString(1));
                game.setGameOutcome(cursor.getString(2));
                game.setKills(Integer.parseInt(cursor.getString(3)));
                game.setDeaths(Integer.parseInt(cursor.getString(4)));
                game.setAssists(Integer.parseInt(cursor.getString(5)));
                game.setKda(Double.parseDouble(cursor.getString(6)));

                statistics.add(game);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return statistics;
    }

    public boolean ifChampionExists(String champion) {
        boolean exists = false;

        String query = "SELECT  * FROM " + TABLE_STATISTICS + " WHERE champion=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{champion});

        if (cursor.moveToFirst()) {
            do {
                if(cursor.getString(1).equals(champion)){
                    exists = true;
                    break;
                }
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return exists;
    }

}