package com.way.tunnelvision.entity.service;

import com.way.tunnelvision.base.MainApp;
import com.way.tunnelvision.entity.dao.NewsDao;
import com.way.tunnelvision.entity.model.NewsModel;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by pc on 2016/3/7.
 */
public class NewsDaoHelper implements GreenDaoHelperInterface {


    private static NewsDaoHelper instance;
    private NewsDao newsDao;

    private NewsDaoHelper() {
        try {
            newsDao = MainApp.getDaoSession().getNewsDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static NewsDaoHelper getInstance() {
        if(instance == null) {
            instance = new NewsDaoHelper();
        }
        return instance;
    }

    @Override
    public <T> void addData(T bean) {
        if(newsDao != null && bean != null) {
            newsDao.insertOrReplace((NewsModel) bean);
        }
    }

    @Override
    public void deleteData(Long id) {
        if(newsDao != null && -1 != id) {
            newsDao.deleteByKey(id);
        }
    }

    @Override
    public NewsModel getDataById(Long id) {
        if(newsDao != null && -1 != id) {
            return newsDao.load(id);
        }
        return null;
    }

    @Override
    public List getAllData() {
        if(newsDao != null) {
            return newsDao.loadAll();
        }
        return null;
    }

    @Override
    public List getAllDataByDocId(String selection, String orderby) {
        if(newsDao != null) {
            QueryBuilder<NewsModel> qb = newsDao.queryBuilder();
            qb.where(NewsDao.Properties.NEWS_DOCID.eq(selection));
            qb.orderAsc(NewsDao.Properties.Id);// 排序依据
            return qb.list();
        }
        return null;
    }

    @Override
    public boolean hasKey(Long id) {
        if(newsDao == null || -1 != id) {
            return false;
        }

        QueryBuilder<NewsModel> qb = newsDao.queryBuilder();
        qb.where(NewsDao.Properties.Id.eq(id));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        if(newsDao == null) {
            return 0;
        }

        QueryBuilder<NewsModel> qb = newsDao.queryBuilder();
        return qb.buildCount().count();
    }

    @Override
    public void deleteAll() {
        if(newsDao != null) {
            newsDao.deleteAll();
        }
    }

    public void testQueryBy() {
        List joes = newsDao.queryBuilder()
                .where(NewsDao.Properties.Id.eq(1))
                .orderAsc(NewsDao.Properties.Id)
                .list();

        QueryBuilder<NewsModel> qb = newsDao.queryBuilder();
        qb.where(qb.or(NewsDao.Properties.Id.gt(0),
                qb.and(NewsDao.Properties.Id.eq(1),
                        NewsDao.Properties.Id.eq(2))));

        qb.orderAsc(NewsDao.Properties.Id);// 排序依据
        qb.list();
    }
}
