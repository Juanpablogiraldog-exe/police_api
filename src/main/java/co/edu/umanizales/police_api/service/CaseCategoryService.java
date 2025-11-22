package co.edu.umanizales.police_api.service;

import co.edu.umanizales.police_api.model.CaseCategory;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class CaseCategoryService {
    private final List<CaseCategory> categories = new ArrayList<>();

    @PostConstruct
    public void loadFromCsv() {
        try {
            Path csvPath = Paths.get("src/main/resources/data/case_categories.csv");
            if (!Files.exists(csvPath)) {
                return;
            }
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
                String[] p = line.split(",", -1);
                String name = p[0];
                String description = p.length > 1 ? p[1] : "";
                categories.add(new CaseCategory(name, description));
            }
        } catch (IOException e) {
            System.err.println("Error al cargar categorías de caso desde CSV: " + e.getMessage());
        }
    }

    public List<CaseCategory> getAll() {
        return categories;
    }

    public CaseCategory getByName(String name) {
        if (name == null) {
            return null;
        }
        for (CaseCategory c : categories) {
            if (c.name() != null && c.name().equals(name)) {
                return c;
            }
        }
        return null;
    }

    public CaseCategory create(CaseCategory c) {
        if (c == null) {
            return null;
        }
        categories.add(c);
        appendToCsv(c);
        return c;
    }

    public CaseCategory update(String name, CaseCategory c) {
        if (name == null || c == null) {
            return null;
        }
        for (int i = 0; i < categories.size(); i++) {
            CaseCategory current = categories.get(i);
            if (current.name() != null && current.name().equals(name)) {
                CaseCategory updated = new CaseCategory(current.name(), c.description());
                categories.set(i, updated);
                updateCsv();
                return updated;
            }
        }
        return null;
    }

    public boolean delete(String name) {
        if (name == null) {
            return false;
        }
        int index = -1;
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).name() != null && categories.get(i).name().equals(name)) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            categories.remove(index);
            updateCsv();
            return true;
        }
        return false;
    }

    private void appendToCsv(CaseCategory c) {
        try {
            Path csvPath = Paths.get("src/main/resources/data/case_categories.csv");
            String n = c.name() == null ? "" : c.name().replace(",", "");
            String d = c.description() == null ? "" : c.description().replace(",", "");
            String csvLine = n + "," + d + "\n";
            Files.write(csvPath, csvLine.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error al escribir en CSV: " + e.getMessage());
        }
    }

    private void updateCsv() {
        try {
            Path csvPath = Paths.get("src/main/resources/data/case_categories.csv");
            StringBuilder csvContent = new StringBuilder("name,description\n");
            for (CaseCategory c : categories) {
                String n = c.name() == null ? "" : c.name().replace(",", "");
                String d = c.description() == null ? "" : c.description().replace(",", "");
                csvContent.append(n).append(",").append(d).append("\n");
            }
            Files.write(csvPath, csvContent.toString().getBytes());
        } catch (IOException e) {
            System.err.println("Error al actualizar CSV: " + e.getMessage());
        }
    }
}
