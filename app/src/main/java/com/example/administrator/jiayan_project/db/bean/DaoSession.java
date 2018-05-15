package com.example.administrator.jiayan_project.db.bean;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.administrator.jiayan_project.db.bean.AddressBean;
import com.example.administrator.jiayan_project.db.bean.LikeBean;

import com.example.administrator.jiayan_project.db.bean.AddressBeanDao;
import com.example.administrator.jiayan_project.db.bean.LikeBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig addressBeanDaoConfig;
    private final DaoConfig likeBeanDaoConfig;

    private final AddressBeanDao addressBeanDao;
    private final LikeBeanDao likeBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        addressBeanDaoConfig = daoConfigMap.get(AddressBeanDao.class).clone();
        addressBeanDaoConfig.initIdentityScope(type);

        likeBeanDaoConfig = daoConfigMap.get(LikeBeanDao.class).clone();
        likeBeanDaoConfig.initIdentityScope(type);

        addressBeanDao = new AddressBeanDao(addressBeanDaoConfig, this);
        likeBeanDao = new LikeBeanDao(likeBeanDaoConfig, this);

        registerDao(AddressBean.class, addressBeanDao);
        registerDao(LikeBean.class, likeBeanDao);
    }
    
    public void clear() {
        addressBeanDaoConfig.clearIdentityScope();
        likeBeanDaoConfig.clearIdentityScope();
    }

    public AddressBeanDao getAddressBeanDao() {
        return addressBeanDao;
    }

    public LikeBeanDao getLikeBeanDao() {
        return likeBeanDao;
    }

}