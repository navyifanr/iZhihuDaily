package cn.cfanr.izhihudaily.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author xifan
 * @time 2016/5/11
 * @desc
 */
public class DateTimeUtils {
    /**
     * 获取当前时间，年月日格式
     */
    public static String getCurrentYMDTime(){
        return getCurrentTime("yyyy-MM-dd");
    }

    public static String getCurrentTime(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        String currentTime = sdf.format(date);
        return currentTime;
    }

    /**
     * 得到系统当前日期的前或者后几天
     *
     * @param iDate
     *                如果要获得前几天日期，该参数为负数； 如果要获得后几天日期，该参数为正数
     * @see java.util.Calendar#add(int, int)
     * @return Date 返回系统当前日期的前或者后几天
     */
    public static Date getDateBeforeOrAfter(int iDate) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, iDate);
        return cal.getTime();
    }

    /**
     * 获取n天前特定日期格式
     */
    public static String getNDaysAgo(int n){
        Date date=getDateBeforeOrAfter(-n);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String result = sdf.format(date);
        return result;
    }
}
