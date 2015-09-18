package pl.pragmatists.cityofficenumbers.stats;

import pl.pragmatists.cityofficenumbers.officegroups.messages.OfficeGroupsFetched;

public class StatsPersister {
    private final StatsRepository statsRepository;

    public StatsPersister(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    public void onEventMainThread(OfficeGroupsFetched officeGroupsFetched) {
        statsRepository.save(new OfficeQueueStat().queueSize(officeGroupsFetched.getOfficeGroups().groups().get(0).queueSize()));
    }
}
