package co.edu.umanizales.police_api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//reporte policiaco con evidencia

@Setter
@Getter
public class Case implements Identifiable {
    private UUID id;
    private String title;
    private String description;
    private CrimeType crimeType; // Clasificación del caso
    private UUID assignedUnitId; // Relación con unidad por UUID
    private List<UUID> reportIds; // Ids de reportes relacionados
    private List<UUID> evidenceIds; // Ids de evidencias relacionadas

    public Case() {
        this.id = UUID.randomUUID();
        this.reportIds = new ArrayList<>();
        this.evidenceIds = new ArrayList<>();
    }

    //Convertir a csv

    @Override
    public String toCsv() {
        String t = title == null ? "" : title.replace(",", "");
        String d = description == null ? "" : description.replace(",", "");
        String ct = crimeType != null ? crimeType.name() : "";
        String au = assignedUnitId != null ? assignedUnitId.toString() : "";
        String rep = joinUuidList(reportIds);
        String ev = joinUuidList(evidenceIds);
        return (id != null ? id.toString() : "") + "," + t + "," + d + "," + ct + "," + au + "," + rep + "," + ev;
    }

    public static Case fromCsv(String line) {
        String[] p = line.split(",", -1);
        Case c = new Case();
        if (!p[0].isEmpty()) c.setId(UUID.fromString(p[0]));
        c.setTitle(p[1]);
        c.setDescription(p[2]);
        c.setCrimeType(p[3].isEmpty() ? null : CrimeType.valueOf(p[3]));
        c.setAssignedUnitId(p[4].isEmpty() ? null : UUID.fromString(p[4]));
        c.setReportIds(parseUuidList(p.length > 5 ? p[5] : ""));
        c.setEvidenceIds(parseUuidList(p.length > 6 ? p[6] : ""));
        return c;
    }

    private void setReportIds(List<UUID> uuids) {
        this.reportIds = (uuids != null) ? new ArrayList<>(uuids) : new ArrayList<>();
    }

    private static List<UUID> parseUuidList(String s) {
        List<UUID> list = new ArrayList<>();
        if (s == null || s.isEmpty()) return list;
        String[] items = s.split(";");
        for (String item : items) {
            if (!item.isEmpty()) list.add(UUID.fromString(item));
        }
        return list;
    }

    private static String joinUuidList(List<UUID> list) {
        if (list == null || list.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) sb.append(';');
            sb.append(list.get(i).toString());
        }
        return sb.toString();
    }
}
