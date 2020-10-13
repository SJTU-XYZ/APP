package com.example.firstapplication.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.firstapplication.MainActivity;
import com.example.firstapplication.R;
import com.example.firstapplication.javaClass.Chart;
import com.example.firstapplication.javaClass.Point;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class HomeFragment extends Fragment {
    //private Activity activity;
    //private ListView companyListView;
    private TextView result = null;
    private Spinner spinner = null;
    private ArrayAdapter<String> adapter = null;
    private static final String[] days = {"第1天", "第2天", "第3天", "第4天", "第5天", "第6天",
                                          "第7天", "第8天", "第9天", "第10天", "第11天", "第12天"};
    private int day = 0;

    private Chart loadChart;
    private Chart PVChart;
    private Chart feeChart;
    private LineChart loadLineChart;
    private LineChart PVLineChart;
    private LineChart feeLineChart;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        loadLineChart = (LineChart) view.findViewById(R.id.loadChart);
        PVLineChart = (LineChart) view.findViewById(R.id.PVChart);
        feeLineChart = (LineChart) view.findViewById(R.id.feeChart);

        InputStream is = null;
        try {
            is = requireActivity().getAssets().open("final.xls");
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadChart = new Chart(is);
        try {
            is = requireActivity().getAssets().open("final.xls");
        } catch (IOException e) {
            e.printStackTrace();
        }
        PVChart = new Chart(is);
        try {
            is = requireActivity().getAssets().open("final.xls");
        } catch (IOException e) {
            e.printStackTrace();
        }
        feeChart = new Chart(is);
        loadChart.GetPointFromSheet(0, 1);
        PVChart.GetPointFromSheet(0,2);
        feeChart.GetPointFromSheet(0,3);

        result = view.findViewById(R.id.result);
        spinner = view.findViewById(R.id.spinner);
        adapter = new ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, days);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将适配器添加到spinner中去
        spinner.setAdapter(adapter);
        //spinner.setVisibility(View.VISIBLE);//设置默认显示
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                day = arg2;
                //result.setText("选择天数：" + ((TextView) arg1).getText());
                loadChart.DrawExcelData(loadLineChart, day, "负荷需求");
                PVChart.DrawExcelData(PVLineChart, day, "光伏曲线");
                feeChart.DrawExcelData(feeLineChart, day, "实时电价");
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        return view;
    }
}
