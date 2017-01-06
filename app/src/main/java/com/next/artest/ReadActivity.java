package com.next.artest;

import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReadActivity extends AppCompatActivity implements RecognitionListener {

    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    private boolean isListening = false;
    @BindView(R.id.read_text) TextView referenceText;
    @BindView(R.id.read_start_btn) FloatingActionButton listenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        mSpeechRecognizer.setRecognitionListener(this);
    }

    @OnClick(R.id.read_start_btn)
    public void onReadToggle(){
        if (!isListening) {
            listenButton.setBackgroundTintList(getResources().getColorStateList(android.R.color.holo_red_dark));
            isListening = true;
            mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
        } else {
            listenButton.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimaryDark));
            isListening = false;
            mSpeechRecognizer.stopListening();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSpeechRecognizer != null) {
            mSpeechRecognizer.destroy();
        }
    }

    @Override
    public void onReadyForSpeech(Bundle bundle) { }

    @Override
    public void onBeginningOfSpeech() { }

    @Override
    public void onRmsChanged(float v) { }

    @Override
    public void onBufferReceived(byte[] bytes) { }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int i) {
        Log.d("Test", "Error : " + String.valueOf(i));
        isListening = false;
        mSpeechRecognizer.stopListening();
        listenButton.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimaryDark));
        switch (i) {
            case SpeechRecognizer.ERROR_NETWORK:
                Snackbar.make(listenButton, "Unable to connect to the internet.", Snackbar.LENGTH_LONG).show();
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                Snackbar.make(listenButton, "Unable to connect to the internet.", Snackbar.LENGTH_LONG).show();
                break;
            default:
                Snackbar.make(listenButton, "Something went wrong. Please try again!", Snackbar.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if(matches.size() > 0) {
            for(String value : matches) {
                Log.d("Test", "Match : " + value);
            }
        }
        mSpeechRecognizer.stopListening();
        isListening = false;
        listenButton.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimaryDark));
    }

    @Override
    public void onPartialResults(Bundle results) {
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if(matches.size() > 0) {
            for(String value : matches) {
                Log.d("Test", "Partial Match : " + value);
                highlightText(value);
            }
        }
    }

    @Override
    public void onEvent(int i, Bundle bundle) { }

    private void highlightText(String value) {
        Spannable wordSpan = new SpannableString(referenceText.getText());
        wordSpan.setSpan(new BackgroundColorSpan(0xFFFFFF00), 0,
                value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        referenceText.setText(wordSpan, TextView.BufferType.SPANNABLE);
    }
}
