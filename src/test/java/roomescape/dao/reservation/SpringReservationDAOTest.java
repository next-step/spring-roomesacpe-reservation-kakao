package roomescape.dao.reservation;

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

@DisplayName("JDBC 데이터베이스 접근 테스트")
@JdbcTest
@Sql("classpath:/test.sql")
public class SpringReservationDAOTest {

    private static final LocalDate DATE_DATA1 = LocalDate.parse("2022-08-01");
    private static final LocalDate DATE_DATA2 = LocalDate.parse("2022-08-02");
    private static final LocalTime TIME_DATA = LocalTime.parse("13:00");
    private static final String NAME_DATA = "test";
    private static final Long THEME_ID_DATA = 1L;

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
    void createReservation() {
        Reservation reservation = new Reservation(DATE_DATA2, TIME_DATA, NAME_DATA, THEME_ID_DATA);
        reservationDAO.create(reservation);

        Long count = jdbcTemplate.queryForObject(COUNT_SQL, Long.class);
        assertThat(count).isEqualTo(2L);
    }

    @DisplayName("예약 조회")
    @Test
    void findReservation() {
        Reservation reservation = reservationDAO.find(1L);

        assertThat(reservation.getName()).isEqualTo(NAME_DATA);
        assertThat(reservation.getDate()).isEqualTo(DATE_DATA1);
        assertThat(reservation.getTime()).isEqualTo(TIME_DATA);
        assertThat(reservation.getThemeId()).isEqualTo(THEME_ID_DATA);
    }

    @DisplayName("예약 삭제")
    @Test
    void removeReservation() {
        reservationDAO.remove(1L);

        Long count = jdbcTemplate.queryForObject(COUNT_SQL, Long.class);
        assertThat(count).isEqualTo(0L);
    }

    @DisplayName("예약 존재 확인")
    @Test
    void existReservation() {
        Reservation reservation1 = new Reservation(DATE_DATA1, TIME_DATA, NAME_DATA, THEME_ID_DATA);
        Reservation reservation2 = new Reservation(DATE_DATA2, TIME_DATA, NAME_DATA, THEME_ID_DATA);

        assertThat(reservationDAO.exist(reservation1)).isTrue();
        assertThat(reservationDAO.exist(reservation2)).isFalse();
    }

    @DisplayName("예약 아이디 존재 확인")
    @Test
    void existReservationId() {
        assertThat(reservationDAO.existId(1L)).isTrue();
        assertThat(reservationDAO.existId(2L)).isFalse();
    }
}