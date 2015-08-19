package pl.pragmatists.cityofficenumbers.officegroups.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OfficeGroupsResultJson {

    public Result result;

    public class Result {
        public String date;
        public String time;
        @JsonProperty("grupy")
        public List<OfficeGroupJson> groups;
    }

    public List<OfficeGroupJson> officeGroups() {
        return result.groups;
    }
}


