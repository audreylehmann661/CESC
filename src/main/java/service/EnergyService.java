
package service;
/**
 *
 * @author docie
 */
import model.BatteryStatus;
import strategy.EnergyCalculationStrategy;

public class EnergyService {

    private final EnergyCalculationStrategy strategy;

    public EnergyService(EnergyCalculationStrategy strategy) {
        this.strategy = strategy;
    }

    public BatteryStatus getBatteryStatus(int scooterId, double batteryLevel, double batteryCapacity) {
        double chargeTime = strategy.calculateChargeTime(batteryLevel, batteryCapacity);
        boolean lowBattery = batteryLevel < 20.0;

        return new BatteryStatus(scooterId, batteryLevel, chargeTime, lowBattery);
    }
}