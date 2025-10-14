package com.zqhy.sdk.db;

import android.os.Environment;
import android.text.TextUtils;

import com.zqhy.app.network.utils.AppUtils;
import com.zqhy.app.utils.cache.ACache;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/19
 */

public class SdkManager {

    private static volatile SdkManager instance;


    private SdkManager() {
    }

    public static SdkManager getInstance() {
        if (instance == null) {
            synchronized (SdkManager.class) {
                if (instance == null) {
                    instance = new SdkManager();
                }
            }
        }
        return instance;
    }

    /**
     * 获取内置SD卡路径
     *
     * @return
     */
    public String getInnerSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    private static final String UNITS = "data_cache_username";

    private static final String TARGET_TGID = "PLATFORM_APP_TGID";

    /**
     * 数据缓存文件夹
     *
     * @return
     */
    public File getDataCacheFileDir(String sdkTag) {
        File file = new File(new File(getInnerSDCardPath(), "zq_sdk"), "userCache");
        File childFile = new File(file, sdkTag);
        if (!childFile.exists()) {
            childFile.mkdirs();
        }
        return childFile;
    }


    /**
     * 缓存用户信息
     *
     * @param userBean
     */
    public void cacheUsers(UserBean userBean, String sdkTag) {
        String cacheNameList = ACache.get(getDataCacheFileDir(sdkTag)).getAsString(UNITS);
        if (TextUtils.isEmpty(cacheNameList)) {
            cacheNameList = "";
        }
        StringBuilder sb = new StringBuilder(cacheNameList);

        if (!cacheNameList.contains(userBean.getUsername())) {
            sb.append(userBean.getUsername() + "&");
        } else {
            userBean.setAddTime(System.currentTimeMillis());
        }
        ACache.get(getDataCacheFileDir(sdkTag)).put(UNITS, sb.toString());
        ACache.get(getDataCacheFileDir(sdkTag)).put(userBean.getUsername(), userBean);
    }

    /**
     * 缓存App的tgid
     */
    public void cacheAppTgid(String sdkTag){
        ACache.get(getDataCacheFileDir(sdkTag)).put(TARGET_TGID, AppUtils.getChannelFromApk());
    }


    /**
     * 删除用户
     *
     * @param username
     */
    public void deleteUser(String username, String sdkTag) {
        ACache.get(getDataCacheFileDir(sdkTag)).remove(username);
    }

    /**
     * 获取单个User
     *
     * @return
     */
    public UserBean getCacheUser(String username, String sdkTag) {
        return (UserBean) ACache.get(getDataCacheFileDir(sdkTag)).getAsObject(username);
    }

    /**
     * 获取全部User
     *
     * @return
     */
    public List<UserBean> getCacheUsers(String sdkTag) {
        List<UserBean> userBeanList = new ArrayList<>();
        String cacheNameList = ACache.get(getDataCacheFileDir(sdkTag)).getAsString(UNITS);
        if (TextUtils.isEmpty(cacheNameList)) {
            return userBeanList;
        }
        String[] names = cacheNameList.split("&", -1);
        for (int i = 0; i < names.length; i++) {
            UserBean userBean = getCacheUser(names[i], sdkTag);
            if (userBean != null) {
                userBeanList.add(userBean);
            }
        }
        Collections.sort(userBeanList, new Comparator<UserBean>() {
            /*
             * int compare(Student o1, Student o2) 返回一个基本类型的整型，
             * 返回负数表示：o1 小于o2，
             * 返回0 表示：o1和o2相等，
             * 返回正数表示：o1大于o2。
             */
            @Override
            public int compare(UserBean o1, UserBean o2) {
                if (o1.getAddTime() < o2.getAddTime()) {
                    return 1;
                }
                if (o1.getAddTime() == o2.getAddTime()) {
                    return 0;
                }
                return -1;
            }
        });

        return userBeanList;
    }
}
