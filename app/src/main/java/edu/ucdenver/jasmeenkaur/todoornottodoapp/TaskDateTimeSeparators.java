package edu.ucdenver.jasmeenkaur.todoornottodoapp;

import java.util.ArrayList;

public class TaskDateTimeSeparators {
    private Task task;

    public TaskDateTimeSeparators(Task task) {
        this.task = task;

    }

    private String[] separateDate(String date){
        String[] separated = date.split("[/-]");
        return separated;
    }

    private String[] separateTime(String time){
        String[] separated = time.split("[: ]");
        return separated;
    }

}
