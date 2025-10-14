package com.zqhy.app.core.view.main.holder.bt;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.mainpage.MainPageMoreLikeGameVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.widget.TitleTextView;

/**
 * @author leeham2734
 * @date 2020/11/24-10:21
 * @description
 */
public class MainPagerMoreLikeItemHolder extends AbsItemHolder<MainPageMoreLikeGameVo, MainPagerMoreLikeItemHolder.ViewHolder> {
    public MainPagerMoreLikeItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_main_page_more_like;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MainPageMoreLikeGameVo item) {
        holder.mTitleTextView.setText(item.getTitle());
        holder.mLlGameInfo.setPadding(ScreenUtil.dp2px(mContext, 15), ScreenUtil.dp2px(mContext, 12), 0, ScreenUtil.dp2px(mContext, 12));

        holder.mLlGameInfo.removeAllViews();
        for (GameInfoVo gameInfoVo : item.getInfoVoList()) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.rightMargin = ScreenUtil.dp2px(mContext, 14);
            params.gravity = Gravity.CENTER_VERTICAL;
            holder.mLlGameInfo.addView(createItemView(mContext, gameInfoVo), params);
        }
    }


    private View createItemView(Context mContext, GameInfoVo gameInfoVo) {
        LinearLayout layout = new LinearLayout(mContext);
        layout.setOrientation(LinearLayout.VERTICAL);

        ImageView image = new ImageView(mContext);
        LinearLayout.LayoutParams imageParam = new LinearLayout.LayoutParams(ScreenUtil.dp2px(mContext, 110), ScreenUtil.dp2px(mContext, 110));
        image.setLayoutParams(imageParam);
        layout.addView(image);
        GlideUtils.loadRoundImage(mContext, gameInfoVo.getGameicon(), image);

        TextView gameName = new TextView(mContext);
        gameName.setTextColor(ContextCompat.getColor(mContext, R.color.color_232323));
        gameName.setTextSize(14);
        gameName.setIncludeFontPadding(false);
        gameName.setSingleLine();
        gameName.setEllipsize(TextUtils.TruncateAt.END);
        gameName.setText(gameInfoVo.getGamename());
        LinearLayout.LayoutParams gameNameParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        gameNameParam.topMargin = ScreenUtil.dp2px(mContext, 6);
        gameNameParam.bottomMargin = ScreenUtil.dp2px(mContext, 6);
        gameName.setLayoutParams(gameNameParam);
        layout.addView(gameName);

        TextView gameInfos = new TextView(mContext);
        gameInfos.setTextColor(ContextCompat.getColor(mContext, R.color.color_747474));
        gameInfos.setIncludeFontPadding(false);
        gameInfos.setTextSize(12);
        gameInfos.setSingleLine();
        gameInfos.setEllipsize(TextUtils.TruncateAt.END);

//        if (gameInfoVo.getGame_type() == 1 && gameInfoVo.getGame_labels() != null && !gameInfoVo.getGame_labels().isEmpty()) {
//            int tagSize = 3;
//            List<GameInfoVo.GameLabelsBean> list = gameInfoVo.getGame_labels().size() > tagSize ? gameInfoVo.getGame_labels().subList(0, tagSize) : gameInfoVo.getGame_labels();
//
//            StringBuilder sb = new StringBuilder();
//            int[][] index = new int[list.size()][2];
//            int startIndex = 0;
//            for (int i = 0; i < list.size(); i++) {
//                GameInfoVo.GameLabelsBean labelsBean = list.get(i);
//                sb.append(labelsBean.getLabel_name()).append("   ");
//                index[i][0] = startIndex;
//                index[i][1] = startIndex + labelsBean.getLabel_name().length();
//                startIndex = sb.length();
//            }
//            SpannableString ss = new SpannableString(sb);
//            for (int i = 0; i < index.length; i++) {
//                GameInfoVo.GameLabelsBean labelsBean = list.get(i);
//                ss.setSpan(new ForegroundColorSpan(Color.parseColor(labelsBean.getText_color())), index[i][0], index[i][1], Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//            }
//            gameInfos.setText(ss);
//        } else {
//        }
        gameInfos.setText(gameInfoVo.getGame_summary());
        layout.addView(gameInfos, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.setOnClickListener(view -> {
            if (_mFragment != null) {
                _mFragment.goGameDetail(gameInfoVo.getGameid(), gameInfoVo.getGame_type());
            }
        });
        return layout;
    }


    public class ViewHolder extends AbsHolder {
        private TitleTextView mTitleTextView;
        private LinearLayout  mLlGameInfo;

        public ViewHolder(View view) {
            super(view);
            mLlGameInfo = findViewById(R.id.ll_game_info);
            mTitleTextView = findViewById(R.id.title_text_view);

        }
    }
}
