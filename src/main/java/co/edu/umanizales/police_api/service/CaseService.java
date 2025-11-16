package co.edu.umanizales.police_api.service;

import co.edu.umanizales.police_api.model.Case;
import co.edu.umanizales.police_api.model.IncidentReport;
import co.edu.umanizales.police_api.model.Evidence;
import co.edu.umanizales.police_api.model.PoliceUnit;
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
                current.setAssignedUnit(c.getAssignedUnit());

                // Reemplaza la lista de reportes manteniendo la instancia.
                List<IncidentReport> targetReports = current.getReports();
                if (targetReports != null) {
                    targetReports.clear();
                }
                if (c.getReports() != null) {
                    if (targetReports == null) {
                        List<IncidentReport> newList = new ArrayList<>(c.getReports());
                        if (current.getReports() != null) {
                            current.getReports().addAll(newList);
                        }
                    } else {
                        targetReports.addAll(c.getReports());
                    }
                }

                // Reemplaza la lista de evidencias manteniendo la instancia.
                List<Evidence> targetEvidence = current.getEvidences();
                if (targetEvidence != null) {
                    targetEvidence.clear();
                }
                if (c.getEvidences() != null) {
                    if (targetEvidence == null) {
                        List<Evidence> newList = new ArrayList<>(c.getEvidences());
                        if (current.getEvidences() != null) {
                            current.getEvidences().addAll(newList);
                        }
                    } else {
                        targetEvidence.addAll(c.getEvidences());
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
