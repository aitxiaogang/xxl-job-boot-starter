package com.xiaogang.xxljobadminsdk.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyUtil {
    private static final String CRON_DATE_FORMAT = "ss mm HH dd MM ? yyyy";

    public static String getCron(final Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(CRON_DATE_FORMAT);
        String formatTimeStr = "";
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }
}
