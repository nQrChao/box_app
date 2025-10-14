package com.zqhy.app.widget.recycleview;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.Log;

import java.util.Arrays;

/**
 * [功能说明]
 *
 * @author 韩国桐
 * @version [0.1, 2020/6/9]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class RecycleUtil {

    public static boolean canPullDown(RecyclerView recyclerView, int dy, boolean stickHead) {
        if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager=(StaggeredGridLayoutManager)recyclerView.getLayoutManager();
            if(layoutManager!=null){
                int[] lastVisibleItem = layoutManager.findLastVisibleItemPositions(null);
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                Arrays.sort(lastVisibleItem);
                return lastVisibleItem[lastVisibleItem.length-1] >= totalItemCount - 1 && dy > 0;
            }
        }else if(recyclerView.getLayoutManager() instanceof GridLayoutManager){
            GridLayoutManager layoutManager=(GridLayoutManager)recyclerView.getLayoutManager();
            if(layoutManager!=null){
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                return lastVisibleItem >= totalItemCount - 1 && dy > 0;
            }
        }else if(recyclerView.getLayoutManager() instanceof LinearLayoutManager){
            LinearLayoutManager layoutManager=(LinearLayoutManager)recyclerView.getLayoutManager();
            if(stickHead){
                // 固定如何设置
            }
            if(layoutManager!=null){
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                return lastVisibleItem >= totalItemCount - 1 && dy > 0;
            }
        }
        return false;
    }

    public static boolean canPullDownH(RecyclerView recyclerView, int dx) {
        if(recyclerView.getLayoutManager() instanceof GridLayoutManager){
            GridLayoutManager layoutManager=(GridLayoutManager)recyclerView.getLayoutManager();
            if(layoutManager!=null){
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                Log.e("canPullDown","lastVisibleItem:"+ lastVisibleItem +"totalItemCount:"+totalItemCount+"dx:"+dx);
                return lastVisibleItem >= totalItemCount - 1 && dx > 0;
            }
        }else if(recyclerView.getLayoutManager() instanceof LinearLayoutManager){
            LinearLayoutManager layoutManager=(LinearLayoutManager)recyclerView.getLayoutManager();
            if(layoutManager!=null){
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                return lastVisibleItem >= totalItemCount - 1 && dx > 0;
            }
        }
        return false;
    }
}
