package com.zqhy.app.core.view.user;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.connection.ConnectionWayInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.vm.connection.ConnectionViewModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author pc
 * @date 2019/12/16-10:08
 * @description
 */
public class OpenPlatformFragment extends BaseFragment<ConnectionViewModel> implements View.OnClickListener {

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_ts_open_platform;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }


    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle("开发平台");
        bindViews();
        initData();
    }

    private void initData() {
        getConnectionInfoData();
    }

    private TextView mTvOpenPlatformQq;
    private TextView mTvCopyQq;
    private TextView mTvOpenPlatformWx;
    private TextView mTvCopyWx;
    private TextView mTvOpenPlatformEmail;
    private TextView mTvCopyEmail;

    private void bindViews() {
        mTvOpenPlatformQq = findViewById(R.id.tv_open_platform_qq);
        mTvCopyQq = findViewById(R.id.tv_copy_qq);
        mTvOpenPlatformWx = findViewById(R.id.tv_open_platform_wx);
        mTvCopyWx = findViewById(R.id.tv_copy_wx);
        mTvOpenPlatformEmail = findViewById(R.id.tv_open_platform_email);
        mTvCopyEmail = findViewById(R.id.tv_copy_email);

        mTvCopyQq.setOnClickListener(this);
        mTvCopyWx.setOnClickListener(this);
        mTvCopyEmail.setOnClickListener(this);


        setButtonBackground(mTvCopyQq);
        setButtonBackground(mTvCopyWx);
        setButtonBackground(mTvCopyEmail);
    }


    private void setButtonBackground(TextView mtv) {
        GradientDrawable gd = new GradientDrawable();
        gd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        gd.setColors(new int[]{Color.parseColor("#FF9900"), Color.parseColor("#FF4E00"),});
        gd.setCornerRadius(32 * density);

        mtv.setBackground(gd);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_copy_qq:
            case R.id.tv_copy_wx:
            case R.id.tv_copy_email:
                String tag = (String) v.getTag();
                if (CommonUtils.copyString(_mActivity, tag)) {
                    Toaster.show( "已复制");
                }
                break;
            default:
                break;
        }
    }


    private void getConnectionInfoData() {
        if (mViewModel != null) {
            mViewModel.getConnectionInfo(new OnBaseCallback<ConnectionWayInfoVo>() {
                @Override
                public void onSuccess(ConnectionWayInfoVo data) {
                    if (data != null && data.isStateOK()) {
                        if (data.getData() != null) {
                            String qq = data.getData().getOpen_platform_qq_number();
                            String wechatId = data.getData().getOpen_platform_wechat_id();
                            String email = data.getData().getOpen_platform_email();
                            mTvOpenPlatformQq.setText("QQ：" + qq);
                            mTvOpenPlatformWx.setText("微信：" + wechatId);
                            mTvOpenPlatformEmail.setText("E-mail：" + email);

                            mTvCopyQq.setTag(qq);
                            mTvCopyWx.setTag(wechatId);
                            mTvCopyEmail.setTag(email);
                        }
                    }
                }
            });
        }
    }
}
