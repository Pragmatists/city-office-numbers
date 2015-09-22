package pl.pragmatists.cityofficenumbers.enternumber;

import pl.pragmatists.cityofficenumbers.groups.OfficeGroups;

public class RequestStatsUpdate {
    private final int groupId;

    private final OfficeGroups officeGroups;

    public RequestStatsUpdate(int groupId, OfficeGroups officeGroups) {
        this.groupId = groupId;
        this.officeGroups = officeGroups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RequestStatsUpdate that = (RequestStatsUpdate) o;

        return groupId == that.groupId;

    }

    @Override
    public int hashCode() {
        return groupId;
    }
}
