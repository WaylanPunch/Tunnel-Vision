package com.way.tunnelvision.entity.service;

import com.way.tunnelvision.base.MainApp;
import com.way.tunnelvision.entity.dao.ChannelDao;
import com.way.tunnelvision.entity.model.ChannelModel;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by pc on 2016/3/7.
 */
public class ChannelDaoHelper implements GreenDaoHelperInterface {


    private static ChannelDaoHelper instance;
    private ChannelDao channelDao;

    private ChannelDaoHelper() {
        try {
            channelDao = MainApp.getDaoSession().getChannelDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ChannelDaoHelper getInstance() {
        if(instance == null) {
            instance = new ChannelDaoHelper();
        }
        return instance;
    }

    @Override
    public <T> void addData(T bean) {
        if(channelDao != null && bean != null) {
            channelDao.insertOrReplace((ChannelModel) bean);
        }
    }

    public <T> void updateData(T bean) {
        if(channelDao != null && bean != null) {
            channelDao.update((ChannelModel) bean);
        }
    }

    @Override
    public void deleteData(Long id) {
        if(channelDao != null && -1 != id) {
            channelDao.deleteByKey(id);
        }
    }

    @Override
    public ChannelModel getDataById(Long id) {
        if(channelDao != null && -1 != id) {
            return channelDao.load(id);
        }
        return null;
    }

    @Override
    public List getAllData() {
        if(channelDao != null) {
            return channelDao.loadAll();
        }
        return null;
    }

    @Override
    public List getAllDataByDocId(String selection, String orderby) {
        if(channelDao != null) {
            QueryBuilder<ChannelModel> qb = channelDao.queryBuilder();
            qb.where(ChannelDao.Properties.Channel_Type.eq(selection));
            qb.orderAsc(ChannelDao.Properties.Id);// 排序依据
            return qb.list();
        }
        return null;
    }

    public List getAllDataByChosen() {
        if(channelDao != null) {
            QueryBuilder<ChannelModel> qb = channelDao.queryBuilder();
            qb.where(ChannelDao.Properties.Channel_Chosen.notEq(2));
            qb.orderAsc(ChannelDao.Properties.Id);// 排序依据
            return qb.list();
        }
        return null;
    }

    @Override
    public boolean hasKey(Long id) {
        if(channelDao == null || -1 != id) {
            return false;
        }

        QueryBuilder<ChannelModel> qb = channelDao.queryBuilder();
        qb.where(ChannelDao.Properties.Id.eq(id));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        if(channelDao == null) {
            return 0;
        }

        QueryBuilder<ChannelModel> qb = channelDao.queryBuilder();
        return qb.buildCount().count();
    }

    @Override
    public void deleteAll() {
        if(channelDao != null) {
            channelDao.deleteAll();
        }
    }

    public void testQueryBy() {
        List joes = channelDao.queryBuilder()
                .where(ChannelDao.Properties.Id.eq(1))
                .orderAsc(ChannelDao.Properties.Id)
                .list();

        QueryBuilder<ChannelModel> qb = channelDao.queryBuilder();
        qb.where(qb.or(ChannelDao.Properties.Id.gt(0),
                qb.and(ChannelDao.Properties.Id.eq(1),
                        ChannelDao.Properties.Id.eq(2))));

        qb.orderAsc(ChannelDao.Properties.Id);// 排序依据
        qb.list();
    }
}
