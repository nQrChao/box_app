package com.zqhy.mod.view.guide

import android.app.Activity
import android.graphics.*
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import com.chaoji.common.R

/**
 * 引导视图（显示组件） - Kotlin 版本
 * 职责：只负责根据 Builder 的配置，显示一个带高亮区域和引导图的遮罩层。
 * 它本身不处理点击事件的业务逻辑。
 */
class GuideViewK private constructor(builder: Builder) : FrameLayout(builder.activity) {

    enum class Position {
        TOP, BOTTOM, LEFT, RIGHT
    }

    private val targetView: View = builder.targetView
    private val guideBitmap: Bitmap
    private val guideImagePosition: Position = builder.guideImagePosition
    private val cornerRadius: Float = builder.cornerRadius.toFloat()
    private val highlightPadding: Int = builder.highlightPadding

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#80000000")
    }
    private val clearPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }
    private val targetRect = Rect()

    init {
        // **核心修复点：检查资源解码结果**
        guideBitmap = BitmapFactory.decodeResource(resources, builder.guideImageResId)
            ?: throw IllegalArgumentException(
                "Provided guideImageResId could not be decoded into a Bitmap. " +
                        "Please check if the resource is a valid image (PNG, JPG, etc.) and not an XML drawable."
            )

        // 初始化
        layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        setBackgroundColor(Color.TRANSPARENT)
        setLayerType(LAYER_TYPE_SOFTWARE, null) // 必须是软件绘制
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 1. 绘制半透明背景
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), backgroundPaint)

        // 2. 获取目标View的位置并应用内边距
        targetView.getGlobalVisibleRect(targetRect)
        targetRect.inset(-highlightPadding, -highlightPadding)

        // 3. 绘制高亮区域（挖洞）
        val highlightRectF = RectF(targetRect)
        canvas.drawRoundRect(highlightRectF, cornerRadius, cornerRadius, clearPaint)

        // 4. 根据设定的方位绘制引导图 (由于构造函数已检查，这里无需再检查 guideBitmap 是否为 null)
        val space = 30f
        val imageX: Float
        val imageY: Float

        when (guideImagePosition) {
            Position.TOP -> {
                imageX = highlightRectF.centerX() - guideBitmap.width / 2f
                imageY = highlightRectF.top - guideBitmap.height - space
            }
            Position.BOTTOM -> {
                imageX = highlightRectF.centerX() - guideBitmap.width / 2f
                imageY = highlightRectF.bottom + space
            }
            Position.LEFT -> {
                imageX = highlightRectF.left - guideBitmap.width - space
                imageY = highlightRectF.centerY() - guideBitmap.height / 2f
            }
            Position.RIGHT -> {
                imageX = highlightRectF.right + space
                imageY = highlightRectF.centerY() - guideBitmap.height / 2f
            }
        }
        canvas.drawBitmap(guideBitmap, imageX, imageY, null)
    }

    /**
     * GuideViewK 的构造器
     */
    class Builder(internal val activity: Activity) {
        internal lateinit var targetView: View
        @DrawableRes
        internal var guideImageResId: Int = 0
        internal var guideImagePosition: Position = Position.TOP
        internal var cornerRadius: Int = 30
        internal var highlightPadding: Int = 10

        fun setTargetView(targetView: View) = apply { this.targetView = targetView }
        fun setGuideImage(@DrawableRes resId: Int) = apply { this.guideImageResId = resId }
        fun setGuideImagePosition(position: Position) = apply { this.guideImagePosition = position }
        fun setCornerRadius(radius: Int) = apply { this.cornerRadius = radius }
        fun setHighlightPadding(padding: Int) = apply { this.highlightPadding = padding }

        fun build(): GuideViewK {
            if (!::targetView.isInitialized) {
                throw IllegalStateException("TargetView has not been set.")
            }
            if (guideImageResId == 0) {
                throw IllegalStateException("GuideImageResId has not been set.")
            }
            return GuideViewK(this)
        }
    }

//    fun test(){
//        val step1: GuideView.Builder = Builder(_mFragment.getActivity())
//            .setTargetView(holder.mTvGameSuffix)
//            .setGuideImage(R.mipmap.easy_photos_ic_controller)
//            .setGuideImagePosition(GuideView.Position.BOTTOM)
//            .setHighlightPadding(15)
//            .setCornerRadius(15)
//
//        val step2: GuideView.Builder = Builder(_mFragment.getActivity())
//            .setTargetView(holder.mTvGamename)
//            .setGuideImage(R.mipmap.easy_photos_ic_controller)
//            .setGuideImagePosition(GuideView.Position.BOTTOM)
//            .setHighlightPadding(15)
//            .setCornerRadius(15)
//
//        Builder(_mFragment.getActivity())
//            .addStep(step1)
//            .addStep(step2)
//            .show()
//    }

}
