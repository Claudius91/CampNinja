package com.example.sven.ninja_test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.sven.ninja_test.weapons.MyGridAdapter;
import com.example.sven.ninja_test.weapons.Weapon;
import com.example.sven.ninja_test.weapons.WeaponManager;

import java.util.ArrayList;

/**
 * Erstellt die ArrayList der {@link WeaponManager} mithilfe der Klasse {@link Weapon} die man durch Coins freischalten kann
 *
 * Created by Julian on 09.06.2017.
 */

public class WeaponActivity extends AppCompatActivity {

    TextView coins;
    private MediaPlayer mPlayerWeapon;
    SharedPreferences preferences;
    private boolean background;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_weapons);
        ArrayList<Weapon> wpnItems = WeaponManager.getWeapons();
        GridView grid = (GridView) findViewById(R.id.grid_weapons);

        mPlayerWeapon = MediaPlayer.create(this, R.raw.weapon_room);
        mPlayerWeapon.setLooping(true);
        mPlayerWeapon.start();

        preferences = getSharedPreferences(MainActivity.PREFERENCE, GameOverActivity.MODE_PRIVATE);
        background =  preferences.getBoolean("background", true);
        if(!background) {mPlayerWeapon.reset();}

        MyGridAdapter gridAdapter = new MyGridAdapter(this, R.layout.weapon_item_activity, wpnItems);
        grid.setAdapter(gridAdapter);
        grid.setOnItemClickListener(gridAdapter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mPlayerWeapon.pause();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPlayerWeapon.start();
        SharedPreferences preferences = getSharedPreferences(MainActivity.PREFERENCE, GameOverActivity.MODE_PRIVATE);

        coins = (TextView) findViewById(R.id.coins);

        String coinValue = "Coins " + String.valueOf(preferences.getInt("coins",0));

        coins.setText(coinValue);
    }

    @Override
    public void onBackPressed() {
        GameActivity.counterReset();
        mPlayerWeapon.stop();
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        finish();
        startActivity(intent);
    }

    /**
     * Aktualisiert die Muenzen
     *
     * @param update Die aktualisierten Muenzen
     */
    public void update(String update){
        String coinValue = "Coins " + update;
        coins.setText(coinValue);
    }



}
