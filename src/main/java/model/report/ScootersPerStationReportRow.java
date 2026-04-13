package model.report;

public class ScootersPerStationReportRow {

    private String stationName;
    private String location;
    private int capacity;
    private int scooterCount;
    private int availableScooters;
    private String scooterList;

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getScooterCount() {
        return scooterCount;
    }

    public void setScooterCount(int scooterCount) {
        this.scooterCount = scooterCount;
    }

    public int getAvailableScooters() {
        return availableScooters;
    }

    public void setAvailableScooters(int availableScooters) {
        this.availableScooters = availableScooters;
    }

    public String getScooterList() {
        return scooterList;
    }

    public void setScooterList(String scooterList) {
        this.scooterList = scooterList;
    }
}
