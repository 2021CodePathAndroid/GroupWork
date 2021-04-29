package com.example.cookit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Timer extends AppCompatActivity {

    private EditText etTimeInput;
    private TextView tvCountDown;
    private Button btnSet;
    private Button btnStart;
    private Button btnPause;
    private Button btnCancel;

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
        btnSet = findViewById(R.id.btnSet);
        btnStart = findViewById(R.id.btnStart);
        btnPause = findViewById(R.id.btnPause);
        btnCancel = findViewById(R.id.btnCancel);

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = etTimeInput.getText().toString();

                if (input.length() == 0) {
                    Toast.makeText(Timer.this, "Minutes cannot be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                long millisInput = Long.parseLong(input) * 60000;

                if (millisInput == 0) {
                    Toast.makeText(Timer.this, "Please enter a positive number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                setTime(millisInput);
                etTimeInput.setText("");

            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!timerRunning) {
                    startTimer();
                }

            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timerRunning){
                    pauseTimer();
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });


    }

    private void setTime(long milliseconds) {

        startTimeInMillis = milliseconds;
        resetTimer();

    }

    private void startTimer() {

        endTime = System.currentTimeMillis() + timeLeftInMillis;

        CountDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisLeftUntilFinished) {
                timeLeftInMillis = millisLeftUntilFinished;
                updateCountDownText();
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

        String timeLeftFormatted = String.format("%d:%02d:%02d", hours, minutes, seconds);

        tvCountDown.setText(timeLeftFormatted);

    }

    private void pauseTimer() {

        CountDownTimer.cancel();
        timerRunning = false;

    }

    private void resetTimer() {

        timeLeftInMillis = startTimeInMillis;
        updateCountDownText();

    }

    private void updateTimerInterface() {

        if (timerRunning) {
            etTimeInput.setVisibility(View.INVISIBLE);
            btnSet.setVisibility(View.INVISIBLE);
        }

        else {

            etTimeInput.setVisibility(View.VISIBLE);
            btnSet.setVisibility(View.VISIBLE);

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

        if (CountDownTimer != null) {
            CountDownTimer.cancel();
        }

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