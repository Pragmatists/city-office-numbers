package pl.pragmatists.cityofficenumbers.officegroups.messages;

import java.util.List;

import pl.pragmatists.cityofficenumbers.groups.OfficeGroup;
import pl.pragmatists.cityofficenumbers.groups.OfficeGroups;

public class OfficeGroupsFetched {

    private final OfficeGroups officeGroups;

    public OfficeGroupsFetched(OfficeGroups officeGroups) {
        this.officeGroups = officeGroups;
    }

    public List<OfficeGroup> groups() {
        return officeGroups.groups();
    }

    public OfficeGroups getOfficeGroups() {
        return officeGroups;
    }
}
