package roomescape.reservation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoSuchReservationException extends RuntimeException {
    public NoSuchReservationException() {
        super("id에 해당하는 예약이 존재하지 않습니다.");
    }
}
