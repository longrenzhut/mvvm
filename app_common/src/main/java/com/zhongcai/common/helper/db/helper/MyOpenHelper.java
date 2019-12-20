package com.zhongcai.common.helper.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.zhongcai.base.Config;
import com.zhongcai.common.helper.db.DaoMaster;
import com.zhongcai.common.helper.db.UserModelDao;

import org.greenrobot.greendao.database.Database;

import java.io.File;

/**
 *  zhutao
 */
public class MyOpenHelper extends DaoMaster.OpenHelper {

    private boolean isUseLocation = false;
    private String dbName;

    public MyOpenHelper(Context context, String name,boolean isUseLocation) {
        super(context, name, null);
        this.isUseLocation = isUseLocation;
        this.dbName = name;
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        if (oldVersion < newVersion) {
            //升级过程需要升级的类，如果是两个，参照下面
            //MigrationHelper.getInstance().migrate(db, UserDao.class, TypeLevelDao.class);
            MigrationHelper.migrate(db, UserModelDao.class);
        }
    }

    /**
     * Environment.getExternalStorageDirectory().toString()
     * + "/highup/"
     * 获取缓存数据库db对象
     *    sqLiteDatabase = ctx.openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);
     */


    @Override
    public SQLiteDatabase getReadableDatabase() {
        if(isUseLocation){
            File f = new File(Config.path + dbName);
            if (f.exists())
                return SQLiteDatabase.openDatabase(f.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        }
        return super.getReadableDatabase();
    }


    @Override
    public SQLiteDatabase getWritableDatabase() {
        if(isUseLocation){
            File f = new File(Config.path + dbName);
            if (f.exists())
                return SQLiteDatabase.openOrCreateDatabase(f.getPath(), null);
        }
        return super.getWritableDatabase();
    }

}

