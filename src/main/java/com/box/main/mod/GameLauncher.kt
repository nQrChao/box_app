package com.box.main.mod

import android.content.Context
import android.content.Intent
import com.blankj.utilcode.util.ActivityUtils

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