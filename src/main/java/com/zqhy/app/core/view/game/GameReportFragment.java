package com.zqhy.app.core.view.game;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.box.other.blankj.utilcode.util.Logs;
import com.box.other.hjq.toast.Toaster;
import com.donkingliang.imageselector.event.PhotoEvent;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.ThumbnailBean;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.utilcode.ConstUtils;
import com.zqhy.app.core.tool.utilcode.FileUtils;
import com.zqhy.app.core.view.community.comment.ThumbnailAdapter;
import com.zqhy.app.core.vm.game.GameViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnMultiCompressListener;


/**
 * @author Administrator
 */
public class GameReportFragment extends BaseFragment<GameViewModel>{

    public static GameReportFragment newInstance(String gamename, int gameid) {
        GameReportFragment fragment = new GameReportFragment();
        Bundle bundle = new Bundle();
        bundle.putString("gamename", gamename);
        bundle.putInt("gameid", gameid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_game_report;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    private String gamename;
    private int gameid;
    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            gamename = getArguments().getString("gamename");
            gameid = getArguments().getInt("gameid");
        }
        super.initView(state);
        showSuccess();
        bindViews();
    }

    private ImageView    mIvBack;
    private RecyclerView mRecyclerViewThumbnail;
    private TextView mTvMore;
    private TextView mTvGameName;
    private TextView mTvUserAccount;
    private EditText mEtPlatform;
    private EditText mEtAddress;
    private EditText mEtDiscount;
    private EditText mEtPhone;
    private TextView mTvCommit;

    private void bindViews() {
        mIvBack = findViewById(R.id.iv_back);
        mRecyclerViewThumbnail = findViewById(R.id.recyclerView_thumbnail);

        mTvMore = findViewById(R.id.tv_more);
        mTvGameName = findViewById(R.id.tv_game_name);
        mTvUserAccount = findViewById(R.id.tv_user_account);
        mEtPlatform = findViewById(R.id.et_platform);
        mEtAddress = findViewById(R.id.et_address);
        mEtDiscount = findViewById(R.id.et_discount);
        mEtPhone = findViewById(R.id.et_phone);
        mTvCommit = findViewById(R.id.tv_commit);

        UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
        mTvGameName.setText(gamename);
        if (userInfo != null) mTvUserAccount.setText(userInfo.getUsername());

        initList();

        mIvBack.setOnClickListener(v -> {
            pop();
        });
        mTvMore.setOnClickListener(v -> {
            if (checkLogin()){
                startFragment(MyReportListFragment.newInstance());
            }
        });
        mTvCommit.setOnClickListener(v -> {
            sentReportAction();
        });
    }

