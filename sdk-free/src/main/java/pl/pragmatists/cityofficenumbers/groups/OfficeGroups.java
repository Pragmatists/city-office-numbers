package pl.pragmatists.cityofficenumbers.groups;

import java.util.ArrayList;
import java.util.List;

import pl.pragmatists.cityofficenumbers.officegroups.json.OfficeGroupJson;
import pl.pragmatists.cityofficenumbers.officegroups.json.OfficeGroupsResultJson;

public class OfficeGroups {

    private final List<OfficeGroup> officeGroups;

    public OfficeGroups(List<OfficeGroup> officeGroups) {
        this.officeGroups = officeGroups;
    }

    public static OfficeGroups fromJson(OfficeGroupsResultJson officeGroupJsons) {
        return new OfficeGroups(toOfficeGroups(officeGroupJsons.officeGroups()));
    }

    public static List<OfficeGroup> toOfficeGroups(List<OfficeGroupJson> officeGroupJson) {
        List<OfficeGroup> result = new ArrayList<>();
        for (OfficeGroupJson groupJson : officeGroupJson) {
            result.add(groupJson.toOfficeGroup());
        }
        return result;
    }

    public List<OfficeGroup> groups() {
        return officeGroups;
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