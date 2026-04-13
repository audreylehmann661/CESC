
package model;
/**
 *
 * @author docie
 */
import java.time.LocalDate;

public class MaintenanceTask {
    private int id;
    private int scooterId;
    private String taskDescription;
    private LocalDate scheduledDate;
    private String assignedTo;
    private String status; // PENDING, IN_PROGRESS, DONE

    public MaintenanceTask() {
    }

    public MaintenanceTask(int id, int scooterId, String taskDescription,
                           LocalDate scheduledDate, String assignedTo, String status) {
        this.id = id;
        this.scooterId = scooterId;
        this.taskDescription = taskDescription;
        this.scheduledDate = scheduledDate;
        this.assignedTo = assignedTo;
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

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}