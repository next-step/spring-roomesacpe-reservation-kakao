package reservation.util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reservation.util.exception.ConnectionException;
import reservation.util.exception.DBException;
import reservation.util.exception.RestAPIException;


@RestControllerAdvice
public class ReservationExceptionHandler {

    // 예약 관련 Exception 핸들러
    @ExceptionHandler(RestAPIException.class)
    public ResponseEntity<String> handleReservationException(RestAPIException e){
        return ResponseEntity.status(e.getHttpStatus())
                .body(e.getErrorMessage());
    }

    // DB 관련 Exception 핸들러
    @ExceptionHandler(DBException.class)
    public ResponseEntity<String> handleDBException(DBException e){
        return ResponseEntity.status(e.getHttpStatus())
                .body(e.getErrorMessage());
    }
}
