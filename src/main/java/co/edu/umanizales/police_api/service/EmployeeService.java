package co.edu.umanizales.police_api.service;

import co.edu.umanizales.police_api.model.Employee;
import co.edu.umanizales.police_api.model.Officer;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {
    private final List<Employee> employees = new ArrayList<>();

    @PostConstruct
    public void loadFromCsv() {
        try {
            Path csvPath = Paths.get("src/main/resources/data/employees.csv");
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
                Officer e = new Officer(); // default concrete type for CSV load
                if (!p[0].isEmpty()) e.setId(UUID.fromString(p[0]));
                e.setFirstName(p[1]);
                e.setLastName(p[2]);
                e.setHiredDate(p[3].isEmpty() ? null : LocalDate.parse(p[3]));
                // unit (p[4]) intentionally not resolved here to match pattern in other services
                employees.add(e);
            }
        } catch (IOException e) {
            System.err.println("Error al cargar empleados desde CSV: " + e.getMessage());
        }
    }

    public List<Employee> getAll() {
        return employees;
    }

    public Employee getById(UUID id) {
        if (id == null) {
            return null;
        }
        for (Employee e : employees) {
            if (e.getId() != null && e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    public Employee create(Employee e) {
        if (e == null) {
            return null;
        }
        if (e.getId() == null) {
            e.setId(UUID.randomUUID());
        }
        employees.add(e);
        appendToCsv(e);
        return e;
    }

    public Employee update(UUID id, Employee e) {
        if (id == null || e == null) {
            return null;
        }
        for (Employee current : employees) {
            if (current.getId() != null && current.getId().equals(id)) {
                current.setFirstName(e.getFirstName());
                current.setLastName(e.getLastName());
                current.setHiredDate(e.getHiredDate());
                current.setUnit(e.getUnit());
                updateCsv();
                return current;
            }
        }
        return null;
    }

    public boolean delete(UUID id) {
        if (id == null) {
            return false;
        }
        int index = -1;
        int i = 0;
        for (Employee e : employees) {
            if (e.getId() != null && e.getId().equals(id)) {
                index = i;
                break;
            }
            i++;
        }
        if (index >= 0) {
            employees.remove(index);
            updateCsv();
            return true;
        }
        return false;
    }

    private void appendToCsv(Employee e) {
        try {
            Path csvPath = Paths.get("src/main/resources/data/employees.csv");
            String csvLine = e.toCsv() + "\n";
            Files.write(csvPath, csvLine.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException ex) {
            System.err.println("Error al escribir en CSV: " + ex.getMessage());
        }
    }

    private void updateCsv() {
        try {
            Path csvPath = Paths.get("src/main/resources/data/employees.csv");
            StringBuilder csvContent = new StringBuilder("id,firstName,lastName,hiredDate,unit\n");
            for (Employee e : employees) {
                csvContent.append(e.toCsv()).append("\n");
            }
            Files.write(csvPath, csvContent.toString().getBytes());
        } catch (IOException ex) {
            System.err.println("Error al actualizar CSV: " + ex.getMessage());
        }
    }
}
