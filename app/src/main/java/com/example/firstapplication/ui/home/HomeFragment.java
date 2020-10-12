package com.example.firstapplication.ui.home;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
import java.util.List;
import java.util.Objects;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class HomeFragment extends Fragment {
    //private Activity activity;
    //private ListView companyListView;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //companyListView = (ListView) view.findViewById(R.id.chart);
        LineChart lineChart = (LineChart) view.findViewById(R.id.chart);
        float[] x = new float[]{1.0f, 2.0f, 3.0f, 4.0f, 5.0f};
        float[] y = new float[]{1.0f, -3.0f, 3.0f, 4.0f, 8.0f};
        //AssetManager assetManager = getContext().getAssets();
        InputStream is = null;
        try {
            is = requireActivity().getAssets().open("excelReadTest.xls");
        } catch (IOException e) {
            e.printStackTrace();
        }
        int i = 0;
        Chart chart = new Chart(is);
        //storage/emulated/0/Android/data/cn.wps.moffice_eng/.Cloud/cn/294959843/f/" +"048b2115-6680-4743-b1ae-0413ada47a9d/
        chart.GetPointFromSheet();
        try {
            chart.DrawExcelData(lineChart);
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }

}
