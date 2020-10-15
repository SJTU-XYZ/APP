package com.example.firstapplication.bluetooth;

import android.annotation.SuppressLint;

import com.calypso.bluelib.bean.SearchResult;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.firstapplication.R;

import java.util.List;

public class DeviceListAdapter extends BaseQuickAdapter<SearchResult, BaseViewHolder> {

    public DeviceListAdapter(int layoutResId, List<SearchResult> data) {
        super(layoutResId, data);
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void convert(BaseViewHolder helper, SearchResult item) {
        helper.setText(R.id.name, item.getName());
        helper.setText(R.id.mac, item.getAddress());
        helper.setText(R.id.rssi, String.format("Rssi: %d", item.getRssi()));
    }
}