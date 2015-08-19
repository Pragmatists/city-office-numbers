package pl.pragmatists.cityofficenumbers.offices;

import java.util.List;

import pl.pragmatists.cityofficenumbers.groups.OfficeGroup;
import pl.pragmatists.cityofficenumbers.groups.OfficeGroups;
import pl.pragmatists.cityofficenumbers.officegroups.json.OfficeGroupJson;

public class OfficeGroupsFetched {

    private final OfficeGroups officeGroups;

    public OfficeGroupsFetched(List<OfficeGroupJson> officeGroupJsons) {
        this.officeGroups = new OfficeGroups(officeGroupJsons);
    }

    public List<OfficeGroup> groups() {
        return officeGroups.groups();
    }

}
