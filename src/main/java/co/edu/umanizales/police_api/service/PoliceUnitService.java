package co.edu.umanizales.police_api.service;

import co.edu.umanizales.police_api.model.PoliceUnit;
import co.edu.umanizales.police_api.model.Employee;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PoliceUnitService {
    private final List<PoliceUnit> units = new ArrayList<>();

    @PostConstruct
    public void loadFromCsv() {
        try {
            Path csvPath = Paths.get("src/main/resources/data/police_units.csv");
            List<String> lines = Files.readAllLines(csvPath);
            
            boolean isHeader = true;
            for (String line : lines) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                if (line.trim().isEmpty()) {
                    continue;
                }
                PoliceUnit u = PoliceUnit.fromCsv(line);
                units.add(u);
            }
        } catch (IOException e) {
            System.err.println("Error al cargar unidades policiales desde CSV: " + e.getMessage());
        }
    }

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
        appendToCsv(u);
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
                updateCsv();
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
            updateCsv();
            return true;
        }
        return false;
    }

    // Agrega una unidad al archivo CSV
    private void appendToCsv(PoliceUnit u) {
        try {
            Path csvPath = Paths.get("src/main/resources/data/police_units.csv");
            String csvLine = u.toCsv() + "\n";
            Files.write(csvPath, csvLine.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error al escribir en CSV: " + e.getMessage());
        }
    }

    // Actualiza el archivo CSV completo con todas las unidades
    private void updateCsv() {
        try {
            Path csvPath = Paths.get("src/main/resources/data/police_units.csv");
            StringBuilder csvContent = new StringBuilder("id,name,members,active\n");
            
            for (PoliceUnit u : units) {
                csvContent.append(u.toCsv()).append("\n");
            }
            
            Files.write(csvPath, csvContent.toString().getBytes());
        } catch (IOException e) {
            System.err.println("Error al actualizar CSV: " + e.getMessage());
        }
    }
}
