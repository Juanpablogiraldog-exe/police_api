package co.edu.umanizales.police_api.service;

import co.edu.umanizales.police_api.model.PoliceVehicle;
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
public class PoliceVehicleService {
    private final List<PoliceVehicle> vehicles = new ArrayList<>();

    //carga en police vehicle service
    @PostConstruct
    public void loadFromCsv() {
        try {
            Path csvPath = Paths.get("src/main/resources/data/police_vehicles.csv");
            List<String> lines = Files.readAllLines(csvPath);
            
            // Omitir la primera línea (encabezado) usando enhanced for loop
            boolean isHeader = true;
            for (String line : lines) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                if (line.trim().isEmpty()) {
                    continue;
                }
                PoliceVehicle vehicle = PoliceVehicle.fromCsv(line);
                vehicles.add(vehicle);
            }
            updateCsv();
        } catch (IOException e) {
            System.err.println("Error al cargar vehículos desde CSV: " + e.getMessage());
        }
    }

    // Retorna todos los vehículos policiales almacenados.
    public List<PoliceVehicle> getAll() {
        return vehicles;
    }

    // Busca un vehículo por su id. Devuelve null si no existe.
    public PoliceVehicle getById(UUID id) {
        if (id == null) {
            return null;
        }
        for (PoliceVehicle v : vehicles) {
            if (v.getId() != null && v.getId().equals(id)) {
                return v;
            }
        }
        return null;
    }

    // Crea un nuevo vehículo y lo agrega a la lista. Genera id si viene nulo.
    public PoliceVehicle create(PoliceVehicle v) {
        if (v == null) {
            return null;
        }
        if (v.getId() == null) {
            v.setId(UUID.randomUUID());
        }
        vehicles.add(v);
        appendToCsv(v);
        return v;
    }

    // Actualiza un vehículo existente por id. Retorna el actualizado o null si no se encuentra.
    public PoliceVehicle update(UUID id, PoliceVehicle v) {
        if (id == null || v == null) {
            return null;
        }
        for (PoliceVehicle current : vehicles) {
            if (current.getId() != null && current.getId().equals(id)) {
                current.setPlateNumber(v.getPlateNumber());
                current.setModel(v.getModel());
                current.setAssignedUnit(v.getAssignedUnit());
                updateCsv();
                return current;
            }
        }
        return null;
    }

    // Elimina un vehículo por id. Retorna true si se eliminó.
    public boolean delete(UUID id) {
        if (id == null) {
            return false;
        }
        int index = -1;
        int i = 0;
        for (PoliceVehicle v : vehicles) {
            if (v.getId() != null && v.getId().equals(id)) {
                index = i;
                break;
            }
            i++;
        }
        if (index >= 0) {
            vehicles.remove(index);
            updateCsv();
            return true;
        }
        return false;
    }

    // Agrega un vehículo al archivo CSV
    private void appendToCsv(PoliceVehicle v) {
        try {
            Path csvPath = Paths.get("src/main/resources/data/police_vehicles.csv");
            String csvLine = v.toCsv() + "\n";
            Files.write(csvPath, csvLine.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error al escribir en CSV: " + e.getMessage());
        }
    }

    // Actualiza el archivo CSV completo con todos los vehículos
    private void updateCsv() {
        try {
            Path csvPath = Paths.get("src/main/resources/data/police_vehicles.csv");
            StringBuilder csvContent = new StringBuilder("id,plateNumber,model,assignedUnit\n");
            
            for (PoliceVehicle vehicle : vehicles) {
                csvContent.append(vehicle.toCsv()).append("\n");
            }
            
            Files.write(csvPath, csvContent.toString().getBytes());
        } catch (IOException e) {
            System.err.println("Error al actualizar CSV: " + e.getMessage());
        }
    }
}
