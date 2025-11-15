package co.edu.umanizales.police_api.service;

import co.edu.umanizales.police_api.model.Evidence;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EvidenceService {
    private final List<Evidence> evidences = new ArrayList<>();

    // Retorna todas las evidencias almacenadas en memoria.
    public List<Evidence> getAll() {
        return evidences;
    }

    // Busca una evidencia por su id. Devuelve null si no existe.
    public Evidence getById(UUID id) {
        if (id == null) {
            return null;
        }
        for (Evidence e : evidences) {
            if (e.getId() != null && e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    // Crea una nueva evidencia y la agrega a la lista. Genera id si viene nulo.
    public Evidence create(Evidence e) {
        if (e == null) {
            return null;
        }
        if (e.getId() == null) {
            e.setId(UUID.randomUUID());
        }
        evidences.add(e);
        return e;
    }

    // Actualiza una evidencia existente por id. Devuelve la actualizada o null si no existe.
    public Evidence update(UUID id, Evidence e) {
        if (id == null || e == null) {
            return null;
        }
        for (Evidence current : evidences) {
            if (current.getId() != null && current.getId().equals(id)) {
                current.setCaseId(e.getCaseId());
                current.setType(e.getType());
                current.setDescription(e.getDescription());
                current.setCollectedAt(e.getCollectedAt());
                return current;
            }
        }
        return null;
    }

    // Elimina una evidencia por id. Retorna true si se eliminó, false si no existe.
    public boolean delete(UUID id) {
        if (id == null) {
            return false;
        }
        int index = -1;
        int i = 0;
        for (Evidence e : evidences) {
            if (e.getId() != null && e.getId().equals(id)) {
                index = i;
                break;
            }
            i++;
        }
        if (index >= 0) {
            evidences.remove(index);
            return true;
        }
        return false;
    }
}
