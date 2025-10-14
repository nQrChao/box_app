package com.zqhy.app.core.inner;

import com.zqhy.app.core.ui.dialog.CustomDialog;

/**
 * @author Administrator
 */
public interface OnCommonDialogClickListener {

    void onCancel(CustomDialog dialog);

    void onConfirm(CustomDialog dialog);
}
