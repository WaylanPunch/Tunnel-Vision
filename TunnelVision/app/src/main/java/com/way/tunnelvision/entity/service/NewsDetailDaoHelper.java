package com.way.tunnelvision.entity.service;

import com.way.tunnelvision.base.MainApp;
import com.way.tunnelvision.entity.dao.NewsDetailDao;
import com.way.tunnelvision.entity.model.NewsDetailModel;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by pc on 2016/3/7.
 */
public class NewsDetailDaoHelper implements GreenDaoHelperInterface {


    private static NewsDetailDaoHelper instance;
    private NewsDetailDao newsDetailDao;

    private NewsDetailDaoHelper() {
        try {
            newsDetailDao = MainApp.getDaoSession().getNewsDetailDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static NewsDetailDaoHelper getInstance() {
        if(instance == null) {
            instance = new NewsDetailDaoHelper();
        }
        return instance;
    }

    @Override
    public <T> void addData(T bean) {
        if(newsDetailDao != null && bean != null) {
            newsDetailDao.insertOrReplace((NewsDetailModel) bean);
        }
    }

    @Override
    public void deleteData(Long id) {
        if(newsDetailDao != null && -1 != id) {
            newsDetailDao.deleteByKey(id);
        }
    }

    @Override
    public NewsDetailModel getDataById(Long id) {
        if(newsDetailDao != null && -1 != id) {
            return newsDetailDao.load(id);
        }
        return null;
    }

    @Override
    public List getAllData() {
        if(newsDetailDao != null) {
            return newsDetailDao.loadAll();
        }
        return null;
    }

    public List getAllDataByDocId(String selection, String orderby) {
        if(newsDetailDao != null) {
            QueryBuilder<NewsDetailModel> qb = newsDetailDao.queryBuilder();
            qb.where(NewsDetailDao.Properties.NEWSDETAIL_DOCID.eq(selection));
            qb.orderAsc(NewsDetailDao.Properties.Id);// 排序依据
            return qb.list();
        }
        return null;
    }

    @Override
    public boolean hasKey(Long id) {
        if(newsDetailDao == null || -1 != id) {
            return false;
        }
        QueryBuilder<NewsDetailModel> qb = newsDetailDao.queryBuilder();
        qb.where(NewsDetailDao.Properties.Id.eq(id));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        if(newsDetailDao == null) {
            return 0;
        }

        QueryBuilder<NewsDetailModel> qb = newsDetailDao.queryBuilder();
        return qb.buildCount().count();
    }

    @Override
    public void deleteAll() {
        if(newsDetailDao != null) {
            newsDetailDao.deleteAll();
        }
    }

    public void testQueryBy() {
        List joes = newsDetailDao.queryBuilder()
                .where(NewsDetailDao.Properties.Id.eq(1))
                .orderAsc(NewsDetailDao.Properties.Id)
                .list();

        QueryBuilder<NewsDetailModel> qb = newsDetailDao.queryBuilder();
        qb.where(qb.or(NewsDetailDao.Properties.Id.gt(0),
                qb.and(NewsDetailDao.Properties.Id.eq(1),
                        NewsDetailDao.Properties.Id.eq(2))));

        qb.orderAsc(NewsDetailDao.Properties.Id);// 排序依据
        qb.list();
    }
}
