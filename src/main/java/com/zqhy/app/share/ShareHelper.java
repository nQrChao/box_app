package com.zqhy.app.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory;
import com.bytedance.sdk.open.douyin.DouYinOpenConfig;
import com.bytedance.sdk.open.douyin.ShareToContact;
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi;
import com.bytedance.sdk.open.douyin.model.ContactHtmlObject;
import com.chaoji.other.blankj.utilcode.util.Logs;
import com.chaoji.other.hjq.toast.Toaster;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.zqhy.app.core.data.model.share.InviteDataVo;
import com.zqhy.app.core.tool.AppUtil;
import com.zqhy.app.core.tool.ImageUtils;
import com.zqhy.app.core.tool.MD5Utils;
import com.zqhy.app.core.tool.utilcode.ScreenUtils;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.network.statistics.JiuYaoStatisticsApi;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.utils.sdcard.SdCardManager;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


/**
 * @author Administrator
 * @date 2018/11/5
 */

public class ShareHelper {

    private Activity _mActivity;

    public IWXAPI  iwxapi;
    public Tencent mTencent;

    private OnShareListener onShareListener;

    public ShareHelper(Activity _mActivity) {
        this._mActivity = _mActivity;
    }

    public ShareHelper(Activity mContext, OnShareListener onShareListener) {
        this._mActivity = mContext;
        mTencent = Tencent.createInstance(BuildConfig.QQ_APP_ID, mContext);
        iwxapi = WXAPIFactory.createWXAPI(mContext, BuildConfig.WX_APP_ID, true);
        DouYinOpenApiFactory.init(new DouYinOpenConfig(BuildConfig.DOUYIN_CLIENT_KEY));

        this.onShareListener = onShareListener;
    }

    private CustomDialog shareDialog, saveDialog;


    public void showShareInviteFriend(InviteDataVo.InviteDataInfoVo inviteDataInfoVo) {
        if (inviteDataInfoVo == null) {
            return;
        }
        if (!hasShareKey()) {
            shareToAndroidSystem(inviteDataInfoVo.getCopy_title(), inviteDataInfoVo.getCopy_description(), inviteDataInfoVo.getUrl());
            return;
        }

        String target_url = inviteDataInfoVo.getUrl();
        String icon_url = inviteDataInfoVo.getIcon();
        if (shareDialog == null) {
            shareDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_invite_friend, null),
                    ScreenUtils.getScreenWidth(_mActivity), WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);

            shareDialog.findViewById(R.id.btn_share_circle).setOnClickListener(view -> {
                JiuYaoStatisticsApi.getInstance().eventStatistics(6, 91, 1);
                shareToWxCircle(target_url, inviteDataInfoVo.getWx_group(), inviteDataInfoVo.getWx_group(), icon_url);
                if (shareDialog != null && shareDialog.isShowing()) {
                    shareDialog.dismiss();
                }
            });
            shareDialog.findViewById(R.id.btn_share_wechat).setOnClickListener(view -> {
                JiuYaoStatisticsApi.getInstance().eventStatistics(6, 91, 2);
                shareToWx(target_url, inviteDataInfoVo.getWx_title(), inviteDataInfoVo.getWx_description(), icon_url);
                if (shareDialog != null && shareDialog.isShowing()) {
                    shareDialog.dismiss();
                }
            });
            shareDialog.findViewById(R.id.btn_share_qq).setOnClickListener(view -> {
                JiuYaoStatisticsApi.getInstance().eventStatistics(6, 91, 3);
                shareToQQ(target_url, inviteDataInfoVo.getQq_title(), inviteDataInfoVo.getQq_description(), icon_url);
                if (shareDialog != null && shareDialog.isShowing()) {
                    shareDialog.dismiss();
                }
            });
            shareDialog.findViewById(R.id.btn_share_qzone).setOnClickListener(view -> {
                JiuYaoStatisticsApi.getInstance().eventStatistics(6, 91, 4);
                shareToQzone(target_url, inviteDataInfoVo.getQq_title(), inviteDataInfoVo.getQqzone_description(), icon_url);
                if (shareDialog != null && shareDialog.isShowing()) {
                    shareDialog.dismiss();
                }
            });
            shareDialog.findViewById(R.id.btn_share_copy).setOnClickListener(view -> {
                JiuYaoStatisticsApi.getInstance().eventStatistics(6, 91, 5);
                String copyStr = inviteDataInfoVo.getCopy_title();
                if (!TextUtils.isEmpty(inviteDataInfoVo.getCopy_description())) {
                    copyStr += "\n" + inviteDataInfoVo.getCopy_description();
                }
                copyStr += "\n" + inviteDataInfoVo.getUrl();
                if (CommonUtils.copyString(_mActivity, copyStr)) {
                    Toaster.show( "链接已复制");
                }

                if (shareDialog != null && shareDialog.isShowing()) {
                    shareDialog.dismiss();
                }
            });
            ImageView iv_qr_code = shareDialog.findViewById(R.id.iv_qr_code);

