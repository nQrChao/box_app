package com.zqhy.mod.xpop

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.Button
import com.chaoji.base.base.action.ClickAction
import com.chaoji.base.base.action.KeyboardAction
import com.chaoji.other.xpopup.core.CenterPopupView
import com.zqhy.app.newproject.R

@SuppressLint("ViewConstructor")
class ModXPopupH5GameTip1(context: Context, private var sure: (() -> Unit)?) :
    CenterPopupView(context), ClickAction, KeyboardAction {
    override fun getImplLayoutId(): Int = R.layout.xpopup_h5_game_tip1

    private var confirmView: Button? = null
    @SuppressLint("SetTextI18n")
    override fun onCreate() {
        super.onCreate()
        confirmView = findViewById<Button>(R.id.btn_confirm)
        setOnClickListener(R.id.btn_confirm)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_confirm -> {
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