package com.kondee.thenewlegend.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtils {

    public static DateFormatUtils newInstance() {
        return new DateFormatUtils();
    }

    public String setDateFromServer(String stringDate) {
        /** "EEE, dd MMM yyyy HH:mm:ss z" */
        SimpleDateFormat sourceDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        Date date = null;
        try {
            date = sourceDate.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat targetDate = new SimpleDateFormat("EEE, dd MMM yyyy ,HH:mm:ss");

        return targetDate.format(date);
    }
}
