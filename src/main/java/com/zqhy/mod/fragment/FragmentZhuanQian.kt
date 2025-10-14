package com.zqhy.mod.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.box.base.base.fragment.BaseTitleBarFragment
import com.box.base.ext.parseModState
import com.box.base.network.NetState
import com.box.common.BR
import com.box.im.appContext
import com.box.im.data.model.AppletsXiaoGameBean
import com.box.im.sdk.ImSDK
import com.box.im.ui.adapter.SpacingItemDecorator
import com.box.mod.game.ModManager
import com.box.other.blankj.utilcode.util.ActivityUtils
import com.box.other.blankj.utilcode.util.GsonUtils
import com.box.other.blankj.utilcode.util.Logs
import com.box.other.blankj.utilcode.util.StringUtils
import com.box.other.hjq.toast.Toaster
import com.box.other.immersionbar.immersionBar
import com.zqhy.app.core.view.browser.BrowserActivity
import com.zqhy.app.core.view.login.LoginActivity
import com.zqhy.app.model.UserInfoModel
import com.zqhy.app.newproject.BuildConfig
import com.zqhy.app.newproject.R
import com.zqhy.app.newproject.databinding.FragmentZhuanqianBinding
import com.zqhy.app.newproject.databinding.ItemXiaoGameBinding
import com.zqhy.mod.game.GameLauncher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class FragmentZhuanQian : BaseTitleBarFragment<NavigationZhuanQianModel, FragmentZhuanqianBinding>() {
    private var appList: MutableList<AppletsXiaoGameBean> = mutableListOf()
    private var appListAdapter = AppletsXiaoGameAdapter(appList)
    override fun layoutId(): Int = R.layout.fragment_zhuanqian
    companion object {
        fun newInstance(): FragmentZhuanQian {
            return FragmentZhuanQian()
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.vm = mViewModel
        mDataBinding.click = ProxyClick()
        immersionBar {
            statusBarDarkFont(true)
            init()
        }

        appListAdapter.setDiffCallback(AppletsXiaoGameDiffCallback())
        appListAdapter.addChildClickViewIds(R.id.btn)
        mDataBinding.recyclerView.run {
            layoutManager = GridLayoutManager(context, 1)
            addItemDecoration(SpacingItemDecorator((resources.displayMetrics.density * 5).toInt()))
            adapter = appListAdapter
        }

        appListAdapter.setOnItemClickListener { adapter, view, position ->
            val applets = adapter.data[position] as AppletsXiaoGameBean
            if (!UserInfoModel.getInstance().isLogined) {
                ActivityUtils.startActivity(LoginActivity::class.java)
            } else {
                if (BuildConfig.APP_TEMPLATE == 9998) {
                    ImSDK.eventViewModelInstance.goTest.value = 2
                }
                if (StringUtils.isEmpty(applets.gameid)) {
                    GameLauncher.startLocalGame(appContext, applets.gameUrl)
                } else if (!StringUtils.isEmpty(applets.appId)) {
                    ModManager.provider.handleJumpAction(
                        appContext, "启动_${applets.gameid.toInt()}"
                    )
                } else {
                    Toaster.show("未找到游戏")
                }


            }
        }
        appListAdapter.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.btn) {
                val applets = adapter.data[position] as AppletsXiaoGameBean
                if (!UserInfoModel.getInstance().isLogined) {
                    ActivityUtils.startActivity(LoginActivity::class.java)
                } else {
                    if (BuildConfig.APP_TEMPLATE == 9998) {
                        ImSDK.eventViewModelInstance.goTest.value = 2
                    }
                    if (StringUtils.isEmpty(applets.gameid)) {
                        GameLauncher.startLocalGame(appContext, applets.gameUrl)
                    } else if (!StringUtils.isEmpty(applets.appId)) {
                        ModManager.provider.handleJumpAction(
                            appContext, "启动_${applets.gameid.toInt()}"
                        )
                    } else {
                        Toaster.show("未找到游戏")
                    }
                }
            }
        }

        mDataBinding.refreshLayout.apply {
            setOnLoadMoreListener {
                lifecycleScope.launch {
                    finishLoadMore(1000)
                    delay(1000)
                    ImSDK.eventViewModelInstance.setMainCurrentItem.value = 1
                }
            }
        }


    }

    override fun initData() {

    }

    override fun createObserver() {
        mViewModel.xiaoGame.observe(this) {
            val list = it.marketjson.list_data
            if (!list.isNullOrEmpty()) {
                appList = list
                val firstGame = appList[0]
                firstGame.xdfirst = true
                appList[0] = firstGame
                if (BuildConfig.APP_TEMPLATE != 9998
                    && BuildConfig.APP_UPDATE_ID != "16"
                    && BuildConfig.APP_UPDATE_ID != "43"
                    && BuildConfig.APP_UPDATE_ID != "11") {
                    firstGame.first = true
                    firstGame.gameUrl = ModManager.GAME_URL
                }
                appListAdapter.setDiffNewData(appList)
                Logs.e("xiaoGameList", GsonUtils.toJson(list))
                mDataBinding.recyclerView.post {
                    if (!mDataBinding.recyclerView.canScrollVertically(1)) {
                        Logs.d("CheckBottom", "列表已在底部")
                        ImSDK.eventViewModelInstance.setMainCurrentItem.value = 1
                    }
                }
            } else {
                appList = mutableListOf()
                appListAdapter.setDiffNewData(appList)
            }
        }


        mViewModel.postDataAppApiByDataIdResult.observe(this) { it ->
            parseModState(it, {
                it?.let {
                    Logs.e("postDataAppApiByDataIdResult", GsonUtils.toJson(it))
                    mViewModel.xiaoGame.value = it
                }
            }, {
                Toaster.show(it.errorLog)
            })
        }

    }

    override fun lazyLoadData() {
        getList()
    }

    fun getList() {
        if (BuildConfig.APP_UPDATE_ID == "16"
            || BuildConfig.APP_UPDATE_ID == "43") {
            mViewModel.postDataAppApiByDataId("440")
        } else if (BuildConfig.APP_UPDATE_ID == "11") {
            mViewModel.postDataAppApiByDataId("457")
        } else {
            mViewModel.postDataAppApiByDataId("405")
        }
    }

    override fun onNetworkStateChanged(it: NetState) {
    }


    /**********************************************Click**************************************************/
    inner class ProxyClick {
        fun daiJinQuan() {
            BrowserActivity.newInstance(activity, mViewModel.xiaoGame.value?.marketjson?.url_coupon648)
        }

        fun fuLiBi() {
            BrowserActivity.newInstance(activity, mViewModel.xiaoGame.value?.marketjson?.url_gold_game)
        }

    }

    class AppletsXiaoGameAdapter constructor(list: MutableList<AppletsXiaoGameBean>) : BaseQuickAdapter<AppletsXiaoGameBean, BaseDataBindingHolder<ItemXiaoGameBinding>>(
        R.layout.item_xiao_game, list
    ) {
        override fun convert(holder: BaseDataBindingHolder<ItemXiaoGameBinding>, item: AppletsXiaoGameBean) {
            holder.dataBinding?.setVariable(BR.xiaoGameBean, item)
        }

        override fun onBindViewHolder(holder: BaseDataBindingHolder<ItemXiaoGameBinding>, position: Int, payloads: MutableList<Any>) {
            super.onBindViewHolder(holder, position, payloads)
            if (payloads.isEmpty()) {
                super.onBindViewHolder(holder, position, payloads)
                return
            }

            val binding = holder.dataBinding
            val item = getItem(position)

            // 遍历所有的 payload
            for (payload in payloads) {
                if (payload == "LIKE_UPDATE") {
                    binding?.setVariable(BR.xiaoGameBean, item)
                }
            }
        }

    }

    class AppletsXiaoGameDiffCallback : DiffUtil.ItemCallback<AppletsXiaoGameBean>() {
        override fun areItemsTheSame(oldItem: AppletsXiaoGameBean, newItem: AppletsXiaoGameBean): Boolean {
            return oldItem.appId == newItem.appId
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: AppletsXiaoGameBean, newItem: AppletsXiaoGameBean): Boolean {
            return oldItem == newItem
        }
    }


}





