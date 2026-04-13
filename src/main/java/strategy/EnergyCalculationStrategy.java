
package strategy;
/**
 *
 * @author docie
 */
public interface EnergyCalculationStrategy {
    double calculateChargeTime(double batteryLevel, double batteryCapacity);
}