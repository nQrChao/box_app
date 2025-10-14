package com.zqhy.app.core.data.repository.transaction;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvvm.http.HttpHelper;
import com.mvvm.http.rx.RxSchedulers;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.BaseRepository;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.GameListVo;
import com.zqhy.app.core.data.model.message.MessageListVo;
import com.zqhy.app.core.data.model.transaction.CollectionBeanVo;
import com.zqhy.app.core.data.model.transaction.GameXhInfoVo;
import com.zqhy.app.core.data.model.transaction.PayBeanVo;
import com.zqhy.app.core.data.model.transaction.TradeCollectInfoListVo;
import com.zqhy.app.core.data.model.transaction.TradeGoodDetailInfoVo;
import com.zqhy.app.core.data.model.transaction.TradeGoodInfoListVo;
import com.zqhy.app.core.data.model.transaction.TradeGoodInfoListVo1;
import com.zqhy.app.core.data.model.transaction.TradeGoodsWaitPayInfoVo;
import com.zqhy.app.core.data.model.transaction.TradeMySellListVo;
import com.zqhy.app.core.data.model.transaction.TradeSearchPageInfoVo;
import com.zqhy.app.core.data.model.transaction.TradeSellConfigVo;
import com.zqhy.app.core.data.model.transaction.TradeXhInfoVo;
import com.zqhy.app.core.data.model.transaction.TradeZeroBuyGoodInfoListVo;
import com.zqhy.app.core.data.model.transaction.TradeZeroBuyGoodInfoListVo1;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.network.IApiService;
import com.zqhy.app.network.rx.RxSubscriber;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 */
public class TransactionRepository extends BaseRepository {

