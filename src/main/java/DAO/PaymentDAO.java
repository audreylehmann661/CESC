package DAO;

import model.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    private final Connection conn;

    public PaymentDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean create(Payment payment) {
        String sql = "INSERT INTO payments(user_id, trip_id, amount, payment_method, payment_status, payment_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, payment.getUserId());
            if (payment.getTripId() == null) {
                stmt.setNull(2, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(2, payment.getTripId());
            }
            stmt.setBigDecimal(3, payment.getAmount());
            stmt.setString(4, payment.getPaymentMethod());
            stmt.setString(5, payment.getPaymentStatus());
            stmt.setTimestamp(6, payment.getPaymentDate());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Payment findById(int id) {
        String sql = "SELECT * FROM payments WHERE id = ?";
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

    public List<Payment> findAll() {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments ORDER BY payment_date DESC";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                payments.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return payments;
    }

    public boolean update(Payment payment) {
        String sql = "UPDATE payments SET user_id = ?, trip_id = ?, amount = ?, payment_method = ?, payment_status = ?, payment_date = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, payment.getUserId());
            if (payment.getTripId() == null) {
                stmt.setNull(2, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(2, payment.getTripId());
            }
            stmt.setBigDecimal(3, payment.getAmount());
            stmt.setString(4, payment.getPaymentMethod());
            stmt.setString(5, payment.getPaymentStatus());
            stmt.setTimestamp(6, payment.getPaymentDate());
            stmt.setInt(7, payment.getId());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM payments WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Payment mapRow(ResultSet rs) throws Exception {
        Payment payment = new Payment();
        payment.setId(rs.getInt("id"));
        payment.setUserId(rs.getInt("user_id"));
        int tripId = rs.getInt("trip_id");
        payment.setTripId(rs.wasNull() ? null : tripId);
        payment.setAmount(rs.getBigDecimal("amount"));
        payment.setPaymentMethod(rs.getString("payment_method"));
        payment.setPaymentStatus(rs.getString("payment_status"));
        payment.setPaymentDate(rs.getTimestamp("payment_date"));
        return payment;
    }
}
