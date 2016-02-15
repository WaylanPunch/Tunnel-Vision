package com.way.tunnelvision.model.dao;


import android.database.sqlite.SQLiteDatabase;

import com.way.tunnelvision.model.MenuModel;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * {@inheritDoc}
 *
 * @see de.greenrobot.dao.AbstractDaoSession
 * <p>
 * Created by pc on 2016/2/15.
 */
public class DaoSession extends AbstractDaoSession {
    private final DaoConfig menuDaoConfig;
    //private final DaoConfig customerDaoConfig;
    //private final DaoConfig orderDaoConfig;

    private final MenuDao menuDao;
    //private final CustomerDao customerDao;
    //private final OrderDao orderDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        menuDaoConfig = daoConfigMap.get(MenuDao.class).clone();
        menuDaoConfig.initIdentityScope(type);

        //customerDaoConfig = daoConfigMap.get(CustomerDao.class).clone();
        //customerDaoConfig.initIdentityScope(type);

        //orderDaoConfig = daoConfigMap.get(OrderDao.class).clone();
        //orderDaoConfig.initIdentityScope(type);

        menuDao = new MenuDao(menuDaoConfig, this);
        //customerDao = new CustomerDao(customerDaoConfig, this);
        //orderDao = new OrderDao(orderDaoConfig, this);

        registerDao(MenuModel.class, menuDao);
        //registerDao(Customer.class, customerDao);
        //registerDao(Order.class, orderDao);
    }

    public void clear() {
        menuDaoConfig.getIdentityScope().clear();
        //customerDaoConfig.getIdentityScope().clear();
        //orderDaoConfig.getIdentityScope().clear();
    }

    public MenuDao getMenuDao() {
        return menuDao;
    }

//    public CustomerDao getCustomerDao() {
//        return customerDao;
//    }
//
//    public OrderDao getOrderDao() {
//        return orderDao;
//    }
}
