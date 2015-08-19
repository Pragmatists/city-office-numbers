package pl.pragmatists.cityofficenumbers.groups;

public class OfficeGroup {
    private String name;

    public OfficeGroup name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return name;
    }
}
