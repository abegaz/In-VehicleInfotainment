package Model;

public class HVAC_Model {
    private byte temperature = 0; //temperature of vehicle
    private boolean ac = false, heater = false, recycle_air = false; //all on and off booleans for the rest

    //getters and setters for HVAC variables
    public String getTemperature() {
        return temperature+"°";
    }

    public void setTemperature(byte temperature) {
        this.temperature = temperature;
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
