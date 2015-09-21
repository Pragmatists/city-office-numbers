package pl.pragmatists.cityofficenumbers.builders;

import pl.pragmatists.cityofficenumbers.groups.OfficeGroup;
import pl.pragmatists.cityofficenumbers.officegroups.json.OfficeGroupJson;

public class OfficeGroupBuilder {
    private int id;

    private int currentNumber = 1;

    private String groupLetter = "A";

    private int queueSize = 100;

    public static OfficeGroupBuilder anOfficeGroup() {
        return new OfficeGroupBuilder();
    }

    public static OfficeGroup defaultOfficeGroup() {
        return defaultOfficeGroupJson().toOfficeGroup();
    }

    public static OfficeGroupJson defaultOfficeGroupJson() {
        OfficeGroupJson officeGroupJson = new OfficeGroupJson();
        officeGroupJson.aktualnyNumer = 0;
        officeGroupJson.liczbaKlwKolejce = 0;
        return officeGroupJson;
    }

    public OfficeGroupBuilder withId(int id) {
        this.id = id;
        return this;
    }

    public OfficeGroup build() {
        return defaultOfficeGroup()
                .groupId(id)
                .currentNumber(currentNumber)
                .groupLetter(groupLetter)
                .queueSize(queueSize);
    }

    public OfficeGroupBuilder withCurrentNumber(int currentNumber) {
        this.currentNumber = currentNumber;
        return this;
    }

    public OfficeGroupBuilder withLetter(String groupLetter) {
        this.groupLetter = groupLetter;
        return this;
    }

    public OfficeGroupBuilder withQueueSize(int queueSize) {
        this.queueSize = queueSize;
        return this;
    }
}
