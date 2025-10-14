package com.zqhy.app.core.view.main;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.fragment.app.FragmentTransaction;

import com.box.other.immersionbar.ImmersionBar;
import com.mvvm.base.BaseMvvmFragment;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.view.game.GameDownloadManagerFragment;
import com.zqhy.app.core.view.game.GameSearchFragment;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.newproject.R;

/**
 * @author pc
 */
public class NewMainGameFragment extends BaseFragment {
    public static NewMainGameFragment newInstance(int index) {
        NewMainGameFragment fragment = new NewMainGameFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("page_index", index);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static NewMainGameFragment newInstance(int game_type, int game_genre_id) {
        return newInstance(game_type, game_genre_id, false);
    }

    public static NewMainGameFragment newInstance(int game_type, int game_genre_id, boolean isShowBack) {
        NewMainGameFragment fragment = new NewMainGameFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("game_type", game_type);
        bundle.putInt("game_genre_id", game_genre_id);
        bundle.putBoolean("is_show_back", isShowBack);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "游戏库";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_ts_main_game_new;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_container;
    }


    private int     page_index;
    private int     game_genre_id;
    private int     game_type = 1;
    private int     tab_id;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            page_index = getArguments().getInt("page_index", 0);
            game_genre_id = getArguments().getInt("game_genre_id", 0);
            game_type = getArguments().getInt("game_type", 1);
            tab_id = getArguments().getInt("tab_id");
        }
        super.initView(state);
        bindViews();
        initFragment();
        showSuccess();
        /*SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME);
        boolean isShowGameSplash = spUtils.getBoolean("isShowGameSplash", false);
        if (!isShowGameSplash){
            showSplashDialog();
        }*/
    }

    private ImageView          mIvMainHomePageSearch;
    private ImageView          mIvMainHomePageDownload;
    private ImageView          mIvMainHomePageMesssage;
    private RelativeLayout mRltop;
    public LinearLayout mTitleBar_Layout;

    private void bindViews() {
        mRltop = findViewById(R.id.rl_top);
        mTitleBar_Layout = findViewById(R.id.titleBar_Layout);
        ImmersionBar.with(this).fullScreen(true).init();
        ImmersionBar.setTitleBar(_mActivity,mTitleBar_Layout);
        mIvMainHomePageSearch = findViewById(R.id.iv_main_home_page_search);
        mIvMainHomePageDownload = findViewById(R.id.iv_main_home_page_download);
        mIvMainHomePageMesssage = findViewById(R.id.iv_main_home_page_messsage);

        mIvMainHomePageDownload.setOnClickListener(view -> {
            //下载
            if (BuildConfig.IS_DOWNLOAD_GAME_FIRST || checkLogin()) {
                startFragment(new GameDownloadManagerFragment());
            }
        });
        mIvMainHomePageMesssage.setOnClickListener(view -> {
            //客服
            goKefuCenter();
        });
        mIvMainHomePageSearch.setOnClickListener(view -> {
            startFragment(new GameSearchFragment());
        });
    }


    private BaseMvvmFragment mContent;
    BaseMvvmFragment btFragment;
    private void initFragment(){
        btFragment = MainGameClassification2Fragment.newInstance(1, game_genre_id, false);
        changeTabFragment(btFragment);
    }

    private void changeTabFragment(BaseMvvmFragment tagFragment) {
        if (mContent == tagFragment) {
            return;
        }
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (!tagFragment.isAdded()) {
            if (mContent != null) {
                transaction.hide(mContent);
            }
            transaction.add(R.id.ll_perch, tagFragment).commitAllowingStateLoss();
        } else {
            if (mContent != null) {
                transaction.hide(mContent);
            }
            transaction.show(tagFragment).commitAllowingStateLoss();
        }
        mContent = tagFragment;

    }

    /**
     * select item
     *
     * @param index
     */
    public void selectPageItem(int index) {
        changeTabFragment(btFragment);
    }

    /**
     * 跳转 首页-游戏-分类-SubTabVo
     */
    public void selectPageByGenreId() {
        changeTabFragment(btFragment);
    }
}
