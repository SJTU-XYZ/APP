package com.example.firstapplication.javaClass;

import android.content.res.AssetManager;
import android.graphics.Color;

import com.example.firstapplication.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Chart extends ReadExcel {
    private List<Point> points = new ArrayList<>();

    public Chart(String filePath) {
        super(filePath);
    }
    public Chart(InputStream is) {
        super(is);
    }

    public void Draw(LineChart chart, float[] x, float[] y) {
        // 你要渲染的数据
        //float[] dataObjects = {1.0f, 2.0f, 3.0f, 4.0f, 5.0f};
        List<Entry> entries = new ArrayList<>();
        // 循环你的数据，向图表中添加点
        for (int i = 0; i < x.length; i++) {
            // turn your data into Entry objects
            // 图形横纵坐标默认为float形式，如果想展示文字形式，需要自定义适配器。后边会讲，这个地方传进去的X轴Y轴值都是float类型
            entries.add(new Entry(x[i], y[i]));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Label"); // 图表绑定数据，设置图表折现备注

        dataSet.setColor(Color.RED); // 设置折线图颜色
        dataSet.setValueTextColor(Color.BLUE); // 设置数据值的颜色

        Description description = chart.getDescription();
        description.setText("电价"); // 设置右下角备注

        LineData lineData = new LineData(dataSet);

        chart.setData(lineData); // 图表绑定数据值
        chart.invalidate(); // 刷新图表

    }

    public void DrawExcelData(LineChart lineChart, int day, String label, int color) {
        List<Entry> entries = new ArrayList<>();
        // 循环你的数据，向图表中添加点
        for (int i = day * 24; i < (day + 1) * 24; i++) {
            // turn your data into Entry objects
            entries.add(new Entry( points.get(i).X(), points.get(i).Y()));
        }

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setTextColor(Color.WHITE);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setDrawGridLines(false);
        yAxis.setAxisLineColor(Color.WHITE);
        lineChart.getAxisRight().setEnabled(false);
        yAxis.setTextColor(Color.WHITE);

        LineDataSet dataSet = new LineDataSet(entries, label); // 图表绑定数据，设置图表折现备注

        dataSet.setColor(color); // 设置折线图颜色
        dataSet.setValueTextColor(Color.WHITE); // 设置数据值的颜色
        dataSet.setDrawCircles(false);

        Description description = lineChart.getDescription();
        description.setText("小时"); // 设置右下角备注
        description.setTextColor(Color.WHITE);

        LineData lineData = new LineData(dataSet);

        lineChart.setData(lineData); // 图表绑定数据值
        lineChart.invalidate(); // 刷新图表
    }

    public void GetPointFromSheet(int sheetNum, int col) {
        int i;
        Workbook book;
        Sheet sheet;
        Cell x, y;
        try {
            book = Workbook.getWorkbook(is);
            sheet = book.getSheet(sheetNum);
            i = 1;
            while (i < 289) {//你的表格行数
                //获取每一行的单元格
                x = sheet.getCell(0, i);//（列，行）
                y = sheet.getCell(col, i);
                Point user = new Point();
                //读取到的参数
                user.Set(Float.parseFloat(x.getContents()), Float.parseFloat(y.getContents()));
                points.add(user);
                i++;
            }
            //book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
