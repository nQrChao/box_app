package com.zqhy.mod.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chaoji.base.base.viewmodel.BaseViewModel
import com.chaoji.base.callback.databind.IntObservableField
import com.chaoji.base.callback.databind.StringObservableField
import com.chaoji.base.ext.modRequest
import com.chaoji.base.ext.modRequestWithMsg
import com.chaoji.base.ext.requestFlow
import com.chaoji.base.modnetwork.ModAppException
import com.chaoji.base.modnetwork.ModExceptionHandle
import com.chaoji.base.state.ModResultState
import com.chaoji.base.state.ModResultStateWithMsg
import com.chaoji.im.data.model.AppletsFuLiLingQu
import com.chaoji.im.data.model.AppletsInfo
import com.chaoji.im.data.model.AppletsXiaoGameFuLiInfo
import com.chaoji.im.data.model.ModGameHallList
import com.chaoji.im.data.model.ModTradeGoodDetailBean
import com.chaoji.im.network.ModApiResponse
import com.chaoji.im.network.NetworkApi
import com.chaoji.im.network.apiService
import com.chaoji.mod.game.ModManager
import com.chaoji.other.blankj.utilcode.util.Logs
import com.zqhy.app.db.table.message.MessageVo_Table.uid
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class NavigationXiaoGameModel : BaseViewModel() {
    var searchKey = StringObservableField()
    var pingtaibi = StringObservableField()
    var title1 = StringObservableField()
    var title1_1 = StringObservableField()
    var title2 = StringObservableField()
    var title2_1 = StringObservableField()
    var fuLiProgress = IntObservableField(0)

    val photoList: ArrayList<Any> = ArrayList()
    var mFuLiInfo = AppletsXiaoGameFuLiInfo()
    var mDialogWanFa = AppletsInfo()
    var mDialogFuLi = AppletsInfo()

    var mHotGameList: MutableList<ModGameHallList> = mutableListOf()
    var mMoreGameList: MutableList<ModGameHallList> = mutableListOf()

    var fuLiInfoResult = MutableLiveData<ModResultStateWithMsg<AppletsXiaoGameFuLiInfo>>()
    var hotGameListResult = MutableLiveData<ModResultStateWithMsg<MutableList<ModGameHallList>>>()
    var moreGameListResult = MutableLiveData<ModResultStateWithMsg<MutableList<ModGameHallList>>>()
    var dialogFuLiInfoResult = MutableLiveData<ModResultStateWithMsg<AppletsInfo>>()
    var dialogWanFaInfoResult = MutableLiveData<ModResultStateWithMsg<AppletsInfo>>()

    var tradeDiyGoodsListResult = MutableLiveData<ModResultState<MutableList<ModTradeGoodDetailBean>>>()

    var gameListResult = MutableLiveData<ModResultState<MutableList<ModGameHallList>>>()

    var lingJiangTimeResult = MutableLiveData<ModResultStateWithMsg<Any>>()
    var lingJiangShiWanResult = MutableLiveData<ModResultStateWithMsg<AppletsFuLiLingQu>>()
    var lingJiangNewFuLiResult = MutableLiveData<ModResultStateWithMsg<AppletsFuLiLingQu>>()
    fun postXiaoGameInfoAll() {
        requestFlow {
            val uid = ModManager.provider.getUserUid()
            val token = ModManager.provider.getUserToken()
            Logs.e("postXiaoGameInfoAll:$uid..$token")
            val fuLiInfo = step(
                block = {
                    val map = mutableMapOf<String, String>()
                    map["api"] = "xgame_index"
                    map["gameid"] = "15223"
                    map["uid"] = uid
                    map["token"] = token
                    apiService.postModXiaoGameFuLiInfo(NetworkApi.INSTANCE.createPostData(map)!!)
                },
                resultState = fuLiInfoResult,
            )
            mFuLiInfo = fuLiInfo

            val wanFaDialog = step(
                block = {
                    val map = mutableMapOf<String, String>()
                    map["api"] = "market_data_appapi"
                    map["market_data_id"] = "594"
                    apiService.postInfoAppApi(NetworkApi.INSTANCE.createPostData(map)!!)
                },
                resultState = dialogWanFaInfoResult
            )
            mDialogWanFa = wanFaDialog

            val fuLiDialog = step(
                block = {
                    val map = mutableMapOf<String, String>()
                    map["api"] = "market_data_appapi"
                    map["market_data_id"] = "595"
                    apiService.postInfoAppApi(NetworkApi.INSTANCE.createPostData(map)!!)
                },
                resultState = dialogFuLiInfoResult
            )
            mDialogFuLi = fuLiDialog

            val hotGameList = step(
                block = {
                    val map = mutableMapOf<String, String>()
                    map["api"] = "gamelist_page"
                    map["page"] = "1"
                    map["order"] = "hot"
                    map["pagecount"] = "12"
                    map["game_type"] = "4"
                    map["list_type"] = "game_list"
                    apiService.postGameHallList(NetworkApi.INSTANCE.createPostData(map)!!)
                },
                resultState = hotGameListResult
            )
            mHotGameList = hotGameList

            val moreGameList = step(
                block = {
                    val map = mutableMapOf<String, String>()
                    map["api"] = "gamelist_page"
                    map["page"] = "1"
                    map["order"] = "newest"
                    map["pagecount"] = "12"
                    map["game_type"] = "4"
                    map["list_type"] = "game_list"
                    apiService.postGameHallList(NetworkApi.INSTANCE.createPostData(map)!!)
                },
                resultState = moreGameListResult
            )
            mMoreGameList = moreGameList

        }
    }

    fun postMoreGameList(page: String,order:String) {
        modRequestWithMsg({
            val map = mutableMapOf<String, String>()
            map["api"] = "gamelist_page"
            map["page"] = page
            map["order"] = order
            map["pagecount"] = "12"
            map["game_type"] = "4"
            map["list_type"] = "game_list"
            apiService.postGameHallList(NetworkApi.INSTANCE.createPostData(map)!!)
        }, moreGameListResult)
    }


    fun postLingJiangTime(id: String) {
        modRequestWithMsg({
            val uid = ModManager.provider.getUserUid()
            val token = ModManager.provider.getUserToken()
            val map = mutableMapOf<String, String>()
            map["api"] = "xgame_get_reward"
            map["id"] = id
            map["uid"] = uid
            map["token"] = token
            apiService.postLingJiangTimeApi(NetworkApi.INSTANCE.createPostData(map)!!)
        }, lingJiangTimeResult)
    }

    fun postLingJiangShiWan(gameid: String) {
        modRequestWithMsg({
            val uid = ModManager.provider.getUserUid()
            val token = ModManager.provider.getUserToken()
            val map = mutableMapOf<String, String>()
            map["api"] = "xgame_get_demo_reward"
            map["gameid"] = gameid
            map["uid"] = uid
            map["token"] = token
            apiService.postLingJiangShiWanApi(NetworkApi.INSTANCE.createPostData(map)!!)
        }, lingJiangShiWanResult)
    }

    fun postLingJiangNewFuLi() {
        modRequestWithMsg({
            val uid = ModManager.provider.getUserUid()
            val token = ModManager.provider.getUserToken()
            val map = mutableMapOf<String, String>()
            map["api"] = "xgame_get_fuli_reward"
            map["uid"] = uid
            map["token"] = token
            apiService.postLingJiangNewFuLiApi(NetworkApi.INSTANCE.createPostData(map)!!)
        }, lingJiangNewFuLiResult)
    }

    fun postFuLiInfo() {
        modRequestWithMsg({
            val uid = ModManager.provider.getUserUid()
            val token = ModManager.provider.getUserToken()
            val map = mutableMapOf<String, String>()
            map["api"] = "xgame_index"
            map["gameid"] = "15223"
            map["uid"] = uid
            map["token"] = token
            apiService.postModXiaoGameFuLiInfo(NetworkApi.INSTANCE.createPostData(map)!!)
        }, fuLiInfoResult)
    }


    fun postGameListOld(genreId: String, orderBy: String, page: String) {
        modRequest({
            val map = mutableMapOf<String, String>()
            map["api"] = "gamelist_page"
            map["page"] = page
            map["order"] = orderBy
            map["orderBy"] = orderBy
            map["genre_id"] = genreId
            map["pagecount"] = "12"
            map["list_type"] = "game_list"
            apiService.postGameHallList(NetworkApi.INSTANCE.createPostData(map)!!)
        }, gameListResult)
    }


    /**
     * 获取游戏列表。
     * 此方法已重构：获取列表后，会接着并发获取每个游戏的图标，并替换原有 gameicon 字段，最后才更新LiveData。
     */
    fun postGameList(genreId: String, orderBy: String, page: String) {
        viewModelScope.launch {
            try {
                val listMap = mutableMapOf<String, String>()
                listMap["api"] = "gamelist_page"
                listMap["page"] = page
                listMap["order"] = orderBy
                listMap["orderBy"] = orderBy
                listMap["genre_id"] = genreId
                listMap["pagecount"] = "12"
                listMap["list_type"] = "game_list"
                val gameListResponse = apiService.postGameHallList(NetworkApi.INSTANCE.createPostData(listMap)!!)
                if (gameListResponse.isSucceed()) {
                    val originalList = gameListResponse.getResponseData()
                    if (!originalList.isNullOrEmpty()) {
                        val updatedList = coroutineScope {
                            originalList.map { game ->
                                async {
                                    try {
                                        val iconMap = mutableMapOf("api" to "market_tradegame", "gameid" to game.gameid)
                                        val iconResponse = apiService.postModTradeGameIcon(NetworkApi.INSTANCE.createPostData(iconMap)!!)
                                        if (iconResponse.isSucceed() && iconResponse.getResponseData() != null) {
                                            val newIconUrl = iconResponse.getResponseData()!!.tradegameicon
                                            if (newIconUrl.isNotEmpty()) {
                                                game.copy(gameicon = newIconUrl)
                                            } else {
                                                game
                                            }
                                        } else {
                                            game
                                        }
                                    } catch (e: Exception) {
                                        Logs.e("IconFetch", "Exception for gameId: ${game.gameid}", e)
                                        game
                                    }
                                }
                            }.awaitAll()
                        }
                        val finalList = updatedList.toMutableList()
                        gameListResult.value = ModResultState.onAppSuccess(finalList)

                    } else {
                        gameListResult.value = ModResultState.onAppSuccess(originalList)
                    }
                } else {
                    gameListResult.value = ModResultState.onAppError(
                        ModAppException(
                            gameListResponse.getResponseCode().toString(),
                            "",
                            gameListResponse.getResponseMsg()
                        )
                    )
                }
            } catch (e: Throwable) {
                gameListResult.value = ModResultState.onAppError(ModExceptionHandle.handleException(e))
            }
        }
    }


    /**
     * 重构后的方法，现在会先获取列表，再获取正确的 gameicon 进行赋值。normal trends
     */
    fun postDiyTradeGoodsList(scene: String, orderBy: String, page: String, pageCount: String) {
        fetchAndProcessGoods(
            goodsListFetcher = {
                val map = mutableMapOf<String, String>()
                map["scene"] = scene
                map["orderby"] = orderBy
                map["pic"] = "multiple"
                map["one_discount"] = "yes"
                map["page"] = page
                map["kw"] = searchKey.get() // 保留了原有的搜索关键字参数
                map["pagecount"] = pageCount
                map["r_time"] = ""
                apiService.tradeGoodsList(NetworkApi.INSTANCE.createPostData(map)!!)
            },
            liveDataResult = tradeDiyGoodsListResult
        )
    }

    /**
     * 核心处理逻辑：获取商品列表 -> 并发获取图标 -> 更新 gameicon 字段 -> 提交结果
     */
    private fun fetchAndProcessGoods(
        goodsListFetcher: suspend () -> ModApiResponse<MutableList<ModTradeGoodDetailBean>>,
        liveDataResult: MutableLiveData<ModResultState<MutableList<ModTradeGoodDetailBean>>>
    ) {
        viewModelScope.launch {
            try {
                val goodsResponse = goodsListFetcher()

                if (goodsResponse.isSucceed()) {
                    val originalList = goodsResponse.getResponseData()

                    if (!originalList.isNullOrEmpty()) {
                        val updatedList = coroutineScope {
                            originalList.map { good ->
                                async {
                                    try {
                                        val iconMap = mutableMapOf("api" to "market_tradegame", "gameid" to good.gameid)
                                        val iconResponse = apiService.postModTradeGameIcon(NetworkApi.INSTANCE.createPostData(iconMap)!!)

                                        if (iconResponse.isSucceed() && iconResponse.getResponseData() != null) {
                                            val newIconUrl = iconResponse.getResponseData()!!.tradegameicon
                                            if (newIconUrl.isNotEmpty()) {
                                                good.copy(gameicon = newIconUrl)
                                            } else {
                                                good
                                            }
                                        } else {
                                            good
                                        }
                                    } catch (e: Exception) {
                                        good
                                    }
                                }
                            }.awaitAll()
                        }

                        val finalList = updatedList.toMutableList()
                        liveDataResult.value = ModResultState.onAppSuccess(finalList)
                    } else {
                        liveDataResult.value = ModResultState.onAppSuccess(originalList)
                    }
                } else {
                    liveDataResult.value = ModResultState.onAppError(
                        ModAppException(
                            goodsResponse.getResponseCode().toString(),
                            "",
                            goodsResponse.getResponseMsg()
                        )
                    )
                }
            } catch (e: Throwable) {
                liveDataResult.value = ModResultState.onAppError(ModExceptionHandle.handleException(e))
            }
        }
    }


}