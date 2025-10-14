package com.zqhy.app.base.holder;

import android.content.Context;

import com.zqhy.app.base.BaseActivity;
import com.zqhy.app.core.AppJumpAction;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;
import com.zqhy.app.core.data.model.jump.AppJumpInfoBean;

/**
 * @author Administrator
 * @date 2018/11/18
 */

public abstract class BaseItemHolder<T, VH extends AbsHolder> extends AbsItemHolder<T, VH> {

    public BaseItemHolder(Context context) {
        super(context);
    }

    /**
     * App动态跳转
     *
     * @param page_type
     * @param paramBean
     */
    protected void appJump(String page_type, AppJumpInfoBean.ParamBean paramBean) {
        AppJumpInfoBean appJumpInfoBean = new AppJumpInfoBean(page_type, paramBean);

        if (_mFragment != null) {
            BaseActivity mActivity = (BaseActivity) _mFragment.getActivity();
            AppJumpAction appJumpAction = new AppJumpAction(mActivity);
            appJumpAction.jumpAction(appJumpInfoBean);
        }
    }

    /**
     * App动态跳转
     * @param appBaseJumpInfoBean
     */
    protected void appJump(AppBaseJumpInfoBean appBaseJumpInfoBean) {
        if (_mFragment != null) {
            BaseActivity mActivity = (BaseActivity) _mFragment.getActivity();
            AppJumpAction appJumpAction = new AppJumpAction(mActivity);
            appJumpAction.jumpAction(appBaseJumpInfoBean);
        }
    }
}
