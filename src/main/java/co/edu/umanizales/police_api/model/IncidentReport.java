package co.edu.umanizales.police_api.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

 //reporte de incidente relacionado con el caso

@Setter
@Getter
public class IncidentReport implements Identifiable {
    private UUID id;
    private UUID caseId;
    private String reporterName;
    private String details;
    private LocalDateTime reportedAt;

    public IncidentReport() {
        this.id = UUID.randomUUID();
    }

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
