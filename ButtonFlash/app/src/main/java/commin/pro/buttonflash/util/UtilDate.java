package commin.pro.buttonflash.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Commin on 2016-07-31.
 */
public class UtilDate {

    public static Long getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();

        return date.getTime();
    }

    public static Long getDivTime(long startTime, long endTime) {
        long asdf = (endTime - startTime);
        return (endTime - startTime) / 6000L;
    }

}

