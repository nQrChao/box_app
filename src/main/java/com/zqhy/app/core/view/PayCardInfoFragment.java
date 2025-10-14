package com.zqhy.app.core.view;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.GetCardInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.game.dialog.CardDialogHelper;
import com.zqhy.app.core.vm.game.GameViewModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author Administrator
 * @date 2018/11/22
 */

public class PayCardInfoFragment extends BaseFragment<GameViewModel> {

    public static PayCardInfoFragment newInstance(GameInfoVo.CardlistBean cardlistBean) {
        PayCardInfoFragment fragment = new PayCardInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("cardlistBean", cardlistBean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_pay_card_info;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    protected GameInfoVo.CardlistBean cardlistBean;
    protected boolean isFromSDK;
    protected String SDKPackageName;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            cardlistBean = (GameInfoVo.CardlistBean) getArguments().getSerializable("cardlistBean");
            isFromSDK = getArguments().getBoolean("isFromSDK", false);
            SDKPackageName = getArguments().getString("SDKPackageName");
        }
        super.initView(state);
        bindViews();
        setStatusBar(0x00000000);
        initActionBackBarAndTitle("");
        setActionBackBar(R.mipmap.ic_actionbar_back_white);
        setTitleBottomLine(View.GONE);

        showSuccess();
    }

    private TextView mTvGiftCount;
    private LinearLayout mLlContentLayout;
    private TextView mTvCardDescription;
    private TextView mTvCardContent;
    private TextView mTvCardUsage;
    private TextView mTvCardValidity;
    private Button mBtnGetCard;
    private TextView mTvCardTitle;

    private void bindViews() {
        mTvCardTitle = findViewById(R.id.tv_card_title);
        mTvGiftCount = findViewById(R.id.tv_gift_count);
        mLlContentLayout = findViewById(R.id.ll_content_layout);
        mTvCardDescription = findViewById(R.id.tv_card_description);
        mTvCardContent = findViewById(R.id.tv_card_content);
        mTvCardUsage = findViewById(R.id.tv_card_usage);
        mTvCardValidity = findViewById(R.id.tv_card_validity);
        mBtnGetCard = findViewById(R.id.btn_get_card);

        mTvCardTitle.setText(cardlistBean.getCardname());


        GradientDrawable gd = new GradientDrawable();

        gd.setCornerRadius(15 * density);
        gd.setColor(ContextCompat.getColor(_mActivity, R.color.color_f6a92f));
        mTvGiftCount.setBackground(gd);

        int cardLeftCount = cardlistBean.getCardkucun();
        mTvGiftCount.setText("库存：" + String.valueOf(cardLeftCount));

        StringBuilder sb = new StringBuilder();

        String txt1 = "";
        switch (cardlistBean.getNeed_pay_type()) {
            case 1:
                txt1 = "累计";
                break;
            case 2:
                txt1 = "单笔";
                break;
            default:
                break;
        }

        sb.append("1.");
        if (cardlistBean.getNeed_pay_end() == 0) {
            sb.append("活动期间");
        }
        sb.append(txt1).append("充值达到").append(String.valueOf(cardlistBean.getNeed_pay_total())).append("元，可领取该礼包").append("\n");

        sb.append("2.活动时间为：");
        if (cardlistBean.getNeed_pay_end() == 0) {
            sb.append("长期有效");
        } else {
            String startTime = CommonUtils.formatTimeStamp(cardlistBean.getNeed_pay_begin() * 1000, "yyyy-MM-dd HH:mm");
            String endTime = CommonUtils.formatTimeStamp(cardlistBean.getNeed_pay_end() * 1000, "yyyy-MM-dd HH:mm");
            String str = startTime + " - " + endTime;
            sb.append(str);
        }
        sb.append("\n");
        sb.append("3.该礼包领完即止，达到要求的亲亲请尽快领取哦~");

        SpannableString spannableString = new SpannableString(sb.toString());
        mTvCardDescription.setText(spannableString);

        mTvCardContent.setText(cardlistBean.getCardcontent());

        if (!TextUtils.isEmpty(cardlistBean.getCardusage())) {
            mTvCardUsage.setText(cardlistBean.getCardusage());
        } else {
            mTvCardUsage.setText("请在游戏内兑换使用");
        }
        if (!TextUtils.isEmpty(cardlistBean.getYouxiaoqi())) {
            mTvCardValidity.setText(cardlistBean.getYouxiaoqi());
        } else {
            mTvCardValidity.setText("暂无");
        }

        mBtnGetCard.setOnClickListener(view -> {
            getCard();
        });
    }


    private CardDialogHelper cardDialogHelper;

    private void getCard() {
        if (checkLogin()) {
            if (checkUserBindPhone("领取提示", "绑定手机后\n即可领取海量礼包福利！")) {
                if (mViewModel != null) {
                    mViewModel.getCardInfo(cardlistBean.getGameid(), cardlistBean.getCardid(), new OnBaseCallback<GetCardInfoVo>() {
                        @Override
                        public void onSuccess(GetCardInfoVo getCardInfoVo) {
                            if (getCardInfoVo != null) {
                                if (getCardInfoVo.isStateOK()) {
                                    if (getCardInfoVo.getData() == null) {
                                        if (cardDialogHelper != null) {
                                            cardDialogHelper = new CardDialogHelper(PayCardInfoFragment.this);
                                        }
                                        cardDialogHelper.showGiftDialog(getCardInfoVo.getData().getCard(), isFromSDK, SDKPackageName);
                                    }
                                } else {
                                    Toaster.show(getCardInfoVo.getMsg());
                                    //ToastT.error(_mActivity, getCardInfoVo.getMsg());
                                }
                            }
                        }
                    });
                }
            }
        }
    }
}
