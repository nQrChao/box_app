package com.zqhy.app.core.view.recycle;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.adapter.abs.AbsAdapter;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.recycle.XhGameRecycleListVo;
import com.zqhy.app.core.data.model.recycle.XhGameRecycleVo;
import com.zqhy.app.core.data.model.recycle.XhRecycleItemVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.recycle.holder.XhRecycleListItemHolder;
import com.zqhy.app.core.view.user.BindPhoneFragment;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.recycle.RecycleViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.widget.CountDownTimerCopyFromAPI26;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class XhRecycleMainFragment extends BaseListFragment<RecycleViewModel> {

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(XhGameRecycleVo.class, new XhRecycleListItemHolder(_mActivity))
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
        initActionBackBarAndTitle("小号回收");
        mRecyclerView.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_f5f5f5));
        addHeaderView();
        setLoadingMoreEnabled(false);
        setPullRefreshEnabled(true);

        initData();
    }

    @Override
    protected View getTitleRightView() {
        TextView tv = new TextView(_mActivity);
        tv.setText("回收记录");
        tv.setPadding((int) (8 * density), 0, (int) (8 * density), 0);
        tv.setTextSize(12);
        tv.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_868686));

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(24 * density);

        gd.setColor(ContextCompat.getColor(_mActivity, R.color.white));
        gd.setStroke((int) (1 * density), ContextCompat.getColor(_mActivity, R.color.color_cccccc));

        tv.setGravity(Gravity.CENTER);
        tv.setBackground(gd);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (24 * density));
        params.gravity = Gravity.CENTER_VERTICAL;
        params.setMargins(0, 0, (int) (6 * density), 0);
        tv.setLayoutParams(params);

        tv.setOnClickListener(view -> {
            if (checkLogin()) {
                //回收记录
                start(new XhRecycleRecordListFragment());
            }
        });
        return tv;
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

    private LinearLayout mLlXhRecycleChooseGame;
    private TextView mTvGameName;

    private void addHeaderView() {
        View mHeaderView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_xh_recycle_header, null);

        mLlXhRecycleChooseGame = mHeaderView.findViewById(R.id.ll_xh_recycle_choose_game);
        mTvGameName = mHeaderView.findViewById(R.id.tv_game_name);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(48 * density);
        gd.setStroke((int) (0.5 * density), ContextCompat.getColor(_mActivity, R.color.color_f79729));
        mLlXhRecycleChooseGame.setBackground(gd);

        mLlXhRecycleChooseGame.setOnClickListener(v -> {
            showChooseGamePop();
        });

        mLlXhRecycleChooseGame.setVisibility(View.GONE);

        mHeaderView.setLayoutParams(new ViewGroup.LayoutParams(ScreenUtil.getScreenWidth(_mActivity), ViewGroup.LayoutParams.WRAP_CONTENT));
        addHeaderView(mHeaderView);
    }

    /**
     * 初始化gameid
     */
    private int gameid = 0;

    private void initData() {
        getXhListData(gameid);
    }


    private void showChooseGamePop() {
        int anchorWidth = mLlXhRecycleChooseGame.getMeasuredWidth();
        int anchorHeight = mLlXhRecycleChooseGame.getMeasuredHeight();

        int width = anchorWidth;
        int height = (int) (155 * density);

        int[] location = new int[2];
        mLlXhRecycleChooseGame.getLocationOnScreen(location);

        PopupWindow popupWindow = new PopupWindow();
        popupWindow.setContentView(createListView(popupWindow));

        popupWindow.setWidth(width);
        popupWindow.setHeight(height);

        popupWindow.setFocusable(true);

//        popupWindow.showAtLocation(mLlXhRecycleChooseGame, Gravity.NO_GRAVITY,
//                (location[0]) + (int) (10 * density),
//                location[1] + anchorHeight + (int) (2 * density));
        popupWindow.showAsDropDown(mLlXhRecycleChooseGame, 0, 0);
    }

    private View createListView(PopupWindow popupWindow) {
        View view = LayoutInflater.from(_mActivity).inflate(R.layout.layout_pop_xh_game, null);

        RecyclerView mRecyclerView = view.findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));


        GameAdapter gameAdapter = new GameAdapter(_mActivity, popGameListData);
        mRecyclerView.setAdapter(gameAdapter);
        gameAdapter.setOnItemClickListener((v, position, data) -> {
            popupWindow.dismiss();
            if (data != null && data instanceof GameInfoVo) {
                GameInfoVo gameInfoVo = (GameInfoVo) data;
                gameid = gameInfoVo.getGameid();
                if (gameid == 0) {
                    mTvGameName.setText("选择游戏");
                } else {
                    mTvGameName.setText(gameInfoVo.getGamename());
                }

                initData();
            }
        });
        return view;
    }

    private List<GameInfoVo> popGameListData;

    class GameAdapter extends AbsAdapter<GameInfoVo> {

        public GameAdapter(Context context, List<GameInfoVo> labels) {
            super(context, labels);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, GameInfoVo data, int position) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.mTv.setTextColor(ContextCompat.getColor(mContext, R.color.color_818181));
            viewHolder.mTv.setTextSize(12);
            viewHolder.mTv.setText(data.getGamename());

            viewHolder.mTvTag.setVisibility(View.VISIBLE);
            switch (data.getGame_type()) {
                case 1:
                    viewHolder.mTvTag.setText("BT");
                    break;
                case 2:
                    viewHolder.mTvTag.setText("折扣");
                    break;
                case 3:
                    viewHolder.mTvTag.setText("H5");
                    break;
                case 4:
                    viewHolder.mTvTag.setText("单机");
                    break;
                default:
                    viewHolder.mTvTag.setVisibility(View.GONE);
                    break;
            }
        }

        @Override
        public int getLayoutResId() {
            return R.layout.layout_pop_item_xh_game;
        }

        @Override
        public AbsViewHolder createViewHolder(View view) {
            return new ViewHolder(view);
        }


        class ViewHolder extends AbsViewHolder {

            private TextView mTv;
            private TextView mTvTag;

            public ViewHolder(View itemView) {
                super(itemView);
                mTv = findViewById(R.id.tv);
                mTvTag = findViewById(R.id.tv_tag);

            }
        }
    }


    /**
     * 获取可回收小号数据列表
     *
     * @param gameid 选填  不填则表示全部游戏
     */
    private void getXhListData(int gameid) {
        if (mViewModel != null) {
            mViewModel.getRecycleXhList(gameid, new OnBaseCallback<XhGameRecycleListVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(XhGameRecycleListVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            XhGameRecycleListVo.DataBean dataBean = data.getData();
                            if (dataBean != null) {
                                List<GameInfoVo> gameInfoVoList = dataBean.getGame_list();
                                List<XhGameRecycleVo> xhGameRecycleVoList = dataBean.getGame_xh_list();
                                if (gameid == 0) {
                                    if (gameInfoVoList != null && !gameInfoVoList.isEmpty()) {
                                        mLlXhRecycleChooseGame.setVisibility(View.VISIBLE);
                                        //设置游戏选择下拉框数据
                                        if (popGameListData == null) {
                                            popGameListData = new ArrayList<>();
                                        }
                                        popGameListData.clear();
                                        GameInfoVo gameInfoVo = new GameInfoVo();
                                        gameInfoVo.setGameid(0);
                                        gameInfoVo.setGamename("全部");
                                        gameInfoVo.setGame_type(0);
                                        popGameListData.add(gameInfoVo);
                                        popGameListData.addAll(gameInfoVoList);
                                    } else {
                                        mLlXhRecycleChooseGame.setVisibility(View.GONE);
                                    }
                                }
                                clearData();
                                if (xhGameRecycleVoList != null && !xhGameRecycleVoList.isEmpty()) {
                                    addAllData(xhGameRecycleVoList);
                                } else {
                                    addData(new EmptyDataVo(R.mipmap.img_empty_data_1).setLayout(EmptyDataVo.LAYOUT_WRAP_CONTENT));
                                }
                                notifyData();
                            }
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private CustomDialog dialog;

    public void recycleXh(XhGameRecycleVo xhGameRecycleVo, XhRecycleItemVo xhRecycleItemVo) {
        if (dialog == null) {
            dialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_xh_recycle, null),
                    WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
        }
        TextView mTvXhRecyclePrice = dialog.findViewById(R.id.tv_xh_recycle_price);
        TextView mTvGameName = dialog.findViewById(R.id.tv_game_name);
        TextView mTvXhAccount = dialog.findViewById(R.id.tv_xh_account);
        TextView mTvXhRecharge = dialog.findViewById(R.id.tv_xh_recharge);
        TextView mTvUserMobile = dialog.findViewById(R.id.tv_user_mobile);
        EditText mEtVerificationCode = dialog.findViewById(R.id.et_verification_code);
        TextView mTvSendCode = dialog.findViewById(R.id.tv_send_code);
        TextView mTvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView mTvConfirm = dialog.findViewById(R.id.tv_confirm);
        LinearLayout mLlVerificationCode = dialog.findViewById(R.id.ll_verification_code);


        dialog.setOnDismissListener(dialog1 -> {
            if (mEtVerificationCode != null) {
                mEtVerificationCode.getText().clear();
            }
        });

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(48 * density);
        gd.setStroke((int) (1 * density), ContextCompat.getColor(_mActivity, R.color.color_dcdcdc));
        mLlVerificationCode.setBackground(gd);

        String xh_username = xhRecycleItemVo.getXh_username();
        String gamename = xhGameRecycleVo.getGamename();
        String xhAccount = xhRecycleItemVo.getXh_showname();
        String xhRecharge = xhRecycleItemVo.getRmb_total();


        mTvXhRecyclePrice.setText(xhRecycleItemVo.getRecycle_gold());
        mTvGameName.setText("游  戏  名：" + gamename);
        mTvXhAccount.setText("小         号：" + xhAccount);
        mTvXhRecharge.setText("实际充值：" + xhRecharge + "元");


        StringBuilder sb = new StringBuilder();
        String txt1 = "手  机  号：";
        sb.append(txt1);

        if (!UserInfoModel.getInstance().isBindMobile()) {
            sb.append("未绑定手机，去绑定");
        } else {
            sb.append(CommonUtils.getHidePhoneNumber(UserInfoModel.getInstance().getUserInfo().getMobile()));
        }
        SpannableString ss = new SpannableString(sb);
        if (!UserInfoModel.getInstance().isBindMobile()) {
            int startIndex = txt1.length() + 6;
            int endIndex = sb.length();

            ss.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    dialog.dismiss();
                    startForResult(BindPhoneFragment.newInstance(false, ""), REQUEST_CODE_BIND_PHONE);
                }
            }, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(_mActivity, R.color.color_11a8ff)), startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        mTvUserMobile.setHighlightColor(Color.parseColor("#36969696"));
        mTvUserMobile.setMovementMethod(LinkMovementMethod.getInstance());
        mTvUserMobile.setText(ss);

        mTvSendCode.setOnClickListener(v -> {
            int gameid = xhGameRecycleVo.getGameid();
            getRecycleCode(gameid, mTvSendCode);
        });
        mTvCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        mTvConfirm.setOnClickListener(v -> {
            String code = mEtVerificationCode.getText().toString().trim();
            if (TextUtils.isEmpty(code)) {
                Toaster.show("请输入验证码");
                return;
            }
            confirmRecycleXh(xh_username, code, dialog);
        });

        dialog.show();
    }

    private void confirmRecycleXh(String xh_username, String code, CustomDialog dialog) {
        if (mViewModel != null) {
            mViewModel.xhRecycleAction(xh_username, code, new OnBaseCallback() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    loadingComplete();
                }

                @Override
                public void onBefore() {
                    super.onBefore();
                    loading();
                }

                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (dialog != null) {
                                dialog.dismiss();
                            }
                            Toaster.show( "回收成功");
                            initData();
                            mViewModel.refreshUserData();
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    /**
     * 小号回收
     * 获取短信验证码
     *
     * @param mTvSendCode
     */
    private void getRecycleCode(int gameid, TextView mTvSendCode) {
        if (mViewModel != null) {
            mViewModel.getRecycleCode(gameid, new OnBaseCallback() {
                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            sendCode(mTvSendCode);
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void sendCode(TextView mTvSendCode) {
        GradientDrawable gd1 = new GradientDrawable();
        gd1.setCornerRadius(30 * density);
        gd1.setColor(Color.parseColor("#C1C1C1"));
        mTvSendCode.setBackground(gd1);

        addCountDownTimer(new CountDownTimerCopyFromAPI26(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTvSendCode.setEnabled(false);
                mTvSendCode.setText("重新发送" + (millisUntilFinished / 1000) + "秒");
            }

            @Override
            public void onFinish() {
                GradientDrawable gd1 = new GradientDrawable();
                gd1.setCornerRadius(30 * density);
                gd1.setColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
                mTvSendCode.setBackground(gd1);
                mTvSendCode.setText("发送验证码");
                mTvSendCode.setEnabled(true);
            }
        }.start());
    }

    private List<CountDownTimerCopyFromAPI26> countDownTimerList;

    private void addCountDownTimer(CountDownTimerCopyFromAPI26 countDownTimer) {
        if (countDownTimerList == null) {
            countDownTimerList = new ArrayList<>();
        }
        countDownTimerList.add(countDownTimer);
    }

    private void clearCountDownTimerList() {
        if (countDownTimerList != null) {
            for (CountDownTimerCopyFromAPI26 countDownTimer : countDownTimerList) {
                countDownTimer.cancel();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clearCountDownTimerList();
    }

}
