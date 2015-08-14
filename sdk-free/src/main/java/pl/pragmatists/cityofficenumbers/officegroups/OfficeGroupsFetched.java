package pl.pragmatists.cityofficenumbers.officegroups;

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
