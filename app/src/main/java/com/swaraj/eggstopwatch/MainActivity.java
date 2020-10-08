package com.swaraj.eggstopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    CountDownTimer countDownTimer;
    MediaPlayer mPlayer;
    SeekBar timerController;
    TextView textView ;
   Button timerButton ;
    boolean controlIsActive = false;

    public void updateTimer(int seconds)
    {
        int minute = (int) seconds/60 ;
        int sec = seconds - (minute*60);
        if(sec <= 9){
            textView.setText(Integer.toString(minute) + ":0" + Integer.toString(sec));
        }else
        {
            textView.setText(Integer.toString(minute) + ":" + Integer.toString(sec));
        }
    }
    public void countDownStarts(View view){
         if(controlIsActive == false) {
             controlIsActive = true;
             timerController.setVisibility(View.INVISIBLE);
             timerButton.setText("STOP!");
            countDownTimer = new CountDownTimer((timerController.getProgress() * 1000) + 100, 1000) {
                 @Override
                 public void onTick(long millisUntilFinished) {
                     updateTimer((int) millisUntilFinished / 1000);
                 }

                 @Override
                 public void onFinish() {
                      mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound);
                     mPlayer.start();

                 }
             }.start();
         }else
         {
             timerController.setVisibility(View.VISIBLE);
             timerButton.setText("GO!!!");
             countDownTimer.cancel();
             timerController.setProgress(30);
             textView.setText("0:30");
             controlIsActive = false;
             mPlayer.stop();
         }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerButton = findViewById(R.id.timerButton);
        timerController = findViewById(R.id.seekBar);
        textView = findViewById(R.id.timerTextView);
        timerController.setMax(600);
        timerController.setProgress(30);
        timerController.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Log.i("Seekbar",Integer.toString(progress));

                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}
