package com.example.firstapplication.javaClass;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import jxl.*;
import jxl.read.biff.BiffException;

public class ReadExcel {

    protected String filePath;
    protected List list = new ArrayList();
    InputStream is;

    public ReadExcel(String filePath) {
        this.filePath = filePath;
    }

    public ReadExcel(InputStream is) {
        this.is = is;
    }

    public void readExcel() throws IOException, BiffException {
        //创建输入流
        InputStream stream = new FileInputStream(filePath);
        //获取Excel文件对象
        Workbook rwb = Workbook.getWorkbook(stream);
        //获取文件的指定工作表 默认的第一个
        Sheet sheet = rwb.getSheet(0);
        //行数(表头的目录不需要，从1开始)
        for (int i = 0; i < sheet.getRows(); i++) {
            //创建一个数组 用来存储每一列的值
            String[] str = new String[sheet.getColumns()];
            Cell cell = null;
            //列数
            for (int j = 0; j < sheet.getColumns(); j++) {
                //获取第i行，第j列的值
                cell = sheet.getCell(j, i);
                str[j] = cell.getContents();
            }
            //把刚获取的列存入list
            list.add(str);
        }
    }

    public void outData() {
        for (int i = 0; i < list.size(); i++) {
            String[] str = (String[]) list.get(i);
            for (int j = 0; j < str.length; j++) {
                System.out.print(str[j] + '\t');
            }
            System.out.println();
        }
    }
    /*
    public static void main(String args[]) throws BiffException, IOException{
        ReadExcel excel = new ReadExcel("E:\\student1.xls");
        excel.readExcel();
        excel.outData();
    }
    */
}
