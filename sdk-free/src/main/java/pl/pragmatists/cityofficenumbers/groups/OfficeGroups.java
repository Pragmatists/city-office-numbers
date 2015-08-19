package pl.pragmatists.cityofficenumbers.groups;

import java.util.ArrayList;
import java.util.List;

import pl.pragmatists.cityofficenumbers.officegroups.json.OfficeGroupJson;

public class OfficeGroups {
    final List<OfficeGroupJson> officeGroupJsons;

    public List<OfficeGroupJson> getOfficeGroupJsons() {
        return officeGroupJsons;
    }

    public OfficeGroups(List<OfficeGroupJson> officeGroupJsons) {
        this.officeGroupJsons = officeGroupJsons;
    }

    public List<OfficeGroup> groups() {
        return toOfficeGroups(officeGroupJsons);
    }

    private List<OfficeGroup> toOfficeGroups(List<OfficeGroupJson> officeGroupJson) {
        List<OfficeGroup> result = new ArrayList<>();
        for (OfficeGroupJson groupJson : officeGroupJson) {
            result.add(new OfficeGroup().name(groupJson.nazwaGrupy));
        }
        return result;
    }
}