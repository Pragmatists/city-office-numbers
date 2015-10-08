package pl.pragmatists.cityofficenumbers.stats.events;

public class StatsUpdate {
    private int averageQueueSize;

    public StatsUpdate averageQueueSize(int averageQueueSize) {
        this.averageQueueSize = averageQueueSize;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StatsUpdate that = (StatsUpdate) o;

        return averageQueueSize == that.averageQueueSize;

    }

    @Override
    public int hashCode() {
        return averageQueueSize;
    }
}
