package com.example.firstapplication.control;

public class Appliance {
    private String name;
    private float power;
    private Mode_e mode;
    private State_e state;
    private ApplianceType type;
    private float powerConsumption;

    public Appliance(String name, float power, ApplianceType type, Mode_e mode) {
        this.name = name;
        this.power = power;
        this.type = type;
        this.mode = mode;
        powerConsumption = 0;
    }

    public void SetName(String name) {
        this.name = name;
    }

    public void SetState(Mode_e state) {
        this.mode = state;
    }

    public void SetPower(float power) {
        this.power = power;
    }

    public String GetName() {
        return this.name;
    }

    public float GetPowConsumption() {
        return powerConsumption;
    }

    public ApplianceType Type() {return type;}

    public void Work(State_e autoState) {
        switch (mode) {
            case AlwaysON:
                state = State_e.ON;
                break;
            case AlwaysOFF:
                state = State_e.OFF;
                break;
            case AUTO:
                state = autoState;
                break;
        }

        if (state == State_e.ON) powerConsumption += power;
    }
}
