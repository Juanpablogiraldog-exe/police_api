package co.edu.umanizales.police_api.service;

import co.edu.umanizales.police_api.model.Case;
import co.edu.umanizales.police_api.model.IncidentReport;
import co.edu.umanizales.police_api.model.Evidence;
import co.edu.umanizales.police_api.model.PoliceUnit;
import co.edu.umanizales.police_api.model.CrimeType;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CaseService {
    private final List<Case> cases = new ArrayList<>();

    @PostConstruct
    public void loadFromCsv() {
        try {
            Path csvPath = Paths.get("src/main/resources/data/cases.csv");
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
                Case c = Case.fromCsv(line);
                cases.add(c);
            }
        } catch (IOException e) {
            System.err.println("Error al cargar casos desde CSV: " + e.getMessage());
        }
    }

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
        appendToCsv(c);
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
                updateCsv();
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
            updateCsv();
            return true;
        }
        return false;
    }

    // Agrega un caso al archivo CSV
    private void appendToCsv(Case c) {
        try {
            Path csvPath = Paths.get("src/main/resources/data/cases.csv");
            String csvLine = c.toCsv() + "\n";
            Files.write(csvPath, csvLine.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error al escribir en CSV: " + e.getMessage());
        }
    }

    // Actualiza el archivo CSV completo con todos los casos
    private void updateCsv() {
        try {
            Path csvPath = Paths.get("src/main/resources/data/cases.csv");
            StringBuilder csvContent = new StringBuilder("id,title,description,crimeType,assignedUnit,reports,evidences\n");
            
            for (Case c : cases) {
                csvContent.append(c.toCsv()).append("\n");
            }
            
            Files.write(csvPath, csvContent.toString().getBytes());
        } catch (IOException e) {
            System.err.println("Error al actualizar CSV: " + e.getMessage());
        }
    }

    // Importa casos desde resources/cases_dataset.json y retorna la cantidad creada
    public int importFromDataset() {
        int created = 0;
        try {
            ClassPathResource resource = new ClassPathResource("cases_dataset.json");
            if (!resource.exists()) {
                System.err.println("No se encontró cases_dataset.json en el classpath");
                return 0;
            }
            try (InputStream is = resource.getInputStream()) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(is);
                JsonNode rows = root.path("rows");
                if (rows != null && rows.isArray()) {
                    for (JsonNode row : rows) {
                        String caseId = row.path("case_id").asText("");
                        String category = row.path("category").asText("other");
                        String notes = row.path("notes").asText("");
                        String location = row.path("location").asText("");
                        String date = row.path("date").asText("");
                        String assignedOfficer = row.path("assigned_officer").asText("");
                        String status = row.path("status").asText("");
                        String priority = row.path("priority").asText("");

                        String title = "Caso " + caseId + " - " + category;
                        String description = notes + " en " + location + " el " + date
                                + " (Oficial: " + assignedOfficer + ", Estado: " + status + ", Prioridad: " + priority + ")";

                        Case c = new Case();
                        c.setTitle(title);
                        c.setDescription(description);
                        c.setCrimeType(mapCrimeType(category));
                        // assignedUnit, reports y evidences permanecen por defecto

                        create(c);
                        created++;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al importar casos desde JSON: " + e.getMessage());
        }
        return created;
    }

    private CrimeType mapCrimeType(String category) {
        if (category == null) {
            return CrimeType.OTHER;
        }
        String v = category.trim().toUpperCase();
        try {
            return CrimeType.valueOf(v);
        } catch (IllegalArgumentException ex) {
            return CrimeType.OTHER;
        }
    }

    public JsonNode getDatasetRows() {
        try {
            ClassPathResource resource = new ClassPathResource("cases_dataset.json");
            ObjectMapper mapper = new ObjectMapper();
            if (!resource.exists()) {
                ObjectNode empty = mapper.createObjectNode();
                empty.putArray("rows");
                return empty;
            }
            try (InputStream is = resource.getInputStream()) {
                JsonNode root = mapper.readTree(is);
                JsonNode rows = root.path("rows");
                ObjectNode out = mapper.createObjectNode();
                if (rows != null && rows.isArray()) {
                    out.set("rows", rows);
                } else {
                    out.putArray("rows");
                }
                return out;
            }
        } catch (IOException e) {
            System.err.println("Error al leer dataset de casos: " + e.getMessage());
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode empty = mapper.createObjectNode();
            empty.putArray("rows");
            return empty;
        }
    }
}
