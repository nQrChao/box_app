package com.zqhy.app.report;

import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.chaoji.other.blankj.utilcode.util.Logs;
import com.zqhy.app.App;
import com.zqhy.app.utils.cache.ACache;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ReportLog {


    private static final String LEAN_CLOUD_APP_ID  = "rhdPpeduMmzrhawVQ08Lsycw-gzGzoHsz";
    private static final String LEAN_CLOUD_APP_KEY = "JS8T3zEcVaGiyH3m6dmFHIXe";

    /**
     * 上传日志-无视错误
     **/
    public static void postData(String json) {
        try {
            OkHttpClient client = new OkHttpClient();
            String actionUrl = "https://api.leancloud.cn/1.1/classes/AdData";
            String appId = LEAN_CLOUD_APP_ID;
            String key = LEAN_CLOUD_APP_KEY;
            Logs.e("postData：", json);
            if (!TextUtils.isEmpty(json)) {
                RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
                Request request = new Request.Builder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("X-LC-Id", appId)
                        .addHeader("X-LC-Key", key)
                        .url(actionUrl)
                        .post(body)
                        .build();
                //通用请求设置
                client = client.newBuilder().
                        connectTimeout(60, TimeUnit.SECONDS).
                        readTimeout(60, TimeUnit.SECONDS).
                        retryOnConnectionFailure(false).build();
                //创建异步访问
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) {

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传日志-无视错误
     **/
    public static void postRegister(String json) {
        try {
            OkHttpClient client = new OkHttpClient();
            String actionUrl = "https://api.leancloud.cn/1.1/classes/Register";
            //            String appId = "WrHMH77wOxluChwwdn0DYENR-gzGzoHsz";
            String appId = LEAN_CLOUD_APP_ID;
            String key = LEAN_CLOUD_APP_KEY;
            Logs.e("postRegister：", json);
            if (!TextUtils.isEmpty(json)) {
                RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
                Request request = new Request.Builder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("X-LC-Id", appId)
                        .addHeader("X-LC-Key", key)
                        .url(actionUrl)
                        .post(body)
                        .build();
                //通用请求设置
                client = client.newBuilder().
                        connectTimeout(60, TimeUnit.SECONDS).
                        readTimeout(60, TimeUnit.SECONDS).
                        retryOnConnectionFailure(false).build();
                //创建异步访问
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) {

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取异常文件名称
     **/
    public static String getCrashFileName() {
        return ACache.get(App.instance()).getAsString("CrashFileName");
    }

    /**
     * 设置异常文件名称
     **/
    public static void setCrashFileName(String name) {
        ACache.get(App.instance()).put("CrashFileName", name);
    }

    /**
     * 上传日志-无视错误
     **/
    public static void postErrorLog() {
        try {
            String filePath = getCrashFileName();
            OkHttpClient client = new OkHttpClient();
            String actionUrl = "https://api.leancloud.cn/1.1/files/";
            String appId = LEAN_CLOUD_APP_ID;
            String key = LEAN_CLOUD_APP_KEY;
            if (!TextUtils.isEmpty(filePath) && new File(filePath).exists()) {
                File file = new File(filePath);
                RequestBody fileBody = RequestBody.create(MediaType.parse("text/plain"), file);
                Request request = new Request.Builder()
                        .addHeader("Content-Type", "text/plain")
                        .addHeader("X-LC-Id", appId)
                        .addHeader("X-LC-Key", key)
                        .url(actionUrl + file.getName())
                        .post(fileBody)
                        .build();
                //通用请求设置
                client = client.newBuilder().
                        connectTimeout(60, TimeUnit.SECONDS).
                        readTimeout(60, TimeUnit.SECONDS).
                        retryOnConnectionFailure(false).build();
                //创建异步访问
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) {
                        if (response.isSuccessful()) {
                            setCrashFileName("");
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
