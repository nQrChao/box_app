package com.zqhy.app.core.view.user.provincecard;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zqhy.app.App;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.user.MoneyCardChangeVo;
import com.zqhy.app.core.data.model.user.MoneyCardOrderVo;
import com.zqhy.app.core.data.model.user.VipMemberTypeVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.user.provincecard.holder.NewProvinceGameItemHolder;
import com.zqhy.app.core.view.user.provincecard.holder.ProvinceCardChangeHolder;
import com.zqhy.app.core.view.user.provincecard.holder.ProvinceCardOrderHolder;
import com.zqhy.app.core.view.user.provincecard.holder.ProvinceItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.user.VipMemberViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/12
 */

public class ProvinceCardRecordFragment extends BaseFragment<VipMemberViewModel> {

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "购买记录";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_province_card_record;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }


    TextView order;
    TextView change;
    RecyclerView recycler;
    private BaseRecyclerAdapter adapter;
    private BaseRecyclerAdapter adapter1;

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("购买记录");
        findViewById(R.id.title_bottom_line).setVisibility(View.GONE);
        order = findViewById(R.id.order);
        change = findViewById(R.id.change);
        recycler = findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity);
        recycler.setLayoutManager(linearLayoutManager);
        adapter = new BaseRecyclerAdapter.Builder<>()
                .bind(MoneyCardOrderVo.CardOrderInfo.class, new ProvinceCardOrderHolder(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
        adapter1 = new BaseRecyclerAdapter.Builder<>()
                .bind(MoneyCardChangeVo.CardChangeInfo.class, new ProvinceCardChangeHolder(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
        recycler.setAdapter(adapter);
        order.setOnClickListener(view -> {
            order.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            order.setTextColor(Color.parseColor("#333333"));
            change.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            change.setTextColor(Color.parseColor("#999999"));
            getProvinceCardRecord();
        });

        change.setOnClickListener(view -> {
            change.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            change.setTextColor(Color.parseColor("#333333"));
            order.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            order.setTextColor(Color.parseColor("#999999"));
            getProvinceCardRecordChange();
        });
        bindView();
        getProvinceCardRecord();
    }


    private void bindView() {

    }

    private void getProvinceCardRecord() {
        loading();
        mViewModel.getProvinceCardRecord(new OnBaseCallback<MoneyCardOrderVo>() {
            @Override
            public void onAfter() {
                super.onAfter();
                showSuccess();
                loadingComplete();
            }

            @Override
            public void onSuccess(MoneyCardOrderVo data) {
                if (data.isStateOK()) {
                    recycler.setAdapter(adapter);
                    if (data.getData() != null&&data.getData().size()!=0) {
                        adapter.clear();
                        adapter.setDatas(data.getData());
                        adapter.notifyDataSetChanged();
                    }else {
                        adapter.clear();
                        adapter.addData(new EmptyDataVo(R.mipmap.ic_game_detail_coupon_dialog_list_empty_bg));
                    }
                }else {
                    recycler.setAdapter(adapter);
                    adapter.clear();
                    adapter.addData(new EmptyDataVo(R.mipmap.ic_game_detail_coupon_dialog_list_empty_bg));
                }
            }
        });
    }

    private void getProvinceCardRecordChange() {
        loading();
        mViewModel.getProvinceCardRecordChange(new OnBaseCallback<MoneyCardChangeVo>() {
            @Override
            public void onAfter() {
                super.onAfter();
                showSuccess();
                loadingComplete();
            }

            @Override
            public void onSuccess(MoneyCardChangeVo data) {
                if (data.isStateOK()) {
                    recycler.setAdapter(adapter1);
                    if (data.getData() != null&&data.getData().size()!=0) {
                        adapter1.clear();
                        adapter1.setDatas(data.getData());
                        adapter1.notifyDataSetChanged();
                    }else {
                        adapter1.clear();
                        EmptyDataVo emptyDataVo = new EmptyDataVo(R.mipmap.ic_game_detail_coupon_dialog_list_empty_bg);
                        adapter1.addData(emptyDataVo);
                    }
                }else {
                    recycler.setAdapter(adapter1);
                    adapter1.clear();
                    EmptyDataVo emptyDataVo = new EmptyDataVo(R.mipmap.ic_game_detail_coupon_dialog_list_empty_bg);
                    adapter1.addData(emptyDataVo);
                }
            }
        });
    }

}
