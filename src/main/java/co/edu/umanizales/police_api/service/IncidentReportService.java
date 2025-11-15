package co.edu.umanizales.police_api.service;

import co.edu.umanizales.police_api.model.IncidentReport;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class IncidentReportService {
    private final List<IncidentReport> reports = new ArrayList<>();

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
        return r;
    }

    // Actualiza un reporte existente por id. Retorna el actualizado o null si no se encuentra.
    public IncidentReport update(UUID id, IncidentReport r) {
        if (id == null || r == null) {
            return null;
        }
        for (IncidentReport current : reports) {
            if (current.getId() != null && current.getId().equals(id)) {
                current.setCaseId(r.getCaseId());
                current.setReporterName(r.getReporterName());
                current.setDetails(r.getDetails());
                current.setReportedAt(r.getReportedAt());
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
            return true;
        }
        return false;
    }
}
