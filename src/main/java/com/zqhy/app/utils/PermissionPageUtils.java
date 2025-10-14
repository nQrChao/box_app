package com.zqhy.app.utils;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import com.box.other.blankj.utilcode.util.Logs;
import com.zqhy.app.newproject.BuildConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 权限请求页适配，不同手机系统跳转到不同的权限请求页
 *
 * @author Administrator
 */
public class PermissionPageUtils {

    private final String TAG = "PermissionPageManager";
    private Context mContext;
    /**
     * 包名
     */
    private String packageName = "com.jyhy.jygame";

    public PermissionPageUtils(Context context) {
        this.mContext = context;
        packageName = BuildConfig.APPLICATION_ID;
    }


    public void jumpPermissionPage() {
        String name = Build.MANUFACTURER;
        Logs.e("jumpPermissionPage --- name : " + name);
        Logs.e("packageName : " + packageName);
        switch (name) {
            case "HUAWEI":
                goHuaWeiManager();
                break;
            case "vivo":
                goVivoManager();
                break;
            case "OPPO":
                goOppoManager();
                break;
            case "Coolpad":
                goCoolpadManager();
                break;
            case "Meizu":
                goMeizuManager();
                break;
            case "Xiaomi":
                goXiaoMiManager();
                break;
            case "samsung":
                goSangXinManager();
                break;
            case "Sony":
                goSonyManager();
                break;
            case "LG":
                goLGManager();
                break;
            default:
                goIntentSetting();
                break;
        }
    }

    private void goLGManager() {
        try {
            Intent intent = new Intent(packageName);
            ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity");
            intent.setComponent(comp);
            intent.putExtra("packageName", packageName);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            goIntentSetting();
        }
    }

    private void goSonyManager() {
        try {
            Intent intent = new Intent(packageName);
            ComponentName comp = new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity");
            intent.setComponent(comp);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            goIntentSetting();
        }
    }

    private void goHuaWeiManager() {
        goIntentSetting();
//        try {
//            Intent intent = new Intent();
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
//            intent.setComponent(comp);
//            intent.putExtra("packageName", packageName);
//            mContext.startActivity(intent);
//        } catch (Exception e) {
//            e.printStackTrace();
//            goIntentSetting();
//        }
    }

    private static String getMiuiVersion() {
        String propName = "ro.miui.ui.version.name";
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return line;
    }

    private void goXiaoMiManager() {
        try {
            String rom = getMiuiVersion();
            Logs.e("goMiaoMiMainager --- rom : " + rom);
            Intent intent = new Intent();
            if ("V6".equals(rom) || "V7".equals(rom)) {
                intent.setAction("miui.intent.action.APP_PERM_EDITOR");
                intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                intent.putExtra("extra_pkgname", packageName);
            } else if ("V8".equals(rom) || "V9".equals(rom)) {
                intent.setAction("miui.intent.action.APP_PERM_EDITOR");
                intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
                intent.putExtra("extra_pkgname", packageName);
            } else {
                goIntentSetting();
            }
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            goIntentSetting();
        }
    }

    private void goMeizuManager() {
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("packageName", packageName);
            mContext.startActivity(intent);
        } catch (ActivityNotFoundException localActivityNotFoundException) {
            localActivityNotFoundException.printStackTrace();
            goIntentSetting();
        }
    }

    private void goSangXinManager() {
        //三星4.3可以直接跳转
        goIntentSetting();
    }

    private void goIntentSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
        intent.setData(uri);
        try {
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goOppoManager() {
        goIntentSetting();
//        doStartApplicationWithPackageName("com.coloros.safecenter");
    }

    /**
     * doStartApplicationWithPackageName("com.yulong.android.security:remote")
     * 和Intent open = getPackageManager().getLaunchIntentForPackage("com.yulong.android.security:remote");
     * startActivity(open);
     * 本质上没有什么区别，通过Intent open...打开比调用doStartApplicationWithPackageName方法更快，也是android本身提供的方法
     */
    private void goCoolpadManager() {
        goIntentSetting();
//        doStartApplicationWithPackageName("com.yulong.android.security:remote");

        /*Intent openQQ = getPackageManager().getLaunchIntentForPackage("com.yulong.android.security:remote");
        startActivity(openQQ);*/
    }

    private void goVivoManager() {
        goIntentSetting();
//        doStartApplicationWithPackageName("com.bairenkeji.icaller");
        /*Intent openQQ = getPackageManager().getLaunchIntentForPackage("com.vivo.securedaemonservice");
        startActivity(openQQ);*/
    }

    private void doStartApplicationWithPackageName(String packagename) {
        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;

        try {
            packageinfo = mContext.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return;
        }
        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = mContext.getPackageManager().queryIntentActivities(resolveIntent, 0);
        Logs.e("PermissionPageManager", "resolveinfoList" + resolveinfoList.size());
        for (int i = 0; i < resolveinfoList.size(); i++) {
            Logs.e("PermissionPageManager", resolveinfoList.get(i).activityInfo.packageName + resolveinfoList.get(i).activityInfo.name);
        }
        ResolveInfo resolveinfo = resolveinfoList.iterator().next();

        if (resolveinfo != null) {

            String packageName = resolveinfo.activityInfo.packageName;
            String className = resolveinfo.activityInfo.name;

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            ComponentName cn = new ComponentName(packageName, className);
            intent.setComponent(cn);

            try {
                mContext.startActivity(intent);
            } catch (Exception e) {
                goIntentSetting();
                e.printStackTrace();
            }
        }
    }
}
