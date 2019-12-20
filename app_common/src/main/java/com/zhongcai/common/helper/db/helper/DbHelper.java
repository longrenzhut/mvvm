package com.zhongcai.common.helper.db.helper;

import android.database.sqlite.SQLiteDatabase;

import com.zhongcai.base.base.application.BaseApplication;
import com.zhongcai.common.helper.db.DaoMaster;
import com.zhongcai.common.helper.db.DaoSession;

public class DbHelper {

    static class Holder{
        static  DbHelper dbHelper = new DbHelper();
    }

    public static DbHelper instance(){
        return  Holder.dbHelper;
    }

    private DaoSession mDaoSession;

    public void init(String dbname){
        MyOpenHelper helper = new MyOpenHelper(BaseApplication.app,dbname,false);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }


    public DaoSession getDaoSession() {
        return DbHelper.instance().mDaoSession;
    }

    private DaoSession mLocalDaoSession;

    //设置本地访问的数据库
    public void initLocal(String dbname){
        MyOpenHelper helper = new MyOpenHelper(BaseApplication.app,dbname,true);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster mDaoMaster = new DaoMaster(db);
        mLocalDaoSession = mDaoMaster.newSession();
    }

    public static DaoSession getLocalDaoSession() {
        return DbHelper.instance().mLocalDaoSession;
    }

}
