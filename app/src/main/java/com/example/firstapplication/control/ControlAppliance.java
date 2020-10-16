package com.example.firstapplication.control;

import java.util.LinkedList;

public class ControlAppliance {
    private float electricityBill;
    private float PVGeneration;
    private float allPowerConsumption;
    private LinkedList<Appliance> appliances;

    public ControlAppliance() {
        appliances = new LinkedList<>();
    }
    public void Add(Appliance app) {
        appliances.add(app);
    }
}
