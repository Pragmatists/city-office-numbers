package pl.pragmatists.cityofficenumbers.stats;

import java.util.List;

public interface StatsRepository {
    void save(OfficeQueueStat officeQueueStat);

    OfficeQueueStat findById(long id);

    int getAverageQueueSize(String officeId, int groupId);

    List<OfficeQueueStat> findAll();
}
