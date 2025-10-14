package com.zqhy.app.core.view.main.new0809.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chaoji.im.glide.GlideApp;
import com.chaoji.im.sdk.ImSDK;
import com.zqhy.app.adapter.abs.AbsAdapter;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.AppJumpAction;
import com.zqhy.app.core.data.model.game.new0809.item.LunboGameDataBeanVo;
import com.zqhy.app.core.data.model.jump.AppJumpInfoBean;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideRoundTransformNew;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.newproject.R;
import com.zqhy.mod.game.GameLauncher;
import com.zqhy.mod.view.guide.GuideManagerJ;
import com.zqhy.mod.view.guide.GuideViewJ;

import java.util.List;

/**
 * @author Administrator
 * @date 2021/8/12 0012-14:08
 * @description
 */
public class NewJXMainPageJingXuanGameBannerHolder extends BaseItemHolder<LunboGameDataBeanVo, NewJXMainPageJingXuanGameBannerHolder.ViewHolder> {

    // 【新增】1. 定义一个接口
    public interface OnButtonClickListener {
        void onLocalGameButtonClick();
    }

    private OnButtonClickListener mListener;

    // 【新增】2. 提供一个公共方法来设置监听器
    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.mListener = listener;
    }

    public NewJXMainPageJingXuanGameBannerHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_main_page_recycler;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull LunboGameDataBeanVo item) {
        if (item.data != null && item.data.size() > 0) {
            LunboGameDataBeanVo.DataBean dataBean = item.data.get(0);
            dataBean.isSelected = true;//设置选中

            setViewDisplay(dataBean, holder);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.mRecyclerView.setLayoutManager(linearLayoutManager);
            MyGameAdapter myGameAdapter = new MyGameAdapter(mContext, item.data);
            myGameAdapter.setOnItemClickListener((v, position, data) -> {
                LunboGameDataBeanVo.DataBean selectData = item.data.get(position);

                setViewDisplay(selectData, holder);

                for (int i = 0; i < item.data.size(); i++) {
                    item.data.get(i).isSelected = false;
                }
                selectData.isSelected = true;
                myGameAdapter.notifyDataSetChanged();
            });
            holder.mRecyclerView.setAdapter(myGameAdapter);

            holder.mIvImage.setOnClickListener(v -> {
                if (_mFragment != null) {
                    for (int i = 0; i < item.data.size(); i++) {
                        if (item.data.get(i).isSelected) {
                            LunboGameDataBeanVo.DataBean selectData = item.data.get(i);
                            if (selectData.getGameid() == 0) {
                                new AppJumpAction(_mFragment.getActivity()).jumpAction(new AppJumpInfoBean(selectData.getPage_type(), selectData.getParam()));
                            } else {
                                _mFragment.goGameDetail(selectData.getGameid(), selectData.getGame_type());
                            }
                        }
                    }
                }
            });

            //if(BuildConfig.APP_TEMPLATE == 9999 &&  AppUtils.getAppPackageName().contains("cqxd.jy.game.nearme.gamecenter")){
            if(BuildConfig.APP_TEMPLATE == 9999 || BuildConfig.APP_TEMPLATE == 9998){
                ImSDK.eventViewModelInstance.getFuliBi().observe(_mFragment, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        holder.localGameBiText.setText("我的福利币："+s);
                    }
                });
                holder.localGameIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GameLauncher.INSTANCE.startLocalGame(mContext,GameLauncher.GAME_URL);
                        //ImSDK.eventViewModelInstance.getStartGame().postValue(true);
                    }
                });
                holder.localGameNameLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GameLauncher.INSTANCE.startLocalGame(mContext,GameLauncher.GAME_URL);
                        //ImSDK.eventViewModelInstance.getStartGame().postValue(true);
                    }
                });

                holder.localGameButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GameLauncher.INSTANCE.startLocalGame(mContext,GameLauncher.GAME_URL);
                        // 这行代码是在通过一个全局的 ViewModel 发送一个“滚动”事件
                        //ImSDK.eventViewModelInstance.getScrollToTabGameList().postValue(true);
                    }
                });
