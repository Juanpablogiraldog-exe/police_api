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
    private PoliceUnit assignedUnit; // Relación directa con unidad

    public PoliceVehicle() {
        this.id = UUID.randomUUID();
    }

    @Override
    public String toCsv() {
        String pn = plateNumber == null ? "null" : plateNumber.replace(",", "");
        String mo = model == null ? "null" : model.replace(",", "");
        String au = assignedUnit != null ? assignedUnit.getId().toString() : "null";
        return (id != null ? id.toString() : "null") + "," + pn + "," + mo + "," + au;
    }

    public static PoliceVehicle fromCsv(String line) {
        String[] p = line.split(",", -1);
        PoliceVehicle v = new PoliceVehicle();
        if (!p[0].isEmpty() && !p[0].equals("null")) v.setId(UUID.fromString(p[0]));
        v.setPlateNumber(p[1].equals("null") ? null : p[1]);
        v.setModel(p[2].equals("null") ? null : p[2]);
        // Note: assignedUnit will be set by service after loading
        return v;
    }
}
