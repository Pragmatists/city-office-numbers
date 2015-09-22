package pl.pragmatists.cityofficenumbers.groups;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pl.pragmatists.cityofficenumbers.officegroups.json.OfficeGroupJson;
import pl.pragmatists.cityofficenumbers.officegroups.json.OfficeGroupsResultJson;

public class OfficeGroups {

    private final List<OfficeGroup> officeGroups;

    private String date;

    private String time;

    private String officeId;

    public OfficeGroups(List<OfficeGroup> officeGroups) {
        this.officeGroups = officeGroups;
    }

    public static OfficeGroups fromJson(OfficeGroupsResultJson officeGroupJson) {
        return new OfficeGroups(toOfficeGroups(officeGroupJson.officeGroups()));
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

    public OfficeGroups date(String date) {
        this.date = date;
        return this;
    }

    public OfficeGroups time(String time) {
        this.time = time;
        return this;
    }

    public Date getTimestamp() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd kk:mm", Locale.ENGLISH).parse(date + " " + time);
        } catch (ParseException e) {
            return null;
        }
    }

    public OfficeGroups officeId(String officeId) {
        this.officeId = officeId;
        return this;
    }

    public String getOfficeId() {
        return officeId;
    }

    private class GroupNotFoundException extends RuntimeException {
    }
}