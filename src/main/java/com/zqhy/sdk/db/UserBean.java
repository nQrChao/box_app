package com.zqhy.sdk.db;

import android.provider.BaseColumns;

import java.io.Serializable;

/**
 *
 * @author Administrator
 * @date 2017/3/14
 */

public final class UserBean implements BaseColumns,Serializable {

//    public static final String MIME_DIR_PREFIX = "vnd.android.cursor.dir";
//    public static final String MIME_ITEM_PREFIX = "vnd.android.cursor.item";
//    public static final String MIME_ITEM = "vnd.example.people";
//
//    public static final String MIME_TYPE_SINGLE = MIME_ITEM_PREFIX + "/" + MIME_ITEM ;
//    public static final String MIME_TYPE_MULTIPLE = MIME_DIR_PREFIX + "/" + MIME_ITEM ;
//
//    public static final String AUTHORITY = "com.zqhy.zqsdk.provider";
//    public static final String PATH_SINGLE = "user/#";
//    public static final String PATH_MULTIPLE = "user";
//    public static final String CONTENT_URI_STRING = "content://" + AUTHORITY + "/" + PATH_MULTIPLE;
//    public static final Uri CONTENT_URI = Uri.parse(CONTENT_URI_STRING);


    // 表数据列
//    public static final String KEY_USERNAME = "username";
//    public static final String KEY_PASSWORD = "password";
//    public static final String KEY_TIME = "time";


    private String username;
    private String password;
    private long addTime;


    private String appid;
    public UserBean(String username, String password, long addTime) {
        this.username = username;
        this.password = password;
        this.addTime = addTime;
    }

    public UserBean(String username, String password, long addTime, String appid) {
        this.username = username;
        this.password = password;
        this.addTime = addTime;
        this.appid = appid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", addTime=" + addTime +
                ", appid='" + appid + '\'' +
                '}';
    }
}
