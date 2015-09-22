package pl.pragmatists.cityofficenumbers.stats;

import pl.pragmatists.cityofficenumbers.groups.OfficeGroup;
import pl.pragmatists.cityofficenumbers.groups.OfficeGroups;
import pl.pragmatists.cityofficenumbers.officegroups.messages.OfficeGroupsFetched;

public class StatsPersister {
    private final StatsRepository statsRepository;

    public StatsPersister(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    public void onEventMainThread(OfficeGroupsFetched officeGroupsFetched) {
        saveStatsFor(officeGroupsFetched.getOfficeGroups());
    }

    public void saveStatsFor(OfficeGroups officeGroups) {
        for (OfficeGroup group : officeGroups.groups()) {
            statsRepository.save(
                    new OfficeQueueStat()
                            .queueSize(group.queueSize())
                            .timestamp(officeGroups.getTimestamp())
                            .officeId(officeGroups.getOfficeId())
            );
        }
    }

}
