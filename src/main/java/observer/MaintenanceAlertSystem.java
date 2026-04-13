
package observer;
/**
 *
 * @author docie
 */
import java.util.ArrayList;
import java.util.List;
import model.MaintenanceAlert;

public class MaintenanceAlertSystem implements AlertSubject {
    private final List<AlertObserver> observers = new ArrayList<>();
    private MaintenanceAlert currentAlert;

    @Override
    public void addObserver(AlertObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(AlertObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (AlertObserver observer : observers) {
            observer.update(currentAlert);
        }
    }

    public void createAlert(MaintenanceAlert alert) {
        this.currentAlert = alert;
        notifyObservers();
    }
}