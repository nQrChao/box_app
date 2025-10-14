package com.zqhy.app.core.view.transaction.holder;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.transaction.TradeGoodInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.transaction.base.TransactionGoodItemActionHelper;
import com.zqhy.app.core.view.transaction.record.TransactionRecordListFragment;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.widget.imageview.ClipRoundImageView;

import cn.iwgang.countdownview.CountdownView;

/**
 * @author Administrator
 */
public class TradeRecordItemHolder1 extends AbsItemHolder<TradeGoodInfoVo, TradeRecordItemHolder1.ViewHolder> {

    public static final int action_modify_good = 0x754;
    public static final int action_good_detail = 0x764;
    public static final int action_buy_good = 0x774;

    private float density;

    private Activity _mActivity;
    private BaseFragment _mChildFragment;

    public TradeRecordItemHolder1(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(context);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        if (_mFragment != null) {
            _mActivity = _mFragment.getActivity();
            _mChildFragment = (BaseFragment) view.getTag(R.id.tag_sub_fragment);
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_transaction_record1;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TradeGoodInfoVo item) {
        TransactionGoodItemActionHelper mHelper = new TransactionGoodItemActionHelper(_mFragment);

        GlideUtils.loadRoundImage(mContext, item.getGoods_pic(), holder.mIvTransactionImage, R.mipmap.ic_placeholder);

        holder.mTvTransactionTitle.setText(item.getGoods_title());
        holder.mTvTransactionGameName.setText(item.getGamename());
        holder.mTvTransactionPrice.setText(item.getGoods_price());

        holder.mTvBtnAction1.setVisibility(View.GONE);
        holder.mTvBtnAction2.setVisibility(View.GONE);

        holder.mTvTransactionFailReason.setVisibility(View.GONE);
        holder.mTvTransactionTime.setVisibility(View.GONE);
        holder.mTvCountDownTransactionTime.setVisibility(View.GONE);

        //下划线
        holder.mTvTransactionGoodStatus.getPaint().setFlags(Paint.ANTI_ALIAS_FLAG);
        holder.mTvTransactionGoodStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_333333));


        holder.mTvTransactionXhRecharge.setText("小号累充" + CommonUtils.saveTwoSizePoint(item.getXh_pay_game_total()) + "元");

