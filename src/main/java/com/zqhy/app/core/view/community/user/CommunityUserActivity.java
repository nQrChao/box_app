package com.zqhy.app.core.view.community.user;

import android.os.Bundle;
import android.text.TextUtils;

import com.zqhy.app.base.BaseActivity;
import com.zqhy.app.core.data.model.chat.ChatTokenVo;
import com.zqhy.app.core.data.model.chat.ChatUserVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.FragmentHolderActivity;
import com.zqhy.app.core.view.game.GameDetailInfoFragment;
import com.zqhy.app.core.vm.main.MainViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;

import cn.jzvd.Jzvd;


/**
 * @author Administrator
 */
public class CommunityUserActivity extends BaseActivity<MainViewModel> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_fragment_holder;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);

        /*String accid = getIntent().getStringExtra(RouterConstant.KEY_ACCOUNT_ID_KEY);
        if (!TextUtils.isEmpty(accid)){
            getUidByAccount(accid);
        }else{
            showSuccess();
            loadRootFragment(R.id.content, CommunityUserFragment.newInstance(UserInfoModel.getInstance().getUID()));
        }*/
    }

    @Override
    protected Object getStateEventKey() {
        return null;
    }

    private void getUidByAccount(String accid){
        if (mViewModel != null) {
            mViewModel.getUidByAccount(accid, new OnBaseCallback<ChatUserVo>(){
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                }

                @Override
                public void onSuccess(ChatUserVo data) {
                    if (data != null && !TextUtils.isEmpty(data.getData())){
                        loadRootFragment(R.id.content, CommunityUserFragment.newInstance(Integer.parseInt(data.getData())));
                    }
                }
            });
        }
    }
}
