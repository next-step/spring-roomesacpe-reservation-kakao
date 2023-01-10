package kakao.service;

import kakao.domain.Reservation;
import kakao.dto.request.CreateReservationRequest;
import kakao.dto.response.ReservationResponse;
import kakao.error.ErrorCode;
import kakao.error.exception.DuplicatedReservationException;
import kakao.error.exception.RecordNotFoundException;
import kakao.repository.ReservationJDBCRepository;
import kakao.repository.ThemeRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ReservationService {

    private final ReservationJDBCRepository reservationJDBCRepository;

    public ReservationService(ReservationJDBCRepository reservationJDBCRepository) {
        this.reservationJDBCRepository = reservationJDBCRepository;
    }

    public long createReservation(CreateReservationRequest request) {
        boolean isDuplicate = reservationJDBCRepository.findByDateAndTime(request.date, request.time).size() > 0;
        if (isDuplicate) {
            throw new DuplicatedReservationException(ErrorCode.DUPLICATE_RESERVATION);
        }
        Reservation reservation = new Reservation(request.date, request.time, request.name, ThemeRepository.theme);
        return reservationJDBCRepository.save(reservation);
    }

    public ReservationResponse getReservation(Long id) {
        Reservation reservation = reservationJDBCRepository.findById(id);
        if (Objects.isNull(reservation)) {
            throw new RecordNotFoundException(ErrorCode.RESERVATION_NOT_FOUND);
        }
        return new ReservationResponse(reservation);
    }

    public void deleteReservation(Long id) {
        int deletedCount = reservationJDBCRepository.delete(id);
        if (deletedCount == 0) {
            throw new RecordNotFoundException(ErrorCode.RESERVATION_NOT_FOUND);
        }
    }
}