package com.zqhy.mod.view

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.databinding.BindingAdapter
import com.zqhy.app.newproject.R


object AppBindAdapter {

    @BindingAdapter(value = ["dynamicBackground"])
    @JvmStatic
    fun setDynamicBackground(view: View, position: Int) {
        // 定义一个 drawable 资源数组，使用 intArrayOf()
        val drawableResources = intArrayOf(
            R.drawable.a_xiao_game_list_item_shiwan1_pic,
            R.drawable.a_xiao_game_list_item_shiwan2_pic,
            R.drawable.a_xiao_game_list_item_shiwan3_pic
        )

        // 根据 position 对数组长度取模，得到对应的 drawable 索引
        val drawableResId = drawableResources[position % drawableResources.size]

        // 设置背景
        view.setBackgroundResource(drawableResId)
    }


    @BindingAdapter(value = ["startRotationAnimation"])
    @JvmStatic
    fun setRotationAnimation(view: View, start: Boolean) {
        if (start) {
            // 如果动画已经存在，则不重复创建
            if (view.getTag(R.id.rotation_animator_tag) != null) {
                return
            }

            val animator = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f)
            animator.duration = 2000 // 动画周期，2秒旋转一圈
            animator.interpolator = LinearInterpolator() // 匀速旋转
            animator.repeatCount = ObjectAnimator.INFINITE // 无限重复
            animator.start()
            // 将动画实例存储在 View 的 Tag 中，以便后续停止
            view.setTag(R.id.rotation_animator_tag, animator)

        } else {
            // 当 start 为 false 时，停止动画
            val animator = view.getTag(R.id.rotation_animator_tag) as? ObjectAnimator
            if (animator != null && animator.isRunning) {
                animator.cancel()
                view.rotation = 0f // 重置旋转角度
                view.setTag(R.id.rotation_animator_tag, null) // 清除Tag
            }
        }
    }



}