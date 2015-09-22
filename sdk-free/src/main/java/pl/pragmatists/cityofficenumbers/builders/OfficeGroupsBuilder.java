package pl.pragmatists.cityofficenumbers.builders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.pragmatists.cityofficenumbers.groups.OfficeGroup;
import pl.pragmatists.cityofficenumbers.groups.OfficeGroups;

public class OfficeGroupsBuilder {

    private OfficeGroup officeGroup = OfficeGroupBuilder.defaultOfficeGroupJson().toOfficeGroup();

    private List<OfficeGroup> officeGroups = new ArrayList<>();

    private String date;

    private String time;

    private String officeId;

    public static OfficeGroupsBuilder withOneGroup(OfficeGroup officeGroup) {
        return new OfficeGroupsBuilder().withGroup(officeGroup);
    }

    private OfficeGroupsBuilder withGroup(OfficeGroup officeGroup) {
        this.officeGroup = officeGroup;
        return this;
    }

    public OfficeGroups build() {
        List<OfficeGroup> officeGroups = this.officeGroups.isEmpty() ? Collections.singletonList(officeGroup) : this.officeGroups;
        return new OfficeGroups(officeGroups).date(date).time(time).officeId(officeId);
    }

    public static OfficeGroupsBuilder withOneGroup() {
        return withOneGroup(OfficeGroupBuilder.defaultOfficeGroup());
    }

    public OfficeGroupsBuilder withOfficeGroup(OfficeGroup officeGroup) {
        officeGroups.add(officeGroup);
        return this;
    }

    public OfficeGroupsBuilder withDate(String date, String time) {
        this.date = date;
        this.time = time;
        return this;
    }

    public OfficeGroupsBuilder withOfficeId(String officeId) {
        this.officeId = officeId;
        return this;
    }
}
