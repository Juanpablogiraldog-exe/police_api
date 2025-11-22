package co.edu.umanizales.police_api.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.Setter;

// Oficial de policía
@Setter
@Getter
@JsonTypeName("Officer")
public class Officer extends Employee {
    private String badge;
    private String rank;

    public Officer() {
        super();
    }

    @Override
    public String performTaskOnCase(Case c) {
        return "Officer " + getFirstName() + " " + getLastName() + " is investigating case: " + c.getTitle();
    }
}
