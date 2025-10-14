package com.zqhy.app.core.view.cloud;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chaoji.im.glide.GlideApp;
import com.chaoji.other.hjq.toast.Toaster;
import com.google.android.material.imageview.ShapeableImageView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.cloud.CloudDeviceListVo;
import com.zqhy.app.core.data.model.cloud.CloudScreenVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.GameListVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.AppUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.browser.BrowserCloudActivity;
import com.zqhy.app.core.view.cloud.holder.CloudGameItemHolder;
import com.zqhy.app.core.view.transaction.util.CustomPopWindow;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.cloud.CloudViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.ButtonClickUtils;
import com.zqhy.app.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @date 2018/11/12
 */

public class CloudGuideFragment extends BaseFragment<CloudViewModel> {

    public static CloudGuideFragment newInstance() {
        CloudGuideFragment fragment = new CloudGuideFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_cloud_guide;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    protected String getUmengPageName() {
        return "云挂机";
    }

    private boolean isToPay = false;
    private boolean isLoadGame = false;
    @Override
    public void initView(Bundle state) {
        super.initView(state);
        bindView();
        initDate();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isToPay){
            initDate();
            isToPay = false;
        }
        if (isLoadGame && cloudDeviceListVo != null && cloudDeviceListVo.getList() != null && cloudDeviceListVo.getList().size() > selectDeviceIndex){
            /*CloudDeviceListVo.DeviceBean deviceBean = cloudDeviceListVo.getList().get(selectDeviceIndex);
            getScreen(deviceBean.getId(), 1);*/
            initDate();
            isLoadGame = false;
        }
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        if (UserInfoModel.getInstance().isLogined()){
            initDate();
        }
    }

    private LinearLayout mLlGuide;
    private TextView mTvCourse;
    private TextView mTvGameList;
    private TextView mTvAction;
    private TextView mTvDevice;
    private ConstraintLayout mClDeviceInfo;
    private ShapeableImageView mIvBgImage;
    private ImageView mIvTopBg;
    private ImageView mIvIssue;
    private TextView mTvDeviceName;
    private TextView mTvDeviceTime;
    private TextView mTvGameName;
    private TextView mTvDeviceStatus;
    private LinearLayout mLlEmptyView;
    private LinearLayout mLlRefresh;
    private LinearLayout mLlRenew;
    private LinearLayout mLlShut;
    private LinearLayout mLlBuy;
    private void bindView() {
        findViewById(R.id.iv_back).setOnClickListener(v -> pop());

        mLlGuide = findViewById(R.id.ll_guide);
        mTvCourse = findViewById(R.id.tv_course);
        mTvGameList = findViewById(R.id.tv_game_list);
        mTvAction = findViewById(R.id.tv_action);

        mTvDevice = findViewById(R.id.tv_device);
        mClDeviceInfo = findViewById(R.id.cl_device_info);
        mIvBgImage = findViewById(R.id.iv_bg_image);
        mIvTopBg = findViewById(R.id.iv_top_bg);
        mIvIssue = findViewById(R.id.iv_issue);
        mTvDeviceName = findViewById(R.id.tv_device_name);
        mTvDeviceTime = findViewById(R.id.tv_device_time);
        mTvGameName = findViewById(R.id.tv_game_name);
        mTvDeviceStatus = findViewById(R.id.tv_device_status);
        mLlEmptyView = findViewById(R.id.ll_empty_view);
        mLlRefresh = findViewById(R.id.ll_refresh);
        mLlRenew = findViewById(R.id.ll_renew);
        mLlShut = findViewById(R.id.ll_shut);
        mLlBuy = findViewById(R.id.ll_buy);

        mTvCourse.setOnClickListener(v -> {
            startFragment(CloudCourseFragment.newInstance());
        });

        mTvGameList.setOnClickListener(v -> {
            //showSelectGameDialog(false);
            showSelectGameDialog(false);
        });

        mTvAction.setOnClickListener(v -> {
            if (cloudDeviceListVo.getNew_user() == 1 && cloudDeviceListVo.getFreetime() > 0){//可领新人福利
                showTrialDialog();
            }else{
                //showBuyDialog();
                startFragment(CloudBuyFragment.newInstance(0));
            }
        });

        mTvDevice.setOnClickListener(v -> {
            showPopupView();
        });

        mTvDeviceName.setOnClickListener(v -> {
            showEditDialog();
        });

        mIvIssue.setOnClickListener(v -> {
            startFragment(CloudCourseFragment.newInstance());
        });

        mLlRefresh.setOnClickListener(v -> {
            CloudDeviceListVo.DeviceBean deviceBean = cloudDeviceListVo.getList().get(selectDeviceIndex);
            if (!"0".equals(deviceBean.getGameid())){
                getScreen(deviceBean.getId(), 1);
            }
        });

        mLlRenew.setOnClickListener(v -> {
            if (checkLogin()) {
                startFragment(CloudBuyFragment.newInstance(cloudDeviceListVo, selectDeviceIndex));
                isToPay = true;
            }
        });

        mLlShut.setOnClickListener(v -> {
            CloudDeviceListVo.DeviceBean deviceBean = cloudDeviceListVo.getList().get(selectDeviceIndex);
            if (!"0".equals(deviceBean.getGameid())){
                showEixtDialog(deviceBean.getId());
            }
        });

        mIvBgImage.setOnClickListener(v -> {
            if (checkLogin()){
                CloudDeviceListVo.DeviceBean deviceBean = cloudDeviceListVo.getList().get(selectDeviceIndex);
                if ("0".equals(deviceBean.getGameid())) {//闲置
                    showSelectGameDialog(true);
                }else {
                    if (ButtonClickUtils.isFastClick()) {
                        return;
                    }
                    openCloud(deviceBean.getGamecode(), deviceBean.getId());
                }
            }
        });

        mLlBuy.setOnClickListener(v -> {
            if (checkLogin()) {
                startFragment(CloudBuyFragment.newInstance(cloudDeviceListVo.getList().size()));
                isToPay = true;
            }
        });
    }

