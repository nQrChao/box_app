package com.zqhy.app.core.view.transaction.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zqhy.app.newproject.R;

import java.util.List;


/**
 * Created by FanXia on 2016/5/9 0009.
 */
public class TypeChooseAdapter extends RecyclerView.Adapter {

    private int mSelectedItem = -1;
    private Context mContext;
    private List<String> mLabels;
    private OnItemClickListenter onItemClickListenter;
    private int styleType = 0;

    public TypeChooseAdapter(Context context, List<String> labels) {
        mContext = context;
        mLabels = labels;

    }

    @Override
    public ChooseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_type_text, parent, false);
        return new ChooseHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        String label = mLabels.get(position);
        String[] splits = label.split("@");
        String split1 = "";
        String split2 = "";
        if (splits.length > 0) {
            split1 = splits[0];
        }
        if (splits.length > 1) {
            split2 = splits[1];
        }

        final ChooseHolder chooseHolder = (ChooseHolder) holder;
        chooseHolder.cbCheck.setId(position);
        chooseHolder.cbCheck.setChecked(position == mSelectedItem);
        chooseHolder.cbCheck.setVisibility(View.GONE);

        chooseHolder.tvText.setText(split1);
        if (chooseHolder.cbCheck.isChecked()) {
            chooseHolder.tvText.setTextColor(mContext.getResources().getColor(R.color.color_ff8f19));
        } else {
            chooseHolder.tvText.setTextColor(mContext.getResources().getColor(R.color.primary_text));
        }
        chooseHolder.tvText.getPaint().setFakeBoldText(false);

        if (split2.equals("-1")) {
            chooseHolder.tvText.setTextColor(mContext.getResources().getColor(R.color.color_red));
            chooseHolder.tvText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }
    }


    @Override
    public int getItemCount() {
        return mLabels.size();
    }

    class ChooseHolder extends RecyclerView.ViewHolder {

        public CheckBox cbCheck;
        public TextView tvText;

        public ChooseHolder(View itemView) {
            super(itemView);
            tvText = (TextView) itemView.findViewById(R.id.tv);
            cbCheck = (CheckBox) itemView.findViewById(R.id.add_checkbox);
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedItem = getAdapterPosition();
                    notifyDataSetChanged();
                    if (onItemClickListenter != null) {
                        onItemClickListenter.onItemClick(v, mSelectedItem);
                    }
                }
            };
            itemView.setOnClickListener(clickListener);
            cbCheck.setOnClickListener(clickListener);
        }
    }


    public void releaseSelected() {
        mSelectedItem = -1;
        notifyDataSetChanged();
    }

    public void selectItem(int index) {
        mSelectedItem = index;
        notifyDataSetChanged();
    }

    public void setStyleType(int styleType) {
        this.styleType = styleType;
    }

    public String getItemString(int position) {
        return mLabels.get(position);
    }

    public void addAll(List<String> mDatas) {
        this.mLabels.addAll(mDatas);
        notifyItemRangeInserted(this.mLabels.size() - mDatas.size(), this.mLabels.size());
    }

    public void clear() {
        mLabels.clear();
    }

    public void setOnItemClickListenter(OnItemClickListenter onItemClickListenter) {
        this.onItemClickListenter = onItemClickListenter;
    }
}
