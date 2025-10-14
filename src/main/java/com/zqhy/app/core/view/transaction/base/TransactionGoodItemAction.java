package com.zqhy.app.core.view.transaction.base;


/**
 * @author Administrator
 */
public interface TransactionGoodItemAction {

    /**
     * 修改商品属性
     *
     * @param gid
     * @param requestCode
     */
    void modifyGoodItem(String gid, int requestCode);

    /**
     * 修改价格
     *
     * @param gameid
     * @param gid
     * @param goods_price
     * @param onResultSuccessListener
     */
    void changeGoodPrice(String gameid, String gid, String goods_price, int can_bargain, int auto_price, OnResultSuccessListener onResultSuccessListener);

    /**
     * 下架商品
     *
     * @param gid
     * @param onResultSuccessListener
     */
    void stopSelling(String gid, OnResultSuccessListener onResultSuccessListener);

    /**
     * 下架商品(带提示)
     *
     * @param gid
     * @param onResultSuccessListener
     */
    void stopSellingWithTips(String gid, OnResultSuccessListener onResultSuccessListener);

    /**
     * 删除记录(交易完成)
     *
     * @param gid
     * @param onResultSuccessListener
     */
    void deleteTradeGood(String gid, OnResultSuccessListener onResultSuccessListener);

    /**
     * 删除记录(出售中)
     *
     * @param gid
     * @param onResultSuccessListener
     */
    void cancelTradeGood(String gid, OnResultSuccessListener onResultSuccessListener);

    /**
     * 如何使用
     */
    void howToUseGoods();
}
