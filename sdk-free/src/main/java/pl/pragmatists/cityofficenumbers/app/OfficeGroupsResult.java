package pl.pragmatists.cityofficenumbers.app;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OfficeGroupsResult {

    public Result result;

    public class Result {
        public String date;
        public String time;
        @JsonProperty("grupy")
        public List<OfficeGroup> groups;
    }

    public List<OfficeGroup> officeGroups() {
        return result.groups;
    }
}


