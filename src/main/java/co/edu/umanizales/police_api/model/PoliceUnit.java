package co.edu.umanizales.police_api.model;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//Registrar Unidad policial

public class PoliceUnit implements Identifiable {
    private UUID id;
    private String name;
    private List<Employee> members;
    private boolean active;

    public PoliceUnit() {
        this.id = UUID.randomUUID();
        this.members = new ArrayList<>();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Employee> getMembers() { return members; }
    public void setMembers(List<Employee> members) { this.members = members; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public String toCsv() {
        String n = name == null ? "" : name.replace(",", "");
        String memberIds = joinUuidList(members);
        return (id != null ? id.toString() : "") + "," + n + "," + memberIds + "," + active;
    }

    public static PoliceUnit fromCsv(String line) {
        String[] p = line.split(",", -1);
        PoliceUnit u = new PoliceUnit();
        if (!p[0].isEmpty()) u.setId(UUID.fromString(p[0]));
        u.setName(p[1]);
        // Note: members will be set by service after loading
        u.setActive(p.length > 3 && Boolean.parseBoolean(p[3]));
        return u;
    }

    private static List<UUID> parseUuidList(String part) {
        List<UUID> list = new ArrayList<>();
        if (part == null || part.isEmpty()) return list;
        String[] items = part.split(";");
        for (String s : items) {
            if (!s.isEmpty()) list.add(UUID.fromString(s));
        }
        return list;
    }

    private static String joinUuidList(List<?> list) {
        if (list == null || list.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) sb.append(';');
            Object item = list.get(i);
            if (item instanceof Identifiable) {
                sb.append(((Identifiable) item).getId().toString());
            } else if (item instanceof UUID) {
                sb.append(item.toString());
            }
        }
        return sb.toString();
    }
}
