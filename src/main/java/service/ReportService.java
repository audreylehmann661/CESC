package service;

import DAO.ReportDAO;
import model.report.CreditDebitReportRow;
import model.report.MonthlyPaymentReportRow;
import model.report.ScootersPerStationReportRow;

import java.sql.Connection;
import java.util.List;

public class ReportService {

    private final ReportDAO reportDAO;

    public ReportService(Connection conn) {
        this.reportDAO = new ReportDAO(conn);
    }

    public List<CreditDebitReportRow> getCreditsAndDebitsReport() {
        return reportDAO.getCreditsAndDebitsReport();
    }

    public List<MonthlyPaymentReportRow> getMonthlyPaymentsReport() {
        return reportDAO.getMonthlyPaymentsReport();
    }

    public List<ScootersPerStationReportRow> getScootersPerStationReport() {
        return reportDAO.getScootersPerStationReport();
    }
}
