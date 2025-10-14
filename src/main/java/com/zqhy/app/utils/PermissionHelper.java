package com.zqhy.app.utils;

import android.Manifest;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;

import java.util.List;

/**
 * @author pc
 * @date 2019/12/11-16:18
 * @description 动态权限管理类
 */
public class PermissionHelper {


    private static String[] STORAGE_PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static String[] PHONE_PERMISSIONS = new String[]{
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_PHONE_STATE
    };

    public static String[] CAMERA_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA
    };

    public static String[] CALENDAR_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_CALENDAR,
            Manifest.permission.READ_CALENDAR
    };


    public static void checkStoragePermissions(OnPermissionListener onPermissionListener) {
        checkPermissions(onPermissionListener, STORAGE_PERMISSIONS);
    }


    /**
     * 申请权限
     * @param onPermissionListener
     * @param permissions
     */
    public static void checkPermissions(OnPermissionListener onPermissionListener, @PermissionConstants.PermissionGroup final String... permissions) {
        if (!hasPermissions(permissions)) {
            PermissionUtils.permission(permissions)
                    .callback(new PermissionUtils.FullCallback() {
                        @Override
                        public void onGranted(List<String> permissionsGranted) {
                            if (onPermissionListener != null) {
                                onPermissionListener.onGranted();
                            }
                        }

                        @Override
                        public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                            if (onPermissionListener != null) {
                                onPermissionListener.onDenied(permissionsDeniedForever, permissionsDenied);
                            }
                        }
                    }).request();
        } else {
            if (onPermissionListener != null) {
                onPermissionListener.onGranted();
            }
        }
    }

    /**
     * 判断是否有权限
     * @param permissions
     * @return
     */
    public static boolean hasPermissions(final String... permissions){
        return PermissionUtils.isGranted(permissions);
    }

    public interface OnPermissionListener {
        /**
         * 已经获取全部权限
         */
        void onGranted();

        /**
         * 未获取全部权限
         *
         * @param permissionsDeniedForever
         * @param permissionsDenied
         */
        void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied);
    }

}
