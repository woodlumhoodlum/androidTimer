package com.javacodegeeks.android.androidtimerexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomeActivity extends Activity {

	private Button deliveredButton;
	private Button clearedButton;
	private Button doneButton;
	private Button backButton;
	private View backgroundHome;

	private TextView timerValue;
	
	private long startTime = 0L;
	
	private Handler customHandler = new Handler();
	
	long timeInMilliseconds = 0L;
	long timeSwapBuff = 0L;
	long updatedTime = 0L;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Task task1 = Task.getInstance();

		timerValue = (TextView) findViewById(R.id.timerValue);
		startTime = task1.getTimeInMilliseconds();//SystemClock.uptimeMillis();
		customHandler.postDelayed(updateTimerThread, 0);

		backgroundHome = findViewById(R.id.backgroundHome);
		deliveredButton = (Button) findViewById(R.id.deliveredButton);
		
		deliveredButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				task1.setState(2);
				//customHandler.postDelayed(updateTimerThread, 0);
				updateColor(task1);
			}
		});

		clearedButton = (Button) findViewById(R.id.clearedButton);

		clearedButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				task1.setState(3);
				updateColor(task1);
			}
		 });

		doneButton = (Button) findViewById(R.id.doneButton);

		doneButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				timeSwapBuff = 0;
				onStop();
				timeInMilliseconds = 0L;
				task1.setState(0);
				startTime = 0;
				updatedTime = 0;
				timerValue.setText("0:00");
				Intent myIntent = new Intent(view.getContext(), MainActivity.class);
				startActivityForResult(myIntent, 0);
			}
		});

		backButton = (Button) findViewById(R.id.backButton);

		backButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(), MainActivity.class);
				startActivityForResult(myIntent, 0);
			}
		});
		updateColor(task1);
	}

	private void updateColor(Task task1) {
		if(task1.getState()==0) {
			backgroundHome.setBackgroundColor(0xFF277118);
		} else if(task1.getState()==1){
			backgroundHome.setBackgroundColor(0xFFFFFFFF);
		} else if(task1.getState()==2){
			backgroundHome.setBackgroundColor(0xFFFFFF00);
		}  else if(task1.getState()==3){
			backgroundHome.setBackgroundColor(0xFF13bcf9);
		}
	}

	protected void onStop() {
		super.onStop();
		customHandler.postDelayed(updateTimerThread, 0);
		customHandler.removeCallbacks(updateTimerThread);
	}

	private Runnable updateTimerThread = new Runnable() {

		public void run() {
			timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

			updatedTime = timeSwapBuff + timeInMilliseconds;

			int secs = (int) (updatedTime / 1000);
			int mins = secs / 60;
			secs = secs % 60;
			//int milliseconds = (int) (updatedTime % 1000);
			timerValue.setText("" + mins + ":"
					+ String.format("%02d", secs) );//+ ":"
					//+ String.format("%03d", milliseconds));
			customHandler.postDelayed(this, 0);
		}

	};

}