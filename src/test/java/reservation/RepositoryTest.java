package reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import reservation.domain.Reservation;
import reservation.domain.Theme;
import reservation.domain.dto.ReservationDto;
import reservation.respository.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class RepositoryTest {
    private ReservationRepository reservationRepository;
    private final Theme theme;
    private final ReservationDto reservationDto;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public RepositoryTest() {
        theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

        // String to LocalDate
        LocalDate date = LocalDate.of(2023, 1, 1);
        LocalTime time = LocalTime.of(11, 0);

        reservationDto = new ReservationDto(date, time, "name");
    }

    @BeforeEach
    void setUp() {
        reservationRepository = new ReservationRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("예약 생성이 되어야 한다.")
    void save() {
        Long id = reservationRepository.createReservation(reservationDto, theme);
        assertThat(id).isGreaterThan(0);
    }

    @Test
    @DisplayName("생성된 예약을 조회할 수 있어야 한다.")
    void find() {
        Long id = reservationRepository.createReservation(reservationDto, theme);
        Reservation reservation = reservationRepository.getReservation(id);
        assertThat(reservation.getTheme()).isEqualTo(theme);
    }

    @Test
    @DisplayName("생성된 예약을 취소할 수 있어야 한다.")
    void delete() {
        Long id = reservationRepository.createReservation(reservationDto, theme);
        int rowCount = reservationRepository.deleteReservation(id);
        assertThat(rowCount).isEqualTo(1);
    }

    @Test
    @DisplayName("시간과 날짜가 중복되는 예약은 불가능하다.")
    void duplicate() {
        Long id = reservationRepository.createReservation(reservationDto, theme);
        assertThat(reservationRepository.existReservation(reservationDto.getDate(), reservationDto.getTime()))
                .isTrue();
    }
}