package com.way.tunnelvision.entity.service;

import com.way.tunnelvision.base.MainApp;
import com.way.tunnelvision.entity.dao.ImageDao;
import com.way.tunnelvision.entity.model.ImageModel;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by pc on 2016/3/7.
 */
public class ImageDaoHelper implements GreenDaoHelperInterface {


    private static ImageDaoHelper instance;
    private ImageDao imageDao;

    private ImageDaoHelper() {
        try {
            imageDao = MainApp.getDaoSession().getImageDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ImageDaoHelper getInstance() {
        if(instance == null) {
            instance = new ImageDaoHelper();
        }
        return instance;
    }

    @Override
    public <T> void addData(T bean) {
        if(imageDao != null && bean != null) {
            imageDao.insertOrReplace((ImageModel) bean);
        }
    }

    @Override
    public void deleteData(Long id) {
        if(imageDao != null && -1 != id) {
            imageDao.deleteByKey(id);
        }
    }

    @Override
    public ImageModel getDataById(Long id) {
        if(imageDao != null && -1 != id) {
            return imageDao.load(id);
        }
        return null;
    }

    @Override
    public List getAllData() {
        if(imageDao != null) {
            return imageDao.loadAll();
        }
        return null;
    }

    public List getAllDataByDocId(String selection, String orderby) {
        if(imageDao != null) {
            QueryBuilder<ImageModel> qb = imageDao.queryBuilder();
            qb.where(ImageDao.Properties.IMAGE_TITLE.eq(selection));
            qb.orderAsc(ImageDao.Properties.Id);// 排序依据
            return qb.list();
        }
        return null;
    }

    @Override
    public boolean hasKey(Long id) {
        if(imageDao == null || -1 != id) {
            return false;
        }

        QueryBuilder<ImageModel> qb = imageDao.queryBuilder();
        qb.where(ImageDao.Properties.Id.eq(id));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        if(imageDao == null) {
            return 0;
        }

        QueryBuilder<ImageModel> qb = imageDao.queryBuilder();
        return qb.buildCount().count();
    }

    @Override
    public void deleteAll() {
        if(imageDao != null) {
            imageDao.deleteAll();
        }
    }

    public void testQueryBy() {
        List joes = imageDao.queryBuilder()
                .where(ImageDao.Properties.Id.eq(1))
                .orderAsc(ImageDao.Properties.Id)
                .list();

        QueryBuilder<ImageModel> qb = imageDao.queryBuilder();
        qb.where(qb.or(ImageDao.Properties.Id.gt(0),
                qb.and(ImageDao.Properties.Id.eq(1),
                        ImageDao.Properties.Id.eq(2))));

        qb.orderAsc(ImageDao.Properties.Id);// 排序依据
        qb.list();
    }
}
