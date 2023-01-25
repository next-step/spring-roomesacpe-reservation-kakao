package roomescape.repository.Reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.config.AppConfig;
import roomescape.domain.Reservation;
import roomescape.repository.DBMapper.DatabaseMapper;
import roomescape.repository.DBMapper.H2Mapper;

import java.sql.*;
import java.util.Optional;

@Repository
public class JdbcReservationRepository implements ReservationRepository{
    public final JdbcTemplate jdbcTemplate;
    private final DatabaseMapper databaseMapper;

    @Autowired
    public JdbcReservationRepository() throws ClassNotFoundException {
        this.jdbcTemplate = new JdbcTemplate(AppConfig.getDataSource());
        this.databaseMapper = new H2Mapper();
    }

    @Override
    public Long createReservation(Reservation reservation) {
        String sql = "INSERT INTO RESERVATION (date, time, name, theme_id) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = autoIncrementKeyStatement(sql, reservation);
        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return databaseMapper.getAutoKey(keyHolder);
    }

    @Override
    public Optional<Reservation> findReservationById(long reservationId) {
        String sql = "select date, time, name, theme_id, from RESERVATION where id = ?";
        Reservation reservation = jdbcTemplate.queryForObject(sql,
                databaseMapper.reservationRowMapper(reservationId), reservationId);
        return Optional.ofNullable(reservation);
    }

    @Override
    public Boolean isExistsByDateAndTime(Reservation reservation) {
        String sql = "SELECT EXISTS(" +
                "SELECT 1 FROM RESERVATION WHERE date = ? AND time = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class,
                reservation.getDate(), reservation.getTime());
    }

    @Override
    public Integer deleteReservation(long deleteId) {
        String sql = "DELETE FROM RESERVATION WHERE id=?";
        return jdbcTemplate.update(sql, deleteId);
    }


    public Boolean isReservation(Long themeId) {
        String sql = "SELECT EXISTS(SELECT 1 FROM RESERVATION WHERE theme_id = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, themeId);
    }


    private PreparedStatementCreator autoIncrementKeyStatement(String sql, Reservation reservation) {
        return (connection) -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"});
            ps.setObject(1, reservation.getDate());
            ps.setObject(2, reservation.getTime());
            ps.setString(3, reservation.getName());
            ps.setLong(4, reservation.getThemeId());
            return ps;
        };
    }
}
