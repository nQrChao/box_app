package com.zqhy.mod.view.guide

import android.app.Activity
import android.view.ViewGroup

/**
 * 多步骤引导的管理器（总指挥官） - Kotlin 版本
 * 职责：负责编排和管理一个或多个引导步骤的显示顺序和流程。
 */
class GuideManagerK private constructor(builder: Builder) {

    private val activity: Activity = builder.activity
    private val steps: List<GuideViewK.Builder> = builder.steps
    private var currentIndex = 0
    private var currentGuideViewK: GuideViewK? = null

    private fun showNext() {
        if (currentIndex >= steps.size || currentGuideViewK != null) {
            return
        }

        currentGuideViewK = steps[currentIndex].build()
        currentGuideViewK?.setOnClickListener {
            dismissCurrent()
            currentIndex++
            showNext()
        }

        val decorView = activity.window.decorView as ViewGroup
        decorView.addView(currentGuideViewK)
    }

    private fun dismissCurrent() {
        currentGuideViewK?.let {
            (it.parent as? ViewGroup)?.removeView(it)
            currentGuideViewK = null
        }
    }

    fun start() {
        dismissCurrent()
        currentIndex = 0
        showNext()
    }

    class Builder(internal val activity: Activity) {
        internal val steps = mutableListOf<GuideViewK.Builder>()

        fun addStep(stepBuilder: GuideViewK.Builder) = apply {
            steps.add(stepBuilder)
        }

        fun build(): GuideManagerK {
            if (steps.isEmpty()) {
                throw IllegalStateException("No guide steps have been added.")
            }
            return GuideManagerK(this)
        }

        fun show() {
            if (steps.isNotEmpty()) {
                // 使用第一个步骤的 targetView 来 post 启动任务
                steps.first().targetView.post {
                    build().start()
                }
            }
        }
    }
}
