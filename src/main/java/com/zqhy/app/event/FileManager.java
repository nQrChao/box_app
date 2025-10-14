package com.zqhy.app.event;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import androidx.core.content.ContextCompat;

import com.chaoji.other.blankj.utilcode.util.Logs;
import com.zqhy.app.App;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 文件管理
 * @author 韩国桐
 * @version [0.1,2019-12-10]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class FileManager {

    /** 历史文件删除逻辑 **/
    private static final int LIMIT_DAY=30;
    /** 时间单位：天 **/
    private static final long DAY= 24 * 60 * 60 * 1000;

    public static final String PIC="image";

    public static String getPicPath(){
        //判断存储空间权限，无存储空间权限则指向应用数据目录，有则使用SD卡目录
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            File[] temp= App.instance().getExternalMediaDirs();
            if(temp.length>0){
                return temp[0].getAbsolutePath();
            }else{
                return App.instance().getFilesDir().getPath();
            }
        }else{
            return Environment.getExternalStorageDirectory().getPath();
        }
    }
    /**
     * 获取文件根目录
     * 有权限的情况下：指向本地目录，否则指向内部目录
     * **/
    public static String getSdkRootPath(){
        //判断存储空间权限，无存储空间权限则指向应用数据目录，有则使用SD卡目录
        if(ContextCompat.checkSelfPermission(App.instance(),Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            return App.instance().getFilesDir().getPath();
        }
        return Environment.getExternalStorageDirectory().getPath();
    }
    /**
     * 删除历史文件
     * **/
    public static void clearHistoryFile(List<File> fileList){
        DeleteOutOfDateLogsTask task=new DeleteOutOfDateLogsTask(fileList);
        task.run();
    }

    /**
     * 删除过期的log文件的task
     */
    private static class DeleteOutOfDateLogsTask implements Runnable{
        private List<File> logDirs;
        private long temp=0;
        DeleteOutOfDateLogsTask(List<File> logDirs){
            this.logDirs = logDirs;
        }

        @Override
        public void run() {
            List<File> filesNeedToDelete = new ArrayList<>();
            FileFilter fileFilter = pathname -> {
                long currentUnixTime = System.currentTimeMillis();
                long modifiedUnixTime = pathname.lastModified();
                if(temp>currentUnixTime){
                    currentUnixTime=temp;
                }
                long days = (currentUnixTime - modifiedUnixTime) / DAY;
                //默认是超过30天算作超期
                return days > LIMIT_DAY ;
            };
            for(File logDir : logDirs){
                File[] files = logDir.listFiles();
                if(files==null){
                    return;
                }
                Arrays.sort(files, (f1, f2) -> {
                    long diff = f1.lastModified() - f2.lastModified();
                    if (diff > 0) {
                        return 1;
                    } else if (diff == 0) {
                        return 0;
                    } else {
                        return -1;
                    }
                });
                if(files.length>0){
                    temp=files[files.length-1].lastModified();
                }
                filesNeedToDelete.addAll(Arrays.asList(logDir.listFiles(fileFilter)));
            }
            for(File file : filesNeedToDelete){
                if(!file.delete()){
                    Logs.w("Log file("+file.getAbsolutePath()+") delete failed.");
                }
            }
        }
    }
}
