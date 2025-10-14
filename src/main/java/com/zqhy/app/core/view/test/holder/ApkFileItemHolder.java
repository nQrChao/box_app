package com.zqhy.app.core.view.test.holder;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class ApkFileItemHolder extends BaseItemHolder<ResolveInfo, ApkFileItemHolder.ViewHolder> {

    public ApkFileItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_apk_file_list;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ResolveInfo item) {
        if (_mFragment != null) {
            PackageManager pm = _mFragment.getActivity().getPackageManager();
            CharSequence appName = item.loadLabel(pm);
            holder.mTvAppName.setText(appName);
            Drawable appIcon = item.loadIcon(pm);
            holder.mIvAppIcon.setImageDrawable(appIcon);
        }
    }

    public class ViewHolder extends AbsHolder {
        private ImageView mIvAppIcon;
        private TextView mTvAppName;

        public ViewHolder(View view) {
            super(view);
            mIvAppIcon = findViewById(R.id.iv_app_icon);
            mTvAppName = findViewById(R.id.tv_app_name);

        }
    }
}
