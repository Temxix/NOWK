package com.example.nowk;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeHolder {
    private final String currentTime;

    public TimeHolder() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date date = new Date();
        this.currentTime = formatter.format(date);
    }

    public String getCurrentTime() {
        return currentTime;
    }
}
