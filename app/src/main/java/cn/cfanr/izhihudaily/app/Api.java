package cn.cfanr.izhihudaily.app;

/**
 * @author xifan
 * @time 2016/5/4
 * @desc
 */
public class Api {
    //api主机地址
    private static String host="http://news-at.zhihu.com/";

    /**
     * api启动页地址 http://news-at.zhihu.com/api/4/start-image/1080*1776
     * 320*432，480*728，720*1184，1080*1776
     */
    public static String url_launch=host+"api/4/start-image/";

    //最新消息
    public static String url_latest_news=host+"api/4/news/latest";

    //过往消息
    public static String url_before_news=host+"api/4/news/before/%s";

}