    private void openCloud(String gamecode, String gid){
        if (checkLogin()) {
            CloudDeviceListVo.DeviceBean deviceBean = cloudDeviceListVo.getList().get(selectDeviceIndex);
            String baseUrl = cloudDeviceListVo.getBase_url();
            String url = addParams(baseUrl, gamecode, gid);
            BrowserCloudActivity.newInstance(_mActivity, url, true, deviceBean.getGamename(), String.valueOf(deviceBean.getGameid()));
            isLoadGame = true;
        }
    }

    private String addParams(String url, String gamecode, String gid) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        AppUtil.UrlEntity urlEntity = AppUtil.parse(url);
        if (urlEntity.params == null) {
            urlEntity.params = new HashMap<>();
        }
        urlEntity.params.put("gamecode", gamecode);
        urlEntity.params.put("gid", gid);

        return urlEntity.getEntireUrl();
    }

    private void showView(){
        if (cloudDeviceListVo != null){
            if (cloudDeviceListVo.getList() != null && cloudDeviceListVo.getList().size() > 0){
                mLlGuide.setVisibility(View.GONE);
                mClDeviceInfo.setVisibility(View.VISIBLE);
                mLlBuy.setVisibility(View.VISIBLE);
                mTvDevice.setVisibility(View.VISIBLE);
                initDevice(true);
            }else {
                mLlGuide.setVisibility(View.VISIBLE);
                mClDeviceInfo.setVisibility(View.GONE);
                mLlBuy.setVisibility(View.GONE);
                mTvDevice.setVisibility(View.GONE);
                if (cloudDeviceListVo.getNew_user() == 1 && cloudDeviceListVo.getFreetime() > 0){//可领新人福利
                    mTvAction.setText("新人免费试用（" + cloudDeviceListVo.getFreetime() + "小时）");
                }else {
                    mTvAction.setText("购买设备");
                }
            }
        }
    }

    private int selectDeviceIndex = 0;
    private void initDevice(boolean needImage){
        CloudDeviceListVo.DeviceBean deviceBean = cloudDeviceListVo.getList().get(selectDeviceIndex);
        if (deviceBean != null){
            mTvDevice.setText(deviceBean.getName());
            mTvDeviceName.setText(deviceBean.getName());
            mTvDeviceTime.setText(CommonUtils.formatTimeStamp(Long.parseLong(deviceBean.getExpiry_time()) * 1000, "到期时间：yyyy/MM/dd  HH:mm"));
            mTvGameName.setText("游戏名称："+ deviceBean.getGamename());
            if ("0".equals(deviceBean.getGameid())){//闲置
                mTvDeviceStatus.setText("闲置中");
                mTvDeviceStatus.setTextColor(Color.parseColor("#6CE8B2"));
                mIvTopBg.setVisibility(View.GONE);
                mTvGameName.setVisibility(View.GONE);
                mLlShut.setVisibility(View.GONE);
                mTvDeviceName.setTextColor(Color.parseColor("#232323"));
                mTvDeviceTime.setTextColor(Color.parseColor("#232323"));
                mLlEmptyView.setVisibility(View.VISIBLE);
                mIvBgImage.setImageResource(R.mipmap.ic_cloud_guide_bg);
                mTvDeviceName.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.ic_cloud_guide_edit), null);
            }else {
                mTvDeviceStatus.setText("挂机中");
                mTvDeviceStatus.setTextColor(Color.parseColor("#F26D2F"));
                mIvTopBg.setVisibility(View.VISIBLE);
                mTvGameName.setVisibility(View.VISIBLE);
                mLlShut.setVisibility(View.VISIBLE);
                mTvDeviceName.setTextColor(Color.parseColor("#FFFFFF"));
                mTvDeviceTime.setTextColor(Color.parseColor("#FFFFFF"));
                mTvGameName.setTextColor(Color.parseColor("#FFFFFF"));
                mLlEmptyView.setVisibility(View.GONE);
                if (needImage) {
                    getScreen(deviceBean.getId(), 1);
                }else {
                    mIvBgImage.setImageResource(R.mipmap.ic_cloud_guide_bg);
                }
                mTvDeviceName.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.ic_cloud_guide_edit_white), null);
            }
        }
    }

    private void initImageBg(){
        mIvBgImage.setImageResource(R.mipmap.ic_cloud_guide_bg);
    }

    private CloudDeviceListVo.DataBean cloudDeviceListVo;
    private void initDate(){
        if (mViewModel != null){
            mViewModel.getDeviceList(new OnBaseCallback<CloudDeviceListVo>(){
                @Override
                public void onSuccess(CloudDeviceListVo data) {
                    showSuccess();
                    if (data != null && data.isStateOK()){
                        if (data.getData() != null){
                            cloudDeviceListVo = data.getData();
                            showView();
                        }
                    }
                }
            });
        }
    }

    private void freeTrial(){
        if (mViewModel != null){
            mViewModel.freeTrial(new OnBaseCallback(){
                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null && data.isStateOK()){
                        initDate();
                    }else {
                        Toaster.show( data.getMsg());
                    }
                }
            });
        }
    }

    private void updateDevice(String name){
        if (mViewModel != null){
            CloudDeviceListVo.DeviceBean deviceBean = cloudDeviceListVo.getList().get(selectDeviceIndex);
            Map<String, String> map = new HashMap<>();
            map.put("id", deviceBean.getId());
            if (!TextUtils.isEmpty(name)){
                map.put("name", name);
            }
            mViewModel.updateDevice(map, new OnBaseCallback(){
                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null && data.isStateOK()){
                        if (!TextUtils.isEmpty(name)) {
                            deviceBean.setName(name);
                            mTvDevice.setText(deviceBean.getName());
                            mTvDeviceName.setText(deviceBean.getName());
                        }
                    }else {
                        Toaster.show( data.getMsg());
                    }
                }
            });
        }
    }

    private void getScreen(String gid, int type){
        if (mViewModel != null){
            Map<String, String> map = new HashMap<>();
            map.put("gid", gid);
            map.put("type", String.valueOf(type));
            mViewModel.getScreen(map, new OnBaseCallback<CloudScreenVo>(){
                @Override
                public void onSuccess(CloudScreenVo data) {
                    if (data != null && data.isStateOK()){
                        CloudDeviceListVo.DeviceBean deviceBean = cloudDeviceListVo.getList().get(selectDeviceIndex);
                        if (type == 1){
                            GlideApp.with(_mActivity)
                                .asBitmap()
                                .load(data.getData())
                                .into(new CustomTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                                        if (bitmap == null) {
                                            return;
                                        }
                                        if (gid.equals(deviceBean.getId()) && !"0".equals(deviceBean.getGameid())){
                                            mIvBgImage.setImageBitmap(bitmap);
                                        }
                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable drawable) {

                                    }
                                });
                        }else if (type == 2){
                            if (gid.equals(deviceBean.getId())){
                                deviceBean.setGameid("0");
                                deviceBean.setGamecode("");
                                deviceBean.setGamename("");
                                initDevice(false);
                            }else {
                                if (cloudDeviceListVo.getList() != null){
                                    for (int i = 0; i < cloudDeviceListVo.getList().size(); i++) {
                                        if (cloudDeviceListVo.getList().get(i).getId().equals(gid)){
                                            deviceBean.setGameid("0");
                                            deviceBean.setGamecode("");
                                            deviceBean.setGamename("");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    private int page = 1;
    private void getGameList(String kw){
        if (mViewModel != null){
            Map<String, String> map = new HashMap<>();
            map.put("kw", kw);
            map.put("page", String.valueOf(page));
            mViewModel.getGameList(map, new OnBaseCallback<GameListVo>(){
                @Override
                public void onSuccess(GameListVo data) {
                    mGameRecyclerView.refreshComplete();
                    mGameRecyclerView.loadMoreComplete();
                    if (data != null && data.isStateOK()){
                        if (data.getData() != null && !data.getData().isEmpty()) {
                            if (page <= 1) {
                                gameAdapter.setDatas(data.getData());
                            }else {
                                gameAdapter.addAllData(data.getData());
                            }
                            if (data.getData().size() < 12) {
                                //has no more data
                                page = -1;
                                mGameRecyclerView.setNoMore(true);
                            }
                            gameAdapter.notifyDataSetChanged();
                        } else {
                            if (page == 1) {
                                gameAdapter.setDatas(new ArrayList());
                                gameAdapter.addData(new EmptyDataVo(R.mipmap.img_empty_data_1)
                                        .setLayout(EmptyDataVo.LAYOUT_WRAP_CONTENT)
                                        .setEmptyWord("支持云挂机游戏持续更新中，敬请期待...")
                                        .setEmptyWordColor(R.color.color_9b9b9b));
                            } else {
                                //has no more data
                            }
                            page = -1;
                            mGameRecyclerView.setNoMore(true);
                            gameAdapter.notifyDataSetChanged();
                        }
                        /*if (data.getData() != null && data.getData().size() > 0){
                            gameAdapter.setDatas(data.getData());
                            gameAdapter.notifyDataSetChanged();
                        }else {
                            gameAdapter.notifyDataSetChanged();
                        }*/
                    }else {
                        gameAdapter.setDatas(new ArrayList());
                        gameAdapter.addData(new EmptyDataVo(R.mipmap.img_empty_data_1)
                                .setLayout(EmptyDataVo.LAYOUT_WRAP_CONTENT)
                                .setEmptyWord("支持云挂机游戏持续更新中，敬请期待...")
                                .setEmptyWordColor(R.color.color_9b9b9b));
                        gameAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    private void showTrialDialog(){
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_cloud_free_trial, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (checkLogin()){
                if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
                freeTrial();
            }
        });
        tipsDialog.findViewById(R.id.tv_cancel).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.show();
    }

    private void showBuyDialog(){
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_cloud_buy, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (checkLogin()){
                if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
                startFragment(CloudBuyFragment.newInstance(0));
                isToPay = true;
            }
        });
        tipsDialog.findViewById(R.id.tv_cancel).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.show();
    }

    private void showEditDialog(){
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_cloud_edit, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        EditText mEtName = tipsDialog.findViewById(R.id.et_name);
        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            String str = mEtName.getText().toString().toString();
            if (TextUtils.isEmpty(str)){
                Toaster.show("请输入设备名");
                return;
            }
            if (checkLogin()){
                if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
                updateDevice(str);
            }
        });
        tipsDialog.findViewById(R.id.tv_cancel).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.show();
    }

    private void showEixtDialog(String id){
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_cloud_out, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (checkLogin()){
                if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
                getScreen(id, 2);
            }
        });
        tipsDialog.findViewById(R.id.tv_cancel).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.show();
    }

    private CustomDialog selectGameDialog;
    private XRecyclerView mGameRecyclerView;
    private BaseRecyclerAdapter gameAdapter;
    private void showSelectGameDialog(boolean needSkip){
        if (selectGameDialog == null) {
            selectGameDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_cloud_select_game, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);

            EditText mEtSearch = selectGameDialog.findViewById(R.id.et_search);
            ImageView mIvDelete = selectGameDialog.findViewById(R.id.iv_delete);
            TextView mTvSearch = selectGameDialog.findViewById(R.id.tv_search);
            mGameRecyclerView = selectGameDialog.findViewById(R.id.recyclerView);
            mGameRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));

            gameAdapter = new BaseRecyclerAdapter.Builder<>()
                    .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                    .bind(GameInfoVo.class, new CloudGameItemHolder(_mActivity))
                    .build().setTag(R.id.tag_fragment, _mActivity)
                    .setTag(R.id.tag_fragment, this);

            mGameRecyclerView.setAdapter(gameAdapter);

            mGameRecyclerView.setLoadingMoreEnabled(true);
            mGameRecyclerView.setPullRefreshEnabled(false);

            mGameRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    page = 1;
                    getGameList("");
                }

                @Override
                public void onLoadMore() {
                    if (page < 0) {
                        return;
                    }
                    page++;
                    getGameList("");
                }
            });

            gameAdapter.setOnItemClickListener((v, position, data) -> {
                if (selectGameDialog != null && selectGameDialog.isShowing()) selectGameDialog.dismiss();
                if (needSkip){
                    if (data instanceof GameInfoVo){
                        if (ButtonClickUtils.isFastClick()) {
                            return;
                        }
                        openCloud((((GameInfoVo) data).getGamecode()), cloudDeviceListVo.getList().get(selectDeviceIndex).getId());
                    }
                }else {
                    //Toaster.show("暂无设备");
                    if (cloudDeviceListVo.getNew_user() == 1 && cloudDeviceListVo.getFreetime() > 0){//可领新人福利
                        showTrialDialog();
                    }else{
                        showBuyDialog();
                    }
                }
            });

            mEtSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s.toString().trim())) {
                        mIvDelete.setVisibility(View.GONE);
                    }else {
                        mIvDelete.setVisibility(View.VISIBLE);
                    }
                }
            });

            mIvDelete.setOnClickListener(v -> {
                mEtSearch.setText("");
                page = 1;
                getGameList("");
            });

            selectGameDialog.findViewById(R.id.iv_close).setOnClickListener(v -> {
                if (selectGameDialog != null && selectGameDialog.isShowing()) selectGameDialog.dismiss();
            });

            mTvSearch.setOnClickListener(v -> {
                String str = mEtSearch.getText().toString().toString();
                if (TextUtils.isEmpty(str)){
                    Toaster.show("请输入游戏名");
                    return;
                }
                page = 1;
                getGameList(str);
            });
            page = 1;
            getGameList("");
        }
        if (!selectGameDialog.isShowing()) selectGameDialog.show();
    }

    private CustomPopWindow popWindow;
    private void showPopupView(){
        View contentView = LayoutInflater.from(_mActivity).inflate(R.layout.pop_cloud_change_device, null);
        RecyclerView mRecyclerViewPop = contentView.findViewById(R.id.recyclerView_pop);
        popWindow = new CustomPopWindow.PopupWindowBuilder(_mActivity)
                .setView(contentView)
                .setFocusable(true)
                .setBgDarkAlpha(0.7F)
                .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT) //显示大小
                .setOutsideTouchable(true)
                .create();

        mRecyclerViewPop.setLayoutManager(new LinearLayoutManager(_mActivity));

        mRecyclerViewPop.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(_mActivity).inflate(R.layout.item_cloud_device_name, parent, false));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                MyViewHolder mHolder = (MyViewHolder)holder;
                int myPosition = position;
                mHolder.mTvDeviceName.setText(cloudDeviceListVo.getList().get(position).getName());
                if (position == selectDeviceIndex){
                    mHolder.mTvDeviceName.setTextColor(Color.parseColor("#5571FE"));
                }else {
                    mHolder.mTvDeviceName.setTextColor(Color.parseColor("#666666"));
                }

                mHolder.itemView.setOnClickListener(v -> {
                    mTvDevice.setText(cloudDeviceListVo.getList().get(position).getName());
                    selectDeviceIndex = myPosition;
                    notifyDataSetChanged();
                    initDevice(true);
                    initImageBg();
                    popWindow.dissmiss();
                });
            }

            @Override
            public int getItemCount() {
                if (cloudDeviceListVo != null && cloudDeviceListVo.getList() != null){
                    return cloudDeviceListVo.getList().size();
                }
                return 0;
            }

            class MyViewHolder extends RecyclerView.ViewHolder{
                private TextView mTvDeviceName;
                public MyViewHolder(@NonNull View itemView) {
                    super(itemView);
                    mTvDeviceName = itemView.findViewById(R.id.tv_device_name);
                }
            }
        });

        popWindow.showAsDropDown(mTvDevice);//指定view下方
    }
}
