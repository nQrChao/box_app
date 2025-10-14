package com.zqhy.app.core.view.cloud;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.cloud.CloudDeviceListVo;
import com.zqhy.app.core.data.model.cloud.CloudPayInfoVo;
import com.zqhy.app.core.data.model.user.PayInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.AbsPayBuyFragment;
import com.zqhy.app.core.view.cloud.holder.CloudPayInfoItemHolder;
import com.zqhy.app.core.view.transaction.util.CustomPopWindow;
import com.zqhy.app.core.vm.cloud.CloudViewModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @date 2018/11/12
 */

public class CloudBuyFragment extends AbsPayBuyFragment<CloudViewModel> {

    public static CloudBuyFragment newInstance(int deviceCount) {
        CloudBuyFragment fragment = new CloudBuyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("deviceCount", deviceCount);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static CloudBuyFragment newInstance(CloudDeviceListVo.DataBean cloudDeviceListVo, int selectDeviceIndex) {
        CloudBuyFragment fragment = new CloudBuyFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("cloudDeviceListVo", cloudDeviceListVo);
        bundle.putInt("selectDeviceIndex", selectDeviceIndex);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_cloud_buy;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    protected String getUmengPageName() {
        return "云挂机";
    }

    private CloudDeviceListVo.DataBean cloudDeviceListVo;
    private int selectDeviceIndex;
    private int deviceCount;
    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            cloudDeviceListVo = (CloudDeviceListVo.DataBean)getArguments().getSerializable("cloudDeviceListVo");
            selectDeviceIndex = getArguments().getInt("selectDeviceIndex", 0);
            deviceCount = getArguments().getInt("deviceCount", 0);
        }
        super.initView(state);
        bindView();
        initData();
    }

    @Override
    protected int getPayAction() {
        return PAY_ACTION_TOP_UP;
    }

    private ImageView mIvNotice;
    private RecyclerView mRecyclerView;
    private BaseRecyclerAdapter mAdapter;
    private ConstraintLayout mClCount;
    private ImageView mIvUp;
    private ImageView mIvDown;
    private TextView mTvCount;
    private TextView mTvPrice;
    private LinearLayout mLlPayAlipay;
    private LinearLayout mLlPayWechat;
    private ImageView mIvPayAlipay;
    private ImageView mIvPayWechat;
    private TextView mTvPay;

    private ConstraintLayout mClDevice;
    private TextView mTvDevice;
    private ConstraintLayout mClTime;
    private TextView mTvTime;

    private int count = 1;
    private int selectIndex = 0;

