package com.zqhy.app.core.view.main.new0809.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.new0809.MainJingXuanDataVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.main.holder.GameNormalItemHolder;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2021/8/14 0014-12:16
 * @description
 */
public class MainJxTabGameListItemHolder extends BaseItemHolder<MainJingXuanDataVo.JXTabGameListDataBeanVo, MainJxTabGameListItemHolder.ViewHolder> {

    public MainJxTabGameListItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_main_game_list_tab_jx;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MainJingXuanDataVo.JXTabGameListDataBeanVo item) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return ScreenUtil.getScreenHeight(mContext);
            }
        };
        holder.mRecyclerView.setLayoutManager(linearLayoutManager);
        BaseRecyclerAdapter mPagerAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(GameInfoVo.class, new GameNormalItemHolder(mContext, true))
                .build().setTag(R.id.tag_fragment, _mFragment).setTag(R.id.tag_sub_fragment, _mFragment);
        holder.mRecyclerView.setAdapter(mPagerAdapter);


        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mContext);
        linearLayoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        holder.mRecyclerViewTab.setLayoutManager(linearLayoutManager1);
        holder.mRecyclerViewTab.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_main_game_list_tab_item, viewGroup, false));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                MyViewHolder viewHolder1 = (MyViewHolder)viewHolder;

                viewHolder1.mTvTab.setText(item.data.get(i).label);

                if (item.data.get(i).labelSelect){
                    viewHolder1.mTvTab.setTextColor(Color.parseColor("#232323"));
                    viewHolder1.mTvTab.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    viewHolder1.mIvTab.setVisibility(View.VISIBLE);
                }else {
                    viewHolder1.mTvTab.setTextColor(Color.parseColor("#666666"));
                    viewHolder1.mTvTab.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    viewHolder1.mIvTab.setVisibility(View.GONE);
                }

                viewHolder1.itemView.setOnClickListener(v -> {
                    for (int j = 0; j < item.data.size(); j++) {
                        if (i == j){
                            item.data.get(j).labelSelect = true;
                        }else {
                            item.data.get(j).labelSelect = false;
                        }
                    }
                    mPagerAdapter.setDatas(item.data.get(i).items);
                    mPagerAdapter.notifyDataSetChanged();
                    notifyDataSetChanged();
                });
            }

            @Override
            public int getItemCount() {
                return item.data.size();
            }

            class MyViewHolder extends RecyclerView.ViewHolder{
                private TextView mTvTab;
                private ImageView mIvTab;
                public MyViewHolder(@NonNull View itemView) {
                    super(itemView);
                    mTvTab = itemView.findViewById(R.id.tv_tab);
                    mIvTab = itemView.findViewById(R.id.iv_tab);
                }
            }
        });
        if (item.data != null && item.data.size() > 0){
            boolean isSet = false;
            for (int i = 0; i < item.data.size(); i++) {
                if (item.data.get(i).labelSelect){
                    isSet = true;
                }
            }

            if (!isSet){
                item.data.get(0).labelSelect = true;
            }
            mPagerAdapter.addAllData(item.data.get(0).items);
        }
    }

    public class ViewHolder extends AbsHolder {

        private RecyclerView mRecyclerViewTab;
        private RecyclerView mRecyclerView;

        public ViewHolder(View view) {
            super(view);
            mRecyclerViewTab = findViewById(R.id.recycler_view_tab);
            mRecyclerView = findViewById(R.id.recycler_view);
        }
    }
}
