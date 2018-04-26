package com.javacodegeeks.android.androidtimerexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private Button taskButton1;

    //private TextView taskTimer1;

    private long startTime = 0L;

    private Handler customHandler = new Handler();

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    Task task1 = Task.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);


        if(Task.getInstance()==null){
            task1 = new Task();

            onStop();

            timeSwapBuff = 0;
            timeInMilliseconds = 0L;
        }

        if(task1.getState() != 0) {
            startTime = task1.getTimeInMilliseconds();
            customHandler.postDelayed(updateTimerThread, 0);
        }
        else {
            //startTime = SystemClock.uptimeMillis();
            //customHandler.postDelayed(updateTimerThread, 0);
        }

        //taskTimer1 = (TextView) findViewById(R.id.taskTimer1);

        taskButton1 = (Button) findViewById(R.id.taskButton1);

        updateColor(task1);

        final Task finalTask = task1;
        taskButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (finalTask.getState() == 0) {
                    startTime = SystemClock.uptimeMillis();
                    finalTask.setState(1);
                    finalTask.setTimeInMilliseconds(startTime);
                    customHandler.postDelayed(updateTimerThread, 0);

                } else {
                    Intent myIntent = new Intent(view.getContext(), HomeActivity.class);
                    startActivityForResult(myIntent, 0);
                }
            }

        });
    }

    protected void onStop() {
        super.onStop();
        customHandler.removeCallbacks(updateTimerThread);
    }

    private void updateColor(Task task1) {
        if(task1.getState()==0) {
            taskButton1.setBackgroundColor(0xFF277118);
        } else if(task1.getState()==1){
            taskButton1.setBackgroundColor(0xFFFFFFFF);
        } else if(task1.getState()==2){
            taskButton1.setBackgroundColor(0xFFFFFF00);
        }  else if(task1.getState()==3){
            taskButton1.setBackgroundColor(0xFF13bcf9);
        }
    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            taskButton1.setText("" + mins + ":"
                    + String.format("%02d", secs));// + ":"
                    //+ String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }

    };

}