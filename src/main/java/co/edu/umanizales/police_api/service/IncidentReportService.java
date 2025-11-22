package co.edu.umanizales.police_api.service;

import co.edu.umanizales.police_api.model.IncidentReport;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class IncidentReportService {
    private final List<IncidentReport> reports = new ArrayList<>();

    @PostConstruct
    public void loadFromCsv() {
        try {
            Path csvPath = Paths.get("src/main/resources/data/incident_reports.csv");
            List<String> lines = Files.readAllLines(csvPath);
            
            boolean isHeader = true;
            for (String line : lines) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                if (line.trim().isEmpty()) {
                    continue;
                }
                IncidentReport r = IncidentReport.fromCsv(line);
                reports.add(r);
            }
        } catch (IOException e) {
            System.err.println("Error al cargar reportes de incidente desde CSV: " + e.getMessage());
        }
    }

    // Retorna todos los reportes de incidente almacenados.
    public List<IncidentReport> getAll() {
        return reports;
    }

    // Busca un reporte por su id. Devuelve null si no existe.
    public IncidentReport getById(UUID id) {
        if (id == null) {
            return null;
        }
        for (IncidentReport r : reports) {
            if (r.getId() != null && r.getId().equals(id)) {
                return r;
            }
        }
        return null;
    }

    // Crea un reporte nuevo y lo agrega a la lista. Genera id si viene nulo.
    public IncidentReport create(IncidentReport r) {
        if (r == null) {
            return null;
        }
        if (r.getId() == null) {
            r.setId(UUID.randomUUID());
        }
        reports.add(r);
        appendToCsv(r);
        return r;
    }

    // Actualiza un reporte existente por id. Retorna el actualizado o null si no se encuentra.
    public IncidentReport update(UUID id, IncidentReport r) {
        if (id == null || r == null) {
            return null;
        }
        for (IncidentReport current : reports) {
            if (current.getId() != null && current.getId().equals(id)) {
                current.setCase_(r.getCase_());
                current.setReporterName(r.getReporterName());
                current.setDetails(r.getDetails());
                current.setReportedAt(r.getReportedAt());
                updateCsv();
                return current;
            }
        }
        return null;
    }

    // Elimina un reporte por id. Retorna true si se eliminó.
    public boolean delete(UUID id) {
        if (id == null) {
            return false;
        }
        int index = -1;
        int i = 0;
        for (IncidentReport r : reports) {
            if (r.getId() != null && r.getId().equals(id)) {
                index = i;
                break;
            }
            i++;
        }
        if (index >= 0) {
            reports.remove(index);
            updateCsv();
            return true;
        }
        return false;
    }

    // Agrega un reporte al archivo CSV
    private void appendToCsv(IncidentReport r) {
        try {
            Path csvPath = Paths.get("src/main/resources/data/incident_reports.csv");
            String csvLine = r.toCsv() + "\n";
            Files.write(csvPath, csvLine.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error al escribir en CSV: " + e.getMessage());
        }
    }

    // Actualiza el archivo CSV completo con todos los reportes
    private void updateCsv() {
        try {
            Path csvPath = Paths.get("src/main/resources/data/incident_reports.csv");
            StringBuilder csvContent = new StringBuilder("id,case_,reporterName,details,reportedAt\n");
            
            for (IncidentReport r : reports) {
                csvContent.append(r.toCsv()).append("\n");
            }
            
            Files.write(csvPath, csvContent.toString().getBytes());
        } catch (IOException e) {
            System.err.println("Error al actualizar CSV: " + e.getMessage());
        }
    }
}
