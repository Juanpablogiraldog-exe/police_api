package co.edu.umanizales.police_api.controller;

import co.edu.umanizales.police_api.model.PoliceVehicle;
import co.edu.umanizales.police_api.service.PoliceVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/policevehicles")
public class PoliceVehicleController {

    private final PoliceVehicleService policeVehicleService;

    @Autowired
    public PoliceVehicleController(PoliceVehicleService policeVehicleService) {
        this.policeVehicleService = policeVehicleService;
    }

    // Obtiene todos los vehículos policiales.
    @GetMapping
    public ResponseEntity<List<PoliceVehicle>> getAll() {
        return ResponseEntity.ok(policeVehicleService.getAll());
    }

    // Obtiene un vehículo por id.
    @GetMapping("/{id}")
    public ResponseEntity<PoliceVehicle> getById(@PathVariable UUID id) {
        PoliceVehicle v = policeVehicleService.getById(id);
        if (v == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(v);
    }

    // Crea un nuevo vehículo policial.
    @PostMapping
    public ResponseEntity<PoliceVehicle> create(@RequestBody PoliceVehicle v) {
        PoliceVehicle created = policeVehicleService.create(v);
        if (created == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(created);
    }

    // Actualiza un vehículo existente por id.
    @PutMapping("/{id}")
    public ResponseEntity<PoliceVehicle> update(@PathVariable UUID id, @RequestBody PoliceVehicle v) {
        PoliceVehicle updated = policeVehicleService.update(id, v);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // Elimina un vehículo por id.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        boolean deleted = policeVehicleService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
