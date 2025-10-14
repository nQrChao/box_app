package com.zqhy.app.core.view.recycle_new;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.recycle.NoXhDataVo;
import com.zqhy.app.core.data.model.recycle.XhGameNewRecycleListVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.recycle.XhRecycleRecordListFragment;
import com.zqhy.app.core.view.transaction.util.CustomPopWindow;
import com.zqhy.app.core.vm.recycle.RecycleViewModel;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leeham2734
 * @date 2021/6/17-10:42
 * @description
 */
public class XhNewRecycleMainFragment extends BaseListFragment<RecycleViewModel> {

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(NoXhDataVo.class, new NoXhDataItemHolder(_mActivity))
                .bind(XhGameNewRecycleListVo.DataBean.class, new XhNewDataItemHolder(_mActivity))
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
    protected String getUmengPageName() {
        return "小号回收";
    }

    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    private FrameLayout mFlTitleRight;
    private TextView title_bottom_line;
    private TextView tv_title;
    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("小号回收");
        setListViewBackgroundColor(Color.parseColor("#FFFFFF"));
        title_bottom_line = findViewById(R.id.title_bottom_line);
        tv_title = findViewById(R.id.tv_title);
        //title_bottom_line.setVisibility(View.GONE);
        mFlTitleRight = findViewById(R.id.fl_title_right);
        mFlTitleRight.addView(getTitleRightView());
        //tv_title.setTextColor(Color.parseColor("#ffffff"));
        addTopHeaderView(_mActivity);
        setLoadingMoreEnabled(true);
        setPullRefreshEnabled(true);
        initData();

    }

    public View getTitleRightView() {
        LinearLayout linearLayout = new LinearLayout(_mActivity);
        TextView tv = new TextView(_mActivity);
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_recycler_more_question);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置边界
        tv.setCompoundDrawablePadding(10);
        tv.setCompoundDrawables(drawable, null, null, null);
        tv.setText("规则说明");
        tv.setPadding((int) (5 * density), 0, (int) (8 * density), 0);
        tv.setTextSize(14);
        tv.setTextColor(Color.parseColor("#232323"));

        tv.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (30 * density));
        params.gravity = Gravity.CENTER_VERTICAL;
        params.setMargins(0, 0, 0, 0);
        tv.setLayoutParams(params);


        tv.setOnClickListener(view -> {
            //BrowserActivity.newInstance(_mActivity, Constants.URL_XH_RECYCLE_RULE);
            BrowserActivity.newInstance(_mActivity, Constants.URL_XH_RECYCLE_RULE);
        });

        linearLayout.setBackgroundResource(R.drawable.shape_fafafa_big_radius_with_f2f2f2_line);
        linearLayout.addView(tv);
        return linearLayout;
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        initData();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        initData();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (page < 0) {
            return;
        }
        page++;
        loadData();
    }

    private void addTopHeaderView(Context context) {
        /*FrameLayout layout = new FrameLayout(context);
        float density = context.getResources().getDisplayMetrics().density;

        TextView leftView = new TextView(context);
        leftView.setText("已回收列表");
        leftView.setTextColor(ContextCompat.getColor(context, R.color.color_232323));
        leftView.setTextSize(13);
        Drawable left = context.getResources().getDrawable(R.mipmap.ic_xh_new_recycle_main_left);
        leftView.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
        leftView.setCompoundDrawablePadding((int) (3 * density));
        leftView.setGravity(Gravity.CENTER);
        leftView.setPadding((int) (10 * density), (int) (5 * density), (int) (10 * density), (int) (5 * density));
        leftView.setIncludeFontPadding(false);
        leftView.setBackgroundResource(R.drawable.shape_white_radius);

        FrameLayout.LayoutParams leftParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        leftParams.gravity = Gravity.CENTER_VERTICAL;
        layout.addView(leftView, leftParams);
        leftView.setOnClickListener(view -> {
            start(new XhRecycleRecordListFragment());
        });

        TextView rightView = new TextView(context);
        rightView.setText("回收规则");
        rightView.setTextColor(ContextCompat.getColor(context, R.color.color_377aff));
        rightView.setTextSize(13);
        Drawable right = context.getResources().getDrawable(R.mipmap.ic_xh_new_recycle_main_right);
        rightView.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
        rightView.setCompoundDrawablePadding((int) (3 * density));
        rightView.setGravity(Gravity.CENTER);
        rightView.setPadding((int) (10 * density), (int) (5 * density), (int) (10 * density), (int) (5 * density));
        rightView.setIncludeFontPadding(false);
        rightView.setBackgroundResource(R.drawable.shape_white_radius);

        FrameLayout.LayoutParams rightParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rightParams.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
        layout.addView(rightView, rightParams);
        rightView.setOnClickListener(view -> {
            //            start(new XhRecycleRuleFragment());
            BrowserActivity.newInstance(_mActivity, Constants.URL_XH_RECYCLE_RULE);
        });

        layout.setLayoutParams(new ViewGroup.LayoutParams(ScreenUtils.getScreenWidth(), ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.setPadding((int) (15 * density), (int) (20 * density), (int) (15 * density), (int) (20 * density));*/

        View inflate = LayoutInflater.from(_mActivity).inflate(R.layout.layout_recycle_main_list_header, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        inflate.setLayoutParams(layoutParams);

        LinearLayout mLlHistory = inflate.findViewById(R.id.ll_history);
        TextView mTvSort = inflate.findViewById(R.id.tv_sort);

        mTvSort.setOnClickListener(v -> {
            showPopupView(mTvSort);
        });

        mLlHistory.setOnClickListener(v -> {
            start(new XhRecycleRecordListFragment());
        });

        addHeaderView(inflate);
    }

    protected int page;
    protected int pageCount;

    @Override
    public int getPageCount() {
        return pageCount;
    }

    private void initData() {
        page = 1;
        loadData();
    }

    private int can = 1;
    private String order = "total";
    private void loadData() {
        if (mViewModel != null) {
            mViewModel.getRecycleNewXhList(can, order, page, pageCount, new OnBaseCallback<XhGameNewRecycleListVo>() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(XhGameNewRecycleListVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null && !data.getData().isEmpty()) {
                                if (page == 1) {
                                    clearData();
                                }
                                addAllData(data.getData());
                                notifyData();
                            } else {
                                if (page == 1) {
                                    clearData();
                                    //empty data;
                                    addData(new NoXhDataVo());
                                    notifyData();
                                }
                                page = -1;
                                setListNoMore(true);
                            }
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private CustomPopWindow popWindow;
    private void showPopupView(TextView tv) {
        View contentView = LayoutInflater.from(_mActivity).inflate(R.layout.pop_recycle_change_sort, null);
        RecyclerView mRecyclerViewPop = contentView.findViewById(R.id.recyclerView_pop);
        popWindow = new CustomPopWindow.PopupWindowBuilder(_mActivity)
                .setView(contentView)
                .setFocusable(true)
                .setBgDarkAlpha(0.7F)
                .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT) //显示大小
                .setOutsideTouchable(true)
                .create();

        mRecyclerViewPop.setLayoutManager(new LinearLayoutManager(_mActivity));

        List<String> list = new ArrayList<>();
        list.add("近期登录顺序");
        list.add("充值金额排序");
        mRecyclerViewPop.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(_mActivity).inflate(R.layout.item_recycle_list_sort, parent, false));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                MyViewHolder mHolder = (MyViewHolder) holder;
                mHolder.mTvDeviceName.setText(list.get(position));
                if (position == list.size() - 1){
                    mHolder.mViewLine.setVisibility(View.GONE);
                }else {
                    mHolder.mViewLine.setVisibility(View.VISIBLE);
                }

                mHolder.itemView.setOnClickListener(v -> {
                    tv.setText(list.get(position));
                    if(position == 0){
                        page = 1;
                        order = "login";
                        loadData();
                    }else {
                        page = 1;
                        order = "total";
                        loadData();
                    }
                    popWindow.dissmiss();
                });
            }

            @Override
            public int getItemCount() {
                return list.size();
            }

            class MyViewHolder extends RecyclerView.ViewHolder {
                private TextView mTvDeviceName;
                private View mViewLine;

                public MyViewHolder(@NonNull View itemView) {
                    super(itemView);
                    mTvDeviceName = itemView.findViewById(R.id.tv_device_name);
                    mViewLine = itemView.findViewById(R.id.view_line);
                }
            }
        });

        popWindow.showAsDropDown(tv);//指定view下方
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == XhNewDataItemHolder.XH_RECYCLE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                initData();
            }
        }
    }
}
