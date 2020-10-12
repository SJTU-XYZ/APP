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
    List<Point> points = new ArrayList<>();
    public Chart(String filePath) {
        super(filePath);
    }
    public Chart(InputStream _is) {
        super(_is);
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

    public void Draw(LineChart chart, List _list, String text) {
        List<Entry> entries = new ArrayList<>();
        // 循环你的数据，向图表中添加点
        for (int i = 0; i < _list.size(); i++) {
            // turn your data into Entry objects
            entries.add(new Entry( ((Point)_list.get(i)).X(), ((Point)_list.get(i)).Y() ));
        }

        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis yAxis = chart.getAxisLeft();
        yAxis.setDrawGridLines(false);
        chart.getAxisRight().setEnabled(false);

        LineDataSet dataSet = new LineDataSet(entries, "Label"); // 图表绑定数据，设置图表折现备注

        dataSet.setColor(Color.RED); // 设置折线图颜色
        //dataSet.setValueTextColor(Color.BLUE); // 设置数据值的颜色
        dataSet.setDrawCircles(false);

        Description description = chart.getDescription();
        description.setText(text); // 设置右下角备注

        LineData lineData = new LineData(dataSet);

        chart.setData(lineData); // 图表绑定数据值
        chart.invalidate(); // 刷新图表
    }

    public void DrawExcelData(LineChart lineChart, String text) throws BiffException, IOException {
        //readExcel();
        Draw(lineChart, points, text);
    }

    public void GetPointFromSheet() {
        int i;
        Workbook book;
        Sheet sheet;
        Cell x, y;
        try {
            book = Workbook.getWorkbook(is);
            //获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
            sheet = book.getSheet(0);
            //获取左上角的单元格
            //U_ID = sheet.getCell(0, 0);

            i = 1;
            while (i < 290) {//你的表格行数
                //获取每一行的单元格
                x = sheet.getCell(0, i);//（列，行）
                y = sheet.getCell(1, i);
                Point user = new Point();
                //读取到的参数
                user.Set((float)i, Float.parseFloat(y.getContents())); //x.getContents())
                points.add(user);
                i++;
            }
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
