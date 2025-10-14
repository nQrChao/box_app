package com.zqhy.app.db;


import com.raizlabs.android.dbflow.annotation.Database;

/**
 * @author Administrator
 * @date 2018/11/11
 */

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {
    /**
     * 数据库名称
     */
    public static final String NAME = "AppDatabase";

    /**
     * 数据库版本号
     */
    public static final int VERSION = 3;
}
