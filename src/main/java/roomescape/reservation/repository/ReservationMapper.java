package roomescape.reservation.repository;

import roomescape.reservation.domain.Reservation;
import roomescape.theme.domain.Theme;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationMapper {
    private ReservationMapper() {
    }

    public static Reservation mapToReservation(ResultSet resultSet) throws SQLException {
        return new Reservation(
                resultSet.getLong("id"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime(),
                resultSet.getString("name"),
                new Theme(
                        resultSet.getString("theme_name"),
                        resultSet.getString("theme_desc"),
                        resultSet.getInt("theme_price")
                )
        );
    }
}