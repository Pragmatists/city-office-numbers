package pl.pragmatists.cityofficenumbers.stats;

import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

public class OfficeQueueStat {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private int queueSize;

    @DatabaseField(dataType = DataType.DATE_STRING)
    private Date timestamp;

    @DatabaseField
    private String officeId;

    @DatabaseField
    private int groupId;

    public OfficeQueueStat() {
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

    public OfficeQueueStat officeId(String officeId) {
        this.officeId = officeId;
        return this;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OfficeQueueStat that = (OfficeQueueStat) o;

        if (queueSize != that.queueSize) {
            return false;
        }
        if (groupId != that.groupId) {
            return false;
        }
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) {
            return false;
        }
        return !(officeId != null ? !officeId.equals(that.officeId) : that.officeId != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + queueSize;
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (officeId != null ? officeId.hashCode() : 0);
        result = 31 * result + groupId;
        return result;
    }

    @Override
    public String toString() {
        return "OfficeQueueStat{" +
                "id=" + id +
                ", queueSize=" + queueSize +
                ", timestamp=" + timestamp +
                ", officeId='" + officeId + '\'' +
                ", groupId=" + groupId +
                '}';
    }

    public String getOfficeId() {
        return officeId;
    }

    public OfficeQueueStat groupId(int groupId) {
        this.groupId = groupId;
        return this;
    }

    public int getGroupId() {
        return groupId;
    }
}
