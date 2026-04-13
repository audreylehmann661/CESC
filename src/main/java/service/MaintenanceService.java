
package service;
/**
 *
 * @author docie
 */
import java.time.LocalDate;
import java.time.LocalDateTime;
import model.MaintenanceAlert;
import model.MaintenanceTask;
import observer.MaintenanceAlertSystem;

public class MaintenanceService {

    private final MaintenanceAlertSystem alertSystem;

    public MaintenanceService(MaintenanceAlertSystem alertSystem) {
        this.alertSystem = alertSystem;
    }

    public MaintenanceAlert detectIssue(int scooterId, double batteryLevel, double usageHours) {
        if (batteryLevel < 15.0) {
            MaintenanceAlert alert = new MaintenanceAlert(
                    0,
                    scooterId,
                    "BATTERY",
                    "Battery level critically low",
                    LocalDateTime.now(),
                    "OPEN"
            );
            alertSystem.createAlert(alert);
            return alert;
        }

        if (usageHours > 100.0) {
            MaintenanceAlert alert = new MaintenanceAlert(
                    0,
                    scooterId,
                    "WEAR",
                    "Scooter requires inspection due to high usage hours",
                    LocalDateTime.now(),
                    "OPEN"
            );
            alertSystem.createAlert(alert);
            return alert;
        }

        return null;
    }

    public MaintenanceTask scheduleTask(int scooterId, String description, String assignedTo, LocalDate date) {
        return new MaintenanceTask(0, scooterId, description, date, assignedTo, "PENDING");
    }
}