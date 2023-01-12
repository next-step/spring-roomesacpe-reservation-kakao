package nextstep.domain.reservation.repository;

import nextstep.domain.reservation.domain.Reservation;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface ReservationRepository {
    Long save(Reservation reservation);

    int countByDateAndTime(LocalDate date, LocalTime time);

    Reservation findById(Long id);

    void delete(Long id);
}