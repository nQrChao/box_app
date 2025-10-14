package com.zqhy.app.glide;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.chaoji.im.glide.GlideApp;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.newproject.R;

import java.io.File;

/**
 * @author Administrator
 * @date 2018/11/18
 */

public class GlideUtils {

    public static void loadImage(Context mContext, String url, ImageView target, int resId) {
        if (checkActivityDestroyed(mContext)) {
            return;
        }
        GlideApp.with(mContext)
                .load(url)
//                .fitCenter()
                .transform(new GlideRoundTransformNew(mContext, 5))
                .placeholder(resId)
                .into(target);
    }

    public static void loadImage(Context mContext, String url, ImageView target, int resId,int radius) {
        if (checkActivityDestroyed(mContext)) {
            return;
        }
        GlideApp.with(mContext)
                .load(url)
//                .fitCenter()
                .transform(new GlideRoundTransformNew(mContext, radius))
                .placeholder(resId)
                .into(target);
    }


    /**
     * 加载普通image(with placeholder)
     *
     * @param mContext
     * @param icon
     * @param imageView
     * @param placeholderRes
     */
    public static void loadNormalImage(Context mContext, String icon, ImageView imageView, int placeholderRes) {
        if (checkActivityDestroyed(mContext)) {
            return;
        }
        GlideApp.with(mContext)
                .load(icon)
                //                .asBitmap()
                .centerCrop()
                .placeholder(placeholderRes)
                .into(imageView);
    }

    /**
     * 加载普通image (default placeholder)
     *
     * @param mContext
     * @param icon
     * @param imageView
     */
    public static void loadNormalImage(Context mContext, String icon, ImageView imageView) {
        loadNormalImage(mContext, icon, imageView, R.mipmap.ic_placeholder);
    }

    /**
     * 加载圆形image
     *
     * @param mContext
     * @param icon
     * @param imageView
     */
    public static void loadCircleImage(Context mContext, String icon, ImageView imageView) {
        loadCircleImage(mContext, icon, imageView, R.mipmap.ic_placeholder, 0, R.color.white);
    }


    /**
     * 加载圆形image
     *
     * @param mContext
     * @param icon
     * @param imageView
     * @param placeholderRes
     */
    public static void loadCircleImage(Context mContext, String icon, ImageView imageView, int placeholderRes) {
        loadCircleImage(mContext, icon, imageView, placeholderRes, 0, R.color.white);
    }


    /**
     * 加载圆形image
     *
     * @param mContext
     * @param icon
     * @param imageView
     * @param strokeWidth
     * @param resColor
     */
    public static void loadCircleImage(Context mContext, String icon, ImageView imageView, int placeholderRes, int strokeWidth, int resColor) {
        if (checkActivityDestroyed(mContext)) {
            return;
        }
        GlideApp.with(mContext)
                .load(icon)
                //                .asBitmap()
                .placeholder(placeholderRes)
                .transform(new GlideCircleTransform(mContext, (int) (strokeWidth * ScreenUtil.getScreenDensity(mContext)), ContextCompat.getColor(mContext, resColor)))
                .into(imageView);
    }


    /**
     * 加载圆角image
     *
     * @param mContext
     * @param icon
     * @param imageView
     */
    public static void loadRoundImage(Context mContext, String icon, ImageView imageView) {
        loadRoundImage(mContext, icon, imageView, R.mipmap.ic_placeholder);
    }


    /**
     * 加载圆角image
     *
     * @param mContext
     * @param icon
     * @param imageView
     * @param placeholderRes
     */
    public static void loadRoundImage(Context mContext, String icon, ImageView imageView, int placeholderRes) {
        loadRoundImage(mContext, icon, imageView, placeholderRes, 5);
    }

    /**
     * 加载游戏图标
     *
     * @param mContext
     * @param icon
     * @param imageView
     */
    public static void loadGameIcon(Context mContext, String icon, ImageView imageView) {
        loadRoundImage(mContext, icon, imageView, R.mipmap.ic_placeholder, 6);
    }


    /**
     * 加载圆角image
     *
     * @param mContext
     * @param icon
     * @param imageView
     * @param placeholderRes
     * @param radius
     */
    public static void loadRoundImage(Context mContext, String icon, ImageView imageView, int placeholderRes, int radius) {
        if (checkActivityDestroyed(mContext)) {
            return;
        }
        GlideApp.with(mContext)
                .load(icon)
                //                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new CenterCrop(), new GlideRoundTransform(mContext, radius))
                .placeholder(placeholderRes)
                .into(imageView);
    }


    /**
     * 加载本地图片
     *
     * @param url
     * @param imageView
     */
    public static void loadLocalImage(Context mContext, String url, ImageView imageView) {
        try {
            if (checkActivityDestroyed(mContext)) {
                return;
            }
            GlideApp.with(mContext)
                    .load(new File(url))
                    //                    .asBitmap()
                    .centerCrop()
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载本地资源图片
     *
     * @param res
     * @param imageView
     */
    public static void loadLocalResImage(Context mContext, int res, ImageView imageView) {
        try {
            if (checkActivityDestroyed(mContext)) {
                return;
            }
            GlideApp.with(mContext)
                    .load(res)
                    //                    .asBitmap()
                    .centerCrop()
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static boolean checkActivityDestroyed(Context mContext) {
        if (mContext instanceof Activity) {
            if (((Activity) mContext).isDestroyed()) {
                return true;
            }
        }
        return false;
    }
}
