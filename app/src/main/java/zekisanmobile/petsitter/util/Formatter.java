package zekisanmobile.petsitter.util;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatter {

    public static String formatDateToString(Date date) {
        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
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
}
