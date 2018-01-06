package com.example.sven.ninja_test;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Dient zur Veranschaulichung der Punkte und dem Hochladen in die Datenbank
 * <p>
 * Created by Sven on 25.04.2017.
 */

public class GameOverActivity extends AppCompatActivity {
    private TextView runden;
    private ImageView neustart;
    private MediaPlayer mPlayerGameOver;
    private TextView score;
    private TextView muenzen;
    public static int highscore;
    private String leistung;
    SharedPreferences preferences;
    private boolean background;
    private static int coins;
    int currentPoints = GameActivity.getCounter();
    private String zahl;
    public String name;
    final Context context = this;
    private Button statistik;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_over);

        score = (TextView) findViewById(R.id.score);
        runden = (TextView) findViewById(R.id.runden);
        muenzen = (TextView) findViewById(R.id.muenzen);
        runden.setText("Rounds: " + GameActivity.getCounter());
        neustart = (ImageView) findViewById(R.id.neustart);
        statistik = (Button) findViewById(R.id.statistik);


        preferences = getSharedPreferences(MainActivity.PREFERENCE, GameOverActivity.MODE_PRIVATE);
        highscore = preferences.getInt("score", 0);
        background = preferences.getBoolean("background", true);
        leistung = Integer.toString(highscore);
        if (currentPoints > highscore) {
            score.setText("Highscore: " + currentPoints);
        } else {
            score.setText("Highscore: " + leistung);
        }

        coins = preferences.getInt("coins", 0);
        coins += currentPoints;
        muenzen.setText("Coins : " + coins);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("coins", coins);
        editor.commit();


        mPlayerGameOver = MediaPlayer.create(this, R.raw.gameover);
        mPlayerGameOver.setLooping(true);
        mPlayerGameOver.start();
        if (!background) {
            mPlayerGameOver.reset();
        }

        //Leitet an {@link Statistik} weiter
        statistik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Statistik.class);
                startActivity(intent);

            }
        });



        //Erneutes Spielen durch Weiterleitung an {@link GameActivity}

        neustart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                speichern();
                GameActivity.counterReset();
                mPlayerGameOver.stop();
                neustart.setEnabled(false);
                Intent intent = new Intent(getBaseContext(), GameActivity.class);
                finish();
                startActivity(intent);

            }
        });
        zahl = Integer.toString(currentPoints);


         // Wird der Highscore geknackt, wird ein AlertDialog ausgelöst.
         // Die Zeichenkette, die in den Edittext eingetragen wird, wird nach Punktkontrolle in die Datenbank gespeichert

        if (highscore < currentPoints) {

            editor.putInt("score", currentPoints);
            score.setText("Highscore: " + currentPoints);
            editor.commit();

            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.YourAlertDialogTheme);
            builder.setTitle("Player name:");

            final EditText input = new EditText(this);

            input.setTextColor(getResources().getColor(R.color.schwarz));
            input.getBackground().setColorFilter(getResources().getColor(R.color.hell_braun), PorterDuff.Mode.SRC_ATOP);
            input.setLinkTextColor(getResources().getColor(R.color.schwarz));

            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    name = input.getText().toString();

                    SharedPreferences.Editor editor = preferences.edit();


                    editor.putString("benutzername", name);
                    editor.apply();
                    insertdb();

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            TextView title = new TextView(context);
            title.setText("Player name :");
            title.setPadding(30, 0, 0, 0);
            title.setTextSize(28);
            title.setBackgroundColor(Color.TRANSPARENT);
            title.setTextColor(Color.BLACK);
            builder.setCustomTitle(title);

            builder.show();
        }


    }

    //Verbindung zum Server und speichert die Daten in die Datenbank mithilfe von {@link DBActivity}

    private void insertdb() {
        String type = "register";
        DBActivity activity = new DBActivity(this);
        activity.execute(type, zahl, name);
    }


    @Override
    public void onPause() {
        super.onPause();
        mPlayerGameOver.pause();
        speichern();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (highscore < currentPoints) {
            savedInstanceState.putInt("score", currentPoints);
        } else {
            savedInstanceState.putInt("score", highscore);
        }


        super.onSaveInstanceState(savedInstanceState);
    }

    //Speichert den aktuellen Highscore {@link GameOverActivity#currentPoints}

    private void speichern() {

        SharedPreferences.Editor editor = preferences.edit();

        if (highscore < currentPoints) {
            editor.putInt("score", currentPoints);
            editor.commit();

        } else {
            editor.putInt("score", highscore);
            editor.commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPlayerGameOver.start();
        highscore = preferences.getInt("score", 0);
        leistung = Integer.toString(highscore);
        score.setText("Highscore: " + leistung);
    }


    @Override
    public void onBackPressed() {
        speichern();
        GameActivity.counterReset();
        mPlayerGameOver.stop();
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }


}