        //商品状态
        String value = "";
        switch (item.getGoods_status()) {
            case 1: {
                value = "待审核";
                holder.mTvBtnAction1.setVisibility(View.VISIBLE);
                holder.mTvBtnAction1.setText("修改");
                holder.mTvBtnAction1.setOnClickListener(view -> {
                    //修改商品属性
                    modifyGoodItem(mHelper, item.getGid());
                });
                holder.mTvBtnAction2.setVisibility(View.VISIBLE);
                holder.mTvBtnAction2.setText("下架");
                holder.mTvBtnAction2.setOnClickListener(view -> {
                    //商品下架
                    stopSelling(mHelper, item.getGid());
                });
                if (item.getGame_is_close() == 1) {
                    value = "该游戏暂不支持账号交易";
                    holder.mTvTransactionGoodStatus.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_ff0000));
                    holder.mTvBtnAction1.setVisibility(View.GONE);
                }
                GradientDrawable gd1 = new GradientDrawable();
                gd1.setCornerRadius(32 * density);
                gd1.setStroke((int) (0.5 * density), ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
                holder.mTvBtnAction1.setBackground(gd1);
                holder.mTvBtnAction1.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));

                GradientDrawable gd2 = new GradientDrawable();
                gd2.setCornerRadius(32 * density);
                gd2.setStroke((int) (0.5 * density), ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
                holder.mTvBtnAction2.setBackground(gd2);
                holder.mTvBtnAction2.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
                holder.mTvBtnAction1.setVisibility(View.GONE);
            }
            break;
            case 2: {
                value = "审核中";
                holder.mTvTransactionGoodStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));
                if (item.getGame_is_close() == 1) {
                    value = "该游戏暂不支持账号交易";
                    holder.mTvTransactionGoodStatus.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_ff0000));

                    holder.mTvBtnAction1.setVisibility(View.VISIBLE);
                    holder.mTvBtnAction1.setText("下架");
                    holder.mTvBtnAction1.setOnClickListener(view -> {
                        //商品下架
                        stopSelling(mHelper, item.getGid());
                    });
                    GradientDrawable gd1 = new GradientDrawable();
                    gd1.setCornerRadius(32 * density);
                    gd1.setStroke((int) (0.5 * density), ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
                    holder.mTvBtnAction1.setBackground(gd1);
                    holder.mTvBtnAction1.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
                }
            }
            break;
            case 3: {
                value = "出售中";
                holder.mTvBtnAction1.setVisibility(View.VISIBLE);
                holder.mTvBtnAction1.setText("改价");
                holder.mTvBtnAction1.setOnClickListener(view -> {
                    //修改价格
                    changeGoodPrice(mHelper, item.getGid(), item.getGoods_price(), item.getGameid(), item.getCan_bargain(), item.getAuto_price());
                });

                holder.mTvBtnAction2.setVisibility(View.VISIBLE);
                holder.mTvBtnAction2.setText("下架");
                holder.mTvBtnAction2.setOnClickListener(view -> {
                    //商品下架
                    stopSellingWithTips(mHelper, item.getGid());
                });

                if (item.getGame_is_close() == 1) {
                    value = "该游戏暂不支持账号交易";
                    holder.mTvTransactionGoodStatus.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_ff0000));
                    holder.mTvBtnAction1.setVisibility(View.GONE);
                }
                GradientDrawable gd1 = new GradientDrawable();
                gd1.setCornerRadius(32 * density);
                gd1.setStroke((int) (0.5 * density), ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
                holder.mTvBtnAction1.setBackground(gd1);
                holder.mTvBtnAction1.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));

                GradientDrawable gd2 = new GradientDrawable();
                gd2.setCornerRadius(32 * density);
                gd2.setStroke((int) (0.5 * density), ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
                holder.mTvBtnAction2.setBackground(gd2);
                holder.mTvBtnAction2.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
            }
            break;
            case 4: {
                value = "交易中";
                holder.mTvTransactionGoodStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_007aff));
                if (item.getIs_seller() != 1) {
                    //买家
                    holder.mTvBtnAction1.setVisibility(View.VISIBLE);
                    holder.mTvBtnAction1.setText("立即付款");
                    holder.mTvBtnAction1.setOnClickListener(view -> {
                        //立即付款
                        String gid = item.getGid();
                        String good_pic = item.getGoods_pic();
                        String good_title = item.getGoods_title();
                        String gamename = item.getGamename();
                        String good_price = item.getGoods_price();
                        String gameid = item.getGameid();
                        String game_type = item.getGame_type();
                        if (_mFragment != null) {
                            //_mFragment.startForResult(TransactionBuyFragment.newInstance(gid, good_pic, good_title, gamename, good_price, gameid, game_type, 1), action_buy_good);
                        }
                    });
                    GradientDrawable gd1 = new GradientDrawable();
                    gd1.setCornerRadius(32 * density);
                    gd1.setStroke((int) (0.5 * density), ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
                    holder.mTvBtnAction1.setBackground(gd1);
                    holder.mTvBtnAction1.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));


                    holder.mTvBtnAction2.setVisibility(View.VISIBLE);
                    holder.mTvBtnAction2.setText("删除");
                    holder.mTvBtnAction2.setOnClickListener(view -> {
                        //交易中，删除记录
                        cancelTradeGood(mHelper, item.getGid());
                    });
                    GradientDrawable gd2 = new GradientDrawable();
                    gd2.setCornerRadius(32 * density);
                    gd2.setStroke((int) (0.5 * density), ContextCompat.getColor(_mActivity, R.color.color_818181));
                    holder.mTvBtnAction2.setBackground(gd2);
                    holder.mTvBtnAction2.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_818181));

