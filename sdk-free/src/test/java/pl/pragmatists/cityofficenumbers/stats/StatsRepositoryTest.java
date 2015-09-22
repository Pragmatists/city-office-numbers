package pl.pragmatists.cityofficenumbers.stats;

import static org.assertj.core.api.Assertions.*;

import java.sql.SQLException;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringTestConfig.class)
@Rollback
public class StatsRepositoryTest {

    @Autowired
    private StatsRepository statsRepository;

    @Test
    public void save_my_stats() throws SQLException {

        statsRepository.save(new OfficeQueueStat()
                .queueSize(3)
                .timestamp(new LocalDate(2010, 2, 3).toDate())
                .officeId("office-id-1"));
        OfficeQueueStat stat = statsRepository.findById(1L);

        assertThat(stat.getId()).isNotNull();
        assertThat(stat.getQueueSize()).isEqualTo(3);
        assertThat(stat.getTimestamp()).isEqualTo("2010-2-3");
        assertThat(stat.getOfficeId()).isEqualTo("office-id-1");
    }


}
