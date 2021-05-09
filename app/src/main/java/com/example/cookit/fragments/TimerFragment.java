package com.example.cookit.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookit.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimerFragment extends Fragment {

    private EditText etTimeInput;
    private TextView tvCountDown;

    private FloatingActionButton fabSet;
    private FloatingActionButton fabStart;
    private FloatingActionButton fabPause;
    private FloatingActionButton fabStop;

    private ProgressBar pbTimer;

    private android.os.CountDownTimer CountDownTimer;

    private boolean timerRunning;

    private long startTimeInMillis;
    private long timeLeftInMillis;
    private long endTime;

    public TimerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //setContentView(R.layout.activity_timer);

        etTimeInput = view.findViewById(R.id.etTimeInput);
        tvCountDown = view.findViewById(R.id.tvCountDown);
        fabSet = view.findViewById(R.id.fabSet);
        fabStart = view.findViewById(R.id.fabStart);
        fabPause = view.findViewById(R.id.fabPause);
        fabStop = view.findViewById(R.id.fabStop);
        pbTimer = view.findViewById(R.id.pbTimer);

        fabSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String input = etTimeInput.getText().toString();

                // Check if the input is empty
                if (input.length() == 0) {
                    Toast.makeText(getContext(), "Minutes cannot be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the input is greater than 24 hours
                if (Integer.parseInt(input) > 1440) {
                    Toast.makeText(getContext(), "Please enter minutes less than 24 hours.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Convert minutes to milliseconds
                long millisInput = Long.parseLong(input) * 60000;

                // Check if the input is invalid
                if (millisInput == 0) {
                    Toast.makeText(getContext(), "Please enter positive minutes.", Toast.LENGTH_SHORT).show();
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
    public void onStop() {

        super.onStop();

        SharedPreferences prefs = getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
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
    public void onStart() {
        super.onStart();

        SharedPreferences prefs = getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);

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