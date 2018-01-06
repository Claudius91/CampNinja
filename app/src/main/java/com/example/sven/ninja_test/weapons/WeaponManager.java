package com.example.sven.ninja_test.weapons;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.sven.ninja_test.MainActivity;
import com.example.sven.ninja_test.R;

import java.util.ArrayList;

/**
 * Dient zur Hinzufügung einer Waffe
 * <p>
 * Created by Julian on 07.06.2017.
 */

public class WeaponManager {
    /**
     * Die {@link ArrayList} von {@link Weapon}
     */
    private static ArrayList<Weapon> weaponsArray = new ArrayList<>();

    static int id = 0;

    /**
     * Initialisiert alle Waffen
     */
    public static void populateWeaponHashMap() {
        SharedPreferences preferences = MainActivity.getContextOfApplication().getSharedPreferences(MainActivity.PREFERENCE, Context.MODE_PRIVATE);

        weaponsArray.clear();
        weaponsArray.add(new Weapon(id++, "Shuriken", R.drawable.wurfstern, R.drawable.zehn, R.drawable.stern_icon, 0, true));
        weaponsArray.add(new Weapon(id++, "Bernd", R.drawable.bernd1, R.drawable.zehn, R.drawable.bernd_icon, 10, preferences.getBoolean("Bernd", false)));
        weaponsArray.add(new Weapon(id++, "Banana", R.drawable.banana, R.drawable.zwanzig, R.drawable.banana_icon, 20, preferences.getBoolean("Banana", false)));
        weaponsArray.add(new Weapon(id++, "Paper", R.drawable.paper, R.drawable.dreisig, R.drawable.paper_icon, 30, preferences.getBoolean("Paper", false)));
        weaponsArray.add(new Weapon(id++, "Pizza", R.drawable.pizza, R.drawable.vierzig, R.drawable.pizza_icon, 40, preferences.getBoolean("Pizza", false)));
        weaponsArray.add(new Weapon(id++, "Shoe", R.drawable.shoe, R.drawable.fuenfzig, R.drawable.shoe_icon, 50, preferences.getBoolean("Shoe", false)));
        weaponsArray.add(new Weapon(id++, "Card", R.drawable.card, R.drawable.sechzig, R.drawable.card_icon, 60, preferences.getBoolean("Card", false)));
        weaponsArray.add(new Weapon(id++, "Car", R.drawable.car, R.drawable.siebzig, R.drawable.car_icon, 70, preferences.getBoolean("Car", false)));
        weaponsArray.add(new Weapon(id++, "Earth", R.drawable.erde, R.drawable.achtzig, R.drawable.erde_icon, 80, preferences.getBoolean("Earth", false)));
        weaponsArray.add(new Weapon(id++, "Frog", R.drawable.frosch, R.drawable.neunzig, R.drawable.frosch_icon, 90, preferences.getBoolean("Frog", false)));
        weaponsArray.add(new Weapon(id++, "Chloe", R.drawable.chloe_wurf, R.drawable.hundert, R.drawable.chloe_icon, 100, preferences.getBoolean("Chloe", false)));
        weaponsArray.add(new Weapon(id++, "Fidget", R.drawable.fidget, R.drawable.hundertzehn, R.drawable.fidget_icon, 110, preferences.getBoolean("Fidget", false)));
    }

    /**
     *
     * @param id Die ID im Array des WeaponManagers
     * @return Eine Waffe
     */
    public static Weapon getWeapon(int id) {
        return weaponsArray.get(id);
    }

    /**
     *
     * @return Alle Waffen
     */
    public static ArrayList<Weapon> getWeapons() {
        return weaponsArray;
    }
}
