package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Centauro01 on 10/09/2015.
 */
public class DateHelper {

    private static DateHelper instance;

    public static void init(){
        if (instance == null) {
            instance = new DateHelper();
        }
    }

    public static DateHelper getInstance() {
        init();
        return instance;
    }

    public static String formatStringDate(String date){
        //Date date = new Date(clima.sys.sunrise *1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-6")); // give a timezone reference for formating (see comment at the bottom
        return sdf.format(date);
    }

    public static Date formatDate(String sdate){
        Date date = null;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // the format of your date
            sdf.setTimeZone(TimeZone.getTimeZone("GMT-6")); // give a timezone reference for formating (see comment at the bottom
            date = sdf.parse(sdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public static String formatTime(int time){
        Date date = new Date(time *1000L); // *1000 is to convert seconds to milliseconds
//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // the format of your date
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss"); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-6")); // give a timezone reference for formating (see comment at the bottom
        return sdf.format(date);
    }
}
