package co.edu.umanizales.police_api.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

// Evidencia recolectada del caso.

@Setter
@Getter
public class Evidence implements Identifiable {
    private UUID id;
    private UUID caseId;
    private String type;
    private String description;
    private LocalDateTime collectedAt;

    public Evidence() {
        this.id = UUID.randomUUID();
    }

    @Override
    public String toCsv() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String t = type == null ? "" : type.replace(",", "");
        String d = description == null ? "" : description.replace(",", "");
        String c = caseId != null ? caseId.toString() : "";
        String ca = collectedAt != null ? collectedAt.format(dtf) : "";
        return (id != null ? id.toString() : "") + "," + c + "," + t + "," + d + "," + ca;
    }

    public static Evidence fromCsv(String line) {
        String[] p = line.split(",", -1);
        Evidence e = new Evidence();
        if (!p[0].isEmpty()) e.setId(UUID.fromString(p[0]));
        e.setCaseId(p[1].isEmpty() ? null : UUID.fromString(p[1]));
        e.setType(p[2]);
        e.setDescription(p[3]);
        e.setCollectedAt(p[4].isEmpty() ? null : LocalDateTime.parse(p[4]));
        return e;
    }
}
