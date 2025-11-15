package co.edu.umanizales.police_api.controller;

import co.edu.umanizales.police_api.model.IncidentReport;
import co.edu.umanizales.police_api.service.IncidentReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/incidentreports")
public class IncidentReportController {

    private final IncidentReportService incidentReportService;

    @Autowired
    public IncidentReportController(IncidentReportService incidentReportService) {
        this.incidentReportService = incidentReportService;
    }

    // Obtiene todos los reportes de incidentes.
    @GetMapping
    public ResponseEntity<List<IncidentReport>> getAll() {
        return ResponseEntity.ok(incidentReportService.getAll());
    }

    // Obtiene un reporte por id.
    @GetMapping("/{id}")
    public ResponseEntity<IncidentReport> getById(@PathVariable UUID id) {
        IncidentReport r = incidentReportService.getById(id);
        if (r == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(r);
    }

    // Crea un nuevo reporte de incidente.
    @PostMapping
    public ResponseEntity<IncidentReport> create(@RequestBody IncidentReport r) {
        IncidentReport created = incidentReportService.create(r);
        if (created == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(created);
    }

    // Actualiza un reporte existente.
    @PutMapping("/{id}")
    public ResponseEntity<IncidentReport> update(@PathVariable UUID id, @RequestBody IncidentReport r) {
        IncidentReport updated = incidentReportService.update(id, r);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // Elimina un reporte por id.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        boolean deleted = incidentReportService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
