package com.zqhy.app.core.vm.transaction;

import android.app.Application;

import androidx.annotation.NonNull;

import com.zqhy.app.core.data.repository.transaction.TransactionRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.vm.BaseViewModel;

import java.io.File;
import java.util.Map;

/**
 * @author Administrator
 */
public class TransactionViewModel extends BaseViewModel<TransactionRepository> {

    public TransactionViewModel(@NonNull Application application) {
        super(application);
    }

    public void getTradeGoodList(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTradeGoodList(params, onNetWorkListener);
        }
    }
    public void getTradeGoodList1(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTradeGoodList1(params, onNetWorkListener);
        }
    }

    /**
     * 我要卖号
     * @param params
     * @param onNetWorkListener
     */
    public void getTradeMySellList(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTradeMySellList(params, onNetWorkListener);
        }
    }

    public void doGoodsBargain(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.doGoodsBargain(params, onNetWorkListener);
        }
    }

    public void checkTransaction(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.checkTransaction(onNetWorkListener);
        }
    }

    public void searchGame(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.searchGame(params, onNetWorkListener);
        }
    }

    /**
     * @param goodid
     * @param type              选填, 获取类型; type=modify时表示玩家修改自己发布商品时获取商品原信息(需要登陆); 其他或不填时表示普通浏览
     * @param onNetWorkListener
     */
    public void getTradeGoodDetail(String goodid, String type, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTradeGoodDetail(goodid, type, onNetWorkListener);
        }
    }

    /**
     * 我的待支付订单
     * @param onNetWorkListener
     */
    public void getTradeGoodsWaitPay( OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTradeGoodsWaitPay( onNetWorkListener);
        }
    }

    /**
     * 交易搜索页面
     * @param onNetWorkListener
     */
    public void getTradeSearchPage( OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTradeSearchPage( onNetWorkListener);
        }
    }
    /**
     * 收藏 或取消收藏
     * @param goodid
     * @param onNetWorkListener
     */
    public void setCollectionGood(String goodid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.setCollectionGood(goodid, onNetWorkListener);
        }
    }

    /**
     * 0元淘号-兑换
     * @param
     * @param onNetWorkListener
     */
    public void doZeroBuyGoodsBuy(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.doZeroBuyGoodsBuy(params, onNetWorkListener);
        }
    }

    public void offLineTradeGood(String gid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.offLineTradeGood(gid, onNetWorkListener);
        }
    }

    public void cancelTradeRecord(String gid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.cancelTradeGoods(gid, onNetWorkListener);
        }
    }

    public void deleteTradeRecord(String gid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.deleteTradeRecord(gid, onNetWorkListener);
        }
    }

    public void buyTradeGood(String gid, int pay_type, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.buyTradeGood(gid, pay_type, onNetWorkListener);
        }
    }

    public void cancelTradePayOrder(String orderid) {
        if (mRepository != null) {
            mRepository.cancelTradePayOrder(orderid, null);
        }
    }
    public void cancelTradePayOrder(String orderid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.cancelTradePayOrder(orderid, onNetWorkListener);
        }
    }

    public void getTradeCode(String targetGameid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTradeCode(targetGameid, onNetWorkListener);
        }
    }

    public void transactionSell(Map<String, String> params, Map<String, File> fileParams, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.transactionSell(params, fileParams, onNetWorkListener);
        }
    }

    public void transactionEdit(Map<String, String> params, Map<String, File> fileParams, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.transactionEdit(params, fileParams, onNetWorkListener);
        }
    }



    public void getTransactionGame(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTransactionGame(onNetWorkListener);
        }
    }

    public void getDynamicGameMessageData(long logTime,OnNetWorkListener onNetWorkListener) {
        if(mRepository != null){
            mRepository.getDynamicGameMessageData(logTime,onNetWorkListener);
        }
    }

    public void getTransactionGameXh(String gameid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTransactionGameXh(gameid, onNetWorkListener);
        }
    }

    public void getTradeRecordList(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTradeRecordList(params, onNetWorkListener);
        }
    }
    public void getTradeCollectionRecordList(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTradeCollectionRecordList(params, onNetWorkListener);
        }
    }

    public void getTradeSellConfig(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTradeSellConfig(params, onNetWorkListener);
        }
    }

    /**
     * 修改商品出售价格
     *
     * @param gid
     * @param goods_price       新价格
     * @param code              验证码
     * @param onNetWorkListener
     */
    public void getModifyGoodPrice(String gid, int goods_price, String code, int can_bargain, int auto_price, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getModifyGoodPrice(gid, goods_price, code, can_bargain, auto_price, onNetWorkListener);
        }
    }

    public void realNameCheck(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.realNameCheck("gold",onNetWorkListener);
        }
    }

    public void getZeroBuyGoodList(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getZeroBuyGoodList(params, onNetWorkListener);
        }
    }
    public void getMyZeroBuyGoodList(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getMyZeroBuyGoodList(params, onNetWorkListener);
        }
    }

    public void tradeXhInfo(String xh_username, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.tradeXhInfo(xh_username, onNetWorkListener);
        }
    }
}
