package com.example.sven.ninja_test;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.media.MediaPlayer;

import com.example.sven.ninja_test.weapons.WeaponManager;

/**
 * In dieser Klasse findet das eigentliche Spiel statt.
 * Bei Druck des unteren ImageViews wird eine Animation ausgelöst und beim richtigen Drücken einen Punkt vergeben.
 * Bei Fehler wird die {@link GameOverActivity } gestartet.
 * <p>
 * Created by Sven on 25.04.2017.
 */


public class GameActivity extends Activity {

    private ImageView waffe;
    private Animation startFlying;
    private Animation smokeanim;
    public static int counter;
    private TextView zaehler;
    private TextView score;
    private ImageView waffeButton;
    private TextView ziel;
    private static int zahl;
    private int sekunden = 1;
    private ImageView oma;
    private ImageView opa;
    private ImageView ninja;
    private ImageView ninja2;
    private ImageView smoke;
    private MediaPlayer mPlayerIngame;
    private MediaPlayer mPlayerThrow;
    private MediaPlayer mPlayerHighscore;
    private int oma_counter;
    private int ninja_counter;
    SharedPreferences preferences;
    private int waffenauswahl;
    private int highscore;
    private boolean rauch;
    private boolean sound;
    private int milsek;
    private boolean background;
    private static boolean correctturn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        waffe = (ImageView) findViewById(R.id.weaponView);

        waffeButton = (ImageView) findViewById(R.id.weaponView);
        oma = (ImageView) findViewById(R.id.oma);
        opa = (ImageView) findViewById(R.id.opa);
        ninja = (ImageView) findViewById(R.id.ninja);
        ninja2 = (ImageView) findViewById(R.id.ninja2);
        smoke = (ImageView) findViewById(R.id.smoke);
        startFlying = AnimationUtils.loadAnimation(this, R.anim.weapon_throw);
        smokeanim = AnimationUtils.loadAnimation(this, R.anim.smoke);

        zaehler = (TextView) findViewById(R.id.zaehler);
        zaehler.setText("Round: " + String.valueOf(counter));
        ziel = (TextView) findViewById(R.id.ziel);
        ziel.setText("Ninja");
        score = (TextView) findViewById(R.id.highscore);


        mPlayerIngame = MediaPlayer.create(this, R.raw.ingame);
        mPlayerIngame.setLooping(true);
        mPlayerIngame.start();

        mPlayerThrow = MediaPlayer.create(this, R.raw.throwing);
        mPlayerThrow.setLooping(false);

        mPlayerHighscore = MediaPlayer.create(this, R.raw.gong);
        mPlayerHighscore.setLooping(false);

        smoke.setVisibility(android.view.View.INVISIBLE);
        ninja.setVisibility(android.view.View.VISIBLE);
        ninja2.setVisibility(android.view.View.INVISIBLE);
        oma.setVisibility(android.view.View.INVISIBLE);
        opa.setVisibility(android.view.View.INVISIBLE);

        preferences = getSharedPreferences(MainActivity.PREFERENCE, GameOverActivity.MODE_PRIVATE);
        background = preferences.getBoolean("background", true);
        highscore = preferences.getInt("score", 0);
        sound = preferences.getBoolean("sounds", true);
        waffenauswahl = preferences.getInt("waffe", 1);
        rauch = preferences.getBoolean("smoke", false);
        counter = 0;

        if (rauch) {
            milsek = 830;
        } else {
            milsek = 1500;
        }

        if (!sound) {
            mPlayerHighscore.reset();
            mPlayerThrow.reset();
        }
        score.setText("HS: " + highscore);
        waffe.setImageResource(WeaponManager.getWeapon(preferences.getInt("waffe", 0)).getImg());

        if (!background) {
            mPlayerIngame.reset();
        }

        //Erstellt einen Timer

