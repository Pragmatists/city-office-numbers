package pl.pragmatists.cityofficenumbers.db;

import java.io.InputStream;
import java.sql.SQLException;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import pl.pragmatists.cityofficenumbers.stats.OfficeQueueStat;
import pl.pragmatists.cityofficenumbers.stats.OrmLiteStatsRepository;
import pl.pragmatists.cityofficenumbers.stats.StatsRepository;
import pl.pragmatists.cityofficenumbers.stats.TimeProvider;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "cityOfficeStats.db";

    private static final int DATABASE_VERSION = 5;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, (InputStream) null);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, OfficeQueueStat.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, OfficeQueueStat.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    public StatsRepository getStatsRepository() {
        Dao<OfficeQueueStat, Long> dao = getDao();
        return new OrmLiteStatsRepository(dao, new TimeProvider());
    }

    private Dao<OfficeQueueStat, Long> getDao() {
        try {
            return getDao(OfficeQueueStat.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
