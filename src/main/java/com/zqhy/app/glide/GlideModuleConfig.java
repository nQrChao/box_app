package com.zqhy.app.glide;

import android.content.Context;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;
import com.zqhy.app.config.Constants;

import java.io.File;

/**
 * @author Administrator
 * @date 2016/8/16
 */
public class GlideModuleConfig implements GlideModule {

    private static final String TAG = "GlideModuleConfig";

    public static final String DISK_CACHE_DIR = "Glide_cache";

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, Constants.MAX_CACHE_DISK_SIZE));
        builder.setMemoryCache(new LruResourceCache(Constants.MAX_CACHE_MEMORY_SIZE));
        builder.setBitmapPool(new LruBitmapPool(Constants.MAX_CACHE_MEMORY_SIZE));
        //builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, DISK_CACHE_DIR, 1024 * 1024 * 1024));

    }

    /**
     * Glide磁盘缓存目录
     *
     * @param context
     * @return
     */
    public static File getGlideDiskCachePath(Context context) {
        return new File(context.getExternalCacheDir(), DISK_CACHE_DIR);
    }

    //在这里注册ModelLoaders
    //可以在这里清除缓存什么的
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            glide.setMemoryCategory(MemoryCategory.NORMAL);
        }
    }
}
