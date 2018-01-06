package com.example.sven.ninja_test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * Startet den Download mithilfe des {@link Downloader} für die Daten der Statistik
 */

public class Statistik extends AppCompatActivity {

    String url = "http://claudiuskockelmann.de/statistik.php";
    private MediaPlayer mPlayerStatistic;
    private boolean background;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistik);
        preferences = getSharedPreferences(MainActivity.PREFERENCE, GameOverActivity.MODE_PRIVATE);
        mPlayerStatistic = MediaPlayer.create(this, R.raw.statistic);
        mPlayerStatistic.setLooping(true);
        mPlayerStatistic.start();
        background = preferences.getBoolean("background", true);
        if (!background) {
            mPlayerStatistic.reset();
        }
        final ListView lv = (ListView) findViewById(R.id.lv);
        final Downloader d = new Downloader(this, url, lv);
        d.execute();

    }

    @Override
    public void onPause() {
        super.onPause();
        mPlayerStatistic.pause();
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPlayerStatistic.start();
    }
}
