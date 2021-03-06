package pl.pragmatists.cityofficenumbers.stats;

import pl.pragmatists.cityofficenumbers.enternumber.RequestStatsUpdate;
import pl.pragmatists.cityofficenumbers.events.EventBus;
import pl.pragmatists.cityofficenumbers.groups.OfficeGroup;
import pl.pragmatists.cityofficenumbers.groups.OfficeGroups;
import pl.pragmatists.cityofficenumbers.stats.events.StatsUpdate;

public class StatsUpdater {
    private final StatsRepository statsRepository;

    private final EventBus bus;

    public StatsUpdater(StatsRepository statsRepository, EventBus bus) {
        this.statsRepository = statsRepository;
        this.bus = bus;
    }

    public void saveStatsFor(OfficeGroups officeGroups) {
        for (OfficeGroup group : officeGroups.groups()) {
            statsRepository.save(
                    new OfficeQueueStat()
                            .groupId(group.groupId())
                            .queueSize(group.queueSize())
                            .timestamp(officeGroups.getTimestamp())
                            .officeId(officeGroups.getOfficeId())
            );
        }
    }

    public void onEventBackgroundThread(RequestStatsUpdate requestStatsUpdate) {
        saveStatsFor(requestStatsUpdate.getOfficeGroups());
        int averageQueueSize = statsRepository.getAverageQueueSize(
                requestStatsUpdate.getOfficeGroups().getOfficeId(),
                requestStatsUpdate.getGroupId());
        bus.post(new StatsUpdate().averageQueueSize(averageQueueSize));
    }
}
