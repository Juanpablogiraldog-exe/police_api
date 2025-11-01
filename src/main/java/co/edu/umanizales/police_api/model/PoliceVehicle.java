package co.edu.umanizales.police_api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


 // vehículo que se asigna a una unidad

@Setter
@Getter
public class PoliceVehicle implements Identifiable {
    private UUID id;
    private String plateNumber;
    private String model;
    private UUID assignedUnitId; // Relación con unidad por UUID.

    public PoliceVehicle() {
        this.id = UUID.randomUUID();
    }

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
