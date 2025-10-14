package com.zqhy.app.core.view.game.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.box.common.glide.GlideApp;
import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.AppJumpAction;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.detail.GameRecommendListVo;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;
import com.zqhy.app.core.tool.AppUtil;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.game.GameDetailInfoFragment;
import com.zqhy.app.core.view.tryplay.TryGameTaskFragment;
import com.zqhy.app.core.view.user.provincecard.NewProvinceCardFragment;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author Administrator
 * @date 2018/11/20
 */

public class NewCustomRecommendItemHolder extends AbsItemHolder<GameRecommendListVo, NewCustomRecommendItemHolder.ViewHolder> {

    private DividerItemDecoration decoration;

    public NewCustomRecommendItemHolder(Context context) {
        super(context);
        decoration = new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL);
        decoration.setDrawable(mContext.getResources().getDrawable(R.drawable.main_pager_item_decoration_vertical));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameRecommendListVo item) {
        holder.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        holder.mRecyclerView.addItemDecoration(decoration);
        if (item.getRecommend_info() != null){
            holder.mRecyclerView.setAdapter(new RecyclerView.Adapter() {
                @NonNull
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                    return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_game_custom_recommend, viewGroup, false));
                }

                @Override
                public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                    GameInfoVo.RecommendInfo recommendInfo = item.getRecommend_info().get(i);
                    if ("sxw".equals(recommendInfo.getType())){
                        GlideApp.with(mContext).load(R.mipmap.ic_game_detail_recommend_sxw).into(((MyViewHolder) viewHolder).mIvIcon);
                        ((MyViewHolder) viewHolder).mTvTitle.setText("省心玩");
                        ((MyViewHolder) viewHolder).mTvContent.setText("不好玩，2小时充值退一半");
                        ((MyViewHolder) viewHolder).mIvArrow.setVisibility(View.VISIBLE);
                        ((MyViewHolder) viewHolder).itemView.setOnClickListener(v -> {
                            if (_mFragment != null && _mFragment instanceof GameDetailInfoFragment){
                                ((GameDetailInfoFragment) _mFragment).clickSXW();
                            }
                        });
                    } else if ("ptb".equals(recommendInfo.getType())){
                        GlideApp.with(mContext).load(R.mipmap.ic_game_detail_recommend_ptb).into(((MyViewHolder) viewHolder).mIvIcon);
                        ((MyViewHolder) viewHolder).mTvTitle.setText("可用福利币");
                        ((MyViewHolder) viewHolder).mTvContent.setText("1福利币=1元，前往获取福利币");
                        ((MyViewHolder) viewHolder).mIvArrow.setVisibility(View.VISIBLE);
                        ((MyViewHolder) viewHolder).itemView.setOnClickListener(v -> {
                            if (_mFragment != null){
                            /*AppBaseJumpInfoBean appBaseJumpInfoBean = new AppBaseJumpInfoBean("money", null);
                            AppJumpAction appJumpAction = new AppJumpAction(_mFragment.getActivity());
                            appJumpAction.jumpAction(appBaseJumpInfoBean);*/
                                if (_mFragment != null){
                                    _mFragment.startFragment(new NewProvinceCardFragment());
                                }
                            }
                        });
                    } else if ("accelerate".equals(recommendInfo.getType())){//加速器
                        GlideApp.with(mContext).load(R.mipmap.ic_game_detail_recommend_accelerator).into(((MyViewHolder) viewHolder).mIvIcon);
                        ((MyViewHolder) viewHolder).mTvTitle.setText("支持加速器");
                        ((MyViewHolder) viewHolder).mTvContent.setText("游戏全局加速，节省时间");
                        ((MyViewHolder) viewHolder).mIvArrow.setVisibility(View.GONE);
                        ((MyViewHolder) viewHolder).itemView.setOnClickListener(v -> {

                        });
                    } else if ("bipartition".equals(recommendInfo.getType())){
                        GlideApp.with(mContext).load(R.mipmap.ic_game_detail_recommend_bipartition).into(((MyViewHolder) viewHolder).mIvIcon);
                        ((MyViewHolder) viewHolder).mTvTitle.setText("双开空间");
                        ((MyViewHolder) viewHolder).mTvContent.setText("创建游戏分身同时玩两个角色");
                        ((MyViewHolder) viewHolder).mIvArrow.setVisibility(View.VISIBLE);
                        ((MyViewHolder) viewHolder).itemView.setOnClickListener(v -> {
                            if (_mFragment != null){
                                if (AppUtil.isNotArmArchitecture()){
                                    //_mFragment.startFragment(BipartitionListFragment.newInstance());
                                }else {
                                    Toaster.show("当前设备不支持此功能！");
                                }
                            }
                        });
                    } else if ("special".equals(recommendInfo.getType())){//特惠卡
                        GlideApp.with(mContext).load(R.mipmap.ic_game_detail_recommend_special).into(((MyViewHolder) viewHolder).mIvIcon);
                        ((MyViewHolder) viewHolder).mTvTitle.setText("可用省钱卡");
                        ((MyViewHolder) viewHolder).mTvContent.setText("每日领取5福利币可抵扣充值");
                        ((MyViewHolder) viewHolder).mIvArrow.setVisibility(View.VISIBLE);
                        ((MyViewHolder) viewHolder).itemView.setOnClickListener(v -> {
                            if (_mFragment != null){
                                _mFragment.startFragment(NewProvinceCardFragment.newInstance(2));
                            }
                        });
                    } else if ("trial".equals(recommendInfo.getType())){//试玩
                        GlideApp.with(mContext).load(R.mipmap.ic_game_detail_recommend_trial).into(((MyViewHolder) viewHolder).mIvIcon);
                        ((MyViewHolder) viewHolder).mTvTitle.setText("试玩赚金");
                        if (!TextUtils.isEmpty(recommendInfo.getTrial_info().getEndtime())) ((MyViewHolder) viewHolder).mTvContent.setText("截至" + CommonUtils.formatTimeStamp(Long.parseLong(recommendInfo.getTrial_info().getEndtime()) * 1000, "MM月dd日") + "，最高领取1000000积分");
                        ((MyViewHolder) viewHolder).mIvArrow.setVisibility(View.VISIBLE);
                        ((MyViewHolder) viewHolder).itemView.setOnClickListener(v -> {
                            if (_mFragment != null){
                                _mFragment.startFragment(TryGameTaskFragment.newInstance(recommendInfo.getTrial_info().getTid()));
                            }
                        });
                    } else if ("subsidy".equals(recommendInfo.getType())){//百亿补贴
                        GlideApp.with(mContext).load(R.mipmap.ic_game_detail_recommend_subsidy).into(((MyViewHolder) viewHolder).mIvIcon);
                        String ratio = String.valueOf(recommendInfo.getYouhui().getRatio());
                        if (ratio.indexOf(".") != 1){
                            ratio = ratio.substring(0, ratio.indexOf("."));
                        }
                        ((MyViewHolder) viewHolder).mTvTitle.setText("补贴福利：立减" + ratio + "%");
                        ((MyViewHolder) viewHolder).mTvContent.setText("截至" + CommonUtils.formatTimeStamp(recommendInfo.getYouhui().getEnd_time() * 1000, "MM月dd日") + "，每笔充值享优惠");
                        ((MyViewHolder) viewHolder).mIvArrow.setVisibility(View.VISIBLE);
                        ((MyViewHolder) viewHolder).itemView.setOnClickListener(v -> {
                            if (_mFragment != null){
                                BrowserActivity.newInstance(_mFragment.getActivity(), "https://mobile.tsyule.cn/index.php/Index/view/?id=102960");
                            }
                        });
                    } else {
                        GlideUtils.loadNormalImage(mContext, recommendInfo.getPic(), ((MyViewHolder) viewHolder).mIvIcon, R.mipmap.ic_placeholder);
                        ((MyViewHolder) viewHolder).mTvTitle.setText(recommendInfo.getTitle());
                        ((MyViewHolder) viewHolder).mTvContent.setText(recommendInfo.getDescription());
                        ((MyViewHolder) viewHolder).mIvArrow.setVisibility(View.VISIBLE);
                        ((MyViewHolder) viewHolder).itemView.setOnClickListener(v -> {
                            if (_mFragment != null){
                                AppBaseJumpInfoBean appBaseJumpInfoBean = new AppBaseJumpInfoBean(recommendInfo.getPage_type(), recommendInfo.getParam());
                                AppJumpAction appJumpAction = new AppJumpAction(_mFragment.getActivity());
                                appJumpAction.jumpAction(appBaseJumpInfoBean);
                            }
                        });
                    }
                }

                @Override
                public int getItemCount() {
                    return item.getRecommend_info().size();
                }

                class MyViewHolder extends RecyclerView.ViewHolder{
                    private ImageView mIvIcon;
                    private TextView mTvTitle;
                    private TextView mTvContent;
                    private ImageView mIvArrow;

                    public MyViewHolder(@NonNull View itemView) {
                        super(itemView);
                        mIvIcon = itemView.findViewById(R.id.iv_icon);
                        mTvTitle = itemView.findViewById(R.id.tv_title);
                        mTvContent = itemView.findViewById(R.id.tv_content);
                        mIvArrow = itemView.findViewById(R.id.iv_arrow);
                    }
                }
            });
        }

        if (item.getGdm() == 1 && !BuildConfig.NEED_BIPARTITION) {//头条包不显示GM标签
            holder.mLlManager.setVisibility(View.VISIBLE);
            holder.mLlManager.setOnClickListener(v -> {
                if (_mFragment != null) {
                    if (!TextUtils.isEmpty(item.getGdm_url())) BrowserActivity.newInstance(_mFragment.getActivity(), item.getGdm_url(), true);
                }
            });
        }else {
            holder.mLlManager.setVisibility(View.GONE);
        }
    }
    @Override
    public int getLayoutResId() {
        return R.layout.item_game_detail_custom_recommend_new;
    }
    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {

        private RecyclerView mRecyclerView;
        private LinearLayout mLlManager;

        public ViewHolder(View view) {
            super(view);
            mRecyclerView = view.findViewById(R.id.recycler_view);
            mLlManager = view.findViewById(R.id.ll_manager);

        }
    }

}
