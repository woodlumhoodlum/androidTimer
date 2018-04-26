package com.javacodegeeks.android.androidtimerexample;

/**
 * Created by Woodlum Hoodlum on 4/21/2018.
 */
public class Task {

    private int size;
    private String name;
    private long timeInMilliseconds = 0L;

    private int state = 0; //0 is not activated, 1 is activated, 2 is delivered, 3 is end

    public long getTimeInMilliseconds() {
        return timeInMilliseconds;
    }

    public void setTimeInMilliseconds(long timeInMilliseconds) {
        this.timeInMilliseconds = timeInMilliseconds;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    private static final Task holder = new Task();
    public static Task getInstance() {return holder;}
}
