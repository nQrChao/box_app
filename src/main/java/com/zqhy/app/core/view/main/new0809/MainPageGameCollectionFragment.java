package com.zqhy.app.core.view.main.new0809;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.game.new0809.AppMenuVo;
import com.zqhy.app.core.data.model.game.new0809.GameCollectionTextVo;
import com.zqhy.app.core.data.model.game.new0809.MainHeJiDataVo;
import com.zqhy.app.core.data.model.game.new0809.item.MainPageItemVo;
import com.zqhy.app.core.data.model.mainpage.figurepush.GameFigurePushVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.nodata.NoMoreDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.main.AbsMainGameListFragment;
import com.zqhy.app.core.view.main.holder.GameFigurePushItemHolder;
import com.zqhy.app.core.view.main.new0809.holder.GameCollectionTextItemHolder;
import com.zqhy.app.core.view.main.new0809.holder.NewNoDataItemHolder;
import com.zqhy.app.core.view.main.new0809.holder.TagGameNormalItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.main.BtGameViewModel;
import com.zqhy.app.newproject.R;

import java.util.Map;

/**
 * @author Administrator
 * @date 2021/8/12 0012-10:37
 * @description
 */
public class MainPageGameCollectionFragment extends AbsMainGameListFragment<BtGameViewModel> {

    public static MainPageGameCollectionFragment newInstance(AppMenuVo.ParamsBean params) {
        MainPageGameCollectionFragment fragment = new MainPageGameCollectionFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("params", params);
        fragment.setArguments(bundle);

        return fragment;
    }

    public static MainPageGameCollectionFragment newInstance(boolean hasTitle, AppMenuVo.ParamsBean params) {
        MainPageGameCollectionFragment fragment = new MainPageGameCollectionFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("params", params);
        bundle.putBoolean("hasTitle", hasTitle);
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    protected String getUmengPageName() {
        return "首页-合辑";
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(GameFigurePushVo.class, new GameFigurePushItemHolder(_mActivity))
                .bind(GameCollectionTextVo.class, new GameCollectionTextItemHolder(_mActivity))
                .bind(MainPageItemVo.class, new TagGameNormalItemHolder(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(NoMoreDataVo.class, new NewNoDataItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this)
                .setTag(R.id.tag_sub_fragment, this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    public AppMenuVo.ParamsBean params;
    public boolean              hasTitle;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            params = (AppMenuVo.ParamsBean) getArguments().getSerializable("params");
            hasTitle = getArguments().getBoolean("hasTitle", false);
        }
        super.initView(state);
        setListViewBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_f2f2f2));
        DividerItemDecoration decoration = new DividerItemDecoration(_mActivity, DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.main_pager_item_decoration));
        mRecyclerView.addItemDecoration(decoration);
        setPullRefreshEnabled(true);
        setLoadingMoreEnabled(false);
        if (hasTitle) {
            initActionBackBarAndTitle("");
        }
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        initData();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        initData();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        initData();
    }


    @Override
    protected boolean isAddStatusBarLayout() {
        return hasTitle;
    }

    private void initData() {
        if (mViewModel != null) {
            Map<String, String> params = this.params == null ? null : this.params.getParams();
            mViewModel.getHjHomePageData(params, new OnBaseCallback<MainHeJiDataVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(MainHeJiDataVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            clearData();
                            if (data.data != null) {
                                if (!TextUtils.isEmpty(data.data.pic)) {
                                    GameFigurePushVo gameFigurePushVo = new GameFigurePushVo();
                                    gameFigurePushVo.setPic(data.data.pic);
                                    addData(gameFigurePushVo);
                                }
                                if (!TextUtils.isEmpty(data.data.title) || !TextUtils.isEmpty(data.data.description)) {
                                    GameCollectionTextVo textVo = new GameCollectionTextVo();
                                    if (hasTitle) {
                                        setTitleText(data.data.title);
                                    }
                                    textVo.title = data.data.title;
                                    textVo.sub_title = data.data.sub_title;
                                    textVo.description = data.data.description;
                                    addData(textVo);
                                }

                                if (checkCollectionNotEmpty(data.data.list)) {
                                    MainPageItemVo vo = new MainPageItemVo();
                                    vo.data = data.data.list;
                                    addData(vo);
                                } else {
                                    addData(new EmptyDataVo(R.mipmap.img_empty_data_2));
                                }
                                addData(new NoMoreDataVo());
                            } else {
                                addData(new EmptyDataVo(R.mipmap.img_empty_data_2));
                            }
                            notifyData();
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }

}
