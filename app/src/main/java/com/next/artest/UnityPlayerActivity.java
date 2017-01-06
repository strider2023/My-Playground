package com.next.artest;

import com.unity3d.player.*;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UnityPlayerActivity extends AppCompatActivity {

	protected UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code
    @BindView(R.id.unity_container) FrameLayout container;
    @BindView(R.id.unity_activity_back) ImageView backButton;

	@Override protected void onCreate (Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.RGBX_8888); // <--- This makes xperia play happy
		setContentView(R.layout.activity_unity);
        ButterKnife.bind(this);

		mUnityPlayer = new UnityPlayer(this);
		container.addView(mUnityPlayer);
		mUnityPlayer.requestFocus();
	}

    @OnClick(R.id.unity_activity_back)
    void goBack() {
        finish();
    }

	@OnClick(R.id.unity_screenshot_btn)
	void takeScreenshot() {
		Bitmap bitmap = Bitmap.createBitmap(
				mUnityPlayer.getWidth(),
				mUnityPlayer.getHeight(),
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		mUnityPlayer.draw(canvas);
		try {
			FileOutputStream output = new FileOutputStream(
					Environment.getExternalStorageDirectory() + "/screenshot_"+
							String.valueOf(System.currentTimeMillis()) + ".png");
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Quit Unity
	@Override protected void onDestroy () {
		mUnityPlayer.quit();
		super.onDestroy();
	}

	// Pause Unity
	@Override protected void onPause() {
		super.onPause();
		mUnityPlayer.pause();
	}

	// Resume Unity
	@Override protected void onResume() {
		super.onResume();
		mUnityPlayer.resume();
	}

	// This ensures the layout will be correct.
	@Override public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mUnityPlayer.configurationChanged(newConfig);
	}

	// Notify Unity of the focus change.
	@Override public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		mUnityPlayer.windowFocusChanged(hasFocus);
	}

	// For some reason the multiple keyevent type is not supported by the ndk.
	// Force event injection by overriding dispatchKeyEvent().
	@Override public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
			return mUnityPlayer.injectEvent(event);
		return super.dispatchKeyEvent(event);
	}

	// Pass any events not handled by (unfocused) views straight to UnityPlayer
	@Override public boolean onKeyUp(int keyCode, KeyEvent event)     { return mUnityPlayer.injectEvent(event); }

	@Override public boolean onKeyDown(int keyCode, KeyEvent event)   {
		return mUnityPlayer.injectEvent(event);
	}

	@Override public boolean onTouchEvent(MotionEvent event)          { return mUnityPlayer.injectEvent(event); }
	/*API12*/ public boolean onGenericMotionEvent(MotionEvent event)  { return mUnityPlayer.injectEvent(event); }
}
