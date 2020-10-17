package com.example.firstapplication.control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.firstapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private List<Appliance> mList = new ArrayList<>();
    ViewHolder viewHolder = null;

    public MyAdapter(Context context, List<Appliance> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item, null);
            viewHolder.mTextView = (TextView) view.findViewById(R.id.item_tv);
            viewHolder.btnDelete = (Button) view.findViewById(R.id.delete_btn);
            viewHolder.btnSetting= (Button) view.findViewById(R.id.setting_btn);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mTextView.setText(mList.get(i).name);
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemDeleteListener.onDeleteClick(i);
            }
        });

        viewHolder.btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemSettingListener.onSettingClick(i);
            }
        });
        return view;
    }

    /**
     * 删除按钮的监听接口
     */
    public interface onItemDeleteListener {
        void onDeleteClick(int i);
    }

    private onItemDeleteListener mOnItemDeleteListener;

    public void setOnItemDeleteClickListener(onItemDeleteListener mOnItemDeleteListener) {
        this.mOnItemDeleteListener = mOnItemDeleteListener;
    }


    public interface onItemSettingListener {
        void onSettingClick(int i);
    }

    private onItemSettingListener mOnItemSettingListener;

    public void setOnItemSettingClickListener(onItemSettingListener mOnItemSettingListener) {
        this.mOnItemSettingListener = mOnItemSettingListener;
    }


    class ViewHolder {
        TextView mTextView;
        Button btnDelete;
        Button btnSetting;
        int textColor;
    }

    public void SetTextColor(int color) {
        viewHolder.textColor = color;
        viewHolder.mTextView.setTextColor(viewHolder.textColor);
    }
}