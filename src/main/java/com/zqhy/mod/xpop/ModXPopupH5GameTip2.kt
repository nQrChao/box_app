package com.zqhy.mod.xpop

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.chaoji.base.base.action.ClickAction
import com.chaoji.base.base.action.KeyboardAction
import com.chaoji.im.appContext
import com.chaoji.im.data.model.AppletsInfo
import com.chaoji.im.ui.activity.CommonActivityBrowser
import com.chaoji.mod.game.ModManager
import com.chaoji.other.xpopup.core.CenterPopupView
import com.zqhy.app.core.view.browser.BrowserActivity
import com.zqhy.app.newproject.R

@SuppressLint("ViewConstructor")
class ModXPopupH5GameTip2(context: Context, private var sure: (() -> Unit)?, private var clickUrl: (() -> Unit)?) :
    CenterPopupView(context), ClickAction, KeyboardAction {
    override fun getImplLayoutId(): Int = R.layout.xpopup_h5_game_tip2

    private var contentText = "1福利币=1元，支持本平台内大多数游戏充值使用，无门槛。点此查看收支记录"
    private var confirmView: Button? = null
    private var fuLiTextView: TextView? = null

    private val linkTextColor = Color.parseColor("#007BFF") // 设置链接颜色，这里使用蓝色，可以替换为其他颜色
    private val userAgreementClickableSpan = object : ClickableSpan() {
        override fun onClick(view: View) {
        }
    }

    private val privacyPolicyClickableSpan = object : ClickableSpan() {
        override fun onClick(view: View) {
            clickUrl?.invoke()
        }
    }
    @SuppressLint("SetTextI18n")
    override fun onCreate() {
        super.onCreate()
        confirmView = findViewById<Button>(R.id.btn_confirm)
        fuLiTextView = findViewById<TextView>(R.id.fuliText)
        fuLiTextView?.text = contentText

        // 使用HtmlCompat.fromHtml处理HTML标记，同时为了更好的兼容性
        val spannableString = SpannableString(
            HtmlCompat.fromHtml(contentText.replace("\n", "<br>"), HtmlCompat.FROM_HTML_MODE_LEGACY)
        )
        var startIndex = contentText.indexOf("1福利币=1元")
        var endIndex = startIndex + "1福利币=1元".length
        if (startIndex >= 0) {
            //spannableString.setSpan(userAgreementClickableSpan, startIndex, endIndex, 0) // 使用ClickableSpan
            spannableString.setSpan(ForegroundColorSpan(linkTextColor), startIndex, endIndex, 0) // 设置颜色
        }

        startIndex = contentText.indexOf("点此查看收支记录")
        endIndex = startIndex + "点此查看收支记录".length
        if (startIndex >= 0) {
            spannableString.setSpan(privacyPolicyClickableSpan, startIndex, endIndex, 0)  // 使用ClickableSpan
            spannableString.setSpan(ForegroundColorSpan(linkTextColor), startIndex, endIndex, 0) // 设置颜色
        }
        fuLiTextView?.text = spannableString
        fuLiTextView?.movementMethod = android.text.method.LinkMovementMethod.getInstance() // 使点击事件生效
        fuLiTextView?.highlightColor = Color.TRANSPARENT //去除点击后的高亮
        //contentTextView?.text = marketInit.marketjson.xieyitanchuang_neirong
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