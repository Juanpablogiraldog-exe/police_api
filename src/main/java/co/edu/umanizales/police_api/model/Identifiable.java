package co.edu.umanizales.police_api.model;

import java.util.UUID;

// Small interface for objects with an identifier and CSV support.

public interface Identifiable {
    UUID getId();
    String toCsv();
}
