package com.zqhy.app.core.view.main.new0809.holder;

import android.content.Context;
import android.graphics.Path;
import android.graphics.Typeface;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
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

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author Administrator
 * @date 2021/8/9 0009-15:23
 * @description
 */
public class NewJXMainMenuItemHolder extends BaseItemHolder<MainMenuVo, NewJXMainMenuItemHolder.ViewHolder> {

    private int menuCount = 5;
    private Disposable subscribe;
    public NewJXMainMenuItemHolder(Context context, int menuCount) {
        super(context);
        this.menuCount = menuCount;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_main_page_menu_new_jx;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MainMenuVo item) {
        holder.mLlContainer.removeAllViews();
        int screenWidth = ScreenUtil.getScreenWidth(mContext);
        if (item.data.size() < menuCount){
            menuCount = item.data.size();
        }
        for (MainMenuVo.DataBean dataBean : item.data) {
            View itemView = createMenuItem(dataBean);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth / menuCount, LinearLayout.LayoutParams.MATCH_PARENT);
            itemView.setOnClickListener(v -> {
                appJump(dataBean);
            });
            holder.mLlContainer.addView(itemView, params);
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)holder.mViewProgress.getLayoutParams();
        if (item.data.size() <= menuCount){
            layoutParams.width = ScreenUtil.dp2px(mContext, 39);
            holder.mLlProgress.setVisibility(View.GONE);
        }else{
            layoutParams.width = ScreenUtil.dp2px(mContext, 39)/item.data.size()*menuCount;
            holder.mLlProgress.setVisibility(View.VISIBLE);
        }
        layoutParams.leftMargin = 0;
        holder.mViewProgress.setLayoutParams(layoutParams);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.mScrollView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                layoutParams.leftMargin = (int)(((float)scrollX / (screenWidth / menuCount * item.data.size())) * ScreenUtil.dp2px(mContext, 39));
                holder.mViewProgress.setLayoutParams(layoutParams);
            });
        }
        if (subscribe != null){
            subscribe.dispose();
            subscribe = null;
        }
        if (item.data.size() > menuCount){
            int scrollDistane;
            if ((item.data.size() - menuCount) < 3){
                scrollDistane = ScreenUtil.getScreenWidth(mContext) / menuCount * (item.data.size() - menuCount);
            }else {
                scrollDistane = (int)(ScreenUtil.getScreenWidth(mContext) / menuCount * 2.5);
            }
            int scrollItem = scrollDistane / 60;
            subscribe = Observable.interval(1000, 5, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        boolean toRight = true;
                        int scrollX = 0;
                        int count = 0;
                        @Override
                        public void accept(Long aLong) throws Exception {
                            Path path = new Path();
                            path.quadTo(0, 0, scrollDistane, 0);
                            if (toRight){
                                if (scrollX >= scrollDistane){
                                    toRight = false;
                                }else {
                                    if (count < scrollItem){
                                        count++;
                                    }
                                    scrollX += count;
                                    if (scrollX <= scrollDistane){
                                        holder.mScrollView.scrollTo(scrollX, 0);
                                    }else {
                                        holder.mScrollView.scrollTo(scrollDistane, 0);
                                    }
                                }
                            }else{
                                if (scrollX <= 0){
                                    subscribe.dispose();
                                    subscribe = null;
                                }else{
                                    scrollX -= count;
                                    if (count == 1){
                                        count--;
                                    }
                                    if (scrollX >= 0){
                                        holder.mScrollView.scrollTo(scrollX, 0);
                                    }else{
                                        holder.mScrollView.scrollTo(0, 0);
                                    }
                                }
                            }

                        }
                    });
        }
    }


    private View createMenuItem(MainMenuVo.DataBean dataBean) {
        LinearLayout layout = new LinearLayout(mContext);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER_VERTICAL);

        ImageView image = new ImageView(mContext);
        LinearLayout.LayoutParams imageParam = new LinearLayout.LayoutParams(ScreenUtil.dp2px(mContext, 60), ScreenUtil.dp2px(mContext, 60));
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
        text.setIncludeFontPadding(false);
        text.setTextSize(12);
        text.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        layout.addView(text, textParam);

        return layout;
    }


    public class ViewHolder extends AbsHolder {
        private HorizontalScrollView mScrollView;
        private LinearLayout mLlContainer;
        private LinearLayout mLlProgress;
        private View mViewProgress;

        public ViewHolder(View view) {
            super(view);
            mScrollView = findViewById(R.id.scrollView);
            mLlContainer = findViewById(R.id.ll_container);
            mLlProgress = findViewById(R.id.ll_progress);
            mViewProgress = findViewById(R.id.view_progress);
        }
    }
}
