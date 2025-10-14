package com.zqhy.app.core.view.game;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.blankj.utilcode.util.Logs;
import com.chaoji.other.hjq.toast.Toaster;
import com.donkingliang.imageselector.event.PhotoEvent;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.ThumbnailBean;
import com.zqhy.app.core.data.model.game.FeedbackInfoItemVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.utilcode.ConstUtils;
import com.zqhy.app.core.tool.utilcode.FileUtils;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.community.comment.ThumbnailAdapter;
import com.zqhy.app.core.view.game.holder.GameFeedbackItemHolder;
import com.zqhy.app.core.vm.game.GameViewModel;
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
public class GameFeedbackFragment extends BaseFragment<GameViewModel>{

    public static GameFeedbackFragment newInstance(String gamename, int gameid) {
        GameFeedbackFragment fragment = new GameFeedbackFragment();
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
        return R.layout.fragment_game_feedback;
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
        bindViews();
        getNetWorkData();
    }

    private ImageView    mIvBack;
    private RecyclerView mRecyclerViewThumbnail;
    private TextView mTvPhoneModel;
    private TextView mTvGameName;
    private EditText mEtQQ;
    private EditText mEtPhone;
    private EditText mEtIdea;
    private TextView mTvCommit;
    private RecyclerView mRecyclerViewType;

    private void bindViews() {
        mIvBack = findViewById(R.id.iv_back);
        mRecyclerViewThumbnail = findViewById(R.id.recyclerView_thumbnail);

        mTvPhoneModel = findViewById(R.id.tv_phone_model);
        mTvGameName = findViewById(R.id.tv_game_name);
        mEtQQ = findViewById(R.id.et_qq);
        mEtPhone = findViewById(R.id.et_phone);
        mEtIdea = findViewById(R.id.et_idea);
        mTvCommit = findViewById(R.id.tv_commit);

        mRecyclerViewType = findViewById(R.id.recycler_view_type);

        mTvPhoneModel.setText(Build.BRAND + "-" + Build.MODEL);
        mTvGameName.setText(gamename);

        initList();

        mIvBack.setOnClickListener(v -> {
            pop();
        });

        mTvCommit.setOnClickListener(v -> {
            String qqNumber = mEtQQ.getText().toString().trim();
            String content = mEtIdea.getText().toString().trim();
            if (TextUtils.isEmpty(qqNumber)){
                Toaster.show("请填写联系QQ");
                //Toaster.show( "请填写联系QQ");
                return;
            }
            if (TextUtils.isEmpty(content)){
                Toaster.show("请描述您的问题");
                //Toaster.show( "请描述您的问题");
                return;
            }
            /*if (mThumbnailAdapter.getItemCount() < 4){
                Toaster.show( "请最少上传3张图片！");
                return;
            }*/
            showTipsDialog();
        });
    }

    private ThumbnailAdapter mThumbnailAdapter;
    private int              maxPicCount = 5;
    private BaseRecyclerAdapter mAdapter;
    private void initList() {
        GridLayoutManager layoutManager = new GridLayoutManager(_mActivity, 4);
        mRecyclerViewThumbnail.setLayoutManager(layoutManager);

        List<ThumbnailBean> list = new ArrayList<>();

        ThumbnailBean thumbnailBean = new ThumbnailBean();
        thumbnailBean.setType(1);
        list.add(thumbnailBean);

        mThumbnailAdapter = new ThumbnailAdapter(this, list, maxPicCount);
        mRecyclerViewThumbnail.setAdapter(mThumbnailAdapter);

        GridLayoutManager layoutManager1 = new GridLayoutManager(_mActivity, 3);
        mRecyclerViewType.setLayoutManager(layoutManager1);

        mAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(FeedbackInfoItemVo.CateBean.class, new GameFeedbackItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);

        mRecyclerViewType.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((v, position, data) -> {
            for (int i = 0; i < mAdapter.getData().size(); i++) {
                if (i == position){
                    ((FeedbackInfoItemVo.CateBean)mAdapter.getData().get(i)).setSelect(true);
                }else {
                    ((FeedbackInfoItemVo.CateBean)mAdapter.getData().get(i)).setSelect(false);
                }
            }
            mAdapter.notifyDataSetChanged();
        });
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
                    //Toaster.show( "第" + (i + 1) + "张图片大小超过了3MB，请选择小于3MB的图片");
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
            String phoneModel = mTvPhoneModel.getText().toString().trim();
            String mobile = mEtPhone.getText().toString().trim();
            String qqNumber = mEtQQ.getText().toString().trim();
            String content = mEtIdea.getText().toString().trim();
            String cateId = "";
            for (int i = 0; i < mAdapter.getData().size(); i++) {
                if (((FeedbackInfoItemVo.CateBean)mAdapter.getData().get(i)).isSelect()){
                    cateId = String.valueOf(((FeedbackInfoItemVo.CateBean)mAdapter.getData().get(i)).getId());
                }
            }
            mViewModel.kefuGameFeedback(gameid, phoneModel, mobile, content, qqNumber, cateId, localPathList, new OnBaseCallback() {

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
                            //ToastT.success(_mActivity, "反馈成功！");
                            Toaster.show("反馈成功");
                            setFragmentResult(Activity.RESULT_OK, null);
                            pop();
                        } else {
                            Toaster.show(data.getMsg());
                            //Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void getNetWorkData() {
        if (mViewModel != null) {
            mViewModel.gameFeedbackInfo(new OnBaseCallback<FeedbackInfoItemVo>(){

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                }

                @Override
                public void onSuccess(FeedbackInfoItemVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null && data.getData().getCate() != null && data.getData().getCate().size() > 0) {
                                data.getData().getCate().get(0).setSelect(true);
                                mAdapter.addAllData(data.getData().getCate());
                            }
                        }else {
                            Toaster.show(data.getMsg());
                            //Toaster.show( data.getMsg());
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


    public void showTipsDialog() {
        CustomDialog tipDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_game_feedback_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        tipDialog.findViewById(R.id.tv_cancel).setOnClickListener(v -> {
            if (tipDialog != null && tipDialog.isShowing()){
                tipDialog.dismiss();
            }
        });
        tipDialog.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            if (tipDialog != null && tipDialog.isShowing()){
                tipDialog.dismiss();
            }
            sentReportAction();
        });
        tipDialog.show();
    }
}
