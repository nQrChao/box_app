package com.zqhy.app.core.view.game.forum.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.LineHeightSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.box.common.glide.GlideApp;
import com.donkingliang.imageselector.PreviewActivity;
import com.donkingliang.imageselector.entry.Image;
import com.google.android.material.imageview.ShapeableImageView;
import com.youth.banner2.Banner;
import com.youth.banner2.adapter.BannerImageAdapter;
import com.youth.banner2.holder.BannerImageHolder;
import com.youth.banner2.indicator.CircleIndicator;
import com.youth.banner2.listener.OnPageChangeListener;
import com.youth.banner2.util.BannerUtils;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.forum.ForumDetailTitleVo;
import com.zqhy.app.core.data.model.forum.ForumDetailVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.game.forum.tool.ClickableTextView;
import com.zqhy.app.core.view.game.forum.tool.GlideImageGetter;
import com.zqhy.app.core.view.transaction.util.CustomPopWindow;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * @author Administrator
 * @date 2018/11/24
 */

public class DetailTitle2View {
    Context mContext;
    BaseFragment _mFragment;
    Clickable clickable ;

    public void setClickable(Clickable clickable) {
        this.clickable = clickable;
    }

    public interface Clickable{
        void onlyOwner(int i);
        void order(String order_type);

        void openEdit();
    }

