package pl.pragmatists.cityofficenumbers.stats;

import java.sql.SQLException;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.spring.DaoFactory;
import com.j256.ormlite.spring.TableCreator;
import com.j256.ormlite.support.ConnectionSource;

@Configuration
public class SpringTestConfig {

    @Bean
    public ConnectionSource connectionSource() throws SQLException {
        return new JdbcConnectionSource("jdbc:h2:mem:account", "", "");
    }

    @Bean
    public StatsRepository statsRepository(ConnectionSource jdbcConnectionSource) {
        return new OrmLiteStatsRepository(initDao(jdbcConnectionSource));
    }
    private Dao<OfficeQueueStat, Long> initDao(ConnectionSource connectionSource)  {
        try {
            System.setProperty(TableCreator.AUTO_CREATE_TABLES, "true");
            Dao<OfficeQueueStat, Long> statDao = DaoFactory.createDao(connectionSource, OfficeQueueStat.class);
            new TableCreator(connectionSource, Arrays.<Dao<?, ?>>asList(statDao)).initialize();
            return statDao;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
