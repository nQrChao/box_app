package com.zqhy.app.core.view.community.qa.list;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.community.qa.QuestionInfoVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.nodata.BlankDataVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.view.BlankItemHolder;
import com.zqhy.app.core.view.community.qa.GameQaCollListFragment;
import com.zqhy.app.core.view.community.qa.GameQaDetailFragment;
import com.zqhy.app.core.view.community.qa.holder.QuestionItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.newproject.R;

import java.util.List;

import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author Administrator
 */
public class GameQaChildListFragment extends BaseListFragment {

    public static final int ACTION_GAME_QA_DETAIL = 0x456;


    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(QuestionInfoVo.class, new QuestionItemHolder(_mActivity))
                .bind(BlankDataVo.class, new BlankItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment,this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        setPullRefreshEnabled(false);
        setOnItemClickListener((v, position, data) -> {
            if (data != null && data instanceof QuestionInfoVo) {
                QuestionInfoVo questionInfoVo = (QuestionInfoVo) data;
                startForResult(GameQaDetailFragment.newInstance(questionInfoVo.getQid()), ACTION_GAME_QA_DETAIL);
            }
        });
        initData();
    }

    @Override
    public void start(ISupportFragment toFragment) {
        if (getParentFragment() != null) {
            ((BaseFragment) getParentFragment()).start(toFragment);
        } else {
            super.start(toFragment);
        }
    }

    @Override
    public void startForResult(ISupportFragment toFragment, int requestCode) {
        if (getParentFragment() != null) {
            ((BaseFragment) getParentFragment()).startForResult(toFragment, requestCode);
        } else {
            super.start(toFragment, requestCode);
        }
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        initData();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        page++;
        getQaListData();
    }

    private int page = 1, pageCount = 12;

    @Override
    public int getPageCount() {
        return pageCount;
    }

    public void initData() {
        if (getParentFragment() != null && getParentFragment() instanceof GameQaCollListFragment) {
            page = 1;
            ((GameQaCollListFragment) getParentFragment()).initData(pageCount);
        }
    }

    private void getQaListData() {
        if (getParentFragment() != null && getParentFragment() instanceof GameQaCollListFragment) {
            ((GameQaCollListFragment) getParentFragment()).getQaListData(page, pageCount);
        }
    }

    public void setListData(GameInfoVo gameInfoVo) {
        showSuccess();
        List<QuestionInfoVo> questionInfoVoList = gameInfoVo.getQuestion_list();
        if (questionInfoVoList != null) {
            if (page == 1) {
                clearData();
            }
            addAllData(questionInfoVoList);
            if (questionInfoVoList.size() < pageCount) {
                //has no more items
                addData(new BlankDataVo());
            }
        } else {
            if (page == 1) {
                clearData();
                addData(new EmptyDataVo(R.mipmap.img_empty_data_2)
                        .setLayout(EmptyDataVo.LAYOUT_WRAP_CONTENT)
                        .setPaddingTop((int) (24 * density)));
            } else {
                //has no more items
                addData(new BlankDataVo());
            }
            setListNoMore(true);
            notifyData();
        }
    }


}
