package com.example.sven.ninja_test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.sven.ninja_test.weapons.WeaponManager;

/**
 * Dies ist der Startbildschirm. Diese Klasse dient als Zentrale, von der die Aktivities {@link GameActivity},
 * {@link OptionsActivity} und {@link WeaponActivity} erreichbar sind.
 * <p>
 * Created by Sven on 25.04.2017.
 */

public class MainActivity extends AppCompatActivity {

    public static Context applicationContext;
    public final static String PREFERENCE = "Lokal Data";

    private MediaPlayer mPlayerTitel;
    private ImageView waffe;
    private ImageView titelbild;
    private ImageView option;
    private ImageView start_button;
    private boolean background;
    SharedPreferences preferences;
    private int waffenauswahl;
    private ImageView panda;
    private Animation zoom;
    private Animation zoomOut;
    private Animation titelZoom;
    private Animation titelZoomOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        applicationContext = getApplicationContext();

        setContentView(R.layout.activity_main);
        waffe = (ImageView) findViewById(R.id.waffe);
        option = (ImageView) findViewById(R.id.option);
        panda = (ImageView) findViewById(R.id.panda);
        titelbild = (ImageView) findViewById(R.id.titelbild);
        zoom = AnimationUtils.loadAnimation(this, R.anim.panda_zoom_in);
        zoomOut = AnimationUtils.loadAnimation(this, R.anim.panda_zoom_out);
        titelZoom = AnimationUtils.loadAnimation(this, R.anim.titel_zoom_in);
        titelZoomOut = AnimationUtils.loadAnimation(this, R.anim.titel_zoom_out);

        preferences = getSharedPreferences(PREFERENCE, GameOverActivity.MODE_PRIVATE);

        WeaponManager.populateWeaponHashMap();

        titelbild.startAnimation(titelZoom);
        panda.startAnimation(zoom);

        //Skaliert ein ImageView abwechselnd groß und klein

        zoom.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                panda.startAnimation(zoomOut);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        });

        //Skaliert den Titel abwechselnd groß und klein

        titelZoom.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                titelbild.startAnimation(titelZoomOut);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        });

        zoomOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                panda.startAnimation(zoom);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        });

        titelZoomOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                titelbild.startAnimation(titelZoom);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        });
        background = preferences.getBoolean("background", true);


        start_button = (ImageView) findViewById(R.id.startbutton);

        mPlayerTitel = MediaPlayer.create(this, R.raw.titelmusik);
        mPlayerTitel.setLooping(true);
        mPlayerTitel.start();

        //Leitet an {@link WeaponActivity} weiter.

        waffe.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), WeaponActivity.class);
                startActivity(intent);
            }
        });


        //Leitet an {@link OptionsActivity} weiter.

        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), OptionsActivity.class);
                startActivity(intent);

            }
        });



        //Leitet an {@link GameActivity} weiter.

        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameActivity.counterReset();
                mPlayerTitel.stop();

                Intent intent = new Intent(getBaseContext(), GameActivity.class);
                startActivity(intent);

            }
        });

        if (!background) {
            mPlayerTitel.reset();
        }


    }

    public static Context getContextOfApplication() {
        return applicationContext;
    }

    @Override
    public void onPause() {
        super.onPause();
        mPlayerTitel.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        waffe.setImageResource(WeaponManager.getWeapon(preferences.getInt("waffe", 0)).getImg_Icon());
        mPlayerTitel.start();
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
