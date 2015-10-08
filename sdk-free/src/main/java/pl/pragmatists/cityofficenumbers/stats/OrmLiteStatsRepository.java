package pl.pragmatists.cityofficenumbers.stats;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

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
    public int getAverageQueueSize(String officeId, int groupId) {
        QueryBuilder<OfficeQueueStat, Long> builder = statDao.queryBuilder();
        builder.selectRaw("select avg(queueSize)");

        try {
            long l = statDao.queryRawValue(builder.prepareStatementString());
            return (int) l;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OfficeQueueStat> findAll() {
        try {
            return statDao.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
