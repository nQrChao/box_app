package com.zqhy.app.core.view.game.holder;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chaoji.im.glide.GlideApp;
import com.chaoji.other.blankj.utilcode.util.Logs;
import com.danikula.videocache.HttpProxyCacheServer;
import com.donkingliang.imageselector.PreviewActivity;
import com.donkingliang.imageselector.entry.Image;
import com.zqhy.app.App;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.detail.GameDesVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.DisInterceptTouchListener;
import com.zqhy.app.widget.video.CustomMediaPlayer.JZExoPlayer;
import com.zqhy.app.widget.video.JzvdStdVolumeAfterFullscreen;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 * @author Administrator
 * @date 2018/11/20
 */

public class GameDesItemHolder extends AbsItemHolder<GameDesVo, GameDesItemHolder.ViewHolder> {

    private       float density;
    private       int   mFixHeight;
    private final float videoScale = 16f / 9f;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration decoration;

    public GameDesItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
        mFixHeight = (int) ((ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dp2px(mContext, 40)) / videoScale);

        linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        decoration = new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL);
        decoration.setDrawable(mContext.getResources().getDrawable(R.drawable.main_pager_item_decoration_vertical));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameDesVo item) {

        //设置游戏简介图片
        //setGameInfoPics(holder, item);

        //设置游戏简介文字
        if (!TextUtils.isEmpty(item.getGame_description())) {
            holder.mTvContent.setText(item.getGame_description());
            holder.mTvContent.post(() -> {
                if (holder.mTvContent.getLineCount() > 3){
                    holder.mTvContent.setMaxLines(3);
                    holder.mTvFold.setVisibility(View.VISIBLE);
                }else {
                    holder.mTvContent.setMaxLines(3);
                    holder.mTvFold.setVisibility(View.GONE);
                }
                holder.mTvFold.setOnClickListener(view -> {
                    if (holder.mTvContent.getLineCount() == 3){
                        holder.mTvContent.setMaxLines(Integer.MAX_VALUE);
                        holder.mTvFold.setText("收起");
                        holder.mTvFold.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.ic_game_detail_rebate_arrow_up), null);
                    }else{
                        holder.mTvContent.setMaxLines(3);
                        holder.mTvFold.setText("展开");
                        holder.mTvFold.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.ic_game_detail_rebate_arrow_down), null);
                    }
                });
            });
        } else {
            holder.mTvContent.setVisibility(View.GONE);
            holder.mTvFold.setVisibility(View.GONE);
        }

        if (item.getVipNews() != null){
            holder.mLlVip.setVisibility(View.VISIBLE);
            holder.mTvTag.setText(item.getVipNews().getTitle2());
            holder.mTvTitle.setText(item.getVipNews().getTitle());
            holder.mLlVip.setOnClickListener(v -> {
                if (_mFragment != null){
                    BrowserActivity.newInstance(_mFragment.getActivity(), item.getVipNews().getUrl());
                }
            });
        }else {
            holder.mLlVip.setVisibility(View.GONE);
            holder.mLlVip.setOnClickListener(null);
        }

        if (item.getGame_model() == 1){//横屏
            holder.mTvGameModel.setVisibility(View.VISIBLE);
            holder.mTvGameModel.setText("横屏游戏");
        }else if (item.getGame_model() == 2){//竖屏
            holder.mTvGameModel.setVisibility(View.VISIBLE);
            holder.mTvGameModel.setText("竖屏游戏");
        }else {
            holder.mTvGameModel.setVisibility(View.GONE);
        }

        if ("1".equals(item.getData_exchange())){
            holder.mTvType.setVisibility(View.VISIBLE);
            holder.mIvTips.setVisibility(View.VISIBLE);
            holder.mTvType.setText("双端互通");
        }else if ("2".equals(item.getData_exchange())){
            holder.mTvType.setVisibility(View.VISIBLE);
            holder.mIvTips.setVisibility(View.VISIBLE);
            holder.mTvType.setText("双端不互通");
        }else {
            holder.mTvType.setVisibility(View.GONE);
            holder.mIvTips.setVisibility(View.GONE);
        }

        holder.mIvTips.setOnClickListener(v -> {
            showWelfareDialog(item.getData_exchange());
        });
        holder.mTvType.setOnClickListener(v -> {
            showWelfareDialog(item.getData_exchange());
        });
    }


    private void setGameInfoPics(ViewHolder holder, GameDesVo item) {
        List<String> viewList = new ArrayList<>();
        if (!TextUtils.isEmpty(item.getVideo_pic()) && !TextUtils.isEmpty(item.getVideo_url())) {
            viewList.add("video");
        }

        List<String> picUrls = item.getScreenshot();
        if (picUrls != null && picUrls.size() > 0) {
            for (int i = 0; i < picUrls.size(); i++) {
                viewList.add("pic");
            }
        }


        //设置recycleView布局管理器
        holder.mRecyclerView.setLayoutManager(linearLayoutManager);
        holder.mRecyclerView.addItemDecoration(decoration);
        holder.mRecyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                if (i == 0){
                    return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_game_exhibition, viewGroup, false));
                }else {
                    return new MyViewHolder1(LayoutInflater.from(mContext).inflate(R.layout.item_game_exhibition1, viewGroup, false));
                }
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                if ("video".equals(viewList.get(i))){
                    View videoView = createMiniVideoView(item.getVideo_pic(), item.getVideo_url());
                    LinearLayout.LayoutParams childParams = (LinearLayout.LayoutParams) videoView.getLayoutParams();
                    childParams.setMargins(0, 0, (int) (8 * density), 0);
                    ((MyViewHolder) viewHolder).mLlContent.addView(videoView, childParams);
                }else {
                    int index;
                    if(viewList == picUrls){
                        index = i;
                    }else {
                        index = i -1;
                    }
                    View childView = createGameInfoPicView(picUrls, index);
                    LinearLayout.LayoutParams childParams = (LinearLayout.LayoutParams) childView.getLayoutParams();
                    childParams.width = picImageWidth;
                    //childParams.setMargins(0, 0, (int) (8 * density), 0);
                    ((MyViewHolder1) viewHolder).mLlContent.addView(childView, childParams);
                }

            }

            @Override
            public int getItemViewType(int position) {
                if ("video".equals(viewList.get(position))){
                    return 0;
                }else {
                    return 1;
                }
            }

            @Override
            public int getItemCount() {
                return viewList.size();
            }

            class MyViewHolder extends RecyclerView.ViewHolder{
                private LinearLayout mLlContent;

                public MyViewHolder(@NonNull View itemView) {
                    super(itemView);
                    mLlContent = itemView.findViewById(R.id.ll_content);
                }
            }

            class MyViewHolder1 extends RecyclerView.ViewHolder{
                private LinearLayout mLlContent;

                public MyViewHolder1(@NonNull View itemView) {
                    super(itemView);
                    mLlContent = itemView.findViewById(R.id.ll_content);
                }
            }
        });
        LinearSnapHelper pagerSnapHelper = new LinearSnapHelper();
        holder.mRecyclerView.setOnFlingListener(null);//避免出现An instance of OnFlingListener already set异常
        // 滑动后Snap
        pagerSnapHelper.attachToRecyclerView(holder.mRecyclerView);
        // 滑动时使父布局不响应事件
        holder.mRecyclerView.setOnTouchListener(new DisInterceptTouchListener());
    }

    /**
     * 创建图集View
     *
     * @param picUrls
     * @param position
     * @return
     */
    private int picImageWidth;
    private View createGameInfoPicView(List<String> picUrls, int position) {
        String url = picUrls.get(position);
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        int mImageHeight = mFixHeight;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, mImageHeight);
        imageView.setLayoutParams(params);

        imageView.setImageResource(R.mipmap.img_placeholder_h);
        GlideApp.with(mContext)
                .asBitmap()
                .load(url)
                .centerCrop()
                .placeholder(R.mipmap.img_placeholder_h)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                        int bitWidth = bitmap.getWidth();
                        int bitHeight = bitmap.getHeight();

                        imageView.setImageBitmap(bitmap);

                        int mImageWidth = (bitWidth * mImageHeight) / bitHeight;

                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                        params.gravity = Gravity.CENTER_VERTICAL;
                        params.width = mImageWidth;
                        params.height = mImageHeight;
                        picImageWidth = mImageWidth;
                        imageView.setLayoutParams(params);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable drawable) {

                    }
                });
        imageView.setOnClickListener(v -> {
            if (picUrls == null) {
                return;
            }
            //预览图片
            ArrayList<Image> images = new ArrayList();
            for (String path : picUrls) {
                Image image = new Image();
                image.setType(1);
                image.setHigh_path(path);
                image.setPath(path);
                images.add(image);
            }
            if (_mFragment != null) {
                PreviewActivity.openActivity(_mFragment.getActivity(), images, true, position, true);
            }
        });
        return imageView;
    }

    /**
     * 小屏幕VideoView
     *
     * @param video_pic
     * @param video_url
     * @return
     */
    private View createMiniVideoView(String video_pic, String video_url) {
        JzvdStdVolumeAfterFullscreen videoPlayer = new JzvdStdVolumeAfterFullscreen(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        videoPlayer.setLayoutParams(params);

        if (TextUtils.isEmpty(video_url)) {
            return videoPlayer;
        }
        GlideApp.with(mContext)
                .asBitmap()
                .load(video_pic)
                .placeholder(R.mipmap.img_placeholder_v_2)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                        if (bitmap == null) {
                            return;
                        }
                        int bitWidth = bitmap.getWidth();
                        int bitHeight = bitmap.getHeight();
                        videoPlayer.thumbImageView.setImageBitmap(bitmap);

                        int mImageHeight = mFixHeight;
                        int mImageWidth = mFixHeight * bitWidth / bitHeight;

                        params.width = mImageWidth;
                        params.height = mImageHeight;
                        videoPlayer.setLayoutParams(params);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable drawable) {

                    }
                });

        Logs.e("视频链接：" + video_url);

        HttpProxyCacheServer proxy = App.getProxy(mContext);
        String proxyUrl = proxy.getProxyUrl(video_url);

        Logs.e("视频链接(proxyUrl)：" + proxyUrl);
        Jzvd.setMediaInterface(new JZExoPlayer());
        videoPlayer.setUp(proxyUrl, "", JzvdStd.SCREEN_WINDOW_LIST);
        //        videoPlayer.startVideo();
        return videoPlayer;
    }

    private void showWelfareDialog(String data_exchange) {
        CustomDialog dialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_game_detail_welfare_tips, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        if ("1".equals(data_exchange)){
            ((TextView) dialog.findViewById(R.id.tv_content)).setText("安卓端和苹果端角色数据互通。你使用安卓设备或苹果设备玩的角色是相同的。");
        }else if ("2".equals(data_exchange)){
            ((TextView) dialog.findViewById(R.id.tv_content)).setText("安卓端和苹果端角色数据不互通。例如，你使用安卓设备只能获取安卓角色数据，无法获取苹果角色数据。");
        }
        dialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        Jzvd.releaseAllVideos();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_detail_des;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private TextView       mTvContent;
        private TextView       mTvFold;
        private RecyclerView mRecyclerView;
        private LinearLayout       mLlVip;
        private TextView       mTvTag;
        private TextView       mTvTitle;
        private TextView mTvGameModel;
        private TextView mTvType;
        private ImageView mIvTips;

        public ViewHolder(View view) {
            super(view);
            mTvContent = findViewById(R.id.tv_content);
            mTvFold = findViewById(R.id.tv_fold);
            mRecyclerView = findViewById(R.id.recycler_view);
            mLlVip = findViewById(R.id.ll_vip);
            mTvTag = findViewById(R.id.tv_tag);
            mTvTitle = findViewById(R.id.tv_title);
            mTvGameModel = view.findViewById(R.id.tv_game_model);
            mTvType = view.findViewById(R.id.tv_type);
            mIvTips = view.findViewById(R.id.iv_tips);
        }
    }
}
