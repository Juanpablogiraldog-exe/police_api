package co.edu.umanizales.police_api.controller;

import co.edu.umanizales.police_api.model.CaseReport;
import co.edu.umanizales.police_api.service.CaseReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/casereports")
public class CaseReportController {

    private final CaseReportService caseReportService;

    @Autowired
    public CaseReportController(CaseReportService caseReportService) {
        this.caseReportService = caseReportService;
    }

    // Obtiene todos los reportes de caso.
    @GetMapping
    public ResponseEntity<List<CaseReport>> getAll() {
        return ResponseEntity.ok(caseReportService.getAll());
    }

    // Obtiene un reporte de caso por id.
    @GetMapping("/{id}")
    public ResponseEntity<CaseReport> getById(@PathVariable UUID id) {
        CaseReport r = caseReportService.getById(id);
        if (r == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(r);
    }

    // Crea un nuevo reporte de caso.
    @PostMapping
    public ResponseEntity<CaseReport> create(@RequestBody CaseReport r) {
        CaseReport created = caseReportService.create(r);
        if (created == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(created);
    }

    // Actualiza un reporte de caso existente.
    @PutMapping("/{id}")
    public ResponseEntity<CaseReport> update(@PathVariable UUID id, @RequestBody CaseReport r) {
        CaseReport updated = caseReportService.update(id, r);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // Elimina un reporte de caso por id.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        boolean deleted = caseReportService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
