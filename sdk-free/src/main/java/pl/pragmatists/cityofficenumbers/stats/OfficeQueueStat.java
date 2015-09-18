package pl.pragmatists.cityofficenumbers.stats;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;

public class OfficeQueueStat {

    @DatabaseField(id = true)
    private Long id;

    @DatabaseField
    private int queueSize;

    @DatabaseField
    private Date timestamp;

    public OfficeQueueStat() {
    }

    public OfficeQueueStat(long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public OfficeQueueStat id(long id) {
        this.id = id;
        return this;
    }

    public OfficeQueueStat queueSize(int queueSize) {
        this.queueSize = queueSize;
        return this;
    }

    public OfficeQueueStat timestamp(Date timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
