package com.zqhy.app.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.zqhy.app.core.inner.OnCommonDialogClickListener;
import com.zqhy.app.core.tool.AppUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.network.utils.AppUtils;
import com.zqhy.app.newproject.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Administrator
 * @date 2018/11/12
 */

public class CommonUtils {

    /**
     * 隐藏手机号中间四位
     *
     * @param phoneNum 手机号
     * @return 13122223333 -->131****3333
     */
    public static String hideTelNum(String phoneNum) {
        if (phoneNum == null) {
            return "";
        }
        if (phoneNum.length() != 11) {
            return phoneNum;
        }
        String firstNum = phoneNum.substring(0, 3);
        String lastNum = phoneNum.substring(7, 11);

        return firstNum + "****" + lastNum;
    }


    public static String hideUserName(String username) {
        if (username == null) {
            return "";
        }
        if (username.length() < 6 || username.length() > 30) {
            return username;
        }

        String firstNum = username.substring(0, 2);

        String lastNum = username.substring(username.length() - 2, username.length());

        return firstNum + "****" + lastNum;
    }

    public static String getHidePhoneNumber(String phoneNumber) {
        return phoneNumber.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    public static String getHideIdCard(String idCard) {
        if (idCard == null) {
            return "";
        }
        return idCard.length() == 18 ? idCard.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1*****$2") :
                idCard.replaceAll("(\\d{4})\\d{8}(\\w{3})", "$1*****$2");
    }

    public static String getHideRealName(String name) {
        return replaceName(name, "*");
    }

    public static String replaceName(String name, String replacement) {
        String reg = ".{1}";
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(name);

        int i = 0;
        while (matcher.find()) {
            i++;
            if (i == 1) {
                continue;
            }
            matcher.appendReplacement(sb, replacement);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    /**
     * 判断一个字符是否是中文
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        // 根据字节码判断
        return c >= 0x4E00 && c <= 0x9FA5;
    }

    /**
     * 判断一个字符串是否含有中文
     *
     * @param str
     * @return
     */
    public static boolean isChinese(String str) {
        if (str == null) {
            return false;
        }
        for (char c : str.toCharArray()) {
            // 有一个中文字符就返回
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 复制文本
     *
     * @param mContext 上下文
     * @param string   文本
     * @return true 复制成功  false 复制失败
     */
    public static boolean copyString(Context mContext, String string) {
        if (TextUtils.isEmpty(string)) {
            return false;
        }
        ClipboardManager mClipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("simple text",
                string);
        mClipboard.setPrimaryClip(clip);
        return true;
    }

    /**
     * 时间戳转换
     *
     * @param ms    毫秒
     * @param regex 模式
     * @return 时间戳
     */
    public static String formatTimeStamp(long ms, String regex) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(regex);
        return simpleDateFormat.format(new Date(ms));
    }

    public static String convertMillisToCountdownTime(long milliseconds) {
        long currentTime = System.currentTimeMillis();
        long remainingMillis = milliseconds - currentTime;

        if (remainingMillis < 0) {
            // 如果指定时间已过，可根据需求选择是否特殊处理，这里简单返回00:00
            return "00:00";
        }

        long hours = TimeUnit.MILLISECONDS.toHours(remainingMillis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(remainingMillis) % 60;
        return String.format("%02d:%02d", hours, minutes);
    }

    /**
     * @param time
     * @return
     */
    public static String friendlyTime(Date time) {
        return friendlyTime(time.getTime());
    }

    /**
     * @param time
     * @return
     */
    public static String friendlyTime(long time) {
        //获取time距离当前的秒数
        int ct = (int) ((System.currentTimeMillis() - time) / 1000);

        if (ct == 0) {
            return "刚刚";
        }

        if (ct > 0 && ct < 60) {
            return ct + "秒前";
        }

        if (ct >= 60 && ct < 3600) {
            return Math.max(ct / 60, 1) + "分钟前";
        }
        if (ct >= 3600 && ct < 86400) {
            return ct / 3600 + "小时前";
        }
        if (ct >= 86400 && ct < 2592000) {
            //86400 * 30
            int day = ct / 86400;
            return day + "天前";
        }
        if (ct >= 2592000 && ct < 31104000) {
            //86400 * 30
            return ct / 2592000 + "月前";
        }
        return ct / 31104000 + "年前";
    }

    /**
     * @param time
     * @return
     */
    public static String friendlyTime2(Date time) {
        return friendlyTime2(time.getTime());

    }

    /**
     * @param time
     * @return
     */
    public static String friendlyTime2(long time) {

        Calendar c = Calendar.getInstance();
        clearCalendar(c, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND);
        long oneDayMs = 24 * 60 * 60 * 1000;
        //今天最早的时间
        long firstOfToday = c.getTimeInMillis();
        //昨天最早的时间
        long firstOfYesterday = firstOfToday - oneDayMs;
        //明天最早的时间
        long firstOfTomorrow = firstOfToday + oneDayMs;
        //后天最早的时间
        long firstOfTheDayAfterTomorrow = firstOfTomorrow + oneDayMs;


        int year = new Date().getYear() + 1900;
        c.clear();
        c.set(Calendar.YEAR, year);
        long firstOfYear = c.getTimeInMillis();

        c.clear();
        c.set(Calendar.YEAR, year + 1);
        long lastOfYear = c.getTimeInMillis();


        if (time >= firstOfToday && time < firstOfTomorrow) {
            //当天
            return formatTimeStamp(time, "今天 HH:mm");
        } else if (time >= firstOfYesterday && time < firstOfToday) {
            //昨天
            return formatTimeStamp(time, "昨天 HH:mm");
        } else if (time >= firstOfTomorrow && time < firstOfTheDayAfterTomorrow) {
            //明天
            return formatTimeStamp(time, "明天 HH:mm");
        } else if (time >= firstOfYear && time < lastOfYear) {
            //昨天以前，今年以内
            return formatTimeStamp(time, "MM-dd HH:mm");
        } else {
            return formatTimeStamp(time, "yyyy-MM-dd HH:mm");
        }
    }

    public static String friendlyTime3(long time) {

        Calendar c = Calendar.getInstance();
        clearCalendar(c, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND);
        long oneDayMs = 24 * 60 * 60 * 1000;
        //今天最早的时间
        long firstOfToday = c.getTimeInMillis();
        //昨天最早的时间
        long firstOfYesterday = firstOfToday - oneDayMs;
        //明天最早的时间
        long firstOfTomorrow = firstOfToday + oneDayMs;
        //后天最早的时间
        long firstOfTheDayAfterTomorrow = firstOfTomorrow + oneDayMs;


        int year = new Date().getYear() + 1900;
        c.clear();
        c.set(Calendar.YEAR, year);
        long firstOfYear = c.getTimeInMillis();

        c.clear();
        c.set(Calendar.YEAR, year + 1);
        long lastOfYear = c.getTimeInMillis();


        if (time >= firstOfToday && time < firstOfTomorrow) {
            //当天
            return formatTimeStamp(time, "HH:mm 首发");
        } else if (time >= firstOfYesterday && time < firstOfToday) {
            //昨天
            return formatTimeStamp(time, "昨天 HH:mm");
        } else if (time >= firstOfTomorrow && time < firstOfTheDayAfterTomorrow) {
            //明天
            return formatTimeStamp(time, "明天 HH:mm");
        } else if (time >= firstOfYear && time < lastOfYear) {
            //昨天以前，今年以内
            return formatTimeStamp(time, "MM-dd HH:mm");
        } else {
            return formatTimeStamp(time, "yyyy-MM-dd HH:mm");
        }
    }

    public static String friendlyTime4(long time) {

        Calendar c = Calendar.getInstance();
        clearCalendar(c, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND);
        long oneDayMs = 24 * 60 * 60 * 1000;
        //今天最早的时间
        long firstOfToday = c.getTimeInMillis();
        //昨天最早的时间
        long firstOfYesterday = firstOfToday - oneDayMs;
        //明天最早的时间
        long firstOfTomorrow = firstOfToday + oneDayMs;
        //后天最早的时间
        long firstOfTheDayAfterTomorrow = firstOfTomorrow + oneDayMs;


        int year = new Date().getYear() + 1900;
        c.clear();
        c.set(Calendar.YEAR, year);
        long firstOfYear = c.getTimeInMillis();

        c.clear();
        c.set(Calendar.YEAR, year + 1);
        long lastOfYear = c.getTimeInMillis();


        if (time >= firstOfToday && time < firstOfTomorrow) {
            //当天
            if (time >= System.currentTimeMillis()){
                return formatTimeStamp(time, "今日HH:mm首发");
            }else {
                return formatTimeStamp(time, "今日HH:mm已上线");
            }
        }else if (time >= firstOfTomorrow && time < firstOfTheDayAfterTomorrow) {
            //明天
            return formatTimeStamp(time, "明天HH:mm上线");
        } else if (time > firstOfToday) {
            return formatTimeStamp(time, "yyyy年MM月dd日上线");
        }  else {
            return formatTimeStamp(time, "yyyy年MM月dd日已上线");
        }
    }

    private static void clearCalendar(Calendar c, int... fields) {
        for (int f : fields) {
            c.set(f, 0);
        }
    }

    /**
     * 获取今天时间最早的ms
     *
     * @return
     */
    public static long getTodayStartMs() {
        Calendar c = Calendar.getInstance();
        clearCalendar(c, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND);
        return c.getTimeInMillis();
    }

    /**
     * 判断今天或者明天
     *
     * @param ms
     * @return -1 今天以前 0 今天 1 明日 2 明天以后
     */
    public static int isTodayOrTomorrow(long ms) {
        long firstOfToday = getTodayStartMs();//今天最早的时间
        long firstOfTomorrow = firstOfToday + (24 * 60 * 60 * 1000);//明天最早的时间
        long endOfTomorrow = firstOfTomorrow + (24 * 60 * 60 * 1000);//明天最晚的时间

        if (ms < firstOfToday) {
            return -1;
        } else if (ms >= firstOfToday && ms < firstOfTomorrow) {
            return 0;
        } else if (ms >= firstOfTomorrow && ms < endOfTomorrow) {
            return 1;
        } else {
            return 2;
        }
    }


    /**
     * /assets/json
     *
     * @param mContext
     * @param fileName
     * @return
     */
    public static String getJsonDataFromAsset(Context mContext, String fileName) {
        try {
            InputStream is = mContext.getAssets().open(fileName);
            int length = is.available();
            byte[] buffer = new byte[length];
            is.read(buffer);
            String result = new String(buffer, "utf8");
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 设置模糊背景
     * <p>
     * https://github.com/robinxdroid/BlurView
     *
     * @param mImageView
     * @param mBitmap
     */
    public static void setBlurImage(ImageView mImageView, Bitmap mBitmap) {
        if (mBitmap != null && mImageView.getVisibility() == View.VISIBLE) {

            ByteArrayOutputStream dataByte = new ByteArrayOutputStream();
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, dataByte);
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inSampleSize = 4;
            opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap mTargetBitmap = BitmapFactory.decodeByteArray(dataByte.toByteArray(), 0, dataByte.size(), opts);

            //Bitmap finalBitmap = NdkStackBlurProcessor.INSTANCE.process(mTargetBitmap, 20);

            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageView.setImageBitmap(mTargetBitmap);
        }
    }


    /**
     * 验证游戏下载地址合法性
     *
     * @param downloadUrl
     * @return
     */
    public static boolean downloadUrlVerification(String downloadUrl) {
        if (TextUtils.isEmpty(downloadUrl)) {
            return false;
        } else if (downloadUrl.startsWith("http://") || downloadUrl.startsWith("https://")) {
            return true;
        }
        return false;
    }


    /**
     * 舍取float
     *
     * @param ft
     * @param scale 小数点位数
     * @param type  舍值方式          BigDecimal.ROUND_DOWN
     * @return
     */
    public static float saveFloatPoint(float ft, int scale, int type) {
        try {
            //表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
            BigDecimal bd = new BigDecimal((double) ft);
            bd = bd.setScale(scale, type);
            ft = bd.floatValue();
        } catch (Exception e) {
            e.printStackTrace();
            return ft;
        }
        return ft;
    }

    /**
     * 保留2位小数
     *
     * @param d
     * @return
     */
    public static String saveTwoSizePoint(double d) {
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        return decimalFormat.format(d);
    }


    /**
     * 过滤BrowserActivity url;
     */
    public static String filterUrlWithUidAndToken(String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        AppUtil.UrlEntity urlEntity = AppUtil.parse(url);
        if (urlEntity.params == null) {
            urlEntity.params = new HashMap<>();
        }
        int userUid = 0;
        String userUsername = "";
        String userToken = "";
        if (UserInfoModel.getInstance().isLogined()) {
            userUid = UserInfoModel.getInstance().getUserInfo().getUid();
            userUsername = UserInfoModel.getInstance().getUserInfo().getUsername();
            userToken = UserInfoModel.getInstance().getUserInfo().getToken();
        }
        urlEntity.params.put("uid", String.valueOf(userUid));
        urlEntity.params.put("username", String.valueOf(userUsername));
        urlEntity.params.put("token", String.valueOf(userToken));
        urlEntity.params.put("tgid", AppUtils.getTgid());
        urlEntity.params.put("oldtgid", AppUtils.getChannelFromApk());

        if (!TextUtils.isEmpty(urlEntity.endUrl)){
            return urlEntity.getEntireUrl() + urlEntity.endUrl;
        }
        return urlEntity.getEntireUrl();
    }


    /**
     * 压缩Bitmap到指定大小
     *
     * @param bitmap
     * @param maxSize 单位KB
     * @return
     */
    public static Bitmap compressBitmapUnderSize(Bitmap bitmap, long maxSize) {
        if (bitmap == null) {
            return bitmap;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int bitmapSize = baos.toByteArray().length;
        if (bitmapSize <= 0 || bitmapSize / 1024 < maxSize) {
            return bitmap;
        }
        int options = 90;
        while (bitmapSize / 1024 > maxSize) {
            // 重置baos即清空baos
            baos.reset();
            // 这里压缩options%,把压缩后的数据存放到baos中
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            // 每次都减少10
            options -= 10;
            bitmapSize = baos.toByteArray().length;
        }
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // 把ByteArrayInputStream数据生成图片
        Bitmap image = BitmapFactory.decodeStream(isBm, null, null);

        return image;
    }


    /**
     * 压缩Bitmap到指定大小
     *
     * @param bitmap
     * @param maxSize 单位KB
     * @return
     */
    public static byte[] compressBitmapByteUnderSize(Bitmap bitmap, long maxSize) {
        if (bitmap == null) {
            return new byte[]{};
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int bitmapSize = baos.toByteArray().length;
        if (bitmapSize <= 0 || bitmapSize < maxSize * 1024) {
            return new byte[]{};
        }
        int options = 90;
        while (bitmapSize > maxSize * 1024) {
            // 重置baos即清空baos
            baos.reset();
            // 这里压缩options%,把压缩后的数据存放到baos中
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            // 每次都减少10
            options -= 10;
            bitmapSize = baos.toByteArray().length;
        }
        return baos.toByteArray();
    }


    /**
     * 设置AlertDialog 文字大小
     *
     * @param builder
     * @param titleTextSize
     * @param messageTextSize
     */
    public static void setAlertDialogTextSize(AlertDialog builder, int titleTextSize, int messageTextSize) {
        try {

            //获取mAlert对象
            Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
            mAlert.setAccessible(true);
            Object mAlertController = mAlert.get(builder);

            //获取mTitleView并设置大小颜色
            Field mTitle = mAlertController.getClass().getDeclaredField("mTitleView");
            mTitle.setAccessible(true);
            TextView mTitleView = (TextView) mTitle.get(mAlertController);
            mTitleView.setTextSize(titleTextSize);

            //获取mMessageView并设置大小颜色
            Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
            mMessage.setAccessible(true);
            TextView mMessageView = (TextView) mMessage.get(mAlertController);
            mMessageView.setTextSize(messageTextSize);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置AlertDialog 文字颜色
     *
     * @param builder
     * @param titleTextColor
     * @param messageTextColor
     */
    public static void setAlertDialogTextColor(AlertDialog builder, int titleTextColor, int messageTextColor) {
        try {
            //获取mAlert对象
            Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
            mAlert.setAccessible(true);
            Object mAlertController = mAlert.get(builder);

            //获取mTitleView并设置大小颜色
            Field mTitle = mAlertController.getClass().getDeclaredField("mTitleView");
            mTitle.setAccessible(true);
            TextView mTitleView = (TextView) mTitle.get(mAlertController);
            mTitleView.setTextColor(titleTextColor);

            //获取mMessageView并设置大小颜色
            Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
            mMessage.setAccessible(true);
            TextView mMessageView = (TextView) mMessage.get(mAlertController);
            mMessageView.setTextColor(messageTextColor);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 创建通用Dialog
     *
     * @param mContext
     * @param title
     * @param message
     * @param mStrCancelText
     * @param mStrConfirmText
     * @param cancelTextColor
     * @param confirmTextColor
     * @param onCommonDialogClickListener
     * @return
     */
    public static CustomDialog createCommonDialog(Context mContext, String title, CharSequence message,
                                                  String mStrCancelText, String mStrConfirmText,
                                                  int cancelTextColor, int confirmTextColor,
                                                  OnCommonDialogClickListener onCommonDialogClickListener) {
        CustomDialog dialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_common, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

        TextView mTvDialogTitle = dialog.findViewById(R.id.tv_dialog_title);
        TextView mTvDialogMessage = dialog.findViewById(R.id.tv_dialog_message);
        TextView mTvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView mTvConfirm = dialog.findViewById(R.id.tv_confirm);

        mTvDialogTitle.setText(title);
        mTvDialogMessage.setText(message);

        mTvCancel.setTextColor(cancelTextColor);
        mTvConfirm.setTextColor(confirmTextColor);

        mTvCancel.setText(mStrCancelText);
        mTvConfirm.setText(mStrConfirmText);

        mTvCancel.setOnClickListener(v -> {
            if (onCommonDialogClickListener != null) {
                onCommonDialogClickListener.onCancel(dialog);
            }
        });

        mTvConfirm.setOnClickListener(v -> {
            if (onCommonDialogClickListener != null) {
                onCommonDialogClickListener.onConfirm(dialog);
            }
        });
        return dialog;
    }

    /**
     * 格式化金额 ###,###,###
     *
     * @param amount
     * @return
     */
    public static String formatAmount(long amount) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        return decimalFormat.format(amount);
    }

    /**
     * 获取当前日期
     *
     * @return 例如 20190617
     */
    public static int getCurrentDate() {
        int year, month, day, hour, minute, second;
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DATE);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
        second = cal.get(Calendar.SECOND);

        StringBuilder sb = new StringBuilder();
        sb.append(year);
        if (month < 10) {
            sb.append("0");
        }
        sb.append(month);
        if (day < 10) {
            sb.append("0");
        }
        sb.append(day);
        int date = 0;
        try {
            date = Integer.parseInt(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * ①万以下：显示实际数字0、99、999、9999
     * ②万以上：1万、1.2万
     *
     * @param number
     * @return
     */
    public static String formatNumber(long number) {
        if (number < 10000) {
            return String.valueOf(number);
        } else {
            double d = number / 10000d;
            DecimalFormat df = new DecimalFormat("#.0");
            return df.format(d) + "万";
        }
    }

    /**
     * ①万以下：显示实际数字0、99、999、9999
     * ②万以上：1w、1.2w
     *
     * @param number
     * @return
     */
    public static String formatNumberType2(long number) {
        if (number < 10000) {
            return String.valueOf(number);
        } else {
            double d = number / 10000d;
            DecimalFormat df = new DecimalFormat("#.0");
            return df.format(d) + "w";
        }
    }

    /**
     * 打开App
     * 新浪微博：
     * com.sina.weibo
     * <p>
     * 腾讯微博：
     * com.tencent.WBlog
     * <p>
     * 微信：
     * com.tencent.mm
     * <p>
     * QQ:
     * com.tencent.mobileqq
     */
    public static boolean openApp(Context mContext, String packageName) {
        PackageInfo packageinfo = null;
        try {
            packageinfo = mContext.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (packageinfo == null) {
            return false;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = mContext.getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String pckName = resolveinfo.activityInfo.packageName;

            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(pckName, className);

            intent.setComponent(cn);
            mContext.startActivity(intent);
            return true;
        }
        return false;
    }

    public static String timeStamp2Date(long time, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }

    /**
     * 判断给定时间戳时间是否为今日
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(long sdate){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(new Date(sdate));
        String today = format.format(new Date(System.currentTimeMillis()));
        if(time.equals(today)){
            return true;
        }
        return false;
    }

    public static String getGamename(String gamename) {
        //游戏名
        String name = "";
        if (!TextUtils.isEmpty(gamename)){
            if (gamename.indexOf("（") != -1 && gamename.indexOf("）") != -1){
                name = gamename.replace(gamename.substring(gamename.indexOf("（"), gamename.indexOf("）")+ 1), "");
            }else if (gamename.indexOf("(") != -1 && gamename.indexOf(")") != -1){
                name = gamename.replace(gamename.substring(gamename.indexOf("("), gamename.indexOf(")") + 1), "");
            }else {
                name = gamename;
            }
        }
        return name;
    }

    public static String getOtherGameName(String gamename){
        //游戏尾缀
        String name = "";
        if (!TextUtils.isEmpty(gamename)){
            if (gamename.indexOf("（") != -1 && gamename.indexOf("）") != -1){
                name = gamename.substring(gamename.indexOf("（") + 1, gamename.indexOf("）"));
            }else if (gamename.indexOf("(") != -1 && gamename.indexOf(")") != -1){
                name = gamename.substring(gamename.indexOf("(") + 1, gamename.indexOf(")"));
            }else {
                name = "";
            }
        }
        return name;
    }
}
