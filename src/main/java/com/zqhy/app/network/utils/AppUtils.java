package com.zqhy.app.network.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.bytedance.hume.readapk.HumeSDK;
import com.box.other.blankj.utilcode.util.Logs;
import com.kwai.monitor.payload.TurboHelper;
import com.zqhy.app.App;
import com.zqhy.app.core.tool.AppUtil;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.subpackage.ChannelReaderUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by Administrator on 2016/5/11.
 */
public class AppUtils {
    private static final String TAG = AppUtil.class.getSimpleName();

    //ea0000001
    //ab1222222
    private static final String E = !TextUtils.isEmpty(BuildConfig.DEFAULE_CHANNEL_TGID) ? BuildConfig.DEFAULE_CHANNEL_TGID : "ba0100201";

    /**
     * 九妖默认Tgid = ""
     * ea0000001
     * wa0000001
     * <p>
     * 审核测试Tgid = aa0001001,cf1111112,ab1212222,ca1111111,ab1212007,aa2004001
     */
    private static String defaultTgid = !TextUtils.isEmpty(BuildConfig.CHANNEL_TGID) ? BuildConfig.CHANNEL_TGID : (com.zqhy.app.config.AppConfig.isRefundChannel() ? "ba0100201" : E);
    private static String tgid = defaultTgid;

