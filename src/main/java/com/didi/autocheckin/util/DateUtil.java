package com.didi.autocheckin.util;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author didi
 */
public class DateUtil {
    /**
     * date类型进行格式化输出
     *
     * @param pattern
     * @return
     */
    public static String dateNow(String pattern) {
        Date date = new Date();
        if (pattern == null && "".equals(pattern)) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String dateString = formatter.format(date);
        return dateString;
    }
}
