package com.example.fitnessapp;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExerciceActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private VideoView videoView;
    private Button btnCountDown;
    private TextView tvCountDown, tvDesc, tvPause;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillisecond = 10000L;
    private boolean timerIsRunning;

    private TextToSpeech mTTS;

    private long difficulty;
    private float ttsSpeedRate;
    private boolean isInPause=false;

    MediaPlayer player;

    private float timeWorkout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercice);

        initViews();
        setBtnCountDownListener();
        playVideo();

        settingMTTS();
        showDesc();
    }

    private void initViews() {
        tvCountDown = findViewById(R.id.tvCountDown);
        btnCountDown = findViewById(R.id.btnCountDown);
        tvDesc = findViewById(R.id.tvDesc);
        progressBar = findViewById(R.id.progressBar);
        prefs();

        progressBar.setMax((int) difficulty);
        progressBar.setProgress((int) difficulty);

        videoView = findViewById(R.id.videoView);
        tvPause = findViewById(R.id.tvPause);
    }

    private void prefs() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        //ttsSpeedRate = Float.parseFloat(sharedPreferences.getString("key_tts_speed", "0.5"));

        difficulty = Long.parseLong(sharedPreferences.getString("key_upload_quality", "0"));
        timeLeftInMillisecond = difficulty * 1000;
        tvCountDown.setText(String.valueOf(difficulty));

        timeWorkout= Float.parseFloat(sharedPreferences.getString(getCurrentDay(),"0"));
        //Toast.makeText(this, "timeWorkout "+timeWorkout + " s", Toast.LENGTH_SHORT).show();
    }

    private void settingMTTS() {

        mTTS = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.getDefault());

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                        Toast.makeText(com.example.fitnessapp.ExerciceActivity.this, R.string.not_supported_lang, Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(com.example.fitnessapp.ExerciceActivity.this, R.string.tts_init_failed, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void playVideo() {
        String videoPath = getIntent().getStringExtra("video");
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
        videoView.stopPlayback();
        stopPlayer();
    }

/*
    private void TTSspeak(String msg) {
        mTTS.setSpeechRate(ttsSpeedRate);
        mTTS.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
        //Toast.makeText(this, "am speaking", Toast.LENGTH_SHORT).show();
    }

 */

    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }
        stopPlayer();
        super.onDestroy();
    }



    private void setBtnCountDownListener() {
        btnCountDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStopTimer();
            }
        });
    }

    public void startStopTimer() {
        if (timerIsRunning)
            stopTimer();
        else
            startTimer();
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillisecond, 1000-500) {
            @Override
            public void onTick(long l) {
                timeLeftInMillisecond = l;
                progressBar.setProgress((int) l / 1000);
                updateTimer();
            }

            @Override
            public void onFinish() {

                timeWorkout+=difficulty;

                Toast.makeText(com.example.fitnessapp.ExerciceActivity.this, "Done ", Toast.LENGTH_SHORT).show();
                if (!isInPause)
                    pauseWorkout();
                else
                    nextExcercise();

                saveData(getCurrentDay(), String.valueOf(timeWorkout));
            }
        }.start();

        timerIsRunning = true;
        btnCountDown.setText("Pause");

        //TTSspeak(tvDesc.getText().toString());

    }

    private void pauseWorkout(){
        isInPause=true;
        tvPause.setVisibility(View.VISIBLE);
        timerIsRunning = false;
        timeLeftInMillisecond = 10 * 1000;
        progressBar.setMax(10);
        progressBar.setProgress(10);

        videoView.pause();
        tvDesc.setText("");
        play();
        startStopTimer();
    }

    private void nextExcercise() {
        tvPause.setVisibility(View.GONE);
        timerIsRunning=false;
        timeLeftInMillisecond=difficulty*1000;
        progressBar.setMax((int)difficulty);
        progressBar.setProgress((int)difficulty);
        showDesc();
        startStopTimer();
        Toast.makeText(this, "More", Toast.LENGTH_SHORT).show();
        playVideo();
    }

    @SuppressLint("SetTextI18n")
    public void stopTimer() {
        if (countDownTimer!=null) countDownTimer.cancel();

        timerIsRunning = false;
        btnCountDown.setText("Resume");
        mTTS.stop();

    }

    @SuppressLint("SetTextI18n")
    public void updateTimer() {
        tvCountDown.setText(timeLeftInMillisecond / 1000 + "");
    }

    public void showDesc() {
        String description = getIntent().getStringExtra("desc");
        tvDesc.setText(description);
    }


    private void saveData(String key, String data){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,data);
        editor.apply();
    }

    public void play() {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.clock);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
            player.start();
        }
    }

    private void stopPlayer () {
        if (player != null) {
            player.release();
            player = null;
            Toast.makeText(this, "MediaPlayer released", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }

    private String getCurrentDay(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(date.getTime());

    }

}

