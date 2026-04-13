

package model;
/**
 *
 * @author docie
 */
public class BatteryStatus {
    private int scooterId;
    private double batteryLevel;
    private double chargeTimeRemaining;
    private boolean lowBattery;

    public BatteryStatus() {
    }

    public BatteryStatus(int scooterId, double batteryLevel, double chargeTimeRemaining, boolean lowBattery) {
        this.scooterId = scooterId;
        this.batteryLevel = batteryLevel;
        this.chargeTimeRemaining = chargeTimeRemaining;
        this.lowBattery = lowBattery;
    }

    public int getScooterId() {
        return scooterId;
    }

    public void setScooterId(int scooterId) {
        this.scooterId = scooterId;
    }

    public double getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(double batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public double getChargeTimeRemaining() {
        return chargeTimeRemaining;
    }

    public void setChargeTimeRemaining(double chargeTimeRemaining) {
        this.chargeTimeRemaining = chargeTimeRemaining;
    }

    public boolean isLowBattery() {
        return lowBattery;
    }

    public void setLowBattery(boolean lowBattery) {
        this.lowBattery = lowBattery;
    }
}