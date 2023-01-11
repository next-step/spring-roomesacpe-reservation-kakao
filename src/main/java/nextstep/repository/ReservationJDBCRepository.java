package nextstep.repository;

import nextstep.dto.ReservationRequestDTO;
import nextstep.entity.Reservation;
import nextstep.entity.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static nextstep.entity.ThemeConstants.*;

@Repository
public class ReservationJDBCRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationJDBCRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional
    @Override
    public Long save(ReservationRequestDTO reservationRequestDTO) throws DataAccessException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("date", Date.valueOf(reservationRequestDTO.getDate()));
        parameters.put("time", Time.valueOf(reservationRequestDTO.getTime()));
        parameters.put("name", reservationRequestDTO.getName());
        parameters.put("theme_name", THEME_NAME);
        parameters.put("theme_desc", THEME_DESC);
        parameters.put("theme_price", THEME_PRICE);
        return new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("RESERVATION")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(parameters)
                .longValue();
    }

    @Override
    public Reservation findById(Long id) throws DataAccessException {
        String sql = "SELECT * from reservation WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new Reservation(rs.getLong("id"), rs.getDate("date").toLocalDate(),
                        rs.getTime("time").toLocalTime(), rs.getString("name"),
                        new Theme(rs.getString("theme_name"),
                                rs.getString("theme_desc"), rs.getInt("theme_price"))), id);
    }

    @Override
    public boolean existByDateAndTime(LocalDate date, LocalTime time)
            throws DataAccessException {
        String sql = "SELECT * from reservation WHERE date = ? AND time = ?";
        return !jdbcTemplate.query(sql, (rs, rowNum) ->
                        new Reservation(rs.getLong("id"), rs.getDate("date").toLocalDate(),
                                rs.getTime("time").toLocalTime(), rs.getString("name"),
                                new Theme(rs.getString("theme_name"),
                                        rs.getString("theme_desc"), rs.getInt("theme_price"))), date, time)
                .isEmpty();
    }

    @Transactional
    @Override
    public int deleteById(Long id) throws DataAccessException {
        return jdbcTemplate.update("DELETE FROM RESERVATION WHERE id = ?", id);
    }
}