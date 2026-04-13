package DAO;

import model.report.CreditDebitReportRow;
import model.report.MonthlyPaymentReportRow;
import model.report.ScootersPerStationReportRow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {

    private final Connection conn;

    public ReportDAO(Connection conn) {
        this.conn = conn;
    }

    public List<CreditDebitReportRow> getCreditsAndDebitsReport() {
        List<CreditDebitReportRow> rows = new ArrayList<>();
        String sql = "SELECT u.name AS user_name, wt.transaction_type, wt.amount, wt.description, wt.transaction_date " +
                "FROM wallet_transactions wt " +
                "JOIN users u ON u.id = wt.user_id " +
                "ORDER BY wt.transaction_date DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                CreditDebitReportRow row = new CreditDebitReportRow();
                row.setUserName(rs.getString("user_name"));
                row.setTransactionType(rs.getString("transaction_type"));
                row.setAmount(rs.getBigDecimal("amount"));
                row.setDescription(rs.getString("description"));
                row.setTransactionDate(rs.getTimestamp("transaction_date"));
                rows.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }

    public List<MonthlyPaymentReportRow> getMonthlyPaymentsReport() {
        List<MonthlyPaymentReportRow> rows = new ArrayList<>();
        String sql = "SELECT DATE_FORMAT(transaction_date, '%Y-%m') AS month_label, " +
                "SUM(CASE WHEN transaction_type = 'CREDIT' THEN amount ELSE 0 END) AS total_credits, " +
                "SUM(CASE WHEN transaction_type = 'DEBIT' THEN amount ELSE 0 END) AS total_debits, " +
                "GREATEST(SUM(CASE WHEN transaction_type = 'DEBIT' THEN amount ELSE 0 END) - " +
                "SUM(CASE WHEN transaction_type = 'CREDIT' THEN amount ELSE 0 END), 0) AS amount_due, " +
                "DATE_FORMAT(DATE_ADD(LAST_DAY(transaction_date), INTERVAL 10 DAY), '%Y-%m-%d') AS due_by " +
                "FROM wallet_transactions " +
                "GROUP BY DATE_FORMAT(transaction_date, '%Y-%m'), LAST_DAY(transaction_date) " +
                "ORDER BY month_label";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                MonthlyPaymentReportRow row = new MonthlyPaymentReportRow();
                row.setMonthLabel(rs.getString("month_label"));
                row.setTotalCredits(rs.getBigDecimal("total_credits"));
                row.setTotalDebits(rs.getBigDecimal("total_debits"));
                row.setAmountDue(rs.getBigDecimal("amount_due"));
                row.setDueBy(rs.getString("due_by"));
                rows.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }

    public List<ScootersPerStationReportRow> getScootersPerStationReport() {
        List<ScootersPerStationReportRow> rows = new ArrayList<>();
        String sql = "SELECT s.name AS station_name, s.location, s.capacity, COUNT(sc.id) AS scooter_count, " +
                "SUM(CASE WHEN sc.status = 'AVAILABLE' THEN 1 ELSE 0 END) AS available_scooters, " +
                "COALESCE(GROUP_CONCAT(CONCAT(sc.code, ' - ', sc.make, ' ', sc.model, ' (', sc.color, ')') " +
                "ORDER BY sc.code SEPARATOR ', '), 'No scooters assigned') AS scooter_list " +
                "FROM stations s " +
                "LEFT JOIN scooters sc ON sc.station_id = s.id " +
                "GROUP BY s.id, s.name, s.location, s.capacity " +
                "ORDER BY scooter_count DESC, s.name";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ScootersPerStationReportRow row = new ScootersPerStationReportRow();
                row.setStationName(rs.getString("station_name"));
                row.setLocation(rs.getString("location"));
                row.setCapacity(rs.getInt("capacity"));
                row.setScooterCount(rs.getInt("scooter_count"));
                row.setAvailableScooters(rs.getInt("available_scooters"));
                row.setScooterList(rs.getString("scooter_list"));
                rows.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }
}
