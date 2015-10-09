package pl.pragmatists.cityofficenumbers.stats;

import static org.assertj.core.api.Assertions.*;

import java.sql.SQLException;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringTestConfig.class)
public class StatsRepositoryTest {

    @Autowired
    private StatsRepository statsRepository;

    @Autowired
    private ConnectionSource connectionSource;

    @Autowired
    private FakeTimeProvider fakeTimeProvider;

    @Before
    public void clearDatabase() throws SQLException {
        TableUtils.clearTable(connectionSource, OfficeQueueStat.class);
    }

    @Test
    public void save_my_stats() throws SQLException {
        statsRepository.save(new OfficeQueueStat()
                        .officeId("office-id-1")
                        .groupId(5)
                        .queueSize(3)
                        .timestamp(new LocalDate(2010, 2, 3).toDate())
        );
        OfficeQueueStat stat = statsRepository.findAll().get(0);

        assertThat(stat.getId()).isNotNull();
        assertThat(stat.getQueueSize()).isEqualTo(3);
        assertThat(stat.getTimestamp()).isEqualTo("2010-2-3");
        assertThat(stat.getOfficeId()).isEqualTo("office-id-1");
        assertThat(stat.getGroupId()).isEqualTo(5);
    }

    @Test
    public void finds_average_queue_size() {
        fakeTimeProvider.todayIs(new LocalDate(2010, 2, 3).toDate());
        // included
        statsRepository.save(new OfficeQueueStat()
                        .officeId("office-id-1")
                        .groupId(4)
                        .queueSize(3)
                        .timestamp(new LocalDate(2010, 2, 3).toDate())
        );
        // included
        statsRepository.save(new OfficeQueueStat()
                        .officeId("office-id-1")
                        .groupId(4)
                        .queueSize(6)
                        .timestamp(new LocalDate(2010, 2, 3).toDate())
        );
        // different office
        statsRepository.save(new OfficeQueueStat()
                        .officeId("office-id-2")
                        .groupId(4)
                        .queueSize(13)
                        .timestamp(new LocalDate(2010, 2, 3).toDate())
        );
        // different group
        statsRepository.save(new OfficeQueueStat()
                        .officeId("office-id-1")
                        .groupId(5)
                        .queueSize(13)
                        .timestamp(new LocalDate(2010, 2, 3).toDate())
        );
        // different date
        statsRepository.save(new OfficeQueueStat()
                        .officeId("office-id-1")
                        .groupId(4)
                        .queueSize(13)
                        .timestamp(new LocalDate(2010, 2, 2).toDate())
        );

        int averageQueueSize = statsRepository.getAverageQueueSize("office-id-1", 4);

        assertThat(averageQueueSize).isEqualTo((3 + 6) / 2);
    }

}
