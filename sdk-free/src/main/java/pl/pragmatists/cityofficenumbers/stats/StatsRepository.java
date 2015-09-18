package pl.pragmatists.cityofficenumbers.stats;

public interface StatsRepository {
    void save(OfficeQueueStat officeQueueStat);

    OfficeQueueStat findById(long id);
}
