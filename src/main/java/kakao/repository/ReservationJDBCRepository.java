package kakao.repository;

import domain.Reservation;
import domain.Theme;
import kakao.error.ErrorCode;
import kakao.error.exception.RecordNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationJDBCRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationJDBCRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    private static final RowMapper<Reservation> customerRowMapper = (resultSet, rowNum) -> {
        Long id = resultSet.getLong("id");
        LocalDate date = resultSet.getDate("date").toLocalDate();
        LocalTime time = resultSet.getTime("time").toLocalTime();
        String name = resultSet.getString("name");
        String themeName = resultSet.getString("theme_name");
        String themeDesc = resultSet.getString("theme_desc");
        Integer themePrice = resultSet.getInt("theme_price");
        return new Reservation(id, date, time, name, new Theme(themeName, themeDesc, themePrice));
    };

    public long save(Reservation reservation) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(reservation);

        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    public Reservation findById(Long id) {
        String SELECT_SQL = "select * from reservation where id=?";
        try {
            return jdbcTemplate.queryForObject(SELECT_SQL, customerRowMapper, id);
        } catch (DataAccessException e) {
            throw new RecordNotFoundException(ErrorCode.RESERVATION_NOT_FOUND);
        }
    }

    public List<Reservation> findByDateAndTime(LocalDate date, LocalTime time) {
        String SELECT_SQL = "select * from reservation where date=? and time=?";

        return jdbcTemplate.query(SELECT_SQL, customerRowMapper, date, time);
    }

    public void delete(Long id) {
        String DELETE_SQL = "delete from reservation where id=?";
        
        if (jdbcTemplate.update(DELETE_SQL, id) == 0) {
            throw new RecordNotFoundException(ErrorCode.RESERVATION_NOT_FOUND);
        }
    }
}
