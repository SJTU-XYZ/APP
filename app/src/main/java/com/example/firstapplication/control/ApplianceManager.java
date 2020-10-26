package com.example.firstapplication.control;

import android.app.Activity;
import android.content.res.AssetManager;

import com.clj.fastble.utils.HexUtil;
import com.example.firstapplication.javaClass.Chart;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 浴霸 OFF 1000
 * 冰箱 ON 2000
 * 喷泉 2000
 * 彩灯 1500
 * 高压电 4000
 */

public class ApplianceManager {
    private float feeSum;
    private float PVGeneration;
    private float PowerConsumption;
    public List<Appliance> appliances;

    private Chart PVChart;
    private Chart feeChart;
    private List<Float> PVData;
    private List<Float> feeData;

    private int hours;
    private int hour;
    private float powerSum;
    private final float feeThreshold = 0.656f;

    private BigInteger sendMsgInteger;
    private String sendMsgStr;
    private int sendMsgInt;

    private List<State_e> states;

    public float GetFee() {return feeSum;}

    public float GetPVGeneration() {return PVGeneration;}

    public ApplianceManager(Activity activity) {
        appliances = new ArrayList<>();
        PVData = new ArrayList<>();
        feeData = new ArrayList<>();
        states = new ArrayList<>();
        try {
            PVChart = new Chart(activity.getAssets().open("typical.xls"));
            feeChart = new Chart(activity.getAssets().open("typical.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        DataInit();
    }

    private void DataInit() {
        PVChart.GetPointFromSheet(0, 2);
        feeChart.GetPointFromSheet(0, 3);
        hours = PVChart.points.size();
        for (int i = 0; i < hours; i++) {
            PVData.add(PVChart.points.get(i).Y());
            feeData.add(feeChart.points.get(i).Y());
        }
        for (int i = 0; i < appliances.size(); i++) {
            appliances.get(i).autoState = AutoState_e.AUTO_ON;
            appliances.get(i).StateSwitch();
        }
    }

    public void Add(Appliance app) {
        appliances.add(app);
    }

    private void PowerSum() {
        powerSum = 0;
        for (int i = 0; i < appliances.size(); i++) {
            if (appliances.get(i).state == State_e.ON) {
                powerSum += appliances.get(i).power;
            }
        }
    }

    public String SendMsg() {
        for (int i = 0; i < appliances.size(); i++) {
            appliances.get(i).StateSwitch();
        }
        sendMsgInt = 0;
        for (int i = 0; i < appliances.size(); i++) {
            if (appliances.get(i).state == State_e.ON) {
                sendMsgInt = sendMsgInt * 2 + 1;
            } else if (appliances.get(i).state == State_e.OFF) {
                sendMsgInt *= 2;
            }
        }
        sendMsgInt += 16;
        sendMsgInteger = new BigInteger(String.valueOf(sendMsgInt));
        sendMsgStr = sendMsgInteger.toString(16);
        return sendMsgStr;
    }

    public void Emulate(int hour) {
        PowerSum();
        PVGeneration += PVData.get(hour);
        if (PVGeneration - powerSum < 0) {
            for (int i = 0; i < appliances.size(); i++) {
                if (feeData.get(hour) > feeThreshold && appliances.get(i).mode == Mode_e.AUTO && appliances.get(i).type == ApplianceType.Unnecessary) {
                    appliances.get(i).autoState = AutoState_e.AUTO_OFF;
                } else {
                    appliances.get(i).autoState = AutoState_e.AUTO_ON;
                }
                appliances.get(i).StateSwitch();
            }
        } else {
        }
        PowerSum();
        PVGeneration -= powerSum;
        if (PVData.get(hour) - powerSum < 0)
            feeSum += (powerSum - PVData.get(hour)) * feeData.get(hour);
    }
}
