package co.edu.umanizales.police_api.service;

import co.edu.umanizales.police_api.model.Case;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CaseService {
    private final List<Case> cases = new ArrayList<>();

    // Retorna todos los casos almacenados en memoria.
    public List<Case> getAll() {
        return cases;
    }

    // Busca un caso por su UUID. Devuelve null si no existe.
    public Case getById(UUID id) {
        if (id == null) {
            return null;
        }
        for (Case c : cases) {
            if (c.getId() != null && c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    // Crea y agrega un nuevo caso a la lista. Genera id si viene nulo.
    public Case create(Case c) {
        if (c == null) {
            return null;
        }
        if (c.getId() == null) {
            c.setId(UUID.randomUUID());
        }
        cases.add(c);
        return c;
    }

    // Actualiza los datos de un caso existente por id. Devuelve el actualizado o null si no se encuentra.
    public Case update(UUID id, Case c) {
        if (id == null || c == null) {
            return null;
        }
        for (Case current : cases) {
            if (current.getId() != null && current.getId().equals(id)) {
                current.setTitle(c.getTitle());
                current.setDescription(c.getDescription());
                current.setCrimeType(c.getCrimeType());
                current.setAssignedUnitId(c.getAssignedUnitId());

                // Reemplaza la lista de reportes manteniendo la instancia.
                List<UUID> targetReports = current.getReportIds();
                if (targetReports != null) {
                    targetReports.clear();
                }
                if (c.getReportIds() != null) {
                    if (targetReports == null) {
                        // Si era null por alguna razón, crea la lista y agrega.
                        List<UUID> newList = new ArrayList<>();
                        for (UUID u : c.getReportIds()) {
                            newList.add(u);
                        }
                        // No hay setter público para reportIds, por eso se evita asignar directamente.
                        // Se asume que targetReports no es null por constructor, pero se maneja por seguridad.
                        if (current.getReportIds() != null) {
                            current.getReportIds().addAll(newList);
                        }
                    } else {
                        for (UUID u : c.getReportIds()) {
                            targetReports.add(u);
                        }
                    }
                }

                // Reemplaza la lista de evidencias manteniendo la instancia.
                List<UUID> targetEvidence = current.getEvidenceIds();
                if (targetEvidence != null) {
                    targetEvidence.clear();
                }
                if (c.getEvidenceIds() != null) {
                    if (targetEvidence == null) {
                        List<UUID> newList = new ArrayList<>();
                        for (UUID u : c.getEvidenceIds()) {
                            newList.add(u);
                        }
                        if (current.getEvidenceIds() != null) {
                            current.getEvidenceIds().addAll(newList);
                        }
                    } else {
                        for (UUID u : c.getEvidenceIds()) {
                            targetEvidence.add(u);
                        }
                    }
                }
                return current;
            }
        }
        return null;
    }

    // Elimina un caso por id. Devuelve true si fue eliminado, false si no se encontró.
    public boolean delete(UUID id) {
        if (id == null) {
            return false;
        }
        int index = -1;
        int i = 0;
        for (Case c : cases) {
            if (c.getId() != null && c.getId().equals(id)) {
                index = i;
                break;
            }
            i++;
        }
        if (index >= 0) {
            cases.remove(index);
            return true;
        }
        return false;
    }
}
