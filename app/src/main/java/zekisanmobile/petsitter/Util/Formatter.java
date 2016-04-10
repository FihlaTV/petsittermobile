package zekisanmobile.petsitter.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatter {

    public static String formattedDateFromString(String date) {
        try {
            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
            Date oldDate = input.parse(date);
            return output.format(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formattedDateForAPI(String date) {
        try {
            SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
            Date oldDate = input.parse(date);
            return output.format(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Date formattedDate(String date) {
        try {
            SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
            return output.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String formattedDateToSQL(Date date) {
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
        return output.format(date);
    }

    public static String formattedDateTimeToSQL(Date date) {
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return output.format(date);
    }
}
