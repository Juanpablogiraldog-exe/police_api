package co.edu.umanizales.police_api.model;

// CSV header: id,caseId,reporterName,details,reportedAt

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * A basic initial report for an incident related to a case.
 */
public class IncidentReport implements Identifiable {
    private UUID id;
    private UUID caseId;
    private String reporterName;
    private String details;
    private LocalDateTime reportedAt;

    public IncidentReport() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getCaseId() { return caseId; }
    public void setCaseId(UUID caseId) { this.caseId = caseId; }

    public String getReporterName() { return reporterName; }
    public void setReporterName(String reporterName) { this.reporterName = reporterName; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public LocalDateTime getReportedAt() { return reportedAt; }
    public void setReportedAt(LocalDateTime reportedAt) { this.reportedAt = reportedAt; }

    @Override
    public String toCsv() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String rn = reporterName == null ? "" : reporterName.replace(",", "");
        String de = details == null ? "" : details.replace(",", "");
        String cd = caseId != null ? caseId.toString() : "";
        String ra = reportedAt != null ? reportedAt.format(dtf) : "";
        return (id != null ? id.toString() : "") + "," + cd + "," + rn + "," + de + "," + ra;
    }

    public static IncidentReport fromCsv(String line) {
        String[] p = line.split(",", -1);
        IncidentReport r = new IncidentReport();
        if (!p[0].isEmpty()) r.setId(UUID.fromString(p[0]));
        r.setCaseId(p[1].isEmpty() ? null : UUID.fromString(p[1]));
        r.setReporterName(p[2]);
        r.setDetails(p[3]);
        r.setReportedAt(p[4].isEmpty() ? null : LocalDateTime.parse(p[4]));
        return r;
    }
}