    /**
     * 从apk中获取渠道信息
     *
     * @return
     */
    public static String getChannelFromApk() {
        /*if (!TextUtils.isEmpty(BuildConfig.TENCENT_APP_ID)){
            String tencentChannel = ChannelReaderUtil.getChannel(App.getContext());
            if (!TextUtils.isEmpty(tencentChannel)){
                if (tencentChannel.contains("_")) {
                    tencentChannel = tencentChannel.split("_")[0];
                }
                if (!TextUtils.isEmpty(tencentChannel)){
                    defaultTgid = tencentChannel;
                }
                return defaultTgid;
            }
        }*/

        if (!TextUtils.isEmpty(BuildConfig.KUAISHOU_APP_ID)) {
            String turboChannel = TurboHelper.getChannel(App.instance());
            if (!TextUtils.isEmpty(turboChannel)) {
                if (turboChannel.contains("_")) {
                    turboChannel = turboChannel.split("_")[0];
                }
                if (!TextUtils.isEmpty(turboChannel)) {
                    defaultTgid = turboChannel;
                }
                return defaultTgid;
            }
        }
        if (BuildConfig.IS_CONTAINS_TOUTIAO_SDK) {
            String channel = HumeSDK.getChannel(App.instance());
            if (!TextUtils.isEmpty(channel)) {
                if (channel.contains("_")) {
                    channel = channel.split("_")[0];
                }
                if (!TextUtils.isEmpty(channel)) {
                    defaultTgid = channel;
                }
                return defaultTgid;
            } else {
                String tencentChannel = ChannelReaderUtil.getChannel(App.getContext());
                //String tencentChannel = "eyJ0Z2lkIjoiZWE2NjY2NjY2IiwicGYiOiJ0c3l1bGUifQ==";
                if (!TextUtils.isEmpty(tencentChannel)) {
                    byte[] decode = Base64.decode(tencentChannel);
                    if (decode != null) {
                        String channelStr = new String(decode);
                        if (!TextUtils.isEmpty(channelStr)) {
                            String tgid = "";
                            try {
                                JSONObject jsonObject = new JSONObject(channelStr);
                                tgid = jsonObject.getString("tgid");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (!TextUtils.isEmpty(tgid)) {
                                defaultTgid = tgid;
                                return defaultTgid;
                            }
                        }
                    }
                }
            }
        }


        String tencentChannel = ChannelReaderUtil.getChannel(App.getContext());
        if (!TextUtils.isEmpty(tencentChannel)) {
            byte[] decode = Base64.decode(tencentChannel);
            if (decode != null) {
                String channel = new String(decode);
                if (!TextUtils.isEmpty(channel)) {
                    String tgid = "";
                    try {
                        JSONObject jsonObject = new JSONObject(channel);
                        tgid = jsonObject.getString("tgid");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (!TextUtils.isEmpty(tgid)) {
                        defaultTgid = tgid;
                    }
                }
            } else {
                String tgid = "";
                try {
                    JSONObject jsonObject = new JSONObject(tencentChannel);
                    tgid = jsonObject.getString("tgid");
                } catch (JSONException e) {
                }
                if (!TextUtils.isEmpty(tgid)) {
                    defaultTgid = tgid;
                }
            }
            return defaultTgid;
        }

        if (!TextUtils.isEmpty(BuildConfig.CHANNEL_TGID)) {
            return BuildConfig.CHANNEL_TGID;
        }

        //从apk包中获取
        ApplicationInfo appinfo = App.instance().getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        //注意这里：默认放在meta-inf/里， 所以需要再拼接一下
        String key = "META-INF/" + "cychannel";
        String ret = "";
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.startsWith(key)) {
                    ret = entryName;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String[] split = ret.split("_");
        for (int i = 0; i < split.length; i++) {
        }
        String channel = defaultTgid;
        if (split != null && split.length >= 2) {
            channel = ret.substring(split[0].length() + 1);
        }

        Logs.e("initTGID",channel);
        return channel;
    }


    public static int getChannelGameidFromApk() {
        //从apk包中获取
        ApplicationInfo appinfo = App.instance().getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        //注意这里：默认放在meta-inf/里， 所以需要再拼接一下
        String key = "META-INF/" + "gameid";
        String ret = "";
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.startsWith(key)) {
                    ret = entryName;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            String[] split = ret.split("_");
            String gameid = null;
            if (split != null && split.length >= 2) {
                gameid = ret.substring(split[0].length() + 1);
            }

            return Integer.parseInt(gameid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 初始化tgid(apk包tgid)
     */
    public static void initTgid() {
        /*if (!TextUtils.isEmpty(BuildConfig.CHANNEL_TGID)) {
            tgid = BuildConfig.CHANNEL_TGID;
            return;
        }*/
        tgid = getChannelFromApk();
        Log.e("AppUtils-TGID:",tgid);

    }

    public static String getTgid() {
        return tgid;
    }

    public static void setTgid(String tgids) {
        tgid = tgids;
    }

    public static void setDefaultTgid(String tgids) {
        defaultTgid = tgids;
    }

    public static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6',
                '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        MessageDigest mdInst = null;
        try {
            byte[] btInput = s.getBytes();
            mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String MapToString(Map<String, String> map) {
        String returnString = "";
        Set<String> set = map.keySet();
        for (String st : set) {
            returnString += st + "=" + map.get(st) + "&";
        }
        return returnString.substring(0, returnString.length() - 1);
    }

    public static String getSignKey(Map<String, String> params) {
        Map<String, String> newParams = new TreeMap<>();
        for (String key : params.keySet()) {
            String value = params.get(key);
            try {
                //2018.07.12 api sign签名时，如果参数为空，键值也需要加入到签名
                if (!TextUtils.isEmpty(value)) {
                    String encodeValue = URLEncoder.encode(value, "UTF-8");
                    String newValue = encodeValue.replace("*", "%2A");
                    newParams.put(key, newValue);
                } else {
                    newParams.put(key, "");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String sign = (MapToString(newParams) + AppConfig.signKey);
        Logs.e(TAG, "signstr(MD5前串):" + sign);
        return MD5(sign);
    }

    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    public static boolean checkState(String state) {
        if ("ok".equals(state)) {
            return true;
        }
        if ("err".equals(state)) {
            return false;
        }
        return false;
    }

    public static int getVersionCode(String packageName) {
        PackageManager packageManager = App.instance().getPackageManager();
        try {
            return packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }
        return Integer.MAX_VALUE;

    }


    public static final Map<String, Integer> apps = new HashMap<String, Integer>();

    public static void loadCustomPkgInfos(Map<String, Integer> apps) {
        try {
            PackageManager packageManager = App.instance().getPackageManager();
            List<PackageInfo> infos = packageManager.getInstalledPackages(PackageManager.GET_ACTIVITIES);
            for (PackageInfo info : infos) {
                if ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                    apps.put(info.packageName, info.versionCode);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
