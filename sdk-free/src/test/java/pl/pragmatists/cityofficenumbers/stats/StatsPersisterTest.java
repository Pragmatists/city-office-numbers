package pl.pragmatists.cityofficenumbers.stats;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import pl.pragmatists.cityofficenumbers.officegroups.json.OfficeGroupJson;
import pl.pragmatists.cityofficenumbers.officegroups.messages.OfficeGroupsFetched;

public class StatsPersisterTest {

    @Test
    public void saves_a_stat_on_event() {
        StatsRepository statsRepository = mock(StatsRepository.class);
        StatsPersister statsPersister = new StatsPersister(statsRepository);
        OfficeGroupJson officeGroupJson = new OfficeGroupJson();
        officeGroupJson.idGrupy = 4;
        officeGroupJson.aktualnyNumer = 40;
        officeGroupJson.liczbaKlwKolejce = 3;

        OfficeGroupsFetched event = new OfficeGroupsFetched(asList(officeGroupJson));

        statsPersister.onEventMainThread(event);

        verify(statsRepository).save(new OfficeQueueStat().queueSize(3));
    }
}