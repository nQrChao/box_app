package com.zqhy.app.core.view.main.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.mainpage.GameTagVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.newproject.R;

/**
 * @author leeham2734
 * @date 2020/5/9-15:09
 * @description
 */
public class GameTagItemHolder extends AbsItemHolder<GameTagVo, GameTagItemHolder.ViewHolder> {

    public GameTagItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_main_page_tag;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameTagVo item) {
        try {
            holder.mFlexBoxLayout.removeAllViews();
            if (item.getTags() != null && item.getTags().length > 0) {
                holder.mFlexBoxLayout.setVisibility(View.VISIBLE);
                for (String tag : item.getTags()) {
                    View view = createTabView(tag);
                    holder.mFlexBoxLayout.addView(view);
                }
            } else {
                holder.mFlexBoxLayout.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View createTabView(String label_name) {
        TextView view = new TextView(mContext);
        view.setTextSize(12);
        view.setTextColor(ContextCompat.getColor(mContext, R.color.color_858585));

        view.setText(label_name);
        view.setCompoundDrawablePadding((int) (4 * ScreenUtil.getScreenDensity(mContext)));
        view.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_game_main_page_tag), null, null, null);

        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
        params.rightMargin = (int) (10 * ScreenUtil.getScreenDensity(mContext));
        view.setLayoutParams(params);

        return view;
    }

    public class ViewHolder extends AbsHolder {

        private FlexboxLayout mFlexBoxLayout;

        public ViewHolder(View view) {
            super(view);
            mFlexBoxLayout = findViewById(R.id.flex_box_layout);

        }
    }
}
