package com.way.tunnelvision.entity.impl;

import com.way.tunnelvision.base.Constants;
import com.way.tunnelvision.entity.model.NewsDetailModel;
import com.way.tunnelvision.entity.model.NewsModel;
import com.way.tunnelvision.util.NewsJsonUtil;
import com.way.tunnelvision.util.OkHttpUtil;

import java.util.List;

/**
 * Created by pc on 2016/2/28.
 */
public class NewsModelImpl {
    private final static String TAG = NewsModelImpl.class.getName();

    /**
     * 加载新闻列表
     *
     * @param type
     * @param pageIndex
     * @param listener
     */
    public void loadNews(final int type, final int pageIndex, final OnLoadNewsListListener listener) {
        String url = getUrl(type, pageIndex);
        OkHttpUtil.ResultCallback<String> loadNewsCallback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                List<NewsModel> newsModelList = NewsJsonUtil.readJsonNewsModels(response, getID(type));
                listener.onSuccess(newsModelList);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("load news list failure.", e);
            }
        };
        OkHttpUtil.get(url, loadNewsCallback);
    }

    /**
     * 加载新闻详情
     *
     * @param docid
     * @param listener
     */
    public void loadNewsDetail(final String docid, final OnLoadNewsDetailListener listener) {
        String url = getDetailUrl(docid);
        OkHttpUtil.ResultCallback<String> loadNewsCallback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                NewsDetailModel newsDetailModel = NewsJsonUtil.readJsonNewsDetailModels(response, docid);
                listener.onSuccess(newsDetailModel);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("load news detail info failure.", e);
            }
        };
        OkHttpUtil.get(url, loadNewsCallback);
    }

    /**
     * 获取ID
     *
     * @param type
     * @return
     */
    private String getID(int type) {
        String id;
        switch (type) {
            case Constants.NEWS.NEWS_TYPE_TOP:
                id = Constants.NEWS.TOP_ID;
                break;
            case Constants.NEWS.NEWS_TYPE_NBA:
                id = Constants.NEWS.NBA_ID;
                break;
            case Constants.NEWS.NEWS_TYPE_CARS:
                id = Constants.NEWS.CAR_ID;
                break;
            case Constants.NEWS.NEWS_TYPE_JOKES:
                id = Constants.NEWS.JOKE_ID;
                break;
            case Constants.NEWS.NEWS_TYPE_FOOTBALL:
                id = Constants.NEWS.FOOTBALL_ID;
                break;
            case Constants.NEWS.NEWS_TYPE_ENTERTAINMENT:
                id = Constants.NEWS.ENTERTAINMENT_ID;
                break;
            case Constants.NEWS.NEWS_TYPE_SPORTS:
                id = Constants.NEWS.SPORTS_ID;
                break;
            case Constants.NEWS.NEWS_TYPE_FINANCE:
                id = Constants.NEWS.FINANCE_ID;
                break;
            case Constants.NEWS.NEWS_TYPE_SCIENCE:
                id = Constants.NEWS.SCIENCE_ID;
                break;
            case Constants.NEWS.NEWS_TYPE_MOVIE:
                id = Constants.NEWS.MOVIE_ID;
                break;
            case Constants.NEWS.NEWS_TYPE_GAME:
                id = Constants.NEWS.GAME_ID;
                break;
            case Constants.NEWS.NEWS_TYPE_FASHION:
                id = Constants.NEWS.FASHION_ID;
                break;
            case Constants.NEWS.NEWS_TYPE_EMOTION:
                id = Constants.NEWS.EMOTION_ID;
                break;
            case Constants.NEWS.NEWS_TYPE_HITS:
                id = Constants.NEWS.HITS_ID;
                break;
            case Constants.NEWS.NEWS_TYPE_RADIO:
                id = Constants.NEWS.RADIO_ID;
                break;
            case Constants.NEWS.NEWS_TYPE_DIGITAL:
                id = Constants.NEWS.DIGITAL_ID;
                break;
            case Constants.NEWS.NEWS_TYPE_MOBILE:
                id = Constants.NEWS.MOBILE_ID;
                break;
            case Constants.NEWS.NEWS_TYPE_LOTTERY:
                id = Constants.NEWS.LOTTERY_ID;
                break;
            case Constants.NEWS.NEWS_TYPE_EDUCATION:
                id = Constants.NEWS.EDUCATION_ID;
                break;
            case Constants.NEWS.NEWS_TYPE_FORUM:
                id = Constants.NEWS.FORUM_ID;
                break;
            case Constants.NEWS.NEWS_TYPE_TRAVEL:
                id = Constants.NEWS.TRAVEL_ID;
                break;
            case Constants.NEWS.NEWS_TYPE_PHONE:
                id = Constants.NEWS.PHONE_ID;
                break;
            case Constants.NEWS.NEWS_TYPE_BLOG:
                id = Constants.NEWS.BLOG_ID;
                break;
            case Constants.NEWS.NEWS_TYPE_SOCIETY:
                id = Constants.NEWS.SOCIETY_ID;
                break;
            case Constants.NEWS.NEWS_TYPE_HOUSE:
                id = Constants.NEWS.HOUSE_ID;
                break;
            case Constants.NEWS.NEWS_TYPE_BABY:
                id = Constants.NEWS.BABY_ID;
                break;
            case Constants.NEWS.NEWS_TYPE_CBA:
                id = Constants.NEWS.CBA_ID;
                break;
            case Constants.NEWS.NEWS_TYPE_MILITARY:
                id = Constants.NEWS.MILITARY_ID;
                break;
            default:
                id = Constants.NEWS.TOP_ID;
                break;
        }
        return id;
    }

    /**
     * 根据类别和页面索引创建url
     * @param type
     * @param pageIndex
     * @return
     */
    private String getUrl(int type, int pageIndex) {
        StringBuffer sb = new StringBuffer();
        switch (type) {
            case Constants.NEWS.NEWS_TYPE_TOP:
                sb.append(Constants.NEWS.TOP_URL).append(Constants.NEWS.TOP_ID);
                break;
            case Constants.NEWS.NEWS_TYPE_NBA:
                sb.append(Constants.NEWS.COMMENT_URL).append(Constants.NEWS.NBA_ID);
                break;
            case Constants.NEWS.NEWS_TYPE_CARS:
                sb.append(Constants.NEWS.COMMENT_URL).append(Constants.NEWS.CAR_ID);
                break;
            case Constants.NEWS.NEWS_TYPE_JOKES:
                sb.append(Constants.NEWS.COMMENT_URL).append(Constants.NEWS.JOKE_ID);
                break;
            case Constants.NEWS.NEWS_TYPE_FOOTBALL:
                sb.append(Constants.NEWS.COMMENT_URL).append(Constants.NEWS.FOOTBALL_ID);
                break;
            case Constants.NEWS.NEWS_TYPE_ENTERTAINMENT:
                sb.append(Constants.NEWS.COMMENT_URL).append(Constants.NEWS.ENTERTAINMENT_ID);
                break;
            case Constants.NEWS.NEWS_TYPE_SPORTS:
                sb.append(Constants.NEWS.COMMENT_URL).append(Constants.NEWS.SPORTS_ID);
                break;
            case Constants.NEWS.NEWS_TYPE_FINANCE:
                sb.append(Constants.NEWS.COMMENT_URL).append(Constants.NEWS.FINANCE_ID);
                break;
            case Constants.NEWS.NEWS_TYPE_SCIENCE:
                sb.append(Constants.NEWS.COMMENT_URL).append(Constants.NEWS.SCIENCE_ID);
                break;
            case Constants.NEWS.NEWS_TYPE_MOVIE:
                sb.append(Constants.NEWS.COMMENT_URL).append(Constants.NEWS.MOVIE_ID);
                break;
            case Constants.NEWS.NEWS_TYPE_GAME:
                sb.append(Constants.NEWS.COMMENT_URL).append(Constants.NEWS.GAME_ID);
                break;
            case Constants.NEWS.NEWS_TYPE_FASHION:
                sb.append(Constants.NEWS.COMMENT_URL).append(Constants.NEWS.FASHION_ID);
                break;
            case Constants.NEWS.NEWS_TYPE_EMOTION:
                sb.append(Constants.NEWS.COMMENT_URL).append(Constants.NEWS.EMOTION_ID);
                break;
            case Constants.NEWS.NEWS_TYPE_HITS:
                sb.append(Constants.NEWS.COMMENT_URL).append(Constants.NEWS.HITS_ID);
                break;
            case Constants.NEWS.NEWS_TYPE_RADIO:
                sb.append(Constants.NEWS.COMMENT_URL).append(Constants.NEWS.RADIO_ID);
                break;
            case Constants.NEWS.NEWS_TYPE_DIGITAL:
                sb.append(Constants.NEWS.COMMENT_URL).append(Constants.NEWS.DIGITAL_ID);
                break;
            case Constants.NEWS.NEWS_TYPE_MOBILE:
                sb.append(Constants.NEWS.COMMENT_URL).append(Constants.NEWS.MOBILE_ID);
                break;
            case Constants.NEWS.NEWS_TYPE_LOTTERY:
                sb.append(Constants.NEWS.COMMENT_URL).append(Constants.NEWS.LOTTERY_ID);
                break;
            case Constants.NEWS.NEWS_TYPE_EDUCATION:
                sb.append(Constants.NEWS.COMMENT_URL).append(Constants.NEWS.EDUCATION_ID);
                break;
            case Constants.NEWS.NEWS_TYPE_FORUM:
                sb.append(Constants.NEWS.COMMENT_URL).append(Constants.NEWS.FORUM_ID);
                break;
            case Constants.NEWS.NEWS_TYPE_TRAVEL:
                sb.append(Constants.NEWS.COMMENT_URL).append(Constants.NEWS.TRAVEL_ID);
                break;
            case Constants.NEWS.NEWS_TYPE_PHONE:
                sb.append(Constants.NEWS.COMMENT_URL).append(Constants.NEWS.PHONE_ID);
                break;
            case Constants.NEWS.NEWS_TYPE_BLOG:
                sb.append(Constants.NEWS.COMMENT_URL).append(Constants.NEWS.BLOG_ID);
                break;
            case Constants.NEWS.NEWS_TYPE_SOCIETY:
                sb.append(Constants.NEWS.COMMENT_URL).append(Constants.NEWS.SOCIETY_ID);
                break;
            case Constants.NEWS.NEWS_TYPE_HOUSE:
                sb.append(Constants.NEWS.COMMENT_URL).append(Constants.NEWS.HOUSE_ID);
                break;
            case Constants.NEWS.NEWS_TYPE_BABY:
                sb.append(Constants.NEWS.COMMENT_URL).append(Constants.NEWS.BABY_ID);
                break;
            case Constants.NEWS.NEWS_TYPE_CBA:
                sb.append(Constants.NEWS.COMMENT_URL).append(Constants.NEWS.CBA_ID);
                break;
            case Constants.NEWS.NEWS_TYPE_MILITARY:
                sb.append(Constants.NEWS.COMMENT_URL).append(Constants.NEWS.MILITARY_ID);
                break;
            default:
                sb.append(Constants.NEWS.TOP_URL).append(Constants.NEWS.TOP_ID);
                break;
        }
        sb.append("/").append(pageIndex).append(Constants.NEWS.END_URL);
        return sb.toString();
    }

    private String getDetailUrl(String docId) {
        StringBuffer sb = new StringBuffer(Constants.NEWS.NEWS_DETAIL);
        sb.append(docId).append(Constants.NEWS.END_DETAIL_URL);
        return sb.toString();
    }

    public interface OnLoadNewsListListener {
        void onSuccess(List<NewsModel> list);

        void onFailure(String msg, Exception e);
    }

    public interface OnLoadNewsDetailListener {
        void onSuccess(NewsDetailModel newsDetailModel);

        void onFailure(String msg, Exception e);
    }
}
