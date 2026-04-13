package DAO;

import model.WalletTransaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class WalletTransactionDAO {

    private final Connection conn;

    public WalletTransactionDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean create(WalletTransaction transaction) {
        String sql = "INSERT INTO wallet_transactions(user_id, transaction_type, amount, description, transaction_date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transaction.getUserId());
            stmt.setString(2, transaction.getTransactionType());
            stmt.setBigDecimal(3, transaction.getAmount());
            stmt.setString(4, transaction.getDescription());
            stmt.setTimestamp(5, transaction.getTransactionDate());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public WalletTransaction findById(int id) {
        String sql = "SELECT * FROM wallet_transactions WHERE id = ?";
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

    public List<WalletTransaction> findAll() {
        List<WalletTransaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM wallet_transactions ORDER BY transaction_date DESC";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                transactions.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public boolean update(WalletTransaction transaction) {
        String sql = "UPDATE wallet_transactions SET user_id = ?, transaction_type = ?, amount = ?, description = ?, transaction_date = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transaction.getUserId());
            stmt.setString(2, transaction.getTransactionType());
            stmt.setBigDecimal(3, transaction.getAmount());
            stmt.setString(4, transaction.getDescription());
            stmt.setTimestamp(5, transaction.getTransactionDate());
            stmt.setInt(6, transaction.getId());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM wallet_transactions WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private WalletTransaction mapRow(ResultSet rs) throws Exception {
        WalletTransaction transaction = new WalletTransaction();
        transaction.setId(rs.getInt("id"));
        transaction.setUserId(rs.getInt("user_id"));
        transaction.setTransactionType(rs.getString("transaction_type"));
        transaction.setAmount(rs.getBigDecimal("amount"));
        transaction.setDescription(rs.getString("description"));
        transaction.setTransactionDate(rs.getTimestamp("transaction_date"));
        return transaction;
    }
}
