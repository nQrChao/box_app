package com.zqhy.mod.view.guide;

import android.app.Activity;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 多步骤引导的管理器（总指挥官）
 * 职责：负责编排和管理一个或多个引导步骤的显示顺序和流程。
 */
public class GuideManagerJ {

    private final Activity activity;
    private final List<GuideViewJ.Builder> steps;
    private int currentIndex = 0;
    private GuideViewJ currentGuideView = null;

    private GuideManagerJ(Builder builder) {
        this.activity = builder.activity;
        this.steps = builder.steps;
    }

    private void showNext() {
        // 如果已经没有下一步了，或者当前有引导页正在显示，则不做任何事
        if (currentIndex >= steps.size() || currentGuideView != null) {
            return;
        }

        // 获取当前步骤的 Builder
        GuideViewJ.Builder currentStepBuilder = steps.get(currentIndex);
        currentGuideView = currentStepBuilder.build();

        // 核心：由 Manager 来设置点击事件，控制流程
        currentGuideView.setOnClickListener(v -> {
            dismissCurrent();
            currentIndex++;
            showNext(); // 递归调用，显示下一步
        });

        // 将 GuideView 添加到屏幕上
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        decorView.addView(currentGuideView);
    }

    /**
     * 移除当前的引导页
     */
    private void dismissCurrent() {
        if (currentGuideView != null) {
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.removeView(currentGuideView);
            currentGuideView = null;
        }
    }

    /**
     * 开始引导流程
     */
    public void start() {
        dismissCurrent(); // 开始前确保没有残留的引导页
        currentIndex = 0;
        showNext();
    }

    /**
     * GuideManager的构造器
     */
    public static class Builder {
        private final Activity activity;
        private final List<GuideViewJ.Builder> steps = new ArrayList<>();

        public Builder(@NonNull Activity activity) {
            this.activity = activity;
        }

        /**
         * 添加一个引导步骤
         * @param stepBuilder 一个已经配置好的 GuideView.Builder 实例
         */
        public Builder addStep(@NonNull GuideViewJ.Builder stepBuilder) {
            steps.add(stepBuilder);
            return this;
        }

        public GuideManagerJ build() {
            if (steps.isEmpty()) {
                throw new IllegalStateException("No guide steps have been added.");
            }
            return new GuideManagerJ(this);
        }

        /**
         * 构建并开始引导流程。
         * 使用 post 确保所有目标 View 都已经完成测量和布局。
         */
        public void show() {
            if (!steps.isEmpty()) {
                // 延迟到第一个目标View布局完成后再启动整个流程
                steps.get(0).getTargetView().post(() -> build().start());
            }
        }
    }
}
