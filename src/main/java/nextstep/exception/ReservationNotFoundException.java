package nextstep.exception;

public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException(Long id) {
        super(id + "에 해당하는 예약이 없습니다.");
    }
}