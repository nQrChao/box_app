package com.zqhy.app.core.vm.chat;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;


import com.zqhy.app.core.data.repository.chat.ChatRepository;
import com.zqhy.app.core.data.repository.connection.ConnectionRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.core.vm.BaseViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.network.rx.RxObserver;
import com.zqhy.app.utils.sp.SPUtils;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observable;

/**
 * @author pc
 * @date 2019/12/23-11:50
 * @description
 */
public class ChatViewModel extends BaseViewModel<ChatRepository> {

    public ChatViewModel(@NonNull Application application) {
        super(application);
    }

    public void getChatRecommend(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getChatRecommend(onNetWorkListener);
        }
    }


    public void addChat(String gameid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.addChat(gameid, onNetWorkListener);
        }
    }

    public void chatActivityRecommend(String tid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.chatActivityRecommend(tid, onNetWorkListener);
        }
    }

    public void chatTeamNotice(String tid, String page, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.chatTeamNotice(tid,page, onNetWorkListener);
        }
    }

    public void chatGetGameId(String tid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.chatGetGameId(tid, onNetWorkListener);
        }
    }

    public void chatRuleTxt(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.chatRuleTxt(onNetWorkListener);
        }
    }

    public void getNetWorkData(String tid, OnNetWorkListener onNetWorkListener){
        if (mRepository != null) {
            mRepository.getNetWorkData(tid, onNetWorkListener);
        }
    }
}
