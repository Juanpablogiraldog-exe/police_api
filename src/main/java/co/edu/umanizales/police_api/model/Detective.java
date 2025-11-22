package co.edu.umanizales.police_api.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.Setter;

// Detective de policía
@Setter
@Getter
@JsonTypeName("Detective")
public class Detective extends Employee {
    private String specialization;
    private int casesResolved;

    public Detective() {
        super();
        this.casesResolved = 0;
    }

    @Override
    public String performTaskOnCase(Case c) {
        return "Detective " + getFirstName() + " " + getLastName() + " (" + specialization + ") is investigating case: " + c.getTitle();
    }
}
