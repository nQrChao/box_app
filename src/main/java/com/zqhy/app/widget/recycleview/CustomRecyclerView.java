package com.zqhy.app.widget.recycleview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.newproject.R;
import com.zqhy.app.widget.expand.DensityUtils;

/**
 * 自定义RecyclerView
 * @author 韩国桐
 * @version [0.1,2019-12-25]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CustomRecyclerView extends SwipeRefreshLayout {
    private RecyclerView       recyclerView;
    private BaseCustomAdapter  customAdapter;
    private OnRecyclerListener onRecyclerListener;
    boolean isBottom=false;
    /**加载中**/
    public final static int LOAD_ING=0;
    /**加载成功**/
    public final static int LOAD_SUCCESS=1;
    /**加载失败**/
    public final static int LOAD_FAILED=2;
    /** 加载中，加载完成，加载失败 **/
    private int loadState=0;

    private int lastPosition = 0;
    private int lastOffset = 0;

    public CustomRecyclerView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        recyclerView=new RecyclerView(context);
        addView(recyclerView);
        setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        setProgressViewOffset(true, -20, 100);
    }
    /**
     * 获取Adapter
     * **/
    public BaseCustomAdapter getCustomAdapter(){
        return customAdapter;
    }


    public RecyclerView getRecyclerView(){
        return recyclerView;
    }
    /**
     * 设置Adapter
     * **/
    public void setAdapter(BaseCustomAdapter customAdapter, RecyclerView.LayoutManager layoutManager){
        this.customAdapter=customAdapter;
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(layoutManager);

    }

    public void addItemDecoration(int dp,boolean isHz){
        recyclerView.addItemDecoration(new InitDecoration(recyclerView.getContext(),dp,isHz));
    }

    /**
     * 设置监听数据
     * **/
    public void setListener(OnRecyclerListener onRecyclerListener){
        setListener(onRecyclerListener,true,false);
    }

    /**
     * 设置监听数据
     * **/
    public void setListener(OnRecyclerListener onRecyclerListener,boolean isLoading){
        setListener(onRecyclerListener,isLoading,false);
    }

    /**
     * 设置监听数据
     * **/
    public void setListener2(OnRecyclerListener onRecyclerListener,boolean isHz){
        setListener2(onRecyclerListener,true,isHz);
    }

    /**
     * 设置监听数据
     * **/
    public void setListener(OnRecyclerListener onRecyclerListener,boolean isLoading,boolean stickHead){
        this.onRecyclerListener=onRecyclerListener;
        setOnRefreshListener(() -> {
            setRefreshing(false);
            onRecyclerListener.reload();
        });
        customAdapter.setItemClickListener(onRecyclerListener::onItemClick);
        customAdapter.setFootView(isLoading);
        if(isLoading){
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    isBottom=RecycleUtil.canPullDown(recyclerView,dy,stickHead);
                }

                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    if(newState== RecyclerView.SCROLL_STATE_IDLE&&isBottom){
                        onRecyclerListener.loadMore();
                    }
                    if(recyclerView.getLayoutManager() instanceof GridLayoutManager){
                        GridLayoutManager mLayoutManager=(GridLayoutManager)recyclerView.getLayoutManager();
                        if(mLayoutManager!=null){
                            View topView = mLayoutManager.getChildAt(0);
                            if(topView!=null){
                                lastOffset = topView.getTop();
                                lastPosition = mLayoutManager.getPosition(topView);
                            }
                        }
                    }else if(recyclerView.getLayoutManager() instanceof LinearLayoutManager){
                        LinearLayoutManager mLayoutManager=(LinearLayoutManager)recyclerView.getLayoutManager();
                        if(mLayoutManager!=null){
                            View topView = mLayoutManager.getChildAt(0);
                            if(topView!=null){
                                lastOffset = topView.getTop();
                                lastPosition = mLayoutManager.getPosition(topView);
                            }
                        }
                    }
                }
            });
        }
    }

    public void setListener2(OnRecyclerListener onRecyclerListener,boolean isLoading,boolean isHz){
        isBottom=false;
        this.onRecyclerListener=onRecyclerListener;
        setOnRefreshListener(() -> {
            setRefreshing(false);
            onRecyclerListener.reload();
        });
        customAdapter.setItemClickListener(onRecyclerListener::onItemClick);
        customAdapter.setFootView(isLoading);
        if(isLoading){
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if(isHz){
                        isBottom=RecycleUtil.canPullDownH(recyclerView,dx);
                    }else{
                        isBottom=RecycleUtil.canPullDown(recyclerView,dx,false);
                    }
                }

                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    if(newState== RecyclerView.SCROLL_STATE_IDLE&&isBottom){
                        onRecyclerListener.loadMore();
                    }
                    if(recyclerView.getLayoutManager() instanceof GridLayoutManager){
                        GridLayoutManager mLayoutManager=(GridLayoutManager)recyclerView.getLayoutManager();
                        if(mLayoutManager!=null){
                            View topView = mLayoutManager.getChildAt(0);
                            if(topView!=null){
                                lastOffset = topView.getTop();
                                lastPosition = mLayoutManager.getPosition(topView);
                            }
                        }
                    }else if(recyclerView.getLayoutManager() instanceof LinearLayoutManager){
                        LinearLayoutManager mLayoutManager=(LinearLayoutManager)recyclerView.getLayoutManager();
                        if(mLayoutManager!=null){
                            View topView = mLayoutManager.getChildAt(0);
                            if(topView!=null){
                                lastOffset = topView.getTop();
                                lastPosition = mLayoutManager.getPosition(topView);
                            }
                        }
                    }
                }
            });
        }
    }
    public void backToShow(){
        LinearLayoutManager mLayoutManager=(LinearLayoutManager)recyclerView.getLayoutManager();
        if(mLayoutManager!=null){
            mLayoutManager.scrollToPositionWithOffset(lastPosition, lastOffset);
        }
    }

    /**
     * 设置加载状态
     * **/
    public void setLoadState(@IntRange(from = 0,to = 2) int end){
        loadState=end;
        customAdapter.setLoadState(loadState);
        customAdapter.notifyDataSetChanged();
    }

    public abstract static class BaseCustomAdapter extends RecyclerView.Adapter {

        /** 加载中，加载完成，加载失败 **/
        private int loadState=0;

        public boolean isFootViewShow=true;

        public OnItemClickListener listener;

        public static final int VIEW_TYPE_ITEM = 10001;
        public static final int VIEW_TYPE_LOADING_FOOT = R.layout.common_loading_foot;

        public void setItemClickListener(OnItemClickListener listener){
            this.listener=listener;
        }

        public interface OnItemClickListener{
            /**
             * 列表项点击
             * @param index 点击索引
             * **/
            void onItemClick(int index);
        }

        /** 设置加载状态 **/
        public void setLoadState(@IntRange(from = 0,to = 2) int end){
            loadState=end;
        }

        /**
         * 绑定列表项
         * @param viewHolder viewHolder
         * @param position position;
         * **/
        public abstract void bindItemViewHolder(RecyclerView.ViewHolder viewHolder, int position);

        /**
         * 创建列表项
         * @param parent parent
         * @return ViewHolder
         * **/
        public abstract RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType);

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if(viewType ==VIEW_TYPE_LOADING_FOOT){
                View view = LayoutInflater.from(parent.getContext()).inflate(VIEW_TYPE_LOADING_FOOT,parent,false);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                view.setLayoutParams(lp);
                return new FootViewHolder(view);
            }else{
                return onCreateItemViewHolder(parent,viewType);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
            switch (getItemViewType(position)) {
                case VIEW_TYPE_LOADING_FOOT:
                    FootViewHolder footViewHolder = (FootViewHolder) viewHolder;
                    if(loadState==LOAD_ING){
                        footViewHolder.loadingArea.setVisibility(View.VISIBLE);
                        footViewHolder.content.setText("正在加载中...");
                    }else if(loadState==LOAD_SUCCESS){
                        footViewHolder.loadingArea.setVisibility(View.GONE);
                        //已无更多数据
                        footViewHolder.content.setText("");
                    }else{
                        footViewHolder.loadingArea.setVisibility(View.GONE);
                        footViewHolder.content.setText("加载失败,请下拉重试");
                    }
                    break;
                default:
                    bindItemViewHolder(viewHolder,position);
                    break;
            }
        }

        public void setFootView(boolean isShow){
            isFootViewShow=isShow;
        }

        @Override
        public int getItemViewType(int position) {
            if(isFootViewShow){
                if(position==getItemCount()-1){
                    return VIEW_TYPE_LOADING_FOOT;
                }else{
                    return VIEW_TYPE_ITEM;
                }
            }else{
                return VIEW_TYPE_ITEM;
            }
        }

        public class FootViewHolder extends RecyclerView.ViewHolder{
            public View loadingArea;
            public TextView content;
            public FootViewHolder(View itemView) {
                super(itemView);
                loadingArea = itemView.findViewById(R.id.progressBar);
                content = itemView.findViewById(R.id.tvLoad);
            }
        }
    }

    public interface OnRecyclerListener{
        /** 下拉重载 **/
        void reload();

        /** 加载更多 **/
        void loadMore();
        /**
         * 列表项点击
         * @param index 点击索引
         * **/
        void onItemClick(int index);
    }

    public static class InitDecoration extends RecyclerView.ItemDecoration{
        private int mDivider;
        private Paint dividerPaint;
        private boolean isHz;
        public InitDecoration(Context context,int dp,boolean isHz) {
            dividerPaint = new Paint();
            dividerPaint.setColor(Color.TRANSPARENT);
            this.isHz=isHz;
            mDivider = DensityUtils.dp2px(context,dp);
        }


        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            if(isHz){
                outRect.right=mDivider;
                outRect.left=mDivider;
                outRect.top=mDivider;
            }else{
                outRect.bottom=mDivider;
            }
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            if(isHz){
                drawH(c,parent);
            }else{
                drawV(c,parent);
            }
        }

        public void drawV(Canvas c, RecyclerView parent){
            int childCount = parent.getChildCount();
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            for (int i = 0; i < childCount; i++) {
                View view = parent.getChildAt(i);
                float top = view.getBottom();
                float bottom = view.getBottom() + mDivider;
                c.drawRect(left, top, right, bottom, dividerPaint);
            }
        }

        public void drawH(Canvas c, RecyclerView parent){
            final int top = parent.getPaddingTop();
            final int bottom = parent.getHeight() - parent.getPaddingBottom();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int left = child.getRight() + params.rightMargin;
                final int right = left + mDivider;
                c.drawRect(left, top, right, bottom,dividerPaint);
            }
        }
    }

    public static class StaggeredDividerItemDecoration  extends RecyclerView.ItemDecoration{
        private int dividerHeight;
        private Paint dividerPaint;
        public StaggeredDividerItemDecoration(Context context,int dp) {
            dividerPaint = new Paint();
            dividerPaint.setColor(Color.TRANSPARENT);
            dividerHeight = DensityUtils.dp2px(context,dp);
        }


        @Override
        public void getItemOffsets(@NonNull Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            StaggeredGridLayoutManager.LayoutParams params = ((StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams());
            if (params.getSpanIndex() != StaggeredGridLayoutManager.LayoutParams.INVALID_SPAN_ID) {
                outRect.right = dividerHeight / 2;
                outRect.left = dividerHeight / 2;
                outRect.top=dividerHeight;
            }
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int childCount = parent.getChildCount();
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            for (int i = 0; i < childCount; i++) {
                View view = parent.getChildAt(i);
                float top = view.getBottom();
                float bottom = view.getBottom() + dividerHeight;
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view
                        .getLayoutParams();
                c.drawRect(left, top, right, bottom, dividerPaint);
            }
        }
    }
}