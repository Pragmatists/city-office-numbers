package pl.pragmatists.cityofficenumbers.stats;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;

public class OrmLiteStatsRepository implements StatsRepository {
    private final Dao<OfficeQueueStat, Long> statDao;

    private final TimeProvider timeProvider;

    public OrmLiteStatsRepository(Dao<OfficeQueueStat, Long> statDao, TimeProvider timeProvider) {
        this.statDao = statDao;
        this.timeProvider = timeProvider;
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
        SelectArg low = new SelectArg(timeProvider.todayLow());
        SelectArg high = new SelectArg(timeProvider.todayHigh());
        try {
            builder.selectRaw("avg(queueSize)")
                    .where().eq("officeId", officeId)
                    .and().eq("groupId", groupId)
                    .and().between("timestamp", low, high);
            return (int) statDao.queryRawValue(
                    builder.prepareStatementString(),
                    low.toString(),
                    high.toString());
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
