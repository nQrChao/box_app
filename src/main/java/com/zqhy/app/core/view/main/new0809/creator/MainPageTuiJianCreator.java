package com.zqhy.app.core.view.main.new0809.creator;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.shizhefei.view.indicator.BannerComponent;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.zqhy.app.base.BaseActivity;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.AppJumpAction;
import com.zqhy.app.core.data.model.banner.BannerVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.new0809.MainCommonDataVo;
import com.zqhy.app.core.data.model.game.new0809.MainFuliStyle1Vo;
import com.zqhy.app.core.data.model.game.new0809.MainTuiJianDataVo;
import com.zqhy.app.core.data.model.game.new0809.item.LunboDataBeanVo;
import com.zqhy.app.core.data.model.game.new0809.item.MainMenuVo;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.main.MainActivity;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.utils.LifeUtil;
import com.zqhy.app.widget.StartSnapHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author leeham2734
 * @date 2021/8/17-10:29
 * @description
 */
public class MainPageTuiJianCreator {
    private BaseFragment fragment;
    private BaseActivity activity;
    private Context      mContext;

    public MainPageTuiJianCreator(BaseFragment fragment, BaseActivity activity) {
        this.activity = activity;
        this.fragment = fragment;
        mContext = activity.getApplicationContext();
    }

    public void initPage(LinearLayout parent, MainCommonDataVo.DataBean data) {
        if (!LifeUtil.isAlive(activity) || fragment == null) {
            return;
        }
        parent.removeAllViews();
        List<MainTuiJianDataVo.ModuleStyle> modules = data.module_style;
        List<String> apiModule = data.getApiModule();

        if (modules != null && modules.size() > 0 && data.data != null) {
            for (int i = 0; i < modules.size(); i++) {
                MainTuiJianDataVo.ModuleStyle item = modules.get(i);
                if (TextUtils.isEmpty(item.style)) {
                    item.style = "A";
                }
                String key = item.api;
                String api = apiModule.get(i);
                boolean isFirst = i == 0;

                if ("tj_menu".equals(api)) {
                    MainMenuVo tj_menu = data.getItemData(MainMenuVo.class, key);
                    if (tj_menu != null && checkCollectionNotEmpty(tj_menu.data)) {
                        parent.addView(initMenu(tj_menu, isFirst));
                    }
                } else if ("tj_lunbo".equals(api)) {
                    LunboDataBeanVo tj_lunbo = data.getItemData(LunboDataBeanVo.class, key);
                    if (tj_lunbo != null && checkCollectionNotEmpty(tj_lunbo.data)) {
                        if (MainTuiJianDataVo.ModuleStyle.TYPE_A.equalsIgnoreCase(item.style)) {
                            parent.addView(initBannerA(tj_lunbo, isFirst));
                        } else if (MainTuiJianDataVo.ModuleStyle.TYPE_B.equalsIgnoreCase(item.style)) {
                            parent.addView(initBannerB(tj_lunbo, isFirst));
                        } else if (MainTuiJianDataVo.ModuleStyle.TYPE_C.equalsIgnoreCase(item.style)) {
                            parent.addView(initBannerC(tj_lunbo, isFirst));
                        }
                    }
                } else if ("tj_remenyouxi".equals(api)) {
                    MainTuiJianDataVo.CommonGameDataBeanVo tj_remenyouxi = data.getItemData(MainTuiJianDataVo.CommonGameDataBeanVo.class, key);
                    if (tj_remenyouxi != null && checkCollectionNotEmpty(tj_remenyouxi.data)) {
                        parent.addView(initReMenYouXi(tj_remenyouxi, item.style, isFirst));
                    }
                } else if ("tj_mianfei".equals(api)) {
                    MainTuiJianDataVo.CommonGameDataBeanVo tj_mianfei = data.getItemData(MainTuiJianDataVo.CommonGameDataBeanVo.class, key);
                    if (tj_mianfei != null && checkCollectionNotEmpty(tj_mianfei.data)) {
                        if (MainTuiJianDataVo.ModuleStyle.TYPE_A.equalsIgnoreCase(item.style)) {
                            parent.addView(initMainFeiA(tj_mianfei, isFirst));
                        } else if (MainTuiJianDataVo.ModuleStyle.TYPE_B.equalsIgnoreCase(item.style)) {
                            parent.addView(initMainFeiB(tj_mianfei, isFirst));
                        } else if (MainTuiJianDataVo.ModuleStyle.TYPE_C.equalsIgnoreCase(item.style)) {
                            parent.addView(initMainFeiC(tj_mianfei, isFirst));
                        }
                    }
                } else if ("tj_tab".equals(api)) {
                    MainTuiJianDataVo.TabGameDataBeanVo tj_tab = data.getItemData(MainTuiJianDataVo.TabGameDataBeanVo.class, key);
                    if (tj_tab != null && checkCollectionNotEmpty(tj_tab.data)) {
                        parent.addView(initTabView(item.style, tj_tab.data, isFirst));
                    }
                }
                /*switch (api) {
                    case "tj_menu":
                        if (data.data.tj_menu != null && checkCollectionNotEmpty(data.data.tj_menu.data)) {
                            parent.addView(initMenu(data.data.tj_menu, isFirst));
                        }
                        break;
                    case "tj_lunbo":
                        if (data.data.tj_lunbo != null && checkCollectionNotEmpty(data.data.tj_lunbo.data)) {
                            if (MainTuiJianDataVo.ModuleStyle.TYPE_A.equalsIgnoreCase(item.style)) {
                                parent.addView(initBannerA(data.data.tj_lunbo, isFirst));
                            } else if (MainTuiJianDataVo.ModuleStyle.TYPE_B.equalsIgnoreCase(item.style)) {
                                parent.addView(initBannerB(data.data.tj_lunbo, isFirst));
                            } else if (MainTuiJianDataVo.ModuleStyle.TYPE_C.equalsIgnoreCase(item.style)) {
                                parent.addView(initBannerC(data.data.tj_lunbo, isFirst));
                            }
                        }
                        break;
                    case "tj_remenyouxi":
                        if (data.data.tj_remenyouxi != null && checkCollectionNotEmpty(data.data.tj_remenyouxi.data)) {
                            parent.addView(initReMenYouXi(data.data.tj_remenyouxi, item.style, isFirst));
                        }
                        break;
                    case "tj_mianfei":
                        if (data.data.tj_mianfei != null && checkCollectionNotEmpty(data.data.tj_mianfei.data)) {
                            if (MainTuiJianDataVo.ModuleStyle.TYPE_A.equalsIgnoreCase(item.style)) {
                                parent.addView(initMainFeiA(data.data.tj_mianfei, isFirst));
                            } else if (MainTuiJianDataVo.ModuleStyle.TYPE_B.equalsIgnoreCase(item.style)) {
                                parent.addView(initMainFeiB(data.data.tj_mianfei, isFirst));
                            } else if (MainTuiJianDataVo.ModuleStyle.TYPE_C.equalsIgnoreCase(item.style)) {
                                parent.addView(initMainFeiC(data.data.tj_mianfei, isFirst));
                            }
                        }
                        break;
                    case "tj_tab":
                        if (data.data.tj_tab != null && checkCollectionNotEmpty(data.data.tj_tab.data)) {
                            parent.addView(initTabView(item.style, data.data.tj_tab.data, isFirst));
                        }
                        break;
                }*/
            }
            parent.addView(initMoreGame());
        }
    }

