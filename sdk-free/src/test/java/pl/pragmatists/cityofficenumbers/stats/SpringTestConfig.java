package pl.pragmatists.cityofficenumbers.stats;

import java.sql.SQLException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.j256.ormlite.jdbc.JdbcConnectionSource;

@Configuration
public class SpringTestConfig {

    @Bean
    public JdbcConnectionSource connectionSource() throws SQLException {
        return new JdbcConnectionSource("jdbc:h2:mem:account", "", "");
    }
}
