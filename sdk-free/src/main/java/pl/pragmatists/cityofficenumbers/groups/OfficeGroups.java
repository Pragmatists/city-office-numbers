package pl.pragmatists.cityofficenumbers.groups;

import java.util.ArrayList;
import java.util.List;

import pl.pragmatists.cityofficenumbers.officegroups.json.OfficeGroupJson;

public class OfficeGroups {

    private final List<OfficeGroup> officeGroups;

    public OfficeGroups(List<OfficeGroupJson> officeGroupJsons) {
        this.officeGroups = toOfficeGroups(officeGroupJsons);
    }

    public List<OfficeGroup> groups() {
        return officeGroups;
    }

    private List<OfficeGroup> toOfficeGroups(List<OfficeGroupJson> officeGroupJson) {
        List<OfficeGroup> result = new ArrayList<>();
        for (OfficeGroupJson groupJson : officeGroupJson) {
            result.add(new OfficeGroup()
                    .name(groupJson.nazwaGrupy)
                    .groupId(groupJson.idGrupy)
                    .currentNumber(groupJson.aktualnyNumer));
        }
        return result;
    }

    public OfficeGroup find(int groupId) {
        for (OfficeGroup officeGroup : officeGroups) {
            if (officeGroup.groupId() == groupId) {
                return officeGroup;
            }
        }
        throw new GroupNotFoundException();
    }

    private class GroupNotFoundException extends RuntimeException {
    }
}