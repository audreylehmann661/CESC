
package model;
/**
 *
 * @author docie
 */
public class Location {
    private double latitude;
    private double longitude;
    private String stationName;

    public Location() {
    }

    public Location(double latitude, double longitude, String stationName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.stationName = stationName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}