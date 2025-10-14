package com.zqhy.app.core.view.community.qa.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.community.qa.GameQaListFragment;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class UserPlayGameItemHolder extends AbsItemHolder<GameInfoVo, UserPlayGameItemHolder.ViewHolder> {

    private float density;
    public UserPlayGameItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_user_game_play;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameInfoVo item) {


        GlideUtils.loadRoundImage(mContext,item.getGameicon(),holder.mIvGameIcon);
        holder.mTvTxt.setText(item.getGamename());
        String strCount = String.valueOf(item.getQuestion_count());
        if (item.getQuestion_count() > 99) {
            strCount = "99+";
        }
        holder.mTvSubTxt.setTextColor(Color.parseColor("#999999"));
        SpannableString sp = new SpannableString("有" + strCount + "条求助");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#11A8FF"));
        sp.setSpan(colorSpan, 1, 1 + strCount.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.mTvSubTxt.setText(sp);
        holder.mTvBtn.setText("帮帮TA");

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(24 * density);
        gd.setColor(Color.parseColor("#ff8f19"));
        holder.mTvBtn.setBackground(gd);
        holder.mTvBtn.setTextColor(Color.parseColor("#ffffff"));

        holder.mLlRootview.setOnClickListener(view -> {
            if(_mFragment != null){
                _mFragment.start(GameQaListFragment.newInstance(item.getGameid()));
            }
        });
    }

    public class ViewHolder extends AbsHolder {
        private LinearLayout mLlRootview;
        private FrameLayout mFlOtherIcon;
        private ImageView mIvGameIcon;
        private TextView mTvTxt;
        private TextView mTvSubTxt;
        private TextView mTvBtn;

        public ViewHolder(View view) {
            super(view);
            mLlRootview = findViewById(R.id.ll_rootview);
            mFlOtherIcon = findViewById(R.id.fl_other_icon);
            mIvGameIcon = findViewById(R.id.iv_game_icon);
            mTvTxt = findViewById(R.id.tv_txt);
            mTvSubTxt = findViewById(R.id.tv_sub_txt);
            mTvBtn = findViewById(R.id.tv_btn);

        }
    }
}
