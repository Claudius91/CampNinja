package com.example.sven.ninja_test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Dient zur Erklärung des Spiels
 */
public class HelpActivity extends AppCompatActivity {
    private Button tutorial;
    private MediaPlayer mPlayerTut;
    private MediaPlayer mPlayerPage;
    private ImageView howto1;
    private ImageView howto2;
    private ImageView howto3;
    private ImageView howto4;
    private ImageView howto5;
    private int x = 1;
    private boolean background;
    private boolean sound;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);

        preferences = getSharedPreferences(MainActivity.PREFERENCE, GameOverActivity.MODE_PRIVATE);
        background = preferences.getBoolean("background", true);
        sound = preferences.getBoolean("sounds", true);
        tutorial = (Button) findViewById(R.id.tutorial);
        howto1 = (ImageView) findViewById(R.id.howto1);
        howto2 = (ImageView) findViewById(R.id.howto2);
        howto3 = (ImageView) findViewById(R.id.howto3);
        howto4 = (ImageView) findViewById(R.id.howto4);
        howto5 = (ImageView) findViewById(R.id.howto5);
        tutorial.setVisibility(View.VISIBLE);
        tutorial.setBackgroundColor(Color.TRANSPARENT);

        mPlayerTut = MediaPlayer.create(this, R.raw.tut);
        mPlayerTut.setLooping(true);
        mPlayerTut.start();

        mPlayerPage = MediaPlayer.create(this, R.raw.page);
        mPlayerPage.setLooping(false);


        if (!sound) mPlayerPage.reset();
        if (!background) mPlayerTut.reset();


        tutorial.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (x) {
                    case 0:
                        howto5.setVisibility(View.INVISIBLE);
                        howto1.setVisibility(android.view.View.VISIBLE);
                        mPlayerPage.start();
                        x++;
                        break;

                    case 1:
                        howto1.setVisibility(View.INVISIBLE);
                        howto2.setVisibility(android.view.View.VISIBLE);
                        mPlayerPage.start();
                        x++;
                        break;
                    case 2:
                        howto2.setVisibility(View.INVISIBLE);
                        howto3.setVisibility(android.view.View.VISIBLE);
                        mPlayerPage.start();
                        x++;
                        break;
                    case 3:
                        howto3.setVisibility(View.INVISIBLE);
                        howto4.setVisibility(android.view.View.VISIBLE);
                        mPlayerPage.start();
                        x++;
                        break;
                    case 4:
                        howto4.setVisibility(View.INVISIBLE);
                        howto5.setVisibility(android.view.View.VISIBLE);
                        mPlayerPage.start();
                        x = 0;
                        break;
                }


            }
        });


    }

    @Override
    public void onBackPressed() {
        mPlayerTut.stop();
        mPlayerPage.stop();
        Intent intent = new Intent(getBaseContext(), OptionsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPlayerTut.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPlayerTut.pause();
    }
}
