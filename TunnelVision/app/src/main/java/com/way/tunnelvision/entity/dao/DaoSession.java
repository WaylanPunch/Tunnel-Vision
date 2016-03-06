package com.way.tunnelvision.entity.dao;


import android.database.sqlite.SQLiteDatabase;

import com.way.tunnelvision.entity.model.ChannelModel;
import com.way.tunnelvision.entity.model.MenuModel;
import com.way.tunnelvision.entity.model.NewsDetailModel;

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
    private final DaoConfig channelDaoConfig;
    private final DaoConfig newsDetailDaoConfig;
    //private final DaoConfig orderDaoConfig;

    private final MenuDao menuDao;
    private final ChannelDao channelDao;
    private final NewsDetailDao newsDetailDao;
    //private final OrderDao orderDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        menuDaoConfig = daoConfigMap.get(MenuDao.class).clone();
        menuDaoConfig.initIdentityScope(type);

        channelDaoConfig = daoConfigMap.get(ChannelDao.class).clone();
        channelDaoConfig.initIdentityScope(type);

        newsDetailDaoConfig = daoConfigMap.get(NewsDetailDao.class).clone();
        newsDetailDaoConfig.initIdentityScope(type);

        //orderDaoConfig = daoConfigMap.get(OrderDao.class).clone();
        //orderDaoConfig.initIdentityScope(type);

        menuDao = new MenuDao(menuDaoConfig, this);
        channelDao = new ChannelDao(channelDaoConfig, this);
        newsDetailDao = new NewsDetailDao(newsDetailDaoConfig, this);
        //orderDao = new OrderDao(orderDaoConfig, this);

        registerDao(MenuModel.class, menuDao);
        registerDao(ChannelModel.class, channelDao);
        registerDao(NewsDetailModel.class, newsDetailDao);
        //registerDao(Order.class, orderDao);
    }

    public void clear() {
        menuDaoConfig.getIdentityScope().clear();
        channelDaoConfig.getIdentityScope().clear();
        newsDetailDaoConfig.getIdentityScope().clear();
        //orderDaoConfig.getIdentityScope().clear();
    }

    public MenuDao getMenuDao() {
        return menuDao;
    }

    public ChannelDao getChannelDao() {
        return channelDao;
    }

    public NewsDetailDao getNewsDetailDao() {
        return newsDetailDao;
    }
}
