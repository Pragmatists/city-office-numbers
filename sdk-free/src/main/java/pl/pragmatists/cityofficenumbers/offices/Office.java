package pl.pragmatists.cityofficenumbers.offices;

public class Office {
    private final String name;

    private String id;

    public Office(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public Office id(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }
}
