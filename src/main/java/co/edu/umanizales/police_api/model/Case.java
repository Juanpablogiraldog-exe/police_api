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
    private PoliceUnit assignedUnit; // Relación directa con unidad
    private List<IncidentReport> reports; // Reportes relacionados
    private List<Evidence> evidences; // Evidencias relacionadas

    public Case() {
        this.id = UUID.randomUUID();
        this.reports = new ArrayList<>();
        this.evidences = new ArrayList<>();
    }

    //Convertir a csv

    @Override
    public String toCsv() {
        String t = title == null ? "" : title.replace(",", "");
        String d = description == null ? "" : description.replace(",", "");
        String ct = crimeType != null ? crimeType.name() : "";
        String au = assignedUnit != null ? assignedUnit.getId().toString() : "";
        String rep = joinUuidList(reports);
        String ev = joinUuidList(evidences);
        return (id != null ? id.toString() : "") + "," + t + "," + d + "," + ct + "," + au + "," + rep + "," + ev;
    }

    public static Case fromCsv(String line) {
        String[] p = line.split(",", -1);
        Case c = new Case();
        if (!p[0].isEmpty()) c.setId(UUID.fromString(p[0]));
        c.setTitle(p[1]);
        c.setDescription(p[2]);
        c.setCrimeType(p[3].isEmpty() ? null : CrimeType.valueOf(p[3]));
        // Note: assignedUnit will be set by service after loading
        // reportIds and evidenceIds are now handled by service
        return c;
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

    private static String joinUuidList(List<?> list) {
        if (list == null || list.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) sb.append(';');
            Object item = list.get(i);
            if (item instanceof Identifiable) {
                sb.append(((Identifiable) item).getId().toString());
            } else if (item instanceof UUID) {
                sb.append(item.toString());
            }
        }
        return sb.toString();
    }
}
