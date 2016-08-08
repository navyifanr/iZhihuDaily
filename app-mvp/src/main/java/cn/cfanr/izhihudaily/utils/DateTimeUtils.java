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
     * @see Calendar#add(int, int)
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
        Date date=getDateBeforeOrAfter(1-n);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String result = sdf.format(date);
        return result;
    }

    /**
     * 时间戳转化为日期
     * @param timeStamp 毫秒
     */
    public static String timeStamp2Date(long timeStamp, String formats){
        String date = new SimpleDateFormat(formats).format(new Date(timeStamp));
        return date;
    }

    /**
     * 首页时间转换   20160523--->05月23日 星期一
     * @param date
     * @return
     */
    public static String convertDateTxt(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(format.parse(date));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        String week="";
        switch (calendar.get(Calendar.DAY_OF_WEEK)){
            case 1:
                week="日";
                break;
            case 2:
                week="一";
                break;
            case 3:
                week="二";
                break;
            case 4:
                week="三";
                break;
            case 5:
                week="四";
                break;
            case 6:
                week="五";
                break;
            case 7:
                week="六";
                break;
        }
        String result=month+"月"+day+"日 星期" +week;
        if(month<10){
            return "0"+result;
        }else{
            return result;
        }
    }
}
