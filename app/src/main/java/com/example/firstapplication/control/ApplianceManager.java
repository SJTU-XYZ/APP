package com.example.firstapplication.control;

import android.app.Activity;

import com.example.firstapplication.javaClass.Chart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
    private float powerSum;
    private final float feeThreshold = 0.656f;

    public ApplianceManager(Activity activity) {
        appliances = new ArrayList<>();
        PVData = new ArrayList<>();
        feeData = new ArrayList<>();
        try {
            PVChart = new Chart(activity.getAssets().open("final.xls"));
            feeChart = new Chart(activity.getAssets().open("final.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        hours = PVChart.points.size();
        GetData();
        for (int i = 0; i < appliances.size(); i++) {
            appliances.get(i).autoState = AutoState_e.AUTO_ON;
            appliances.get(i).StateSwitch();
        }
    }

    private void GetData() {
        for (int i = 0; i < hours; i++) {
            PVData.add(PVChart.points.get(i).Y());
            feeData.add(feeChart.points.get(i).Y());
        }
    }

    public void Add(Appliance app) {
        appliances.add(app);
    }

    private void PowerSum() {
        for (int i = 0; i < appliances.size(); i++) {
            if (appliances.get(i).state == State_e.ON) {
                powerSum += appliances.get(i).GetPowConsumption();
            }
        }
    }

    public void StartEmulate() {
        for (int hour = 0; hour < hours; hour++) {
            PowerSum();
            PVGeneration += PVData.get(hour);
            if (PVGeneration - powerSum < 0) {
                for (int i = 0; i < appliances.size(); i++) {
                    if (appliances.get(i).mode == Mode_e.AUTO && appliances.get(i).type == ApplianceType.Unnecessary) {
                        appliances.get(i).autoState = AutoState_e.AUTO_OFF;
                    }
                    appliances.get(i).StateSwitch();
                }
            } else {
                for (int i = 0; i < appliances.size(); i++) {
                    if (feeData.get(hour) >= feeThreshold && appliances.get(i).mode == Mode_e.AUTO && appliances.get(i).type == ApplianceType.Unnecessary) {
                        appliances.get(i).autoState = AutoState_e.AUTO_OFF;
                    }
                    appliances.get(i).StateSwitch();
                }
            }
            PowerSum();
            PVGeneration -= powerSum;
            if (PVGeneration < 0) feeSum += (-PVGeneration) * feeData.get(hour);
        }
    }
}
