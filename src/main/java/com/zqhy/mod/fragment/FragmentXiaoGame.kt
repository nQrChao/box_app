package com.zqhy.mod.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.box.base.base.action.StatusAction
import com.box.base.base.fragment.BaseTitleBarFragment
import com.box.base.ext.parseModStateWithMsg
import com.box.base.network.NetState
import com.box.common.BR
import com.box.im.appContext
import com.box.im.data.model.AppletsXiaoGameFuLiInfo
import com.box.im.data.model.ModGameHallList
import com.box.im.ui.adapter.HorizontalSpaceItemDecoration
import com.box.im.ui.adapter.SpacingItemDecorator
import com.box.im.ui.layout.StatusLayout
import com.box.mod.game.ModManager
import com.box.other.blankj.utilcode.util.ActivityUtils
import com.box.other.blankj.utilcode.util.ColorUtils
import com.box.other.blankj.utilcode.util.GsonUtils
import com.box.other.blankj.utilcode.util.Logs
import com.box.other.hjq.toast.Toaster
import com.box.other.immersionbar.immersionBar
import com.box.other.xpopup.XPopup
import com.box.other.xpopup.core.BasePopupView
import com.zqhy.app.core.view.browser.BrowserActivity
import com.zqhy.app.newproject.R
import com.zqhy.app.newproject.databinding.ItemGameHotBinding
import com.zqhy.app.newproject.databinding.ItemGameMoreBinding
import com.zqhy.app.newproject.databinding.ItemGameShiwanBinding
import com.zqhy.app.newproject.databinding.FragmentXiaoGameBinding
import com.zqhy.app.newproject.databinding.ItemGameFuliTaskBinding
import com.zqhy.mod.xpop.ModXPopupH5GameTip1
import com.zqhy.mod.xpop.ModXPopupH5GameTip2
import com.zqhy.mod.xpop.ModXPopupH5GameTip3

class FragmentXiaoGame : BaseTitleBarFragment<NavigationXiaoGameModel, FragmentXiaoGameBinding>(), StatusAction {
    private var currentPage = 1
    private var currentOrderby = "newest"

    private var fuLiTaskList: MutableList<AppletsXiaoGameFuLiInfo.PlayInfo.TaskArray> = mutableListOf()
    private var fuLiTaskAdapter = FuLiTaskAdapter(fuLiTaskList)

    private var gameShiWanList: MutableList<AppletsXiaoGameFuLiInfo.DemoInfo> = mutableListOf()
    private var gameShiWanAdapter = GameShiWanAdapter(gameShiWanList)

    private var gameHotList: MutableList<ModGameHallList> = mutableListOf()
    private var gameHotAdapter = GameHotAdapter(gameHotList)

    private var gameMoreList: MutableList<ModGameHallList> = mutableListOf()
    private var gameMoreAdapter = GameMoreAdapter(gameMoreList)

    private var newFuLiPop: BasePopupView? = null

    override fun layoutId(): Int = R.layout.fragment_xiao_game
    override fun lazyLoadData() {
        mViewModel.postXiaoGameInfoAll()
    }

    companion object {
        fun newInstance(): FragmentXiaoGame {
            return FragmentXiaoGame()
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

        showLoading()
        mDataBinding.refreshLayout.apply {
            setOnRefreshListener {
                currentPage = 1
                mViewModel.postXiaoGameInfoAll()
            }

            setOnLoadMoreListener {
                currentPage++
                mViewModel.postMoreGameList(currentPage.toString(), currentOrderby)
            }

        }

        /************************************************************************************/

        fuLiTaskAdapter.setDiffCallback(FuLiTaskDiffCallback())
        mDataBinding.recyclerViewTop.run {
            layoutManager = GridLayoutManager(context, 4)
            addItemDecoration(SpacingItemDecorator((resources.displayMetrics.density * 1).toInt()))
            adapter = fuLiTaskAdapter
        }

        fuLiTaskAdapter.setOnItemClickListener { adapter, view, position ->
            val fuLiTaskBean = adapter.data[position] as AppletsXiaoGameFuLiInfo.PlayInfo.TaskArray
            if (!fuLiTaskBean.isGet() && fuLiTaskBean.canGet()) {
                mViewModel.postLingJiangTime(fuLiTaskBean.id)
            }else if(fuLiTaskBean.isGet()){
                Logs.e("已领")
            }else{
                Toaster.show("您的游玩时长尚未满足奖励条件")
            }

        }

        /************************************************************************************/

        mDataBinding.recyclerView.run {
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(SpacingItemDecorator((resources.displayMetrics.density * 2).toInt()))
            adapter = gameShiWanAdapter
        }

        gameShiWanAdapter.addChildClickViewIds(R.id.shiwanLingQu)
        gameShiWanAdapter.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.shiwanLingQu) {
                val gameShiWanBean = adapter.data[position] as AppletsXiaoGameFuLiInfo.DemoInfo
                if (gameShiWanBean.canGet()) {
                    mViewModel.postLingJiangShiWan(gameShiWanBean.gameid)
                }
            }
        }

