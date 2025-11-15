package co.edu.umanizales.police_api.controller;

import co.edu.umanizales.police_api.model.Case;
import co.edu.umanizales.police_api.service.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cases")
public class CaseController {

    private final CaseService caseService;

    @Autowired
    public CaseController(CaseService caseService) {
        this.caseService = caseService;
    }

    // Obtiene todos los casos.
    @GetMapping
    public ResponseEntity<List<Case>> getAll() {
        return ResponseEntity.ok(caseService.getAll());
    }

    // Obtiene un caso por id.
    @GetMapping("/{id}")
    public ResponseEntity<Case> getById(@PathVariable UUID id) {
        Case c = caseService.getById(id);
        if (c == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(c);
    }

    // Crea un nuevo caso.
    @PostMapping
    public ResponseEntity<Case> create(@RequestBody Case c) {
        Case created = caseService.create(c);
        if (created == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(created);
    }

    // Actualiza un caso existente.
    @PutMapping("/{id}")
    public ResponseEntity<Case> update(@PathVariable UUID id, @RequestBody Case c) {
        Case updated = caseService.update(id, c);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // Elimina un caso por id.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        boolean deleted = caseService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
