package kakao.service;

import kakao.dto.request.CreateReservationRequest;
import kakao.dto.response.ReservationResponse;
import kakao.error.exception.DuplicatedReservationException;
import kakao.error.exception.RecordNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest
public class ReservationServiceTest {

    @Autowired
    ReservationService reservationService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("TRUNCATE TABLE reservation");
        jdbcTemplate.execute("ALTER TABLE reservation ALTER COLUMN id RESTART WITH 1");
    }

    private final CreateReservationRequest request = new CreateReservationRequest(
            LocalDate.of(2022, 10, 13),
            LocalTime.of(13, 00),
            "baker"
    );

    @DisplayName("CreateRequest를 받아 해앋하는 새로운 예약을 생성해 데이터베이스에 저장한다")
    @Test
    void createReservation() {
        Assertions.assertThatNoException().isThrownBy(() ->
                reservationService.createReservation(request));
    }

    @DisplayName("이미 저장된 날짜와 시간에 예약이 발생하면 DuplicatedReservation 예외를 발생한다")
    @Test
    void duplicateReservation() {
        reservationService.createReservation(request);

        Assertions.assertThatExceptionOfType(DuplicatedReservationException.class)
                .isThrownBy(() -> reservationService.createReservation(request));
    }

    @DisplayName("id로 저장된 reservation을 조회해 ReservationResponse를 반환한다")
    @Test
    void getReservation() {
        reservationService.createReservation(request);
        ReservationResponse response = reservationService.getReservation(1L);

        Assertions.assertThat(request.date).isEqualTo(response.date);
        Assertions.assertThat(request.time).isEqualTo(response.time);
        Assertions.assertThat(request.name).isEqualTo(response.name);
    }

    @DisplayName("저장되지 않은 id로 조회하면 RecordNotFound 예외를 발생한다")
    @Test
    void getNoIdReservation() {
        Assertions.assertThatExceptionOfType(RecordNotFoundException.class)
                .isThrownBy(() -> reservationService.getReservation(10L));
    }

    @DisplayName("id에 해당되는 예약을 삭제한다")
    @Test
    void delete() {
        reservationService.createReservation(request);
        Assertions.assertThatNoException().isThrownBy(() ->
                reservationService.deleteReservation(1L));
    }

    @DisplayName("존재하지 않는 id 삭제를 시도하면 RecordNotFound 예외를 발생한다")
    @Test
    void deleteNoId() {
        Assertions.assertThatExceptionOfType(RecordNotFoundException.class)
                .isThrownBy(() -> reservationService.deleteReservation(10L));
    }
}
