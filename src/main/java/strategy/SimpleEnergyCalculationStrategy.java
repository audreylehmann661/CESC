
package strategy;
/**
 *
 * @author docie
 */
public class SimpleEnergyCalculationStrategy implements EnergyCalculationStrategy {

    @Override
    public double calculateChargeTime(double batteryLevel, double batteryCapacity) {
        double remainingPercent = 100.0 - batteryLevel;
        return (remainingPercent / 100.0) * batteryCapacity;
    }
}