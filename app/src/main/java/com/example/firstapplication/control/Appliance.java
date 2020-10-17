package com.example.firstapplication.control;

public class Appliance {
    public String name;
    public float power;
    public Mode_e mode;
    public State_e state;
    public AutoState_e autoState;
    public ApplianceType type;
    private float powerConsumption;

    public Appliance(String name, float power, ApplianceType type, Mode_e mode) {
        this.name = name;
        this.power = power;
        this.type = type;
        this.mode = mode;
        powerConsumption = 0;
    }

    public float GetPowConsumption() {
        return powerConsumption;
    }

    public void StateSwitch() {
        switch (mode) {
            case AlwaysON:
                state = State_e.ON;
                break;
            case AlwaysOFF:
                state = State_e.OFF;
                break;
            case AUTO:
                switch (autoState) {
                    case AUTO_ON:
                        state = State_e.ON;
                        break;
                    case AUTO_OFF:
                        state = State_e.OFF;
                        break;
                }
                break;
        }

        //if (state == State_e.ON) powerConsumption += power;
    }
}
