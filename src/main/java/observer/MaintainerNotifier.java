
package observer;
/**
 *
 * @author docie
 */
import model.MaintenanceAlert;

public class MaintainerNotifier implements AlertObserver {

    @Override
    public void update(MaintenanceAlert alert) {
        System.out.println("Maintenance alert received: Scooter #" 
                + alert.getScooterId() + " - " + alert.getMessage());
    }
}