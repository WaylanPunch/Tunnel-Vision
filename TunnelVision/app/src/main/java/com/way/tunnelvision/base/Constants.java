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
    public static final int SCHEMA_VERSION = 1012;
    //public static final int SCHEMA_VERSION_NEW = 1001;

    //Bugly监控追踪
    public static final String  BUGLY_APPID = "900018056";
    public static final String  BUGLY_APPKEY = "Pb08l8XkjzZMtX5c";

    //YouMi广告
    public static final String YouMi_ID = "2b60c18b7e10fab4";
    public static final String YouMi_KEY = "46f38e0e7ac8754e";

    public static final String QQ_OPEN_APP_ID = "1105316728";
    public static final String QQ_OPEN_APP_KEY = "bxEcRvwdlRdRqeEI";
    public static final String QQ_AUTH_NICKNAME = "qq_auth_nickname";
    public static final String QQ_AUTH_GENDER = "qq_auth_gender";
    public static final String QQ_AUTH_FIGUREURL = "qq_auth_figureurl";

    /**
     * Description : 接口API的URL
     * Author : lauren
     * Email  : lauren.liuling@gmail.com
     * Blog   : http://www.liuling123.com
     * Date   : 15/12/13
     */
    public class NEWS {
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
        public static final int NEWS_TYPE_TOP = 0;
        // 新闻详情
        public static final String NEWS_DETAIL = HOST + "nc/article/";

        public static final String COMMENT_URL = HOST + "nc/article/list/";

        // 汽车
        public static final String CAR_ID = "T1348654060988";
        public static final String CAR_TITLE = "汽车";
        public static final String CAR_NAME = "car";
        public static final int NEWS_TYPE_CARS = 2;
        // 笑话
        public static final String JOKE_ID = "T1350383429665";
        public static final String JOKE_TITLE = "笑话";
        public static final String JOKE_NAME = "joke";
        public static final int NEWS_TYPE_JOKES = 3;
        // nba
        public static final String NBA_ID = "T1348649145984";
        public static final String NBA_TITLE = "NBA";
        public static final String NBA_NAME = "nba";
        public static final int NEWS_TYPE_NBA = 1;
        // 足球
        public static final String FOOTBALL_ID = "T1399700447917";
        public static final String FOOTBALL_TITLE = "足球";
        public static final String FOOTBALL_NAME = "football";
        public static final int NEWS_TYPE_FOOTBALL = 4;
        // 娱乐
        public static final String ENTERTAINMENT_ID = "T1348648517839";
        public static final String ENTERTAINMENT_TITLE = "娱乐";
        public static final String ENTERTAINMENT_NAME = "entertainment";
        public static final int NEWS_TYPE_ENTERTAINMENT = 5;
        // 体育
        public static final String SPORTS_ID = "T1348649079062";
        public static final String SPORTS_TITLE = "体育";
        public static final String SPORTS_NAME = "sports";
        public static final int NEWS_TYPE_SPORTS = 6;
        // 财经
        public static final String FINANCE_ID = "T1348648756099";
        public static final String FINANCE_TITLE = "财经";
        public static final String FINANCE_NAME = "finance";
        public static final int NEWS_TYPE_FINANCE = 7;
        // 科技
        public static final String SCIENCE_ID = "T1348649580692";
        public static final String SCIENCE_TITLE = "科技";
        public static final String SCIENCE_NAME = "science";
        public static final int NEWS_TYPE_SCIENCE = 8;
        // 电影
        public static final String MOVIE_ID = "T1348648650048";
        public static final String MOVIE_TITLE = "电影";
        public static final String MOVIE_NAME = "movie";
        public static final int NEWS_TYPE_MOVIE = 9;
        // 游戏
        public static final String GAME_ID = "T1348654151579";
        public static final String GAME_TITLE = "游戏";
        public static final String GAME_NAME = "game";
        public static final int NEWS_TYPE_GAME = 10;
        // 时尚
        public static final String FASHION_ID = "T1348650593803";
        public static final String FASHION_TITLE = "时尚";
        public static final String FASHION_NAME = "fashion";
        public static final int NEWS_TYPE_FASHION = 11;
        // 情感
        public static final String EMOTION_ID = "T1348650839000";
        public static final String EMOTION_TITLE = "情感";
        public static final String EMOTION_NAME = "emotion";
        public static final int NEWS_TYPE_EMOTION = 12;
        // 精选
        public static final String HITS_ID = "T1370583240249";
        public static final String HITS_TITLE = "精选";
        public static final String HITS_NAME = "hits";
        public static final int NEWS_TYPE_HITS = 13;
        // 电台
        public static final String RADIO_ID = "T1379038288239";
        public static final String RADIO_TITLE = "电台";
        public static final String RADIO_NAME = "radio";
        public static final int NEWS_TYPE_RADIO = 14;
        // 数码
        public static final String DIGITAL_ID = "T1348649776727";
        public static final String DIGITAL_TITLE = "数码";
        public static final String DIGITAL_NAME = "digital";
        public static final int NEWS_TYPE_DIGITAL = 15;
        // 移动
        public static final String MOBILE_ID = "T1351233117091";
        public static final String MOBILE_TITLE = "移动";
        public static final String MOBILE_NAME = "mobile";
        public static final int NEWS_TYPE_MOBILE = 16;
        // 彩票
        public static final String LOTTERY_ID = "T1356600029035";
        public static final String LOTTERY_TITLE = "彩票";
        public static final String LOTTERY_NAME = "lottery";
        public static final int NEWS_TYPE_LOTTERY = 17;
        // 教育
        public static final String EDUCATION_ID = "T1348654225495";
        public static final String EDUCATION_TITLE = "教育";
        public static final String EDUCATION_NAME = "education";
        public static final int NEWS_TYPE_EDUCATION = 18;
        // 论坛
        public static final String FORUM_ID = "T1349837670307";
        public static final String FORUM_TITLE = "论坛";
        public static final String FORUM_NAME = "forum";
        public static final int NEWS_TYPE_FORUM = 19;
        // 旅游
        public static final String TRAVEL_ID = "T1348654204705";
        public static final String TRAVEL_TITLE = "旅游";
        public static final String TRAVEL_NAME = "travel";
        public static final int NEWS_TYPE_TRAVEL = 20;
        // 手机
        public static final String PHONE_ID = "T1348649654285";
        public static final String PHONE_TITLE = "手机";
        public static final String PHONE_NAME = "phone";
        public static final int NEWS_TYPE_PHONE = 21;
        // 博客
        public static final String BLOG_ID = "T1349837698345";
        public static final String BLOG_TITLE = "博客";
        public static final String BLOG_NAME = "blog";
        public static final int NEWS_TYPE_BLOG = 22;
        // 社会
        public static final String SOCIETY_ID = "T1348648037603";
        public static final String SOCIETY_TITLE = "社会";
        public static final String SOCIETY_NAME = "society";
        public static final int NEWS_TYPE_SOCIETY = 23;
        // 家居
        public static final String HOUSE_ID = "T1348654105308";
        public static final String HOUSE_TITLE = "家居";
        public static final String HOUSE_NAME = "house";
        public static final int NEWS_TYPE_HOUSE = 24;
        // 暴雪游戏
        //public static final String BaoXueId = "T1397016069906";
        // 亲子
        public static final String BABY_ID = "T1397116135282";
        public static final String BABY_TITLE = "亲子";
        public static final String BABY_NAME = "baby";
        public static final int NEWS_TYPE_BABY = 25;
        // CBA
        public static final String CBA_ID = "T1348649475931";
        public static final String CBA_TITLE = "CBA";
        public static final String CBA_NAME = "cba";
        public static final int NEWS_TYPE_CBA = 26;
        // 消息
        //public static final String MsgId = "T1371543208049";
        // 军事
        public static final String MILITARY_ID = "T1348648141035";
        public static final String MILITARY_TITLE = "军事";
        public static final String MILITARY_NAME = "military";
        public static final int NEWS_TYPE_MILITARY = 27;
        // 北京
        public static final String Local = HOST + "nc/article/local/";

        public static final String BeiJingId = "5YyX5Lqs";






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
