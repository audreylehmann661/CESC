

package observer;
/**
 *
 * @author docie
 */
public interface AlertSubject {
    void addObserver(AlertObserver observer);
    void removeObserver(AlertObserver observer);
    void notifyObservers();
}