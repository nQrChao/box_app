package com.zqhy.app.utils;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.box.other.blankj.utilcode.util.Logs;
import com.zqhy.app.App;
import com.zqhy.app.DeviceBean;
import com.zqhy.app.core.tool.utilcode.DeviceUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Administrator
 */
public class JiuYaoDeviceUtils {

    private static final String TAG = JiuYaoDeviceUtils.class.getSimpleName();

    /**
     * 设备唯一标示
     * 兼容Android 8.0
     *
     * @param context
     * @return
     */
    public static String getUniqueId(Context context) {
        String androidID;
        DeviceBean deviceBean = App.getDeviceBean();
        if (deviceBean.getAndroidid() == null){
            deviceBean.setAndroidid(DeviceUtils.getAndroidID(App.instance()));
        }
        androidID = deviceBean.getAndroidid();
        String serial = "";
        try{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    serial = Build.getSerial();
                } else {
                    serial = Build.SERIAL;
                }
            } else {
                serial = Build.SERIAL;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return androidID + serial;
    }

    /**
     * 设备唯一标示
     *
     * @param context
     * @return
     */
    public static String getDeviceSign(Context context) {
        String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String id = androidID + Build.SERIAL;
        Logs.d(TAG, "getDeviceSign = " + id);
        try {
            return toMD5(id);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return id;
        }
    }


    private static String toMD5(String text) throws NoSuchAlgorithmException {
        //获取摘要器 MessageDigest
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
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
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_DENIED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    //26++(8.0)
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
}
