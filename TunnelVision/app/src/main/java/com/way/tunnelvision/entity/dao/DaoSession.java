package com.way.tunnelvision.entity.dao;


import android.database.sqlite.SQLiteDatabase;

import com.way.tunnelvision.entity.model.ChannelModel;
import com.way.tunnelvision.entity.model.HeaderImageModel;
import com.way.tunnelvision.entity.model.ImageModel;
import com.way.tunnelvision.entity.model.MenuModel;
import com.way.tunnelvision.entity.model.NewsDetailModel;
import com.way.tunnelvision.entity.model.NewsModel;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * {@inheritDoc}
 *
 * @see de.greenrobot.dao.AbstractDaoSession
 * <p/>
 * Created by pc on 2016/2/15.
 */
public class DaoSession extends AbstractDaoSession {
    private final DaoConfig menuDaoConfig;
    private final DaoConfig channelDaoConfig;
    private final DaoConfig newsDaoConfig;
    private final DaoConfig newsDetailDaoConfig;
    private final DaoConfig imageDaoConfig;
    private final DaoConfig headerImageDaoConfig;

    private final MenuDao menuDao;
    private final ChannelDao channelDao;
    private final NewsDao newsDao;
    private final NewsDetailDao newsDetailDao;
    private final ImageDao imageDao;
    private final HeaderImageDao headerImageDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        menuDaoConfig = daoConfigMap.get(MenuDao.class).clone();
        menuDaoConfig.initIdentityScope(type);

        channelDaoConfig = daoConfigMap.get(ChannelDao.class).clone();
        channelDaoConfig.initIdentityScope(type);

        newsDaoConfig = daoConfigMap.get(NewsDao.class).clone();
        newsDaoConfig.initIdentityScope(type);

        newsDetailDaoConfig = daoConfigMap.get(NewsDetailDao.class).clone();
        newsDetailDaoConfig.initIdentityScope(type);

        imageDaoConfig = daoConfigMap.get(ImageDao.class).clone();
        imageDaoConfig.initIdentityScope(type);

        headerImageDaoConfig = daoConfigMap.get(HeaderImageDao.class).clone();
        headerImageDaoConfig.initIdentityScope(type);

        menuDao = new MenuDao(menuDaoConfig, this);
        channelDao = new ChannelDao(channelDaoConfig, this);
        newsDao = new NewsDao(newsDaoConfig, this);
        newsDetailDao = new NewsDetailDao(newsDetailDaoConfig, this);
        imageDao = new ImageDao(imageDaoConfig, this);
        headerImageDao = new HeaderImageDao(headerImageDaoConfig, this);

        registerDao(MenuModel.class, menuDao);
        registerDao(ChannelModel.class, channelDao);
        registerDao(NewsModel.class, newsDao);
        registerDao(NewsDetailModel.class, newsDetailDao);
        registerDao(ImageModel.class, imageDao);
        registerDao(HeaderImageModel.class, headerImageDao);
    }

    public void clear() {
        menuDaoConfig.getIdentityScope().clear();
        channelDaoConfig.getIdentityScope().clear();
        newsDaoConfig.getIdentityScope().clear();
        newsDetailDaoConfig.getIdentityScope().clear();
        imageDaoConfig.getIdentityScope().clear();
        headerImageDaoConfig.getIdentityScope().clear();
    }

    public MenuDao getMenuDao() {
        return menuDao;
    }

    public ChannelDao getChannelDao() {
        return channelDao;
    }

    public NewsDao getNewsDao() {
        return newsDao;
    }

    public NewsDetailDao getNewsDetailDao() {
        return newsDetailDao;
    }

    public ImageDao getImageDao() {
        return imageDao;
    }

    public HeaderImageDao getHeaderImageDao() {
        return headerImageDao;
    }
}
