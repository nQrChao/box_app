# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#-------------------------------------------定制化区域----------------------------------------------
#---------------------------------1.实体类---------------------------------
-keep class com.zqhy.app.core.data.model.** { *; }
-keep class com.zqhy.app.core.data.model.**$* { *; }

-keep class com.zqhy.app.audit.data.model.** { *; }
-keep class com.zqhy.app.audit.data.model.**$* { *; }

-keep class com.zqhy.app.audit.sub.modle.** { *; }
-keep class com.zqhy.app.audit.sub.modle.**$* { *; }

-keep class com.zqhy.app.aprajna.data.** { *; }
-keep class com.zqhy.app.aprajna.data.**$* { *; }
-keep class com.zqhy.app.core.vm.main.data.MainPageData { *; }
-keep class com.zqhy.app.core.vm.main.data.MainPageData$* { *; }

-keep class com.zqhy.app.audit2.data.** { *; }
-keep class com.zqhy.app.audit2.data.**$* { *; }

-keep class com.zqhy.app.db.table.message.MessageVo.** { *; }
-keep class com.zqhy.app.db.table.message.MessageVo.**$* { *; }


-keep class com.zqhy.app.db.table.search.SearchGameVo.** { *; }
-keep class com.zqhy.app.db.table.search.SearchGameVo.**$* { *; }

-keep class com.zqhy.app.db.table.search.BipartitionGameVo.** { *; }
-keep class com.zqhy.app.db.table.search.BipartitionGameVo.**$* { *; }

-keep class com.zqhy.sdk.db.UserBean{*;}
-keep class com.zqhy.app.network.request.BaseMessage{ *; }


# 保留 扩展函数包
-keep class com.chaoji.base.ext.** { *; }
# 保留 Kotlin 协程相关的类
-keep class kotlinx.coroutines.** { *; }
-keepnames class kotlinx.coroutines.** { *; }
-keepclassmembers class kotlinx.coroutines.** { *; }
# 保留 ViewModel 类
-keep class com.zqhy.app.core.view.AppSplashModel { *; }
-keepclassmembers class com.zqhy.app.core.view.AppSplashModel { *; }
-keep class com.chaoji.base.state.ResultState.** { *; }
-keepclassmembers class com.chaoji.base.state.ResultState.** { *; }
-keep class com.chaoji.base.network.BaseResponse { *; }
-keepclassmembers class com.chaoji.base.network.BaseResponse { *; }
-keep class com.chaoji.base.network.AppException { *; }
-keepclassmembers class com.chaoji.base.network.AppException { *; }
-keep class com.chaoji.base.network.ExceptionHandle { *; }
-keepclassmembers class com.chaoji.base.network.ExceptionHandle { *; }
-keep class com.chaoji.im.ui.fragment.navigation1.data.** { *; }
-keepclassmembers class com.chaoji.im.ui.fragment.navigation1.data.** { *; }
# 如果你的 BaseResponseVo 是一个泛型类，可能需要更具体的规则
-keep class com.zqhy.app.core.data.model.BaseResponseVo { *; }
-keepclassmembers class com.zqhy.app.core.data.model.BaseResponseVo { *; }
-keep class com.zqhy.app.core.data.model.** { *; }
-keepclassmembers class com.zqhy.app.core.data.model.** { *; }
# 保留 com.google.gson.** 以确保 Gson 可以正常工作
-keep class com.google.gson.** { *; }
-keep class com.google.gson.**$* { *; }
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes EnclosingMethod
# 如果你的接口使用了 Retrofit，可能还需要保留 Retrofit 相关的类
-keep interface retrofit2.** { *; }
-keep class retrofit2.** { *; }
-keep class retrofit2.http.** { *; }

# 保留接口方法参数上的注解，Retrofit可能会用到
-keepattributes MethodParameters

# 保持注解不被移除
-keep @interface *

# 保持所有使用了 @SerializedName 注解的字段
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

#--------------------------------特殊的类--------------------------------
#------------------------------------------------------------------------


#-------------------------------------------------------------------------
#---------------------------------2.第三方包-------------------------------

#eventBus
-keepattributes *Annotation*
-keepclassmembers class ** { @org.greenrobot.eventbus.Subscribe <methods>; }
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# Only required if you use AsyncExecutor
#-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent { <init>(java.lang.Throwable); }

#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}


#fragmentation
-keep class me.yokeyword.fragmentation.* {*;}
-keep interface me.yokeyword.fragmentation.* {*;}
-keep class me.yokeyword.fragmentation_components.* {*;}
-keep interface me.yokeyword.fragmentation_components.* {*;}

#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}

#okio
-dontwarn okio.**
-keep class okio.**{*;}

#当然如果你确实不需要混淆okgo的代码,可以继续添加以下代码
#okgo
-dontwarn com.lzy.okgo.**
-keep class com.lzy.okgo.**{*;}

#okrx
-dontwarn com.lzy.okrx.**
-keep class com.lzy.okrx.**{*;}

#rx
-dontwarn rx.**
-keep class rx.**{*;}