//                String guide = MMKVUtil.INSTANCE.getGuide();
//                if(StringUtils.isEmpty(guide)){
//                    showGuide(holder.localGameLayout);
//                    MMKVUtil.INSTANCE.saveGuide("Guide");
//                }
            }
        }
    }

    private void showGuide(View view){
        if (_mFragment != null && _mFragment.getActivity() != null) {
            GuideViewJ.Builder step1 = new GuideViewJ.Builder(_mFragment.getActivity())
                    .setTargetView(view)
                    .setGuideImage(R.drawable.local_game_guide_img)
                    .setGuideImagePosition(GuideViewJ.Position.BOTTOM_RIGHT)
                    .setHighlightPadding(5)
                    .setGuideImageSizeInDp(200, 100)
                    .setCornerRadius(150);

            new GuideManagerJ.Builder(_mFragment.getActivity())
                    .addStep(step1)
                    .show();
        }
    }

    private void setViewDisplay(LunboGameDataBeanVo.DataBean dataBean, ViewHolder holder) {
        holder.mTvGamename.setText(dataBean.getGamename());
        if (!TextUtils.isEmpty(dataBean.getOtherGameName())) {
            holder.mTvGameSuffix.setVisibility(View.VISIBLE);
            holder.mTvGameSuffix.setText(dataBean.getOtherGameName());
        } else {
            holder.mTvGameSuffix.setVisibility(View.GONE);
        }
        holder.mTvInfoMiddle.setText(dataBean.getGame_summary());
        GlideApp.with(mContext)
                .load(dataBean.getPic())
                .transform(new GlideRoundTransformNew(mContext, 15))
                .into(holder.mIvImage);

        //显示折扣
        int showDiscount = dataBean.showDiscount();
        if (showDiscount == 1 || showDiscount == 2) {
            if (showDiscount == 1) {
                if (dataBean.getDiscount() <= 0 || dataBean.getDiscount() >= 10) {
                    if (dataBean.getSelected_game() == 1) {//是否是精选游戏
                        holder.mLlDiscount1.setVisibility(View.GONE);
                        holder.mLlDiscount2.setVisibility(View.VISIBLE);
                        holder.mLlDiscount3.setVisibility(View.GONE);
                        holder.mLlDiscount4.setVisibility(View.GONE);
                    } else {
                        holder.mLlDiscount1.setVisibility(View.GONE);
                        holder.mLlDiscount2.setVisibility(View.GONE);
                        holder.mLlDiscount3.setVisibility(View.GONE);
                        holder.mLlDiscount4.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (dataBean.getSelected_game() == 1) {//是否是精选游戏
                        holder.mLlDiscount1.setVisibility(View.GONE);
                        holder.mLlDiscount2.setVisibility(View.GONE);
                        holder.mLlDiscount3.setVisibility(View.VISIBLE);
                        holder.mLlDiscount4.setVisibility(View.GONE);
                        holder.mTvDiscount2.setText(String.valueOf(dataBean.getDiscount()));
                    } else {
                        holder.mLlDiscount1.setVisibility(View.VISIBLE);
                        holder.mLlDiscount2.setVisibility(View.GONE);
                        holder.mLlDiscount3.setVisibility(View.GONE);
                        holder.mLlDiscount4.setVisibility(View.GONE);
                        holder.mTvDiscount1.setText(String.valueOf(dataBean.getDiscount()));
                    }
                }
            } else if (showDiscount == 2) {
                if (dataBean.getFlash_discount() <= 0 || dataBean.getFlash_discount() >= 10) {
                    if (dataBean.getSelected_game() == 1) {//是否是精选游戏
                        holder.mLlDiscount1.setVisibility(View.GONE);
                        holder.mLlDiscount2.setVisibility(View.VISIBLE);
                        holder.mLlDiscount3.setVisibility(View.GONE);
                        holder.mLlDiscount4.setVisibility(View.GONE);
                    } else {
                        holder.mLlDiscount1.setVisibility(View.GONE);
                        holder.mLlDiscount2.setVisibility(View.GONE);
                        holder.mLlDiscount3.setVisibility(View.GONE);
                        holder.mLlDiscount4.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (dataBean.getSelected_game() == 1) {//是否是精选游戏
                        holder.mLlDiscount1.setVisibility(View.GONE);
                        holder.mLlDiscount2.setVisibility(View.GONE);
                        holder.mLlDiscount3.setVisibility(View.VISIBLE);
                        holder.mLlDiscount4.setVisibility(View.GONE);
                        holder.mTvDiscount2.setText(String.valueOf(dataBean.getFlash_discount()));
                    } else {
                        holder.mLlDiscount1.setVisibility(View.VISIBLE);
                        holder.mLlDiscount2.setVisibility(View.GONE);
                        holder.mLlDiscount3.setVisibility(View.GONE);
                        holder.mLlDiscount4.setVisibility(View.GONE);
                        holder.mTvDiscount1.setText(String.valueOf(dataBean.getFlash_discount()));
                    }
                }
            }
        } else {
            if (dataBean.getSelected_game() == 1) {//是否是精选游戏
                holder.mLlDiscount1.setVisibility(View.GONE);
                holder.mLlDiscount2.setVisibility(View.VISIBLE);
                holder.mLlDiscount3.setVisibility(View.GONE);
                holder.mLlDiscount4.setVisibility(View.GONE);
            } else {
                holder.mLlDiscount1.setVisibility(View.GONE);
                holder.mLlDiscount2.setVisibility(View.GONE);
                holder.mLlDiscount3.setVisibility(View.GONE);
                holder.mLlDiscount4.setVisibility(View.VISIBLE);
            }
        }
        if (dataBean.getGameid() == 0) {
            holder.mLlDiscount1.setVisibility(View.GONE);
            holder.mLlDiscount2.setVisibility(View.GONE);
            holder.mLlDiscount3.setVisibility(View.GONE);
            holder.mLlDiscount4.setVisibility(View.GONE);
        }
    }

    public class ViewHolder extends AbsHolder {
        private TextView mTvGamename;
        private TextView mTvGameSuffix;
        private TextView mTvInfoMiddle;
        private ImageView mIvImage;
        public LinearLayout mLlDiscount1;
        public LinearLayout mLlDiscount2;
        public LinearLayout mLlDiscount3;
        public LinearLayout mLlDiscount4;
        public TextView mTvDiscount1;
        public TextView mTvDiscount2;
        public TextView localGameBiText;
        public ImageView localGameIcon;
        public Button localGameButton;
        public LinearLayout localGameNameLayout;
        private RecyclerView mRecyclerView;
        private ConstraintLayout mRootLayout;
        private ConstraintLayout localGameLayout;

        public ViewHolder(View view) {
            super(view);
            mRootLayout = view.findViewById(R.id.page_recycler_layout);
            mTvGamename = view.findViewById(R.id.tv_game_name);
            mTvGameSuffix = view.findViewById(R.id.tv_game_suffix);
            mTvInfoMiddle = view.findViewById(R.id.tv_info_middle);
            mIvImage = view.findViewById(R.id.iv_image);
            localGameIcon = view.findViewById(R.id.localGameIcon);
            localGameLayout = view.findViewById(R.id.localGameLayout);
            localGameBiText = view.findViewById(R.id.localGameBiText);
            localGameNameLayout = view.findViewById(R.id.localGameNameLayout);
            localGameButton = view.findViewById(R.id.localGameButton);
            mLlDiscount1 = view.findViewById(R.id.ll_discount_1);
            mLlDiscount2 = view.findViewById(R.id.ll_discount_2);
            mLlDiscount3 = view.findViewById(R.id.ll_discount_3);
            mLlDiscount4 = view.findViewById(R.id.ll_discount4);
            mTvDiscount1 = view.findViewById(R.id.tv_discount_1);
            mTvDiscount2 = view.findViewById(R.id.tv_discount_2);
            mRecyclerView = view.findViewById(R.id.recycler_view);

            //if(BuildConfig.APP_TEMPLATE != 9999){
                localGameLayout.setVisibility(View.GONE);
            //}

//            if(AppUtils.getAppPackageName().contains("cqxd.jy.game.nearme.gamecenter")){
//                localGameLayout.setVisibility(View.VISIBLE);
//            }

            ViewGroup.LayoutParams layoutParams = mIvImage.getLayoutParams();
            layoutParams.width = ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dp2px(mContext, 20);
            layoutParams.height = layoutParams.width * 442 / 750;
            mIvImage.setLayoutParams(layoutParams);

            ViewGroup.LayoutParams layoutParams1 = mRootLayout.getLayoutParams();
            layoutParams1.width = ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dp2px(mContext, 20);
            layoutParams1.height = layoutParams.width - ScreenUtil.dp2px(mContext, 80);
            mRootLayout.setLayoutParams(layoutParams1);
        }
    }

    private class MyGameAdapter extends AbsAdapter<LunboGameDataBeanVo.DataBean> {

        public MyGameAdapter(Context context, List<LunboGameDataBeanVo.DataBean> labels) {
            super(context, labels);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, LunboGameDataBeanVo.DataBean data, int position) {
            GameViewHolder viewHolder = (GameViewHolder) holder;
            if (data.isSelected) {
                viewHolder.mIvBg.setVisibility(View.VISIBLE);
            } else {
                viewHolder.mIvBg.setVisibility(View.GONE);
            }
            GlideUtils.loadGameIcon(mContext, data.getGameicon(), viewHolder.mIvIcon);
        }

        @Override
        public int getLayoutResId() {
            return R.layout.item_main_page_recycler_game_icon;
        }

        @Override
        public AbsViewHolder createViewHolder(View view) {
            return new GameViewHolder(view);
        }

        public class GameViewHolder extends AbsAdapter.AbsViewHolder {
            private ConstraintLayout mClBg;
            private ImageView mIvIcon;
            private ImageView mIvBg;

            public GameViewHolder(View view) {
                super(view);
                mClBg = view.findViewById(R.id.cl_bg);
                mIvIcon = view.findViewById(R.id.iv_icon);
                mIvBg = view.findViewById(R.id.iv_bg);

                ViewGroup.LayoutParams layoutParams = mClBg.getLayoutParams();
                layoutParams.width = ((int) ((ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dp2px(mContext, 90)) / 4.25));
                layoutParams.height = layoutParams.width;
                mClBg.setLayoutParams(layoutParams);
                GlideApp.with(mContext).asBitmap().load(R.drawable.shape_white_radius_10).transform(new GlideRoundTransformNew(mContext, 15)).into(mIvBg);
            }
        }
    }
}
