package com.zqhy.app.core.view.main.new0809.holder;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.game.new0809.item.IconMenuVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2021/8/9 0009-15:23
 * @description
 */
public class IconMenuItemHolder extends BaseItemHolder<IconMenuVo, IconMenuItemHolder.ViewHolder> {

    private int menuCount = 3;
    public IconMenuItemHolder(Context context, int menuCount) {
        super(context);
        this.menuCount = menuCount;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_main_page_menu;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull IconMenuVo item) {
        holder.mLlContainer.removeAllViews();
        for (IconMenuVo.DataBean dataBean : item.data) {
            View itemView = createMenuItem(dataBean);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dp2px(mContext, 20F)) / menuCount, LinearLayout.LayoutParams.MATCH_PARENT);
            itemView.setOnClickListener(v -> {
                appJump(dataBean);
            });
            holder.mLlContainer.addView(itemView, params);
        }
    }


    private View createMenuItem(IconMenuVo.DataBean dataBean) {
        LinearLayout layout = new LinearLayout(mContext);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER_VERTICAL);

        ImageView image = new ImageView(mContext);
        LinearLayout.LayoutParams imageParam = new LinearLayout.LayoutParams(ScreenUtil.dp2px(mContext, 100), ScreenUtil.dp2px(mContext, 55));
        imageParam.gravity = Gravity.CENTER;
        imageParam.topMargin = ScreenUtil.dp2px(mContext, 6);
        layout.addView(image, imageParam);
        GlideUtils.loadNormalImage(mContext, dataBean.icon, image);

        return layout;
    }


    public class ViewHolder extends AbsHolder {
        private LinearLayout mLlContainer;

        public ViewHolder(View view) {
            super(view);
            mLlContainer = findViewById(R.id.ll_container);
        }
    }
}
