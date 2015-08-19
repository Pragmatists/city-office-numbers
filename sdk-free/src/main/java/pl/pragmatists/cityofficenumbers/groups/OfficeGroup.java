package pl.pragmatists.cityofficenumbers.groups;

public class OfficeGroup {
    private String name;

    private int groupId;

    private int currentNumber;

    private String groupLetter;

    private int queueSize;

    public OfficeGroup name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return name;
    }

    public OfficeGroup groupId(int groupId) {
        this.groupId = groupId;
        return this;
    }

    public int groupId() {
        return groupId;
    }

    public OfficeGroup currentNumber(int currentNumber) {
        this.currentNumber = currentNumber;
        return this;
    }

    public int currentNumber() {
        return currentNumber;
    }

    public String groupLetter() {
        return groupLetter;
    }

    public OfficeGroup groupLetter(String groupLetter) {
        this.groupLetter = groupLetter;
        return this;
    }

    public OfficeGroup queueSize(int queueSize) {
        this.queueSize = queueSize;
        return this;
    }

    public int queueSize() {
        return queueSize;
    }
}
