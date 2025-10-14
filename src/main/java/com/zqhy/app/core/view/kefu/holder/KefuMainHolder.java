package com.zqhy.app.core.view.kefu.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.kefu.KefuInfoVo;
import com.zqhy.app.core.tool.MResource;
import com.zqhy.app.core.view.FragmentHolderActivity;
import com.zqhy.app.core.view.kefu.KefuListFragment;
import com.zqhy.app.newproject.R;
import com.zqhy.app.widget.InnerGridView;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/11
 */

public class KefuMainHolder extends AbsItemHolder<KefuInfoVo.DataBean,KefuMainHolder.ViewHolder> {

    public KefuMainHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_kefu_main;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull KefuInfoVo.DataBean item) {
        holder.mTvKefuTitle1.setText(item.getTitle());
        int resId = MResource.getResourceId(mContext, "mipmap", item.getRes());
        holder.mIcKefuIcon.setImageResource(resId);

        ItemAdapter mItemAdapter = new ItemAdapter(item.getItems());
        holder.mGrid.setAdapter(mItemAdapter);

        holder.mGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                toKefuItemDetail(item, position);
            }
        });
        holder.mLlKefuItem.setOnClickListener(v -> {
            toKefuItemDetail(item, -1);
        });
    }

    public class ViewHolder extends AbsHolder {
        private LinearLayout mLlKefuItem;
        private ImageView mIcKefuIcon;
        private TextView mTvKefuTitle1;
        private InnerGridView mGrid;

        public ViewHolder(View view) {
            super(view);
            mLlKefuItem = view.findViewById(R.id.ll_kefu_item);
            mIcKefuIcon = view.findViewById(R.id.ic_kefu_icon);
            mTvKefuTitle1 = view.findViewById(R.id.tv_kefu_title_1);
            mGrid = view.findViewById(R.id.grid);

        }

    }

    class ItemAdapter extends BaseAdapter {

        private List<KefuInfoVo.ItemsBean> itemsBeans;

        public ItemAdapter(List<KefuInfoVo.ItemsBean> itemsBeans) {
            this.itemsBeans = itemsBeans;
        }

        @Override
        public int getCount() {
            return itemsBeans == null ? 0 : itemsBeans.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return itemsBeans.get(i).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ItemViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ItemViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_kefu_main_titles, null);
                viewHolder.mTitle = convertView.findViewById(R.id.tv_title);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ItemViewHolder) convertView.getTag();
            }

            KefuInfoVo.ItemsBean itemsBean = itemsBeans.get(position);
            if (itemsBean != null) {
                viewHolder.mTitle.setText(itemsBean.getTitle1());
            }

            return convertView;
        }
    }

    class ItemViewHolder {
        private TextView mTitle;
    }


    public void toKefuItemDetail(KefuInfoVo.DataBean kefuItemBean, int position) {
        BaseFragment fragment = KefuListFragment.newInstance(kefuItemBean.getTitle(), position, kefuItemBean.getItems());

        FragmentHolderActivity.startFragmentInActivity(mContext,fragment);
    }
}
