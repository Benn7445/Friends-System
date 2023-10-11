package me.quartz.friends.utils;

import java.util.Date;

public class TimeUtil {

    public static String findDifference(Date start_date) {
        long difference_In_Time = new Date().getTime() - start_date.getTime();
        long difference_In_Seconds
                = (difference_In_Time
                / 1000)
                % 60;
        long difference_In_Minutes
                = (difference_In_Time
                / (1000 * 60))
                % 60;
        long difference_In_Hours
                = (difference_In_Time
                / (1000 * 60 * 60))
                % 24;
        long difference_In_Years
                = (difference_In_Time
                / (1000l * 60 * 60 * 24 * 365));
        long difference_In_Days
                = (difference_In_Time
                / (1000 * 60 * 60 * 24))
                % 365;
        return difference_In_Years > 0 ? difference_In_Years + " years" : (
                difference_In_Days > 0 ? difference_In_Days + " days" : (
                        difference_In_Hours > 0 ? difference_In_Hours + " hours" : (
                                difference_In_Minutes > 0 ? difference_In_Minutes + " minutes" : difference_In_Seconds + " seconds"
                        )
                )
        );
    }
}
