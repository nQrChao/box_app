package com.zqhy.app.core.vm.user;

import android.app.Application;
import androidx.annotation.NonNull;

import com.zqhy.app.core.data.repository.user.CertificationRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.vm.BaseViewModel;

/**
 * @author Administrator
 * @date 2018/11/12
 */

public class CertificationViewModel extends BaseViewModel<CertificationRepository> {
    public CertificationViewModel(@NonNull Application application) {
        super(application);
    }

    public void userCertification(String sfzname, String sfzid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.userCertification(sfzname, sfzid, onNetWorkListener);
        }
    }

    /**
     * 实名认证
     *
     * @param sfzname           姓名
     * @param sfzid             身份证号
     * @param code              验证码
     * @param mobile            手机号
     * @param is_check          1，新注册或未绑定手机号用户；2，已绑定手机号用户
     * @param onNetWorkListener
     */
    public void userCertificationV2(String sfzname, String sfzid, String code, String mobile, int is_check, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.userCertification(sfzname, sfzid, code, mobile, is_check, onNetWorkListener);
        }
    }


    /**
     * 获取已经实名的数量
     *
     * @param onNetWorkListener
     */
    public void getCertificationNum(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getCertificationNum(onNetWorkListener);
        }
    }

    /**
     * @param mobilePhone 11位手机号
     * @param isCheck     检测, 1:手机号未注册可以发送短信; 2: 手机号注册了才会发送短信
     */
    public void getCode(String mobilePhone, int isCheck, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getCode(mobilePhone, isCheck, onNetWorkListener);
        }
    }
}
