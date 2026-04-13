
package service;
/**
 *
 * @author docie
 */
import java.time.LocalDateTime;
import model.Location;
import model.TrackingRecord;

public class TrackingService {

    public TrackingRecord updateTracking(int scooterId, double latitude, double longitude,
                                         String stationName, String lastStation,
                                         double timeAwayFromStationHours) {

        String status = (stationName != null && !stationName.isBlank()) ? "AT_STATION" : "IN_TRANSIT";

        Location location = new Location(latitude, longitude, stationName);

        return new TrackingRecord(
                0,
                scooterId,
                location,
                status,
                lastStation,
                LocalDateTime.now(),
                timeAwayFromStationHours
        );
    }
}