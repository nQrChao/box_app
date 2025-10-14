package com.zqhy.app.core.view.community.integral.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.community.integral.IntegralMallListVo;
import com.zqhy.app.core.tool.utilcode.ScreenUtils;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.widget.LhhCouponView;

/**
 * @author Administrator
 */
public class IntegralMallItemHolder extends AbsItemHolder<IntegralMallListVo.ProductsListVo, IntegralMallItemHolder.ViewHolder> {

    public IntegralMallItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_integral_mall;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    private int spanCount = 2;

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull IntegralMallListVo.ProductsListVo item) {
        int mImageWidth = ScreenUtils.getScreenWidth(mContext) / spanCount - (holder.mLlRootview.getPaddingLeft() + holder.mLlRootview.getPaddingRight());

        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mImageWidth);
        holder.mFlContainerImage.setLayoutParams(imageParams);

        holder.mIvGoodImage.setVisibility(View.VISIBLE);
        holder.mTvGoodTitle.setText(item.getProduct_name());
        holder.mTvGoodIntegral.setText(String.valueOf(item.getPrice()) + "积分");
        GlideUtils.loadNormalImage(mContext,item.getProduct_pic(),holder.mIvGoodImage,R.mipmap.ic_placeholder_2);
    }

    private View createTargetCouponView(int imageRes) {
        ImageView view = new ImageView(mContext);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        view.setImageResource(imageRes);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        view.setLayoutParams(params);
        return view;
    }

    private View createCommonImageView(String txt, String radiusColor, String bgStartColor, String bgEndColor, String txtColor) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_common_coupon, null);

        LhhCouponView mCouponView = view.findViewById(R.id.couponView);
        TextView mTvCouponTxt1 = view.findViewById(R.id.tv_coupon_txt_1);
        SuperButton mBtnCoupon1 = view.findViewById(R.id.btn_coupon_1);

        try {
            GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{
                    Color.parseColor(bgStartColor),
                    Color.parseColor(bgEndColor)
            });
            mCouponView.setBackground(gd);
            mCouponView.setSemicircleColor(Color.parseColor(radiusColor));

            mBtnCoupon1.setTextColor(Color.parseColor(txtColor));
            mBtnCoupon1.setClickable(false);

        } catch (Exception e) {
            e.printStackTrace();
        }

        mTvCouponTxt1.setText(txt);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        view.setLayoutParams(params);

        return view;
    }


    public class ViewHolder extends AbsHolder {
        private FrameLayout mFlContainerImage;
        private ImageView mIvGoodImage;
        private TextView mTvGoodTitle;
        private TextView mTvGoodIntegral;

        private LinearLayout mLlRootview;

        public ViewHolder(View view) {
            super(view);
            mLlRootview = findViewById(R.id.ll_rootview);
            mFlContainerImage = findViewById(R.id.fl_container_image);
            mIvGoodImage = findViewById(R.id.iv_good_image);
            mTvGoodTitle = findViewById(R.id.tv_good_title);
            mTvGoodIntegral = findViewById(R.id.tv_good_integral);

        }
    }
}