        final CountDownTimer countDownTimer = new CountDownTimer(sekunden * milsek, 1) {

            @Override
            public void onTick(long millis) {

            }

            //Diese Methode wird nach Ablauf des Timers ausgeführt.
            //Bei Fehler leitet sie zur GameOverActivity weiter.


            @Override
            public void onFinish() {
                if (ninja.getVisibility() == View.VISIBLE) {
                    waffeButton.setOnClickListener(null);
                    waffeButton.setEnabled(false);
                    waffeButton.setClickable(false);

                    Log.e("TAG", "counter finish");

                    if (!GameActivity.this.isFinishing()) {
                        startTimerAnimation();
                        Intent intent = new Intent(getBaseContext(), GameOverActivity.class);
                        finish();
                        startActivity(intent);
                    }
                    mPlayerHighscore.stop();
                    mPlayerIngame.stop();
                    return;
                }

                if (ninja2.getVisibility() == View.VISIBLE) {
                    waffeButton.setOnClickListener(null);
                    waffeButton.setEnabled(false);
                    waffeButton.setClickable(false);

                    Log.e("TAG", "counter finish");

                    if (!GameActivity.this.isFinishing()) {
                        startTimerAnimation();
                        Intent intent = new Intent(getBaseContext(), GameOverActivity.class);
                        finish();
                        startActivity(intent);
                    }


                    mPlayerHighscore.stop();
                    mPlayerIngame.stop();
                    return;
                }

                if (oma.getVisibility() == View.VISIBLE) {
                    if(correctturn){
                    this.start();
                    hide();
                    if (rauch) {
                        waffeButton.setEnabled(false);
                        waffeButton.setClickable(false);
                        startExplosion();
                    }

                        gegnerSpawn();
                        counter++;
                    }

                    if (counter > highscore) {
                        score.setText("HS: " + counter);
                    }
                    startTimerAnimation();
                    if (counter == highscore) {
                        mPlayerHighscore.start();
                    }
                    zaehler.setText("Round: " + String.valueOf(counter));


                }
                if (opa.getVisibility() == View.VISIBLE) {
                    if(correctturn) {
                        this.start();
                        hide();
                        if (rauch) {
                            waffeButton.setEnabled(false);
                            waffeButton.setClickable(false);
                            startExplosion();
                        }
                        gegnerSpawn();
                        counter++;

                    }
                    if (counter > highscore) {
                        score.setText("HS: " + counter);
                    }
                    startTimerAnimation();
                    if (counter == highscore) {
                        mPlayerHighscore.start();
                    }

                    zaehler.setText("Round: " + String.valueOf(counter));


                }


            }
        };


         /*Dient dem Starten und spielen des Spiels.
         Mit dem Drücken von waffeButton wird eine Animation ausgelöst.
         Hierbei wird ein Imageview senkrecht bewegt,
         anschließend folgt eine Kontrolle auf Art des sichtbaren ImageViews,
         welches nach jedem Wurf per Zufall am oberen Bildschirm generiert wird.*/

        waffeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                waffeButton.setEnabled(false);
                waffeButton.setClickable(false);

