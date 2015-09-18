package pl.pragmatists.cityofficenumbers.stats;

import static org.assertj.core.api.Assertions.*;

import java.sql.SQLException;
import java.util.Arrays;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.spring.DaoFactory;
import com.j256.ormlite.spring.TableCreator;
import com.j256.ormlite.support.ConnectionSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringTestConfig.class)
@Rollback
public class StatsRepositoryTest {

    @Autowired
    private ConnectionSource connectionSource;

    @Test
    public void save_my_stats() throws SQLException {
        Dao<OfficeQueueStat, Long> statDao = initDao();
        StatsRepository statsRepository = new StatsRepository(statDao);

        statsRepository.save(new OfficeQueueStat().id(1L).queueSize(3).timestamp(new LocalDate(2010, 2, 3).toDate()));
        OfficeQueueStat stat = statsRepository.findById(1L);

        assertThat(stat.getId()).isEqualTo(1L);
        assertThat(stat.getQueueSize()).isEqualTo(3);
        assertThat(stat.getTimestamp()).isEqualTo("2010-2-3");
    }

    private Dao<OfficeQueueStat, Long> initDao() throws SQLException {
        System.setProperty(TableCreator.AUTO_CREATE_TABLES, "true");
        Dao<OfficeQueueStat, Long> statDao = DaoFactory.createDao(connectionSource, OfficeQueueStat.class);
        new TableCreator(connectionSource, Arrays.<Dao<?, ?>>asList(statDao)).initialize();
        return statDao;
    }
}
