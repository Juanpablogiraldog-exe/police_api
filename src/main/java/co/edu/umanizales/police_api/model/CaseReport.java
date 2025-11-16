package co.edu.umanizales.police_api.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

//reporte del caso

@Setter
@Getter
public class CaseReport implements Identifiable {
    private UUID id;
    private Case case_;
    private String summary;
    private LocalDate createdAt;

    public CaseReport() {
        this.id = UUID.randomUUID();
    }

    //convertir

    @Override
    public String toCsv() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String s = summary == null ? "" : summary.replace(",", "");
        String c = case_ != null ? case_.getId().toString() : "";
        String ca = createdAt != null ? createdAt.format(df) : "";
        return (id != null ? id.toString() : "") + "," + c + "," + s + "," + ca;
    }

    public static CaseReport fromCsv(String line) {
        String[] p = line.split(",", -1);
        CaseReport r = new CaseReport();
        if (!p[0].isEmpty()) r.setId(UUID.fromString(p[0]));
        // Note: case_ will be set by service after loading
        r.setSummary(p[2]);
        r.setCreatedAt(p[3].isEmpty() ? null : LocalDate.parse(p[3]));
        return r;
    }
}
