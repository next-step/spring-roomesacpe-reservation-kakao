package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import roomescape.dto.Reservation;
import roomescape.dto.Theme;

@DisplayName("JDBC 데이터베이스 접근 테스트")
@JdbcTest
@Sql("classpath:/test.sql")
public class SpringReservationDAOTest {

    private static final LocalDate DATE_DATA1 = LocalDate.parse("2022-08-01");
    private static final LocalDate DATE_DATA2 = LocalDate.parse("2022-08-02");
    private static final LocalTime TIME_DATA = LocalTime.parse("13:00");
    private static final String NAME_DATA = "test";
    private static final String THEME_NAME_DATA = "워너고홈";
    private static final String THEME_DESC_DATA = "병맛 어드벤처 회사 코믹물";
    private static final int THEME_PRICE_DATA = 29000;

    private static final Theme THEME_DATA = new Theme(
            THEME_NAME_DATA, THEME_DESC_DATA, THEME_PRICE_DATA);

    private static final String COUNT_SQL = "SELECT count(*) FROM RESERVATION";

    private ReservationDAO reservationDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        reservationDAO = new SpringReservationDAO(jdbcTemplate);
    }

    @DisplayName("예약 생성")
    @Test
    void addReservation() {
        Reservation reservation = new Reservation(DATE_DATA2, TIME_DATA, NAME_DATA, THEME_DATA);
        reservationDAO.addReservation(reservation);

        Long count = jdbcTemplate.queryForObject(COUNT_SQL, Long.class);
        assertThat(count).isEqualTo(2L);
    }

    @DisplayName("예약 조회")
    @Test
    void findReservation() {
        Reservation reservation = reservationDAO.findReservation(1L);

        assertThat(reservation.getName()).isEqualTo(NAME_DATA);
        assertThat(reservation.getDate()).isEqualTo(DATE_DATA1);
        assertThat(reservation.getTime()).isEqualTo(TIME_DATA);
        assertThat(reservation.getTheme().getName()).isEqualTo(THEME_NAME_DATA);
        assertThat(reservation.getTheme().getDesc()).isEqualTo(THEME_DESC_DATA);
        assertThat(reservation.getTheme().getPrice()).isEqualTo(THEME_PRICE_DATA);
    }

    @DisplayName("예약 삭제")
    @Test
    void deleteReservation() {
        reservationDAO.deleteReservation(1L);

        Long count = jdbcTemplate.queryForObject(COUNT_SQL, Long.class);
        assertThat(count).isEqualTo(0L);
    }
}
