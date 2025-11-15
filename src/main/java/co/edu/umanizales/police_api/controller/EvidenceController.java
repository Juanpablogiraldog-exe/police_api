package co.edu.umanizales.police_api.controller;

import co.edu.umanizales.police_api.model.Evidence;
import co.edu.umanizales.police_api.service.EvidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/evidences")
public class EvidenceController {

    private final EvidenceService evidenceService;

    @Autowired
    public EvidenceController(EvidenceService evidenceService) {
        this.evidenceService = evidenceService;
    }

    // Obtiene todas las evidencias.
    @GetMapping
    public ResponseEntity<List<Evidence>> getAll() {
        return ResponseEntity.ok(evidenceService.getAll());
    }

    // Obtiene una evidencia por id.
    @GetMapping("/{id}")
    public ResponseEntity<Evidence> getById(@PathVariable UUID id) {
        Evidence e = evidenceService.getById(id);
        if (e == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(e);
    }

    // Crea una nueva evidencia.
    @PostMapping
    public ResponseEntity<Evidence> create(@RequestBody Evidence e) {
        Evidence created = evidenceService.create(e);
        if (created == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(created);
    }

    // Actualiza una evidencia existente.
    @PutMapping("/{id}")
    public ResponseEntity<Evidence> update(@PathVariable UUID id, @RequestBody Evidence e) {
        Evidence updated = evidenceService.update(id, e);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // Elimina una evidencia por id.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        boolean deleted = evidenceService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
