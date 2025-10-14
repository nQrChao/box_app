package com.zqhy.mod.fragment

import androidx.lifecycle.MutableLiveData
import com.chaoji.base.base.viewmodel.BaseViewModel
import com.chaoji.base.ext.modRequest
import com.chaoji.base.state.ModResultState
import com.chaoji.im.data.model.AppletsXiaoGame
import com.chaoji.im.network.NetworkApi
import com.chaoji.im.network.apiService
import com.chaoji.mod.game.ModManager


class NavigationZhuanQianModel : BaseViewModel() {
    var xiaoGame = MutableLiveData<AppletsXiaoGame>()
    var postDataAppApiByDataIdResult = MutableLiveData<ModResultState<AppletsXiaoGame>>()

    fun postDataAppApiByDataId(dataId: String) {
        val uid = ModManager.provider.getUserUid()
        val token = ModManager.provider.getUserToken()
        modRequest({
            val map = mutableMapOf<String, String>()
            map["api"] = "market_data_appapi"
            map["market_data_id"] = dataId
            map["uid"] = uid
            map["token"] = token
            apiService.postXiaoGameApi(NetworkApi.INSTANCE.createPostData(map)!!)
        }, postDataAppApiByDataIdResult)
    }

}