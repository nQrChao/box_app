package com.zqhy.app.core.view.rebate.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zqhy.app.adapter.abs.AbsAdapter;
import com.zqhy.app.base.BaseActivity;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.rebate.RebateProVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.rebate.RebateHelpFragment;
import com.zqhy.app.newproject.R;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/16
 */

public class RebateHelper {


    public void showBTRebateProDialog(Activity _mActivity) {
        CustomDialog btRebateProDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_rebate_help, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        RecyclerView mRecyclerView = btRebateProDialog.findViewById(R.id.recyclerView);
        TextView mBtnCancel = btRebateProDialog.findViewById(R.id.btn_cancel);
        TextView mBtnConfirm = btRebateProDialog.findViewById(R.id.btn_confirm);

        initListPro(_mActivity, btRebateProDialog, mRecyclerView);
        mBtnCancel.setOnClickListener(view -> {
            if (btRebateProDialog != null && btRebateProDialog.isShowing()) {
                btRebateProDialog.dismiss();
            }
        });
        mBtnConfirm.setOnClickListener(view -> {
            if (btRebateProDialog != null && btRebateProDialog.isShowing()) {
                btRebateProDialog.dismiss();
            }
            //跳转常见问题
            if (_mActivity instanceof BaseActivity) {
                try {
                    BaseFragment topFragment = (BaseFragment) ((BaseActivity) _mActivity).getTopFragment();

                    topFragment.start(RebateHelpFragment.newInstance(2));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btRebateProDialog.show();
    }

    private void initListPro(Activity _mActivity, Dialog currentDialog, RecyclerView mRecyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setLayoutManager(layoutManager);

        String jsonData = getRebateHelpJson(_mActivity);
        List<RebateProVo> list = null;

        try {
            Type listType = new TypeToken<List<RebateProVo>>() {
            }.getType();
            Gson gson = new Gson();
            list = gson.fromJson(jsonData, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RebateAdapter mRebateAdapter = new RebateAdapter(_mActivity, list);
        mRecyclerView.setAdapter(mRebateAdapter);
        mRebateAdapter.setOnItemClickListener((v, position, data) -> {
            if (currentDialog != null && currentDialog.isShowing()) {
                currentDialog.dismiss();
            }
            //跳转常见问题
            if (_mActivity instanceof BaseActivity) {
                try {
                    BaseFragment topFragment = (BaseFragment) ((BaseActivity) _mActivity).getTopFragment();

                    topFragment.start(RebateHelpFragment.newInstance(2, position));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String getRebateHelpJson(Activity _mActivity) {
        try {
            InputStream is = _mActivity.getAssets().open("rebate_common_problems.json");
            int length = is.available();
            byte[] buffer = new byte[length];
            is.read(buffer);
            String result = new String(buffer, "utf8");
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    class RebateAdapter extends AbsAdapter<RebateProVo> {

        private float density;

        public RebateAdapter(Context context, List labels) {
            super(context, labels);
            density = ScreenUtil.getScreenDensity(mContext);
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, RebateProVo rebateProVo, int position) {
            if (holder instanceof ViewHolder) {
                ViewHolder viewHolder = (ViewHolder) holder;
                GradientDrawable gd = new GradientDrawable();
                gd.setCornerRadius(5 * density);
                gd.setColor(ContextCompat.getColor(mContext, R.color.color_f5f5f5));
                viewHolder.mLlRootview.setBackground(gd);

                viewHolder.mTvTxt.setText(rebateProVo.getPro_title());
            }
        }

        @Override
        public int getLayoutResId() {
            return R.layout.item_rebate_problem_list;
        }

        @Override
        public AbsViewHolder createViewHolder(View view) {
            return new ViewHolder(view);
        }

        class ViewHolder extends AbsAdapter.AbsViewHolder {
            private LinearLayout mLlRootview;
            private TextView mTvTxt;

            public ViewHolder(View itemView) {
                super(itemView);
                mLlRootview = itemView.findViewById(R.id.ll_rootview);
                mTvTxt = itemView.findViewById(R.id.tv_txt);

            }
        }
    }
}
