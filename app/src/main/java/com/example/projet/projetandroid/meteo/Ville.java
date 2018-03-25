package com.example.projet.projetandroid.meteo;

/**
 * Created by etudiant on 13/02/2018.
 */

public class Ville {

    private String name;
    private String country;
    private Coord  coord;

    public Ville(String name) {
        this.name = name;
    }

    public Ville(String name, String country, Coord coord) {
        this.name = name;
        this.country = country;
        this.coord = coord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    @Override
    public String toString() {
        return "City{" + ", name='" + name + '\'' + ", country='" + country + '\'' + ", coord=" + coord + '}';
    }
}
