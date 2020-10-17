package com.example.firstapplication.control;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ApplianceManager {
    private float electricityBill;
    private float PVGeneration;
    private float allPowerConsumption;
    public List<Appliance> appliances;

    public ApplianceManager() {
        appliances = new ArrayList<>();
    }

    public void Add(Appliance app) {
        appliances.add(app);
    }
}
