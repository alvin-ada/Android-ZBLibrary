package zblibrary.xscan.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class AppUtils {
    public static long getDateTimeLong(String date, String pattern) {
        long dateLong = 0L;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);

        try {
            dateLong = sdf.parse(date).getTime();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return dateLong;
    }

    public static String getDateTimeStr(long dateLong, String pattern) {
        String msgTimeStr = "";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);

        try {
            Date date = new Date(dateLong);
            msgTimeStr = sdf.format(date);
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return msgTimeStr;
    }
}