                if (oma.getVisibility() == View.VISIBLE) {
                    waffeButton.setEnabled(false);
                    waffeButton.setClickable(false);
                    correctturn = false;
                    throwing(false);
                    mPlayerHighscore.stop();
                    mPlayerIngame.stop();


                    //Verzögerung, da das Aufrufen der GameOverActivity schneller ist, als die Animation.

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(330);
                                Intent intent = new Intent(getBaseContext(), GameOverActivity.class);
                                startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                finish();
                            }
                        }
                    });

                    thread.start();


                }
                if (opa.getVisibility() == View.VISIBLE) {
//                        waffeButton.setOnClickListener(null);
                    waffeButton.setEnabled(false);
                    waffeButton.setClickable(false);
                    correctturn = false;
                    throwing(false);
                    mPlayerIngame.stop();
                    mPlayerHighscore.stop();


                    //Verzögerung, da das Aufrufen der link GameOverActivity schneller ist, als die Animation.

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(330);
                                Intent intent = new Intent(getBaseContext(), GameOverActivity.class);
                                startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                finish();
                            }
                        }
                    });

                    thread.start();

                }


                //Kontrolle, ob richtiger Wurf. Es wird anschließend die Wurf-Animation durchgeführt und der Timer zurückgesetzt.

                if (ninja.getVisibility() == View.VISIBLE) {
                    mPlayerThrow.start();
                    countDownTimer.start();
                    startTimerAnimation();
                    waffe.setVisibility(android.view.View.INVISIBLE);
                    throwing(true);
                    Log.d("VorNinja", "###" + counter);
                    counter++;
                    Log.d("NachNinja", "###" + counter);
                    correctturn = true;
                }
                if (ninja2.getVisibility() == View.VISIBLE) {
                    mPlayerThrow.start();
                    countDownTimer.start();
                    startTimerAnimation();
                    waffe.setVisibility(android.view.View.INVISIBLE);
                    throwing(true);
                    counter++;
                    correctturn = true;
                }
                zaehler.setText("Round: " + String.valueOf(counter));

                if (counter > highscore) {
                    score.setText("HS: " + counter);
                }
                if (counter == highscore) {
                    mPlayerHighscore.start();
                }

            }

        });


    }


    //Beinhaltet das Ausführen der Animation sowie einen Gegnerspawn



    private void throwing(final boolean spawn) {

        waffe.startAnimation(startFlying);
        startFlying.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                waffe.setVisibility(android.view.View.VISIBLE);
                if (correctturn == true) hide();
                if (rauch) {
                    waffeButton.setEnabled(false);
                    waffeButton.setClickable(false);
                    startExplosion();
                }
                if (spawn) gegnerSpawn();

                waffeButton.setEnabled(true);
                waffeButton.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {

            }
        });


    }

    public static int getCounter() {
        return counter;
    }

    public static void counterReset() {
        counter = 0;
    }

    //Generiert je nach Rundenanzahl einen zufälligen Gegner oder Verbündeten

    public void gegnerSpawn() {
        if (counter < 15) {
            zahl = (int) (Math.random() * 2);
            switch ((int) zahl) {
                case 0:
                    if (ninja_counter == 5) {
                        oma.setVisibility(android.view.View.VISIBLE);
                        ninja_counter = 0;
                        break;
                    }

                    ninja.setVisibility(android.view.View.VISIBLE);
                    ninja_counter++;
                    break;

                case 1:
                    if (oma_counter == 2) {
                        ninja.setVisibility(android.view.View.VISIBLE);
                        oma_counter = 0;
                        break;
                    }
                    oma.setVisibility(android.view.View.VISIBLE);
                    oma_counter++;


                    break;
            }
        } else if (counter < 25) {
            zahl = (int) (Math.random() * 3);

            switch (zahl) {
                case 0:
                    if (ninja_counter == 5) {
                        oma.setVisibility(android.view.View.VISIBLE);
                        ninja_counter = 0;
                        break;
                    }

                    ninja.setVisibility(android.view.View.VISIBLE);
                    ninja_counter++;
                    break;

                case 1:
                    if (oma_counter == 2) {
                        ninja.setVisibility(android.view.View.VISIBLE);
                        oma_counter = 0;
                        break;
                    }
                    oma.setVisibility(android.view.View.VISIBLE);
                    oma_counter++;

                    break;

                case 2:
                    if (ninja_counter == 5) {
                        oma.setVisibility(android.view.View.VISIBLE);
                        ninja_counter = 0;
                        break;
                    }

                    ninja2.setVisibility(android.view.View.VISIBLE);
                    ninja_counter++;
                    break;
            }
        } else {
            zahl = (int) (Math.random() * 4);

            switch (zahl) {
                case 0:
                    if (ninja_counter == 5) {
                        oma.setVisibility(android.view.View.VISIBLE);
                        ninja_counter = 0;
                        break;
                    }

                    ninja.setVisibility(android.view.View.VISIBLE);
                    ninja_counter++;
                    break;

                case 1:
                    if (oma_counter == 2) {
                        ninja.setVisibility(android.view.View.VISIBLE);
                        oma_counter = 0;
                        break;
                    }
                    oma.setVisibility(android.view.View.VISIBLE);
                    oma_counter++;

                    break;

                case 2:
                    if (ninja_counter == 5) {
                        opa.setVisibility(android.view.View.VISIBLE);
                        ninja_counter = 0;
                        break;
                    }

                    ninja2.setVisibility(android.view.View.VISIBLE);
                    ninja_counter++;
                    break;

                case 3:
                    if (oma_counter == 2) {
                        ninja.setVisibility(android.view.View.VISIBLE);
                        oma_counter = 0;
                        break;
                    }
                    opa.setVisibility(android.view.View.VISIBLE);
                    oma_counter++;

                    break;

            }

        }
    }


    //Versteckt das ImageView am oberen Bildschirm

    public void hide() {
        ninja.setVisibility(View.INVISIBLE);
        ninja2.setVisibility(View.INVISIBLE);
        oma.setVisibility(View.INVISIBLE);
        opa.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPlayerIngame.pause();
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPlayerIngame.start();
    }

    @Override
    public void onBackPressed() {
        GameActivity.counterReset();
        mPlayerIngame.stop();
        mPlayerHighscore.stop();
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }

    //Verursacht eine kurze Rauchexplosion, die als Animation gestartet wird.

    private void startExplosion() {
        smoke.startAnimation(smokeanim);
        smokeanim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                waffeButton.setEnabled(true);
                waffeButton.setClickable(true);
                gegnerSpawn();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        });
    }


    //Stellt einen visuellen Timer in der linken oberen Ecke dar.

    private void startTimerAnimation() {
        ProgressBar mProgressBar = (ProgressBar) findViewById(R.id.progressbar_timer);
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(mProgressBar, "progress", 100, 0);
        progressAnimator.setDuration(sekunden * milsek);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();
    }

}
