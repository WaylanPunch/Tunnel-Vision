package com.way.tunnelvision.entity.service;

import android.content.Context;

import com.way.tunnelvision.base.Constants;
import com.way.tunnelvision.entity.model.ChannelModel;
import com.way.tunnelvision.entity.model.HeaderImageModel;
import com.way.tunnelvision.util.LogUtil;

/**
 * Created by pc on 2016/2/25.
 */
public class DatabaseUtil {
    private final static String TAG = DatabaseUtil.class.getName();

    private DatabaseUtil() {
        throw new AssertionError();
    }


    public static void initDataBase(Context context) {
        LogUtil.d(TAG, "initDataBase debug, start");
        ChannelDaoHelper channelDaoHelper = ChannelDaoHelper.getInstance();

        //int currentVersion = PreferencesUtil.getInt(MainApp.getContext(), Constants.PREFERENCE_KEY_DATABASE_VERSION_CURRENT);
        //if (currentVersion < Constants.SCHEMA_VERSION_NEW) {
        //MainApp.getDevOpenHelper().onUpgrade(MainApp.getSQLiteDatabase(), Constants.SCHEMA_VERSION, Constants.SCHEMA_VERSION_NEW);
        //}

        long channelTotalCount = channelDaoHelper.getTotalCount();
        LogUtil.d(TAG, "initDataBase debug, Channel Row Count = " + channelTotalCount);
        try {
            if (channelTotalCount == 0) {
                ChannelModel channel;
                channel = new ChannelModel(null,
                        Constants.NEWS.TOP_ID,
                        Constants.NEWS.TOP_TITLE,
                        Constants.NEWS.TOP_NAME,
                        Constants.NEWS.TOP_URL,
                        Constants.NEWS.NEWS_TYPE_TOP,
                        0);
                channelDaoHelper.addData(channel);
                channel = new ChannelModel(null,
                        Constants.NEWS.NBA_ID,
                        Constants.NEWS.NBA_TITLE,
                        Constants.NEWS.NBA_NAME,
                        Constants.NEWS.NBA_ID,
                        Constants.NEWS.NEWS_TYPE_NBA,
                        1);
                channelDaoHelper.addData(channel);
                channel = new ChannelModel(null,
                        Constants.NEWS.CAR_ID,
                        Constants.NEWS.CAR_TITLE,
                        Constants.NEWS.CAR_NAME,
                        Constants.NEWS.CAR_ID,
                        Constants.NEWS.NEWS_TYPE_CARS,
                        1);
                channelDaoHelper.addData(channel);
                channel = new ChannelModel(null,
                        Constants.NEWS.JOKE_ID,
                        Constants.NEWS.JOKE_TITLE,
                        Constants.NEWS.JOKE_NAME,
                        Constants.NEWS.JOKE_ID,
                        Constants.NEWS.NEWS_TYPE_JOKES,
                        1);
                channelDaoHelper.addData(channel);
                channel = new ChannelModel(null,
                        Constants.NEWS.FOOTBALL_ID,
                        Constants.NEWS.FOOTBALL_TITLE,
                        Constants.NEWS.FOOTBALL_NAME,
                        Constants.NEWS.FOOTBALL_ID,
                        Constants.NEWS.NEWS_TYPE_FOOTBALL,
                        2);
                channelDaoHelper.addData(channel);
                channel = new ChannelModel(null,
                        Constants.NEWS.ENTERTAINMENT_ID,
                        Constants.NEWS.ENTERTAINMENT_TITLE,
                        Constants.NEWS.ENTERTAINMENT_NAME,
                        Constants.NEWS.ENTERTAINMENT_ID,
                        Constants.NEWS.NEWS_TYPE_ENTERTAINMENT,
                        2);
                channelDaoHelper.addData(channel);
                channel = new ChannelModel(null,
                        Constants.NEWS.SPORTS_ID,
                        Constants.NEWS.SPORTS_TITLE,
                        Constants.NEWS.SPORTS_NAME,
                        Constants.NEWS.SPORTS_ID,
                        Constants.NEWS.NEWS_TYPE_SPORTS,
                        2);
                channelDaoHelper.addData(channel);
                channel = new ChannelModel(null,
                        Constants.NEWS.FINANCE_ID,
                        Constants.NEWS.FINANCE_TITLE,
                        Constants.NEWS.FINANCE_NAME,
                        Constants.NEWS.FINANCE_ID,
                        Constants.NEWS.NEWS_TYPE_FINANCE,
                        2);
                channelDaoHelper.addData(channel);
                channel = new ChannelModel(null,
                        Constants.NEWS.SCIENCE_ID,
                        Constants.NEWS.SCIENCE_TITLE,
                        Constants.NEWS.SCIENCE_NAME,
                        Constants.NEWS.SCIENCE_ID,
                        Constants.NEWS.NEWS_TYPE_SCIENCE,
                        2);
                channelDaoHelper.addData(channel);
                channel = new ChannelModel(null,
                        Constants.NEWS.MOVIE_ID,
                        Constants.NEWS.MOVIE_TITLE,
                        Constants.NEWS.MOVIE_NAME,
                        Constants.NEWS.MOVIE_ID,
                        Constants.NEWS.NEWS_TYPE_MOVIE,
                        2);
                channelDaoHelper.addData(channel);
                channel = new ChannelModel(null,
                        Constants.NEWS.GAME_ID,
                        Constants.NEWS.GAME_TITLE,
                        Constants.NEWS.GAME_NAME,
                        Constants.NEWS.GAME_ID,
                        Constants.NEWS.NEWS_TYPE_GAME,
                        2);
                channelDaoHelper.addData(channel);
                channel = new ChannelModel(null,
                        Constants.NEWS.FASHION_ID,
                        Constants.NEWS.FASHION_TITLE,
                        Constants.NEWS.FASHION_NAME,
                        Constants.NEWS.FASHION_ID,
                        Constants.NEWS.NEWS_TYPE_FASHION,
                        2);
                channelDaoHelper.addData(channel);
                channel = new ChannelModel(null,
                        Constants.NEWS.EMOTION_ID,
                        Constants.NEWS.EMOTION_TITLE,
                        Constants.NEWS.EMOTION_NAME,
                        Constants.NEWS.EMOTION_ID,
                        Constants.NEWS.NEWS_TYPE_EMOTION,
                        2);
                channelDaoHelper.addData(channel);
                channel = new ChannelModel(null,
                        Constants.NEWS.HITS_ID,
                        Constants.NEWS.HITS_TITLE,
                        Constants.NEWS.HITS_NAME,
                        Constants.NEWS.HITS_ID,
                        Constants.NEWS.NEWS_TYPE_HITS,
                        2);
                channelDaoHelper.addData(channel);
                channel = new ChannelModel(null,
                        Constants.NEWS.RADIO_ID,
                        Constants.NEWS.RADIO_TITLE,
                        Constants.NEWS.RADIO_NAME,
                        Constants.NEWS.RADIO_ID,
                        Constants.NEWS.NEWS_TYPE_RADIO,
                        2);
                channelDaoHelper.addData(channel);
                channel = new ChannelModel(null,
                        Constants.NEWS.DIGITAL_ID,
                        Constants.NEWS.DIGITAL_TITLE,
                        Constants.NEWS.DIGITAL_NAME,
                        Constants.NEWS.DIGITAL_ID,
                        Constants.NEWS.NEWS_TYPE_DIGITAL,
                        2);
                channelDaoHelper.addData(channel);
                channel = new ChannelModel(null,
                        Constants.NEWS.MOBILE_ID,
                        Constants.NEWS.MOBILE_TITLE,
                        Constants.NEWS.MOBILE_NAME,
                        Constants.NEWS.MOBILE_ID,
                        Constants.NEWS.NEWS_TYPE_MOBILE,
                        2);
                channelDaoHelper.addData(channel);
                channel = new ChannelModel(null,
                        Constants.NEWS.LOTTERY_ID,
                        Constants.NEWS.LOTTERY_TITLE,
                        Constants.NEWS.LOTTERY_NAME,
                        Constants.NEWS.LOTTERY_ID,
                        Constants.NEWS.NEWS_TYPE_LOTTERY,
                        2);
                channelDaoHelper.addData(channel);
                channel = new ChannelModel(null,
                        Constants.NEWS.EDUCATION_ID,
                        Constants.NEWS.EDUCATION_TITLE,
                        Constants.NEWS.EDUCATION_NAME,
                        Constants.NEWS.EDUCATION_ID,
                        Constants.NEWS.NEWS_TYPE_EDUCATION,
                        2);
                channelDaoHelper.addData(channel);
                channel = new ChannelModel(null,
                        Constants.NEWS.FORUM_ID,
                        Constants.NEWS.FORUM_TITLE,
                        Constants.NEWS.FORUM_NAME,
                        Constants.NEWS.FORUM_ID,
                        Constants.NEWS.NEWS_TYPE_FORUM,
                        2);
                channelDaoHelper.addData(channel);
                channel = new ChannelModel(null,
                        Constants.NEWS.TRAVEL_ID,
                        Constants.NEWS.TRAVEL_TITLE,
                        Constants.NEWS.TRAVEL_NAME,
                        Constants.NEWS.TRAVEL_ID,
                        Constants.NEWS.NEWS_TYPE_TRAVEL,
                        2);
                channelDaoHelper.addData(channel);
                channel = new ChannelModel(null,
                        Constants.NEWS.PHONE_ID,
                        Constants.NEWS.PHONE_TITLE,
                        Constants.NEWS.PHONE_NAME,
                        Constants.NEWS.PHONE_ID,
                        Constants.NEWS.NEWS_TYPE_PHONE,
                        2);
                channelDaoHelper.addData(channel);
                channel = new ChannelModel(null,
                        Constants.NEWS.BLOG_ID,
                        Constants.NEWS.BLOG_TITLE,
                        Constants.NEWS.BLOG_NAME,
                        Constants.NEWS.BLOG_ID,
                        Constants.NEWS.NEWS_TYPE_BLOG,
                        2);
                channelDaoHelper.addData(channel);
                channel = new ChannelModel(null,
                        Constants.NEWS.SOCIETY_ID,
                        Constants.NEWS.SOCIETY_TITLE,
                        Constants.NEWS.SOCIETY_NAME,
                        Constants.NEWS.SOCIETY_ID,
                        Constants.NEWS.NEWS_TYPE_SOCIETY,
                        2);
                channelDaoHelper.addData(channel);
                channel = new ChannelModel(null,
                        Constants.NEWS.HOUSE_ID,
                        Constants.NEWS.HOUSE_TITLE,
                        Constants.NEWS.HOUSE_NAME,
                        Constants.NEWS.HOUSE_ID,
                        Constants.NEWS.NEWS_TYPE_HOUSE,
                        2);
                channelDaoHelper.addData(channel);
                channel = new ChannelModel(null,
                        Constants.NEWS.BABY_ID,
                        Constants.NEWS.BABY_TITLE,
                        Constants.NEWS.BABY_NAME,
                        Constants.NEWS.BABY_ID,
                        Constants.NEWS.NEWS_TYPE_BABY,
                        2);
                channelDaoHelper.addData(channel);
                channel = new ChannelModel(null,
                        Constants.NEWS.CBA_ID,
                        Constants.NEWS.CBA_TITLE,
                        Constants.NEWS.CBA_NAME,
                        Constants.NEWS.CBA_ID,
                        Constants.NEWS.NEWS_TYPE_CBA,
                        2);
                channelDaoHelper.addData(channel);
                channel = new ChannelModel(null,
                        Constants.NEWS.MILITARY_ID,
                        Constants.NEWS.MILITARY_TITLE,
                        Constants.NEWS.MILITARY_NAME,
                        Constants.NEWS.MILITARY_ID,
                        Constants.NEWS.NEWS_TYPE_MILITARY,
                        2);
                channelDaoHelper.addData(channel);
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "initDataBase error", e);
        }

        HeaderImageDaoHelper headerImageDaoHelper = HeaderImageDaoHelper.getInstance();
        long headerImageTotalCount = headerImageDaoHelper.getTotalCount();
        LogUtil.d(TAG, "initDataBase debug, Header Image Row Count = " + headerImageTotalCount);
        try {
            if (headerImageTotalCount == 0) {
                String title = "Earth Art in Northwestern Australia";
                String link = "http://www.nasa.gov/image-feature/earth-art-in-northwestern-australia";
                String description = "During an International Space Station flyover of Australia, NASA astronaut Jeff Williams captured a colorful image of the coast and shared it with his social media followers on March 29, 2016, writing, &quot;The unique terrain of the northwestern Australian coast.&quot;";
                String url = "http://www.nasa.gov/sites/default/files/thumbnails/image/12909467_553362454846586_5546587330991507170_o.jpg";
                long length = 354172;
                String type = "image/jpeg";
                String guid = "http://www.nasa.gov/image-feature/earth-art-in-northwestern-australia";
                String pubDate = "Wed, 30 Mar 2016 11:20 EDT";
                int chosen = 0;
                HeaderImageModel headerImageModel = new HeaderImageModel(null, title, link, description, url, length, type, guid, pubDate, chosen);
                headerImageDaoHelper.addData(headerImageModel);
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "initDataBase error", e);
        }
        LogUtil.d(TAG, "initDataBase debug, end");
    }


}