    private View initTabView(String style, List<MainFuliStyle1Vo> item, boolean isFirst) {
        View parent = LayoutInflater.from(activity).inflate(R.layout.item_main_tj_tab, null);
        LinearLayout mLlTabContainer = parent.findViewById(R.id.ll_tab_container);
        RecyclerView mRecyclerView = parent.findViewById(R.id.recycler_view);


        Map<Integer, View> viewMap = new HashMap<>();
        View lastItemView = null;
        TabInfoListVo infoListVo = new TabInfoListVo();
        for (int i = 0; i < item.size(); i++) {
            for (MainFuliStyle1Vo.DataBean itemBean : item.get(i).data_list) {
                itemBean.style = style;
            }
            infoListVo.addItem(new TabInfoVo(i, item.get(i).title, item.get(i).data_list));
        }

        mLlTabContainer.removeAllViews();
        for (int i = 0; i < infoListVo.data_list.size(); i++) {
            TabInfoVo tabInfoVo = infoListVo.data_list.get(i);
            View itemView = createTabViewStyle(style, tabInfoVo, viewMap);
            if (itemView != null) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) itemView.getLayoutParams();
                if (params == null) {
                    params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                }
                if (tabInfoVo.id == infoListVo.getLastIndexItemGameId()) {
                    lastItemView = itemView;
                }
                if (MainTuiJianDataVo.ModuleStyle.TYPE_A.equalsIgnoreCase(style)) {
                } else if (MainTuiJianDataVo.ModuleStyle.TYPE_B.equalsIgnoreCase(style)) {
                    params.rightMargin = ScreenUtil.dp2px(activity, 16);
                    params.gravity = Gravity.CENTER_VERTICAL;
                } else if (MainTuiJianDataVo.ModuleStyle.TYPE_C.equalsIgnoreCase(style)) {
                }
                mLlTabContainer.addView(itemView, params);
            }
        }

        if (MainTuiJianDataVo.ModuleStyle.TYPE_A.equalsIgnoreCase(style)) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mLlTabContainer.getLayoutParams();
            if (params != null) {
                params.gravity = Gravity.CENTER;
                mLlTabContainer.setLayoutParams(params);
            }
        } else if (MainTuiJianDataVo.ModuleStyle.TYPE_B.equalsIgnoreCase(style)) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mLlTabContainer.getLayoutParams();
            if (params != null) {
                params.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
                params.leftMargin = ScreenUtil.dp2px(activity, 16);
                mLlTabContainer.setLayoutParams(params);
            }
        } else if (MainTuiJianDataVo.ModuleStyle.TYPE_C.equalsIgnoreCase(style)) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mLlTabContainer.getLayoutParams();
            if (params != null) {
                params.gravity = Gravity.CENTER;
                mLlTabContainer.setLayoutParams(params);
            }
        }

        if (MainTuiJianDataVo.ModuleStyle.TYPE_A.equalsIgnoreCase(style)) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        } else if (MainTuiJianDataVo.ModuleStyle.TYPE_B.equalsIgnoreCase(style)) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        } else if (MainTuiJianDataVo.ModuleStyle.TYPE_C.equalsIgnoreCase(style)) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        }
        mRecyclerView.setNestedScrollingEnabled(false);
        GameAdapter mGameAdapter = new GameAdapter(null);
        mRecyclerView.setAdapter(mGameAdapter);

        for (Integer id : viewMap.keySet()) {
            View target = viewMap.get(id);
            target.setOnClickListener(v -> {
                infoListVo.setLastIndexItemGameId(id);
                onHeaderTabClick(style, v, viewMap, mGameAdapter);
            });
        }
        if (lastItemView != null) {
            lastItemView.performClick();
        }
        setCommonLayoutParams(parent, isFirst);
        return parent;
    }

    private class GameAdapter extends RecyclerView.Adapter {

        private final int viewTypeA = 1;
        private final int viewTypeB = 2;
        private final int viewTypeC = 3;

        private List<MainFuliStyle1Vo.DataBean> data_list;

        public GameAdapter(List<MainFuliStyle1Vo.DataBean> data_list) {
            this.data_list = data_list;
        }


        public void refreshList(List<MainFuliStyle1Vo.DataBean> data_list) {
            if (this.data_list == null) {
                this.data_list = new ArrayList<>();
            }
            this.data_list.clear();
            this.data_list.addAll(data_list);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == viewTypeA) {
                return new ViewHolderA(LayoutInflater.from(activity).inflate(R.layout.item_main_tj_tab_item_a, parent, false));
            } else if (viewType == viewTypeB) {
                return new ViewHolderB(LayoutInflater.from(activity).inflate(R.layout.item_main_tj_tab_item_b, parent, false));
            } else if (viewType == viewTypeC) {
                return new ViewHolderC(LayoutInflater.from(activity).inflate(R.layout.item_main_tj_tab_item_c, parent, false));
            }
            return null;
        }

        @Override
        public int getItemViewType(int position) {
            MainFuliStyle1Vo.DataBean item = data_list.get(position);
            if (MainTuiJianDataVo.ModuleStyle.TYPE_A.equalsIgnoreCase(item.style)) {
                return viewTypeA;
            } else if (MainTuiJianDataVo.ModuleStyle.TYPE_B.equalsIgnoreCase(item.style)) {
                return viewTypeB;
            } else if (MainTuiJianDataVo.ModuleStyle.TYPE_C.equalsIgnoreCase(item.style)) {
                return viewTypeC;
            }
            return super.getItemViewType(position);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
            MainFuliStyle1Vo.DataBean item = data_list.get(position);
            String style = item.style;
            if (MainTuiJianDataVo.ModuleStyle.TYPE_A.equalsIgnoreCase(style)) {
                ViewHolderA holder = (ViewHolderA) viewHolder;
                GlideUtils.loadRoundImage(mContext, item.getGameicon(), holder.mGameIconIV, R.mipmap.ic_placeholder);
                holder.mTvGameName.setText(item.getGamename());
                holder.mTvGameFirstTag.setVisibility(item.getIs_first() == 1 ? View.VISIBLE : View.GONE);
                holder.mTvInfoMiddle.setText(item.getGenre_str() + "  " + CommonUtils.formatNumber(item.getPlay_count()) + "人在玩");

                boolean hasLineTag = false;
                holder.mFlexBoxLayout.removeAllViews();
                holder.mFlexBoxLayout.setVisibility(View.VISIBLE);
                holder.mTvInfoBottom.setVisibility(View.GONE);
                int tagSize = 2;
                if (item.getGame_labels() != null && !item.getGame_labels().isEmpty()) {
                    List<GameInfoVo.GameLabelsBean> list = item.getGame_labels().size() > tagSize ? item.getGame_labels().subList(0, tagSize) : item.getGame_labels();
                    for (GameInfoVo.GameLabelsBean labelsBean : list) {
                        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                        params.rightMargin = (int) (4 * ScreenUtil.getScreenDensity(mContext));
                        params.topMargin = (int) (4 * ScreenUtil.getScreenDensity(mContext));
                        holder.mFlexBoxLayout.addView(createLabelViewA(labelsBean), params);
                    }
                    hasLineTag = true;
                } else {
                    holder.mTvInfoBottom.setVisibility(View.VISIBLE);
                    holder.mTvInfoBottom.setText(item.getGame_summary());
                }
                if (!hasLineTag) {
                    holder.mFlexBoxLayout.setVisibility(View.GONE);
                }
            } else if (MainTuiJianDataVo.ModuleStyle.TYPE_B.equalsIgnoreCase(style)) {
                ViewHolderB holder = (ViewHolderB) viewHolder;
                GlideUtils.loadRoundImage(mContext, item.getGameicon(), holder.mGameIconIV, R.mipmap.ic_placeholder);
                holder.mTvGameName.setText(item.getGamename());
                holder.mTvInfoMiddle.setText(item.getGenre_str() + "  " + CommonUtils.formatNumber(item.getPlay_count()) + "人在玩");

                boolean hasLineTag = false;
                holder.mFlexBoxLayout.removeAllViews();
                holder.mFlexBoxLayout.setVisibility(View.VISIBLE);
                holder.mTvInfoBottom.setVisibility(View.GONE);
                int tagSize = 2;
                if (item.getGame_labels() != null && !item.getGame_labels().isEmpty()) {
                    List<GameInfoVo.GameLabelsBean> list = item.getGame_labels().size() > tagSize ? item.getGame_labels().subList(0, tagSize) : item.getGame_labels();
                    for (GameInfoVo.GameLabelsBean labelsBean : list) {
                        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                        params.rightMargin = (int) (4 * ScreenUtil.getScreenDensity(mContext));
                        params.topMargin = (int) (4 * ScreenUtil.getScreenDensity(mContext));
                        holder.mFlexBoxLayout.addView(createLabelViewB(labelsBean), params);
                    }
                    hasLineTag = true;
                } else {
                    holder.mTvInfoBottom.setVisibility(View.VISIBLE);
                    holder.mTvInfoBottom.setText(item.getGame_summary());
                }
                if (!hasLineTag) {
                    holder.mFlexBoxLayout.setVisibility(View.GONE);
                }
            } else if (MainTuiJianDataVo.ModuleStyle.TYPE_C.equalsIgnoreCase(style)) {
                ViewHolderC holder = (ViewHolderC) viewHolder;
                GlideUtils.loadRoundImage(mContext, item.getGameicon(), holder.mGameIconIV, R.mipmap.ic_placeholder);
                holder.mTvGameName.setText(item.getGamename());
                holder.mTvInfoMiddle.setText(item.getGenre_str() + "  " + CommonUtils.formatNumber(item.getPlay_count()) + "人在玩");

                boolean hasLineTag = false;
                holder.mLlGameLabelContainer.removeAllViews();
                holder.mLlGameLabelContainer.setVisibility(View.VISIBLE);
                holder.mTvInfoBottom.setVisibility(View.GONE);
                int tagSize = 2;
                if (item.getGame_labels() != null && !item.getGame_labels().isEmpty()) {
                    List<GameInfoVo.GameLabelsBean> list = item.getGame_labels().size() > tagSize ? item.getGame_labels().subList(0, tagSize) : item.getGame_labels();
                    for (GameInfoVo.GameLabelsBean labelsBean : list) {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.topMargin = (int) (3 * ScreenUtil.getScreenDensity(mContext));
                        params.gravity = Gravity.CENTER;
                        holder.mLlGameLabelContainer.addView(createLabelViewC(labelsBean), params);
                    }
                    hasLineTag = true;
                } else {
                    holder.mTvInfoBottom.setVisibility(View.VISIBLE);
                    holder.mTvInfoBottom.setText(item.getGame_summary());
                }
                if (!hasLineTag) {
                    holder.mLlGameLabelContainer.setVisibility(View.GONE);
                }
            }
            viewHolder.itemView.setOnClickListener(v -> {
                if (fragment != null) {
                    fragment.goGameDetail(item.getGameid(), item.getGame_type());
                }
            });
        }

        @Override
        public int getItemCount() {
            return data_list == null ? 0 : data_list.size();
        }

        private View createLabelViewA(GameInfoVo.GameLabelsBean labelsBean) {
            TextView textView = new TextView(mContext);
            textView.setText(labelsBean.getLabel_name());
            textView.setIncludeFontPadding(false);
            try {
                textView.setTextColor(Color.parseColor(labelsBean.getText_color()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            textView.setTextSize(11.5f);
            textView.setPadding(ScreenUtil.dp2px(mContext, 4), ScreenUtil.dp2px(mContext, 2), ScreenUtil.dp2px(mContext, 4), ScreenUtil.dp2px(mContext, 2));

            return textView;
        }

        private View createLabelViewB(GameInfoVo.GameLabelsBean labelsBean) {
            TextView textView = new TextView(mContext);
            textView.setText(labelsBean.getLabel_name());
            textView.setIncludeFontPadding(false);
            try {
                textView.setTextColor(Color.parseColor(labelsBean.getText_color()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(ScreenUtil.dp2px(mContext, 4));
            try {
                gd.setColor(Color.parseColor("#F2F2F2"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            textView.setBackground(gd);
            textView.setTextSize(11f);
            textView.setPadding(ScreenUtil.dp2px(mContext, 4), ScreenUtil.dp2px(mContext, 2), ScreenUtil.dp2px(mContext, 4), ScreenUtil.dp2px(mContext, 2));

            return textView;
        }

        private View createLabelViewC(GameInfoVo.GameLabelsBean labelsBean) {
            TextView textView = new TextView(mContext);
            textView.setText(labelsBean.getLabel_name());
            textView.setIncludeFontPadding(false);
            try {
                textView.setTextColor(Color.parseColor(labelsBean.getText_color()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(ScreenUtil.dp2px(mContext, 4));
            try {
                gd.setStroke(ScreenUtil.dp2px(mContext, 1), Color.parseColor("#F2F2F2"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            textView.setBackground(gd);
            textView.setTextSize(11f);
            textView.setPadding(ScreenUtil.dp2px(mContext, 4), ScreenUtil.dp2px(mContext, 2), ScreenUtil.dp2px(mContext, 4), ScreenUtil.dp2px(mContext, 2));

            return textView;
        }

        public class ViewHolderA extends RecyclerView.ViewHolder {
            private ImageView     mGameIconIV;
            private TextView      mTvGameName;
            private TextView      mTvGameFirstTag;
            private TextView      mTvInfoMiddle;
            private FlexboxLayout mFlexBoxLayout;
            private TextView      mTvInfoBottom;

            public ViewHolderA(View itemView) {
                super(itemView);

                mGameIconIV = itemView.findViewById(R.id.gameIconIV);
                mTvGameName = itemView.findViewById(R.id.tv_game_name);
                mTvGameFirstTag = itemView.findViewById(R.id.tv_game_first_tag);
                mTvInfoMiddle = itemView.findViewById(R.id.tv_info_middle);
                mFlexBoxLayout = itemView.findViewById(R.id.flex_box_layout);
                mTvInfoBottom = itemView.findViewById(R.id.tv_info_bottom);

                GradientDrawable gdTemp1 = new GradientDrawable();
                int radius = ScreenUtil.dp2px(mContext, 6);
                gdTemp1.setColor(Color.parseColor("#F8505A"));
                gdTemp1.setCornerRadii(new float[]{0, 0, radius, radius, 0, 0, radius, radius});
                mTvGameFirstTag.setBackground(gdTemp1);
            }
        }

        public class ViewHolderB extends RecyclerView.ViewHolder {
            private ImageView     mGameIconIV;
            private TextView      mTvGameName;
            private TextView      mTvInfoMiddle;
            private FlexboxLayout mFlexBoxLayout;
            private TextView      mTvInfoBottom;

            public ViewHolderB(View itemView) {
                super(itemView);
                mGameIconIV = itemView.findViewById(R.id.gameIconIV);
                mTvGameName = itemView.findViewById(R.id.tv_game_name);
                mTvInfoMiddle = itemView.findViewById(R.id.tv_info_middle);
                mFlexBoxLayout = itemView.findViewById(R.id.flex_box_layout);
                mTvInfoBottom = itemView.findViewById(R.id.tv_info_bottom);

            }
        }

        public class ViewHolderC extends RecyclerView.ViewHolder {
            private ImageView    mGameIconIV;
            private TextView     mTvGameName;
            private TextView     mTvInfoMiddle;
            private LinearLayout mLlGameLabelContainer;
            private TextView     mTvInfoBottom;

            public ViewHolderC(View itemView) {
                super(itemView);
                mGameIconIV = itemView.findViewById(R.id.gameIconIV);
                mTvGameName = itemView.findViewById(R.id.tv_game_name);
                mTvInfoMiddle = itemView.findViewById(R.id.tv_info_middle);
                mLlGameLabelContainer = itemView.findViewById(R.id.ll_game_label_container);
                mTvInfoBottom = itemView.findViewById(R.id.tv_info_bottom);

            }
        }
    }

    private void onHeaderTabClick(String style, View view, Map<Integer, View> viewMap, GameAdapter gameAdapter) {
        for (Integer gameid : viewMap.keySet()) {
            View target = viewMap.get(gameid);
            boolean isCheck = view == target;
            if (MainTuiJianDataVo.ModuleStyle.TYPE_A.equalsIgnoreCase(style)) {
                TextView targetA = target.findViewById(R.id.tuijian_style_a_id_1);
                if (isCheck) {
                    GradientDrawable gd = new GradientDrawable();
                    gd.setCornerRadius(ScreenUtil.dp2px(mContext, 100));
                    gd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
                    gd.setColors(new int[]{Color.parseColor("#0D9EFF"), Color.parseColor("#0079FB")});
                    target.setBackground(gd);
                    targetA.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    TabInfoVo currentTabInfoVo = (TabInfoVo) view.getTag(R.id.tag_first);
                    gameAdapter.refreshList(currentTabInfoVo.data);
                } else {
                    GradientDrawable gd = new GradientDrawable();
                    gd.setCornerRadius(ScreenUtil.dp2px(mContext, 100));
                    gd.setColor(Color.parseColor("#ECF7FF"));
                    target.setBackground(gd);
                    targetA.setTextColor(Color.parseColor("#0079FB"));
                }
            } else if (MainTuiJianDataVo.ModuleStyle.TYPE_B.equalsIgnoreCase(style)) {
                TextView targetB = target.findViewById(R.id.tuijian_style_b_id_1);
                ImageView targetImageB = target.findViewById(R.id.tuijian_style_b_id_2);
                if (isCheck) {
                    targetB.setTextSize(23);
                    targetB.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    targetImageB.setVisibility(View.VISIBLE);
                    TabInfoVo currentTabInfoVo = (TabInfoVo) view.getTag(R.id.tag_first);
                    gameAdapter.refreshList(currentTabInfoVo.data);
                } else {
                    targetB.setTextSize(17);
                    targetB.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    targetImageB.setVisibility(View.INVISIBLE);
                }

            } else if (MainTuiJianDataVo.ModuleStyle.TYPE_C.equalsIgnoreCase(style)) {
                TextView targetC = target.findViewById(R.id.tuijian_style_c_id_1);
                if (isCheck) {
                    targetC.setTextSize(23);
                    targetC.setTextColor(Color.parseColor("#333333"));
                    targetC.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    TabInfoVo currentTabInfoVo = (TabInfoVo) view.getTag(R.id.tag_first);
                    gameAdapter.refreshList(currentTabInfoVo.data);
                } else {
                    targetC.setTextSize(17);
                    targetC.setTextColor(Color.parseColor("#6A6A68"));
                    targetC.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                }
            }
        }
    }

    private View createTabViewStyle(String style, TabInfoVo vo, Map<Integer, View> viewMap) {
        if (MainTuiJianDataVo.ModuleStyle.TYPE_A.equalsIgnoreCase(style)) {
            return createTabViewStyleA(vo, viewMap);
        } else if (MainTuiJianDataVo.ModuleStyle.TYPE_B.equalsIgnoreCase(style)) {
            return createTabViewStyleB(vo, viewMap);
        } else if (MainTuiJianDataVo.ModuleStyle.TYPE_C.equalsIgnoreCase(style)) {
            return createTabViewStyleC(vo, viewMap);
        }
        return null;
    }

    private View createTabViewStyleA(TabInfoVo vo, Map<Integer, View> viewMap) {
        TextView view = new TextView(mContext);
        view.setId(R.id.tuijian_style_a_id_1);
        view.setText(vo.text);
        view.setTextSize(15);
        view.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtil.dp2px(mContext, 98),
                ScreenUtil.dp2px(mContext, 35));
        params.leftMargin = ScreenUtil.dp2px(mContext, 15);
        params.rightMargin = ScreenUtil.dp2px(mContext, 15);
        view.setLayoutParams(params);
        view.setTag(R.id.tag_first, vo);
        viewMap.put(vo.id, view);
        return view;
    }

    private View createTabViewStyleB(TabInfoVo vo, Map<Integer, View> viewMap) {
        LinearLayout layout = new LinearLayout(mContext);
        layout.setOrientation(LinearLayout.VERTICAL);
        TextView view = new TextView(mContext);
        view.setId(R.id.tuijian_style_b_id_1);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.addView(view, params);
        view.setTextColor(Color.parseColor("#7266F7"));
        view.setText(vo.text);
        view.setGravity(Gravity.CENTER);

        ImageView image = new ImageView(mContext);
        image.setId(R.id.tuijian_style_b_id_2);
        image.setImageResource(R.mipmap.ic_main_tuijian_tab_b);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageParams.gravity = Gravity.CENTER;
        layout.addView(image, imageParams);

        layout.setTag(R.id.tag_first, vo);
        viewMap.put(vo.id, layout);

        return layout;
    }

    private View createTabViewStyleC(TabInfoVo vo, Map<Integer, View> viewMap) {
        TextView view = new TextView(mContext);
        view.setId(R.id.tuijian_style_c_id_1);
        view.setText(vo.text);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = ScreenUtil.dp2px(mContext, 10);
        params.rightMargin = ScreenUtil.dp2px(mContext, 10);
        view.setLayoutParams(params);
        view.setGravity(Gravity.CENTER);

        view.setTag(R.id.tag_first, vo);
        viewMap.put(vo.id, view);
        return view;
    }

    private class TabInfoListVo {
        public List<TabInfoVo> data_list;

        public TabInfoListVo addItem(TabInfoVo tabInfoVo) {
            if (data_list == null) {
                data_list = new ArrayList<>();
            }
            data_list.add(tabInfoVo);
            return this;
        }

        private int     lastIndexItemGameId;
        private boolean hasSetDefaultItemGameId;
        private boolean hasChangeItemGameId;

        public int getLastIndexItemGameId() {
            if (hasChangeItemGameId) {
                return lastIndexItemGameId;
            } else {
                return getDefaultItemGameId();
            }
        }

        public void setDefaultItemGameId(int defaultIndexItemGameId) {
            this.lastIndexItemGameId = defaultIndexItemGameId;
            hasSetDefaultItemGameId = true;
        }

        public void setLastIndexItemGameId(int lastIndexItemGameId) {
            if (this.lastIndexItemGameId == lastIndexItemGameId) {
                return;
            }
            hasChangeItemGameId = true;
            this.lastIndexItemGameId = lastIndexItemGameId;
        }

        private int getDefaultItemGameId() {
            if (hasSetDefaultItemGameId && lastIndexItemGameId != 0) {
                return lastIndexItemGameId;
            }
            if (data_list == null || data_list.isEmpty()) {
                return 0;
            }
            return data_list.get(0).id;
        }
    }

    private class TabInfoVo {
        public int                             id;
        public String                          text;
        public List<MainFuliStyle1Vo.DataBean> data;

        public TabInfoVo(int id, String text, List<MainFuliStyle1Vo.DataBean> data) {
            this.id = id;
            this.text = text;
            this.data = data;
        }

    }

    private View initMainFeiA(MainTuiJianDataVo.CommonGameDataBeanVo tj_mianfei, boolean isFirst) {
        View parent = LayoutInflater.from(activity).inflate(R.layout.item_main_tj_mianfei_a, null);
        TextView mTvSubTitle = parent.findViewById(R.id.tv_sub_title);
        FlexboxLayout mFlexBoxLayout = parent.findViewById(R.id.flex_box_layout);
        ImageView mImage = parent.findViewById(R.id.image);

        mTvSubTitle.setText(tj_mianfei.module_title);
        try {
            mTvSubTitle.setTextColor(Color.parseColor(tj_mianfei.module_title_color));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (tj_mianfei.additional != null) {
            mImage.setVisibility(View.VISIBLE);
            GlideUtils.loadRoundImage(mContext, tj_mianfei.additional.pic, mImage, R.mipmap.img_placeholder_v_1);
            mImage.setOnClickListener(view -> {
                try {
                    appJump(tj_mianfei.additional);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } else {
            mImage.setVisibility(View.GONE);
        }


        mFlexBoxLayout.removeAllViews();
        if (checkCollectionNotEmpty(tj_mianfei.data)) {
            mFlexBoxLayout.setVisibility(View.VISIBLE);
            for (GameInfoVo gameInfoVo : tj_mianfei.data) {
                View itemView = createMainFeiAItemView(gameInfoVo);
                FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.width = (ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dp2px(mContext, 50)) / 3;
                params.topMargin = ScreenUtil.dp2px(mContext, 6);
                params.bottomMargin = ScreenUtil.dp2px(mContext, 6);
                mFlexBoxLayout.addView(itemView, params);
            }
        } else {
            mFlexBoxLayout.setVisibility(View.GONE);
        }

        setCommonLayoutParams(parent, isFirst);
        return parent;
    }

    private View createMainFeiAItemView(GameInfoVo gameDataVo) {
        View itemView = LayoutInflater.from(activity).inflate(R.layout.item_main_tj_mianfei_a_item, null);
        ImageView mIvGameIcon = itemView.findViewById(R.id.iv_game_icon);
        TextView mTvGameVip = itemView.findViewById(R.id.tv_game_vip);
        TextView mTvGameName = itemView.findViewById(R.id.tv_game_name);
        TextView mTvGameCoin = itemView.findViewById(R.id.tv_game_coin);

        GlideUtils.loadRoundImage(mContext, gameDataVo.getGameicon(), mIvGameIcon);
        mTvGameName.setText(gameDataVo.getGamename());
        mTvGameCoin.setText(gameDataVo.getCoupon_amount() + "元");

        if (!TextUtils.isEmpty(gameDataVo.getVip_label())) {
            mTvGameVip.setText(gameDataVo.getVip_label());

            GradientDrawable gd = new GradientDrawable();
            float radius = ScreenUtil.dp2px(mContext, 8);
            gd.setCornerRadii(new float[]{0, 0, radius, radius, 0, 0, radius, radius});
            gd.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
            gd.setColors(new int[]{Color.parseColor("#FE6631"), Color.parseColor("#EF0F16")});
            mTvGameVip.setBackground(gd);
        }


        GradientDrawable gd = new GradientDrawable();
        float radius = ScreenUtil.dp2px(mContext, 8);
        gd.setCornerRadius(radius);
        gd.setColor(Color.parseColor("#150D9EFF"));
        itemView.setBackground(gd);

        itemView.setOnClickListener(view -> {
            if (fragment != null) {
                fragment.goGameDetail(gameDataVo.getGameid(), gameDataVo.getGame_type());
            }
        });

        return itemView;
    }

    private View initMainFeiB(MainTuiJianDataVo.CommonGameDataBeanVo tj_mianfei, boolean isFirst) {
        View parent = LayoutInflater.from(activity).inflate(R.layout.item_main_tj_mianfei_b, null);
        TextView mTvSubTitle = parent.findViewById(R.id.tv_sub_title);
        LinearLayout mFlexBoxLayout = parent.findViewById(R.id.flex_box_layout);
        ImageView mImage = parent.findViewById(R.id.image);

        mTvSubTitle.setText(tj_mianfei.module_title);
        try {
            mTvSubTitle.setTextColor(Color.parseColor(tj_mianfei.module_title_color));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (tj_mianfei.additional != null) {
            mImage.setVisibility(View.VISIBLE);
            GlideUtils.loadRoundImage(mContext, tj_mianfei.additional.pic, mImage, R.mipmap.img_placeholder_v_1);
            mImage.setOnClickListener(view -> {
                try {
                    appJump(tj_mianfei.additional);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } else {
            mImage.setVisibility(View.GONE);
        }


        mFlexBoxLayout.removeAllViews();
        if (checkCollectionNotEmpty(tj_mianfei.data)) {
            mFlexBoxLayout.setVisibility(View.VISIBLE);
            for (GameInfoVo gameInfoVo : tj_mianfei.data) {
                View itemView = createMainFeiBItemView(gameInfoVo);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                mFlexBoxLayout.addView(itemView, params);
            }
        } else {
            mFlexBoxLayout.setVisibility(View.GONE);
        }

        setCommonLayoutParams(parent, isFirst);
        return parent;
    }

    private View createMainFeiBItemView(GameInfoVo gameDataVo) {
        View itemView = LayoutInflater.from(activity).inflate(R.layout.item_main_tj_mianfei_b_item, null);
        ImageView mIvGameIcon = itemView.findViewById(R.id.iv_game_icon);
        TextView mTvGameVip = itemView.findViewById(R.id.tv_game_vip);
        TextView mTvGameName = itemView.findViewById(R.id.tv_game_name);
        FlexboxLayout mFlexBoxLayout = itemView.findViewById(R.id.flex_box_layout);
        TextView mBtnConfirm = itemView.findViewById(R.id.btn_confirm);

        GlideUtils.loadRoundImage(mContext, gameDataVo.getGameicon(), mIvGameIcon);
        mTvGameName.setText(gameDataVo.getGamename());

        List<GameInfoVo.GameLabelsBean> list = new ArrayList<>();

        if (!TextUtils.isEmpty(gameDataVo.getVip_label())) {
            mTvGameVip.setVisibility(View.VISIBLE);
            mTvGameVip.setText(gameDataVo.getVip_label());

            GradientDrawable gd = new GradientDrawable();
            float radius = ScreenUtil.dp2px(mContext, 8);
            gd.setCornerRadii(new float[]{0, 0, radius, radius, 0, 0, radius, radius});
            gd.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
            gd.setColors(new int[]{Color.parseColor("#FE6631"), Color.parseColor("#EF0F16")});
            mTvGameVip.setBackground(gd);

            list.add(new GameInfoVo.GameLabelsBean("免费送", "#F70000", "#FFEAEA"));
        } else {
            mTvGameVip.setVisibility(View.GONE);
        }
        if (gameDataVo.getCoupon_amount() > 0) {
            list.add(new GameInfoVo.GameLabelsBean("送" + gameDataVo.getCoupon_amount() + "元游戏充值券", "#3168FF", "#193168FF"));
        }

        boolean hasLineTag = false;
        mFlexBoxLayout.removeAllViews();
        mFlexBoxLayout.setVisibility(View.VISIBLE);

        if (!list.isEmpty()) {
            for (GameInfoVo.GameLabelsBean labelsBean : list) {
                FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                params.rightMargin = (int) (4 * ScreenUtil.getScreenDensity(mContext));
                params.topMargin = (int) (4 * ScreenUtil.getScreenDensity(mContext));
                mFlexBoxLayout.addView(createLabelView(labelsBean), params);
            }
            hasLineTag = true;
        }
        if (!hasLineTag) {
            mFlexBoxLayout.setVisibility(View.GONE);
        }

        GradientDrawable gd = new GradientDrawable();
        float radius = ScreenUtil.dp2px(mContext, 8);
        gd.setCornerRadius(radius);
        gd.setColor(Color.parseColor("#150D9EFF"));
        itemView.setBackground(gd);

        itemView.setOnClickListener(view -> {
            if (fragment != null) {
                fragment.goGameDetail(gameDataVo.getGameid(), gameDataVo.getGame_type());
            }
        });

        mBtnConfirm.setOnClickListener(view -> {

        });
        return itemView;
    }

    private View createLabelView(GameInfoVo.GameLabelsBean labelsBean) {
        TextView textView = new TextView(mContext);
        textView.setText(labelsBean.getLabel_name());
        textView.setIncludeFontPadding(false);
        try {
            textView.setTextColor(Color.parseColor(labelsBean.getText_color()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        textView.setTextSize(11.5f);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(ScreenUtil.dp2px(mContext, 4));
        try {
            gd.setColor(Color.parseColor(labelsBean.getBgcolor()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        textView.setBackground(gd);

        textView.setPadding(ScreenUtil.dp2px(mContext, 4), ScreenUtil.dp2px(mContext, 2), ScreenUtil.dp2px(mContext, 4), ScreenUtil.dp2px(mContext, 2));

        return textView;
    }

    private View initMainFeiC(MainTuiJianDataVo.CommonGameDataBeanVo tj_mianfei, boolean isFirst) {
        View parent = LayoutInflater.from(activity).inflate(R.layout.item_main_tj_mianfei_c, null);
        TextView mTvSubTitle = parent.findViewById(R.id.tv_sub_title);
        FlexboxLayout mFlexBoxLayout = parent.findViewById(R.id.flex_box_layout);
        ImageView mImage = parent.findViewById(R.id.image);

        mTvSubTitle.setText(tj_mianfei.module_title);
        try {
            mTvSubTitle.setTextColor(Color.parseColor(tj_mianfei.module_title_color));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (tj_mianfei.additional != null) {
            mImage.setVisibility(View.VISIBLE);
            GlideUtils.loadRoundImage(mContext, tj_mianfei.additional.pic, mImage, R.mipmap.img_placeholder_v_1);
            mImage.setOnClickListener(view -> {
                try {
                    appJump(tj_mianfei.additional);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } else {
            mImage.setVisibility(View.GONE);
        }


        mFlexBoxLayout.removeAllViews();
        if (checkCollectionNotEmpty(tj_mianfei.data)) {
            mFlexBoxLayout.setVisibility(View.VISIBLE);
            for (GameInfoVo gameInfoVo : tj_mianfei.data) {
                View itemView = createMainFeiCItemView(gameInfoVo);
                FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.width = ScreenUtil.getScreenWidth(mContext) / 3;
                params.topMargin = ScreenUtil.dp2px(mContext, 6);
                params.bottomMargin = ScreenUtil.dp2px(mContext, 6);
                mFlexBoxLayout.addView(itemView, params);
            }
        } else {
            mFlexBoxLayout.setVisibility(View.GONE);
        }

        setCommonLayoutParams(parent, isFirst);
        return parent;
    }

    private View createMainFeiCItemView(GameInfoVo gameDataVo) {
        View itemView = LayoutInflater.from(activity).inflate(R.layout.item_main_tj_mianfei_c_item, null);
        ImageView mIvGameIcon = itemView.findViewById(R.id.iv_game_icon);
        TextView mTvGameVip = itemView.findViewById(R.id.tv_game_vip);
        TextView mTvGameName = itemView.findViewById(R.id.tv_game_name);
        TextView mTvGameCoin = itemView.findViewById(R.id.tv_game_coin);
        TextView mTvSong = itemView.findViewById(R.id.tv_song);

        GlideUtils.loadRoundImage(mContext, gameDataVo.getGameicon(), mIvGameIcon);
        mTvGameName.setText(gameDataVo.getGamename());
        mTvGameCoin.setText(gameDataVo.getCoupon_amount() + "元");

        GradientDrawable gdTemp1 = new GradientDrawable();
        gdTemp1.setColor(Color.parseColor("#F02121"));
        gdTemp1.setCornerRadius(ScreenUtil.dp2px(mContext, 4));
        mTvSong.setBackground(gdTemp1);


        if (!TextUtils.isEmpty(gameDataVo.getVip_label())) {
            mTvGameVip.setText(gameDataVo.getVip_label());

            GradientDrawable gd = new GradientDrawable();
            float radius = ScreenUtil.dp2px(mContext, 8);
            gd.setCornerRadii(new float[]{0, 0, radius, radius, 0, 0, radius, radius});
            gd.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
            gd.setColors(new int[]{Color.parseColor("#FE6631"), Color.parseColor("#EF0F16")});
            mTvGameVip.setBackground(gd);
        }

        itemView.setOnClickListener(view -> {
            if (fragment != null) {
                fragment.goGameDetail(gameDataVo.getGameid(), gameDataVo.getGame_type());
            }
        });

        return itemView;
    }

    private View initReMenYouXi(MainTuiJianDataVo.CommonGameDataBeanVo tj_remenyouxi, String style, boolean isFirst) {
        View parent = LayoutInflater.from(activity).inflate(R.layout.item_main_tj_remenyouxi, null);
        TextView mTvSubTitle = parent.findViewById(R.id.tv_sub_title);
        FlexboxLayout mFlexBoxLayout = parent.findViewById(R.id.flex_box_layout);
        HorizontalScrollView mHsv = parent.findViewById(R.id.hsv);
        LinearLayout mLlContainer = parent.findViewById(R.id.ll_container);


        mTvSubTitle.setText(tj_remenyouxi.module_title);
        try {
            if (!TextUtils.isEmpty(tj_remenyouxi.module_title_color) && (tj_remenyouxi.module_title_color.length() == 7 || tj_remenyouxi.module_title_color.length() == 5 || tj_remenyouxi.module_title_color.length() == 9)){
                mTvSubTitle.setTextColor(Color.parseColor(tj_remenyouxi.module_title_color));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mLlContainer.removeAllViews();
        mFlexBoxLayout.removeAllViews();

        mHsv.setVisibility(View.GONE);
        mFlexBoxLayout.setVisibility(View.GONE);

        for (GameInfoVo gameInfoVo : tj_remenyouxi.data) {
            View itemView = null;
            if (MainTuiJianDataVo.ModuleStyle.TYPE_A.equalsIgnoreCase(style)) {
                mFlexBoxLayout.setVisibility(View.VISIBLE);
                itemView = createItemReMenYouXiA(gameInfoVo);
                FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.width = (ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dp2px(mContext, 60)) / 3;
                params.topMargin = ScreenUtil.dp2px(mContext, 4);
                params.bottomMargin = ScreenUtil.dp2px(mContext, 12);
                mFlexBoxLayout.addView(itemView, params);
            } else if (MainTuiJianDataVo.ModuleStyle.TYPE_B.equalsIgnoreCase(style)) {
                mHsv.setVisibility(View.VISIBLE);
                itemView = createItemReMenYouXiB(gameInfoVo);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.width = (int) ((ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dp2px(mContext, 30)) / 4f);
                params.leftMargin = ScreenUtil.dp2px(mContext, 10);
                params.rightMargin = ScreenUtil.dp2px(mContext, 10);
                mLlContainer.addView(itemView, params);
            } else if (MainTuiJianDataVo.ModuleStyle.TYPE_C.equalsIgnoreCase(style)) {
                mFlexBoxLayout.setVisibility(View.VISIBLE);
                itemView = createItemReMenYouXiC(gameInfoVo);
                FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.width = (ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dp2px(mContext, 60)) / 3;
                params.topMargin = ScreenUtil.dp2px(mContext, 4);
                params.bottomMargin = ScreenUtil.dp2px(mContext, 12);
                mFlexBoxLayout.addView(itemView, params);
            }
        }
        setCommonLayoutParams(parent, isFirst);
        return parent;
    }

    private View createItemReMenYouXiA(GameInfoVo gameInfoVo) {
        View itemView = LayoutInflater.from(activity).inflate(R.layout.item_main_tj_remenyouxi_flex_item_a, null);
        ImageView mIvGameIcon = itemView.findViewById(R.id.iv_game_icon);
        TextView mTvGameName = itemView.findViewById(R.id.tv_game_name);
        TextView mTvGameCoin = itemView.findViewById(R.id.tv_game_coin);
        TextView mTvGameVip = itemView.findViewById(R.id.tv_game_vip);

        GlideUtils.loadRoundImage(mContext, gameInfoVo.getGameicon(), mIvGameIcon);
        mTvGameName.setText(gameInfoVo.getGamename());
        mTvGameCoin.setText(gameInfoVo.getCoin_label());

        if (!TextUtils.isEmpty(gameInfoVo.getVip_label())) {
            mTvGameVip.setText(gameInfoVo.getVip_label());

            GradientDrawable gd = new GradientDrawable();
            float radius = ScreenUtil.dp2px(mContext, 8);
            gd.setCornerRadii(new float[]{0, 0, radius, radius, 0, 0, radius, radius});
            gd.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
            gd.setColors(new int[]{Color.parseColor("#FE6631"), Color.parseColor("#EF0F16")});
            mTvGameVip.setBackground(gd);
        }
        itemView.setOnClickListener(view -> {
            if (fragment != null) {
                fragment.goGameDetail(gameInfoVo.getGameid(), gameInfoVo.getGame_type());
            }
        });
        return itemView;
    }

    private View createItemReMenYouXiB(GameInfoVo gameInfoVo) {
        View itemView = LayoutInflater.from(activity).inflate(R.layout.item_main_tj_remenyouxi_flex_item_b, null);
        ImageView mIvGameIcon = itemView.findViewById(R.id.iv_game_icon);
        TextView mTvGameName = itemView.findViewById(R.id.tv_game_name);
        TextView mTvTitle1 = itemView.findViewById(R.id.tv_title_1);
        TextView mTvTitle2 = itemView.findViewById(R.id.tv_title_2);
        GlideUtils.loadRoundImage(mContext, gameInfoVo.getGameicon(), mIvGameIcon);
        mTvGameName.setText(gameInfoVo.getGamename());
        mTvTitle1.setText(gameInfoVo.getVip_label());
        mTvTitle2.setText(gameInfoVo.getCoin_label());

        itemView.setOnClickListener(view -> {
            if (fragment != null) {
                fragment.goGameDetail(gameInfoVo.getGameid(), gameInfoVo.getGame_type());
            }
        });
        return itemView;
    }

    private View createItemReMenYouXiC(GameInfoVo gameInfoVo) {
        View itemView = LayoutInflater.from(activity).inflate(R.layout.item_main_tj_remenyouxi_flex_item_c, null);
        ImageView mIvGameIcon = itemView.findViewById(R.id.iv_game_icon);
        TextView mTvGameName = itemView.findViewById(R.id.tv_game_name);
        TextView mTvGameCoin = itemView.findViewById(R.id.tv_game_coin);
        TextView mTvGameVip = itemView.findViewById(R.id.tv_game_vip);

        GlideUtils.loadRoundImage(mContext, gameInfoVo.getGameicon(), mIvGameIcon);
        mTvGameName.setText(gameInfoVo.getGamename());
        mTvGameCoin.setText(gameInfoVo.getCoin_label());

        if (!TextUtils.isEmpty(gameInfoVo.getVip_label())) {
            mTvGameVip.setText(gameInfoVo.getVip_label());

            GradientDrawable gd = new GradientDrawable();
            float radius = ScreenUtil.dp2px(mContext, 8);
            gd.setCornerRadii(new float[]{0, 0, radius, radius, 0, 0, radius, radius});
            gd.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
            gd.setColors(new int[]{Color.parseColor("#A819E4"), Color.parseColor("#F01D21")});
            mTvGameVip.setBackground(gd);
        }
        GradientDrawable gd = new GradientDrawable();
        float radius = ScreenUtil.dp2px(mContext, 5);
        gd.setCornerRadius(radius);
        gd.setStroke(ScreenUtil.dp2px(mContext, 1), Color.parseColor("#A819E4"));
        mTvGameCoin.setBackground(gd);

        itemView.setOnClickListener(view -> {
            if (fragment != null) {
                fragment.goGameDetail(gameInfoVo.getGameid(), gameInfoVo.getGame_type());
            }
        });
        return itemView;
    }


    private View initBannerA(LunboDataBeanVo tj_lunbo, boolean isFirst) {
        View parent = LayoutInflater.from(activity).inflate(R.layout.item_main_tj_banner_a, null);
        RelativeLayout mRlBanner = parent.findViewById(R.id.rl_banner);
        ViewPager mBannerViewPager = parent.findViewById(R.id.banner_viewPager);
        FixedIndicatorView mBannerIndicator = parent.findViewById(R.id.banner_indicator);

        GradientDrawable gdCheck = new GradientDrawable();
        GradientDrawable gdNormal = new GradientDrawable();
        float density = mContext.getResources().getDisplayMetrics().density;
        gdCheck.setCornerRadius(4 * density);
        gdCheck.setColor(Color.parseColor("#FFFFFFFF"));

        gdNormal.setCornerRadius(4 * density);
        gdNormal.setShape(GradientDrawable.OVAL);
        gdNormal.setColor(Color.parseColor("#9AFFFFFF"));

        int bannerSize = tj_lunbo.data.size();
        mBannerIndicator.setSplitMethod(FixedIndicatorView.SPLITMETHOD_WRAP);
        BannerComponent bannerComponent = new BannerComponent(mBannerIndicator, mBannerViewPager, false);
        mBannerViewPager.setOffscreenPageLimit(bannerSize);
        InnerIndicatorViewPagerAdapter adapter = new InnerIndicatorViewPagerAdapter(tj_lunbo.data, mBannerViewPager);
        bannerComponent.setOnIndicatorPageChangeListener(new IndicatorViewPager.OnIndicatorPageChangeListener() {
            @Override
            public void onIndicatorPageChange(int preItem, int currentItem) {
                Map<Integer, View> tabViewMaps = adapter.getTabViewMaps();
                for (Integer key : tabViewMaps.keySet()) {
                    View selectItemView = tabViewMaps.get(key);
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) selectItemView.getLayoutParams();
                    if (params == null) {
                        params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    }
                    float density = mContext.getResources().getDisplayMetrics().density;
                    params.leftMargin = (int) (2 * density);
                    params.rightMargin = (int) (2 * density);

                    if (key == currentItem) {
                        params.width = (int) (10 * density);
                        params.height = (int) (4 * density);
                        selectItemView.setBackground(gdCheck);
                    } else {
                        params.width = (int) (4 * density);
                        params.height = (int) (4 * density);
                        selectItemView.setBackground(gdNormal);
                    }
                    selectItemView.setLayoutParams(params);
                }
            }
        });
        bannerComponent.setAdapter(adapter);
        bannerComponent.setCurrentItem(0, false);
        bannerComponent.setAutoPlayTime(5000);
        if (bannerSize > 1) {
            bannerComponent.startAutoPlay();
        }

        setCommonLayoutParams(parent, isFirst, true);
        return parent;
    }

    private View initBannerB(LunboDataBeanVo tj_lunbo, boolean isFirst) {
        View parent = LayoutInflater.from(activity).inflate(R.layout.item_main_tj_banner_b, null);
        RecyclerView mRecyclerView = parent.findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(new BannerBAdapter(mContext, tj_lunbo.data));
        StartSnapHelper snapHelper = new StartSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);

        setCommonLayoutParams(parent, isFirst, true);
        return parent;
    }

    private View initBannerC(LunboDataBeanVo tj_lunbo, boolean isFirst) {
        View parent = LayoutInflater.from(activity).inflate(R.layout.item_main_tj_banner_a, null);
        RelativeLayout mRlBanner = parent.findViewById(R.id.rl_banner);
        ViewPager mBannerViewPager = parent.findViewById(R.id.banner_viewPager);
        FixedIndicatorView mBannerIndicator = parent.findViewById(R.id.banner_indicator);

        int bannerSize = tj_lunbo.data.size();
        mBannerIndicator.setSplitMethod(FixedIndicatorView.SPLITMETHOD_WRAP);
        BannerComponent bannerComponent = new BannerComponent(mBannerIndicator, mBannerViewPager, false);
        mBannerViewPager.setOffscreenPageLimit(bannerSize);
        InnerIndicatorViewPagerAdapter adapter = new InnerIndicatorViewPagerAdapter(tj_lunbo.data, mBannerViewPager);

        mBannerViewPager.setPageMargin(16);
        mBannerViewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            float scale = 0.95f;

            @Override
            public void transformPage(View page, float position) {
                if (position >= 0 && position <= 1) {
                    page.setScaleY(scale + (1 - scale) * (1 - position));
                } else if (position > -1 && position < 0) {
                    page.setScaleY(1 + (1 - scale) * position);
                } else {
                    page.setScaleY(scale);
                }
            }
        });

        bannerComponent.setOnIndicatorPageChangeListener(new IndicatorViewPager.OnIndicatorPageChangeListener() {
            @Override
            public void onIndicatorPageChange(int preItem, int currentItem) {
                Map<Integer, View> tabViewMaps = adapter.getTabViewMaps();
                for (Integer key : tabViewMaps.keySet()) {
                    View selectItemView = tabViewMaps.get(key);
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) selectItemView.getLayoutParams();
                    if (params == null) {
                        params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    }
                    float density = mContext.getResources().getDisplayMetrics().density;
                    params.leftMargin = (int) (2 * density);
                    params.rightMargin = (int) (2 * density);

                    params.width = (int) (7 * density);
                    params.height = (int) (7 * density);
                    GradientDrawable gd = new GradientDrawable();
                    gd.setCornerRadius(7 * density);
                    gd.setShape(GradientDrawable.OVAL);
                    if (key == currentItem) {
                        gd.setColor(Color.parseColor("#0052FE"));
                    } else {
                        gd.setColor(Color.parseColor("#FFFFFF"));
                    }
                    selectItemView.setBackground(gd);
                    selectItemView.setLayoutParams(params);
                }
            }
        });
        bannerComponent.setAdapter(adapter);
        bannerComponent.setCurrentItem(0, false);
        bannerComponent.setAutoPlayTime(5000);
        if (bannerSize > 1) {
            bannerComponent.startAutoPlay();
        }

        setCommonLayoutParams(parent, isFirst, true);
        return parent;
    }


    private View initMenu(MainMenuVo tj_menu, boolean isFirst) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_main_page_menu, null, false);
        LinearLayout mLlContainer = view.findViewById(R.id.ll_container);

        for (MainMenuVo.DataBean dataBean : tj_menu.data) {
            View itemView = createMenuItem(dataBean);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtil.getScreenWidth(mContext) / 4, LinearLayout.LayoutParams.MATCH_PARENT);

            itemView.setOnClickListener(v -> {
                appJump(dataBean);
            });
            mLlContainer.addView(itemView, params);
        }
        setCommonLayoutParams(view, isFirst);
        return view;
    }

    private View createMenuItem(MainMenuVo.DataBean dataBean) {
        LinearLayout layout = new LinearLayout(mContext);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER_VERTICAL);

        ImageView image = new ImageView(mContext);
        LinearLayout.LayoutParams imageParam = new LinearLayout.LayoutParams(ScreenUtil.dp2px(mContext, 50), ScreenUtil.dp2px(mContext, 50));
        imageParam.gravity = Gravity.CENTER;
        layout.addView(image, imageParam);
        GlideUtils.loadNormalImage(mContext, dataBean.icon, image);

        TextView text = new TextView(mContext);
        LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textParam.gravity = Gravity.CENTER;
        textParam.leftMargin = ScreenUtil.dp2px(mContext, 12);
        textParam.rightMargin = ScreenUtil.dp2px(mContext, 12);
        textParam.topMargin = ScreenUtil.dp2px(mContext, 6);
        text.setText(dataBean.title);
        try {
            text.setTextColor(Color.parseColor(dataBean.title_color));
        } catch (Exception e) {
            e.printStackTrace();
        }
        text.setIncludeFontPadding(false);
        text.setTextSize(12);
        text.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        layout.addView(text, textParam);


        return layout;
    }

    private View initMoreGame() {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_more_game, null, false);
        LinearLayout mLlMoreGame = view.findViewById(R.id.ll_more_game);

        mLlMoreGame.setOnClickListener(v -> {
            goToMainGamePage(0);
        });
        return view;
    }

    public void goToMainGamePage(int index) {
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).goToMainGamePage(index);
        }
    }


    private void setCommonLayoutParams(View parent, boolean isFirst) {
        setCommonLayoutParams(parent, isFirst, false);
    }

    private void setCommonLayoutParams(View parent, boolean isFirst, boolean isBanner) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = isFirst ? 0 : ScreenUtil.dp2px(mContext, 10);
        params.bottomMargin = ScreenUtil.dp2px(mContext, 10);
        parent.setLayoutParams(params);

        if (!isBanner) {
            GradientDrawable gd = new GradientDrawable();
            gd.setColor(Color.WHITE);
            int radius = ScreenUtil.dp2px(mContext, 10);
            if (isFirst) {
                gd.setCornerRadii(new float[]{0, 0, 0, 0, radius, radius, radius, radius});
            } else {
                gd.setCornerRadius(radius);
            }
            parent.setBackground(gd);
        }
    }

    protected boolean checkCollectionNotEmpty(Collection collection) {
        return collection != null && !collection.isEmpty();
    }


    private class InnerIndicatorViewPagerAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter {

        private Map<Integer, View> tabViewMaps = new HashMap<>();

        private List<BannerVo> mBannerVos;
        private ViewPager      mViewPager;

        public InnerIndicatorViewPagerAdapter(List<BannerVo> mBannerVos, ViewPager targetViewPager) {
            this.mBannerVos = mBannerVos;
            this.mViewPager = targetViewPager;
        }

        @Override
        public int getCount() {
            return mBannerVos == null ? 0 : mBannerVos.size();
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = new View(mContext);
            }
            tabViewMaps.put(position, convertView);
            return convertView;
        }

        public Map<Integer, View> getTabViewMaps() {
            return tabViewMaps;
        }

        @Override
        public View getViewForPage(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = new RelativeLayout(mContext);
                convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                ImageView imageView = new ImageView(mContext);
                imageView.setId(R.id.banner);
                imageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                ((ViewGroup) convertView).addView(imageView);
            }
            ImageView mImageView = convertView.findViewById(R.id.banner);
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            String imageUrl = mBannerVos.get(position).getPic();
            GlideUtils.loadRoundImage(mContext, imageUrl, mImageView, R.mipmap.img_placeholder_v_1);

            mImageView.setOnClickListener(v -> {
                try {
                    BannerVo bannerVo = mBannerVos.get(position);
                    if (bannerVo != null) {
                        appJump(bannerVo.getJumpInfo());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return convertView;
        }

        public void refreshAll(List<BannerVo> sliderBanner) {
            if (sliderBanner == null) {
                return;
            }
            mBannerVos.clear();
            mBannerVos.addAll(sliderBanner);
            notifyDataSetChanged();
        }
    }

    private class BannerBAdapter extends RecyclerView.Adapter<BannerBAdapter.ViewHolder> {

        private Context        mContext;
        private List<BannerVo> data;

        public BannerBAdapter(Context context, List<BannerVo> data) {
            mContext = context;
            this.data = data;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(activity).inflate(R.layout.item_main_tj_banner_b_rv_item, null);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.mImage.getLayoutParams();
            if (params != null) {
                params.width = ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dp2px(mContext, 90);
                if (position == 0) {
                    params.leftMargin = ScreenUtil.dp2px(mContext, 10);
                } else {
                    params.leftMargin = ScreenUtil.dp2px(mContext, 0);
                }
                params.rightMargin = ScreenUtil.dp2px(mContext, 10);
                holder.mImage.setLayoutParams(params);
            }
            BannerVo vo = data.get(position);
            GlideUtils.loadImage(mContext, vo.getPic(), holder.mImage, R.mipmap.img_placeholder_v_2);
            holder.mImage.setOnClickListener(view -> {
                appJump(vo.getJumpInfo());
            });
        }

        @Override
        public int getItemCount() {
            return data == null ? 0 : data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView mImage;

            public ViewHolder(View itemView) {
                super(itemView);
                mImage = itemView.findViewById(R.id.image);

            }
        }
    }

    private void appJump(AppBaseJumpInfoBean jumpInfo) {
        AppJumpAction appJumpAction = new AppJumpAction(activity);
        appJumpAction.jumpAction(jumpInfo);
    }
}
