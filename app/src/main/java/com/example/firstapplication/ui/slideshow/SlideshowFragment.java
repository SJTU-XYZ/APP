package com.example.firstapplication.ui.slideshow;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.firstapplication.R;
import com.example.firstapplication.control.Appliance;
import com.example.firstapplication.control.ApplianceType;
import com.example.firstapplication.control.ApplianceManager;
import com.example.firstapplication.control.Mode_e;
import com.example.firstapplication.control.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private EditText inputAppliance;
    private CreateAddAppDialog addDialog;
    private SettingAppDialog settingDialog;
    private ApplianceManager appManager;
    private View.OnClickListener onClickListener;
    private Button addBtn;

    private ListView listView;

    private int settingIndex;
    //private List<Appliance> appList = appManager.appliances;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);
        addBtn = (Button)view.findViewById(R.id.btn_add);
        appManager = new ApplianceManager();

        addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getId() == R.id.btn_save) {
                            String name = addDialog.text_name.getText().toString().trim();
                            String power = addDialog.text_power.getText().toString().trim();
                            Appliance app = new Appliance(name, Float.parseFloat(power), addDialog.type, Mode_e.AUTO);
                            appManager.Add(app);
                            if(addDialog.text_name != null && addDialog.text_power != null) {
                                addDialog.cancel();
                            }
                        }
                    }
                };

                addDialog = new CreateAddAppDialog(getActivity(), R.style.AppTheme, onClickListener);
                addDialog.show();
            }
        });

        this.listView = (ListView) view.findViewById(R.id.listView);
        final MyAdapter adapter = new MyAdapter(getContext(), appManager.appliances);
        listView.setAdapter(adapter);
        //ListView item的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), "Click item" + i, Toast.LENGTH_SHORT).show();
            }
        });
        //ListView item 中的删除按钮的点击事件
        adapter.setOnItemDeleteClickListener(new MyAdapter.onItemDeleteListener() {
            @Override
            public void onDeleteClick(int i) {
                appManager.appliances.remove(i);
                adapter.notifyDataSetChanged();
            }
        });

        adapter.setOnItemSettingClickListener(new MyAdapter.onItemSettingListener() {
            @Override
            public void onSettingClick(int i) {
                settingIndex = i;
                onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getId() == R.id.btn_mode_save) {
                            appManager.appliances.get(settingIndex).SetMode(settingDialog.mode);
                            settingDialog.cancel();
                        }
                    }
                };

                settingDialog = new SettingAppDialog(getActivity(), R.style.AppTheme, onClickListener);
                settingDialog.show();
            }
        });

        return view;
    }
}