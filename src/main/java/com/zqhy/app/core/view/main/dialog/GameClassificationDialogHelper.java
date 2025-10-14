package com.zqhy.app.core.view.main.dialog;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import androidx.core.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.main.MainGameClassificationFragment;
import com.zqhy.app.newproject.R;

import java.util.List;

/**
 * @author pc
 * @date 2019/11/29-15:32
 * @description
 */
public class GameClassificationDialogHelper {


    private Context             mContext;
    private OnTabSelectListener onTabSelectListener;
    private float               density;

    public GameClassificationDialogHelper(Context mContext, OnTabSelectListener onTabSelectListener) {
        this.mContext = mContext;
        this.onTabSelectListener = onTabSelectListener;
        density = ScreenUtil.getScreenDensity(mContext);
    }

    private RadioGroup    mRgTab;
    private RadioButton   mRbTabBt;
    private RadioButton   mRbTabDiscount;
    private RadioButton   mRbTabH5;
    private RadioButton   mRbTabSingle;
    private FlexboxLayout mFlexboxLayout1;
    private FlexboxLayout mFlexboxLayout2;
    private FlexboxLayout mFlexboxLayout3;
    private ImageView     mIvClose;

    private CustomDialog gameCenterDialog;

    private int choose_game_type;

    public void showDialog(int game_type, List<MainGameClassificationFragment.GameTabIndicatorVo> gameTabVos) {
        if (gameCenterDialog == null) {
            gameCenterDialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_game_classification, null),
                    ScreenUtil.getScreenWidth(mContext),
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);
            mRgTab = gameCenterDialog.findViewById(R.id.rg_tab);
            mRbTabBt = gameCenterDialog.findViewById(R.id.rb_tab_bt);
            mRbTabDiscount = gameCenterDialog.findViewById(R.id.rb_tab_discount);
            mRbTabH5 = gameCenterDialog.findViewById(R.id.rb_tab_h5);
            mRbTabSingle = gameCenterDialog.findViewById(R.id.rb_tab_single);
            mFlexboxLayout1 = gameCenterDialog.findViewById(R.id.flexbox_layout_1);
            mFlexboxLayout2 = gameCenterDialog.findViewById(R.id.flexbox_layout_2);
            mFlexboxLayout3 = gameCenterDialog.findViewById(R.id.flexbox_layout_3);
            mIvClose = gameCenterDialog.findViewById(R.id.iv_close);

            setSelector(mRbTabBt);
            setSelector(mRbTabDiscount);
            setSelector(mRbTabH5);
            setSelector(mRbTabSingle);

            mRgTab.setOnCheckedChangeListener((radioGroup, i) -> {
                switch (i) {
                    case R.id.rb_tab_bt:
                        choose_game_type = 1;
                        break;
                    case R.id.rb_tab_discount:
                        choose_game_type = 2;
                        break;
                    case R.id.rb_tab_h5:
                        choose_game_type = 3;
                        break;
                    case R.id.rb_tab_single:
                        choose_game_type = 4;
                        break;
                    default:
                        break;
                }
                initFlexLayout3(choose_game_type, gameTabVos);
            });

            mIvClose.setOnClickListener(view -> {
                if (gameCenterDialog != null && gameCenterDialog.isShowing()) {
                    gameCenterDialog.dismiss();
                }
            });

