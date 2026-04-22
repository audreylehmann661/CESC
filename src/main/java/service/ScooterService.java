package service;

import DAO.ScooterDAO;
import model.Scooter;

public class ScooterService {

    private ScooterDAO scooterDAO;

    public ScooterService(ScooterDAO scooterDAO) {
        this.scooterDAO = scooterDAO;
    }

    public boolean registerScooter(String make, String model, String color,
                                   String vehicleNumber, int batteryCapacity, int sponsorId) {

        if (make == null || make.isBlank() ||
            model == null || model.isBlank() ||
            vehicleNumber == null || vehicleNumber.isBlank() ||
            batteryCapacity <= 0) {
            return false;
        }

        if (scooterDAO.getScooterByVehicleNumber(vehicleNumber) != null) {
            return false;
        }

        Scooter scooter = new Scooter.Builder()
                .make(make)
                .model(model)
                .color(color)
                .vehicleNumber(vehicleNumber)
                .batteryCapacity(batteryCapacity)
                .sponsorId(sponsorId)
                .build();

        return scooterDAO.addScooter(scooter);
    }
}
