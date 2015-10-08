package pl.pragmatists.cityofficenumbers.stats;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;

public class OrmLiteStatsRepository implements StatsRepository {
    private final Dao<OfficeQueueStat, Long> statDao;

    public OrmLiteStatsRepository(Dao<OfficeQueueStat, Long> statDao) {
        this.statDao = statDao;
    }

    @Override
    public void save(OfficeQueueStat officeQueueStat) {
        try {
            statDao.create(officeQueueStat);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OfficeQueueStat findById(long id) {
        try {
            return statDao.queryForId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getAverageQueueSize(int groupId) {
        return 36;
    }
}
