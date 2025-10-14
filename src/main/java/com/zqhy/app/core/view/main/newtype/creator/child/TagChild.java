package com.zqhy.app.core.view.main.newtype.creator.child;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zqhy.app.App;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.compat.ColorCompat;
import com.zqhy.app.utils.compat.ResCompat;
import com.zqhy.app.widget.expand.DensityUtils;

/**
 * @author leeham2734
 * @date 2021/3/25-17:17
 * @description
 */
public class TagChild {
    public float cornerRadius;
    public TagChild(){
        cornerRadius= DensityUtils.dp2px(App.instance(),5);
    }

    /**
     * 创建大标签
     * 文字，文字颜色，标签（开始颜色，结束颜色）
     * **/
    public View createTicketTag(String text, String textColor, String startColor, String endColor){
        float cornerRadius=DensityUtils.dp2px(App.instance(),12);
        View tag= LayoutInflater.from(App.instance()).inflate(R.layout.item_game_main_tag_ticket,null);
        TextView tagText=tag.findViewById(R.id.tag_text);
        tagText.setText(text);
        tagText.setTextColor(Color.parseColor(textColor));
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        drawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        drawable.setColors(new int[]{Color.parseColor(startColor),Color.parseColor(endColor)});
        drawable.setCornerRadii(new float[]{cornerRadius,cornerRadius,cornerRadius,cornerRadius,cornerRadius,cornerRadius,cornerRadius,cornerRadius});
        tagText.setBackground(drawable);
        return tag;
    }

    /**
     * 创建大标签
     * 文字，文字颜色，标签（开始颜色，结束颜色）
     * **/
    public View createBigTag(String text,String textColor,String startColor,String endColor){
        float two=DensityUtils.dp2px(App.instance(),2);
        View tag=LayoutInflater.from(App.instance()).inflate(R.layout.item_game_main_tag_big,null);
        TextView tagText=tag.findViewById(R.id.tag_text);
        tagText.setText(text);
        tagText.setTextColor(Color.parseColor(textColor));
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        drawable.setGradientRadius(90);
        drawable.setColors(new int[]{Color.parseColor(startColor),Color.parseColor(endColor)});
        drawable.setCornerRadii(new float[]{two, two, two, two, two, two, two, two});
        tagText.setBackground(drawable);
        return tag;
    }

    /**
     * 创建大标签
     * 文字，文字颜色，标签（开始颜色，结束颜色）
     * **/
    public void setBigTag(TextView tagText,String text,String textColor,String startColor,String endColor){
        tagText.setText(text);
        tagText.setTextColor(Color.parseColor(textColor));
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        drawable.setGradientRadius(90);
        drawable.setColors(new int[]{Color.parseColor(startColor),Color.parseColor(endColor)});
        drawable.setCornerRadii(new float[]{0, 0, cornerRadius, cornerRadius, 0, 0, cornerRadius, cornerRadius});
        tagText.setBackground(drawable);
    }
    /**
     * 创建小标签
     * 文字内容
     * **/
    public View createBtTag(String text,String textColor,String startColor,String endColor){
        View tag=LayoutInflater.from(App.instance()).inflate(R.layout.item_game_main_tag_small,null);
        TextView tagText=tag.findViewById(R.id.tag_text);
        tagText.setText(text);
        tagText.setTextColor(ColorCompat.parseColor(textColor,R.color.color_ff8f19));
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        drawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        drawable.setColors(new int[]{Color.parseColor(startColor),Color.parseColor(endColor)});
        drawable.setCornerRadii(new float[]{0, 0,cornerRadius*2,cornerRadius*2,cornerRadius*2,cornerRadius*2,cornerRadius*2,cornerRadius*2});
        tagText.setBackground(drawable);
        return tag;
    }

    /**
     * 创建小标签
     * 文字内容
     * **/
    public View createSmallTag(String text,String mainColor){
        View tag=LayoutInflater.from(App.instance()).inflate(R.layout.item_game_main_tag_small,null);
        TextView tagText=tag.findViewById(R.id.tag_text);
        tagText.setText(text);
        tagText.setTextColor(ColorCompat.parseColor(mainColor,R.color.color_ff8f19));
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        drawable.setGradientRadius(90);
        drawable.setStroke(DensityUtils.dp2px(App.instance(),1), ColorCompat.parseColor(mainColor,R.color.color_ff8f19));
        drawable.setColors(new int[]{ResCompat.getColor(R.color.white),ResCompat.getColor(R.color.white)});
        drawable.setCornerRadii(new float[]{0, 0,cornerRadius*2,cornerRadius*2,cornerRadius*2,cornerRadius*2,cornerRadius*2,cornerRadius*2});
        tagText.setBackground(drawable);
        return tag;
    }

    /**
     * 创建小标签etc
     * 文字内容
     * **/
    public View createSmallTagSet(String text){
        View tag=LayoutInflater.from(App.instance()).inflate(R.layout.item_game_main_tag_small,null);
        TextView tagText=tag.findViewById(R.id.tag_text);
        tagText.setText(text);
        tagText.setTextColor(ResCompat.getColor(R.color.color_ff4e00));
        tagText.setBackgroundResource(R.drawable.a_test_bg5);
        return tag;
    }
}
