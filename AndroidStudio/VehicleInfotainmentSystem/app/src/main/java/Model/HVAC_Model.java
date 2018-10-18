/*
Author(s): Trevor, Connor
Date: 10/18/18
*/
package Model;

public class HVAC_Model {
    private int temperature = 0; //temperature of vehicle
    private boolean ac = false, heater = false, recycle_air = false; //all on and off booleans for the rest

    //getters and setters for HVAC variables
    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(byte temperature) {
        if(this.temperature>90){this.temperature = 90;}
        else if (this.temperature<50){this.temperature = 50;}
    }

    public boolean isAc() {
        return ac;
    }

    public void setAc(boolean ac) {
        this.ac = ac;
    }

    public boolean isHeater() {
        return heater;
    }

    public void setHeater(boolean heater) {
        this.heater = heater;
    }

    public boolean isRecycle_air() {
        return recycle_air;
    }

    public void setRecycle_air(boolean recycle_air) {
        this.recycle_air = recycle_air;
    }




}
