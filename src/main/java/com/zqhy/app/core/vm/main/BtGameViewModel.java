package com.zqhy.app.core.vm.main;

import android.app.Application;
import androidx.annotation.NonNull;

import com.mvvm.base.AbsViewModel;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.data.repository.main.BtGameRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.model.UserInfoModel;

import java.util.Map;

/**
 * @author Administrator
 * @date 2018/11/7
 */

public class BtGameViewModel extends AbsViewModel<BtGameRepository> {

    public BtGameViewModel(@NonNull Application application) {
        super(application);
    }


    public void getIndexGameData(int game_type, int page, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getIndexGameData(game_type, page, onNetWorkListener);
        }
    }

    /**
     * 2020.08.27 新增BT首頁新接口
     *
     * @param onNetWorkListener
     */
    public void getIndexBTGameData(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getIndexBTGameData(onNetWorkListener);
        }
    }

    /**
     * 2020.11.23 新增首页更多游戏接口
     *
     * @param game_type
     * @param page
     * @param pageCount
     * @param onNetWorkListener
     */
    public void getIndexMoreGameData(int game_type, int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getIndexPageListData(game_type, page, pageCount, onNetWorkListener);
        }
    }


    public void getGameNavigationData(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getGameNavigationData(onNetWorkListener);
        }
    }

    public void getGameSearchData(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getGameSearchData(onNetWorkListener);
        }
    }


    /**
     * 新游列表接口
     *
     * @param onNetWorkListener
     */
    public void getAppointmentGameList(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getAppointmentGameList(onNetWorkListener);
        }
    }

    /**
     * 2020.03.30 新游预约
     *
     * @param gameid
     * @param onNetWorkListener
     */
    public void gameAppointment(int gameid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.gameAppointment(gameid, onNetWorkListener);
        }
    }

    /**
     * 游戏类型换一批
     *
     * @param page
     * @param genre_id
     * @param game_type
     * @param onNetWorkListener
     */
    public void getGenreGameByPage(int page, int genre_id, int game_type, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getGenreGameByPage(page, genre_id, game_type, onNetWorkListener);
        }
    }

    /**
     * 领取新人礼包
     *
     * @param onNetWorkListener
     */
    public void getRookieCoupons(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getRookieCoupons(onNetWorkListener);
        }
    }


    /**
     * 新游：首页
     *
     * @param onNetWorkListener
     */
    public void getXyHomePageData(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getXyHomePageData(onNetWorkListener);
        }
    }

    /**
     * 新游：首页
     *
     * @param onNetWorkListener
     */
    public void getXyTabHomePageData(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getXyTabHomePageData(onNetWorkListener);
        }
    }

    /**
     * 折扣：首页
     *
     * @param onNetWorkListener
     */
    public void getZkHomePageData(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getZkHomePageData(onNetWorkListener);
        }
    }

    /**
     * 新游：福利服
     *
     * @param onNetWorkListener
     */
    public void getFlfHomePageData(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getFlfHomePageData(onNetWorkListener);
        }
    }


    /**
     * uid，token登陆账号
     */
    public void getUserInfo() {
        if (mRepository != null) {
            if (UserInfoModel.getInstance().isLogined()) {
                UserInfoVo.DataBean dataBean = UserInfoModel.getInstance().getUserInfo();
                int uid = dataBean.getUid();
                String username = dataBean.getUsername();
                String token = dataBean.getToken();
                mRepository.getUserInfo(uid, token, username);
            }
        }
    }

    /**
     * 刷新用户数据
     */
    public void refreshUserData(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            if (UserInfoModel.getInstance().isLogined()) {
                UserInfoVo.DataBean dataBean = UserInfoModel.getInstance().getUserInfo();
                int uid = dataBean.getUid();
                String username = dataBean.getUsername();
                String token = dataBean.getToken();
                mRepository.getUserInfoWithoutNotification(uid, token, username,onNetWorkListener);
            }
        }
    }
    /**
     * 新游：精选
     *
     * @param onNetWorkListener
     */
    public void getJxHomePageData(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getJxHomePageData(onNetWorkListener);
        }
    }

    /**
     * 福利币
     */
    public void getFiliBiData(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getJxHomePageData(onNetWorkListener);
        }
    }

    /**
     * 新游：合集
     *
     * @param onNetWorkListener
     */
    public void getHjHomePageData(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getHjHomePageData(params, onNetWorkListener);
        }
    }

    /**
     * 新游：推荐类
     *
     * @param onNetWorkListener
     */
    public void getTjHomePageData(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTjHomePageData(onNetWorkListener);
        }
    }
}
