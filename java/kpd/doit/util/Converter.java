package kpd.doit.util;

import androidx.room.TypeConverter;

import java.util.Date;

import model.Priority;

//Converts types of data for database
public class Converter {

    @TypeConverter       //reference for all classes and packages to convert data type for android
    public static Date fomTimeStamp(Long value){
        return value == null ? null: new Date(value);
    }
    @TypeConverter
    public static Long dateToTimeStamp(Date date){  //convert a date type to accepted data type
        return date == null ? null: date.getTime();
    }
    @TypeConverter
    public static String fromPriority(Priority priority){
        return priority ==null ? null: priority.name();  //.name() enum gives string representation of enum field

    }

    @TypeConverter
    public static Priority toPriority (String priority){
        return priority==null? null: Priority.valueOf(priority);  //gives opposite of previous line
    }


}
