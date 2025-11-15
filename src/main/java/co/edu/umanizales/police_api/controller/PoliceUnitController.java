package co.edu.umanizales.police_api.controller;

import co.edu.umanizales.police_api.model.PoliceUnit;
import co.edu.umanizales.police_api.service.PoliceUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/policeunits")
public class PoliceUnitController {

    private final PoliceUnitService policeUnitService;

    @Autowired
    public PoliceUnitController(PoliceUnitService policeUnitService) {
        this.policeUnitService = policeUnitService;
    }

    // Obtiene todas las unidades policiales.
    @GetMapping
    public ResponseEntity<List<PoliceUnit>> getAll() {
        return ResponseEntity.ok(policeUnitService.getAll());
    }

    // Obtiene una unidad por id.
    @GetMapping("/{id}")
    public ResponseEntity<PoliceUnit> getById(@PathVariable UUID id) {
        PoliceUnit u = policeUnitService.getById(id);
        if (u == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(u);
    }

    // Crea una nueva unidad policial.
    @PostMapping
    public ResponseEntity<PoliceUnit> create(@RequestBody PoliceUnit u) {
        PoliceUnit created = policeUnitService.create(u);
        if (created == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(created);
    }

    // Actualiza una unidad existente por id.
    @PutMapping("/{id}")
    public ResponseEntity<PoliceUnit> update(@PathVariable UUID id, @RequestBody PoliceUnit u) {
        PoliceUnit updated = policeUnitService.update(id, u);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // Elimina una unidad por id.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        boolean deleted = policeUnitService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
