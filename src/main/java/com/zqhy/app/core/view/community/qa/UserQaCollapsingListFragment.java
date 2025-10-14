package com.zqhy.app.core.view.community.qa;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.community.qa.QaCenterQuestionListVo;
import com.zqhy.app.core.data.model.community.qa.UserQaCenterInfoVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.community.qa.holder.QaCollapsingCenterItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.community.qa.QaViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class UserQaCollapsingListFragment extends BaseListFragment<QaViewModel> {

    private final int ID_TAB_1 = 0x001;
    private final int ID_TAB_2 = 0x002;

    public static UserQaCollapsingListFragment newInstance(int user_id) {
        UserQaCollapsingListFragment fragment = new UserQaCollapsingListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", 2);
        bundle.putInt("user_id", user_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static UserQaCollapsingListFragment newInstance(int type, int user_id) {
        UserQaCollapsingListFragment fragment = new UserQaCollapsingListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("user_id", user_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(UserQaCenterInfoVo.QaCenterQuestionVo.class, new QaCollapsingCenterItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }


    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    private int user_id;
    private int type;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            type = getArguments().getInt("type");
            user_id = getArguments().getInt("user_id");
        }
        super.initView(state);
        initActionBackBarAndTitle(getTitle());
        setTitleBottomLine(View.GONE);
        addFixHeaderView();

        if (type == 1) {
            onTab1Selected();
        } else if (type == 2) {
            onTab2Selected();
        }
    }

    private String getTitle() {
        if (UserInfoModel.getInstance().isLogined()) {
            int uid = UserInfoModel.getInstance().getUserInfo().getUid();
            if (uid == user_id) {
                return "我的问答";
            }
        }
        return "TA的问答";
    }

    @Override
    protected View getTitleRightView() {
        ImageView mIvQuestion = new ImageView(_mActivity);
        mIvQuestion.setImageResource(R.mipmap.ic_message_common);
        int padding = (int) (6 * density);
        mIvQuestion.setPadding(padding, padding, padding, padding);

        mIvQuestion.setOnClickListener(v -> goMessageCenter());

        return null;
    }

    TextView mTabBtn1, mTabBtn2;

    private void addFixHeaderView() {
        LinearLayout mFixHeaderView = new LinearLayout(_mActivity);
        mFixHeaderView.setOrientation(LinearLayout.HORIZONTAL);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, (int) (48 * density));
        params.gravity = Gravity.CENTER;
        mFixHeaderView.setLayoutParams(params);
        mFixHeaderView.setGravity(Gravity.CENTER);


        mTabBtn1 = new TextView(_mActivity);
        mTabBtn1.setId(ID_TAB_1);
        LinearLayout.LayoutParams btnParams1 = new LinearLayout.LayoutParams((int) (115 * density), (int) (30 * density));
        btnParams1.setMargins((int) (6 * density), 0, (int) (6 * density), 0);
        mTabBtn1.setLayoutParams(btnParams1);
        mTabBtn1.setText("回答");
        mTabBtn1.setTextSize(15);
        mTabBtn1.setGravity(Gravity.CENTER);
        mTabBtn1.setOnClickListener(v -> switchTabView(v));

        mTabBtn2 = new TextView(_mActivity);
        mTabBtn2.setId(ID_TAB_2);
        LinearLayout.LayoutParams btnParams2 = new LinearLayout.LayoutParams((int) (115 * density), (int) (30 * density));
        btnParams2.setMargins((int) (6 * density), 0, (int) (6 * density), 0);
        mTabBtn2.setLayoutParams(btnParams2);
        mTabBtn2.setText("提问");
        mTabBtn2.setTextSize(15);
        mTabBtn2.setGravity(Gravity.CENTER);

        mTabBtn2.setOnClickListener(v -> switchTabView(v));

        mFixHeaderView.addView(mTabBtn2);
        mFixHeaderView.addView(mTabBtn1);

        mFlListFixTop.addView(mFixHeaderView);
    }

    private void switchTabView(View v) {
        if (v == null) {
            return;
        }
        switch (v.getId()) {
            case ID_TAB_1:
                onTab1Selected();
                break;
            case ID_TAB_2:
                onTab2Selected();
                break;
            default:
                break;
        }
    }

    private void onTab1Selected() {
        if (mTabBtn1 != null) {
            mTabBtn1.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));
            mTabBtn1.setBackgroundResource(R.drawable.shape_btn_gradient_default);
        }
        if (mTabBtn2 != null) {
            mTabBtn2.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_9b9b9b));
            GradientDrawable gd = new GradientDrawable();
            gd.setColor(ContextCompat.getColor(_mActivity, R.color.color_f2f2f2));
            gd.setCornerRadius(48 * density);
            mTabBtn2.setBackground(gd);
        }
        type = 1;
        initData();
    }

    private void onTab2Selected() {
        if (mTabBtn1 != null) {
            mTabBtn1.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_9b9b9b));
            GradientDrawable gd = new GradientDrawable();
            gd.setColor(ContextCompat.getColor(_mActivity, R.color.color_f2f2f2));
            gd.setCornerRadius(48 * density);
            mTabBtn1.setBackground(gd);
        }
        if (mTabBtn2 != null) {
            mTabBtn2.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));
            mTabBtn2.setBackgroundResource(R.drawable.shape_btn_gradient_default);
        }
        type = 2;
        initData();
    }


    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        initData();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        initData();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        page++;
        getUserQaListData();
    }

    private int page = 1, pageCount = 12;

    @Override
    public int getPageCount() {
        return pageCount;
    }

    private void initData() {
        page = 1;
        getUserQaListData();
    }

    private void getUserQaListData() {
        if (mViewModel != null) {
            mViewModel.getUserQaListData(type, user_id, page, pageCount, new OnBaseCallback<QaCenterQuestionListVo>() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(QaCenterQuestionListVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                if (page == 1) {
                                    clearData();
                                }
                                addAllData(data.getData());
                            } else {
                                if (page == 1) {
                                    clearData();
                                    addData(new EmptyDataVo(R.mipmap.img_empty_data_1));
                                }
                                setListNoMore(true);
                                notifyData();
                            }
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }

}
