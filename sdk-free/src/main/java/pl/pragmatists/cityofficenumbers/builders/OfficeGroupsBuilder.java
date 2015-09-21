package pl.pragmatists.cityofficenumbers.builders;

import java.util.Collections;

import pl.pragmatists.cityofficenumbers.groups.OfficeGroup;
import pl.pragmatists.cityofficenumbers.groups.OfficeGroups;

public class OfficeGroupsBuilder {

    private OfficeGroup officeGroup = OfficeGroupBuilder.defaultOfficeGroupJson().toOfficeGroup();

    public static OfficeGroupsBuilder withOneGroup(OfficeGroup officeGroup) {
        return new OfficeGroupsBuilder().withGroup(officeGroup);
    }

    private OfficeGroupsBuilder withGroup(OfficeGroup officeGroup) {
        this.officeGroup = officeGroup;
        return this;
    }

    public OfficeGroups build() {
        return new OfficeGroups(Collections.singletonList(officeGroup));
    }

    public static OfficeGroupsBuilder withOneGroup() {
        return withOneGroup(OfficeGroupBuilder.defaultOfficeGroup());
    }
}
