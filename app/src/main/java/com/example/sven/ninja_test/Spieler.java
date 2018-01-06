package com.example.sven.ninja_test;

/**
 * Dient der Verwaltung aller Informationen zu einem Spieler
 * <p>
 * Created by Claudius on 08.06.2017.
 */

public class Spieler {
    private String name;
    private String highscore;

    /**
     * Erstellt ein Spieler Objekt mit den Informationen zu einem Spieler
     *
     * @param name      Der Name des Spielers
     * @param highscore Der Highscore des Spielers
     */

    public Spieler(String name, String highscore) {
        this.name = name;
        this.highscore = highscore;
    }

    /**
     *
     * @return Name des Spielers
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return Highscore des Spielers
     */
    public String getHighscore() {
        return highscore;
    }

}
