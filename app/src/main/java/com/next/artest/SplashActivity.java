package com.next.artest;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by arindamnath on 25/11/16.
 */
public class SplashActivity extends AppCompatActivity {

    private ImageView splashImage;
    private AnimationDrawable splashAnim;
    private Animation animFast, animSlow, animVerySlow;
    private TextView splashText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        animFast = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.float_anim);
        animSlow = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.float_anim_slow);
        animVerySlow = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.float_anim_very_slow);

        splashImage = (ImageView) findViewById(R.id.splash_image);
        splashText = (TextView) findViewById(R.id.splash_text);

        splashAnim = (AnimationDrawable) splashImage.getBackground();

        splashImage.post(new Runnable() {
            @Override
            public void run() {
                splashAnim.start();
            }
        });

        findViewById(R.id.cloud_image_1).startAnimation(animVerySlow);
        findViewById(R.id.cloud_image_2).startAnimation(animSlow);
        findViewById(R.id.cloud_image_3).startAnimation(animFast);
        findViewById(R.id.cloud_image_4).startAnimation(animVerySlow);

        splashText.setTypeface(Typeface.createFromAsset(getAssets(), "font/font.ttf"));

        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 2500);
    }
}
