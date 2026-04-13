
package model;

/**
 *
 * @author docie
 */
import java.time.LocalDateTime;

public class MaintenanceAlert {
    private int id;
    private int scooterId;
    private String issueType;
    private String message;
    private LocalDateTime createdAt;
    private String status; // OPEN, SCHEDULED, RESOLVED

    public MaintenanceAlert() {
    }

    public MaintenanceAlert(int id, int scooterId, String issueType, String message,
                            LocalDateTime createdAt, String status) {
        this.id = id;
        this.scooterId = scooterId;
        this.issueType = issueType;
        this.message = message;
        this.createdAt = createdAt;
        this.status = status;
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

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}