    public DetailTitle2View(Context context,BaseFragment mFragment,ForumDetailTitleVo item) {
        this.mContext = context;
        this._mFragment = mFragment;
        createViewHolder();
        onBindViewHolder(item);
    }
    public View getView(){
        return view;
    }
    boolean only = false;
    int  mPosition = 1;
    protected void onBindViewHolder( ForumDetailTitleVo item) {
        ForumDetailVo data = item.getData();
        if (data.getTitle().isEmpty()) {
            tv_title.setVisibility(GONE);
        }else {
            tv_title.setVisibility(VISIBLE);
            tv_title.setText(data.getTitle());
        }
        tv_reply_count.setText("共"+data.getReply_count()+"条评论");
        switch (selected) {
            case 1:
                tv_sort.setText("最新");
                break;
              case 2:
                tv_sort.setText("最热");
                break;
              case 3:
                tv_sort.setText("最早");
                break;

        }

        tv_sort.setOnClickListener(v -> {
            showPopListView(tv_sort, R.layout.pop_forum_order_type_select);
        });

        tv_open_edit.setOnClickListener(v -> {
            if (clickable!=null){
                clickable.openEdit();
            }
        });
        tv_only_owner.setOnClickListener(v -> {
            only = !only;
            if (only){
                tv_only_owner.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_item_forum_detail_title_1_t), null, null, null);
            }else {
                tv_only_owner.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_item_forum_detail_title_1), null, null, null);
            }
            if (clickable!=null) {
                if (only){
                    clickable.onlyOwner(1);
                }else {
                    clickable.onlyOwner(0);
                }
            }
        });
        if (UserInfoModel.getInstance().isLogined()){
            GlideApp.with(mContext)
                    .load(UserInfoModel.getInstance().getUserInfo().getUser_icon())
                    .centerCrop()
                    .placeholder(R.mipmap.ic_user_login_new_sign)
                    .into(iv_icon);
        }else {
            GlideApp.with(mContext)
                    .load(R.mipmap.ic_user_login_new_sign)
                    .centerCrop()
                    .placeholder(R.mipmap.ic_user_login_new_sign)
                    .into(iv_icon);
        }
        if (data.getContent().isEmpty()) {
            tv_content.setVisibility(View.GONE);
        }else {
            tips.setText(data.getTime_description() + " · " + data.getView_count() + "浏览");
            GlideImageGetter imageGetter = new GlideImageGetter(mContext, tv_content);
            Spanned spanned = Html.fromHtml(data.getContent(), imageGetter, null);
            // 使用自定义的 ImageGetter 解析 HTML 内容
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(spanned);
            int length = spannableStringBuilder.length();
            while (length > 0 && spannableStringBuilder.charAt(length - 1) == '\n') {
                spannableStringBuilder.delete(length - 1, length);
                length = spannableStringBuilder.length();
            }

            // 定义固定行高，单位为像素
            int fixedLineHeight = ScreenUtil.dp2px(mContext, 22);
            // 创建自定义的 LineHeightSpan
            FixedLineHeightSpan lineHeightSpan = new FixedLineHeightSpan(fixedLineHeight);
            // 将自定义的 LineHeightSpan 应用到 SpannableString 中
            spannableStringBuilder.setSpan(lineHeightSpan, 0, spannableStringBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


            tv_content.setText(spannableStringBuilder);
        }
        if (data.getPic() != null && !data.getPic().isEmpty()){
            rl_banner.setVisibility(View.VISIBLE);
            if (data.getPic().size()<=1) {
                indicator.setVisibility(View.GONE);
            }

            tv_banner.setText(mPosition+"/"+data.getPic().size());
            //设置图片加载器
            banner.addOnPageChangeListener(new OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    mPosition = position+1;
                    tv_banner.setText(mPosition+"/"+data.getPic().size());
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            banner.setAdapter(new BannerImageAdapter<String>(data.getPic()) {
                @Override
                public void onBindView(BannerImageHolder holder, String a, int position, int size) {
                    holder.imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    GlideApp.with(mContext)
                            .load(a)
                            .placeholder(R.mipmap.img_placeholder_h)
                            .fitCenter()
                            .into( holder.imageView);
                    holder.imageView.setOnClickListener(view -> {
                        ArrayList<Image> images = new ArrayList();
                        for (String s : data.getPic()) {
                            Image image = new Image();
                            image.setType(1);
                            image.setPath(s);
                            images.add(image);
                        }
                        PreviewActivity.openActivity(_mFragment.getActivity(), images, true, position, true,true);
                    });
                }
            });
            banner.setIndicator(indicator, false)
                    .setIndicatorNormalColor(Color.parseColor("#CCCCCC"))
                    .setIndicatorSelectedColor(Color.parseColor("#5571FE"))
                    .setIndicatorSelectedWidth(BannerUtils.dp2px(6))
                    .setIndicatorNormalWidth(BannerUtils.dp2px(6))
                    .isAutoLoop(false);
        }else {
            rl_banner.setVisibility(View.GONE);
        }
    }

    // 自定义 LineHeightSpan 类来实现固定行高
    private static class FixedLineHeightSpan implements LineHeightSpan {
        private final int fixedHeight;

        public FixedLineHeightSpan(int fixedHeight) {
            this.fixedHeight = fixedHeight;
        }

        @Override
        public void chooseHeight(CharSequence text, int start, int end, int spanstartv, int v, Paint.FontMetricsInt fm) {
            int currentHeight = fm.descent - fm.ascent;
            if (currentHeight < fixedHeight) {
                int need = fixedHeight - currentHeight;
                // 平均分配需要增加的高度到 ascent 和 descent
                fm.descent += need / 2;
                fm.ascent -= need - need / 2;
            }
        }
    }

    private TextView tv_title;
    private TextView tv_reply_count;
    private TextView tips;
    private TextView tv_banner;
    private TextView tv_sort;
    private Banner banner;
    private RelativeLayout rl_banner;
    private TextView tv_only_owner;
    private TextView tv_open_edit;
    private ClickableTextView tv_content;
    private ShapeableImageView iv_icon;
    private CircleIndicator indicator;
    View view;
    public void createViewHolder() {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_forum_detail_title2,null,false);
        rl_banner = view.findViewById(R.id.rl_banner);
        banner = view.findViewById(R.id.banner);
        tv_banner = view.findViewById(R.id.tv_banner);
        indicator = view.findViewById(R.id.indicator);
        tv_open_edit = view.findViewById(R.id.tv_open_edit);
        tv_only_owner = view.findViewById(R.id.tv_only_owner);
        iv_icon = view.findViewById(R.id.iv_icon);
        tv_title = view.findViewById(R.id.tv_title);
        tv_sort = view.findViewById(R.id.tv_sort);
        tv_reply_count = view.findViewById(R.id.tv_reply_count);
        tips = view.findViewById(R.id.tips);
        tv_content = view.findViewById(R.id.tv_content);
    }

    private TextView tv_item1;
    private TextView tv_item2;
    private TextView tv_item3;
    int selected = 3;
    private void showPopListView(TextView v, int resource) {
        if (resource == 0) return;
        View contentView = LayoutInflater.from(mContext).inflate(resource, null);
        tv_item1 = contentView.findViewById(R.id.tv_item1);
        tv_item2 = contentView.findViewById(R.id.tv_item2);
        tv_item3 = contentView.findViewById(R.id.tv_item3);
        tv_item1.setText("最新");
        tv_item2.setText("最热");
        tv_item3.setText("最早");

        switch (selected) {
            case 1:
                tv_item1.setTextColor(Color.parseColor("#5571FE"));
                tv_item2.setTextColor(Color.parseColor("#333333"));
                tv_item3.setTextColor(Color.parseColor("#333333"));
                break;
            case 2:
                tv_item1.setTextColor(Color.parseColor("#333333"));
                tv_item2.setTextColor(Color.parseColor("#5571FE"));
                tv_item3.setTextColor(Color.parseColor("#333333"));
                break;
            case 3:
                tv_item1.setTextColor(Color.parseColor("#333333"));
                tv_item2.setTextColor(Color.parseColor("#333333"));
                tv_item3.setTextColor(Color.parseColor("#5571FE"));
                break;

        }
        CustomPopWindow popWindow = new CustomPopWindow.PopupWindowBuilder(_mFragment.getActivity())
                .setView(contentView)
                .setFocusable(true)
                .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT) //显示大小
                .setOutsideTouchable(true)
                .create();
//        popWindow.setBackgroundAlpha(0.7f);
        tv_item1.setOnClickListener(view -> {
            selected = 1;
            v.setText("最新");//newest：最新; hottest:最热; earliest:最早
            if (clickable!=null){
                clickable.order("newest");
            }
            popWindow.dissmiss();
        });
        tv_item2.setOnClickListener(view -> {
            selected = 2;
            v.setText("最热");
            if (clickable!=null){
                clickable.order("hottest");
            }
            popWindow.dissmiss();
        });
        tv_item3.setOnClickListener(view -> {
            selected = 3;
            v.setText("最早");
            if (clickable!=null){
                clickable.order("earliest");
            }
            popWindow.dissmiss();
        });

        popWindow.showAsDropDown(v, (-v.getWidth() / 2) + 20, 0);//指定view正下方
    }
}
