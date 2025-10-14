package com.zqhy.app.core.view.main.new0809.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.game.new0809.item.MainMenuVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2021/8/9 0009-15:23
 * @description
 */
public class MainZkMenuItemHolder extends BaseItemHolder<MainMenuVo, MainZkMenuItemHolder.ViewHolder> {

    private int menuCount = 4;
    public MainZkMenuItemHolder(Context context, int menuCount) {
        super(context);
        this.menuCount = menuCount;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_main_page_menu_zk;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MainMenuVo item) {
        holder.mLlContainer.removeAllViews();
        for (MainMenuVo.DataBean dataBean : item.data) {
            View itemView = createMenuItem(dataBean);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtil.getScreenWidth(mContext) / menuCount, LinearLayout.LayoutParams.MATCH_PARENT);
            itemView.setOnClickListener(v -> {
                appJump(dataBean);
            });
            holder.mLlContainer.addView(itemView, params);
        }
    }


    private View createMenuItem(MainMenuVo.DataBean dataBean) {
        LinearLayout layout = new LinearLayout(mContext);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER_VERTICAL);

        ImageView image = new ImageView(mContext);
        LinearLayout.LayoutParams imageParam = new LinearLayout.LayoutParams(ScreenUtil.dp2px(mContext, 50), ScreenUtil.dp2px(mContext, 50));
        imageParam.gravity = Gravity.CENTER;
        imageParam.topMargin = ScreenUtil.dp2px(mContext, 6);
        layout.addView(image, imageParam);
        GlideUtils.loadNormalImage(mContext, dataBean.icon, image);

        TextView text = new TextView(mContext);
        LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textParam.gravity = Gravity.CENTER;
        textParam.leftMargin = ScreenUtil.dp2px(mContext, 5);
        textParam.rightMargin = ScreenUtil.dp2px(mContext, 5);
        textParam.topMargin = ScreenUtil.dp2px(mContext, 10);
        textParam.bottomMargin = ScreenUtil.dp2px(mContext, 6);
        text.setText(dataBean.title);
        /*try {
            text.setTextColor(Color.parseColor(dataBean.title_color));
        } catch (Exception e) {
           Logs.w("title_color 是空值");
        }*/
        text.setIncludeFontPadding(false);
        text.setTextSize(13);
        text.setTextColor(Color.parseColor("#232323"));
        text.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        layout.addView(text, textParam);
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
