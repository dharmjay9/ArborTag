package in.com.arbortag.Utils;

import android.text.TextUtils;
import android.text.format.DateUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by divum on 22/11/16.
 */


public class ArborTagUtils {
    public static String getDateAsString(String textDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = df.parse(textDate);
            return DateUtils.getRelativeTimeSpanString(date.getTime()).toString();
        } catch (ParseException e) {
            return "Now..";
        }
    }

    public static String getDateAsString(long textDate) {
        return DateUtils.getRelativeTimeSpanString(textDate).toString();

    }

    public static String getTitle(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        str = str.replace('_', ' ');
        str = String.valueOf(str.charAt(0)).toUpperCase() + str.substring(1);
        return str;
    }

    public static Calendar getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar;
    }

    public static Calendar getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }


    public static Calendar setDate(int field, int dateValue) {
        Calendar calendar = getStartOfDay(new Date());
        calendar.set(field, dateValue);
        return calendar;
    }

    public static Calendar addDate(int field, int dateValue) {
        Calendar calendar = getStartOfDay(new Date());
        calendar.add(field, dateValue);
        return calendar;
    }

    public static String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("HH:mm aa dd MMM, yyyy");
        return format.format(date);
    }

    public static long getTimestamp(Date time) {
        return new Timestamp(time.getTime()).getTime();
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
