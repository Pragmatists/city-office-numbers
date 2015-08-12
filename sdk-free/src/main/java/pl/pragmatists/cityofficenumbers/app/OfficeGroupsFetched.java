package pl.pragmatists.cityofficenumbers.app;

import java.util.List;

public class OfficeGroupsFetched {

    private final List<OfficeGroup> officeGroups;

    public OfficeGroupsFetched(List<OfficeGroup> officeGroups) {

        this.officeGroups = officeGroups;
    }

    public List<OfficeGroup> groups() {
        return officeGroups;
    }

}
