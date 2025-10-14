package com.zqhy.mod.game

import android.content.Context
import android.content.Intent
import com.chaoji.im.parseWithSplitToList
import com.chaoji.mod.game.ModManager
import com.chaoji.mod.ui.activity.game.ModActivityGameBrowser
import com.chaoji.mod.ui.activity.game.ModActivityLocalGameBrowser
import com.chaoji.other.blankj.utilcode.util.ActivityUtils
import com.zqhy.app.core.view.FragmentHolderActivity
import com.zqhy.app.core.view.game.GameDetailInfoFragment
import com.zqhy.app.core.view.main.MainActivity

object GameLauncher {
    const val GAME_URL = ModManager.GAME_URL
    fun startGame(context: Context) {
        ModActivityGameBrowser.start(context, GAME_URL)
    }

    fun startLocalGame(context: Context) {
        ModActivityLocalGameBrowser.start(context)
    }

    fun startLocalGame(context: Context, url: String) {
        ModActivityGameBrowser.start(context, url) { jumpString: String ->
            val list = parseWithSplitToList(jumpString)
            when (val firstItem = list?.getOrNull(0)) {
                "详情" -> {
                    val gameDetailInfoFragment = GameDetailInfoFragment.newInstance(
                        list[1].toInt(),
                        2,
                        false,
                        "",
                        false,
                        true
                    )
                    FragmentHolderActivity.startFragmentInActivity(context, gameDetailInfoFragment)
                }

                "启动" -> {
                    val gameDetailInfoFragment = GameDetailInfoFragment.newInstance(
                        list[1].toInt(),
                        2,
                        false,
                        "",
                        false,
                        true
                    )
                    FragmentHolderActivity.startFragmentInActivity(context, gameDetailInfoFragment)
                }

                "领取" -> {
                    val intent = Intent(
                        context,
                        MainActivity::class.java
                    )
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    intent.putExtra("jingQuJump", "jingQuJump")
                    ActivityUtils.startActivity(intent)
                    //Toaster.show(firstItem)
                }
            }
        }
    }
}