package com.zqhy.app.core.view.game.forum.holder;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.view.game.forum.ForumDraftsBean;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author leeham2734
 * @date 2021/6/17-14:33
 * @description
 */
public class ForumDraftsHolder extends AbsItemHolder<ForumDraftsBean, ForumDraftsHolder.ViewHolder> {

   public interface ClickDelete{
       void onDelete(ForumDraftsBean bean);
       void onEdit(ForumDraftsBean bean);
   }
    ClickDelete mClickDelete;
    public ForumDraftsHolder(Context context,ClickDelete clickDelete) {
        super(context);
        mClickDelete = clickDelete;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_forum_drafts;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ForumDraftsBean item) {
        if (item.getTitle().isEmpty()){
            holder.title.setText("暂无标题");
            holder.title.setTextColor(Color.parseColor("#999999"));
        }else {
            holder.title.setText(item.getTitle());
            holder.title.setTextColor(Color.parseColor("#232323"));
        }
        holder.delete.setOnClickListener(v -> {
            if (mClickDelete!=null){
                mClickDelete.onDelete(item);
            }
        });
        holder.edit.setOnClickListener(v -> {
            if (mClickDelete!=null){
                mClickDelete.onEdit(item);
            }
        });
        holder.tips.setText(item.getTips());
        holder.game_name.setText(item.getGameName());
        holder.time.setText(CommonUtils.formatTimeStamp(item.getTime(),"yyyy.MM.dd"));
    }

    public class ViewHolder extends AbsHolder {
        private TextView       title;
        private TextView       tips;
        private TextView       time;
        private TextView       edit;
        private TextView       game_name;
        private ImageView delete;

        public ViewHolder(View view) {
            super(view);
            game_name = findViewById(R.id.game_name);
            delete = findViewById(R.id.delete);
            title = findViewById(R.id.title);
            tips = findViewById(R.id.tips);
            time = findViewById(R.id.time);
            edit = findViewById(R.id.edit);
        }
    }
}
