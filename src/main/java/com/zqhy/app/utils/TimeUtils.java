package com.zqhy.app.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2018/11/8.
 */

public class TimeUtils {

    /**
     * 时间戳转换
     *
     * @param ms    毫秒
     * @param regex 模式
     * @return 时间戳
     */
    public static String formatTimeStamp(long ms, String regex) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(regex);
        return simpleDateFormat.format(new Date(ms));
    }


    /**
     * 判断今天或者明天
     *
     * @param ms
     * @return -1 今天以前 0 今天 1 明日 2 明天以后
     */
    public static int isTodayOrTomorrow(long ms) {
        Calendar c = Calendar.getInstance();
        clearCalendar(c, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND);
        //今天最早的时间
        long firstOfToday = c.getTimeInMillis();
        //明天最早的时间
        long firstOfTomorrow = firstOfToday + (24 * 60 * 60 * 1000);
        //明天最晚的时间
        long endOfTomorrow = firstOfTomorrow + (24 * 60 * 60 * 1000);

        if (ms < firstOfToday) {
            return -1;
        } else if (ms >= firstOfToday && ms < firstOfTomorrow) {
            return 0;
        } else if (ms >= firstOfTomorrow && ms < endOfTomorrow) {
            return 1;
        } else {
            return 2;
        }
    }

    private static void clearCalendar(Calendar c, int... fields) {
        for (int f : fields) {
            c.set(f, 0);
        }
    }

}