    public void getTradeGoodList(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (params == null) {
            params = new TreeMap<>();
        }
//        params.put("api", "trade_goods_list");
        String data = createPostData(params);
        IApiService iApiService1 = HttpHelper.getInstance().transactionCreate(URL.TRANSACTION_API_URL, IApiService.class);
        addDisposable(iApiService1.tradeGoodsList(data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);

                        Type type = new TypeToken<TradeGoodInfoListVo>() {
                        }.getType();

                        TradeGoodInfoListVo goodInfoListVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(goodInfoListVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }
    public void getTradeGoodList1(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (params == null) {
            params = new TreeMap<>();
        }
//        params.put("api", "trade_goods_list");
        String data = createPostData(params);
        IApiService iApiService1 = HttpHelper.getInstance().transactionCreate(URL.TRANSACTION_API_URL, IApiService.class);

        addDisposable(iApiService1.tradeGoodsList(data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);

                        Type type = new TypeToken<TradeGoodInfoListVo1>() {
                        }.getType();

                        TradeGoodInfoListVo1 goodInfoListVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(goodInfoListVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void getTradeSellConfig(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (params == null) {
            params = new TreeMap<>();
        }
//        params.put("api", "trade_config");
        String data = createPostData(params);
        IApiService iApiService1 = HttpHelper.getInstance().transactionCreate(URL.TRANSACTION_API_URL, IApiService.class);
        addDisposable(iApiService1.tradeConfig(data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);

                        Type type = new TypeToken<TradeSellConfigVo>() {
                        }.getType();

                        TradeSellConfigVo goodInfoListVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(goodInfoListVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void getTradeMySellList(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (params == null) {
            params = new TreeMap<>();
        }
        params.put("api", "trade_user_can_trade_xh_list");
        String data = createPostData(params);
        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);

                        Type type = new TypeToken<TradeMySellListVo>() {
                        }.getType();

                        TradeMySellListVo goodInfoListVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(goodInfoListVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void getZeroBuyGoodList(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (params == null) {
            params = new TreeMap<>();
        }
        params.put("api", "zero_buy_goods_list");

        String data = createPostData(params);
        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);

                        Type type = new TypeToken<TradeZeroBuyGoodInfoListVo>() {
                        }.getType();

                        TradeZeroBuyGoodInfoListVo goodInfoListVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(goodInfoListVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void getMyZeroBuyGoodList(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (params == null) {
            params = new TreeMap<>();
        }
        params.put("api", "zero_buy_user_goods_list");

        String data = createPostData(params);
        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);

                        Type type = new TypeToken<TradeZeroBuyGoodInfoListVo1>() {
                        }.getType();

                        TradeZeroBuyGoodInfoListVo1 goodInfoListVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(goodInfoListVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void checkTransaction(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
//        params.put("api", "trade_add_check");

        String data = createPostData(params);
        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<BaseVo>() {
                        }.getType();

                        BaseVo baseVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void searchGame(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (params == null) {
            params = new TreeMap<>();
        }
        params.put("api", "gamelist");

        String data = createPostData(params);
        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<GameListVo>() {
                        }.getType();

                        GameListVo baseVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void getTradeGoodDetail(String goodid, String type, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
//        params.put("api", "trade_goods_info");
        params.put("gid", goodid);
        if (!TextUtils.isEmpty(type)) {
            params.put("type", type);
        }
        String data = createPostData(params);
        IApiService iApiService1 = HttpHelper.getInstance().transactionCreate(URL.TRANSACTION_API_URL, IApiService.class);
        addDisposable(iApiService1.tradeGoodsInfo(data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<TradeGoodDetailInfoVo>() {
                        }.getType();

                        TradeGoodDetailInfoVo baseVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));

    }

    public void getTradeSearchPage( OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
//        params.put("api", "trade_search_page");

        String data = createPostData(params);
        IApiService iApiService1 = HttpHelper.getInstance().transactionCreate(URL.TRANSACTION_API_URL, IApiService.class);
        addDisposable(iApiService1.tradeSearchPage(data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<TradeSearchPageInfoVo>() {
                        }.getType();

                        TradeSearchPageInfoVo baseVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));

    }

    public void getTradeGoodsWaitPay( OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
//        params.put("api", "trade_goods_wait_pay");
        params.put("pic", "multiple");
        String data = createPostData(params);
        IApiService iApiService1 = HttpHelper.getInstance().transactionCreate(URL.TRANSACTION_API_URL, IApiService.class);
        addDisposable(iApiService1.tradeUnpaidList(data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<TradeGoodsWaitPayInfoVo>() {
                        }.getType();

                        TradeGoodsWaitPayInfoVo baseVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));

    }

    public void doGoodsBargain(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
//        params.put("api", "trade_goods_bargain");
        String data = createPostData(params);
        IApiService iApiService1 = HttpHelper.getInstance().transactionCreate(URL.TRANSACTION_API_URL, IApiService.class);
        addDisposable(iApiService1.tradeBargain(data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<CollectionBeanVo>() {
                        }.getType();

                        CollectionBeanVo baseVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));

    }

    public void doZeroBuyGoodsBuy(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (params==null) {
            return;
        }
        params.put("api", "zero_buy_goods_buy");
        String data = createPostData(params);
        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<CollectionBeanVo>() {
                        }.getType();

                        CollectionBeanVo baseVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));

    }

    public void setCollectionGood(String goodid, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
//        params.put("api", "trade_goods_collection_switch");
        params.put("gid", goodid);
        String data = createPostData(params);
        IApiService iApiService1 = HttpHelper.getInstance().transactionCreate(URL.TRANSACTION_API_URL, IApiService.class);
        addDisposable(iApiService1.tradeCollect(data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<CollectionBeanVo>() {
                        }.getType();

                        CollectionBeanVo baseVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));

    }

    public void offLineTradeGood(String gid, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
//        params.put("api", "trade_goods_off");
        params.put("gid", gid);
        String data = createPostData(params);
        IApiService iApiService1 = HttpHelper.getInstance().transactionCreate(URL.TRANSACTION_API_URL, IApiService.class);
        addDisposable(iApiService1.tradeGoodsOff(data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<BaseVo>() {
                        }.getType();

                        BaseVo baseVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void deleteTradeRecord(String gid, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
//        params.put("api", "trade_goods_del");
        params.put("gid", gid);
        String data = createPostData(params);
        IApiService iApiService1 = HttpHelper.getInstance().transactionCreate(URL.TRANSACTION_API_URL, IApiService.class);
        addDisposable(iApiService1.tradeGoodsGel(data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<BaseVo>() {
                        }.getType();

                        BaseVo baseVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));

    }

    public void cancelTradeGoods(String gid, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "trade_goods_cancel");
        params.put("gid", gid);

        String data = createPostData(params);
        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<BaseVo>() {
                        }.getType();

                        BaseVo baseVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));

    }

    public void buyTradeGood(String gid, int pay_type, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
//        params.put("api", "pay_type");
        params.put("gid", gid);
        params.put("pay_type", String.valueOf(pay_type));
        String data = createPostData(params);
        IApiService iApiService1 = HttpHelper.getInstance().transactionCreate(URL.TRANSACTION_API_URL, IApiService.class);
        addDisposable(iApiService1.tradePurchase(data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<PayBeanVo>() {
                        }.getType();

                        PayBeanVo baseVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));

    }

    public void cancelTradePayOrder(String orderid, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
//        params.put("api", "trade_pay_cancel");
        params.put("order_no", orderid);
        String data = createPostData(params);
        IApiService iApiService1 = HttpHelper.getInstance().transactionCreate(URL.TRANSACTION_API_URL, IApiService.class);
        addDisposable(iApiService1.tradeCancelPurchase(data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<PayBeanVo>() {
                        }.getType();

                        PayBeanVo baseVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));

    }

    public void getTradeCode(String gameid, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "trade_get_code");
        params.put("gameid", gameid);
        String data = createPostData(params);

        iApiService = HttpHelper.getInstance().create(URL.getHttpUrl(), IApiService.class);
        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<BaseVo>() {
                        }.getType();

                        BaseVo baseVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));

    }

    public void transactionSell(Map<String, String> params, Map<String, File> fileParams, OnNetWorkListener onNetWorkListener) {
        String data = createPostData(params);
        IApiService iApiService1 = HttpHelper.getInstance().transactionCreate(URL.TRANSACTION_API_URL, IApiService.class);
        addDisposable(iApiService1.tradeGoodsAdd(createPostPicData(data), createPostPicPartData(fileParams))
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<BaseVo>() {
                        }.getType();

                        BaseVo baseVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }
    public void transactionEdit(Map<String, String> params, Map<String, File> fileParams, OnNetWorkListener onNetWorkListener) {
        String data = createPostData(params);
        IApiService iApiService1 = HttpHelper.getInstance().transactionCreate(URL.TRANSACTION_API_URL, IApiService.class);
        addDisposable(iApiService1.tradeGoodsEdit(createPostPicData(data), createPostPicPartData(fileParams))
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<BaseVo>() {
                        }.getType();

                        BaseVo baseVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void getDynamicGameMessageData(long logTime,OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "msg_gamechange");
        if (logTime > 0) {
            params.put("logtime", String.valueOf(logTime));
        }
        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<MessageListVo>() {
                        }.getType();

                        MessageListVo messageListVo = gson.fromJson(result, type);
                        if(onNetWorkListener != null){
                            onNetWorkListener.onSuccess(messageListVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void getTransactionGame(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "trade_user_game");

        String data = createPostData(params);
        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<GameListVo>() {
                        }.getType();

                        GameListVo baseVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void getTransactionGameXh(String gameid, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "trade_user_xhlist");
        params.put("gameid", gameid);

        String data = createPostData(params);
        iApiService = HttpHelper.getInstance().create(URL.getHttpUrl(), IApiService.class);
        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<GameXhInfoVo>() {
                        }.getType();

                        GameXhInfoVo baseVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));

    }

    public void getTradeRecordList(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (params == null) {
            params = new TreeMap<>();
        }
//        params.put("api", "trade_goods_list");
        String data = createPostData(params);
        IApiService iApiService1 = HttpHelper.getInstance().transactionCreate(URL.TRANSACTION_API_URL, IApiService.class);
        addDisposable(iApiService1.tradeGoodsList(data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<TradeGoodInfoListVo>() {
                        }.getType();

                        TradeGoodInfoListVo baseVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }
    public void getTradeCollectionRecordList(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (params == null) {
            params = new TreeMap<>();
        }
//        params.put("api", "trade_collection_goods_list");
        String data = createPostData(params);
        IApiService iApiService1 = HttpHelper.getInstance().transactionCreate(URL.TRANSACTION_API_URL, IApiService.class);
        addDisposable(iApiService1.tradeCollectList(data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<TradeCollectInfoListVo>() {
                        }.getType();

                        TradeCollectInfoListVo baseVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
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
        Map<String, String> params = new TreeMap<>();
//        params.put("api", "trade_modify_goods_price");
        params.put("gid", gid);
        params.put("goods_price", String.valueOf(goods_price));
        params.put("code", code);
        params.put("can_bargain", String.valueOf(can_bargain));
        params.put("auto_price", String.valueOf(auto_price));
        IApiService iApiService1 = HttpHelper.getInstance().transactionCreate(URL.TRANSACTION_API_URL, IApiService.class);
        String data = createPostData(params);
        addDisposable(iApiService1.tradePriceAdjustment(data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<BaseVo>() {
                        }.getType();

                        BaseVo baseVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    /**
     * 获取小号信息
     * @param onNetWorkListener
     */
    public void tradeXhInfo(String xh_username, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "trade_xh_info");
        params.put("xh_username", xh_username);
        String data = createPostData(params);
        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<TradeXhInfoVo>() {
                        }.getType();

                        TradeXhInfoVo baseVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }
}
