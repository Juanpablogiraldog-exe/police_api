package co.edu.umanizales.police_api.controller;

import co.edu.umanizales.police_api.model.CaseCategory;
import co.edu.umanizales.police_api.service.CaseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/casecategories")
public class CaseCategoryController {

    private final CaseCategoryService caseCategoryService;

    @Autowired
    public CaseCategoryController(CaseCategoryService caseCategoryService) {
        this.caseCategoryService = caseCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<CaseCategory>> getAll() {
        return ResponseEntity.ok(caseCategoryService.getAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<CaseCategory> getByName(@PathVariable String name) {
        CaseCategory c = caseCategoryService.getByName(name);
        if (c == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(c);
    }

    @PostMapping
    public ResponseEntity<CaseCategory> create(@RequestBody CaseCategory c) {
        CaseCategory created = caseCategoryService.create(c);
        if (created == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{name}")
    public ResponseEntity<CaseCategory> update(@PathVariable String name, @RequestBody CaseCategory c) {
        CaseCategory updated = caseCategoryService.update(name, c);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> delete(@PathVariable String name) {
        boolean deleted = caseCategoryService.delete(name);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
