package hr.algebra.java.web.lego.legostorewithdata.repository;


import hr.algebra.java.web.lego.legostorewithdata.domain.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class LogRepositoryJdbc implements LogRepository {

    private static final String LOG_TABLE_NAME = "Log";
    private static final String LOG_TABLE_COLUMN_NAME = "message";

    private static final String INSERT_LOG = "INSERT INTO Log (" + LOG_TABLE_COLUMN_NAME + ") VALUES (?)";
    private static final String SELECT_ALL_LOGS = "SELECT " + LOG_TABLE_COLUMN_NAME + " FROM Log";

    private JdbcTemplate jdbcTemplate;

    public LogRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Log mapRowToLog(ResultSet rs, int rowNum) throws SQLException {
        Log log = new Log();
        log.setMessage(rs.getString(LOG_TABLE_COLUMN_NAME));
        return log;
    }

    @Override
    public void createLog(String log) {
        jdbcTemplate.update(INSERT_LOG, log);
    }

    @Override
    public List<Log> getAllLogs() {
        return jdbcTemplate.query(SELECT_ALL_LOGS, this::mapRowToLog);
    }
}
