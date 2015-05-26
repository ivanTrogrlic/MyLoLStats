package com.example.ivan.entitet;

import android.os.Parcel;
import android.os.Parcelable;

public class GameStatistics implements CalculateKda, Parcelable {

    private int id, kills, assists, deaths;
    private String championName, gameOutcome;
    private double kda;

    public GameStatistics() {
    }

    public GameStatistics(String championName, String gameOutcome, int kills, int deaths, int assists) {
        this.championName = championName;
        this.assists = assists;
        this.gameOutcome = gameOutcome;
        this.deaths = deaths;
        this.kills = kills;

        this.kda = calculateKda(kills, deaths, assists);
    }

    public GameStatistics(int id, String championName, String gameOutcome, int kills, int deaths, int assists) {
        this.championName = championName;
        this.gameOutcome = gameOutcome;
        this.kills = kills;
        this.assists = assists;
        this.deaths = deaths;
        this.id = id;

        this.kda = calculateKda(kills, deaths, assists);
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public String getChampionName() {
        return championName;
    }

    public void setChampionName(String championName) {
        this.championName = championName;
    }

    public String getGameOutcome() {
        return gameOutcome;
    }

    public void setGameOutcome(String gameOutcome) {
        this.gameOutcome = gameOutcome;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public double getKda() {
        return kda;
    }

    public void setKda(double kda) {
        this.kda = kda;
    }

    @Override
    public String toString() {
        return "GameStatistics{" +
                "assists=" + assists +
                ", id=" + id +
                ", kills=" + kills +
                ", deaths=" + deaths +
                ", championName='" + championName + '\'' +
                ", gameOutcome='" + gameOutcome + '\'' +
                ", kda=" + kda +
                '}';
    }

    @Override
    public float calculateKda(int kills, int deaths, int assists) {
        if (deaths > 0) {
            return ((float)kills + (float)assists)/(float)deaths;
        }else{
            return ((float)kills + (float)assists);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(championName);
        dest.writeString(gameOutcome);
        dest.writeInt(kills);
        dest.writeInt(assists);
        dest.writeInt(deaths);
    }

    private GameStatistics(Parcel in){
        this.id = in.readInt();
        this.championName = in.readString();
        this.gameOutcome = in.readString();
        this.kills = in.readInt();
        this.assists = in.readInt();
        this.deaths = in.readInt();
    }

    public static final Parcelable.Creator<GameStatistics> CREATOR = new Parcelable.Creator<GameStatistics>() {

        @Override
        public GameStatistics createFromParcel(Parcel source) {
            return new GameStatistics(source);
        }

        @Override
        public GameStatistics[] newArray(int size) {
            return new GameStatistics[size];
        }
    };
}
