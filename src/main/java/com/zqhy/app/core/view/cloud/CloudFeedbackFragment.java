package com.zqhy.app.core.view.cloud;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.blankj.utilcode.util.Logs;
import com.chaoji.other.hjq.toast.Toaster;
import com.donkingliang.imageselector.event.PhotoEvent;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.ThumbnailBean;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.utilcode.ConstUtils;
import com.zqhy.app.core.tool.utilcode.FileUtils;
import com.zqhy.app.core.view.community.comment.ThumbnailAdapter;
import com.zqhy.app.core.vm.cloud.CloudViewModel;
import com.zqhy.app.newproject.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnMultiCompressListener;

/**
 * @author Administrator
 * @date 2018/11/12
 */

public class CloudFeedbackFragment extends BaseFragment<CloudViewModel> {

    public static CloudFeedbackFragment newInstance() {
        CloudFeedbackFragment fragment = new CloudFeedbackFragment();
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
        return R.layout.fragment_cloud_feedback;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    protected String getUmengPageName() {
        return "云挂机";
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        bindView();
    }

    private TextView mTvCondition1;
    private TextView mTvCondition2;
    private TextView mTvCondition3;
    private TextView mTvCondition4;
    private TextView mTvCondition5;
    private EditText mEtContent;
    private RecyclerView mRecyclerViewThumbnail;
    private TextView mTvConfirm;
    private void bindView() {
        findViewById(R.id.iv_back).setOnClickListener(v -> pop());

        mTvCondition1 = findViewById(R.id.tv_condition_1);
        mTvCondition2 = findViewById(R.id.tv_condition_2);
        mTvCondition3 = findViewById(R.id.tv_condition_3);
        mTvCondition4 = findViewById(R.id.tv_condition_4);
        mTvCondition5 = findViewById(R.id.tv_condition_5);
        mEtContent = findViewById(R.id.et_content);
        mRecyclerViewThumbnail = findViewById(R.id.recyclerView_thumbnail);
        mTvConfirm = findViewById(R.id.tv_confirm);

        initList();

        mTvCondition1.setOnClickListener(v -> {
            cate_id = 51;
            mTvCondition1.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_cloud_feedback_selected), null, null, null);
            mTvCondition2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_cloud_feedback_unselect), null, null, null);
            mTvCondition3.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_cloud_feedback_unselect), null, null, null);
            mTvCondition4.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_cloud_feedback_unselect), null, null, null);
            mTvCondition5.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_cloud_feedback_unselect), null, null, null);
        });
        mTvCondition2.setOnClickListener(v -> {
            cate_id = 52;
            mTvCondition1.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_cloud_feedback_unselect), null, null, null);
            mTvCondition2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_cloud_feedback_selected), null, null, null);
            mTvCondition3.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_cloud_feedback_unselect), null, null, null);
            mTvCondition4.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_cloud_feedback_unselect), null, null, null);
            mTvCondition5.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_cloud_feedback_unselect), null, null, null);
        });
        mTvCondition3.setOnClickListener(v -> {
            cate_id = 53;
            mTvCondition1.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_cloud_feedback_unselect), null, null, null);
            mTvCondition2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_cloud_feedback_unselect), null, null, null);
            mTvCondition3.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_cloud_feedback_selected), null, null, null);
            mTvCondition4.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_cloud_feedback_unselect), null, null, null);
            mTvCondition5.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_cloud_feedback_unselect), null, null, null);
        });
        mTvCondition4.setOnClickListener(v -> {
            cate_id = 54;
            mTvCondition1.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_cloud_feedback_unselect), null, null, null);
            mTvCondition2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_cloud_feedback_unselect), null, null, null);
            mTvCondition3.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_cloud_feedback_unselect), null, null, null);
            mTvCondition4.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_cloud_feedback_selected), null, null, null);
            mTvCondition5.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_cloud_feedback_unselect), null, null, null);
        });
        mTvCondition5.setOnClickListener(v -> {
            cate_id = 55;
            mTvCondition1.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_cloud_feedback_unselect), null, null, null);
            mTvCondition2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_cloud_feedback_unselect), null, null, null);
            mTvCondition3.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_cloud_feedback_unselect), null, null, null);
            mTvCondition4.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_cloud_feedback_unselect), null, null, null);
            mTvCondition5.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_cloud_feedback_selected), null, null, null);
        });

        mTvConfirm.setOnClickListener(v -> {
            sentCommentAction();
        });
    }

    private ThumbnailAdapter mThumbnailAdapter;
    private int maxPicCount = 3;
    private int cate_id = 51;
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

    private void refreshThumbnails() {
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

    /**
     * 发表点评
     */
    private void sentCommentAction() {
        String content = mEtContent.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            Toaster.show( "请输入不少于10个字的描述~");
            return;
        }
        if (content.length() < 10) {
            Toaster.show( "请输入不少于10个字的描述~");
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
                    Toaster.show( "第" + (i + 1) + "张图片大小超过了3MB，请选择小于3MB的图片");
                    return;
                }
                localPathList.add(file);
            }
            compressAction(content, localPathList);
        }
    }

    private void compressAction(String content, List<File> localPathList) {
        if (localPathList == null) {
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("content", content);
        map.put("cate_id", String.valueOf(cate_id));
        if (localPathList.isEmpty()) {
            kefuCloudFeedback(localPathList, map);
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
                            kefuCloudFeedback(localPathList, map);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Logs.e("compress error");
                            e.printStackTrace();
                        }
                    });
        }

    }

    private void kefuCloudFeedback(List<File> localPathList, Map<String, String> params) {
        if (mViewModel != null) {
            mViewModel.kefuCloudFeedback(localPathList, params, new OnBaseCallback() {

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
                            Toaster.show("提交成功！");
                            setFragmentResult(Activity.RESULT_OK, null);
                            pop();
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }
}
