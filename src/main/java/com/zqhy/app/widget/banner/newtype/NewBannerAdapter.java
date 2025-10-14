package com.zqhy.app.widget.banner.newtype;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chaoji.im.glide.GlideApp;
import com.google.android.flexbox.FlexboxLayout;
import com.zqhy.app.App;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.main.newtype.creator.child.TagChild;
import com.zqhy.app.core.vm.main.data.MainPageData;
import com.zqhy.app.glide.GlideRoundTransformNew;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.widget.expand.DensityUtils;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/7
 */

public class NewBannerAdapter extends PagerAdapter {

    private List<MainPageData.BannerData> dataList;
    private int                           pointSize;
    private ViewPagerOnItemClickListener  mViewPagerOnItemClickListener;
    private boolean                       showFlag=false;
    void setViewPagerOnItemClickListener(ViewPagerOnItemClickListener mViewPagerOnItemClickListener) {
        this.mViewPagerOnItemClickListener = mViewPagerOnItemClickListener;
    }

    NewBannerAdapter(List<MainPageData.BannerData> list, int pointSize) {
        this.dataList = list;
        this.pointSize = pointSize;
    }

    NewBannerAdapter(boolean showFlag, List<MainPageData.BannerData> list, int pointSize) {
        this.showFlag=showFlag;
        this.dataList = list;
        this.pointSize = pointSize;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
        return arg0 == arg1;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //对ViewPager页号求模取出View列表中要显示的项
        position %= dataList.size();
        if (position < 0) {
            position = dataList.size() + position;
        }
        View view=LayoutInflater.from(container.getContext()).inflate(R.layout.item_new_main_banner_game,null,false);
        initView(view,dataList.get(position),position);
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp = view.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        try{
            View view = (View) object;
            container.removeView(view);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    interface ViewPagerOnItemClickListener {
        void onItemClick(int position);
    }

    private void initView(View view, MainPageData.BannerData data, int finalPosition){
        ImageView topBg=view.findViewById(R.id.top_bg);
        ImageView gameIcon=view.findViewById(R.id.game_icon);
        FlexboxLayout bigFlow=view.findViewById(R.id.big_tag_flow);
        TextView type=view.findViewById(R.id.game_type);
        TextView gameName=view.findViewById(R.id.game_name);
        TextView gameSever=view.findViewById(R.id.game_sever);
        ViewGroup btnCheck=view.findViewById(R.id.game_item);
        //Glide.clear(topBg);
        GlideApp.with(view.getContext()).asBitmap()
                .load(data.pic)
                .centerCrop()
                .transform(new GlideRoundTransformNew(view.getContext(),5))
                .placeholder(R.mipmap.img_placeholder_v_2)
                .into(topBg);
        if(showFlag){
            btnCheck.setVisibility(View.VISIBLE);
            GlideUtils.loadRoundImage(view.getContext(),data.gameicon,gameIcon);
            int temp=0;
            int mod=21;
            if(data.colorLabels!=null&&data.colorLabels.size()>0){
                temp=data.colorLabels.size();
                mod+=data.colorLabels.size()*3;
            }
            gameName.setMaxWidth(ScreenUtil.dp2px(view.getContext(),170-temp*mod));
            gameName.setText(data.gamename);
            type.setText(data.genre_str+" • ");
            gameSever.setText(data.getTime());
            initFlexBox(bigFlow,data);
        }else{
            btnCheck.setVisibility(View.GONE);
        }
        topBg.setOnClickListener(v -> {
            if (mViewPagerOnItemClickListener != null) {
                if (pointSize == 2) {
                    mViewPagerOnItemClickListener.onItemClick(finalPosition>=2?finalPosition-2:finalPosition);
                } else {
                    mViewPagerOnItemClickListener.onItemClick(finalPosition);
                }
            }
        });
        btnCheck.setOnClickListener(v -> {
            if (mViewPagerOnItemClickListener != null) {
                if (pointSize == 2) {
                    mViewPagerOnItemClickListener.onItemClick(finalPosition>=2?finalPosition-2:finalPosition);
                } else {
                    mViewPagerOnItemClickListener.onItemClick(finalPosition);
                }
            }
        });
    }

    private void initFlexBox(FlexboxLayout big, MainPageData.BannerData itemData){
        TagChild child=new TagChild();
        if(itemData.colorLabels!=null&&itemData.colorLabels.size()>0){
            big.removeAllViews();
            for (int i = 0; i <itemData.colorLabels.size(); i++) {
                MainPageData.GameItemData.ColorLabel item=itemData.colorLabels.get(i);
                View itemView = child.createBigTag(item.label_name,item.textColor,item.beginColor,item.endColor);
                FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(DensityUtils.dp2px(App.instance(),1), DensityUtils.dp2px(App.instance(),1),
                        DensityUtils.dp2px(App.instance(),1), DensityUtils.dp2px(App.instance(),1));
                big.addView(itemView, params);
            }
        }else{
            big.removeAllViews();
            big.setVisibility(View.GONE);
        }
    }
}
