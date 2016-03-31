package com.way.tunnelvision.base;

/**
 * Created by pc on 2016/1/16.
 */
public class Constants {
    //SharedPreferences
    public static final String PREFERENCE_NAME = "TunnelVisionPrefs";
    public static final String PREFERENCE_KEY_FIRST_ENTER = "First_Enter";
    //public static final String PREFERENCE_KEY_DATABASE_VERSION_CURRENT = "Database_Version_Current";

    //Key of Activity Bundle
    public static final String NEWSFRAGMENT_PARAMETER = "ChannelModel";
    public static final String NEWSDETAILACTIVITY_PARAMETER = "NewsDetailModel";
    public static final String ACTIVITY_PARAMETER = "ActivityParam";

    //SQLite Database
    public static final String  DATABASE_NAME = "TunnelVisionDB";
    public static final int SCHEMA_VERSION = 1011;
    //public static final int SCHEMA_VERSION_NEW = 1001;

    //Bugly监控追踪
    public static final String  BUGLY_APPID = "900018056";
    public static final String  BUGLY_APPKEY = "Pb08l8XkjzZMtX5c";

    //YouMi广告
    public static final String YouMi_ID = "2b60c18b7e10fab4";
    public static final String YouMi_KEY = "46f38e0e7ac8754e";


    /**
     * Description : 接口API的URL
     * Author : lauren
     * Email  : lauren.liuling@gmail.com
     * Blog   : http://www.liuling123.com
     * Date   : 15/12/13
     */
    public class NEWS {
        public static final int NEWS_TYPE_TOP = 0;
        public static final int NEWS_TYPE_NBA = 1;
        public static final int NEWS_TYPE_CARS = 2;
        public static final int NEWS_TYPE_JOKES = 3;
        //http://c.m.163.com/nc/article/headline/T1348647909107/0-5.html  头条

        public static final int PAGE_SIZE = 20;

        public static final String HOST = "http://c.m.163.com/";
        public static final String END_URL = "-" + PAGE_SIZE + ".html";
        public static final String END_DETAIL_URL = "/full.html";
        // 头条
        public static final String TOP_URL = HOST + "nc/article/headline/";
        public static final String TOP_ID = "T1348647909107";
        public static final String TOP_TITLE = "头条";
        public static final String TOP_NAME = "headline";
        // 新闻详情
        public static final String NEWS_DETAIL = HOST + "nc/article/";

        public static final String COMMENT_URL = HOST + "nc/article/list/";

        // 汽车
        public static final String CAR_ID = "T1348654060988";
        public static final String CAR_TITLE = "汽车";
        public static final String CAR_NAME = "car";
        // 笑话
        public static final String JOKE_ID = "T1350383429665";
        public static final String JOKE_TITLE = "笑话";
        public static final String JOKE_NAME = "joke";
        // nba
        public static final String NBA_ID = "T1348649145984";
        public static final String NBA_TITLE = "NBA";
        public static final String NBA_NAME = "nba";
        // 图片
        public static final String IMAGES_URL = "http://api.laifudao.com/open/tupian.json";

        // 天气预报url
        public static final String WEATHER = "http://wthrcdn.etouch.cn/weather_mini?city=";

        //百度定位
        public static final String INTERFACE_LOCATION = "http://api.map.baidu.com/geocoder";

        //标题图片
        public static final String HEADER_IMAGE_SOURCE = "http://www.nasa.gov/rss/dyn/lg_image_of_the_day.rss";
        public static final String HEADER_IMAGE_DEFAULT = "http://img0.ph.126.net/Hcnpoc0xeWdihdNzpY1l2g==/201817558402165569.jpg";
                //"https://raw.githubusercontent.com/WaylanPunch/Tunnel-Vision/master/Images/default_header_image.jpg";
    }

    public class RSSFEED {
        public static final String HEADER_IMAGE_TAG_TITLE = "title";
        public static final String HEADER_IMAGE_TAG_LINK = "link";
        public static final String HEADER_IMAGE_TAG_DESCRIPTION = "description";
        public static final String HEADER_IMAGE_TAG_ENCLOSURE = "enclosure";
        public static final String HEADER_IMAGE_TAG_ENCLOSURE_URL = "url";
        public static final String HEADER_IMAGE_TAG_ENCLOSURE_LENGTH = "length";
        public static final String HEADER_IMAGE_TAG_ENCLOSURE_TYPE = "type";
        public static final String HEADER_IMAGE_TAG_GUID = "guid";
        public static final String HEADER_IMAGE_TAG_PUBDATE = "pubDate";
    }
}
