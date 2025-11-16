package co.edu.umanizales.police_api.service;

import co.edu.umanizales.police_api.model.PoliceUnit;
import co.edu.umanizales.police_api.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PoliceUnitService {
    private final List<PoliceUnit> units = new ArrayList<>();

    // Retorna todas las unidades policiales en memoria.
    public List<PoliceUnit> getAll() {
        return units;
    }

    // Busca una unidad por su id. Devuelve null si no existe.
    public PoliceUnit getById(UUID id) {
        if (id == null) {
            return null;
        }
        for (PoliceUnit u : units) {
            if (u.getId() != null && u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }

    // Crea una nueva unidad y la agrega a la lista. Genera id si viene nulo.
    public PoliceUnit create(PoliceUnit u) {
        if (u == null) {
            return null;
        }
        if (u.getId() == null) {
            u.setId(UUID.randomUUID());
        }
        units.add(u);
        return u;
    }

    // Actualiza los datos de una unidad por id. Retorna la actualizada o null si no se encuentra.
    public PoliceUnit update(UUID id, PoliceUnit u) {
        if (id == null || u == null) {
            return null;
        }
        for (PoliceUnit current : units) {
            if (current.getId() != null && current.getId().equals(id)) {
                current.setName(u.getName());
                current.setActive(u.isActive());

                // Reemplaza la lista de miembros manteniendo la instancia.
                List<Employee> targetMembers = current.getMembers();
                if (targetMembers != null) {
                    targetMembers.clear();
                }
                if (u.getMembers() != null) {
                    if (targetMembers == null) {
                        List<Employee> newList = new ArrayList<>(u.getMembers());
                        if (current.getMembers() != null) {
                            current.getMembers().addAll(newList);
                        }
                    } else {
                        targetMembers.addAll(u.getMembers());
                    }
                }
                return current;
            }
        }
        return null;
    }

    // Elimina una unidad por id. Retorna true si se eliminó.
    public boolean delete(UUID id) {
        if (id == null) {
            return false;
        }
        int index = -1;
        int i = 0;
        for (PoliceUnit u : units) {
            if (u.getId() != null && u.getId().equals(id)) {
                index = i;
                break;
            }
            i++;
        }
        if (index >= 0) {
            units.remove(index);
            return true;
        }
        return false;
    }
}
