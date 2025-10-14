package com.zqhy.app.core.view.game.holder;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameToolBoxListVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.newproject.R;

/**
 * @author leeham2734
 * @date 2020/5/9-15:09
 * @description
 */
public class GameToolBoxItemHolder extends AbsItemHolder<GameToolBoxListVo, GameToolBoxItemHolder.ViewHolder> {

    public GameToolBoxItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_tool_box;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameToolBoxListVo item) {
        if (holder.getLayoutPosition() == 1){
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.mLlRoot.getLayoutParams();
            layoutParams.setMargins(ScreenUtil.dp2px(mContext, 20), ScreenUtil.dp2px(mContext, 20),ScreenUtil.dp2px(mContext, 20), ScreenUtil.dp2px(mContext, 5));
            holder.mLlRoot.setLayoutParams(layoutParams);
        }else {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.mLlRoot.getLayoutParams();
            layoutParams.setMargins(ScreenUtil.dp2px(mContext, 20), ScreenUtil.dp2px(mContext, 5),ScreenUtil.dp2px(mContext, 20), ScreenUtil.dp2px(mContext, 5));
            holder.mLlRoot.setLayoutParams(layoutParams);
        }
        switch (item.getType()){
            case 1:
                holder.mIvIcon.setImageResource(R.mipmap.ic_game_tool_box_accelerate);
                holder.mTvName.setText("加速器");
                holder.mTvSubhead.setText("游戏全局加速，节省时间");
                holder.mTvContent.setText("进入游戏后，查看到【加速】悬浮球，调节速度并开启加速，即可享受游戏加速哦！");
                holder.mTvContent1.setVisibility(View.GONE);
                holder.mTvAction.setVisibility(View.GONE);
                break;
            case 2:
                holder.mIvIcon.setImageResource(R.mipmap.ic_game_tool_box_outboard);
                holder.mTvName.setText("云挂机");
                holder.mTvSubhead.setText("通过云端的手机长时间挂机");
                holder.mTvContent.setText("①点击“去使用”快速进入云挂机，或自行前往个人中心\n②购买云手机后，选择游戏，直接进行挂机");
                holder.mTvContent1.setVisibility(View.GONE);
                holder.mTvAction.setVisibility(View.VISIBLE);
                holder.mTvAction.setOnClickListener(view -> {
                    if (_mFragment != null) {
                        //if (_mFragment.checkLogin()) _mFragment.startFragment(CloudVeGuideFragment.newInstance());
                        Toaster.show("功能开发中。。。");
                    }
                });
                break;
            case 3:
                holder.mIvIcon.setImageResource(R.mipmap.ic_game_tool_box_bipartition);
                SpannableString spannableString = new SpannableString("双开空间(免安装)");
                spannableString.setSpan(new StyleSpan(Typeface.NORMAL), 4, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new RelativeSizeSpan(0.8F), 4, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.mTvName.setText(spannableString);
                holder.mTvSubhead.setText("在一个游戏中同时登录两个账号");
                SpannableString ss = new SpannableString("已安装平台游戏在玩，想再开1个号同时玩怎么办？\n点击“去使用”进入双开空间，搜索游戏启动，将使用双开空间玩，不影响本地游戏进程，可同时玩。");
                ss.setSpan(new StyleSpan(Typeface.BOLD), 0, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.mTvContent.setText(ss);
                SpannableString ss1 = new SpannableString("双开空间的免安装玩法\n点击“去使用”进入双开空间，搜索游戏启动，会下载游戏，但下载后无需安装可直接开始游戏。");
                ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.mTvContent1.setText(ss1);
                holder.mTvContent1.setVisibility(View.GONE);
                holder.mTvAction.setVisibility(View.VISIBLE);
                /*holder.mTvAction.setOnClickListener(view -> {
                    if (_mFragment != null) {
                        if (_mFragment.checkLogin()) _mFragment.startFragment(BipartitionListFragment.newInstance());
                    }
                });*/
                break;
            case 4:
                holder.mIvIcon.setImageResource(R.mipmap.ic_game_tool_box_connector);
                holder.mTvName.setText("连点器");
                holder.mTvSubhead.setText("代替手动点击，解放双手");
                holder.mTvContent.setText("①使用双开空间进入游戏\n②点击工具悬浮球，找到连点器进行使用");
                holder.mTvContent1.setVisibility(View.GONE);
                holder.mTvAction.setVisibility(View.GONE);
                break;
            case 5:
                holder.mIvIcon.setImageResource(R.mipmap.ic_game_tool_box_record);
                holder.mTvName.setText("游戏录屏");
                holder.mTvSubhead.setText("录制游戏画面");
                holder.mTvContent.setText("①使用双开空间进入游戏\n②点击工具悬浮球，找到录屏进行使用");
                holder.mTvContent1.setVisibility(View.GONE);
                holder.mTvAction.setVisibility(View.GONE);
                break;
            case 6:
                holder.mIvIcon.setImageResource(R.mipmap.ic_game_tool_box_window);
                holder.mTvName.setText("小窗模式");
                holder.mTvSubhead.setText("游戏通过小窗模式后台运行");
                holder.mTvContent.setText("①使用双开空间进入游戏\n②点击工具悬浮球，找到双开分屏进行使用");
                holder.mTvContent1.setVisibility(View.GONE);
                holder.mTvAction.setVisibility(View.GONE);
                break;
        }
        if (item.isUnfold()){
            holder.mTvFold.setText("收起");
            holder.mIvFold.setImageResource(R.mipmap.ic_arrow_up);
        }else {
            holder.mTvFold.setText("使用介绍");
            holder.mIvFold.setImageResource(R.mipmap.ic_arrow_down);

        }
        holder.mLlFold.setOnClickListener(view -> {
            item.setUnfold(!item.isUnfold());
            if (item.isUnfold()){
                holder.mTvFold.setText("收起");
                holder.mIvFold.setImageResource(R.mipmap.ic_arrow_up);
                holder.mTvContent.setVisibility(View.VISIBLE);
                if (item.getType() == 3) {
                    holder.mTvContent1.setVisibility(View.VISIBLE);
                } else {
                    holder.mTvContent1.setVisibility(View.GONE);
                }
            }else {
                holder.mTvFold.setText("使用介绍");
                holder.mIvFold.setImageResource(R.mipmap.ic_arrow_down);
                holder.mTvContent.setVisibility(View.GONE);
                holder.mTvContent1.setVisibility(View.GONE);
            }
        });
    }

    public class ViewHolder extends AbsHolder {

        private LinearLayout mLlRoot;
        private ImageView mIvIcon;
        private TextView mTvName;
        private TextView mTvSubhead;
        private TextView mTvAction;
        private TextView mTvContent;
        private TextView mTvContent1;
        private LinearLayout mLlFold;
        private TextView mTvFold;
        private ImageView mIvFold;

        public ViewHolder(View view) {
            super(view);
            mLlRoot = view.findViewById(R.id.ll_root);
            mIvIcon = view.findViewById(R.id.iv_icon);
            mTvName = view.findViewById(R.id.tv_name);
            mTvSubhead = view.findViewById(R.id.tv_subhead);
            mTvAction = view.findViewById(R.id.tv_action);
            mTvContent = view.findViewById(R.id.tv_content);
            mTvContent1 = view.findViewById(R.id.tv_content_1);
            mLlFold = view.findViewById(R.id.ll_fold);
            mTvFold = view.findViewById(R.id.tv_fold);
            mIvFold = view.findViewById(R.id.iv_fold);
        }
    }
}
