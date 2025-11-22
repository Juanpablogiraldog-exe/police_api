package co.edu.umanizales.police_api.service;

import co.edu.umanizales.police_api.model.Evidence;
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
public class EvidenceService {
    private final List<Evidence> evidences = new ArrayList<>();

    @PostConstruct
    public void loadFromCsv() {
        try {
            Path csvPath = Paths.get("src/main/resources/data/evidences.csv");
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
                Evidence e = Evidence.fromCsv(line);
                evidences.add(e);
            }
        } catch (IOException e) {
            System.err.println("Error al cargar evidencias desde CSV: " + e.getMessage());
        }
    }

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
        appendToCsv(e);
        return e;
    }

    // Actualiza una evidencia existente por id. Devuelve la actualizada o null si no existe.
    public Evidence update(UUID id, Evidence e) {
        if (id == null || e == null) {
            return null;
        }
        for (Evidence current : evidences) {
            if (current.getId() != null && current.getId().equals(id)) {
                current.setCase_(e.getCase_());
                current.setType(e.getType());
                current.setDescription(e.getDescription());
                current.setCollectedAt(e.getCollectedAt());
                updateCsv();
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
            updateCsv();
            return true;
        }
        return false;
    }

    // Agrega una evidencia al archivo CSV
    private void appendToCsv(Evidence e) {
        try {
            Path csvPath = Paths.get("src/main/resources/data/evidences.csv");
            String csvLine = e.toCsv() + "\n";
            Files.write(csvPath, csvLine.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException ex) {
            System.err.println("Error al escribir en CSV: " + ex.getMessage());
        }
    }

    // Actualiza el archivo CSV completo con todas las evidencias
    private void updateCsv() {
        try {
            Path csvPath = Paths.get("src/main/resources/data/evidences.csv");
            StringBuilder csvContent = new StringBuilder("id,case_,type,description,collectedAt\n");
            
            for (Evidence e : evidences) {
                csvContent.append(e.toCsv()).append("\n");
            }
            
            Files.write(csvPath, csvContent.toString().getBytes());
        } catch (IOException ex) {
            System.err.println("Error al actualizar CSV: " + ex.getMessage());
        }
    }
}
