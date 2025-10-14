package com.zqhy.app.core.view.main.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.newproject.R;

import java.util.List;

public class GameNormalLabelAdapter extends RecyclerView.Adapter<GameNormalLabelAdapter.LabelViewHolder> {

    private Context mContext;
    private List<Object> mItems; // 支持两种类型：String 或 GameLabelsBean

    public GameNormalLabelAdapter(Context context) {
        mContext = context;
    }

    public void setItems(List<Object> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LabelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_game_label, parent, false);
        return new LabelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LabelViewHolder holder, int position) {
        Object item = mItems.get(position);
        TextView textView = holder.tvLabel;

        if (item instanceof String) {
            String label = (String) item;
            textView.setText(label);
            // 特殊标签样式（专服/可加速/省心玩）
            textView.setTextColor(Color.parseColor("#4E76FF"));
            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(ScreenUtil.dp2px(mContext, 2));
            gd.setColors(new int[]{Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF")});
            gd.setStroke(ScreenUtil.dp2px(mContext, 0.5F), Color.parseColor("#4E76FF"));
            textView.setBackground(gd);
        } else if (item instanceof GameInfoVo.GameLabelsBean) {
            GameInfoVo.GameLabelsBean bean = (GameInfoVo.GameLabelsBean) item;
            textView.setText(bean.getLabel_name());

            // 普通标签样式
            textView.setTextColor(Color.parseColor("#666666"));
            GradientDrawable gd = new GradientDrawable();
            gd.setColors(new int[]{Color.parseColor("#F5F5F5"), Color.parseColor("#F5F5F5")});
            gd.setCornerRadius(ScreenUtil.dp2px(mContext, 2));
            textView.setBackground(gd);
        }
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    static class LabelViewHolder extends RecyclerView.ViewHolder {
        TextView tvLabel;

        LabelViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLabel = itemView.findViewById(R.id.tv_label);
        }
    }
}