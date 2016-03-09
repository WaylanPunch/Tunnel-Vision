package com.way.tunnelvision.entity.service;

import com.way.tunnelvision.base.MainApp;
import com.way.tunnelvision.entity.dao.MenuDao;
import com.way.tunnelvision.entity.model.MenuModel;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by pc on 2016/3/7.
 */
public class MenuDaoHelper implements GreenDaoHelperInterface {


    private static MenuDaoHelper instance;
    private MenuDao menuDao;

    private MenuDaoHelper() {
        try {
            menuDao = MainApp.getDaoSession().getMenuDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MenuDaoHelper getInstance() {
        if(instance == null) {
            instance = new MenuDaoHelper();
        }
        return instance;
    }

    @Override
    public <T> void addData(T bean) {
        if(menuDao != null && bean != null) {
            menuDao.insertOrReplace((MenuModel) bean);
        }
    }

    @Override
    public void deleteData(Long id) {
        if(menuDao != null && -1 != id) {
            menuDao.deleteByKey(id);
        }
    }

    @Override
    public MenuModel getDataById(Long id) {
        if(menuDao != null && -1 != id) {
            return menuDao.load(id);
        }
        return null;
    }

    @Override
    public List getAllData() {
        if(menuDao != null) {
            return menuDao.loadAll();
        }
        return null;
    }

    @Override
    public List getAllDataByDocId(String selection, String orderby) {
        if(menuDao != null) {
            QueryBuilder<MenuModel> qb = menuDao.queryBuilder();
            qb.where(MenuDao.Properties.Menu_GUID.eq(selection));
            qb.orderAsc(MenuDao.Properties.Id);// 排序依据
            return qb.list();
        }
        return null;
    }

    @Override
    public boolean hasKey(Long id) {
        if(menuDao == null || -1 != id) {
            return false;
        }

        QueryBuilder<MenuModel> qb = menuDao.queryBuilder();
        qb.where(MenuDao.Properties.Id.eq(id));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        if(menuDao == null) {
            return 0;
        }

        QueryBuilder<MenuModel> qb = menuDao.queryBuilder();
        return qb.buildCount().count();
    }

    @Override
    public void deleteAll() {
        if(menuDao != null) {
            menuDao.deleteAll();
        }
    }

    public void testQueryBy() {
        List joes = menuDao.queryBuilder()
                .where(MenuDao.Properties.Id.eq(1))
                .orderAsc(MenuDao.Properties.Id)
                .list();

        QueryBuilder<MenuModel> qb = menuDao.queryBuilder();
        qb.where(qb.or(MenuDao.Properties.Id.gt(0),
                qb.and(MenuDao.Properties.Id.eq(1),
                        MenuDao.Properties.Id.eq(2))));

        qb.orderAsc(MenuDao.Properties.Id);// 排序依据
        qb.list();
    }
}
