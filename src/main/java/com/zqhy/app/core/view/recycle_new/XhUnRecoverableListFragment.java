package com.zqhy.app.core.view.recycle_new;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.recycle.XhGameNewRecycleListVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.recycle.RecycleViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class XhUnRecoverableListFragment extends BaseListFragment<RecycleViewModel> {

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(XhGameNewRecycleListVo.DataBean.class, new XhNoRecoverableItemHolder(_mActivity))
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

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("不可回收");
        addHeaderView();
        setPullRefreshEnabled(true);
        setLoadingMoreEnabled(true);
        setListViewBackgroundColor(Color.parseColor("#FFFFFF"));
        initData();
    }

    private TextView mXhRecycledCount;
    private void addHeaderView() {
        View mHeaderView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_header_xh_recycle_record_1, null);
        mXhRecycledCount = mHeaderView.findViewById(R.id.xh_recycled_count);
        mHeaderView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addHeaderView(mHeaderView);
    }


    private int page = 1, pageCount = 12;

    @Override
    public int getPageCount() {
        return pageCount;
    }


    @Override
    public void onRefresh() {
        super.onRefresh();
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

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        initData();
    }

    private void initData() {
        page = 1;
        loadData();
    }

    private int can = 0;
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
                                    addData(new EmptyDataVo(R.mipmap.img_empty_data_1).setLayout(EmptyDataVo.LAYOUT_WRAP_CONTENT));
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

    public void showTipDialog() {
        CustomDialog dialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_un_recoverable_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

        TextView tv_tips = dialog.findViewById(R.id.tv_tips);
        Button mBtnCancel = dialog.findViewById(R.id.btn_cancel);

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("3、小号回收条件会根据实际运营情况调整，更多不满足回收条件，请点击前往回收规则查看。");
        spannableStringBuilder.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.parseColor("#4E76FF"));
            }

            @Override
            public void onClick(@NonNull View widget) {
                BrowserActivity.newInstance(_mActivity, Constants.URL_XH_RECYCLE_RULE);
            }
        }, spannableStringBuilder.length() - 7, spannableStringBuilder.length() - 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_tips.setText(spannableStringBuilder);
        tv_tips.setMovementMethod(LinkMovementMethod.getInstance());

        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        mBtnCancel.setOnClickListener(view -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