    private void bindView() {
        findViewById(R.id.iv_back).setOnClickListener(v -> pop());

        mIvNotice = findViewById(R.id.iv_notice);
        mRecyclerView = findViewById(R.id.recyclerView);
        mClCount = findViewById(R.id.cl_count);
        mIvUp = findViewById(R.id.iv_up);
        mIvDown = findViewById(R.id.iv_down);
        mTvCount = findViewById(R.id.tv_count);
        mTvPrice = findViewById(R.id.tv_price);
        mLlPayAlipay = findViewById(R.id.ll_pay_alipay);
        mLlPayWechat = findViewById(R.id.ll_pay_wechat);
        mIvPayAlipay = findViewById(R.id.iv_pay_alipay);
        mIvPayWechat = findViewById(R.id.iv_pay_wechat);
        mTvPay = findViewById(R.id.tv_pay);
        mClDevice = findViewById(R.id.cl_device);
        mTvDevice = findViewById(R.id.tv_device);
        mClTime = findViewById(R.id.cl_time);
        mTvTime = findViewById(R.id.tv_time);

        if (cloudDeviceListVo == null){
            mClCount.setVisibility(View.VISIBLE);
            mClDevice.setVisibility(View.GONE);
            mClTime.setVisibility(View.GONE);
        }else {
            mClCount.setVisibility(View.GONE);
            mClDevice.setVisibility(View.VISIBLE);
            mClTime.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(CloudPayInfoVo.DataBean.class, new CloudPayInfoItemHolder(_mActivity))
                .build().setTag(R.id.tag_fragment, _mActivity)
                .setTag(R.id.tag_fragment, this);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((v, position, data) -> {
            for (int i = 0; i < mAdapter.getData().size(); i++) {
                ((CloudPayInfoVo.DataBean)mAdapter.getData().get(i)).setSelected(false);
            }
            selectIndex = position;
            ((CloudPayInfoVo.DataBean)mAdapter.getData().get(position)).setSelected(true);
            mAdapter.notifyDataSetChanged();
            setPrice();
            initDevice();
        });

        mIvNotice.setOnClickListener(v -> {
            startFragment(CloudNoticeFragment.newInstance());
        });

        mIvUp.setOnClickListener(v -> {
            count++;
            mTvCount.setText(String.valueOf(count));
            setPrice();
        });
        mIvDown.setOnClickListener(v -> {
            if (count > 1){
                count--;
                mTvCount.setText(String.valueOf(count));
                setPrice();
            }
        });
        mLlPayAlipay.setOnClickListener(v -> {
            if (PAY_TYPE != PAY_TYPE_ALIPAY){
                PAY_TYPE = PAY_TYPE_ALIPAY;
                mIvPayAlipay.setImageResource(R.mipmap.ic_vip_member_pay_check);
                mIvPayWechat.setImageResource(R.mipmap.ic_vip_member_pay_uncheck);
            }
        });
        mLlPayWechat.setOnClickListener(v -> {
            if (PAY_TYPE != PAY_TYPE_WECHAT){
                PAY_TYPE = PAY_TYPE_WECHAT;
                mIvPayAlipay.setImageResource(R.mipmap.ic_vip_member_pay_uncheck);
                mIvPayWechat.setImageResource(R.mipmap.ic_vip_member_pay_check);
            }
        });
        mTvPay.setOnClickListener(v -> {
            if (checkLogin()){
                String buyTypeId = "";
                for (int i = 0; i < mAdapter.getData().size(); i++) {
                    CloudPayInfoVo.DataBean dataBean = (CloudPayInfoVo.DataBean) mAdapter.getData().get(i);
                    if (dataBean.isSelected()) {
                        buyTypeId = dataBean.getId();
                    }
                }
                if (!TextUtils.isEmpty(buyTypeId)){
                    launchPay(buyTypeId);
                }
            }
        });

        mTvDevice.setOnClickListener(v -> {
            showPopupView();
        });

        mLlPayAlipay.performClick();
    }

    private void setPrice(){
        CloudPayInfoVo.DataBean dataBean = (CloudPayInfoVo.DataBean) mAdapter.getData().get(selectIndex);
        if (dataBean.isSelected()) {
            mTvPrice.setText(CommonUtils.saveFloatPoint(Float.parseFloat(dataBean.getPrice()) * count, 2, BigDecimal.ROUND_DOWN) + "元");
        }
    }

    private void initData() {
        if (mViewModel != null){
            mViewModel.getPayInfo(new OnBaseCallback<CloudPayInfoVo>(){
                @Override
                public void onSuccess(CloudPayInfoVo data) {
                    if (data != null && data.isStateOK()){
                        if (data.getData() != null && data.getData().size() > 0){
                            if (selectIndex >= data.getData().size()){
                                selectIndex = 0;
                            }
                            data.getData().get(selectIndex).setSelected(true);
                            mAdapter.setDatas(data.getData());
                            mAdapter.notifyDataSetChanged();
                            setPrice();
                            initDevice();
                        }
                    }
                    showSuccess();
                }
            });
        }
    }

    private void initDevice(){
        if (cloudDeviceListVo != null){
            CloudPayInfoVo.DataBean dataBean = (CloudPayInfoVo.DataBean) mAdapter.getData().get(selectIndex);
            CloudDeviceListVo.DeviceBean deviceBean = cloudDeviceListVo.getList().get(selectDeviceIndex);
            mTvDevice.setText(deviceBean.getName());
            mTvTime.setText(CommonUtils.formatTimeStamp((Long.parseLong(deviceBean.getExpiry_time()) + (Integer.parseInt(dataBean.getDay())*24*60*60)) * 1000, "yyyy-MM-dd  HH:mm"));
        }
    }

    public void launchPay(String id) {
        if (mViewModel != null) {
            Map<String, String> map = new HashMap<>();
            map.put("id", id);
            map.put("pay_type", String.valueOf(PAY_TYPE));
            if (cloudDeviceListVo != null) {
                map.put("gid", cloudDeviceListVo.getList().get(selectDeviceIndex).getId());
            }else {
                map.put("num", String.valueOf(count));
            }
            mViewModel.launchPay(map, new OnBaseCallback<PayInfoVo>() {
                @Override
                public void onSuccess(PayInfoVo payInfoVo) {
                    if (payInfoVo != null) {
                        if (payInfoVo.isStateOK()) {
                            if (payInfoVo.getData() != null) {
                                doPay(payInfoVo.getData());
                                isPayStart = true;
                            }
                        } else {
                            Toaster.show( payInfoVo.getMsg());
                        }
                    }
                }
            });
        }
    }

    private CustomPopWindow popWindow;
    private void showPopupView(){
        if (popWindow == null){
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
                        if (cloudDeviceListVo != null){
                            initDevice();
                        }
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
        }
        popWindow.showAsDropDown(mTvDevice);//指定view下方
    }

    private boolean isPayStart = false;
    @Override
    public void onResume() {
        super.onResume();
        if (isPayStart){
            getDeviceList();
        }
    }

    private void getDeviceList(){
        if (mViewModel != null){
            mViewModel.getDeviceList(new OnBaseCallback<CloudDeviceListVo>(){
                @Override
                public void onSuccess(CloudDeviceListVo data) {
                    if (data != null && data.isStateOK()){
                        if (data.getData() != null){
                            CloudDeviceListVo.DataBean data1 = data.getData();
                            if (cloudDeviceListVo != null) {
                                if (!cloudDeviceListVo.getList().get(selectDeviceIndex).getExpiry_time().equals(data1.getList().get(selectDeviceIndex).getExpiry_time())){//截止时间不同
                                    Toaster.show( "支付成功");
                                    pop();
                                }
                            }else {
                                if (data1.getList() != null && data1.getList().size() > deviceCount){
                                    Toaster.show( "支付成功");
                                    pop();
                                }
                            }
                        }
                    }
                }
            });
        }
    }
}
