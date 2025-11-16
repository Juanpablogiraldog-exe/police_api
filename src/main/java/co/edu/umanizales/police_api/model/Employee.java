package co.edu.umanizales.police_api.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


 //empleados de la estacion policial

@Setter
@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Officer.class, name = "Officer"),
    @JsonSubTypes.Type(value = Detective.class, name = "Detective")
})
public abstract class Employee implements Identifiable {
    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDate hiredDate;
    private PoliceUnit unit; // Relación directa con unidad


    public Employee() {
        this.id = UUID.randomUUID();
    }

    @Override
    public String toCsv() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String hd = hiredDate != null ? hiredDate.format(df) : "";
        String fn = firstName == null ? "" : firstName.replace(",", "");
        String ln = lastName == null ? "" : lastName.replace(",", "");
        String uid = unit != null ? unit.getId().toString() : "";
        return (id != null ? id.toString() : "") + "," + fn + "," + ln + "," + hd + "," + uid;
    }

    public static Employee fromCsv(String line) {


        throw new UnsupportedOperationException("Use a concrete subclass to create an Employee from CSV.");
    }

    public abstract String performTaskOnCase(Case c);
}
