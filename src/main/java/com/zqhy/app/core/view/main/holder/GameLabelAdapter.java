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

public class GameLabelAdapter extends RecyclerView.Adapter<GameLabelAdapter.LabelViewHolder> {

    private Context mContext;
    private List<GameInfoVo.GameLabelsBean> mLabels;

    public GameLabelAdapter(Context context) {
        mContext = context;
    }

    public void setLabels(List<GameInfoVo.GameLabelsBean> labels) {
        mLabels = labels;
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
        GameInfoVo.GameLabelsBean label = mLabels.get(position);
        holder.tvLabel.setText(label.getLabel_name());

        // 设置标签样式
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(ScreenUtil.dp2px(mContext, 4));
        gd.setStroke(ScreenUtil.dp2px(mContext, 0.5F), Color.parseColor("#DCDCDC"));
        holder.tvLabel.setBackground(gd);
    }

    @Override
    public int getItemCount() {
        return mLabels != null ? mLabels.size() : 0;
    }

    static class LabelViewHolder extends RecyclerView.ViewHolder {
        TextView tvLabel;

        LabelViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLabel = itemView.findViewById(R.id.tv_label);
        }
    }
}