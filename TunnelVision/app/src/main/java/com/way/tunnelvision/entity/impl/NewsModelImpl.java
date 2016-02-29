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
