package DAO;

import model.Scooter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ScooterDAO {

    private final Connection conn;

    public ScooterDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean create(Scooter scooter) {
        String sql = "INSERT INTO scooters(code, make, model, color, battery_capacity, battery_level, status, sponsor_user_id, station_id, last_service_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, scooter.getCode());
            stmt.setString(2, scooter.getMake());
            stmt.setString(3, scooter.getModel());
            stmt.setString(4, scooter.getColor());
            stmt.setInt(5, scooter.getBatteryCapacity());
            stmt.setInt(6, scooter.getBatteryLevel());
            stmt.setString(7, scooter.getStatus());
            if (scooter.getSponsorUserId() == null) {
                stmt.setNull(8, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(8, scooter.getSponsorUserId());
            }
            if (scooter.getStationId() == null) {
                stmt.setNull(9, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(9, scooter.getStationId());
            }
            stmt.setDate(10, scooter.getLastServiceDate());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Scooter findById(int id) {
        String sql = "SELECT * FROM scooters WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Scooter> findAll() {
        List<Scooter> scooters = new ArrayList<>();
        String sql = "SELECT * FROM scooters ORDER BY code";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                scooters.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scooters;
    }

    public boolean update(Scooter scooter) {
        String sql = "UPDATE scooters SET code = ?, make = ?, model = ?, color = ?, battery_capacity = ?, battery_level = ?, status = ?, sponsor_user_id = ?, station_id = ?, last_service_date = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, scooter.getCode());
            stmt.setString(2, scooter.getMake());
            stmt.setString(3, scooter.getModel());
            stmt.setString(4, scooter.getColor());
            stmt.setInt(5, scooter.getBatteryCapacity());
            stmt.setInt(6, scooter.getBatteryLevel());
            stmt.setString(7, scooter.getStatus());
            if (scooter.getSponsorUserId() == null) {
                stmt.setNull(8, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(8, scooter.getSponsorUserId());
            }
            if (scooter.getStationId() == null) {
                stmt.setNull(9, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(9, scooter.getStationId());
            }
            stmt.setDate(10, scooter.getLastServiceDate());
            stmt.setInt(11, scooter.getId());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM scooters WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Scooter mapRow(ResultSet rs) throws Exception {
        Scooter scooter = new Scooter();
        scooter.setId(rs.getInt("id"));
        scooter.setCode(rs.getString("code"));
        scooter.setMake(rs.getString("make"));
        scooter.setModel(rs.getString("model"));
        scooter.setColor(rs.getString("color"));
        scooter.setBatteryCapacity(rs.getInt("battery_capacity"));
        scooter.setBatteryLevel(rs.getInt("battery_level"));
        scooter.setStatus(rs.getString("status"));
        int sponsorUserId = rs.getInt("sponsor_user_id");
        scooter.setSponsorUserId(rs.wasNull() ? null : sponsorUserId);
        int stationId = rs.getInt("station_id");
        scooter.setStationId(rs.wasNull() ? null : stationId);
        scooter.setLastServiceDate(rs.getDate("last_service_date"));
        scooter.setCreatedAt(rs.getTimestamp("created_at"));
        return scooter;
    }
}
