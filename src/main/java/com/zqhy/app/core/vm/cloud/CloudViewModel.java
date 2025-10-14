package com.zqhy.app.core.vm.cloud;

import android.app.Application;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvvm.http.rx.RxSchedulers;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.model.cloud.CloudCourseVo;
import com.zqhy.app.core.data.repository.cloud.CloudRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.vm.BaseViewModel;
import com.zqhy.app.network.rx.RxSubscriber;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 * @date 2018/11/19
 */

public class CloudViewModel extends BaseViewModel<CloudRepository> {
    public CloudViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 云挂机FAQ
     */
    public void getCourseData(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getCourseData(onNetWorkListener);
        }
    }

    /**
     * 云挂机问题反馈
     */
    public void kefuCloudFeedback(List<File> localPathList,  Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            params.put("api", "kefu_cloud_feedback");

            Map<String, File> fileParams = new TreeMap<>();
            if (localPathList != null) {
                for (int i = 0; i < localPathList.size(); i++) {
                    fileParams.put("upload_pic" + (i + 1), localPathList.get(i));
                }
            }
            mRepository.kefuCloudFeedback(params, fileParams, onNetWorkListener);
        }
    }

    /**
     * 云挂机-设备信息列表
     */
    public void getDeviceList(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getDeviceList(onNetWorkListener);
        }
    }

    /**
     * 云挂机-领取新人福利
     */
    public void freeTrial(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.freeTrial(onNetWorkListener);
        }
    }
    public void freeVeTrial(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.freeVeTrial(onNetWorkListener);
        }
    }

    /**
     * 云挂机-设备信息修改
     */
    public void updateDevice(Map<String, String> params,OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.updateDevice(params, onNetWorkListener);
        }
    }

    /**
     * 云挂机-购买须知
     */
    public void getNotice(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getNotice(onNetWorkListener);
        }
    }

    /**
     * 云挂机-支付信息查询
     */
    public void getPayInfo(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getPayInfo(onNetWorkListener);
        }
    }

    /**
     * 云挂机-发起支付
     */
    public void launchPay(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.launchPay(params, onNetWorkListener);
        }
    }

    /**
     * 云挂机-获取截图/停止挂机
     */
    public void getScreen(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getScreen(params, onNetWorkListener);
        }
    }
    /**
     * 火云挂机-获取截图
     */
    public void getVeScreen(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getVeScreen(params, onNetWorkListener);
        }
    }

    /**
     * 云挂机-游戏列表
     */
    public void getGameList(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getGameList(params, onNetWorkListener);
        }
    }


    /**
     * 火山云机-鉴权 云挂机
     * @param onNetWorkListener
     */
    public void getToken(String gid , OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getToken(gid, onNetWorkListener);
        }
    }
    /**
     * 火山云机-鉴权 云试玩
     * @param onNetWorkListener
     */
    public void getTokenDemo(String gameid,OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTokenDemo(gameid,onNetWorkListener);
        }
    }
    /**
     * 火山云机-鉴权 云游
     * @param onNetWorkListener
     */
    public void getTokenPlay(String gameid,OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTokenPlay(gameid,onNetWorkListener);
        }
    }

    /**
     * 火云挂机-设备信息列表
     */
    public void getVeDeviceList(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getVeDeviceList(onNetWorkListener);
        }
    }
    /**
     * 火云挂机-游戏列表
     */
    public void getVeGameList(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getVeGameList(params, onNetWorkListener);
        }
    }
    /**
     * 火云挂机-开始挂机前发送设备号
     */
    public void getVolcengineStart(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getVolcengineStart(params, onNetWorkListener);
        }
    }
    /**
     * 火云试玩-开始前发送设备号
     */
    public void getVolcengineDemoStart(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getVolcengineDemoStart(params, onNetWorkListener);
        }
    }
    /**
     * 火云游-开始前发送设备号
     */
    public void getVolcenginePlayStart(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getVolcenginePlayStart(params, onNetWorkListener);
        }
    }
    /**
     * 火云挂机-停止挂机
     */
    public void getVolcengineStop(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getVolcengineStop(params, onNetWorkListener);
        }
    }

}
