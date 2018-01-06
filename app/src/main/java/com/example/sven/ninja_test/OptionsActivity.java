package com.example.sven.ninja_test;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Dient zur Einstellung von Musik, zur Einsicht in die Statistik und das Zurücksetzen aller Daten.
 * <p>
 * Created by Sven on 25.04.2017.
 */

public class OptionsActivity extends AppCompatActivity {
    private ImageView tafel;
    private ImageView schrift;
    private boolean rauch;
    private Switch smoke;
    private Switch sounds;
    private Switch backgroundmusic;
    private boolean sound;
    private boolean hintergrundmusik;
    private Button delete;
    final Context context = this;
    AlertDialog dialog;
    SharedPreferences preferences;
    public int highscore;
    private Button statistik;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        preferences = getSharedPreferences(MainActivity.PREFERENCE, GameOverActivity.MODE_PRIVATE);
        highscore = preferences.getInt("score", 0);

        smoke = (Switch) findViewById(R.id.switch1);
        sounds = (Switch) findViewById(R.id.switch2);
        backgroundmusic = (Switch) findViewById(R.id.switch3);
        tafel = (ImageView) findViewById(R.id.tafellink);
        schrift = (ImageView) findViewById(R.id.howto);
        delete = (Button) findViewById(R.id.delete);

        schrift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), HelpActivity.class);
                startActivity(intent);

            }
        });

        //Startet Spielbeschreibung

        tafel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), HelpActivity.class);
                startActivity(intent);

            }
        });

        final SharedPreferences.Editor editor = preferences.edit();


        rauch = preferences.getBoolean("smoke", false);
        smoke.setChecked(false);

        //Schaltet Rauch ein/aus
/*
        smoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (smoke.isChecked()) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("smoke", true);
                    editor.commit();
                } else {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("smoke", false);
                    editor.commit();
                }
            }
        });
*/
        sound = preferences.getBoolean("sounds", true);

        sounds.setChecked(sound);

        //Schaltet Soundeffekte ein/aus

        sounds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sounds.isChecked()) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("sounds", true);
                    editor.commit();
                } else {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("sounds", false);
                    editor.commit();
                }
            }
        });


        hintergrundmusik = preferences.getBoolean("background", true);
        backgroundmusic.setChecked(hintergrundmusik);

        //Schaltet Hintergrundmusik ein/aus

        backgroundmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (backgroundmusic.isChecked()) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("background", true);
                    editor.commit();
                } else {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("background", false);
                    editor.commit();
                }
            }
        });

        //Löscht alle Daten

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OptionsActivity.this);
                builder.setTitle("Warning !");
                builder.setMessage("Do you really want to delete everything?");
                builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(OptionsActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        editor.putInt("score", 0);
                        editor.putInt("waffe", 0);
                        editor.putInt("coins", 0);
                        editor.putBoolean("Bernd", false);
                        editor.putBoolean("Banana", false);
                        editor.putBoolean("Paper", false);
                        editor.putBoolean("Shoe", false);
                        editor.putBoolean("Chloe", false);
                        editor.putBoolean("Pizza", false);
                        editor.putBoolean("Card", false);
                        editor.putBoolean("Car", false);
                        editor.putBoolean("Frog", false);
                        editor.putBoolean("Earth", false);
                        editor.putBoolean("Fidget", false);
                        editor.commit();

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                dialog = builder.create();

                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#7F4B05"));
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#7F4B05"));
                    }
                });
//                Ändert Titelgröße und Farbe
                TextView title = new TextView(context);
                title.setText("Warning !");
                title.setGravity(Gravity.CENTER);
                title.setTextSize(30);
                title.setBackgroundColor(Color.TRANSPARENT);
                title.setTextColor(Color.BLACK);
                dialog.setCustomTitle(title);
                dialog.show();
//                Ändert die Message Schriftgröße
                TextView textView = (TextView) dialog.findViewById(android.R.id.message);
                textView.setTextSize(18);
            }
        });

        statistik = (Button) findViewById(R.id.statistik);

        //Leitet an {@link Statistik} weiter

        statistik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Statistik.class);
                startActivity(intent);

            }
        });




    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }
}
