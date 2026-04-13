/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model;
/**
 *
 * @author docie
 */
import java.time.LocalDateTime;

public class TrackingRecord {
    private int id;
    private int scooterId;
    private Location location;
    private String status; // AT_STATION or IN_TRANSIT
    private String lastStation;
    private LocalDateTime timestamp;
    private double timeAwayFromStationHours;

    public TrackingRecord() {
    }

    public TrackingRecord(int id, int scooterId, Location location, String status,
                          String lastStation, LocalDateTime timestamp, double timeAwayFromStationHours) {
        this.id = id;
        this.scooterId = scooterId;
        this.location = location;
        this.status = status;
        this.lastStation = lastStation;
        this.timestamp = timestamp;
        this.timeAwayFromStationHours = timeAwayFromStationHours;
    }

    public int getId() {
        return id;
    }

    public int getScooterId() {
        return scooterId;
    }

    public void setScooterId(int scooterId) {
        this.scooterId = scooterId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastStation() {
        return lastStation;
    }

    public void setLastStation(String lastStation) {
        this.lastStation = lastStation;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getTimeAwayFromStationHours() {
        return timeAwayFromStationHours;
    }

    public void setTimeAwayFromStationHours(double timeAwayFromStationHours) {
        this.timeAwayFromStationHours = timeAwayFromStationHours;
    }
}