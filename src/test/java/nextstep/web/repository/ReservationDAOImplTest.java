package nextstep.web.repository;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.web.repository.ReservationDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class ReservationDAOImplTest {
    private ReservationDAOImpl reservationDAOImpl;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        reservationDAOImpl = new ReservationDAOImpl(jdbcTemplate);

        jdbcTemplate.execute("DROP TABLE reservation IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE RESERVATION("
                + "    id          bigint not null auto_increment,"
                + "    date        date,"
                + "    time        time,"
                + "    name        varchar(20),"
                + "    theme_name  varchar(20),"
                + "    theme_desc  varchar(255),"
                + "    theme_price int,"
                + "    primary key (id)"
                + ")");

        List<Object[]> reservations = List.of(
                new Object[]{Date.valueOf("2022-08-11"), Time.valueOf("13:00:00"), "name", "워너고홈", "병맛 어드벤처 회사 코믹물",
                        29_000},
                new Object[]{Date.valueOf("2022-08-11"), Time.valueOf("14:00:00"), "name2", "워너고홈", "병맛 어드벤처 회사 코믹물",
                        29_000}
        );

        jdbcTemplate.batchUpdate(
                "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);",
                reservations);
    }

    @Test
    @DisplayName("ID를 통해 예약 정보를 잘 받아오는지 테스트")
    void findByIdTest() {
        Reservation reservation = reservationDAOImpl.findById(2L);

        assertThat(reservation).isNotNull();
        assertThat(reservation.getName()).isEqualTo("name2");
    }

    @Test
    @DisplayName("예약 날짜와 시간이 일치하는 예약 정보를 잘 받아오는지 테스트")
    void findByDateAndTimeTest() {
        List<Reservation> reservations = reservationDAOImpl.findByDateAndTime(
                LocalDate.of(2022, 8, 11),
                LocalTime.of(13, 0)
        );

        assertThat(reservations).hasSize(1);
        assertThat(reservations.get(0).getName()).isEqualTo("name");
    }

    @Test
    @DisplayName("예약 정보를 생성하고 생성한 예약의 ID를 잘 받아오는지 테스트")
    void insertWithKeyHolderTest() {
        Reservation reservation = new Reservation(
                LocalDate.of(2022, 8, 11),
                LocalTime.of(13, 0),
                "name3",
                new Theme(
                        "워너고홈",
                        "병맛 어드벤처 회사 코믹물",
                        29000
                )
        );
        Long id = reservationDAOImpl.insertWithKeyHolder(reservation);

        assertThat(id).isNotNull();

        Reservation reservationById = reservationDAOImpl.findById(id);
        assertThat(reservationById).isNotNull();
        assertThat(reservationById.getName()).isEqualTo("name3");
    }


    @Test
    @DisplayName("ID가 일치하는 예약을 잘 삭제하는지 테스트")
    void deleteTest() {
        int rowNum = reservationDAOImpl.delete(1L);

        assertThat(rowNum).isEqualTo(1);
    }
}