//                    if(holder.countDownTimer == null){
//                        holder.downCount = item.getCount_down() * 1000;
//                    }
//                    holder.countDownTimer = new CountDownTimer(holder.downCount, 1000) {
//                        @Override
//                        public void onTick(long millisUntilFinished) {
//                            holder.downCount = millisUntilFinished;
//                            if (item.getGoods_status() == 4 && item.getIs_seller() != 1) {
//                                holder.mTvTransactionTime.setText("（还剩" + formatTime(millisUntilFinished) + "）");
//                            } else {
//                                holder.countDownTimer.cancel();
//                            }
//                        }
//
//                        @Override
//                        public void onFinish() {
//                            refreshData();
//                        }
//                    }.start();
//                    if (countDownMap != null) {
//                        countDownMap.put(holder.itemView.hashCode(), holder.countDownTimer);
//                    }
                    holder.mTvTransactionTime.setVisibility(View.VISIBLE);
                    holder.mTvTransactionTime.setText("还剩");
                    holder.mTvCountDownTransactionTime.setVisibility(View.VISIBLE);
                    holder.refreshTime(item.getEndTime() - System.currentTimeMillis());
                } else {
                    //卖家
                    if (item.getGame_is_close() == 1) {
                        value = "该游戏暂不支持账号交易";
                        holder.mTvTransactionGoodStatus.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_ff0000));

                        holder.mTvBtnAction1.setVisibility(View.VISIBLE);
                        holder.mTvBtnAction1.setText("下架");
                        holder.mTvBtnAction1.setOnClickListener(view -> {
                            //商品下架
                            stopSelling(mHelper, item.getGid());
                        });
                        GradientDrawable gd1 = new GradientDrawable();
                        gd1.setCornerRadius(32 * density);
                        gd1.setStroke((int) (0.5 * density), ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
                        holder.mTvBtnAction1.setBackground(gd1);
                        holder.mTvBtnAction1.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));

                        holder.mTvTransactionTime.setVisibility(View.GONE);
                    }
                }

            }

            break;
            case 5: {
                holder.mTvTransactionTime.setText(CommonUtils.formatTimeStamp(item.getShow_time() * 1000, "（MM-dd HH:mm）"));
                holder.mTvTransactionTime.setVisibility(View.VISIBLE);
                value = "已购买";
                holder.mTvBtnAction1.setVisibility(View.VISIBLE);
                holder.mTvBtnAction2.setVisibility(View.VISIBLE);
                holder.mTvBtnAction1.setText("如何使用");
                holder.mTvBtnAction2.setText("删除");
                holder.mTvBtnAction1.setOnClickListener(view -> {
                    //如何使用
                    howToUseGoods(mHelper);

                });
                holder.mTvBtnAction2.setOnClickListener(view -> {
                    //删除记录
                    deleteTradeGood(mHelper, item.getGid());
                });

                GradientDrawable gd1 = new GradientDrawable();
                gd1.setCornerRadius(32 * density);
                gd1.setStroke((int) (0.5 * density), ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
                holder.mTvBtnAction1.setBackground(gd1);
                holder.mTvBtnAction1.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));

                GradientDrawable gd2 = new GradientDrawable();
                gd2.setCornerRadius(32 * density);
                gd2.setStroke((int) (0.5 * density), ContextCompat.getColor(_mActivity, R.color.color_818181));
                holder.mTvBtnAction2.setBackground(gd2);
                holder.mTvBtnAction2.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_818181));
            }

            break;
            case 10: {
                holder.mTvTransactionTime.setText(CommonUtils.formatTimeStamp(item.getShow_time() * 1000, "（MM-dd HH:mm）"));
                holder.mTvTransactionTime.setVisibility(View.VISIBLE);

                value = "已出售";
                holder.mTvTransactionGoodStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_ff4949));
                holder.mTvBtnAction2.setVisibility(View.VISIBLE);
                holder.mTvBtnAction2.setText("删除");
                holder.mTvBtnAction2.setOnClickListener(view -> {
                    //删除记录
                    deleteTradeGood(mHelper, item.getGid());
                });

                GradientDrawable gd2 = new GradientDrawable();
                gd2.setCornerRadius(32 * density);
                gd2.setStroke((int) (0.5 * density), ContextCompat.getColor(_mActivity, R.color.color_818181));
                holder.mTvBtnAction2.setBackground(gd2);
                holder.mTvBtnAction2.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_818181));
            }

            break;
            case -1: {
                value = "审核未通过";
                //下划线
                holder.mTvTransactionGoodStatus.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

                holder.mTvTransactionGoodStatus.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_ff4949));
                holder.mTvTransactionGoodStatus.setOnClickListener(view -> {
                    _mFragment.showTransactionRule();
                });

                holder.mTvTransactionFailReason.setVisibility(View.VISIBLE);
                holder.mTvTransactionFailReason.setText(item.getFail_reason());
                holder.mTvTransactionFailReason.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_333333));
                holder.mTvBtnAction1.setVisibility(View.VISIBLE);
                holder.mTvBtnAction1.setText("修改");
                holder.mTvBtnAction1.setOnClickListener(view -> {
                    //修改商品属性
                    modifyGoodItem(mHelper, item.getGid());
                });
                holder.mTvBtnAction2.setVisibility(View.VISIBLE);
                holder.mTvBtnAction2.setText("删除");
                holder.mTvBtnAction2.setOnClickListener(view -> {
                    //删除记录
                    deleteTradeGood(mHelper, item.getGid());
                });

                GradientDrawable gd1 = new GradientDrawable();
                gd1.setCornerRadius(32 * density);
                gd1.setStroke((int) (0.5 * density), ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
                holder.mTvBtnAction1.setBackground(gd1);
                holder.mTvBtnAction1.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));

                GradientDrawable gd2 = new GradientDrawable();
                gd2.setCornerRadius(32 * density);
                gd2.setStroke((int) (0.5 * density), ContextCompat.getColor(_mActivity, R.color.color_818181));
                holder.mTvBtnAction2.setBackground(gd2);
                holder.mTvBtnAction2.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_818181));
            }

            break;
            case -2: {
                value = "已下架";
                holder.mTvTransactionGoodStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));

                holder.mTvBtnAction1.setVisibility(View.VISIBLE);
                holder.mTvBtnAction1.setText("修改");
                holder.mTvBtnAction1.setOnClickListener(view -> {
                    //修改商品详情
                    modifyGoodItem(mHelper, item.getGid());
                });

                GradientDrawable gd1 = new GradientDrawable();
                gd1.setCornerRadius(32 * density);
                gd1.setStroke((int) (0.5 * density), ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
                holder.mTvBtnAction1.setBackground(gd1);
                holder.mTvBtnAction1.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));


                holder.mTvBtnAction2.setVisibility(View.VISIBLE);
                holder.mTvBtnAction2.setText("删除");
                holder.mTvBtnAction2.setOnClickListener(view -> {
                    //删除记录
                    deleteTradeGood(mHelper, item.getGid());
                });

                GradientDrawable gd2 = new GradientDrawable();
                gd2.setCornerRadius(32 * density);
                gd2.setStroke((int) (0.5 * density), ContextCompat.getColor(_mActivity, R.color.color_818181));
                holder.mTvBtnAction2.setBackground(gd2);
                holder.mTvBtnAction2.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_818181));
            }

            break;
            default:
                break;
        }
        holder.mTvTransactionGoodStatus.setText(value);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.mTvCountDownTransactionTime.stop();
    }

    private void deleteTradeGood(TransactionGoodItemActionHelper mHelper, String gid) {
        mHelper.deleteTradeGood(gid, () -> refreshData());
    }

    private void howToUseGoods(TransactionGoodItemActionHelper mHelper) {
        mHelper.howToUseGoods();
    }

    private void cancelTradeGood(TransactionGoodItemActionHelper mHelper, String gid) {
        mHelper.cancelTradeGood(gid, () -> refreshData());
    }

    private void changeGoodPrice(TransactionGoodItemActionHelper mHelper, String gid, String goods_price, String gameid, int can_bargain, int auto_price) {
        mHelper.changeGoodPrice(gameid, gid, goods_price, can_bargain, auto_price, () -> refreshData());
    }

    private void stopSelling(TransactionGoodItemActionHelper mHelper, String gid) {
        mHelper.stopSelling(gid, () -> refreshData());
    }

    private void stopSellingWithTips(TransactionGoodItemActionHelper mHelper, String gid) {
        mHelper.stopSellingWithTips(gid, () -> refreshData());
    }

    private void modifyGoodItem(TransactionGoodItemActionHelper mHelper, String gid) {
        mHelper.modifyGoodItem(gid, action_modify_good);
    }

    private void refreshData() {
        if (_mChildFragment instanceof TransactionRecordListFragment) {
            ((TransactionRecordListFragment) _mChildFragment).refreshData();
        }
    }

    private String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24 * 2;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;

        StringBuffer sb = new StringBuffer();

        if (minute < 10) {
            sb.append("0");
        }
        sb.append(String.valueOf(minute)).append(":");

        if (second < 10) {
            sb.append("0");
        }
        sb.append(String.valueOf(second));
        return sb.toString();
    }


    public class ViewHolder extends AbsHolder {
        private TextView mTvTransactionGoodStatus;
        private TextView mTvTransactionTime;
        private TextView mTvBtnAction1;
        private TextView mTvBtnAction2;
        private TextView mTvTransactionFailReason;
        private ClipRoundImageView mIvTransactionImage;
        private TextView mTvTransactionTitle;
        private TextView mTvTransactionGameName;
        private TextView mTvTransactionPrice;
        private View mViewLine;
        private TextView mTvTransactionXhRecharge;

        private CountdownView mTvCountDownTransactionTime;

        public ViewHolder(View view) {
            super(view);
            mTvTransactionGoodStatus = findViewById(R.id.tv_transaction_good_status);
            mTvTransactionTime = findViewById(R.id.tv_transaction_time);
            mTvBtnAction1 = findViewById(R.id.tv_btn_action_1);
            mTvBtnAction2 = findViewById(R.id.tv_btn_action_2);
            mTvTransactionFailReason = findViewById(R.id.tv_transaction_fail_reason);
            mIvTransactionImage = findViewById(R.id.iv_transaction_image);
            mTvTransactionTitle = findViewById(R.id.tv_transaction_title);
            mTvTransactionGameName = findViewById(R.id.tv_transaction_game_name);
            mTvTransactionPrice = findViewById(R.id.tv_transaction_price);
            mViewLine = findViewById(R.id.view_line);
            mTvCountDownTransactionTime = findViewById(R.id.tv_count_down_transaction_time);
            mTvTransactionXhRecharge = findViewById(R.id.tv_transaction_xh_recharge);


            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(4 * density);
            gd.setColor(Color.parseColor("#21F5BE43"));
            mTvTransactionXhRecharge.setBackground(gd);
            mTvTransactionXhRecharge.setTextColor(ContextCompat.getColor(mContext, R.color.color_ff8f19));
        }

        public void refreshTime(long leftTime) {
            if (leftTime > 0) {
                mTvCountDownTransactionTime.setVisibility(View.VISIBLE);
                mTvCountDownTransactionTime.start(leftTime);
                mTvCountDownTransactionTime.setOnCountdownEndListener(cv -> {
                    mTvCountDownTransactionTime.setVisibility(View.GONE);
                });
            } else {
                mTvCountDownTransactionTime.setVisibility(View.GONE);
                refreshData();
//                mTvCountDownTransactionTime.stop();
//                mTvCountDownTransactionTime.allShowZero();
            }
        }
    }
}