    private ThumbnailAdapter mThumbnailAdapter;
    private int              maxPicCount = 3;
    private void initList() {
        GridLayoutManager layoutManager = new GridLayoutManager(_mActivity, 4);
        mRecyclerViewThumbnail.setLayoutManager(layoutManager);

        List<ThumbnailBean> list = new ArrayList<>();

        ThumbnailBean thumbnailBean = new ThumbnailBean();
        thumbnailBean.setType(1);
        list.add(thumbnailBean);

        mThumbnailAdapter = new ThumbnailAdapter(this, list, maxPicCount);
        mRecyclerViewThumbnail.setAdapter(mThumbnailAdapter);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void OnEvent(PhotoEvent photoEvent){
        List<ThumbnailBean> thumbnailBeanList = new ArrayList<>();
        for (String image : photoEvent.getImages()) {
            ThumbnailBean thumbnailBean = new ThumbnailBean();
            thumbnailBean.setType(0);
            thumbnailBean.setImageType(0);
            thumbnailBean.setLocalUrl(image);

            thumbnailBeanList.add(thumbnailBean);
        }

        mThumbnailAdapter.addAll(thumbnailBeanList);
        mThumbnailAdapter.notifyDataSetChanged();

        refreshThumbnails();
    }

    public void refreshThumbnails() {
        if (mThumbnailAdapter.getItemCount() >= (maxPicCount + 1)) {
            //去掉加号键
            deleteThumbnailItem(0);
        } else {
            if (!mThumbnailAdapter.isContainsAdd()) {
                //加上加号键
                ThumbnailBean thumbnailBean = new ThumbnailBean();
                thumbnailBean.setType(1);
                mThumbnailAdapter.add(thumbnailBean);
                mThumbnailAdapter.notifyDataSetChanged();
            }
        }
    }

    private void deleteThumbnailItem(int position) {
        mThumbnailAdapter.deleteItem(position);
        mThumbnailAdapter.notifyDataSetChanged();
    }

    private void sentReportAction() {
        if (TextUtils.isEmpty(mEtPlatform.getText().toString().trim())) {
            //ToastT.warning(_mActivity, "请填写折扣平台名称");
            Toaster.show("请填写折扣平台名称");
            return;
        }
        if (TextUtils.isEmpty(mEtPhone.getText().toString().trim())) {
            //ToastT.warning(_mActivity, "请提供您的手机号");
            Toaster.show("请提供您的手机号");
            return;
        }

        if (mThumbnailAdapter != null) {
            List<ThumbnailBean> thumbnailBeanList = mThumbnailAdapter.getDatas();
            List<File> localPathList = new ArrayList<>();

            for (int i = 0; i < thumbnailBeanList.size(); i++) {
                ThumbnailBean thumbnailBean = thumbnailBeanList.get(i);
                if (thumbnailBean.getType() == 1 || thumbnailBean.getImageType() == 1) {
                    continue;
                }
                File file = new File(thumbnailBean.getLocalUrl());
                int fileSize = (int) FileUtils.getFileSize(file, ConstUtils.MemoryUnit.MB);
                if (fileSize > 3) {
                    Toaster.show("第" + (i + 1) + "张图片大小超过了3MB，请选择小于3MB的图片");
                    //ToastT.warning(_mActivity, "第" + (i + 1) + "张图片大小超过了3MB，请选择小于3MB的图片");
                    return;
                }
                localPathList.add(file);
            }
            compressAction(localPathList);
        }
    }

    private void compressAction(List<File> localPathList) {
        if (localPathList == null) {
            return;
        }
        if (localPathList.isEmpty()) {
            report(localPathList);
        } else {
            loading("图片压缩中...");
            Luban.compress(_mActivity, localPathList)
                    .putGear(Luban.THIRD_GEAR)
                    .setMaxSize(200)
                    .launch(new OnMultiCompressListener() {
                        @Override
                        public void onStart() {
                            Logs.e("compress start");
                        }

                        @Override
                        public void onSuccess(List<File> fileList) {
                            report(fileList);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Logs.e("compress error");
                            e.printStackTrace();
                        }
                    });
        }
    }

    private void report(List<File> localPathList) {
        if (mViewModel != null) {
            String platformName = mEtPlatform.getText().toString().trim();
            String mobile = mEtPhone.getText().toString().trim();
            String downloadUrl = mEtAddress.getText().toString().trim();
            String discount = mEtDiscount.getText().toString().trim();
            mViewModel.addReport(gameid, platformName, mobile, discount, downloadUrl, localPathList, new OnBaseCallback() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    loadingComplete();
                }

                @Override
                public void onBefore() {
                    super.onBefore();
                    loading();
                }

                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            Toaster.show("举报内容上传成功!");
                            //ToastT.success(_mActivity, "举报内容上传成功！");
                            setFragmentResult(Activity.RESULT_OK, null);
                            pop();
                        } else {
                            Toaster.show(data.getMsg());
                            //ToastT.error(_mActivity, data.getMsg());
                        }
                    }
                }
            });
        }
    }

    public void picDelete(int position) {
        deleteThumbnailItem(position);
        refreshThumbnails();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }
}
