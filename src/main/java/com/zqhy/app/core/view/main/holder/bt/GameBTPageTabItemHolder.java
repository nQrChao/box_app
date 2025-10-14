package com.zqhy.app.core.view.main.holder.bt;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.bt.MainBTPageGameVo;
import com.zqhy.app.core.data.model.game.bt.MainBTPageTabVo;
import com.zqhy.app.newproject.R;

import java.util.List;

/**
 * @author leeham2734
 * @date 2020/8/27-15:23
 * @description
 */
public class GameBTPageTabItemHolder extends AbsItemHolder<MainBTPageTabVo, GameBTPageTabItemHolder.ViewHolder> {
    public GameBTPageTabItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_bt_main_tab;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    BaseRecyclerAdapter mAdapter;
    int                 tabPosition = 1;

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MainBTPageTabVo item) {
        mAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(MainBTPageGameVo.class, new GameBTPageItemHolder(mContext))
                .build().setTag(R.id.tag_fragment, _mFragment);
        holder.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        holder.mRecyclerView.setAdapter(mAdapter);

        holder.mLlMainTab1.setOnClickListener(view -> {
            tab1Click(holder, item);
        });
        holder.mLlMainTab2.setOnClickListener(view -> {
            tab2Click(holder, item);
        });

        if (tabPosition == 1) {
            tab1Click(holder, item);
        } else if (tabPosition == 2) {
            tab2Click(holder, item);
        }
    }

    /**
     * Tab1点击事件
     *
     * @param viewHolder
     * @param mainBTPageTabVo
     */
    private void tab1Click(ViewHolder viewHolder, MainBTPageTabVo mainBTPageTabVo) {
        tabPosition = 1;
        tab1Status(viewHolder, true);
        tab2Status(viewHolder, false);
        setSwitchTab(mainBTPageTabVo);
    }

    /**
     * Tab2点击事件
     *
     * @param viewHolder
     * @param mainBTPageTabVo
     */
    private void tab2Click(ViewHolder viewHolder, MainBTPageTabVo mainBTPageTabVo) {
        tabPosition = 2;
        tab1Status(viewHolder, false);
        tab2Status(viewHolder, true);
        setSwitchTab(mainBTPageTabVo);
    }


    private void tab1Status(ViewHolder viewHolder, boolean isChecked) {
        viewHolder.mTvMainTabTitle1.setTextColor(ContextCompat.getColor(mContext, isChecked ? R.color.color_0052ef : R.color.color_232323));
        viewHolder.mTvMainTabSubTitle1.setTextColor(ContextCompat.getColor(mContext, isChecked ? R.color.color_5191ff : R.color.color_858585));
        viewHolder.mTvMainTabImage1.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
    }

    private void tab2Status(ViewHolder viewHolder, boolean isChecked) {
        viewHolder.mTvMainTabTitle2.setTextColor(ContextCompat.getColor(mContext, isChecked ? R.color.color_0052ef : R.color.color_232323));
        viewHolder.mTvMainTabSubTitle2.setTextColor(ContextCompat.getColor(mContext, isChecked ? R.color.color_5191ff : R.color.color_858585));
        viewHolder.mTvMainTabImage2.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
    }

    protected void setSwitchTab(MainBTPageTabVo mainBTPageTabVo) {
        if (mainBTPageTabVo == null) {
            return;
        }
        switch (tabPosition) {
            case 1:
                if (mainBTPageTabVo.getRemen_list() != null) {
                    fillGameList(mainBTPageTabVo.getRemen_list());
                }
                break;
            case 2:
                if (mainBTPageTabVo.getZuixin_list() != null) {
                    fillGameList(mainBTPageTabVo.getZuixin_list());
                }
                break;
            default:
                break;
        }
    }

    private void fillGameList(List<GameInfoVo> gameInfoBeanList) {
        mAdapter.clear();
        MainBTPageGameVo mainBTPageGameVo = new MainBTPageGameVo().setGameInfoVoList(gameInfoBeanList);
        mAdapter.addData(mainBTPageGameVo);
        mAdapter.notifyDataSetChanged();
    }

    public class ViewHolder extends AbsHolder {
        private LinearLayout mLlMainTab1;
        private TextView     mTvMainTabTitle1;
        private TextView     mTvMainTabSubTitle1;
        private ImageView    mTvMainTabImage1;
        private LinearLayout mLlMainTab2;
        private TextView     mTvMainTabTitle2;
        private TextView     mTvMainTabSubTitle2;
        private ImageView    mTvMainTabImage2;
        private RecyclerView mRecyclerView;

        public ViewHolder(View view) {
            super(view);
            mLlMainTab1 = findViewById(R.id.ll_main_tab_1);
            mTvMainTabTitle1 = findViewById(R.id.tv_main_tab_title_1);
            mTvMainTabSubTitle1 = findViewById(R.id.tv_main_tab_sub_title_1);
            mTvMainTabImage1 = findViewById(R.id.tv_main_tab_image_1);
            mLlMainTab2 = findViewById(R.id.ll_main_tab_2);
            mTvMainTabTitle2 = findViewById(R.id.tv_main_tab_title_2);
            mTvMainTabSubTitle2 = findViewById(R.id.tv_main_tab_sub_title_2);
            mTvMainTabImage2 = findViewById(R.id.tv_main_tab_image_2);
            mRecyclerView = findViewById(R.id.recycler_view);


        }
    }
}