#okrx2
-dontwarn com.lzy.okrx2.**
-keep class com.lzy.okrx2.**{*;}

#okserver
-dontwarn com.lzy.okserver.**
-keep class com.lzy.okserver.**{*;}

################gson###############
#-keepattributes Signature
#-keepattributes *Annotation*
#-keepattributes EnclosingMethod
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod

-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.** { *; }

#getui:sdk
-keep class com.igexin.** {*;}
-keep interface com.igexin.** {*;}

#zing
-keep class com.google.zing.** {*;}
-keep interface com.google.zing.** {*;}

#CouponView
-keep class yyydjk.com.library.** {*;}
-keep interface yyydjk.com.library.** {*;}

#TalkingData
-dontwarn com.tendcloud.tenddata.**
-keep class com.tendcloud.** {*;}
-keep public class com.tendcloud.tenddata.** { public protected *;}
-keepclassmembers class com.tendcloud.tenddata.**{
public void *(***);
}
-keep class com.talkingdata.sdk.TalkingDataSDK {public *;}
-keep class com.apptalkingdata.** {*;}
-keep class dice.** {*; }
-dontwarn dice.**

-keep class com.talkingdata.sdk.** {*;}


#阿里支付
-dontwarn com.alipay.**
-keep class com.alipay.**{*;}

-dontwarn com.ta.utdid2.**
-keep class com.ta.utdid2.**{*;}

-dontwarn com.ut.device.**
-keep class com.ut.device.**{*;}
-keep class HttpUtils.HtppFetcher{*;}


-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
-keep class com.tencent.mm.sdk.** {
   *;
}
-keep class com.tencent.mm.opensdk.** {
   *;
}
-keep class com.tencent.wxop.** {
   *;
}
-keep class com.tencent.mm.sdk.** {
   *;
}

-keep class com.tencent.** {*;}
-dontwarn com.tencent.**

#DBFlow
-keep class * extends com.raizlabs.android.dbflow.config.DatabaseHolder { *; }


#umeng
-keep class com.umeng.** {*;}

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#jzvd
-keep public class cn.jzvd.JZMediaInterface {*; }
-keep public class cn.jzvd.JZMediaSystem {*; }
-keep public class com.zqhy.app.widget.video.CustomMediaPlayer.JZExoPlayer {*; }
-keep public class com.zqhy.app.widget.video.CustomMediaPlayer.JZMediaIjkplayer {*; }
-keep public class com.zqhy.app.widget.video.CustomMediaPlayer.CustomMediaPlayerAssertFolder {*; }

-keep class tv.danmaku.ijk.media.player.** {*; }
-dontwarn tv.danmaku.ijk.media.player.*
-keep interface tv.danmaku.ijk.media.player.** { *; }


#gdt 腾讯统计上报
-dontwarn com.qq.gdt.action.**
-keep class com.qq.gdt.action.** {*;}
-keep public class com.tencent.turingfd.sdk.**
-keepclasseswithmembers class * {
native <methods>;
}

#热云统计
-dontwarn org.bouncycastle.**
-keep class org.bouncycastle.** {*;}
-dontwarn com.bun.miitmdid.**
-keep class com.bun.miitmdid.** {*;}
-dontwarn com.reyun.tracking.**
-keep class com.reyun.tracking.** {*;}



-dontwarn com.qiyukf.**
-keep class com.qiyukf.** {*;}
-keep class com.qiyukf.unicorn.api.ConsultSource.** { *; }
-keep class com.qiyukf.unicorn.api.ConsultSource.**$* { *; }

#百度转换SDK
-dontwarn com.baidu.mobads.action.**
-keep class com.baidu.mobads.action.** {*;}
-keep class com.bun.miitmdid.core.** {*;}

#-------------------------------------------------------------------------
#---------------------------------3.与js互相调用的类------------------------
-keepclasseswithmembers class com.zqhy.app.core.view.browser.JavaScriptInterface { <methods>; }



#-------------------------------------------------------------------------
#---------------------------------4.反射相关的类和方法-----------------------
#TODO 我的工程里没有。。。
#----------------------------------------------------------------------------
#---------------------------------------------------------------------------------------------------




#-------------------------------------------基本不用动区域--------------------------------------------
#---------------------------------基本指令区----------------------------------
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
#----------------------------------------------------------------------------
#---------------------------------默认保留区---------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

#fragment
-keep public class * extends android.app.Fragment
-keep public class * extends android.support.v4.app.Fragment{
        <fields>;
        <methods>;
}

-keepclasseswithmembernames class * {
    native <methods>;
 }
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
 }
-keep public class * extends android.view.View{
    *** get*(); void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
 }
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
 }
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
 }
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
 }
-keep class **.R$* { *; }
-keepclassmembers class * {
    void *(**On*Event);
 }

 -keep class org.** {*;}
 -keep class com.android.**{*;}

 -dontwarn android.net.http.**
 -keep class org.apache.http.** { *;}
#----------------------------------------------------------------------------
#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview { public *; }
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
 }
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
 }
 -ignorewarnings
