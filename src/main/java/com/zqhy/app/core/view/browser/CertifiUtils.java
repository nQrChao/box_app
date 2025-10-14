package com.zqhy.app.core.view.browser;

import com.lzy.okgo.https.HttpsUtils;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;

import java.io.IOException;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Leeham2734
 * @date 2020/2/18-12:36
 * @description
 */
public class CertifiUtils {
    // 验证证书
    public static void OnCertificateOfVerification(final SslErrorHandler handler, String url) {

        OkHttpClient.Builder builder = setCertificates(new OkHttpClient.Builder());
        Request request = new Request.Builder().url(url)
                .build();
        builder.build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handler.proceed();
            }
        });
    }

    private static OkHttpClient.Builder setCertificates(OkHttpClient.Builder client) {
        try {
            //Xutils.getSSLContext：获取证书SSLSocketFactory（这个网络上有很多代码，并且我之前的文章里也有写出来，在这里就不过多的描述了）
            SSLSocketFactory sslSocketFactory = HttpsUtils.getSslSocketFactory().sSLSocketFactory;
            X509TrustManager trustManager = HttpsUtils.getSslSocketFactory().trustManager;
            client.sslSocketFactory(sslSocketFactory, trustManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }
}
