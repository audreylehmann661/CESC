package DAO;

import model.Station;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StationDAO {

    private final Connection conn;

    public StationDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean create(Station station) {
        String sql = "INSERT INTO stations(name, location, capacity, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, station.getName());
            stmt.setString(2, station.getLocation());
            stmt.setInt(3, station.getCapacity());
            stmt.setString(4, station.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Station findById(int id) {
        String sql = "SELECT * FROM stations WHERE id = ?";
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

    public List<Station> findAll() {
        List<Station> stations = new ArrayList<>();
        String sql = "SELECT * FROM stations ORDER BY name";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                stations.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stations;
    }

    public boolean update(Station station) {
        String sql = "UPDATE stations SET name = ?, location = ?, capacity = ?, status = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, station.getName());
            stmt.setString(2, station.getLocation());
            stmt.setInt(3, station.getCapacity());
            stmt.setString(4, station.getStatus());
            stmt.setInt(5, station.getId());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM stations WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Station mapRow(ResultSet rs) throws Exception {
        Station station = new Station();
        station.setId(rs.getInt("id"));
        station.setName(rs.getString("name"));
        station.setLocation(rs.getString("location"));
        station.setCapacity(rs.getInt("capacity"));
        station.setStatus(rs.getString("status"));
        station.setCreatedAt(rs.getTimestamp("created_at"));
        return station;
    }
}
