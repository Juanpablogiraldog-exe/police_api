package co.edu.umanizales.police_api.model;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


 //empleados de la estacion policial

@Setter
@Getter
public abstract class Employee implements Identifiable {
    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDate hiredDate;
    private UUID unitId; // Relación por UUID para mantener la simplicidad.


    public Employee() {
        this.id = UUID.randomUUID();
    }

    @Override
    public String toCsv() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String hd = hiredDate != null ? hiredDate.format(df) : "";
        String fn = firstName == null ? "" : firstName.replace(",", "");
        String ln = lastName == null ? "" : lastName.replace(",", "");
        String uid = unitId != null ? unitId.toString() : "";
        return (id != null ? id.toString() : "") + "," + fn + "," + ln + "," + hd + "," + uid;
    }

    public static Employee fromCsv(String line) {


        throw new UnsupportedOperationException("Use a concrete subclass to create an Employee from CSV.");
    }

    public abstract String performTaskOnCase(Case c);
}
