package com.zqhy.app.glide;

import android.content.Context;
import android.os.Looper;

import com.bumptech.glide.Glide;
import com.zqhy.app.utils.cache.FolderUtils;

/**
 * @author Administrator
 * @date 2018/5/16
 */

public class GlideCacheUtils {
    private static GlideCacheUtils inst;

    public static GlideCacheUtils getInstance() {
        if (inst == null) {
            inst = new GlideCacheUtils();
        }
        return inst;
    }

    /**
     * 清除图片磁盘缓存
     */
    public boolean clearImageDiskCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(context).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 清除图片内存缓存
     */
    public void clearImageMemoryCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                //只能在主线程执行
                Glide.get(context).clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Glide造成的缓存大小
     *
     * @return CacheSize
     */
    public String getCacheSize(Context context) {
        try {
            return FolderUtils.getFormatSize(FolderUtils.getFolderSize(GlideModuleConfig.getGlideDiskCachePath(context)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
