package pl.pragmatists.cityofficenumbers.stats;

import static org.mockito.Mockito.*;
import static pl.pragmatists.cityofficenumbers.builders.OfficeGroupBuilder.*;
import static pl.pragmatists.cityofficenumbers.builders.OfficeGroupsBuilder.*;

import org.joda.time.LocalDateTime;
import org.junit.Test;

import pl.pragmatists.cityofficenumbers.builders.OfficeGroupsBuilder;
import pl.pragmatists.cityofficenumbers.enternumber.RequestStatsUpdate;
import pl.pragmatists.cityofficenumbers.events.EventBus;
import pl.pragmatists.cityofficenumbers.stats.events.StatsUpdate;

public class StatsUpdaterTest {

    private final StatsRepository statsRepository = mock(StatsRepository.class);

    private EventBus bus = mock(EventBus.class);

    private final StatsUpdater statsUpdater = new StatsUpdater(statsRepository, bus);

    @Test
    public void saves_a_stat_on_event() {
        statsUpdater.onEventBackgroundThread(new RequestStatsUpdate(3, withOneGroup(anOfficeGroup()
                .withId(3)
                .withQueueSize(3).build())
                .withDate("2010-03-01", "12:38")
                .withOfficeId("office-id-1")
                .build()));

        verify(statsRepository).save(new OfficeQueueStat()
                        .queueSize(3)
                        .groupId(3)
                        .timestamp(new LocalDateTime(2010, 3, 1, 12, 38).toDate())
                        .officeId("office-id-1")
        );
    }

    @Test
    public void saves_stats_for_many_groups() {
        statsUpdater.saveStatsFor(new OfficeGroupsBuilder()
                        .withOfficeGroup(anOfficeGroup().withQueueSize(2).build())
                        .withOfficeGroup(anOfficeGroup().withQueueSize(5).build())
                        .build()
        );

        verify(statsRepository).save(new OfficeQueueStat().queueSize(2));
        verify(statsRepository).save(new OfficeQueueStat().queueSize(5));
    }

    @Test
    public void publishes_new_stats_on_update_request() {
        when(statsRepository.getAverageQueueSize("office-id-2", 5)).thenReturn(4);

        statsUpdater.onEventBackgroundThread(new RequestStatsUpdate(5, withOneGroup().withOfficeId("office-id-2").build()));

        verify(bus).post(new StatsUpdate().averageQueueSize(4));
    }
}