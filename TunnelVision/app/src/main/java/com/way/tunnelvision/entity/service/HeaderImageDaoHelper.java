package com.way.tunnelvision.entity.service;

import com.way.tunnelvision.base.MainApp;
import com.way.tunnelvision.entity.dao.HeaderImageDao;
import com.way.tunnelvision.entity.model.HeaderImageModel;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by pc on 2016/3/7.
 */
public class HeaderImageDaoHelper implements GreenDaoHelperInterface {


    private static HeaderImageDaoHelper instance;
    private HeaderImageDao headerImageDao;

    private HeaderImageDaoHelper() {
        try {
            headerImageDao = MainApp.getDaoSession().getHeaderImageDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HeaderImageDaoHelper getInstance() {
        if(instance == null) {
            instance = new HeaderImageDaoHelper();
        }
        return instance;
    }

    @Override
    public <T> void addData(T bean) {
        if(headerImageDao != null && bean != null) {
            headerImageDao.insertOrReplace((HeaderImageModel) bean);
        }
    }

    @Override
    public void deleteData(Long id) {
        if(headerImageDao != null && -1 != id) {
            headerImageDao.deleteByKey(id);
        }
    }

    @Override
    public HeaderImageModel getDataById(Long id) {
        if(headerImageDao != null && -1 != id) {
            return headerImageDao.load(id);
        }
        return null;
    }

    @Override
    public List getAllData() {
        if(headerImageDao != null) {
            return headerImageDao.loadAll();
        }
        return null;
    }

    public List getAllDataByNumber(int number) {
        if(headerImageDao != null) {
            if(number < getTotalCount()){
                QueryBuilder<HeaderImageModel> qb = headerImageDao.queryBuilder();
                qb.orderAsc(HeaderImageDao.Properties.Id);// 排序依据
                return qb.list().subList(0, number);
            }
        }
        return null;
    }

    public List getAllDataByGUID(String selection, String orderby) {
        if(headerImageDao != null) {
            QueryBuilder<HeaderImageModel> qb = headerImageDao.queryBuilder();
            qb.where(HeaderImageDao.Properties.HEADERIMAGE_GUID.eq(selection));
            qb.orderAsc(HeaderImageDao.Properties.Id);// 排序依据
            return qb.list();
        }
        return null;
    }

    @Override
    public boolean hasKey(Long id) {
        if(headerImageDao == null || -1 != id) {
            return false;
        }

        QueryBuilder<HeaderImageModel> qb = headerImageDao.queryBuilder();
        qb.where(HeaderImageDao.Properties.Id.eq(id));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        if(headerImageDao == null) {
            return 0;
        }

        QueryBuilder<HeaderImageModel> qb = headerImageDao.queryBuilder();
        return qb.buildCount().count();
    }

    @Override
    public void deleteAll() {
        if(headerImageDao != null) {
            headerImageDao.deleteAll();
        }
    }

    public void testQueryBy() {
        List joes = headerImageDao.queryBuilder()
                .where(HeaderImageDao.Properties.Id.eq(1))
                .orderAsc(HeaderImageDao.Properties.Id)
                .list();

        QueryBuilder<HeaderImageModel> qb = headerImageDao.queryBuilder();
        qb.where(qb.or(HeaderImageDao.Properties.Id.gt(0),
                qb.and(HeaderImageDao.Properties.Id.eq(1),
                        HeaderImageDao.Properties.Id.eq(2))));

        qb.orderAsc(HeaderImageDao.Properties.Id);// 排序依据
        qb.list();
    }
}
