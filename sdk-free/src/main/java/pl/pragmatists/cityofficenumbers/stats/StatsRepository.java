package pl.pragmatists.cityofficenumbers.stats;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;

public class StatsRepository {
    private final Dao<OfficeQueueStat, Long> statDao;

    public StatsRepository(Dao<OfficeQueueStat, Long> statDao) {
        this.statDao = statDao;
    }

    public void save(OfficeQueueStat officeQueueStat) {
        try {
            statDao.create(officeQueueStat);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public OfficeQueueStat findById(long id) {
        try {
            return statDao.queryForId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
