package com.next.artest;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView splashImage;
    private AnimationDrawable splashAnim;
    private Animation animFast, animSlow, animVerySlow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animFast = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.float_anim);
        animSlow = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.float_anim_slow);
        animVerySlow = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.float_anim_very_slow);

        splashImage = (ImageView) findViewById(R.id.splash_image);

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

        findViewById(R.id.launch_ar_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UnityPlayerNativeActivity.class));
            }
        });
    }
}
