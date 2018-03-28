package com.example.projet.projetandroid.meteo;

import java.util.ArrayList;

/**
 * Created by Latour on 20/03/2017.
 */

public class Base {
    private String stations;
    private int visibility;
    private int id;
    private String name;
    private int cod;

    private Coord coord;
    private ArrayList<Weather> weather;
    private Main main;
    private Wind wind;
    private Clouds clouds;
    private Sys sys;

    public Base(){
        this.weather = new ArrayList<>();
    }



    public Base(String stations, int visibility, int id, String name, int cod, Coord coord, Weather weather, Main main, Wind wind, Clouds clouds, Sys sys) {
        this.stations = stations;
        this.visibility = visibility;
        this.id = id;
        this.name = name;
        this.cod = cod;
        this.coord = coord;
        this.weather = new ArrayList<>();
        this.main = main;
        this.wind = wind;

        this.clouds = clouds;
        this.sys = sys;
    }

    public String getStations() {
        return stations;
    }

    public void setStations(String stations) {
        this.stations = stations;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public void setWeather(ArrayList<Weather> weather) {
        this.weather = weather;
    }
}
