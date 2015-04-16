package pl.pragmatists.cityofficenumbers.offices;

public class Office {
    private final String name;

    public Office(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
