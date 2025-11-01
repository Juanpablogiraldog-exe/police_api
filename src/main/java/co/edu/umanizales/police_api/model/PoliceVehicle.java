package co.edu.umanizales.police_api.model;

// CSV header: id,plateNumber,model,assignedUnitId

import java.util.UUID;

/**
 * A simple police vehicle assigned to a unit.
 */
public class PoliceVehicle implements Identifiable {
    private UUID id;
    private String plateNumber;
    private String model;
    private UUID assignedUnitId; // Relación con unidad por UUID.

    public PoliceVehicle() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public UUID getAssignedUnitId() { return assignedUnitId; }
    public void setAssignedUnitId(UUID assignedUnitId) { this.assignedUnitId = assignedUnitId; }

    @Override
    public String toCsv() {
        String pn = plateNumber == null ? "" : plateNumber.replace(",", "");
        String mo = model == null ? "" : model.replace(",", "");
        String au = assignedUnitId != null ? assignedUnitId.toString() : "";
        return (id != null ? id.toString() : "") + "," + pn + "," + mo + "," + au;
    }

    public static PoliceVehicle fromCsv(String line) {
        String[] p = line.split(",", -1);
        PoliceVehicle v = new PoliceVehicle();
        if (!p[0].isEmpty()) v.setId(UUID.fromString(p[0]));
        v.setPlateNumber(p[1]);
        v.setModel(p[2]);
        v.setAssignedUnitId(p[3].isEmpty() ? null : UUID.fromString(p[3]));
        return v;
    }
}
