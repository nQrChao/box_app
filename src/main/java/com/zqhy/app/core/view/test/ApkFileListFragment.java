package com.zqhy.app.core.view.test;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.view.test.holder.ApkFileItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.newproject.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.zip.ZipFile;

/**
 * @author Administrator
 */
public class ApkFileListFragment extends BaseListFragment {

    private static final String TAG = "ApkFileListFragment";

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(ResolveInfo.class, new ApkFileItemHolder(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new GridLayoutManager(_mActivity, 4);
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    private PackageManager pm;

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle("Apk Files");


        setOnItemClickListener((v, position, data) -> {
            if (data != null && data instanceof ResolveInfo) {
                ResolveInfo info = (ResolveInfo) data;
                String appDir = null;
                CharSequence appName = null;
                try {
                    // 指定包名的程序源文件路径
                    appDir = pm.getApplicationInfo(
                            info.activityInfo.packageName, 0).sourceDir;
                    appName = info.loadLabel(pm);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                StringBuilder sb = new StringBuilder();
                sb.append("appName = ").append(appName).append("\n")
                        .append("appDir = ").append(appDir);
                Log.e(TAG, sb.toString());


                try {
                    File apkFile = new File(appDir);
                    if (apkFile == null || !apkFile.exists()) {
                        return;
                    }
                    String apkAnnotation = readAPK2(apkFile);
                    Log.e(TAG, "压缩包注释：" + apkAnnotation);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        setLoadingMoreEnabled(false);
        setPullRefreshEnabled(false);

        pm = _mActivity.getPackageManager();
        initData();
    }

    private ArrayList<ResolveInfo> mApps;

    private void initData() {
        // 获取android设备的应用列表
        // 动作匹配
        Intent intent = new Intent(Intent.ACTION_MAIN);
        // 类别匹配
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        mApps = (ArrayList<ResolveInfo>) pm.queryIntentActivities(intent, 0);
        clearData();
        if (mApps != null && !mApps.isEmpty()) {
            // 排序
            Collections.sort(mApps, new Comparator<ResolveInfo>() {
                @Override
                public int compare(ResolveInfo a, ResolveInfo b) {
                    // 排序规则
                    PackageManager pm = _mActivity.getPackageManager();
                    // 忽略大小写
                    return String.CASE_INSENSITIVE_ORDER.compare(a.loadLabel(pm)
                            .toString(), b.loadLabel(pm).toString());
                }
            });
            addAllData(mApps);
        } else {
            addDataWithNotifyData(new EmptyDataVo());
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String readAPK2(File file) {
        ZipFile zipFile;
        try {
            zipFile = new ZipFile(file);
            String zipComment = zipFile.getComment();

            return zipComment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取apk注释
     *
     * @param file
     * @return
     */
    public static String readAPK(File file) {
        byte[] bytes = null;
        try {
            RandomAccessFile accessFile = new RandomAccessFile(file, "r");
            long index = accessFile.length();
            bytes = new byte[2];
            index = index - bytes.length;
            accessFile.seek(index);
            accessFile.readFully(bytes);

            int contentLength = stream2Short(bytes, 0);

            bytes = new byte[contentLength];
            index = index - bytes.length;
            accessFile.seek(index);
            accessFile.readFully(bytes);

            String a = new String(bytes, "utf-8");
            Log.d(TAG, a);
            return a;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * short转换成字节数组（小端序）
     *
     * @param stream
     * @param offset
     * @return
     */
    private static short stream2Short(byte[] stream, int offset) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(stream[offset]);
        buffer.put(stream[offset + 1]);
        return buffer.getShort(0);
    }
}
