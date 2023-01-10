package nextstep.domain.repository;

public class Queries {

    public static class Reservation {
        public static final String INSERT_SQL = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
        public static final String SELECT_BY_ID_SQL = "SELECT * FROM reservation WHERE id = ?";
        public static final String SELECT_COUNT_BY_DATE_AND_TIME_SQL = "SELECT COUNT(*) FROM reservation WHERE date = ? AND time = ?";
        public static final String DELETE_BY_ID_SQL = "DELETE FROM reservation WHERE id = ?";
        public static final String DELETE_ALL_SQL = "DELETE FROM reservation";
    }

}