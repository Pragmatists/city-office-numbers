package pl.pragmatists.cityofficenumbers.stats;

import static org.mockito.Mockito.*;
import static pl.pragmatists.cityofficenumbers.builders.OfficeGroupBuilder.*;
import static pl.pragmatists.cityofficenumbers.builders.OfficeGroupsBuilder.*;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Test;

import pl.pragmatists.cityofficenumbers.builders.OfficeGroupsBuilder;

public class StatsPersisterTest {

    private final StatsRepository statsRepository = mock(StatsRepository.class);

    private final StatsPersister statsPersister = new StatsPersister(statsRepository);

    @Test
    public void saves_a_stat_on_event() {
        statsPersister.saveStatsFor(
                withOneGroup(anOfficeGroup().withQueueSize(3).build())
                        .withDate("2010-03-01", "12:38")
                        .withOfficeId("office-id-1")
                        .build());

        verify(statsRepository).save(new OfficeQueueStat()
                .queueSize(3)
                .timestamp(new LocalDateTime(2010, 3, 1, 12, 38).toDate())
                .officeId("office-id-1")
        );
    }

    @Test
    public void saves_stats_for_many_groups() {
        statsPersister.saveStatsFor(new OfficeGroupsBuilder()
                .withOfficeGroup(anOfficeGroup().withQueueSize(2).build())
                .withOfficeGroup(anOfficeGroup().withQueueSize(5).build())
                        .build()
        );

        verify(statsRepository).save(new OfficeQueueStat().queueSize(2));
        verify(statsRepository).save(new OfficeQueueStat().queueSize(5));
    }
}