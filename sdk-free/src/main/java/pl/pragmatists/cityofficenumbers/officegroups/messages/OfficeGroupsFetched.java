package pl.pragmatists.cityofficenumbers.officegroups.messages;

import java.util.List;

import pl.pragmatists.cityofficenumbers.officegroups.json.OfficeGroup;

public class OfficeGroupsFetched {

    private final List<OfficeGroup> officeGroups;

    public OfficeGroupsFetched(List<OfficeGroup> officeGroups) {

        this.officeGroups = officeGroups;
    }

    public List<OfficeGroup> groups() {
        return officeGroups;
    }

}
