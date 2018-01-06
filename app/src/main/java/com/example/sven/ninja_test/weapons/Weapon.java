package com.example.sven.ninja_test.weapons;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.sven.ninja_test.MainActivity;

/**
 * Dient der Verwaltung aller Informationen zu einer Waffe
 * <p>
 * Created by Julian on 07.06.2017.
 */

public class Weapon {
    private String name;
    private int img_Wpn;
    private int img_Vlu;


    private int img_Icon;
    private int value;
    private int id;
    private boolean unlocked;

    /**
     * @param id       ID der Waffe
     * @param name     Name der Waffe
     * @param wpn      Bild der Waffe
     * @param vlu      Bild der Kosten einer Waffe
     * @param icon     Icon der Waffe
     * @param value    Wert zur Freischaltung der Waffe
     * @param unlocked Variable ob die Waffe freigeschaltet ist oder nicht
     */
    public Weapon(int id, String name, int wpn, int vlu, int icon, int value, boolean unlocked) {
        this.id = id;
        this.name = name;
        this.img_Wpn = wpn;
        this.img_Vlu = vlu;
        this.img_Icon = icon;
        this.value = value;
        this.unlocked = unlocked;
    }

    /**
     *
     * @return Name der Waffe
     */
    String getName() {
        return name;
    }

    /**
     *
     * @param name Name der Waffe
     */
    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        if (unlocked) {
            return img_Wpn;
        } else {
            return img_Vlu;
        }
    }

    /**
     *
     * @return Wert der Waffe
     */
    int getValue() {
        return value;
    }

    /**
     *
     * @return Icon der Waffe
     */
    public int getImg_Icon() {
        return img_Icon;
    }

    /**
     * Status der Waffe
     * @return Status der Waffe
     */
    boolean isUnlocked() {
        return unlocked;
    }

    /**
     *  Aendert den Status der Waffe
     */
    void unlock() {
        if (!unlocked) {
            unlocked = true;
            SharedPreferences preferences = MainActivity.getContextOfApplication().getSharedPreferences(MainActivity.PREFERENCE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(name, true).apply();
        } else {
            unlocked = false;
        }
    }
}
