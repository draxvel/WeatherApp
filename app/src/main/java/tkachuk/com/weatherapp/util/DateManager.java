package tkachuk.com.weatherapp.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateManager {
    private static SimpleDateFormat formatDayOfWeek = new SimpleDateFormat("EEEE");
    private static SimpleDateFormat formatTimeDate = new SimpleDateFormat("HH:mm dd-MMM-yyyy");

    public static String getDayOfWeek(int count){
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, count);
        dt = c.getTime();
        String weekday = formatDayOfWeek.format(dt);
        return weekday;
    }

    public static String getTimeFromUTC(long dt){
        Calendar calendar = utcToCalendar(dt);
        return calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE);
    }

    public static String getCurrentDateTime(){
        Calendar calendar = Calendar.getInstance();
        formatTimeDate.setCalendar(calendar);
        String dateFormatted = formatTimeDate.format(calendar.getTime());
        return dateFormatted;
    }

    private static Calendar utcToCalendar(long dt){
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(dt * 1000);
        return date;
    }
}