#----------------------------------------------------------------------------
#---------------------------------------------------------------------------------------------------
-keepclasseswithmembernames class * {
native <methods>;
}-dontwarn com.gism.**
#----------------------------------------------------------------------------
#---------------------------------authlogin------------------------------------
-keep class com.nirvana.** { *; }
-keep class com.uc.crashsdk.** { *; }
-keep class com.idsmanager.doraemon.** { *; }


-keepclassmembers class * {
    <init>(...);
}

-keepclassmembers class * extends java.lang.Enum {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class androidx.** {*;}
-keep public class * extends androidx.**
-keep interface androidx.** {*;}
-keep class android.view.** {*;}
-keep class android.content.** {*;}
-dontwarn androidx.**
# ----- common ---------------------------------------  end

-keep class com.tencent.mars.** { *; }
-keep class okhttp3.** { *; }
-keep class retrofit2.** { *; }
-keep class me.jessyan.autosize.** { *; }
-keep class kotlin.** { *; }
-keep class kotlinx.** { *; }
-keep class com.google.** { *; }
-keep class com.tencent.** { *; }
-keep class jonathanfinerty.once.** { *; }
-keep class es.dmoral.toasty.** { *; }
-keep class com.airbnb.lottie.** { *; }
-keep class com.luck.picture.lib.** { *; }
-keep class com.makeramen.roundedimageview.** { *; }
-keep class com.github.promeg.pinyinhelper.** { *; }
-keep class com.bumptech.glide.** { *; }

-keep class com.gmspace.app.bean.** { *; }

-dontwarn android.**
-dontwarn com.android.**
-dontwarn ref.**

-keep class kotlin.Metadata { *; }
-keep class com.vmos.** { *; }
-keep class top.canyie.** { *; }
-keep class com.tqzhang.** { *; }
#-dontwarn com.vmos.**
#-dontwarn top.canyie.**
#-dontwarn com.tqzhang.**

## NIM SDK 防混淆
-dontwarn com.netease.nim.**
-keep class com.netease.nim.** {*;}

-dontwarn com.netease.nimlib.**
-keep class com.netease.nimlib.** {*;}

-dontwarn com.netease.share.**
-keep class com.netease.share.** {*;}

-dontwarn com.netease.mobsec.**
-keep class com.netease.mobsec.** {*;}

## 如果你使用全文检索插件，需要加入
-dontwarn org.apache.lucene.**

-keep class android.car.** { *; }
-dontwarn android.car.**

## IM SDK
-dontwarn com.netease.nim.**
-keep class com.netease.nim.** {*;}

-dontwarn com.netease.nimlib.**
-keep class com.netease.nimlib.** {*;}

-dontwarn com.netease.share.**
-keep class com.netease.share.** {*;}

-dontwarn com.netease.mobsec.**
-keep class com.netease.mobsec.** {*;}

#如果你使用全文检索插件，需要加入
-dontwarn org.apache.lucene.**
-keep class org.apache.lucene.** {*;}

## IMUIKit
-dontwarn com.netease.yunxin.kit.**
-keep class com.netease.yunxin.kit.** {*;}
-keep public class * extends com.netease.yunxin.kit.corekit.XKitInitOptions
-keep class * implements com.netease.yunxin.kit.corekit.XKitService {*;}

## 呼叫组件混淆
-keep class com.netease.lava.** {*;}
-keep class com.netease.yunxin.** {*;}

### glide 4
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}

#如果你使用全文检索插件，需要加入
-dontwarn org.apache.lucene.**
-keep class org.apache.lucene.** {*;}

#如果你开启数据库功能，需要加入
-keep class net.sqlcipher.** {*;}

-keep class com.google.android.material.** {*;}

-keep class androidx.** {*;}

-keep public class * extends androidx.**

-keep interface androidx.** {*;}

-dontwarn com.google.android.material.**

-dontnote com.google.android.material.**

-dontwarn androidx.**

### APP 3rd party jars(xiaomi push)
-dontwarn com.xiaomi.push.**
-keep class com.xiaomi.** {*;}

### APP 3rd party jars(huawei push)
-ignorewarnings
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
# hmscore-support: remote transport
-keep class com.huawei.hianalytics.**{*;}
-keep class com.huawei.updatesdk.**{*;}
-keep class com.huawei.hms.**{*;}

### APP 3rd party jars(meizu push)
-dontwarn com.meizu.cloud.**
-keep class com.meizu.cloud.** {*;}

#vivo
-dontwarn com.vivo.push.**
-keep class com.vivo.push.** {*;}
-keep class com.vivo.vms.** {*;}
-keep class com.netease.nimlib.mixpush.vivo.VivoPushReceiver {*;}

#oppo
-keep public class * extends android.app.Service
-keep class com.heytap.msp.** { *;}


### org.json xml
-dontwarn org.json.**
-keep class org.json.**{*;}


#okio
-dontwarn okio.**
-keep class okio.**{*;}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep class com.bytedance.ads.convert.broadcast.common.EncryptionTools {*;}