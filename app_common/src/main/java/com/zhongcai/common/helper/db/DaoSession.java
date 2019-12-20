package com.zhongcai.common.helper.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.zhongcai.common.ui.model.UserModel;
import com.zhongcai.common.widget.optaddress.AddrItemModel;

import com.zhongcai.common.helper.db.UserModelDao;
import com.zhongcai.common.helper.db.AddrItemModelDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userModelDaoConfig;
    private final DaoConfig addrItemModelDaoConfig;

    private final UserModelDao userModelDao;
    private final AddrItemModelDao addrItemModelDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userModelDaoConfig = daoConfigMap.get(UserModelDao.class).clone();
        userModelDaoConfig.initIdentityScope(type);

        addrItemModelDaoConfig = daoConfigMap.get(AddrItemModelDao.class).clone();
        addrItemModelDaoConfig.initIdentityScope(type);

        userModelDao = new UserModelDao(userModelDaoConfig, this);
        addrItemModelDao = new AddrItemModelDao(addrItemModelDaoConfig, this);

        registerDao(UserModel.class, userModelDao);
        registerDao(AddrItemModel.class, addrItemModelDao);
    }
    
    public void clear() {
        userModelDaoConfig.clearIdentityScope();
        addrItemModelDaoConfig.clearIdentityScope();
    }

    public UserModelDao getUserModelDao() {
        return userModelDao;
    }

    public AddrItemModelDao getAddrItemModelDao() {
        return addrItemModelDao;
    }

}
