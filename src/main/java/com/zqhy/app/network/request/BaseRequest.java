package com.zqhy.app.network.request;

import android.content.Intent;
import android.text.TextUtils;

import com.box.other.blankj.utilcode.util.Logs;
import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseActivity;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.view.login.LoginActivity;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.network.OKHTTPUtil;
import com.zqhy.app.utils.AppManager;

import java.util.HashMap;
import java.util.Map;


/**
 * 基础请求数据
 * @author 韩国桐
 * @version [0.1,2019-12-24]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BaseRequest {
    public  OKHTTPUtil         okHttpUtils =new OKHTTPUtil();
    private Map<String,String> userData    =new HashMap<>();
    public BaseRequest(){
        UserInfoVo.DataBean dataBean= UserInfoModel.getInstance().getUserInfo();
        if(dataBean!=null&& !TextUtils.isEmpty(dataBean.getToken())&&dataBean.getUid()!=0){
            userData.put("uid",String.valueOf(dataBean.getUid()));
            userData.put("token",String.valueOf(dataBean.getToken()));
        }
    }

    public void setUserMap(Map<String,String> params){
        UserInfoVo.DataBean dataBean= UserInfoModel.getInstance().getUserInfo();
        if(dataBean!=null&& !TextUtils.isEmpty(dataBean.getToken())&&dataBean.getUid()!=0){
            userData.put("uid",String.valueOf(dataBean.getUid()));
            userData.put("token",String.valueOf(dataBean.getToken()));
        }
        if(userData.size()>0){
            params.putAll(userData);
        }
    }

    public void verifyUserLogin(String error) {
        Logs.e("用户token过期");
        try {
            BaseActivity _mActivity = (BaseActivity) AppManager.getInstance().getTopActivity();
            UserInfoModel.getInstance().logout();
            _mActivity.startActivity(new Intent(_mActivity, LoginActivity.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toaster.show(error);
    }

    public interface OnAction{
        void onStart();
        void onEnd();
        void onSuccess(String message);
        void onError(String error);
    }
}
