package com.example.cookieclicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    TextView numOfCookies, perSecond, plusOne;
    ImageView smallCookie, fullgrandma, cookiePic, smallGranPic;
    Button grandma;
    ConstraintLayout layout1, dynamic;
    Toolbar toolbar;
    static AtomicInteger count;
    static AtomicInteger price;
    static AtomicInteger numgrandmabought;
    static AtomicInteger a;
    static AtomicInteger cps;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numOfCookies = findViewById(R.id.id_cookienumber);
        numOfCookies.setShadowLayer(40, 0, 0, Color.RED);
        perSecond = findViewById(R.id.id_perSecond);
        perSecond.setShadowLayer(40, 0, 0, Color.RED);
        cookiePic = findViewById(R.id.id_cookiePic);
        smallGranPic = findViewById(R.id.id_grandmaPic);
        cookiePic.setImageResource(R.drawable.cookie);
        layout1 = findViewById(R.id.layout);
        dynamic = findViewById(R.id.dynamic_content);
        toolbar = findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);

        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);
        //animationDrawable.start();

        price = new AtomicInteger(15);
        numgrandmabought = new AtomicInteger(0);
        a = new AtomicInteger(0);
        cps = new AtomicInteger(0);
        count = new AtomicInteger(0);
        numOfCookies.setText(count + " Cookies");
        grandma = findViewById(R.id.id_grandma);
        grandma.setText("Grandma - " + price.get());
        grandma.setAlpha(.5f);
        grandma.setClickable(false);

        RotateAnimation rotateGran = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateGran.setDuration(1000);
        rotateGran.setRepeatCount(Animation.INFINITE);
        rotateGran.setRepeatMode(1);
        smallGranPic.startAnimation(rotateGran);

        t1.start();
        timeRun();

    }//onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menucookie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.stop:
                if(player != null){
                    player.release();
                    player = null;
                }
                return true;
            case R.id.pause:
                if(player != null){
                    player.pause();
                }
                return true;
            case R.id.play:
                if(player == null){
                    player = MediaPlayer.create(this, R.raw.music2);
                }
                player.setLooping(true);
                player.start();
                return true;
            case R.id.item1:
                layout1.setBackgroundResource(R.drawable.ccbackground);
                return true;
            case R.id.item2:
                if(dynamic.getChildCount() > 0) {
                    dynamic.removeAllViews();
                }
                count.set(0);
                price.set(15);
                numgrandmabought.set(0);
                a.set(0);
                cps.set(0);
                grandma.setText("Grandma - " + price.get());
                perSecond.setText("per second: "+ Math.round(cps.get() * 10.0)/10.0);
                if(player != null){
                    player.release();
                    player = null;
                }
                layout1.setBackgroundResource(R.drawable.background);
                grandma.setAlpha(.5f);
                grandma.setClickable(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(player != null){
            player.release();
            player = null;
        }
    }

    Thread t1 = new Thread(new Runnable() {
        public void run() {
            cookiePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count.getAndIncrement();
                    numOfCookies.setText(count + " Cookies");

                    ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0.95f, 1f, 0.95f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.7f);
                    scaleAnimation.setDuration(100);
                    cookiePic.startAnimation(scaleAnimation);

                    plusOne = new TextView(MainActivity.this);
                    plusOne.setId(View.generateViewId());
                    plusOne.setText("+1");
                    plusOne.setTextColor(Color.WHITE);
                    plusOne.setTextSize((float) 35);
                    plusOne.setShadowLayer(150, 0, 0, Color.GREEN);

                    smallCookie = new ImageView(MainActivity.this);
                    smallCookie.setId(View.generateViewId());
                    smallCookie.setImageResource(R.drawable.smallcookie);
                    smallCookie.setScaleType(ImageView.ScaleType.FIT_XY);

                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                    ConstraintLayout.LayoutParams params1 = new ConstraintLayout.LayoutParams(130, 130);

                    plusOne.setLayoutParams(params);
                    smallCookie.setLayoutParams(params1);

                    layout1.addView(plusOne);
                    layout1.addView(smallCookie);

                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(layout1);

                    constraintSet.connect(plusOne.getId(), ConstraintSet.TOP, layout1.getId(), ConstraintSet.TOP);
                    constraintSet.connect(plusOne.getId(), ConstraintSet.BOTTOM, layout1.getId(), ConstraintSet.BOTTOM);
                    constraintSet.connect(plusOne.getId(), ConstraintSet.START, layout1.getId(), ConstraintSet.START);
                    constraintSet.connect(plusOne.getId(), ConstraintSet.END, layout1.getId(), ConstraintSet.END);

                    constraintSet.connect(smallCookie.getId(), ConstraintSet.TOP, layout1.getId(), ConstraintSet.TOP);
                    constraintSet.connect(smallCookie.getId(), ConstraintSet.BOTTOM, layout1.getId(), ConstraintSet.BOTTOM);
                    constraintSet.connect(smallCookie.getId(), ConstraintSet.START, layout1.getId(), ConstraintSet.START);
                    constraintSet.connect(smallCookie.getId(), ConstraintSet.END, layout1.getId(), ConstraintSet.END);

                    float rand1 = (float)((double)(Math.random()* 0.3) + 0.1);
                    float rand2 = (float)((double)(Math.random()* 0.8) + 0.15);
                    constraintSet.setVerticalBias(plusOne.getId(), rand1);
                    constraintSet.setHorizontalBias(plusOne.getId(), rand2);

                    float rand3 = (float)((double)(Math.random()* 0.8) + 0.15);
                    float rand4 = (float)((double)(Math.random()* 0.4) + 0.2);
                    constraintSet.setHorizontalBias(smallCookie.getId(), rand3);
                    constraintSet.setVerticalBias(smallCookie.getId(), rand4);
                    constraintSet.applyTo(layout1);

                    plusOne.setVisibility(View.INVISIBLE);
                    smallCookie.setVisibility(View.INVISIBLE);

                    AlphaAnimation fade = new AlphaAnimation(1f, 0f);
                    fade.setDuration(1000);
                    fade.setFillAfter(true);

                    TranslateAnimation move = new TranslateAnimation(0, 0, 200, 0);
                    move.setDuration(1000);
                    move.setFillAfter(true);

                    AnimationSet animations = new AnimationSet(false);
                    animations.addAnimation(fade);
                    animations.addAnimation(move);
                    animations.setDuration(1000);
                    plusOne.startAnimation(animations);

                    animations.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            layout1.post(new Runnable() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            plusOne.setVisibility(View.GONE);
                                            layout1.removeView(plusOne);
                                        }
                                    });
                                }
                            });
                        }
                    });

                    AlphaAnimation fade1 = new AlphaAnimation(1f, 0f);
                    fade1.setDuration(1000);
                    fade1.setFillAfter(true);

                    TranslateAnimation move1 = new TranslateAnimation(0, 0, 0, 100);
                    move1.setDuration(1000);
                    move1.setFillAfter(true);

                    AnimationSet animations1 = new AnimationSet(false);
                    animations1.addAnimation(fade1);
                    animations1.addAnimation(move1);
                    animations1.setDuration(1000);
                    smallCookie.startAnimation(animations1);

                    animations1.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            smallCookie.post(new Runnable() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            smallCookie.setVisibility(View.GONE);
                                            layout1.removeView(smallCookie);
                                        }
                                    });
                                }
                            });
                        }
                    });
                    Log.d("Views", String.valueOf(layout1.getChildCount()));
                }
            }); //cookiePic.setOnClickListener

            grandma.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    numgrandmabought.getAndIncrement();

                    ScaleAnimation scaleAnimation3 = new ScaleAnimation(1f, 0.6f, 1f, 0.6f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    scaleAnimation3.setDuration(1000);
                    grandma.startAnimation(scaleAnimation3);

                    fullgrandma = new ImageView(MainActivity.this);
                    fullgrandma.setId(View.generateViewId());
                    fullgrandma.setImageResource(R.drawable.fullgrandma);
                    fullgrandma.setScaleType(ImageView.ScaleType.FIT_XY);

                    ConstraintLayout.LayoutParams params2 = new ConstraintLayout.LayoutParams(170, 400);

                    fullgrandma.setLayoutParams(params2);

                    dynamic.addView(fullgrandma);
                    //layout1.addView(fullgrandma);

                    ConstraintSet constraintSett = new ConstraintSet();
                    constraintSett.clone(dynamic);

                    constraintSett.connect(fullgrandma.getId(), ConstraintSet.TOP, dynamic.getId(), ConstraintSet.TOP);
                    constraintSett.connect(fullgrandma.getId(), ConstraintSet.BOTTOM, dynamic.getId(), ConstraintSet.BOTTOM);
                    constraintSett.connect(fullgrandma.getId(), ConstraintSet.START, dynamic.getId(), ConstraintSet.START);
                    constraintSett.connect(fullgrandma.getId(), ConstraintSet.END, dynamic.getId(), ConstraintSet.END);

                    //constraintSett.connect(fullgrandma.getId(), ConstraintSet.TOP, layout1.getId(), ConstraintSet.TOP);
                    //constraintSett.connect(fullgrandma.getId(), ConstraintSet.BOTTOM, layout1.getId(), ConstraintSet.BOTTOM);
                    //constraintSett.connect(fullgrandma.getId(), ConstraintSet.START, layout1.getId(), ConstraintSet.START);
                    //constraintSett.connect(fullgrandma.getId(), ConstraintSet.END, layout1.getId(), ConstraintSet.END);

                    RotateAnimation rotate = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotate.setDuration(1000);
                    rotate.setFillAfter(true);

                    float rand6 = (float)((double)(Math.random()* 0.95)+0.05);
                    constraintSett.setVerticalBias(fullgrandma.getId(), 0.79f);
                    constraintSett.setHorizontalBias(fullgrandma.getId(), rand6);

                    constraintSett.applyTo(dynamic);
                    //constraintSett.applyTo(layout1);

                    ScaleAnimation scaleAnimationn = new ScaleAnimation(1f, 0.5f, 1f, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.7f);
                    scaleAnimationn.setDuration(100);
                    scaleAnimationn.setFillAfter(true);

                    AnimationSet animations2 = new AnimationSet(false);
                    animations2.addAnimation(rotate);
                    animations2.addAnimation(scaleAnimationn);
                    animations2.setDuration(2000);
                    fullgrandma.startAnimation(animations2);

                    Log.d("Grandmas", String.valueOf(numgrandmabought));

                    count.getAndAdd(-1*price.get());
                    numOfCookies.setText(count.get() + " Cookies");
                    price.getAndAdd(5);

                    cps.set(0);
                    cps.getAndAdd(numgrandmabought.get() * 1);
                    perSecond.setText("per second: "+ Math.round(cps.get() * 10.0)/10.0);

                    grandma.setText("Grandma - " + price.get());
                    if (count.get() < price.get()) {
                        grandma.setAlpha(0.5f);
                        grandma.setClickable(false);
                    }

                }
            }); //grandma.setOnClickListener
            if (count.get() < price.get()) {
                grandma.setAlpha(0.5f);
                grandma.setClickable(false);
            }
        }
    }); //Thread t1

    private void timeRun() {
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        a.set(numgrandmabought.get());
                        if(a.get() > 0){
                            count.getAndAdd(1*a.get());
                            a.getAndDecrement();
                        }
                        numOfCookies.setText(count.get() + " Cookies");
                        if (count.get() >= price.get()) {
                            grandma.setAlpha(1f);
                            grandma.setClickable(true);
                        }
                    }
                });
            }
        };
        timer.schedule(task, 1000, 1000);
    } //timeRun()

} //MainActivity