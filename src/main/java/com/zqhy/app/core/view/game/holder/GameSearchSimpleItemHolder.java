package com.zqhy.app.core.view.game.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.game.search.GameSimpleInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class GameSearchSimpleItemHolder extends BaseItemHolder<GameSimpleInfoVo, GameSearchSimpleItemHolder.ViewHolder> {


    private float density;

    public GameSearchSimpleItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_search_game_2;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameSimpleInfoVo item) {
        if (position == listSize - 1) {
            holder.mViewLine.setVisibility(View.GONE);
        } else {
            holder.mViewLine.setVisibility(View.VISIBLE);
        }
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(density * 10);
        holder.mTvGameTag.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        if (item.getGame_type() == 1) {
            holder.mTvGameTag.setText("BT");
            gd.setColor(Color.parseColor("#1AFF5400"));
            holder.mTvGameTag.setTextColor(ContextCompat.getColor(mContext, R.color.color_ff5400));
        } else if (item.getGame_type() == 2) {
            holder.mTvGameTag.setText("折扣");
            gd.setColor(Color.parseColor("#1AFF3787"));
            holder.mTvGameTag.setTextColor(ContextCompat.getColor(mContext, R.color.color_ff3787));
        } else if (item.getGame_type() == 3) {
            holder.mTvGameTag.setText("H5");
            gd.setColor(Color.parseColor("#1A56ACFF"));
            holder.mTvGameTag.setTextColor(ContextCompat.getColor(mContext, R.color.color_56acff));
        } else if (item.getGame_type() == 4) {
            holder.mTvGameTag.setText("单机");
            gd.setColor(Color.parseColor("#1A8D7DFF"));
            holder.mTvGameTag.setTextColor(ContextCompat.getColor(mContext, R.color.color_8d7dff));
        }
        holder.mTvGameTag.setBackground(gd);

        holder.mTvGameName.setTextColor(ContextCompat.getColor(mContext, R.color.color_818181));
        String searchValue = item.getSearchValue();
        if (!TextUtils.isEmpty(searchValue) && !TextUtils.isEmpty(item.getGamename())) {
            int startIndex = item.getGamename().indexOf(searchValue);
            if (startIndex != -1) {
                int endIndex = startIndex + searchValue.length();
                SpannableString ss = new SpannableString(item.getGamename());
                ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_333333)), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ss.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                holder.mTvGameName.setText(ss);
//                holder.mTvGameName.setMovementMethod(LinkMovementMethod.getInstance());
            } else {
                holder.mTvGameName.setText(item.getGamename());
            }
        } else {
            holder.mTvGameName.setText(item.getGamename());
        }


        if (item.getGame_type() == 1) {
            holder.mTvGameTag2.setText("");
        } else {
            if (item.showDiscount() == 0) {
                holder.mTvGameTag2.setText("");
            } else {
                holder.mTvGameTag2.setText(item.getDiscount() + "折");
            }
        }

        holder.mLlItem.setOnClickListener(v -> {
            if (_mFragment != null) {
                _mFragment.goGameDetail(item.getGameid(), item.getGame_type());
            }
        });

    }

    public class ViewHolder extends AbsHolder {
        private LinearLayout mLlItem;
        private ImageView mIvSearch;
        private TextView mTvGameName;
        private TextView mTvGameTag;
        private TextView mTvGameTag2;
        private View mViewLine;

        public ViewHolder(View view) {
            super(view);
            mLlItem = findViewById(R.id.ll_item);
            mIvSearch = findViewById(R.id.iv_search);
            mTvGameName = findViewById(R.id.tv_game_name);
            mTvGameTag = findViewById(R.id.tv_game_tag);
            mTvGameTag2 = findViewById(R.id.tv_game_tag_2);
            mViewLine = findViewById(R.id.view_line);

        }
    }
}