            mRgTab.check(R.id.rb_tab_bt);
            initFlexLayout(gameTabVos);
        }
        refreshFirstTabs(game_type);
        gameCenterDialog.show();
    }

    private void refreshFirstTabs(int game_type) {
        if (mRgTab != null) {
            switch (game_type) {
                case 1:
                    mRgTab.check(R.id.rb_tab_bt);
                    break;
                case 2:
                    mRgTab.check(R.id.rb_tab_discount);
                    break;
                case 3:
                    mRgTab.check(R.id.rb_tab_h5);
                    break;
                case 4:
                    mRgTab.check(R.id.rb_tab_single);
                    break;
            }
        }
        choose_game_type = game_type;
    }

    private void initFlexLayout(List<MainGameClassificationFragment.GameTabIndicatorVo> gameTabVos) {
        if (gameTabVos != null) {
            for (MainGameClassificationFragment.GameTabIndicatorVo gameTabVo : gameTabVos) {
                if (gameTabVo.getType() == 1) {
                    mFlexboxLayout1.addView(createGenreTabView(gameTabVo));
                } else if (gameTabVo.getType() == 2) {
                    mFlexboxLayout2.addView(createGenreTabView(gameTabVo));
                }
            }
        }
    }


    private void initFlexLayout3(int game_type, List<MainGameClassificationFragment.GameTabIndicatorVo> gameTabVos) {
        if (gameTabVos != null) {
            mFlexboxLayout3.removeAllViews();
            for (MainGameClassificationFragment.GameTabIndicatorVo gameTabVo : gameTabVos) {
                if (gameTabVo.getType() == 0) {
                    if (gameTabVo.isContainsGameType(String.valueOf(game_type))) {
                        mFlexboxLayout3.addView(createGenreTabView(gameTabVo));
                    }
                }
            }
        }
    }

    private View createGenreTabView(MainGameClassificationFragment.GameTabIndicatorVo gameTabVo) {
        TextView textView = new TextView(mContext);
        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams((int) (58 * density), (int) (28 * density));
        params.setMargins(0, (int) (6 * density), (int) (12 * density), (int) (6 * density));
        textView.setLayoutParams(params);

        textView.setText(gameTabVo.getTabTitle());
        textView.setTextColor(ContextCompat.getColor(mContext, R.color.color_000000));
        textView.setTextSize(13);
        textView.setGravity(Gravity.CENTER);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(6);
        gd.setColor(ContextCompat.getColor(mContext, R.color.color_f2f2f2));
        textView.setBackground(gd);

        textView.setOnClickListener(view -> {
            if (gameCenterDialog != null && gameCenterDialog.isShowing()) {
                gameCenterDialog.dismiss();
            }
            if (onTabSelectListener != null) {
                onTabSelectListener.onTabSelected(choose_game_type, gameTabVo);
            }
        });

        return textView;
    }


    private void setSelector(RadioButton targetRadioButton) {
        GradientDrawable gdCheck = new GradientDrawable();
        gdCheck.setCornerRadius(4 * density);
        gdCheck.setColor(ContextCompat.getColor(mContext, R.color.color_0052ef));

        GradientDrawable gdNormal = new GradientDrawable();
        gdNormal.setCornerRadius(4 * density);
        gdNormal.setColor(ContextCompat.getColor(mContext, R.color.color_f2f2f2));

        int normalColor = ContextCompat.getColor(mContext, R.color.color_666666);
        int selectedColor = ContextCompat.getColor(mContext, R.color.white);

        setTabButtonBackgroundSelector(targetRadioButton, normalColor, selectedColor, gdNormal, gdCheck);
    }

    /**
     * 设置RadioButton样式
     *
     * @param targetRadioButton
     * @param normalColor
     * @param selectedColor
     * @param drawableDefault
     * @param drawableChecked
     */
    public void setTabButtonBackgroundSelector(RadioButton targetRadioButton,
                                               int normalColor, int selectedColor,
                                               Drawable drawableDefault, Drawable drawableChecked) {
        StateListDrawable stateListDrawable = new StateListDrawable();

        stateListDrawable.addState(new int[]{android.R.attr.state_checked},
                drawableChecked);
        stateListDrawable.addState(new int[]{-android.R.attr.state_checked},
                drawableDefault);
        stateListDrawable.addState(new int[]{android.R.attr.state_selected},
                drawableChecked);
        stateListDrawable.addState(new int[]{-android.R.attr.state_selected},
                drawableDefault);

        targetRadioButton.setBackground(stateListDrawable);

        ColorStateList tabColorState = new ColorStateList(new int[][]{{android.R.attr.state_checked, android.R.attr.state_enabled}, {}},
                new int[]{selectedColor, normalColor});
        targetRadioButton.setTextColor(tabColorState);

    }

    public interface OnTabSelectListener {
        void onTabSelected(int game_type, MainGameClassificationFragment.GameTabIndicatorVo gameTabVo);
    }
}
