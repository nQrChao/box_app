package com.zqhy.app.utils.sdcard;

import android.content.Context;
import android.os.Environment;

import com.zqhy.app.App;
import com.zqhy.app.newproject.R;

import java.io.File;


/**
 * @author Administrator
 * @date 2018/11/6
 */

public class SdCardManager {

    private static volatile SdCardManager instance;

    private SdCardManager() {
    }

    public static SdCardManager getInstance() {
        if (instance == null) {
            synchronized (SdCardManager.class) {
                if (instance == null) {
                    instance = new SdCardManager();
                }
            }
        }
        return instance;
    }


    private final String pathName = App.instance().getResources().getString(R.string.app_name);

    private final String apkDir = "saveApk";

    private final String imageDir = "image";

    private final String crashDir = "crash";

    /**
     * 获取内置SD卡路径
     *
     * @return
     */
    public String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }


    /**
     * 根目录路径
     *
     * @return /sdcard/Android/Data/packageName/File/pathName
     */
    private File getAndroidDataRootPath() {
        return App.instance().getExternalFilesDir(pathName);
    }

    /**
     * 下载App文件夹
     *
     * @return sdcard/Android/Data/packageName/File/pathName/saveApk
     */
    public File getDownloadApkDir() {
        File file = new File(getAndroidDataRootPath(), apkDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * image文件夹
     *
     * @return /sdcard/Android/Data/packageName/File/pathName/image
     */
    public File getImageDir() {
        File file = new File(getAndroidDataRootPath(), imageDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * crash文件夹
     *
     * @return /sdcard/Android/Data/packageName/File/pathName/crash
     */
    public File getCrashDir() {
        File file = new File(getAndroidDataRootPath(), crashDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * jsonData文件夹
     *
     * @return /sdcard/Android/Data/packageName/File/pathName/jsonData
     */
    public File getJsonDataDir() {
        File file = new File(getAndroidDataRootPath(), jsonDataDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }


    private final String jsonDataDir = "jsonData";
    private final String mainMenuDir = "mainMenu";

    /**
     * json数据缓存文件夹
     *
     * @param mContext
     * @return sdcard/Android/data/packageName/cache/jsonData
     */
    public File getJsonDataDir(Context mContext) {
        File file = new File(mContext.getExternalCacheDir(), jsonDataDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * json数据缓存文件夹
     *
     * @param mContext sdcard/Android/data/packageName/files/mainMenu
     * @return
     */
    public File getMainMenuDir(Context mContext) {
        return mContext.getExternalFilesDir(mainMenuDir);
    }


    /**
     * 保存主题样式文件夹
     *
     * @param mContext
     * @return /data/data/packageName/files/menu
     */
    public static File getFileMenuDir(Context mContext) {
        File file = mContext.getExternalFilesDir("menu");
        if (file != null && !file.exists()) {
            file.mkdirs();
        }
        return file;
    }

}
