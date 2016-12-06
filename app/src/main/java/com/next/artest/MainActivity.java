package com.next.artest;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements PopupMenu.OnMenuItemClickListener {

    @BindView(R.id.splash_image) ImageView splashImage;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.open_menu) ImageView showMenu;

    private AnimationDrawable splashAnim;
    private Animation animFast, animSlow, animVerySlow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        animFast = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.float_anim);
        animSlow = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.float_anim_slow);
        animVerySlow = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.float_anim_very_slow);

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
    }

    @OnClick({R.id.launch_words_btn, R.id.launch_numbers_btn})
    void onLauncherSelected(FloatingActionButton fab) {
        Intent intent = new Intent(MainActivity.this, SelectTypeActivity.class);
        switch (fab.getId()) {
            case R.id.launch_words_btn:
                intent.putExtra("title", "Learning Alphabets");
                intent.putExtra(SelectTypeActivity.ACTIVITY_LIST_TYPE, 0);
                break;
            case R.id.launch_numbers_btn:
                intent.putExtra("title", "Learning Numbers");
                intent.putExtra(SelectTypeActivity.ACTIVITY_LIST_TYPE, 1);
                break;
        }
        startActivity(intent);
    }

    @OnClick(R.id.open_menu)
    void showOptionsMenu() {
        PopupMenu popupMenu = new PopupMenu(this, showMenu);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.menu_select_type);
        popupMenu.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_logout:
                finish();
                return true;
            default:
                return false;
        }
    }
}
