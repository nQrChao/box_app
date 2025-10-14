package com.zqhy.app.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.chaoji.other.blankj.utilcode.util.Logs;
import com.reyun.tracking.sdk.Tracking;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.zqhy.app.App;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * <pre>
 *     author: lihan
 *     time  : 2016/9/13
 *     desc  : 设备相关工具类
 * </pre>
 */
public class TsDeviceUtils {

    private TsDeviceUtils() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }


    private static final String TAG = TsDeviceUtils.class.getSimpleName();

    /**
     * 设备唯一标示
     * MD5(androidID?getUUID)
     *
     * @param context
     * @return
     */
    public static String getUniqueId(Context context) {
        String uniqueID = getUniqueID(context);
        return uniqueID;
    }

    /**
     * 设备唯一标示
     * 兼容Android 8.0
     * <p>
     * 需要权限
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    @Deprecated
    public static String getUniqueIdBak(Context context) {
        String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String serial = "";

        try {
            if (Build.VERSION.SDK_INT >= 29) {
                serial = Build.SERIAL;
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    serial = Build.getSerial();
                } else {
                    serial = Build.SERIAL;
                }
            } else {
                serial = Build.SERIAL;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String id = androidID + "_" + serial + "_" + Build.TIME;
        Logs.d(TAG, "getUniqueIdBak = " + id);
        return toMD5(id);
    }

    private static String toMD5(String text) {
        //获取摘要器 MessageDigest
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            //通过摘要器对字符串的二进制字节数组进行hash计算
            byte[] digest = messageDigest.digest(text.getBytes());

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                //循环每个字符 将计算结果转化为正整数;
                int digestInt = digest[i] & 0xff;
                //将10进制转化为较短的16进制
                String hexString = Integer.toHexString(digestInt);
                //转化结果如果是个位数会省略0,因此判断并补0
                if (hexString.length() < 2) {
                    sb.append(0);
                }
                //将循环结果添加到缓冲区
                sb.append(hexString);
            }
            //返回整个结果
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return text;
    }


    /**
     * 设备唯一码
     * androidID?getUUID
     *
     * @param context
     * @return
     */
    private static String getUniqueID(Context context) {
        String id = null;
        final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (!TextUtils.isEmpty(androidId) && !"9774d56d682e549c".equals(androidId)) {
            try {
                UUID uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                id = uuid.toString();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if (TextUtils.isEmpty(id)) {
            id = getUUID();
        }

        return TextUtils.isEmpty(id) ? UUID.randomUUID().toString() : id;
    }

    /**
     * 使用硬件信息拼凑出来的15位号码
     *
     * @return
     */
    private static String getUUID() {
        String serial = null;
        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                Build.USER.length() % 10; //13 位
        try {
            serial = Build.SERIAL;
            //API>=9 使用serial号
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            //serial需要一个初始化
            // 随便一个初始化
            serial = "serial";
        }
        //使用硬件信息拼凑出来的15位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    /**
     * 设备唯一标示 md5(androidID + serial)
     *
     * @param context
     * @return
     */
    public static String getDeviceSign(Context context) {
        String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String id = androidID + Build.SERIAL;
        Logs.d(TAG, "getDeviceSign = " + id);
        String uniqueID = getUniqueID(context);
        Logs.d(TAG, "uniqueID = " + uniqueID);
        return id;
    }


    /**
     * 获取设备IMEI值
     * <p>
     * 如果设备有IMEI1 IMEI2 MEID多个值,则用“_”拼接
     * <p>
     * 需要Manifest.permission.READ_PHONE_STATE权限
     *
     * @param context
     * @return
     */
    public static String getDeviceIMEI(Context context) {
        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
        String MEID = "";
        String IMEI1 = "";
        String IMEI2 = "";
        try {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_DENIED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        //26-28(8.0)
                        IMEI1 = TelephonyMgr.getImei(0);
                        IMEI2 = TelephonyMgr.getImei(1);
                        MEID = TelephonyMgr.getMeid();
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                        //23~26(6.0~8.0)
                        MEID = TelephonyMgr.getDeviceId();
                        IMEI1 = TelephonyMgr.getDeviceId(0);
                        IMEI2 = TelephonyMgr.getDeviceId(1);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
                        //21~22(5.0,5.1)
                        IMEI1 = getIMEI(0);
                        IMEI2 = getIMEI(1);
                        MEID = getMEID();
                        if (TextUtils.isEmpty(IMEI1) && TextUtils.isEmpty(IMEI2)) {
                            IMEI1 = TelephonyMgr.getDeviceId();
                        }
                    } else {//21--(5.0以下)
                        IMEI1 = TelephonyMgr.getDeviceId();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(IMEI1)) {
            sb.append(IMEI1);
        }
        if (!TextUtils.isEmpty(IMEI2)) {
            if (!TextUtils.isEmpty(sb)) {
                sb.append("_");
            }
            sb.append(IMEI2);
        }
        if (!TextUtils.isEmpty(MEID)) {
            if (!TextUtils.isEmpty(sb)) {
                sb.append("_");
            }
            sb.append(MEID);
        }
        return sb.toString();
    }

    private static String getIMEI(int slotId) {
        try {
            Class clazz = Class.forName("android.os.SystemProperties");
            Method method = clazz.getMethod("get", String.class, String.class);
            String imei = (String) method.invoke(null, "ril.gsm.imei", "");
            if (!TextUtils.isEmpty(imei)) {
                String[] split = imei.split(",");
                if (split.length > slotId) {
                    imei = split[slotId];
                }
                Logs.d(TAG, "getIMEI imei: " + imei);
                return imei;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            Logs.w(TAG, "getIMEI error : " + e.getMessage());
        }
        return "";
    }

    private static String getMEID() {
        try {
            Class clazz = Class.forName("android.os.SystemProperties");
            Method method = clazz.getMethod("get", String.class, String.class);

            String meid = (String) method.invoke(null, "ril.cdma.meid", "");
            if (!TextUtils.isEmpty(meid)) {
                Logs.d(TAG, "getMEID meid: " + meid);
                return meid;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            Logs.w(TAG, "getMEID error : " + e.getMessage());
        }
        return "";
    }


    /**
     * 获取友盟的device_id
     *
     * @return
     */
    public static String getUmengDeviceId() {
        String device_id = DeviceConfig.getDeviceIdForGeneral(App.getContext());
        String mac = DeviceConfig.getMac(App.getContext());

        if (TextUtils.isEmpty(device_id) || "unknown".equals(device_id)) {
            //获取不到device_id的情况
            return mac;
        }
        return device_id;
    }

    /**
     * 获取热云SDK的device_id
     * 优先级：imei>oaid>androidid
     *
     * @return
     */
    public static String getReyunDeviceId() {
        return Tracking.getDeviceId();
    }
}
