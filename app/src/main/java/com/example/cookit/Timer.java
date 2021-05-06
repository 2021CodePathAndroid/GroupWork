package com.example.cookit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class Timer extends AppCompatActivity {

    private EditText etTimeInput;
    private TextView tvCountDown;

    private FloatingActionButton fabSet;
    private FloatingActionButton fabStart;
    private FloatingActionButton fabPause;
    private FloatingActionButton fabStop;

    private ProgressBar pbTimer;

    private CountDownTimer CountDownTimer;

    private boolean timerRunning;

    private long startTimeInMillis;
    private long timeLeftInMillis;
    private long endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        etTimeInput = findViewById(R.id.etTimeInput);
        tvCountDown = findViewById(R.id.tvCountDown);
        fabSet = findViewById(R.id.fabSet);
        fabStart = findViewById(R.id.fabStart);
        fabPause = findViewById(R.id.fabPause);
        fabStop = findViewById(R.id.fabStop);
        pbTimer = findViewById(R.id.pbTimer);

        fabSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String input = etTimeInput.getText().toString();

                // Check if the input is empty
                if (input.length() == 0) {
                    Toast.makeText(Timer.this, "Minutes cannot be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the input is greater than 24 hours
                if (Integer.parseInt(input) > 1440) {
                    Toast.makeText(Timer.this, "Please enter minutes less than 24 hours.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Convert minutes to milliseconds
                long millisInput = Long.parseLong(input) * 60000;

                // Check if the input is invalid
                if (millisInput == 0) {
                    Toast.makeText(Timer.this, "Please enter positive minutes.", Toast.LENGTH_SHORT).show();
                    return;
                }

                setTime(millisInput);
                etTimeInput.setText("");

            }
        });

        fabStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!timerRunning) {
                    startTimer();
                }
            }
        });

        fabPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timerRunning){
                    pauseTimer();
                }
            }
        });

        fabStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelTimer();
            }
        });

    }

    private void setTime(long milliseconds) {

        startTimeInMillis = milliseconds;
        cancelTimer();

    }

    private void startTimer() {

        endTime = System.currentTimeMillis() + timeLeftInMillis;

        CountDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisLeftUntilFinished) {
                timeLeftInMillis = millisLeftUntilFinished;
                updateCountDownText();
                pbTimer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                timerRunning = false;
            }
        }.start();

        // Timer is now running
        timerRunning = true;

        updateTimerInterface();

    }

    private void updateCountDownText() {

        int hours = (int) (timeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((timeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);

        tvCountDown.setText(timeLeftFormatted);

    }

    private void pauseTimer() {

        CountDownTimer.cancel();
        timerRunning = false;
        pbTimer.setVisibility(View.INVISIBLE);

    }

    private void cancelTimer() {

        timeLeftInMillis = startTimeInMillis;

        if (CountDownTimer != null) {
            CountDownTimer.cancel();
        }

        timerRunning = false;
        updateCountDownText();
        updateTimerInterface();
        pbTimer.setVisibility(View.INVISIBLE);

    }

    private void updateTimerInterface() {

        if (timerRunning) {

            etTimeInput.setVisibility(View.INVISIBLE);
            fabSet.setVisibility(View.INVISIBLE);

        }

        else {

            etTimeInput.setVisibility(View.VISIBLE);
            fabSet.setVisibility(View.VISIBLE);

        }

    }

    @Override
    protected void onStop() {

        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTimeInMillis", startTimeInMillis);
        editor.putLong("millisLeft", timeLeftInMillis);
        editor.putBoolean("timerRunning", timerRunning);
        editor.putLong("endTime", endTime);

        // Save the values
        editor.apply();

        CountDownTimer.cancel();

    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        startTimeInMillis = prefs.getLong("startTimeInMillis", 0);
        timeLeftInMillis = prefs.getLong("millisLeft", startTimeInMillis);
        timerRunning = prefs.getBoolean("timerRunning", false);

        updateCountDownText();
        updateTimerInterface();

        if (timerRunning) {

            endTime = prefs.getLong("endTime", 0);
            timeLeftInMillis = endTime - System.currentTimeMillis();

            if (timeLeftInMillis < 0) {

                timeLeftInMillis = 0;
                timerRunning = false;
                updateCountDownText();
                updateTimerInterface();

            }

            else {
                startTimer();
            }
        }

    }

}