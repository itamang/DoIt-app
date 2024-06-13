package kpd.doit.util;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import model.Priority;
import model.ToDoModel;

public class Utils {

    public static String formatDate(Date date){
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
            //create date patttern Jan, 30, 2025
            simpleDateFormat.applyPattern("EEE, MMM d");
            return simpleDateFormat.format(date);
    }

    public static String formatTime(Date time){
        SimpleDateFormat simpleDateFormat=(SimpleDateFormat) SimpleDateFormat.getTimeInstance();
        //create time pattern hour:min
        simpleDateFormat.applyPattern("HH : m");
        return simpleDateFormat.format(time);
    }


    //close the keyboard
    public static void hideSoftKeyboard(View view){
        InputMethodManager imm= (InputMethodManager) view.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);

    }


    public static int priorityColor(ToDoModel task){
        int color;
        if (task.getPriority()== Priority.DAILY_TASK){
            color = Color.argb(200, 128,0,128);
        }else if (task.getPriority()==Priority.ASSIGNMENT){
            color = Color.argb(200,238,130,238);
        }else{
            color = Color.argb(200,0,128,128);
        }
        return color;
    }


}
