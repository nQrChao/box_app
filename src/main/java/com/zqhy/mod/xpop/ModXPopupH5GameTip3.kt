package com.zqhy.mod.xpop

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.chaoji.base.base.action.ClickAction
import com.chaoji.base.base.action.KeyboardAction
import com.chaoji.other.xpopup.core.CenterPopupView
import com.zqhy.app.newproject.R

@SuppressLint("ViewConstructor")
class ModXPopupH5GameTip3(context: Context, private var sure: (() -> Unit)?) :
    CenterPopupView(context), ClickAction, KeyboardAction {
    override fun getImplLayoutId(): Int = R.layout.xpopup_h5_game_tip3

    private var rootView: ImageView? = null
    @SuppressLint("SetTextI18n")
    override fun onCreate() {
        super.onCreate()
        rootView = findViewById<ImageView>(R.id.rootView)
        setOnClickListener(R.id.rootView)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.rootView -> {
                sure?.invoke()
                dismiss()
            }
        }
    }

    override fun dismiss() {
        super.dismiss()
        hideKeyboard(this)
    }


}