            final Bitmap bitmap = QRUtils.createQRImage(_mActivity, target_url, iv_qr_code.getMeasuredWidth(), iv_qr_code.getMeasuredHeight());
            iv_qr_code.setImageBitmap(bitmap);
            iv_qr_code.setOnLongClickListener(view -> {
                String fileName = MD5Utils.encode(target_url) + ".png";
                showSave(bitmap, fileName);
                return false;
            });
        }
        shareDialog.show();
    }

    public void showShareInviteFriendNo2Code(InviteDataVo.InviteDataInfoVo inviteDataInfoVo) {
        if (inviteDataInfoVo == null) {
            return;
        }
        if (!hasShareKey()) {
            shareToAndroidSystem(inviteDataInfoVo.getCopy_title(), inviteDataInfoVo.getCopy_description(), inviteDataInfoVo.getUrl());
            return;
        }

        String target_url = inviteDataInfoVo.getUrl();
        String icon_url = inviteDataInfoVo.getIcon();
        if (shareDialog == null) {
            shareDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_invite_friend_no_code, null),
                    ScreenUtils.getScreenWidth(_mActivity), WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);

            shareDialog.findViewById(R.id.btn_share_circle).setOnClickListener(view -> {
                JiuYaoStatisticsApi.getInstance().eventStatistics(6, 91, 1);
                shareToWxCircle(target_url, inviteDataInfoVo.getWx_group(), inviteDataInfoVo.getWx_group(), icon_url);
                if (shareDialog != null && shareDialog.isShowing()) {
                    shareDialog.dismiss();
                }
            });
            shareDialog.findViewById(R.id.btn_share_wechat).setOnClickListener(view -> {
                JiuYaoStatisticsApi.getInstance().eventStatistics(6, 91, 2);
                shareToWx(target_url, inviteDataInfoVo.getWx_title(), inviteDataInfoVo.getWx_description(), icon_url);
                if (shareDialog != null && shareDialog.isShowing()) {
                    shareDialog.dismiss();
                }
            });
            shareDialog.findViewById(R.id.btn_share_qq).setOnClickListener(view -> {
                JiuYaoStatisticsApi.getInstance().eventStatistics(6, 91, 3);
                shareToQQ(target_url, inviteDataInfoVo.getQq_title(), inviteDataInfoVo.getQq_description(), icon_url);
                if (shareDialog != null && shareDialog.isShowing()) {
                    shareDialog.dismiss();
                }
            });
            shareDialog.findViewById(R.id.btn_share_qzone).setOnClickListener(view -> {
                JiuYaoStatisticsApi.getInstance().eventStatistics(6, 91, 4);
                shareToQzone(target_url, inviteDataInfoVo.getQq_title(), inviteDataInfoVo.getQqzone_description(), icon_url);
                if (shareDialog != null && shareDialog.isShowing()) {
                    shareDialog.dismiss();
                }
            });
            shareDialog.findViewById(R.id.btn_share_copy).setOnClickListener(view -> {
                JiuYaoStatisticsApi.getInstance().eventStatistics(6, 91, 5);
                String copyStr = inviteDataInfoVo.getCopy_title();
                if (!TextUtils.isEmpty(inviteDataInfoVo.getCopy_description())) {
                    copyStr += "\n" + inviteDataInfoVo.getCopy_description();
                }
                copyStr += "\n" + inviteDataInfoVo.getUrl();
                if (CommonUtils.copyString(_mActivity, copyStr)) {
                    Toaster.show( "链接已复制");
                }

                if (shareDialog != null && shareDialog.isShowing()) {
                    shareDialog.dismiss();
                }
            });

        }
        shareDialog.show();
    }

    /**
     * 分享到微信朋友圈
     *
     * @param targetUrl
     * @param title
     * @param description
     * @param icon
     */
    private void shareToWxCircle(String targetUrl, String title, String description, String icon) {
        if (iwxapi == null) {
            return;
        }
        try {
            if (isWeiXinAvailable()) {
                WXWebpageObject webPage = new WXWebpageObject();
                webPage.webpageUrl = targetUrl + "?from=wx";
                WXMediaMessage msg = new WXMediaMessage(webPage);
                msg.title = title;
                msg.description = description;

                OkGo.<Bitmap>get(icon)
                        .execute(new BitmapCallback() {
                            @Override
                            public void onSuccess(com.lzy.okgo.model.Response<Bitmap> response) {
                                Bitmap bitmap = response.body();
                                if (bitmap == null) {
                                    return;
                                }
                                msg.thumbData = CommonUtils.compressBitmapByteUnderSize(bitmap, 32);
                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                req.transaction = String.valueOf(System.currentTimeMillis());
                                req.message = msg;
                                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                                iwxapi.sendReq(req);
                            }
                        });

            } else {
                Toaster.show( "未安装微信");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 分享到微信好友
     *
     * @param targetUrl
     * @param title
     * @param description
     * @param icon
     */
    private void shareToWx(String targetUrl, String title, String description, String icon) {
        if (iwxapi == null) {
            return;
        }
        try {
            if (isWeiXinAvailable()) {
                WXWebpageObject webPage = new WXWebpageObject();

                webPage.webpageUrl = targetUrl + "?from=wx";

                WXMediaMessage msg = new WXMediaMessage(webPage);
                msg.title = title;
                msg.description = description;

                OkGo.<Bitmap>get(icon)
                        .execute(new BitmapCallback() {
                            @Override
                            public void onSuccess(com.lzy.okgo.model.Response<Bitmap> response) {
                                Bitmap bitmap = response.body();
                                if (bitmap == null) {
                                    return;
                                }
                                msg.thumbData = CommonUtils.compressBitmapByteUnderSize(response.body(), 32);
                                Logs.e("msg.thumbData.length = " + msg.thumbData.length);

                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                req.transaction = String.valueOf(System.currentTimeMillis());
                                req.message = msg;
                                req.scene = SendMessageToWX.Req.WXSceneSession;
                                boolean result = iwxapi.sendReq(req);
                                Logs.e("result = " + result);
                            }
                        });
            } else {
                Toaster.show( "未安装微信");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 分享到QQ好友
     *
     * @param targetUrl
     * @param title
     * @param description
     * @param icon
     */
    private void shareToQQ(String targetUrl, String title, String description, String icon) {
        try {
            final Bundle params = new Bundle();
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, description);
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, targetUrl);
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, icon);
            mTencent.shareToQQ(_mActivity, params, iUiListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 分享到Qzone
     *
     * @param targetUrl
     * @param title
     * @param description
     * @param icon
     */
    private void shareToQzone(String targetUrl, String title, String description, String icon) {
        try {
            final Bundle params = new Bundle();
            params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
            params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
            params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, targetUrl);
            params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, description);
            params.putString(QzoneShare.SHARE_TO_QQ_IMAGE_URL, icon);

            ArrayList<String> imgUrls = new ArrayList<>();
            imgUrls.add(icon);
            params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgUrls);
            mTencent.shareToQzone(_mActivity, params, iUiListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分享到抖音
     *
     * @param targetUrl
     * @param title
     * @param description
     * @param icon
     */
    private void shareToDouyin(String targetUrl, String title, String description, String icon) {
        DouYinOpenApi douyinOpenApi = DouYinOpenApiFactory.create(_mActivity);

        // 分享html
        ContactHtmlObject htmlObject = new ContactHtmlObject();
        // 你的html链接（必填）
        htmlObject.setHtml(targetUrl);
        // 你的html描述（必填）
        htmlObject.setDiscription(description);
        // 你的html  title（必填）
        htmlObject.setTitle(title);
        // 你的html的封面图(远程图片) （选填，若不填，则使用开放平台官网申请时上传的图标）
        htmlObject.setThumbUrl(icon);
        ShareToContact.Request request = new ShareToContact.Request();
        request.htmlObject = htmlObject;

        // 吊起分享
        if (douyinOpenApi.isAppSupportShareToContacts()) {
            douyinOpenApi.shareToContacts(request);
        } else {
            Toast.makeText(_mActivity, "当前抖音版本不支持", Toast.LENGTH_LONG).show();
        }
    }


    private IUiListener iUiListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            Logs.e("shareToQQ/Qzone------分享成功");
            if (onShareListener != null) {
                onShareListener.onSuccess();
            }
        }

        @Override
        public void onError(UiError uiError) {
            Logs.e("shareToQQ/Qzone------分享失败");
            Logs.e("errorCode=" + uiError.errorCode + "\n" +
                    "errorMessage=" + uiError.errorMessage + "\n" +
                    "errorDetail=" + uiError.errorDetail);

            if (onShareListener != null) {
                onShareListener.onError(uiError.errorMessage);
            }
        }

        @Override
        public void onCancel() {
            Logs.e("shareToQQ/Qzone------分享取消");
            if (onShareListener != null) {
                onShareListener.onCancel();
            }
        }
    };

    public IUiListener getIUiListener() {
        return iUiListener;
    }

    /**
     * 处理微信分享回调
     *
     * @param mWxShareEvent
     */
    public void handleWxShareListener(WxShareEvent mWxShareEvent) {
        if (mWxShareEvent != null) {
            BaseResp baseResp = mWxShareEvent.getBaseResp();
            if (baseResp == null) {
                return;
            }
            int errorCode = baseResp.errCode;

            Logs.e("errorCode = " + errorCode);
            switch (errorCode) {
                case BaseResp.ErrCode.ERR_OK: {
                    Logs.e("分享到微信------分享成功");
                    if (onShareListener != null) {
                        onShareListener.onSuccess();
                    }
                }
                break;
                case BaseResp.ErrCode.ERR_USER_CANCEL: {
                    Logs.e("分享到微信------分享取消");
                    if (onShareListener != null) {
                        onShareListener.onCancel();
                    }
                }
                break;
                case BaseResp.ErrCode.ERR_SENT_FAILED: {
                    Logs.e("分享到微信------分享失败");
                    Logs.e("errorCode=" + errorCode + "\n" +
                            "errorStr=" + baseResp.errStr);
                    if (onShareListener != null) {
                        onShareListener.onError(baseResp.errStr);
                    }
                }
                break;
                default:
                    break;
            }
        }
    }

    /**
     * 保存二维码
     *
     * @param bitmap
     */
    protected void showSave(final Bitmap bitmap, String fileName) {
        if (saveDialog == null) {
            saveDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_tip, null),
                    ScreenUtils.getScreenWidth(_mActivity),
                    WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
            saveDialog.findViewById(R.id.tv_save_pic).setOnClickListener(view -> {
                JiuYaoStatisticsApi.getInstance().eventStatistics(6, 91, 6);
                String filePath = SdCardManager.getInstance().getImageDir().getPath();
                String path = ImageUtils.saveBitmap(bitmap, filePath, fileName);
                if (!TextUtils.isEmpty(path)) {
                    //显示在相册
                    ImageUtils.showImageToGallery(_mActivity, path, fileName);
                    Toaster.show( "二维码保存成功");
                } else {
                    Toaster.show( "保存失败");
                }
                if (saveDialog != null && saveDialog.isShowing()) {
                    saveDialog.dismiss();
                }
            });
            saveDialog.findViewById(R.id.tv_cancel).setOnClickListener(view -> {
                if (saveDialog != null && saveDialog.isShowing()) {
                    saveDialog.dismiss();
                }
            });
        }
        saveDialog.show();
    }

    /**
     * 微信是否可用
     *
     * @return
     */
    private boolean isWeiXinAvailable() {
        return AppUtil.isAppAvailable(_mActivity, "com.tencent.mm");
    }

    /**
     * 手机QQ是否可用
     *
     * @param context
     * @return
     */
    private boolean isQQAvailable(Context context) {
        return AppUtil.isAppAvailable(context, "com.tencent.mobileqq");
    }


    /**
     * bitmap转成byte数组
     *
     * @param bmp
     * @param needRecycle
     * @return
     */
    private byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        Bitmap thumbnailBitmap = CommonUtils.compressBitmapUnderSize(bmp, 32);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        thumbnailBitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            thumbnailBitmap.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 网页分享
     *
     * @param mTitle
     * @param mText
     * @param mTargeUrl
     * @param mImage
     */
    public void showNormalShare(String mTitle, String mText, String mTargeUrl, String mImage) {
        if (!hasShareKey()) {
            shareToAndroidSystem(mTitle, mText, mTargeUrl);
            return;
        }
        CustomDialog normalShareDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_share_url_page, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);

        TextView mBtnShareCircle = normalShareDialog.findViewById(R.id.btn_share_circle);
        TextView mBtnShareWechat = normalShareDialog.findViewById(R.id.btn_share_wechat);
        TextView mBtnShareQq = normalShareDialog.findViewById(R.id.btn_share_qq);
        TextView mBtnShareQzone = normalShareDialog.findViewById(R.id.btn_share_qzone);
        TextView mBtnShareCopy = normalShareDialog.findViewById(R.id.btn_share_copy);


        normalShareDialog.show();

        mBtnShareCopy.setOnClickListener(view -> {
            String copyStr = mTitle;
            if (!TextUtils.isEmpty(mText)) {
                copyStr += "\n" + mText;
            }
            copyStr += "\n" + mTargeUrl;
            if (CommonUtils.copyString(_mActivity, copyStr)) {
                Toaster.show("链接已复制");
            }
            if (normalShareDialog != null && normalShareDialog.isShowing()) {
                normalShareDialog.dismiss();
            }
        });
        mBtnShareCircle.setOnClickListener(view -> {
            shareToWxCircle(mTargeUrl, mTitle, mText, mImage);
            if (normalShareDialog != null && normalShareDialog.isShowing()) {
                normalShareDialog.dismiss();
            }

        });
        mBtnShareWechat.setOnClickListener(view -> {
            shareToWx(mTargeUrl, mTitle, mText, mImage);
            if (normalShareDialog != null && normalShareDialog.isShowing()) {
                normalShareDialog.dismiss();
            }
        });
        mBtnShareQq.setOnClickListener(view -> {
            shareToQQ(mTargeUrl, mTitle, mText, mImage);
            if (normalShareDialog != null && normalShareDialog.isShowing()) {
                normalShareDialog.dismiss();
            }
        });
        mBtnShareQzone.setOnClickListener(view -> {
            shareToQzone(mTargeUrl, mTitle, mText, mImage);
            if (normalShareDialog != null && normalShareDialog.isShowing()) {
                normalShareDialog.dismiss();
            }
        });
    }

    public interface OnShareListener {
        void onSuccess();

        void onError(String error);

        void onCancel();
    }

    public void shareToAndroidSystem(String mTitle, String mText, String mTargeUrl) {
        String content = mTitle;
        if (!TextUtils.isEmpty(mText)) {
            content += "\n" + mText;
        }
        content += "\n" + mTargeUrl;
        Intent textIntent = new Intent(Intent.ACTION_SEND);
        textIntent.setType("text/plain");
        textIntent.putExtra(Intent.EXTRA_TEXT, content);
        textIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _mActivity.startActivity(Intent.createChooser(textIntent, "分享"));
    }

    private boolean hasShareKey() {
        String wxAppId = BuildConfig.WX_APP_ID;
        String qqAppId = BuildConfig.QQ_APP_ID;
        return !TextUtils.isEmpty(wxAppId) && !TextUtils.isEmpty(qqAppId);
    }
}
