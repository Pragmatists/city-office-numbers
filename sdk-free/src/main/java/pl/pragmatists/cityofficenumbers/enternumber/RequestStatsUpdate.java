package pl.pragmatists.cityofficenumbers.enternumber;

public class RequestStatsUpdate {
    private final int groupId;

    public RequestStatsUpdate(int groupId) {
        this.groupId = groupId;
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