        gameShiWanAdapter.setOnItemClickListener { adapter, view, position ->
            val gameShiWanBean = adapter.data[position] as AppletsXiaoGameFuLiInfo.DemoInfo
            ModManager.provider.handleJumpAction(
                appContext, "启动_${gameShiWanBean.gameid.toInt()}"
            )
        }

        /************************************************************************************/

        mDataBinding.recyclerView2.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(HorizontalSpaceItemDecoration(2))
            adapter = gameHotAdapter
        }

        gameHotAdapter.setOnItemClickListener { adapter, view, position ->
            val gameHotBean = adapter.data[position] as ModGameHallList
            ModManager.provider.handleJumpAction(
                appContext, "启动_${gameHotBean.gameid.toInt()}"
            )
        }

        /************************************************************************************/

        mDataBinding.recyclerView3.run {
            layoutManager = GridLayoutManager(context, 1)
            addItemDecoration(SpacingItemDecorator((resources.displayMetrics.density * 5).toInt()))
            adapter = gameMoreAdapter
        }


        gameMoreAdapter.addChildClickViewIds(R.id.startGame)
        gameMoreAdapter.setOnItemClickListener { adapter, view, position ->
            val gameMoreBean = adapter.data[position] as ModGameHallList
            ModManager.provider.handleJumpAction(
                appContext, "启动_${gameMoreBean.gameid.toInt()}"
            )
        }

        gameMoreAdapter.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.startGame) {
                val gameMoreBean = adapter.data[position] as ModGameHallList
                ModManager.provider.handleJumpAction(
                    appContext, "启动_${gameMoreBean.gameid.toInt()}"
                )
            }
        }


    }

    override fun initData() {

    }

    override fun createObserver() {
        mViewModel.lingJiangTimeResult.observe(this) { resultState ->
            parseModStateWithMsg(resultState,
                onSuccess = { data, msg ->
                    if (msg == "ok") {
                        Toaster.show("领取成功")
                        mViewModel.postFuLiInfo()
                    }
                },
                onError = {
                    Toaster.show(it.msg)
                }
            )
        }

        mViewModel.lingJiangShiWanResult.observe(this) { resultState ->
            parseModStateWithMsg(resultState,
                onSuccess = { data, msg ->
                    if (msg == "ok") {
                        Toaster.showFuLiSuccess(data?.ptb)
                        mViewModel.postFuLiInfo()
                    }
                },
                onError = {
                    Toaster.show(it.msg)
                }
            )
        }
        mViewModel.lingJiangNewFuLiResult.observe(this) { resultState ->
            parseModStateWithMsg(resultState,
                onSuccess = { data, msg ->
                    if (msg == "ok") {
                        Toaster.showFuLiSuccess(data?.ptb)
                        mViewModel.postFuLiInfo()
                    }
                },
                onError = {
                    Toaster.show(it.msg)
                }
            )
        }

        mViewModel.fuLiInfoResult.observe(this) { resultState ->
            parseModStateWithMsg(resultState,
                onSuccess = { data, msg ->
                    val taskArr = data?.play_info?.task_arrs
                    if (!taskArr.isNullOrEmpty()) {
                        val completedCount = taskArr.count { it.can_get == "1" }
                        val progressValue = completedCount * 25
                        mViewModel.fuLiProgress.set(progressValue)
                    } else {
                        mViewModel.fuLiProgress.set(0)
                    }
                    if (data?.showNewTip() == true) {
                        showNewFuLi()
                    }
                    mViewModel.pingtaibi.set(data?.pingtaibi)
                    fuLiTaskAdapter.setDiffNewData(taskArr)
                    gameShiWanAdapter.setList(data?.demo_info)
                },
                onError = {
                    Toaster.show(it.msg)
                }
            )
        }

        mViewModel.dialogWanFaInfoResult.observe(this) { resultState ->
            parseModStateWithMsg(resultState,
                onSuccess = { data, msg ->

                },
                onError = {
                    Toaster.show(it.msg)
                }
            )
        }

        mViewModel.dialogFuLiInfoResult.observe(this) { resultState ->
            parseModStateWithMsg(resultState,
                onSuccess = { data, msg ->

                },
                onError = {
                    Toaster.show(it.msg)
                }
            )
        }

        mViewModel.hotGameListResult.observe(this) { resultState ->
            parseModStateWithMsg(resultState,
                onSuccess = { data, msg ->
                    gameHotAdapter.setList(data)
                },
                onError = {
                    Toaster.show(it.msg)
                }
            )
            showComplete()
        }


        mViewModel.moreGameListResult.observe(this) { resultState ->
            parseModStateWithMsg(resultState,
                onSuccess = { gameList, msg ->
                    Logs.e("moreGameListResult:${GsonUtils.toJson(gameList)}")
                    if (currentPage == 1) {
                        gameMoreAdapter.setList(null)
                        mDataBinding.refreshLayout.finishRefresh()
                        gameMoreAdapter.setList(gameList)
                        //mDataBinding.recyclerView.scrollToPosition(0)
                        mDataBinding.refreshLayout.resetNoMoreData()
                    } else {
                        if (gameList.isNullOrEmpty()) {
                            mDataBinding.refreshLayout.finishLoadMoreWithNoMoreData()
                        } else {
                            mDataBinding.refreshLayout.finishLoadMore()
                            gameMoreAdapter.addData(gameList)
                        }
                    }
                },
                onError = {
                    Toaster.show(it.msg)
                    if (currentPage > 1) {
                        currentPage--
                        mDataBinding.refreshLayout.finishLoadMore(false)
                    } else {
                        if (mDataBinding.refreshLayout.isRefreshing) {
                            mDataBinding.refreshLayout.finishRefresh(false)
                        }
                    }
                }
            )
        }


//        mViewModel.moreGameListResult.observe(this) { resultState ->
//            parseModStateWithMsg(resultState,
//                onSuccess = { data, msg ->
//                    Logs.e("moreGameListResult:${GsonUtils.toJson(data)}")
//                    gameMoreAdapter.setList(data)
//                },
//                onError = {
//                    Toaster.show(it.msg)
//                }
//            )
//        }

    }


    override fun onNetworkStateChanged(it: NetState) {
    }

    private fun showNewFuLi() {
        if (newFuLiPop == null) {
            newFuLiPop = XPopup.Builder(activity)
                .hasStatusBar(true)
                .animationDuration(5)
                .isLightStatusBar(true)
                .hasNavigationBar(true)
                .dismissOnTouchOutside(false)
                .dismissOnBackPressed(false)
                .isDestroyOnDismiss(true)
                .navigationBarColor(ColorUtils.getColor(com.box.common.R.color.xpop_shadow_color))
                .asCustom(
                    ModXPopupH5GameTip3(ActivityUtils.getTopActivity()) {
                        mViewModel.postLingJiangNewFuLi()
                    })
                .show()
        } else {
            if (!newFuLiPop!!.isShow) {
                newFuLiPop?.show()
            }
        }


    }

    /**********************************************Click**************************************************/
    inner class ProxyClick {
        fun fuLiBi() {
            //Toaster.showFuLiSuccess("1.5");
            BrowserActivity.newInstance(activity, "https://hd.xiaodianyouxi.com/index.php/usage/gold_game")
        }

        fun youHuiTip() {
            BrowserActivity.newInstance(activity, "https://hd.xiaodianyouxi.com/index.php/newbie/index")
        }

        fun fuLiDialog() {
            XPopup.Builder(activity)
                .hasStatusBar(true)
                .animationDuration(5)
                .isLightStatusBar(true)
                .hasNavigationBar(true)
                .isDestroyOnDismiss(true)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .navigationBarColor(ColorUtils.getColor(com.box.common.R.color.xpop_shadow_color))
                .asCustom(
                    ModXPopupH5GameTip2(ActivityUtils.getTopActivity(), {
                    }) {
                        if (checkUserLogin()) {
                            ModManager.provider.startCurrencyFragment()
                        }
                    })
                .show()
        }

        fun wanFaDialog() {
            XPopup.Builder(activity)
                .hasStatusBar(true)
                .animationDuration(5)
                .isLightStatusBar(true)
                .hasNavigationBar(true)
                .isDestroyOnDismiss(true)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .navigationBarColor(ColorUtils.getColor(com.box.common.R.color.xpop_shadow_color))
                .asCustom(
                    ModXPopupH5GameTip1(ActivityUtils.getTopActivity()) {
                    })
                .show()
        }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.postFuLiInfo()
    }

    /**
     * 检查是否登录
     */
    private fun checkUserLogin(): Boolean {
        if (!ModManager.provider.isUserLoggedIn()) {
            ModManager.provider.startLoginActivity(mActivity)
            return false
        } else {
            return true
        }
    }

    class FuLiTaskAdapter constructor(list: MutableList<AppletsXiaoGameFuLiInfo.PlayInfo.TaskArray>) :
        BaseQuickAdapter<AppletsXiaoGameFuLiInfo.PlayInfo.TaskArray, BaseDataBindingHolder<ItemGameFuliTaskBinding>>(
            R.layout.item_game_fuli_task, list
        ) {
        override fun convert(holder: BaseDataBindingHolder<ItemGameFuliTaskBinding>, item: AppletsXiaoGameFuLiInfo.PlayInfo.TaskArray) {
            holder.dataBinding?.setVariable(BR.taskArray, item)
        }

        override fun onBindViewHolder(holder: BaseDataBindingHolder<ItemGameFuliTaskBinding>, position: Int, payloads: MutableList<Any>) {
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

    class FuLiTaskDiffCallback : DiffUtil.ItemCallback<AppletsXiaoGameFuLiInfo.PlayInfo.TaskArray>() {
        override fun areItemsTheSame(oldItem: AppletsXiaoGameFuLiInfo.PlayInfo.TaskArray, newItem: AppletsXiaoGameFuLiInfo.PlayInfo.TaskArray): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: AppletsXiaoGameFuLiInfo.PlayInfo.TaskArray, newItem: AppletsXiaoGameFuLiInfo.PlayInfo.TaskArray): Boolean {
            return oldItem == newItem
        }
    }


    class GameShiWanAdapter constructor(list: MutableList<AppletsXiaoGameFuLiInfo.DemoInfo>) :
        BaseQuickAdapter<AppletsXiaoGameFuLiInfo.DemoInfo, BaseDataBindingHolder<ItemGameShiwanBinding>>(
            R.layout.item_game_shiwan, list
        ) {
        override fun convert(holder: BaseDataBindingHolder<ItemGameShiwanBinding>, item: AppletsXiaoGameFuLiInfo.DemoInfo) {
            holder.dataBinding?.setVariable(com.box.mod.BR.demoInfo, item)
            holder.dataBinding?.setVariable(com.box.mod.BR.position, holder.bindingAdapterPosition)
        }
    }


    class GameHotAdapter constructor(list: MutableList<ModGameHallList>) : BaseQuickAdapter<ModGameHallList, BaseDataBindingHolder<ItemGameHotBinding>>(
        R.layout.item_game_hot, list
    ) {
        override fun convert(holder: BaseDataBindingHolder<ItemGameHotBinding>, item: ModGameHallList) {
            holder.dataBinding?.setVariable(com.box.mod.BR.gameHallListBean, item)
        }
    }

    class GameMoreAdapter constructor(list: MutableList<ModGameHallList>) : BaseQuickAdapter<ModGameHallList, BaseDataBindingHolder<ItemGameMoreBinding>>(
        R.layout.item_game_more, list
    ) {
        override fun convert(holder: BaseDataBindingHolder<ItemGameMoreBinding>, item: ModGameHallList) {
            holder.dataBinding?.setVariable(com.box.mod.BR.gameHallListBean, item)
        }
    }

    override fun getStatusLayout(): StatusLayout {
        return mDataBinding.hlHint
    }


}





