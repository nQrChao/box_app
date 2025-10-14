package com.zqhy.app.utils.compat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.FileProvider;

import java.io.File;

public class FileCompat {
    public static Uri fromFile(Context context, Intent intent, File uriFile){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String packageName = context.getPackageName();
            Uri uriForFile = FileProvider.getUriForFile(context, packageName + ".fileProvider", uriFile);
            if(intent!=null){
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            return uriForFile;
        }else{
            return Uri.fromFile(uriFile);
        }
    }
}
