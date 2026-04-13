
package observer;
/**
 *
 * @author docie
 */
public
import model.MaintenanceAlert;

public interface AlertObserver {
    void update(MaintenanceAlert alert);
}