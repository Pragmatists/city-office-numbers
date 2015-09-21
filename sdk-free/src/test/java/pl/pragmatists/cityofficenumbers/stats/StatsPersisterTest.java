package pl.pragmatists.cityofficenumbers.stats;

import static org.mockito.Mockito.*;

import org.junit.Test;

import pl.pragmatists.cityofficenumbers.officegroups.messages.OfficeGroupsFetched;
import pl.pragmatists.cityofficenumbers.builders.OfficeGroupBuilder;
import pl.pragmatists.cityofficenumbers.builders.OfficeGroupsBuilder;

public class StatsPersisterTest {

    @Test
    public void saves_a_stat_on_event() {
        StatsRepository statsRepository = mock(OrmLiteStatsRepository.class);
        StatsPersister statsPersister = new StatsPersister(statsRepository);
        OfficeGroupsFetched event = new OfficeGroupsFetched(
                OfficeGroupsBuilder.withOneGroup(OfficeGroupBuilder.anOfficeGroup().withQueueSize(3).build()).build()
        );

        statsPersister.onEventMainThread(event);

        verify(statsRepository).save(new OfficeQueueStat().queueSize(3));
    }
}