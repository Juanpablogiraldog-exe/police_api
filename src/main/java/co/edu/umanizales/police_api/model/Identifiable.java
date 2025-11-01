package co.edu.umanizales.police_api.model;

import java.util.UUID;

// interface para objetos con identificador para id

public interface Identifiable {
    UUID getId();
    String toCsv();
}
